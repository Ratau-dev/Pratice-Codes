/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hospitality;

import java.awt.*;
import javax.swing.*;


/**
 *
 * @author cass
 */
public class LoginGUI extends JFrame{
    private JPanel mainPanel,panel,north , south, centre, centre1, centre2;
    private JTextField txtSurname, txtName, txtID;
    private JButton addSubmit, addClear;
    private JLabel lbSurname,lbName , lbID;     
    
    public LoginGUI() {
         this.setSize(300, 300);
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
         this.setMinimumSize(new Dimension(500,500));
         this.setMaximumSize(new Dimension(600,600));
         
          mainPanel = new JPanel(new BorderLayout());
          
          JLabel lbtxt = new JLabel("Click: ");
          JLabel lbstatus = new JLabel("Status: ");
          JButton lbtxt2 = new JButton("Refresh");
          lbtxt2.setBorderPainted(false);
          lbtxt2.setFocusPainted(false);
          JLabel lbstatus2 = new JLabel("Not connected ");
          
          //lbtxt.setHorizontalTextPosition(SwingConstants.LEFT);
         // lbstatus.setHorizontalTextPosition(SwingConstants.RIGHT);
         centre = new JPanel(new BorderLayout());
          centre1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
          centre2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
          centre1.add(lbtxt);
          centre2.add(lbstatus);
          centre1.add(lbtxt2);
          centre2.add(lbstatus2);
          centre.setBackground(Color.red);
          centre.add(centre1, BorderLayout.WEST);
          centre.add(centre2, BorderLayout.EAST);
          mainPanel.add(centre, BorderLayout.NORTH);
          
           north = new JPanel(new FlowLayout(FlowLayout.CENTER));
           JPanel northMain = new JPanel(new BorderLayout());
           panel = new JPanel(new GridLayout(3, 2, 0, 0));
          lbSurname = new JLabel("Surname:");
          txtSurname = new JTextField(20);
          north.setBackground(Color.green);
          panel.setBackground(Color.BLUE);
          panel.add(lbSurname);
          panel.add(txtSurname);
          
          
          lbName = new JLabel("Names:");
          txtName = new JTextField(20);
          panel.add(lbName);
          panel.add(txtName);
          
          lbID = new JLabel("ID No:");
          txtID = new JTextField(14);
          
          panel.add(lbID);
          panel.add(txtID);
          north.add(panel);
          
          JTextArea area1 = new JTextArea(40,40);
          JScrollPane scroll = new JScrollPane(area1);
          JPanel pScroll = new JPanel();
          pScroll.add(scroll);
          //north.add(pScroll);
          
          northMain.add(north, BorderLayout.NORTH);
          northMain.add(pScroll, BorderLayout.CENTER);
         mainPanel.add(northMain, BorderLayout.CENTER);
         
         //Buttons
         south = new JPanel(new FlowLayout(FlowLayout.CENTER));
         addSubmit = new JButton("Submit");
         addClear = new JButton("Clear");
         addSubmit.setBorderPainted(false);
         addSubmit.setFocusPainted(false);
         addClear.setFocusPainted(false);
         addClear.setBorderPainted(false);
         addClear.setBackground(Color.green);
         south.add(addSubmit);
         south.add(addClear);
         mainPanel.add(south, BorderLayout.SOUTH);
         this.add( mainPanel);
         this.pack();
         this.setVisible(true);
    }
    
    
    
    
}
