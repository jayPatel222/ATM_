import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;

public class atmInterface extends JFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JButton proceedButton;
    private JPanel atmInterface1;
    private JPanel atmInterface;
    public  static int AcNo;

    public atmInterface() throws SQLException {

        Connex connex = new Connex();
        Connection connection = connex.connects();
        add(atmInterface);
        setTitle("ATM");
        setSize(600, 500);

        proceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                     AcNo = checkCredentials(parseInt(textField1.getText()),parseInt(textField2.getText()));
                    if (AcNo != 0){
                        dispose();
                       atmInterface.setVisible(false);
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                balanceInterface user = null;
                                try {
                                    user = new balanceInterface();
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }

                                user.setVisible(true);
                            }
                        });

                    }
                    else {
                        AcNo = 0;
                        JOptionPane.showMessageDialog(atmInterface1,"You have Do not Have Account " +
                                "Please Check Account No or Pin");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
    static int getNo() {
        return AcNo;
    }

    static int checkCredentials(int accountToCheck, int pinToCheck) throws SQLException {
        Connex connex = new Connex();
        Connection connection = connex.connects();
        int pinDB = 0;

        String sql = "SELECT userPIN from useraccount WHERE accountId = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, accountToCheck);
        ResultSet result = stmt.executeQuery();
        if (result.next())
            pinDB = result.getInt(1);

        if (pinDB == pinToCheck){
            return accountToCheck;
        }else {
            return 0;
        }
    }
}
