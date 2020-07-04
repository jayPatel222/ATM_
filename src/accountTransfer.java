import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class accountTransfer extends JFrame{
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton confirmButton;
    private JPanel accountTransfer;
    private JTextField textField4;
    private JTextField textField5;
    private JButton cancelButton;
    float accountInfo;

    public accountTransfer() throws SQLException {
        Connex connex = new Connex();
        Connection connection = connex.connects();
        add(accountTransfer);
        setTitle("Add User");
        setSize(600, 500);

        int accounToFetch = atmInterface.getNo();
        String sql = "SELECT * from useraccount WHERE accountId = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, accounToFetch);
        ResultSet result = stmt.executeQuery();
        if (result.next()){
            accountInfo = (float) result.getObject("accountBalance");
            textField1.setText(result.getObject("accountId").toString());

            textField4.setText(result.getObject("accountBalance").toString());
            textField5.setText(result.getObject("userFirstName")
                    +" "+ result.getObject("userLastName"));
            textField1.setEditable(false);
            textField4.setEditable(false);
            textField5.setEditable(false);

        }


        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField2.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        if (textField2.getText().length() >= 6 ) // limit to 4 characters
                            e.consume();
                    }
                });
                int recAccountno = Integer.parseInt(textField2.getText());
                float money = Float.parseFloat(textField3.getText());
                if (money > accountInfo ){
                    JOptionPane.showMessageDialog(accountTransfer,"Not Enough Funds");
                }else{
                    String sql1 = "SELECT * from useraccount WHERE accountId = ?";
                    PreparedStatement stmt1 = null;
                    try {
                        stmt1 = connection.prepareStatement(sql1);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    try {
                        stmt1.setInt(1, recAccountno);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    ResultSet result1 = null;
                    try {
                        result1 = stmt1.executeQuery();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    try {
                        if (!result1.next()){
                            JOptionPane.showMessageDialog(accountTransfer,"Account Does not Exists");
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    String refId = getSaltString();
                    try {
                        new Transaction(refId,money, atmInterface.getNo(), 3,recAccountno);
                        JOptionPane.showMessageDialog(accountTransfer,"Reference No.:- "+refId+"\n"+" Transaction amount:- "+money +
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
        textField3.addKeyListener(new KeyAdapter() {
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
