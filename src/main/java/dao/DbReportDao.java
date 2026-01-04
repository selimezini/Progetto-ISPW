package dao;


import model.Report;
import model.ProblemType;
import model.UrgencyType;
import model.users.Citizen;
import model.users.User;
import exceptions.DataAccessException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DbReportDao extends ReportDao {

    @Override
    public void addReport(Report report) {
        String sql = """
        INSERT INTO reports (
            report_id,
            title,
            description,
            problem_type,
            urgency_type,
            image_path,
            status,
            author_username,
            mun_name,
            mun_province,
            via_problema
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try  {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, report.getReportId());
            ps.setString(2, report.getTitle());
            ps.setString(3, report.getDescription());
            ps.setString(4, report.getProblemType().name());
            ps.setString(5, report.getUrgencyType().name());


            if (report.getImagePath() != null && !report.getImagePath().isBlank()) {
                ps.setString(6, report.getImagePath());
            } else {
                ps.setNull(6, Types.VARCHAR);
            }

            ps.setString(7, report.getStatus());
            ps.setString(8, report.getAuthor().getUsername());
            ps.setString(9, report.getMunicipality().getName());
            ps.setString(10, report.getMunicipality().getProvince());


            ps.setString(11, report.getViaDelProblema());

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new DataAccessException("Errore inserendo nuovo report", ex);
        }
    }

    @Override
    public void updateReport(String id, String newStatus) {
        String sql = """
        UPDATE reports
           SET status = ?
         WHERE report_id = ?
        """;

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newStatus);
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DataAccessException("Errore aggiornando lo stato del report con ID: " + id, ex);
        }
    }


    @Override
    public List<Report> getReportsOfUser(String username) {
        return List.of();
    }

    @Override
    public List<Report> getAllReportsOfMunicipality(String munName, String munProvince) {
        String sql = """
        SELECT report_id, title, description,
               problem_type, urgency_type,
               image_path, status, created_at,
               author_username, via_problema
          FROM reports
         WHERE mun_name     = ?
           AND mun_province = ?
         ORDER BY created_at
        """;

        List<Report> list = new ArrayList<>();
        try  {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, munName);
            ps.setString(2, munProvince);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    Report r = mapRowToReport(rs);
                    list.add(r);
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(
                    "Errore caricando report per comune " + munName + "/" + munProvince, ex);
        }
        return list;
    }



    private Report mapRowToReport(ResultSet rs) throws SQLException {
        Report r = new Report();


        r.setReportId(rs.getString("report_id"));


        r.setTitle(rs.getString("title"));
        r.setDescription(rs.getString("description"));


        r.setProblemType(ProblemType.valueOf(rs.getString("problem_type")));
        r.setUrgencyType(UrgencyType.valueOf(rs.getString("urgency_type")));


        r.setImagePath(rs.getString("image_path"));
        r.setStatus(rs.getString("status"));


        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) {
            r.setDate(new Date(ts.getTime()));
        }


        r.setViaDelProblema(rs.getString("via_problema"));


        String authorUsername = rs.getString("author_username");

        User author = FactoryDao.getInstance()
                .createUserDao()
                .findByUsername(authorUsername);
        if (!(author instanceof Citizen)) {
            throw new DataAccessException("Autore non è un cittadino: " + authorUsername);
        }
        r.setAuthor((Citizen) author);


        return r;
    }







}
