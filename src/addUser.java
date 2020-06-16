import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class addUser extends JFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JPanel rootPanel;
    private JButton addUserButton;
    private JTextField textField5;
    private JTextField textField6;

    public addUser(){
        add(rootPanel);
        setTitle("Add User");
        setSize(400,500);
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(rootPanel,"User Added");
                String firstName = textField1.getText();
                String lastName = textField2.getText();
                String sin = textField3.getText();
                int score = Integer.parseInt(textField4.getText());
                int pin = Integer.parseInt(textField5.getText());
                int accBalance = Integer.parseInt(textField6.getText());
                new UserAccount(firstName, lastName, sin, score, pin, accBalance,1,1);
            }
        });
    }
}
