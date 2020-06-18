import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class listUsers extends JFrame {
    private JPanel listPanel;
    private JList list1;

    public listUsers() throws SQLException {

        Connex connex = new Connex();
        Connection connection = connex.connects();
        add(listPanel);
        setTitle("Add User");
        setSize(600, 500);

        DefaultListModel listModel = new DefaultListModel();
        String sql = "SELECT * from mydb.useraccount;";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet result = stmt.executeQuery();
        while(result.next()){
            listModel.addElement(result.getObject("userFirstName") +"  "+result.getObject("userLastName")
                + "  " +    result.getObject("accountId"));// I think you want get this field
        }

        list1.setModel(listModel);
    }
}
