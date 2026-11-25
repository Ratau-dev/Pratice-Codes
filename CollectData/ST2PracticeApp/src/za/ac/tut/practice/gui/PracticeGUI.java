package za.ac.tut.practice.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 * Target GUI skeleton for the Outcome 3 and Outcome 4 Practice Test.
 *
 * Students must complete only the named private ActionListener bodies.
 *
 * @author LANGARM
 */
public class PracticeGUI extends JFrame {

    /*
     * Question 1 components
     */
    private final JTextArea taSample;
    private final JTextArea taOutput;
    private final JButton btnDisplaySortedTokens;
    private final JButton btnMapVowelConsonantWords;
    private final JLabel lblQuestion1Status;

    /*
     * Question 2 components
     */
    private final JButton btnConnect;
    private final JButton btnViewEquipment;
    private final JButton btnResetCSE;
    private final JLabel lblStatus;
    private final JLabel lblOperationStatus;
    private final JTable tblEquipment;
    private final DefaultTableModel tableModel;

    /*
     * Shared database connection
     */
    private final String url = "jdbc:derby://localhost:1527/labDB";
    private Connection conn;

    /**
     * Constructs and displays the complete practice-test GUI.
     */
    public PracticeGUI() {

        setTitle("Practice Test for ST2");
        setSize(1080, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Color charcoal = new Color(38, 50, 56);
        Color outcomeGold = new Color(253, 184, 19);
        Color outcomeTeal = new Color(0, 121, 107);
        Color darkTeal = new Color(23, 75, 69);
        Color blue = new Color(49, 95, 125);
        Color purple = new Color(106, 91, 147);
        Color amber = new Color(176, 107, 0);

        /*
         * =====================================================
         * CREATE THE APPLICATION HEADING
         * =====================================================
         */
        JPanel headingPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headingPnl.setBackground(charcoal);

        JLabel lblHeading = new JLabel("Data Structures and JDBC Application Practice");
        lblHeading.setForeground(Color.WHITE);
        lblHeading.setFont(new Font("SansSerif", Font.BOLD, 22));

        headingPnl.add(lblHeading);

        /*
         * =====================================================
         * CREATE QUESTION 1 COMPONENTS
         * =====================================================
         */
        taSample = new JTextArea(16, 42);
        taSample.setLineWrap(true);
        taSample.setWrapStyleWord(true);
        taSample.setFont(new Font("SansSerif", Font.PLAIN, 23));

        taSample.setText(
                "The quality-control team reviewed a high-priority "
                + "shipment: sensor-ready tablets were tested twice; "
                + "however, two units failed (screen/display). The "
                + "re-test report marked unit#A17 as stable, unit@B42 "
                + "as unstable, and the pre-approved backup as "
                + "ready-to-use. Can quality-control release the "
                + "shipment?"
        );

        taOutput = new JTextArea(16, 36);
        taOutput.setEditable(false);
        taOutput.setLineWrap(true);
        taOutput.setWrapStyleWord(true);
        taOutput.setFont(new Font("Monospaced", Font.PLAIN, 14));

        btnDisplaySortedTokens = new JButton("Display Sorted Tokens");
        btnDisplaySortedTokens.setBackground(outcomeGold);
        btnDisplaySortedTokens.setForeground(Color.BLACK);
        
        btnMapVowelConsonantWords = new JButton("Map Vowel-Consonants Words");
        btnMapVowelConsonantWords.setBackground(blue);
        btnMapVowelConsonantWords.setForeground(Color.WHITE);        

        lblQuestion1Status = new JLabel("Select a paragraph-processing operation.");
        lblQuestion1Status.setFont(new Font("SansSerif", Font.BOLD, 13));

        JScrollPane sampleScrollPane = new JScrollPane(taSample);
        sampleScrollPane.setBorder(BorderFactory.createTitledBorder("Sample Paragraph"));

        JScrollPane outputScrollPane = new JScrollPane(taOutput);
        outputScrollPane.setBorder(BorderFactory.createTitledBorder("Output"));

        JPanel question1TextPnl = new JPanel(new GridLayout(1, 2, 8, 8));

        question1TextPnl.add(sampleScrollPane);
        question1TextPnl.add(outputScrollPane);

        JPanel question1ButtonPnl = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        question1ButtonPnl.setBorder(BorderFactory.createTitledBorder("Operations"));

        question1ButtonPnl.add(btnDisplaySortedTokens);
        question1ButtonPnl.add(btnMapVowelConsonantWords);

        JPanel question1SouthPnl = new JPanel(new BorderLayout(5, 5));

        question1SouthPnl.add(question1ButtonPnl, BorderLayout.CENTER);

        JPanel question1Pnl = new JPanel(new BorderLayout(8, 8));

        question1Pnl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        question1Pnl.add(question1TextPnl, BorderLayout.CENTER);
        question1Pnl.add(question1SouthPnl, BorderLayout.SOUTH);

        /*
         * =====================================================
         * CREATE QUESTION 2 COMPONENTS
         * =====================================================
         */
        btnConnect = new JButton("Connect");
        btnViewEquipment = new JButton("View Equipment");
        btnResetCSE = new JButton("Reset Comp Systems Engineering Condition");

        btnConnect.setBackground(outcomeTeal);
        btnConnect.setForeground(Color.WHITE);

        btnViewEquipment.setBackground(outcomeTeal);
        btnViewEquipment.setForeground(Color.WHITE);

        btnResetCSE.setBackground(amber);
        btnResetCSE.setForeground(Color.WHITE);

        btnViewEquipment.setEnabled(false);
        btnResetCSE.setEnabled(false);

        lblStatus = new JLabel("Not connected");
        lblStatus.setForeground(new Color(160, 40, 35));
        lblStatus.setFont(new Font("SansSerif", Font.BOLD, 13));

        lblOperationStatus = new JLabel("Connect to Derby before viewing records.");
        lblOperationStatus.setFont(new Font("SansSerif", Font.BOLD, 13));

        JPanel connectionPnl = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 7));
        connectionPnl.setBorder(BorderFactory.createTitledBorder("Database Connection"));

