package za.ac.aop.prac.exam;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import za.ac.aop.api.CallInfo;
import za.ac.aop.api.CallInfoProcessor;

public class PracticeExamGUI extends JFrame {

    private JTabbedPane tabs;
    private JPanel tab1,tab2,tab3;
    
    //TAB 1 shandis
    private JButton btnGroupCalls;
    private JButton btnCountBusinessLines;
    private JTextArea taCallRecords;
    private CallInfoProcessor cip;

    //TAB 2 shandis
    private JTextArea taInput, taOutput;
    private JButton btnPrioritizeWords;
    
    //TAB 3 shandis
    private JButton btnViewRecords;
    private JButton btnCountReplacement;
    private JButton btnAverageCost;
    private JTable tblEquipment;
    private DefaultTableModel eqModel;

    private Connection connection;

    public PracticeExamGUI() {
        super("2026 S2 Practice GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 650);
        setLocationRelativeTo(null);
        
        Question1Tab(); //initializing tab1 in here
        
        Question2Tab(); //initializing tab2 in here

        Question3Tab(); //initializing tab3 in here
        
        // Add tabs
        tabs = new JTabbedPane();
        tabs.addChangeListener((e)->{
            if (tabs.getSelectedIndex() == 0){
                SwingUtilities.invokeLater(()->{ loadfile(); });
            }
            else if (tabs.getSelectedIndex() == 2){
                SwingUtilities.invokeLater(()->{ setSize(850, 650); });
            }
        });
        tabs.addTab("Q1 - Outcome 2", tab1);
        tabs.addTab("Q2 - Outcome 3", tab2);
        tabs.addTab("Q3 - Outcome 4", tab3);

        add(tabs);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (connection != null) try { connection.close(); } catch(SQLException x){}
            }
        });
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void Question1Tab(){
        tab1 = new JPanel(new BorderLayout(10, 10));
        JPanel fileControls = new JPanel();
     
        btnGroupCalls = new JButton("Group Calls By Date");
        btnCountBusinessLines = new JButton("Count Business Calls");
           
        fileControls.add(btnGroupCalls);
        fileControls.add(btnCountBusinessLines);
        
        taCallRecords = new JTextArea(14, 55);
        taCallRecords.setFont(new Font("Arial", Font.BOLD, 14));
        taCallRecords.setEditable(false);
        taCallRecords.setTabSize(4);
        tab1.add(fileControls, BorderLayout.NORTH);
        tab1.add(new JScrollPane(taCallRecords), BorderLayout.CENTER);
        
        cip = new CallInfoProcessor();
        
        btnGroupCalls.addActionListener(new BtnGroupCallsClickListener());
        btnCountBusinessLines.addActionListener(new BtnCountBusinessCalls());
    }
    
    private void Question2Tab() {
        tab2 = new JPanel(new BorderLayout());
        
        String sampleText = "The 2:00 AM video audio queue is absolute chaos. Our ongoing online area assessment has a unique "
                + "dilemma as an immediate visual error ruins our entire academic presentation. "
                + "Our naive cohort is surviving solely on absolute panic, audio feeds, and ideas "
                + "as we evaluate the issue. If we actually survive this quiet structural routine, "
                + "every single one of us deserves an ideal route, an easy A grade "
                + "and a 12-hour nap!";


        JPanel pnlArea = new JPanel(new GridLayout(1, 2));
        pnlArea.setBorder(new TitledBorder(new LineBorder(Color.yellow, 3), "Text Areas"));

        taInput = new JTextArea(6, 25);
        taInput.setFont(new Font("Arial", Font.PLAIN, 20));
        taInput.setLineWrap(true);
        taInput.setWrapStyleWord(true);
        taInput.setText(sampleText);
        taInput.setEditable(false);
        JScrollPane inputScroll = new JScrollPane(taInput);
        
        taOutput = new JTextArea(6, 25);
        taOutput.setLineWrap(true);
        taOutput.setFont(new Font("Monospaced", Font.BOLD, 16));
        taOutput.setWrapStyleWord(true);
        JScrollPane outputScroll = new JScrollPane(taOutput);      
        
        pnlArea.add(inputScroll);
        pnlArea.add(outputScroll);
        
        JPanel pnlButtons = new JPanel();
        pnlButtons.setBorder(new TitledBorder(new LineBorder(Color.green, 3), "Functionality"));
        
        btnPrioritizeWords = new JButton("Prioritize Words by Length");
        btnPrioritizeWords.setBackground(Color.decode("#aaa495"));
        btnPrioritizeWords.addActionListener(new BtnPrioritizeWordsClickListener());
        pnlButtons.add(btnPrioritizeWords);
        
        tab2.add(pnlArea, BorderLayout.CENTER);
        tab2.add(pnlButtons, BorderLayout.SOUTH);
    }
    
    private void Question3Tab(){
        tab3 = new JPanel(new BorderLayout(10, 10));
        JPanel dbControls = new JPanel();
        
        btnViewRecords = new JButton("Connect and Display");
        btnCountReplacement = new JButton("Count Need Replacing");
        btnAverageCost = new JButton("Calculate Average Cost");
        
        eqModel = new DefaultTableModel(
                new Object[]{"ID", "Name", "Department", "Date", "Condition", "Is Used", "Price"}, 0);
        tblEquipment = new JTable(eqModel){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblEquipment.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(5);
        tblEquipment.getTableHeader().getColumnModel().getColumn(3).setPreferredWidth(20);
        tblEquipment.getTableHeader().getColumnModel().getColumn(4).setPreferredWidth(20);
        tblEquipment.getTableHeader().getColumnModel().getColumn(5).setPreferredWidth(20);
        tblEquipment.getTableHeader().getColumnModel().getColumn(6).setPreferredWidth(20);
        tblEquipment.getTableHeader().getColumnModel().getColumn(2).setPreferredWidth(120);
        
        dbControls.add(btnViewRecords);
        dbControls.add(btnCountReplacement);
        dbControls.add(btnAverageCost);
        tab3.add(dbControls, BorderLayout.NORTH);
        tab3.add(new JScrollPane(tblEquipment), BorderLayout.CENTER);
        
        btnViewRecords.addActionListener(new BtnViewEquipmentsListener());
        btnCountReplacement.addActionListener(new BtnCountReplacementListener());
        btnAverageCost.addActionListener(new BtnAverageCostListener());
        
        btnCountReplacement.setEnabled(false);
        btnAverageCost.setEnabled(false);
    }

    /* ---------- PROVIDED METHODS ------------- */
    private void loadfile() {
        taCallRecords.setText("");
        try(Scanner s = new Scanner(new File("November.dat"))){
            while (s.hasNextLine()){
                String line = s.nextLine();
                String[] fields = line.split(",");
                
                CallInfo cInfo = new CallInfo(
                        LocalDate.parse(fields[0], DateTimeFormatter.ofPattern("dd/MM/yyyy")), 
                        LocalTime.parse(fields[1]), 
                        LocalTime.parse(fields[2]),
                        Long.parseLong(fields[3]),
                        fields[4], fields[5]);
                        
                cip.addCallInfo(cInfo);
                
                taCallRecords.append(cInfo + "\n");
            }
            taCallRecords.setText(taCallRecords.getText().trim());
        }
        catch (IOException x){
            JOptionPane.showMessageDialog(rootPane, x.getMessage());
        }
    }

    private void viewDBrecords(){
        try{
            if (connection == null || connection.isClosed())
                JOptionPane.showMessageDialog(null, "Not connected to database");

            try (Statement stmt = connection.createStatement()) {
                String qry = "select * from lab_equipment order by equipment_id";
                try (ResultSet data = stmt.executeQuery(qry)) {
                    while (data.next()){
                        eqModel.addRow(new Object[]{
                            data.getInt(1),
                            data.getString(2),
                            data.getString(3),
                            data.getDate(4),
                            data.getString(5),
                            data.getBoolean(6),
                            data.getDouble(7)
                        });
                    }
                }
            }
        }
        catch(SQLException sqle){
            JOptionPane.showMessageDialog(rootPane, sqle.getMessage());
        }
    }
    
     /* ---------- END OF PROVIDED METHODS ------------- */
    
    //TOTAL [12]
    private class BtnGroupCallsClickListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //WRITE CODE HERE
           taCallRecords.setText("");
            TreeSet<String> st = new TreeSet<>();
            for (CallInfo date : cip.getCalls()) {
                
                st.add(date.getCallDate().toString());
                
                
            }
            
            
            
            for (Iterator<String> iterator = st.iterator(); iterator.hasNext();) {
                String next = iterator.next();
                //iterator.remove();
                //String dateOf = next;
                String totalCalls = "";
                for (CallInfo date : cip.getCalls()) {
                
                  if(date.getCallDate().toString().equalsIgnoreCase(next)){
                  
                     totalCalls += date.getStartTime() +"\t " +date.getEndTime() +"\t " +date.getExtension() +"\t" +date.getPhoneNumber() +"\t " +date.getNetworkProvider() +"\n";
                  }
                
                
                }
                taCallRecords.append("Calls made on "+next + "\n ==============================================\n"+totalCalls);
            }
            taCallRecords.setText(taCallRecords.getText().trim());
        }
    }
    
    private class BtnCountBusinessCalls implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //WRITE CODE HERE
            int count = 0;
            
            for (CallInfo date : cip.getCalls()) {
                
                if(date.getPhoneNumber().substring(0, 2).equals("01") ||
                   date.getPhoneNumber().substring(0, 2).equals("02")
                   ||date.getPhoneNumber().substring(0, 2).equals("03")
                   || date.getPhoneNumber().substring(0, 3).equals("086")){
                    count++; 
                    
                }
                
            }
            JOptionPane.showMessageDialog(rootPane,count+ " calls were made to bussiness services");
        }
        
    }
    //TOTAL [15]
    private class BtnPrioritizeWordsClickListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //WRITE CODE HERE
            taOutput.setText("");
            String[] words = taInput.getText().toLowerCase().trim().split("\\s+");
            
            TreeSet<String> ts = new TreeSet<>();
            HashSet<String> hs = new HashSet<>();
            
            for (String word : words) {
                String clean = word.trim().replaceAll("[^a-zA-Z0-9:\\-]", "");
                if(word.length() >= 6){ ts.add(clean);}
                if(word.length() < 6){ hs.add(clean);}
                
            }
            String big = "";
            for (Iterator<String> iterator = ts.iterator(); iterator.hasNext();) {
                String next = iterator.next();
                big += next +"\n";
            }
            String small = "";
            for (Iterator<String> iterator = hs.iterator(); iterator.hasNext();) {
                String next = iterator.next();
                small += next +"\n";
            }
            taOutput.setText("======================\nHigher Priority Words\n===================\n"+big
                    +"\n======================\nNormal Priority Words\n===================\n"+small);
        }
    }

    //TOTAL [7]
    private class BtnViewEquipmentsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            
                //WRITE CODE HERE
                
               
            try { 
                 String url = "jdbc:derby://localhost:1527/baba";
                String user = "app";
                String passwd = "app";
              // String sql = "select * from lab_equipment order by equipment_id";
                connection = DriverManager.getConnection(url, user, passwd);
                if(connection.isClosed() || connection==null){return;}
                
               /* Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(sql);
                
                while(rs.next()){
                      eqModel.addRow(new Object[]{
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getDate(4),
                            rs.getString(5),
                            rs.getBoolean(6),
                            rs.getDouble(7)
                        });*/
                viewDBrecords();
                btnAverageCost.setEnabled(true);
                btnCountReplacement.setEnabled(true);
                } catch (Exception ex) {
                Logger.getLogger(PracticeExamGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
             
            
            
        }
    }
    
    // TOTAL [10]
    private class BtnCountReplacementListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //WRITE CODE HERE
            int count = 0;
            int years = 0;
            LocalDate currentDate = LocalDate.now();
            
            for (int i = 0; i < eqModel.getRowCount(); i++) {
                LocalDate diff = LocalDate.parse(eqModel.getValueAt(i, 3).toString());
                years = currentDate.getYear() - diff.getYear(); 
                
                if(years >= 5){count++;}
            }
            JOptionPane.showMessageDialog(rootPane, years+" equioment have reached  years and need to be replaced");
        }
    }
    
    // TOTAL [6]
    private class BtnAverageCostListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //WRITE CODE HERE
            String sql = "select avg(price) from lab_equipment";
            
            Statement st;
            try {
                st = connection.createStatement();
                ResultSet rs = st.executeQuery(sql);
                double results = 0.0;
                while(rs.next()){
                     results = rs.getDouble(1);
                }
                JOptionPane.showMessageDialog(rootPane,"The average cost of all equipment is : R"+results);
                st.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(PracticeExamGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PracticeExamGUI::new);
    }
    
    
}
