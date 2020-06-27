import javax.swing.*;
import java.sql.*;

public class ATM {

    public static void main(String[] args) throws SQLException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                atmInterface user = null;
                try {
                    user = new atmInterface();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                user.setVisible(true);
            }
        });

        //new UserAccount("John", "Doe", "939467012", 750, 12345, 1,1);
        //new Transaction("234 567 384", 5.55, 597273);
        //new Loan(597273, 1000, 24, 1);

    }


}