        connectionPnl.add(btnConnect);
        connectionPnl.add(new JLabel("Status:"));
        connectionPnl.add(lblStatus);

        JPanel databaseButtonPnl = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 7));
        databaseButtonPnl.setBorder(BorderFactory.createTitledBorder("Database Operations"));

        databaseButtonPnl.add(btnViewEquipment);
        databaseButtonPnl.add(btnResetCSE);

        JPanel databaseNorthPnl = new JPanel(new GridLayout(2, 1, 5, 5));

        databaseNorthPnl.add(connectionPnl);
        databaseNorthPnl.add(databaseButtonPnl);

        String[] columns = {"ID", "Name", "Department", "Purchase Date",
            "Condition", "In Use", "Price"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblEquipment = new JTable(tableModel);
        tblEquipment.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tblEquipment.setRowHeight(25);
        tblEquipment.setGridColor(new Color(170, 192, 186));
        tblEquipment.setSelectionBackground(new Color(185, 221, 213));
        tblEquipment.setSelectionForeground(Color.BLACK);
        tblEquipment.getTableHeader().setBackground(darkTeal);
        tblEquipment.getTableHeader().setForeground(Color.WHITE);
        tblEquipment.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));

        JScrollPane tableScrollPane = new JScrollPane(tblEquipment);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Equipment Records"));

        JPanel databaseStatusPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        databaseStatusPnl.setBorder(BorderFactory.createTitledBorder("Operation Status"));
        databaseStatusPnl.add(lblOperationStatus);

        JPanel question2Pnl = new JPanel(new BorderLayout(8, 8));
        question2Pnl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        question2Pnl.add(databaseNorthPnl, BorderLayout.NORTH);
        question2Pnl.add(tableScrollPane, BorderLayout.CENTER);

        question2Pnl.add(databaseStatusPnl,BorderLayout.SOUTH);
        /*
         * =====================================================
         * CREATE THE TABBED PANE
         * =====================================================
         */
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Q1: Data Structures",question1Pnl);

        tabbedPane.addTab("Q2: Database",question2Pnl);

        add(headingPnl, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);

        /*
         * =====================================================
         * REGISTER THE PRIVATE LISTENERS
         * =====================================================
         */
        btnDisplaySortedTokens.addActionListener(new BtnDisplaySortedTokensClickListener());
        btnMapVowelConsonantWords.addActionListener(new BtnMapVowelConsonantWordsClickListener());
        btnConnect.addActionListener(new BtnConnectClickListener());
        btnViewEquipment.addActionListener(new BtnViewEquipmentClickListener());
        btnResetCSE.addActionListener(new BtnResetCSEClickListener());

        setVisible(true);
    }

    /**
     * Question 1.1
     */
    private class BtnDisplaySortedTokensClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //WRITE YOUR CODE
            
            String[] words = taSample.getText().trim().toLowerCase().replaceAll("[^a-z\\-\\/#@0-9]"," ").split("\\s+");
            
            Set<String> st = new TreeSet<>();
            
            for (String word : words) {
                st.add(word);
            }
            String results = "";
            int count = 1;
            System.out.println(st);
            
             while(!st.isEmpty()){
               String element = st.iterator().next();
               st.remove(element);
             
              results += count+". "+element+ "\n";
              count++;
                
            }
             
             taOutput.setText(results);
        }
    }

    /**
     * Question 1.2
     */
    private class BtnMapVowelConsonantWordsClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //WRITE YOUR CODE HERE
            String[] words = taSample.getText().trim().toLowerCase().replaceAll("[^a-z\\-\\/#@]"," ").split("\\s+");
            
            LinkedHashMap<String, String> mp = new LinkedHashMap<>();
            
            for (String word : words) {
                int vowels = 0;
                int consonats = 0;
                String  rt = "true";
                char[] character = word.toCharArray();
                for (int i = 0; i < character.length; i++) {
                    if(character[i]=='a' || character[i]=='e' || character[i]=='i' || character[i]=='o'|| character[i]=='u'){
                        vowels++;
                    }
                    else{
                        consonats++;
                    }
                }
                if(vowels != consonats ){ 
                   rt = "false";
                }
                
                
                   mp.put(word, rt);
                
            }
            int count = 1;
            String results = "";
            for (Map.Entry<String, String> entry : mp.entrySet()) {
                 results += count+". "+entry.getKey()+" ==> "+entry.getValue()+" \n";
                count++;
                
            }
            taOutput.setText(results);
        }
    }

    /**
     * Question 2.1
     */
    private class BtnConnectClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //WRITE YOUR CODE HERE
            
            String username = "app";
            String password = "app";
            
            try {
                conn = DriverManager.getConnection(url,username,password);
                if(conn != null && !conn.isClosed()){
                    lblOperationStatus.setText("Connect to "+url);
                     btnConnect.setEnabled(false);
                     btnViewEquipment.setEnabled(true);
                     btnResetCSE.setEnabled(true);
                     
                }
            } catch (SQLException ex) {
                
            }
            
        }
    }

    /**
     * Question 2.2
     */
    private class BtnViewEquipmentClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //WRITE YOUR CODE HERE
            tableModel.setRowCount(0);
            
            String sql = "select * from APP.LAB_EQUIPMENT order by DEPARTMENT asc";
            
            try (Statement stat = conn.createStatement();
                ResultSet rs = stat.executeQuery(sql);){
                
                
                while(rs.next()){
                   
                int id = rs.getInt("EQUIPMENT_ID");
                String name = rs.getString("NAME");
                String dept =  rs.getString("DEPARTMENT");
                java.sql.Date date = rs.getDate("PURCHASE_DATE");
                 String con = rs.getString("CONDITION");
                 boolean use = rs.getBoolean("IN_USE");
                double price = rs.getDouble("PRICE"); 
                    
                Object[] row  ={id,name,dept,date,con,use,price};
                
                 tableModel.addRow(row);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PracticeGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
    }

    /**
     * Question 2.3
     */
    private class BtnResetCSEClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //WRITE YOUR CODE HERE
             int response = JOptionPane.showConfirmDialog(
        null,
        "Are you sure you want to reset all Computer Systems Engineering equipment?",
        "Confirm Update",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE
    );

    if (response == JOptionPane.YES_OPTION) {

        String sql = "UPDATE APP.LAB_EQUIPMENT "
                   + "SET CONDITION = ?, IN_USE = ? "
                   + "WHERE DEPARTMENT = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Parameters
            pstmt.setString(1, "Damaged");
            pstmt.setBoolean(2, false);
            pstmt.setString(3, "Computer Systems Engineering");

            // Execute update
            int recordsAffected = pstmt.executeUpdate();

            // Show number of affected records
            lblOperationStatus.setText(
                recordsAffected + " records affected"
            );

            // Disable reset button
            btnResetCSE.setEnabled(false);

            // Refresh JTable
            btnViewEquipment.doClick();

        } catch (SQLException ex) {
            Logger.getLogger(PracticeGUI.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }
            
        }
    }

    /**
     * Starts the application on the Swing event-dispatch thread.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                new Runnable() {
            @Override
            public void run() {
                new PracticeGUI();
            }
        }
        );
    }
}
