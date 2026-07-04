package db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {
    private static Connection conn = null;

    public static Connection getConnection() {
        if(conn == null) {
            Properties props = loadProperties();

            String url = props.getProperty("dburl");

            try {
                conn = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }

        return conn;
    }

    public static void CloseConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs);

            return props;
        } catch (IOException ex) {
            throw new DbException(ex.getMessage());
        }
    }

    public static void CloseStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        }
    }

    public static void CloseResultSet(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        }
    }
}
