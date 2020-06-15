import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connex {
    String url = "jdbc:mysql://localhost:3306/mydb";
    String username = "root";
    String password = "";

    public Connection connects()
    {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new Error("Problem", e);
        }
        return connection;
    }
}
