import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.*;

//CHANGE USERNAME AND PASSWORD ACCORDINGLY
public class DatabaseConnectionManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_ms";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Password123#@!";

    private static DatabaseConnectionManager instance;
    private Connection connection;

    private DatabaseConnectionManager() {
        // Private constructor to prevent external instantiation
    }

    public static synchronized DatabaseConnectionManager getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            }
        } catch (SQLException e) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "Database connection failure! Check credentials.",
               "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// USAGE:
// Get the database connection instance
// DatabaseConnectionManager connectionManager = DatabaseConnectionManager.getInstance();
// Connection connection = connectionManager.getConnection();
// Now you can use the 'connection' object to perform database operations