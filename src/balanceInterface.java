import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class balanceInterface  extends JFrame {
    private JPanel panel1;
    private JTextField textField1;
    private JCheckBox withdrawCheckBox;
    private JCheckBox depositCheckBox;
    private JTextField textField2;
    private JButton confirmButton;
    private JPanel balanceInterface;
    private JPanel panel2;
    float accountBalance;
   static String refId;

    public balanceInterface() throws SQLException {
        int accounToFetch = atmInterface.getNo();
        Connex connex = new Connex();
        Connection connection = connex.connects();
        add(panel1);
        setTitle("Process Transaction");
        setSize(600, 500);
        String sql = "SELECT accountBalance from useraccount WHERE accountId = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, accounToFetch);
        ResultSet result = stmt.executeQuery();
        if (result.next()){
            accountBalance = (float) result.getObject("accountBalance");
            textField1.setText(result.getObject("accountBalance").toString());
            textField1.setEditable(false);
        }


        confirmButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int transType = 0;
                if (withdrawCheckBox.isSelected()){
                    transType = 1;
                }
                else if (depositCheckBox.isSelected()){
                    transType = 2;
                }
                float money = Float.parseFloat(textField2.getText());
                if (money > accountBalance && transType == 1){
                    JOptionPane.showMessageDialog(panel1,"Not Enough Funds");
                }
                else {
                    try {
                         refId = getSaltString();
                        new Transaction(refId,money, atmInterface.getNo(), transType);

                        JOptionPane.showMessageDialog(panel1,"Reference No.:- "+refId+"\n"+" Transaction amount:- "+money +
                                "\nAccount No:- " +atmInterface.getNo());
                        dispose();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }
            }
        });

    }
    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

}
