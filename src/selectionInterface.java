import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class selectionInterface extends JFrame{
    private JButton withdrawDepositButton;
    private JButton balanceTransferButton;
    private JButton cancelButton;
    private JPanel selectionInterface;

    public selectionInterface() {
        Connex connex = new Connex();
        Connection connection = connex.connects();
        add(selectionInterface);
        setTitle("Select Transaction type");
        setSize(600,500);
        balanceTransferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        accountTransfer user = null;
                        try {
                            user = new accountTransfer();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                        user.setVisible(true);
                    }
                });
                dispose();
            }
        });
        withdrawDepositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        balanceInterface user = null;
                        try {
                            user = new balanceInterface();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException classNotFoundException) {
                            classNotFoundException.printStackTrace();
                        }

                        user.setVisible(true);
                    }
                });
                dispose();
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
    }


}
