package dao;

import Model.Municipality;
import Model.users.Citizen;
import Model.users.Employee;
import Model.users.User;
import exceptions.DataAccessException;
import exceptions.UserNotFoundException;

import java.sql.*;

/**
 * DAO “live” per gli utenti su MySQL.
 * Non chiude mai la Connection; propaga sempre le eccezioni come DaoException.
 */
public class DbUserDao extends UserDao {

    private final Connection conn = ConnectionFactory.getConnection();

    @Override
    public void addUser(User user) {
        String sql = """
            INSERT INTO users(username, password_hash, role, mun_name, mun_province)
            VALUES (?, ?, ?, ?, ?)
            """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());

            if (user instanceof Employee) {
                Employee e = (Employee) user;
                Municipality m = e.getMyMunicipality();
                ps.setString(4, m.getName());
                ps.setString(5, m.getProvince());
            } else {
                ps.setNull(4, Types.VARCHAR);
                ps.setNull(5, Types.VARCHAR);
            }

            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Errore JDBC in addUser", ex);
        }
    }

    @Override
    public Citizen authenticateCitizen(String username, String password) {
        String sql = """
            SELECT 1
              FROM users
             WHERE username = ? 
               AND password_hash = ? 
               AND role = 'Citizen'
            """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new UserNotFoundException("Credenziali non valide per cittadino");
                }
                return new Citizen(username, password, "Citizen");
            }
        } catch (SQLException ex) {
            throw new UserNotFoundException();
        }
    }

    @Override
    public Employee authenticateEmployee(String username, String password, String municipalityCode) {
        // 1) verifica username/password e ruolo
        String sql = """
            SELECT 1
              FROM users
             WHERE username = ?
               AND password_hash = ?
               AND role = 'Employee'
            """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new UserNotFoundException("Credenziali non valide per dipendente");
                }
            }
        } catch (SQLException ex) {
            throw new UserNotFoundException();
        }

        // 2) recupera il Comune dal codice
        Municipality m = FactoryDao.getInstance()
                .createMunicipalityDao()
                .getMunicipalityByCode(municipalityCode);
        if (m == null) {
            throw new UserNotFoundException("Codice comune non valido");
        }

        Employee emp = new Employee(username, password, "Employee");
        emp.setMyMunicipality(m);
        return emp;
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
}
