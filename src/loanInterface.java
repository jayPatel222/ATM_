import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
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
        setSize(600, 500);
        issueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int acc = Integer.parseInt(textField1.getText());
                    int amount = Integer.parseInt(textField2.getText());
                    int months = Integer.parseInt(textField3.getText());
                    if (acc == 0 || amount == 0 || months == 0){
                        JOptionPane.showMessageDialog(loanPanel, "All fields are Mandotary !!");
                    }
                    else {
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
                    }
                } catch (SQLException throwables) {
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
