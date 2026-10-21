import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/smart_city";
    private static final String USER = "root";
    // Change this to your local MySQL password.
    private static final String PASSWORD =
    System.getenv("SMART_CITY_DB_PASSWORD");

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
