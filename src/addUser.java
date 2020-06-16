import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class addUser extends JFrame  {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JPanel rootPanel;
    private JButton addUserButton;
    private JTextField textField5;
    private JTextField textField6;
    private JButton addButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;

    public addUser() throws SQLException {

        Connex connex = new Connex();
        Connection connection = connex.connects();
        add(rootPanel);
        setTitle("Add User");
        setSize(600,500);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(rootPanel,"User Added");
                String firstName = textField1.getText();
                String lastName = textField2.getText();
                String sin = textField3.getText();
                int score = Integer.parseInt(textField4.getText());
                int pin = Integer.parseInt(textField5.getText());
                float accBalance = Float.parseFloat(textField6.getText());
                String selectedItem = comboBox1.getSelectedItem().toString();
                int ch1 = Integer.parseInt(selectedItem);
                String selectedItem2 = comboBox2.getSelectedItem().toString();
                int ch2 = Integer.parseInt(selectedItem2);
                System.out.println(ch1 + " "+ ch2);
                new UserAccount(firstName, lastName, sin, score, pin, accBalance,ch1,ch2);

        }
    });
        String sql = "SELECT * from mydb.typeac;";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet result = stmt.executeQuery();
        while(result.next())
        {
            comboBox1.addItem(result.getInt("typeId") );
        }
        String sql2 = "SELECT * from mydb.bank;";
        PreparedStatement stmt2 = connection.prepareStatement(sql2);
        ResultSet result2 = stmt2.executeQuery();
        while(result2.next())
        {
            comboBox2.addItem(result2.getInt("bankId") );
        }
    }
}
