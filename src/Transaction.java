import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Transaction {

    Connex connex = new Connex();
    Connection connection;

    public Transaction(double amount, int account, int transactionType) throws SQLException {
        connection = connex.connects();

        if (transactionType == 1) // Deposit transaction
        {
            String sql = "INSERT INTO transaction (transactionAmount, transactionDateTime, transactionAccountId," +
                    "transactionTypeId) VALUES (?,CURRENT_TIMESTAMP,?,?);";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, amount);
            stmt.setInt(2, account);
            stmt.setInt(3, transactionType);
            stmt.executeUpdate();

            stmt.clearParameters();
            sql = "UPDATE useraccount SET accountBalance = accountBalance + ? WHERE accountId = ?;";
            stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, amount);
            stmt.setInt(2, account);
            stmt.executeUpdate();

            stmt.close();
            connection.close();
        }
        else //Withdrawal
        {
            double balance;
            String sql = "SELECT accountBalance FROM useraccount WHERE accountId = ?;";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, account);

            ResultSet result = stmt.executeQuery();
            if (result.next())
            {
                balance = result.getDouble(1);
                if (balance - amount < 0)
                {
                    System.out.println("Insufficient balance!");
                }
                else
                {
                    String sqlUp = "INSERT INTO transaction (transactionAmount, transactionDateTime, transactionAccountId," +
                            "transactionTypeId) VALUES (?,CURRENT_TIMESTAMP,?,?);";
                    PreparedStatement stmtUp = connection.prepareStatement(sqlUp);
                    stmtUp.setDouble(1, amount);
                    stmtUp.setInt(2, account);
                    stmtUp.setInt(3, transactionType);
                    stmtUp.executeUpdate();
                    stmtUp.clearParameters();

                    sqlUp = "UPDATE useraccount SET accountBalance = accountBalance - ? WHERE accountId = ?;";
                    stmtUp = connection.prepareStatement(sqlUp);
                    stmtUp.setDouble(1, amount);
                    stmtUp.setInt(2, account);
                    stmtUp.executeUpdate();
                }
            }
        }
    }
    // Payment has its own constructor
    public Transaction(String code, double amount, int account) throws SQLException {
        connection = connex.connects();

        double balance;
        String sql = "SELECT accountBalance FROM useraccount WHERE accountId = ?;";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, account);

        ResultSet result = stmt.executeQuery();
        if (result.next())
        {
            balance = result.getDouble(1);
            if (balance - amount < 0)
            {
                System.out.println("Insufficient balance!");
            }
            else
            {
                String sqlUp = "INSERT INTO transaction (transactionCode, transactionAmount, transactionDateTime," +
                        "transactionAccountId, transactionTypeId) VALUES (?,?,CURRENT_TIMESTAMP,?,?);";
                PreparedStatement stmtUp = connection.prepareStatement(sqlUp);
                stmtUp.setString(1, code);
                stmtUp.setDouble(2, amount);
                stmtUp.setInt(3, account);
                stmtUp.setInt(4, 1);
                stmtUp.executeUpdate();
                stmtUp.clearParameters();

                sqlUp = "UPDATE useraccount SET accountBalance = accountBalance - ? WHERE accountId = ?;";
                stmtUp = connection.prepareStatement(sqlUp);
                stmtUp.setDouble(1, amount);
                stmtUp.setInt(2, account);
                stmtUp.executeUpdate();
            }
        }
    }
}
