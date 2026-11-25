package za.ac.tut.o4.lab1;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;

/**
 * Main GUI class for the Equipment Basic Operations Lab application. Recreates
 * the user interface shown in the screenshot using Java Swing components and
 * layouts.
 */
public class EquipmentOPGUI extends JFrame {

    // Components requiring access outside the constructor for listeners
    private JButton btnConnect;
    private JLabel lblConnectionStatusValue;
    private JButton btnViewRecords;
    private JButton btnAddEquipment;
    private JButton btnFindEquipment;
    private JButton btnSetAllInUseToFalse;
    private JButton btnExit;
    private DefaultTableModel tableModelEquipment;

    public EquipmentOPGUI() {
        super("Equipment Basic Operations Lab");

        // Set up the main content pane with BorderLayout
        JPanel pnlContent = new JPanel();
        pnlContent.setLayout(new BorderLayout());
        setContentPane(pnlContent);

        // --- Header Panel (NORTH) ---
        JPanel pnlHeader = new JPanel();
        pnlHeader.setLayout(new BorderLayout());
        pnlHeader.setBackground(new Color(47, 60, 71)); // Dark grey background

        JLabel lblHeaderTitle = new JLabel("Equipment Basic Operations Lab", JLabel.CENTER);
        lblHeaderTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblHeaderTitle.setForeground(Color.WHITE);
        lblHeaderTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Spacing inside header
        pnlHeader.add(lblHeaderTitle, BorderLayout.CENTER);

        pnlContent.add(pnlHeader, BorderLayout.NORTH);

        // --- Main Body (CENTER) - for nested panels ---
        JPanel pnlMainBody = new JPanel();
        pnlMainBody.setLayout(new BorderLayout());

        // --- Database Connection Section ---
        JPanel pnlDatabaseConnection = new JPanel();
        pnlDatabaseConnection.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnlDatabaseConnection.setBorder(new TitledBorder("Database Connection"));

        btnConnect = new JButton("Connect");
        pnlDatabaseConnection.add(btnConnect);

        JLabel lblConnectionStatusPrefix = new JLabel("Status:");
        pnlDatabaseConnection.add(lblConnectionStatusPrefix);

        lblConnectionStatusValue = new JLabel("Not connected");
        lblConnectionStatusValue.setForeground(Color.RED);
        lblConnectionStatusValue.setFont(new Font("Arial", Font.BOLD, 12));
        pnlDatabaseConnection.add(lblConnectionStatusValue);

        pnlMainBody.add(pnlDatabaseConnection, BorderLayout.NORTH);

        // --- Operations and Records Container ---
        JPanel pnlOperationsAndRecords = new JPanel();
        pnlOperationsAndRecords.setLayout(new BorderLayout());

        // --- Database Operations Section ---
        JPanel pnlDatabaseOperations = new JPanel();
        pnlDatabaseOperations.setLayout(new GridLayout(1, 4, 10, 0)); // Even spacing between 4 buttons
        pnlDatabaseOperations.setBorder(new TitledBorder("Database Operations"));

        btnViewRecords = new JButton("View Records");
        btnViewRecords.setEnabled(false); // Initially disabled
        pnlDatabaseOperations.add(btnViewRecords);

        btnAddEquipment = new JButton("Add Equipment");
        btnAddEquipment.setEnabled(false); // Initially disabled
        pnlDatabaseOperations.add(btnAddEquipment);

        btnFindEquipment = new JButton("Find Equipment by ID");
        btnFindEquipment.setEnabled(false); // Initially disabled
        pnlDatabaseOperations.add(btnFindEquipment);

        btnSetAllInUseToFalse = new JButton("Set All In-Use to False");
        btnSetAllInUseToFalse.setEnabled(false); // Initially disabled
        pnlDatabaseOperations.add(btnSetAllInUseToFalse);

        pnlOperationsAndRecords.add(pnlDatabaseOperations, BorderLayout.NORTH);

        // --- Equipment Records Section with JTable ---
        JPanel pnlEquipmentRecords = new JPanel();
        pnlEquipmentRecords.setLayout(new BorderLayout());
        pnlEquipmentRecords.setBorder(new TitledBorder("Equipment Records"));

        // Define column headers for the JTable
        String[] columnNames = {"ID", "Name", "Department", "Purchase Date", "Condition", "In Use", "Price"};

        // Create table model and make table non-editable
        tableModelEquipment = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells non-editable
            }
        };

        JTable tblEquipment = new JTable(tableModelEquipment);

        // Style the table header to match the screenshot (dark background, white text)
        JTableHeader tableHeader = tblEquipment.getTableHeader();
        tableHeader.setBackground(new Color(12, 66, 65)); // Dark teal/green background
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setFont(new Font("Arial", Font.BOLD, 12));

        // Place table in a scroll pane
        JScrollPane scpTable = new JScrollPane(tblEquipment);
        pnlEquipmentRecords.add(scpTable, BorderLayout.CENTER);

        pnlOperationsAndRecords.add(pnlEquipmentRecords, BorderLayout.CENTER);

        pnlMainBody.add(pnlOperationsAndRecords, BorderLayout.CENTER);

        pnlContent.add(pnlMainBody, BorderLayout.CENTER);

        // --- Footer Area (SOUTH) - status bar and exit ---
        JPanel pnlFooter = new JPanel();
        pnlFooter.setLayout(new BorderLayout());
        pnlFooter.setBorder(new TitledBorder("Operation Status"));

        JPanel pnlStatusBar = new JPanel();
        pnlStatusBar.setLayout(new BorderLayout());

        JLabel lblStatusMessage = new JLabel("Ready");
        lblStatusMessage.setFont(new Font("Arial", Font.BOLD, 12));
        pnlStatusBar.add(lblStatusMessage, BorderLayout.WEST);

        btnExit = new JButton("Exit");
        pnlStatusBar.add(btnExit, BorderLayout.EAST);

        pnlFooter.add(pnlStatusBar, BorderLayout.CENTER);

        pnlContent.add(pnlFooter, BorderLayout.SOUTH);

        // --- Register Listeners with components ---
        btnConnect.addActionListener(new BtnConnectListener());
        btnViewRecords.addActionListener(new BtnViewRecordsListener());
        btnAddEquipment.addActionListener(new BtnAddEquipmentListener());
        btnFindEquipment.addActionListener(new BtnFindEquipmentByIDListener());
        btnSetAllInUseToFalse.addActionListener(new BtnSetAllInUseToFalseListener());
        btnExit.addActionListener(new BtnExitListener());

        // --- Frame final setup ---
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Set reasonable default size
        // Alternatively use pack(); setLocationRelativeTo(null); to let Java size the components
        setLocationRelativeTo(null); // Center frame on screen
    }

    // --- Private Named ActionListener Inner Classes ---
    private Connection conn;

    private class BtnConnectListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            // TODO: Implement database connection logic and update status/button state
            String url = "jdbc:derby://localhost:1527/O4Lab1DB";
            String user = "app";
            String pwd = user;
            try {
                conn = DriverManager.getConnection(url, user, pwd);
                lblConnectionStatusValue.setText("Connected");
                btnConnect.setEnabled(false);
                btnAddEquipment.setEnabled(true);
                btnViewRecords.setEnabled(true);
                btnFindEquipment.setEnabled(true);
                btnSetAllInUseToFalse.setEnabled(true);
            } catch (SQLException x) {
                JOptionPane.showMessageDialog(rootPane, x.getMessage());
            }
        }
    }

    private class BtnViewRecordsListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            // TODO: Implement viewing all equipment records in the table
            String sql = "SELECT * FROM lab_equipment ORDER BY equipment_id";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet r = ps.executeQuery();

                tableModelEquipment.setRowCount(0);
                while (r.next()) {
                    String eName = r.getString("Name");
                    double price = r.getDouble(7);
                    Date dt = r.getDate(4);
                    String dept = r.getString(3);
                    String condition = r.getString(5);
                    boolean inUse = r.getBoolean(6);
                    int eid = r.getInt("Equipment_ID");

                    Object[] row = {eid, eName, dept, dt, condition, inUse, price};

                    tableModelEquipment.addRow(row);
                }

            } catch (SQLException x) {
                JOptionPane.showMessageDialog(rootPane, x.getMessage());
            }
        }
    }

    private class BtnAddEquipmentListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            // TODO: Implement adding a new equipment record
            String name = JOptionPane.showInputDialog("Enter name of equipment");
            String dept = JOptionPane.showInputDialog("Enter department");
            String cond = JOptionPane.showInputDialog("Enter condition");

            Date purchase = Date.valueOf(LocalDate.now());
            boolean inUse = false;

            String dml = "INSERT INTO LAB_EQUIPMENT "
                    + "(NAME, DEPARTMENT, PURCHASE_DATE, CONDITION, IN_USE, PRICE) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            try {
                double price = Double.parseDouble(JOptionPane.showInputDialog("Enter Price"));
                if (!conn.isClosed()) {
                    PreparedStatement ps = conn.prepareStatement(dml);
                    ps.setString(1, name);
                    ps.setString(2, dept);
                    ps.setDate(3, purchase);
                    ps.setString(4, cond);
                    ps.setBoolean(5, inUse);
                    ps.setDouble(6, price);

                    int rowAffect = ps.executeUpdate();
                    if (rowAffect == 1) {
                        JOptionPane.showMessageDialog(null,
                                "Thank you chief, " + rowAffect + " row inserted");

                        btnViewRecords.doClick();
                    }
                }
            } catch (NumberFormatException | SQLException x) {
            }
        }
    }

    private class BtnFindEquipmentByIDListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            // TODO: Implement finding and displaying a single equipment record by ID
            tableModelEquipment.setRowCount(0); //clear table

            String sql = "SELECT * FROM lab_equipment WHERE equipment_id = ?";

            int idFromUser = Integer.parseInt(
                    JOptionPane.showInputDialog(null, "Enter equipment ID"));

            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, idFromUser);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String eName = rs.getString("Name");
                    double price = rs.getDouble(7);
                    Date dt = rs.getDate(4);
                    String dept = rs.getString(3);
                    String condition = rs.getString(5);
                    boolean inUse = rs.getBoolean(6);
                    int eid = rs.getInt("Equipment_ID");

                    Object[] row = {eid, eName, dept, dt, condition, inUse, price};

                    tableModelEquipment.addRow(row);
                }

            } catch (SQLException x) {
            }

        }
    }

    private class BtnSetAllInUseToFalseListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            // TODO: Implement updating all equipment records to set in-use status to false
            String dml = "UPDATE lab_equipment "
                    + "SET in_use = ?, "
                    + "condition = ? "
                    + "WHERE department = ?";

            try {
                PreparedStatement ps = conn.prepareStatement(dml);
                ps.setBoolean(1, false);
                ps.setString(2, "Damaged");
                ps.setString(3, "Computer Systems Engineering");

                int bChoice = JOptionPane.showConfirmDialog(null, 
                        "Are you sure you want to update CSE?",
                        "Confirm CSE update",
                        JOptionPane.YES_NO_OPTION);
                if (bChoice == 0) { //pressed Yes
                    int affect = ps.executeUpdate();
                    if (affect >= 1) {
                        JOptionPane.showMessageDialog(null, affect + " row(s) updated");
                        btnViewRecords.doClick();
                    } else {
                        JOptionPane.showMessageDialog(null, "Could not update");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "User pressed No");
                }

            } catch (SQLException x) {
                JOptionPane.showMessageDialog(null, x.getMessage());
            }
        }
    }

    private class BtnExitListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            // TODO: Implement application exit functionality
            /*String sql = "SELECT * FROM LAB_EQUIPMENT";
            
            try{
                ResultSet rs = conn.prepareStatement(sql).executeQuery();
                double highPrice = 0.0;
                String highEq = "";
                while (rs.next()){
                    double price = rs.getDouble("PRICE");
                    if (price > highPrice){
                        highPrice = price;
                        highEq = rs.getString("NAME");
                    }
                    
                    
                }
                JOptionPane.showMessageDialog(null, "The highest equipment is " + highEq
                            + "costing R: " + highPrice);
            }
            catch(SQLException x){}*/
            
            //To actually exit
            try { conn.close(); } catch(SQLException x){}
            
            dispose();
        }
    }

    // --- Main Method to Run the GUI ---
    public static void main(String[] args) {
        // Run GUI code in the Event Dispatch Thread (EDT) without lambda expressions
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Using default cross-platform Java Look and Feel to match common lab environments
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                new EquipmentOPGUI().setVisible(true);
            }
        });
    }
}
