package testing;

import dao.ConnectionFactory;
import dao.FactoryDao;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

 class DaoAndConnectionTest {


    @Test
    void testGetInstance() {
        try {
            FactoryDao factoryDao = FactoryDao.getInstance();
            assertNotNull(factoryDao, "FactoryDao instance should be not null");
        } catch (Exception e) {
            fail("Exception while creating FactoryDao instance: " + e.getMessage());
        }
    }

    @Test
    void testGetConnection() {
        try {
            Connection connection = ConnectionFactory.getConnection();

            assertNotNull(connection, "DB connection should be not null");
            assertTrue(connection.isValid(2), "DB connection should be valid");

        } catch (SQLException e) {
            fail("Exception while connecting to DB: " + e.getMessage());
        }
    }


}
