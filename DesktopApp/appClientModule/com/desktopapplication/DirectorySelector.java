package com.desktopapplication;


import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.util.*;


public class DirectorySelector extends JPanel
   implements ActionListener {
   JButton go;
   
   static JFileChooser chooser;
   String choosertitle;
   
   
  public  DirectorySelector() {
    go = new JButton("Select Path");
    go.addActionListener(this);
    add(go);
   }

  public void actionPerformed(ActionEvent e) {
    int result;
    
        
    chooser = new JFileChooser(); 
    chooser.setCurrentDirectory(new java.io.File("."));
    chooser.setDialogTitle(choosertitle);
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    //
    // disable the "All files" option.
    //
    chooser.setAcceptAllFileFilterUsed(false);
    //    
    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
      System.out.println("getCurrentDirectory(): " 
         +  chooser.getCurrentDirectory());
      System.out.println("getSelectedFile() : " 
         +  chooser.getSelectedFile());
      Tabbedpane.textField.setText(chooser.getSelectedFile().getAbsolutePath());
      Tabbedpane.textField2.setText(chooser.getSelectedFile().getAbsolutePath());
      
      }
    else {
      System.out.println("No Selection ");
      }
     }
   
 
    
  
}