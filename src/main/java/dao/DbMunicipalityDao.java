package dao;

import model.Municipality;
import exceptions.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO per i Municipi su MySQL, che NON chiude mai
 * né PreparedStatement né ResultSet.
 * Si affida al garbage collector per il rilascio delle risorse.
 */
public class DbMunicipalityDao extends MunicipalityDao {

    private final Connection conn = ConnectionFactory.getConnection();

    @Override
    public List<Municipality> getMunicipalityByName(String nameFragment) {
        String sql = """
            SELECT name, province, codice, region
              FROM municipalities
             WHERE LOWER(name) LIKE ?
            """;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + nameFragment.toLowerCase() + "%");
            ResultSet rs = ps.executeQuery();

            List<Municipality> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Municipality(
                        rs.getString("name"),
                        rs.getString("province"),
                        rs.getString("codice"),
                        rs.getString("region")
                ));
            }
            return list;

        } catch (SQLException ex) {
            throw new DataAccessException("Errore cercando comuni per nome", ex);
        }
    }

    @Override
    public Municipality getMunicipalityByCode(String code) {
        String sql = """
            SELECT name, province, codice, region
              FROM municipalities
             WHERE codice = ?
            """;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Municipality(
                        rs.getString("name"),
                        rs.getString("province"),
                        rs.getString("codice"),
                        rs.getString("region")
                );
            }
            return null;

        } catch (SQLException ex) {
            throw new DataAccessException("Non trovato nessun comune con questo codice", ex);
        }
    }

    @Override
    public Municipality getMunicipalityByNameAndRegion(String name, String region) {
        String sql = """
        SELECT name, province, codice, region
          FROM municipalities
         WHERE name = ? AND region = ?
        """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, region);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Municipality(
                        rs.getString("name"),
                        rs.getString("province"),
                        rs.getString("codice"),
                        rs.getString("region")
                );
            }
            return null;
        } catch (SQLException ex) {
            throw new DataAccessException("Errore cercando comune per nome e regione", ex);
        }

    }

    }
