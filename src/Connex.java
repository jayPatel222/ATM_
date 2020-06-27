import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connex {
    String url = "jdbc:mysql://den1.mysql3.gear.host:3306/atmproject";
    String username = "atmproject";
    String password = "Bg8a6~1d2-p3";

    public Connection connects() {
        Connection connection = null;
        try {

            connection = DriverManager.getConnection(url,username,password);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
