import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class listUsers extends JFrame {

    JTable table;
    private JScrollPane sp;
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

    public listUsers() throws SQLException {

        Connex connex = new Connex();
        Connection connection = connex.connects();

        setTitle("Users list");
        setSize(600, 500);


        String sql = "SELECT * from mydb.useraccount;";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet result = stmt.executeQuery();
        int rowCount =0;
        String sql1 = "SELECT * from mydb.useraccount;";
        PreparedStatement stmt1 = connection.prepareStatement(sql1);
        ResultSet result1 = stmt1.executeQuery();
        while(result1.next()){
            rowCount++;
        }
        String[][] resultSet = new String[rowCount][8];
        int row = 0;
        while (result.next()) {
            for (int i = 0; i < 1; i++) {
                resultSet[row][i] = result.getObject("userFirstName").toString() +"   "+ result.getObject("userLastName").toString();
                resultSet[row][i+1] = result.getObject("accountBalance").toString();
                resultSet[row][i+2] = result.getObject("accountId").toString();
                resultSet[row][i+3] = result.getObject("accountTypeId").toString();
                resultSet[row][i+4] = result.getObject("accountBankId").toString();
                resultSet[row][i+5] = result.getObject("userScore").toString();
                resultSet[row][i+6] = result.getObject("userSSN").toString();
            }
            row++;
        }


        // Column Names
        String[] columnNames = { "Full Name", "Balance","Account No.","Account Type","Bank ID","Score","SIN"};

        // Initializing the JTable
         table = new JTable(resultSet,columnNames);
         table.setBounds(30, 40, 200, 300);
         sp = new JScrollPane(table);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);

         add(sp);
    }
}
