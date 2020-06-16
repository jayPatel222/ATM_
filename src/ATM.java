import javax.swing.*;
import java.sql.*;

public class ATM {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
       SwingUtilities.invokeLater(new Runnable() {
           @Override
           public void run() {
               addUser user = null;
               try {
                   user = new addUser();
               } catch (SQLException throwables) {
                   throwables.printStackTrace();
               }

               user.setVisible(true);
           }
       });
        //new UserAccount("John", "Doe", "939467012", 750, 12345, 1,1);
        new Transaction("234 567 384", 3.33, 597273);

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
