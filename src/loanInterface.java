import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loanInterface extends JFrame{
    private JPanel loanPanel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton issueButton;
    private JPanel loanPanel;
    private JButton onGoingLoansButton;
    Connex connex = new Connex();

    public loanInterface() {
        Connection connection = connex.connects();
        add(loanPanel);
        setTitle("Start Loan");
        setIconImage(new ImageIcon(getClass().getResource("/images/atmTwo.png")).getImage());
        setSize(600, 500);
        issueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!textField1.getText().equals("") && !textField2.getText().equals("") && !textField3.getText().equals("") &&
                            !textField1.getText().equals("0") && !textField2.getText().equals("0") && !textField3.getText().equals("0")) {

                        int acc = Integer.parseInt(textField1.getText());
                        int amount = Integer.parseInt(textField2.getText());
                        int months = Integer.parseInt(textField3.getText());
                        if (acc == 0 || amount == 0 || months == 0) {
                            JOptionPane.showMessageDialog(loanPanel, "All fields are Mandotary !!");
                        } else {
                            String sql = "SELECT * from useraccount WHERE accountId = ?";
                            PreparedStatement stmt = connection.prepareStatement(sql);
                            stmt.setInt(1, acc);
                            ResultSet result = stmt.executeQuery();
                            if (result.next()){
                                new Loan(acc, amount, months);
                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        listLoans user = null;
                                        try {
                                            user = new listLoans();
                                        } catch (SQLException throwables) {
                                            throwables.printStackTrace();
                                        }

                                        user.setVisible(true);
                                    }
                                });
                              dispose();
                            }
                            else {
                                JOptionPane.showMessageDialog(loanPanel, "Account does not exists.");
                            }
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(loanPanel, "All fields are Mandotary !!");
                    }
                    } catch(SQLException throwables){
                        throwables.printStackTrace();
                    }

            }
        });
        onGoingLoansButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        listLoans user = null;
                        try {
                            user = new listLoans();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                        user.setVisible(true);
                    }
                });
            }
        });
    }
}
