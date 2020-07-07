import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
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
    private JButton cancelButton;
    float accountBalance;
   static String refId;

    public balanceInterface() throws SQLException, ClassNotFoundException {
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
                        new Transaction(refId,money, atmInterface.getNo(), transType,atmInterface.getNo());

                        JOptionPane.showMessageDialog(panel1,"Reference No.:- "+refId+"\n"+" Transaction amount:- "+money +
                                "\nAccount No:- " +atmInterface.getNo());
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
                        dispose();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }
            }
        });


        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                dispose();
            }

        });
        textField2.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep();
                    e.consume();
                }
            }
        });
    }

    protected String getSaltString() throws SQLException {
        Connex connex = new Connex();
        Connection connection = connex.connects();
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        String sql = "SELECT transactionCode from atmproject.transaction";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet result = stmt.executeQuery();
        while (result.next()){
           if (saltStr.equals(result.getObject("transactionCode"))){
               getSaltString();
            }
        }
        return saltStr;
    }


}


