package dao;

import Model.Municipality;
import Model.Report;
import Model.ProblemType;
import Model.UrgencyType;
import Model.users.User;
import exceptions.DataAccessException;
import javafx.scene.image.Image;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DbReportDao extends ReportDao {

    @Override
    public void addReport(Report report) {
        String sql = """
            INSERT INTO reports(
               report_id, title, description,
               problem_type, urgency_type,
               image_path, status,
               author_username, mun_name, mun_province
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, report.getReportId());
            ps.setString(2, report.getTitle());
            ps.setString(3, report.getDescription());
            ps.setString(4, report.getProblemType().name());
            ps.setString(5, report.getUrgencyType().name());
            // se non c'è immagine, salvo NULL
            ps.setString(6, report.getImagePath());;
            ps.setString(7, report.getStatus());
            ps.setString(8, report.getAuthor().getUsername());
            ps.setString(9, report.getMunicipality().getName());
            ps.setString(10, report.getMunicipality().getProvince());

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DataAccessException("Errore inserendo nuovo report", ex);

        }
    }

    @Override
    public void updateReport(Report report, String newStatus) {
        String sql = """
            UPDATE reports
               SET status = ?
             WHERE report_id = ?
            """;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setString(2, report.getReportId());
            int updated = ps.executeUpdate();
            if (updated == 0) {
                throw new DataAccessException(
                        "Nessun report trovato con ID " + report.getReportId());
            }
        } catch (SQLException ex) {
            throw new DataAccessException(
                    "Errore aggiornando lo status del report " + report.getReportId(), ex);
        }
    }

    @Override
    public List<Report> getReportsOfUser(String username) {
        String sql = """
            SELECT report_id, title, description,
                   problem_type, urgency_type,
                   image_path, status,
                   author_username, mun_name, mun_province
              FROM reports
             WHERE author_username = ?
            """;
        List<Report> list = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToReport(rs));
                }
            }
            return list;
        } catch (SQLException ex) {
            throw new DataAccessException(
                    "Errore caricando report di utente " + username, ex);
        }
    }

    @Override
    public List<Report> getAllReportsOfMunicipality(Municipality municipality) {
        String sql = """
            SELECT report_id, title, description,
                   problem_type, urgency_type,
                   image_path, status,
                   author_username, mun_name, mun_province
              FROM reports
             WHERE mun_name = ? AND mun_province = ?
            """;
        List<Report> list = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, municipality.getName());
            ps.setString(2, municipality.getProvince());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToReport(rs));
                }
            }
            return list;
        } catch (SQLException ex) {
            throw new DataAccessException(
                    "Errore caricando report per comune "
                            + municipality.getName()
                            + ", " + municipality.getProvince(), ex);
        }
    }

    /**
     * Mappa una singola riga di ResultSet in un oggetto Report.
     * Associa correttamente autore (Citizen) e comune.
     */
    private Report mapRowToReport(ResultSet rs) throws SQLException {
        Report r = new Report();
        r.setReportId   (rs.getString("report_id"));
        r.setTitle      (rs.getString("title"));
        r.setDescription(rs.getString("description"));
        r.setProblemType(
                Enum.valueOf(ProblemType.class, rs.getString("problem_type"))
        );
        r.setUrgencyType(
                Enum.valueOf(UrgencyType.class, rs.getString("urgency_type"))
        );
        r.setStatus     (rs.getString("status"));

        // ricostruisco l'immagine dal path salvato
        String imgPath = rs.getString("image_path");
        if (imgPath != null && !imgPath.isBlank()) {
            r.setImage(new Image(imgPath));
        }

        // autore (solo Citizen può creare report)
        String authorUsername = rs.getString("author_username");
        User author = FactoryDao.getInstance()
                .createUserDao()
                .findByUsername(authorUsername);
        if (!(author instanceof Model.users.Citizen)) {
            throw new DataAccessException(
                    "Autore non è un cittadino: " + authorUsername);
        }
        r.setAuthor((Model.users.Citizen) author);

        // comune
        String munCode = rs.getString("mun_province"); // puoi anche usare mun_name
        r.setMunicipality(
                FactoryDao.getInstance()
                        .createMunicipalityDao()
                        .getMunicipalityByCode(munCode)
        );

        return r;
    }
}
