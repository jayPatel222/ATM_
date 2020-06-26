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
                    new Loan(acc,amount,months);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
}
