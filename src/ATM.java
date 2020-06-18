import java.sql.*;

public class ATM {

    public static void main(String[] args) throws SQLException {

        //new UserAccount("John", "Doe", "939467012", 750, 12345, 1,1);
        //new Transaction("234 567 384", 5.55, 597273);
        //new Loan(597273, 1000, 24, 1);

    }

    static boolean checkCredentials(int accountToCheck, int pinToCheck) throws SQLException {
        Connex connex = new Connex();
        Connection connection = connex.connects();
        int pinDB = 0;

        String sql = "SELECT userPIN from useraccount WHERE accountId = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, accountToCheck);
        ResultSet result = stmt.executeQuery();
        if (result.next())
            pinDB = result.getInt(1);

        return pinDB == pinToCheck;
    }
}
