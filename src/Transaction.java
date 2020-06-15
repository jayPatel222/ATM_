import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Transaction {

    Connex connex = new Connex();
    Connection connection;

    public Transaction(double amount, int account, int transactionType) throws SQLException {
        connection = connex.connects();

        String sql = "INSERT INTO transaction (transactionAmount, transactionDateTime, transactionAccountId," +
                    "transactionTypeId) VALUES (?,CURRENT_TIMESTAMP,?,?);";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setDouble(1, amount);
        stmt.setInt(2, account);
        stmt.setInt(3, transactionType);
        stmt.executeUpdate();

    }
}
