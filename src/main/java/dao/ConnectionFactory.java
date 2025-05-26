package dao;

import exceptions.DataAccessException;



import exceptions.DataAccessException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static Connection connection;

    static {
        // Carica il file da src/main/resources/config/db.properties
        try (InputStream input = ConnectionFactory.class
                .getClassLoader()
                .getResourceAsStream("config/db.properties")) {
            if (input == null) {
                throw new DataAccessException("File di configurazione 'config/db.properties' non trovato nel classpath.");
            }

            Properties properties = new Properties();
            properties.load(input);

            String connectionUrl = properties.getProperty("CONNECTION_URL");
            String user          = properties.getProperty("LOGIN_USER");
            String pass          = properties.getProperty("LOGIN_PASS");

            connection = DriverManager.getConnection(connectionUrl, user, pass);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Errore nella connessione al database.", e);
        }
    }

    /** Restituisce la Connection singleton. */
    public static Connection getConnection() {
        return connection;
    }
}
