package za.ac.aop.ct2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author LANGARM
 */
public class CT2GUI extends JFrame{
    private final JTextArea taInput, taOutput;
    private final JButton btnSortUniqueWords;
    private final JButton btnReverseParagraph, btnMapWordOccurrence;
    
    public CT2GUI() {
        setTitle("Class Test 2 - AOP216D/AOR216D");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
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
        
        /*btnPrioritizeWords = new JButton("Prioritize Words by Length");
        btnPrioritizeWords.setBackground(Color.decode("#aaa495"));*/
        
        btnReverseParagraph = new JButton("Reverse paragraph structure");
        btnReverseParagraph.setBackground(Color.decode("#fac621"));
        
        btnSortUniqueWords = new JButton("Determine and Sort Unique Words");
        btnSortUniqueWords.setBackground(Color.decode("#caf741"));
        
        btnMapWordOccurrence = new JButton("Determine Word-Occurrence");
        btnMapWordOccurrence.setBackground(Color.decode("#ca92f0"));
        
        pnlButtons.add(btnReverseParagraph); 
        pnlButtons.add(btnSortUniqueWords); 
        pnlButtons.add(btnMapWordOccurrence);
        
        add(pnlArea, BorderLayout.CENTER);
        add(pnlButtons, BorderLayout.SOUTH);
        
        //btnPrioritizeWords.addActionListener(new BtnPrioritizeWordsClickListener());
        btnReverseParagraph.addActionListener(new BtnWordReverseClickListener());
        btnSortUniqueWords.addActionListener(new BtnSortUniqueWordsClickListener());
        btnMapWordOccurrence.addActionListener(new BtnMapWordOccurrenceClickListener());
        
        setVisible(true);
    }
    
    //TOTAL [9]
    private class BtnWordReverseClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //WRITE YOUR CODE HERE
           taOutput.setText("");
            
            Stack<String> st = new Stack<>();
           
            String[] words = taInput.getText().split(" ");
          

            for(String word : words){
                
                st.push(word);
                
                
             
            }
            
            String reversed = "";
            while(!st.isEmpty()){
               
                reversed +=  st.pop()+ " ";
            
            }
            taOutput.setText(reversed);
            
            
        }
    }
    
    //TOTAL [12]
    private class BtnSortUniqueWordsClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //WRITE YOUR CODE HERE
            taOutput.setText("");
            
            Set<String> se = new TreeSet<>();
            String[] words = taInput.getText().split(" ");
            
            for(String word : words ){
             
                se.add(word);
             
            }
            String order = " ";
            int counter = 1;
            while(!se.isEmpty()){
               String element = se.iterator().next();
               se.remove(element);
              order += counter+". "+element+ "\n";
              counter++;
                
            }
              taOutput.setText(order);
        }
    }
    
    //TOTAL [14]
    private class BtnMapWordOccurrenceClickListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //WRITE YOUR CODE HERE
            taOutput.setText("");
            
            String[] words = taInput.getText().toLowerCase().split("\\s+");
            
            HashMap<String, Integer> mp = new HashMap<>();
            int count = 1;
            
            for(String word : words){
                String clean = word.trim().replaceAll("[^a-z0-9:\\-]", "");
                clean = clean.trim().replaceAll("^[^a-z]+|[^a-z]+$","");
                if(mp.containsKey(clean)){
                    mp.put(clean, count +1);
                }
                else{
                    mp.put(clean, count);
                }
            }
            String results = "";
            for(Map.Entry<String, Integer> entry : mp.entrySet()){
                results += entry.getKey()+" ==> "+entry.getValue()+"\n";
            }
            
            taOutput.setText(results);
        }
        
    }
    
    public static void main(String[] args) { SwingUtilities.invokeLater(CT2GUI::new); }
}
