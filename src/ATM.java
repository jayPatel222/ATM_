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
                addUser user = null;
                try {
                    user = new addUser();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                user.setVisible(true);
            }
        });



    }


}
