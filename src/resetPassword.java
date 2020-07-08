import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class resetPassword extends JFrame{
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton cancelButton;
    private JButton changePinButton;
    private JTextField textField4;
    private JPanel resetInterface;

    public resetPassword() {
        Connex connex = new Connex();
        Connection connection = connex.connects();
        add(resetInterface);
        setTitle("Change PIN");
        setSize(600,500);
        changePinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                try {
                    if (!textField4.getText().equals("") && !textField1.getText().equals("") &&
                            !textField3.getText().equals("") && !textField2.getText().equals("") &&
                            !textField4.getText().equals("0") && !textField1.getText().equals("0") &&
                            !textField3.getText().equals("0") && !textField2.getText().equals("0")) {
                        int accToChange = 0 + Integer.parseInt(textField4.getText());
                        int SIN = 0 + Integer.parseInt(textField1.getText());
                        int newPin = 0 + Integer.parseInt(textField2.getText());
                        int confirmPin =0 + Integer.parseInt(textField3.getText());
                        int length  = String.valueOf(newPin).length();
                        if (length == 4) {
                            if (newPin == confirmPin) {
                                if (checkSin(accToChange, SIN) == 0) {
                                    JOptionPane.showMessageDialog(resetInterface, "Check Account No and " +
                                            "sin number. it does not match our records");
                                } else {
                                    String sql = "UPDATE useraccount set userPIN = ? WHERE accountId = ?";
                                    PreparedStatement stmt = connection.prepareStatement(sql);
                                    stmt.setInt(1, newPin);
                                    stmt.setInt(2, accToChange);
                                    stmt.executeUpdate();
                                    JOptionPane.showMessageDialog(resetInterface, "PIN Updated");
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
                            } else {
                                JOptionPane.showMessageDialog(resetInterface, "Pin is not Matching Please retry");
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(resetInterface, "PIN should be 4 digit only");
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(resetInterface, "All fields are mandatory");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        textField1.addKeyListener(new KeyAdapter() {
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
        textField4.addKeyListener(new KeyAdapter() {
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
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                dispose();
            }
        });
    }

    static int checkSin(int accountToCheck, int sinToChh) throws SQLException {
        Connex connex = new Connex();
        Connection connection = connex.connects();
        int pinDB = 0;

        String sql = "SELECT userSSN from useraccount WHERE accountId = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, accountToCheck);
        ResultSet result = stmt.executeQuery();
        if (result.next())
            pinDB = result.getInt(1);

        if (pinDB == sinToChh){
            return accountToCheck;
        }else {
            return 0;
        }
    }
}
