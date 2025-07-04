package dao;

import model.Municipality;
import model.users.Citizen;
import model.users.Employee;
import model.users.User;
import exceptions.DataAccessException;
import exceptions.UserNotFoundException;

import java.sql.*;


public class DbUserDao extends UserDao {

    private final Connection conn = ConnectionFactory.getConnection();

    @Override
    public void addUser(User user) {
        String sql = """
        INSERT INTO users(username, password_hash, role, mun_code)
        VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());

            if (user instanceof Employee) {
                Employee e = (Employee) user;
                Municipality m = e.getMyMunicipality();
                // salvo il codice del comune
                ps.setString(4, m.getCodice());
            } else {
                ps.setNull(4, Types.VARCHAR);
            }

            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Errore JDBC in addUser", ex);
        }
    }




    @Override
    public User verifyUser(String username, String password, String role) {
        String sql = """
        SELECT 1
          FROM users
         WHERE username      = ?
           AND password_hash = ?
           AND role          = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new UserNotFoundException("Credenziali non valide per ruolo: " + role);
                }
            }

            // Costruisci il tipo corretto
            if ("Employee".equals(role)) {
                return new Employee(username, password, role);
            } else {
                return new Citizen(username, password, role);
            }

        } catch (SQLException ex) {
            throw new UserNotFoundException("Credenziali non valide per ruolo: " + role);
        }
    }








    @Override
    public User findByUsername(String username) {
        String sql = """
        SELECT username,
               password_hash,
               role,
               mun_code
          FROM users
         WHERE username = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username.trim());
            try (ResultSet rs = ps.executeQuery()) {
                // Se non esiste alcun utente con questo username, restituisco null
                if (!rs.next()) {
                    return null;
                }

                String pwdHash = rs.getString("password_hash");
                String role    = rs.getString("role");
                String munCode = rs.getString("mun_code"); // può essere anche NULL

                if ("Citizen".equalsIgnoreCase(role)) {
                    // Se è un cittadino, non serve mun_code
                    return new Citizen(username, pwdHash, role);
                }
                else {
                    // Se è un dipendente, eventualmente recupero la Municipality
                    Employee emp = new Employee(username, pwdHash, role);
                    if (munCode != null && !munCode.isBlank()) {
                        Municipality m = FactoryDao.getInstance()
                                .createMunicipalityDao()
                                .getMunicipalityByCode(munCode);
                        emp.setMyMunicipality(m);
                    }
                    return emp;
                }
            }
        } catch (SQLException ex) {
            throw new UserNotFoundException("Errore nella query di findByUsername");
        }
    }

    @Override
    public void updatePassword( String username, String newPassword) {
        String sql = """
        UPDATE users
           SET password_hash = ?
         WHERE username = ?
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newPassword);
            ps.setString(2, username);
            ps.executeUpdate();

        } catch (SQLException ex) {
            throw new DataAccessException("Errore aggiornando la password per utente: " + username, ex);
        }
    }

    @Override
    public void updateUsername( String username, String newUsername) {
        String sql = """
        UPDATE users
           SET username = ?
         WHERE username = ?
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newUsername);
            ps.setString(2, username);
            ps.executeUpdate();

        } catch (SQLException ex) {
            throw new DataAccessException("Errore aggiornando lo username da '" + username + "' a '" + newUsername + "'", ex);
        }
    }



}
