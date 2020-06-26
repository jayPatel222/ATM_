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
    private JButton listUsersButton;
    private JButton manageLoansButton;

    public addUser() throws SQLException {

        Connex connex = new Connex();
        Connection connection = connex.connects();
        add(rootPanel);
        setTitle("Add User");
        setSize(600,500);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = textField1.getText();
                String lastName = textField2.getText();
                String sin = textField3.getText();
                int score = Integer.parseInt(textField4.getText());
                int pin = Integer.parseInt(textField5.getText());
                float accBalance = Float.parseFloat(textField6.getText());
                if (firstName.equals("") || lastName.equals("") || sin.equals("") || score == 0 || pin == 0 ||
                accBalance == 0.0) {
                    JOptionPane.showMessageDialog(rootPanel, "All fields are Mandotary !!");
                }
                else {

                    int item = Integer.parseInt(comboBox1.getSelectedItem().toString().substring(0,1));
                    int ch1 = Integer.parseInt(String.valueOf(item));

                    int item1 = Integer.parseInt(comboBox2.getSelectedItem().toString().substring(0,1));
                    int ch2 = Integer.parseInt(String.valueOf(item1));
                    System.out.println(item + " " + item1);
                    new UserAccount(firstName, lastName, sin, score, pin, accBalance, ch1, ch2);
                }

        }
    });
        String sql = "SELECT * from mydb.typeac;";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet result = stmt.executeQuery();
        while(result.next()) {
            String str = result.getInt("typeId") + "   " + result.getString("typeName") ;
            comboBox1.addItem(str);
        }
        String sql2 = "SELECT * from mydb.bank;";
        PreparedStatement stmt2 = connection.prepareStatement(sql2);
        ResultSet result2 = stmt2.executeQuery();
        while(result2.next()) {
            String str2 = result2.getInt("bankId") + "   " + result2.getString("bankName") ;
            comboBox2.addItem(str2);
        }
        listUsersButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (InstantiationException instantiationException) {
                    instantiationException.printStackTrace();
                } catch (IllegalAccessException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                } catch (UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
                    unsupportedLookAndFeelException.printStackTrace();
                }
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        listUsers user = null;
                        try {
                            user = new listUsers();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                        user.setVisible(true);
                    }
                });
            }
        });
        manageLoansButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        loanInterface user = null;
                        user = new loanInterface();

                        user.setVisible(true);
                    }
                });
            }
        });
    }
}
