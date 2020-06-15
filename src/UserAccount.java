import java.sql.*;
import java.util.Random;

public class UserAccount {

    Connex connex = new Connex();
    Connection connection;

    public UserAccount(String firstName, String lastName, String ssn, int score, int pin, int accountType, int accountBank)
    {
        connection = connex.connects();

        try {
            int accountId = getUniqueAccount();

            String sql = "INSERT INTO useraccount (accountId, userFirstName, userLastName, userSSN, userScore, userPin," +
                         "accountTypeId, accountBankId) VALUES (?,?,?,?,?,?,?,?);";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, accountId);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, ssn);
            stmt.setInt(5, score);
            stmt.setInt(6, pin);
            stmt.setInt(7,accountType);
            stmt.setInt(8,accountBank);
            stmt.executeUpdate();

            System.out.printf("User: %s %s\nSSN: %s\nAccount Number: %d\n", firstName, lastName, ssn, accountId);

            stmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getUniqueAccount() throws SQLException {
        String accountNumber = "";
        Random rdm = new Random();
        int length = 6;
        boolean nonUnique = false;
        Connection connection = connex.connects();

        String sql = "SELECT accountId FROM useraccount;";
        Statement stmt = connection.createStatement();
        ResultSet result = stmt.executeQuery(sql);

        do{
            for (int i = 0; i < length; ++i)
                accountNumber += ((Integer)rdm.nextInt(10)).toString();
            while (result.next())
            {
                int valueToMatch = result.getInt(1);
                if (Integer.parseInt(accountNumber) == valueToMatch)
                {
                    nonUnique = true;
                    break;
                }
            }
        }while(nonUnique);

        return Integer.parseInt(accountNumber);
    }
}
