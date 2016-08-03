package com.desktopapplication;

import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;


public class CheckTable {
	static File userDir = new File(System.getProperty("user.dir"));
	
	 static FilenameFilter filter = new MyFileFilter();
    static File featurefilepath = new File(userDir+"/slingshot/specifications/maximus");
	 static File[] files = featurefilepath.listFiles(filter);
	 public List<String> nameList;
	 public static DefaultListModel dlm = new DefaultListModel();
	 public static JList list = new JList(dlm);
	 static String featurefileList;
	 static String featurefilename;
	 static List<Boolean> rowList;
	 static JCheckBox jcb;
	 
		
		 static int Listsize = files.length;
    public static final CheckModel model = new CheckModel(Listsize);
     
   
    public static final JTable table = new JTable(model) {

        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return new Dimension(50, 300);
        }

        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
             jcb = (JCheckBox) super.prepareRenderer(renderer, row, column);
            jcb.setHorizontalTextPosition(JCheckBox.LEADING);
            jcb.setText(files[row].getName());
           
            return jcb;
        }
    };

    public static void main(String[] args){
    	
        EventQueue.invokeLater(new Runnable() {

        	@Override
            public void run() {
            	JDialog f2 = new JDialog();
            	JPanel ButtonPanel =new JPanel();
              JButton Add = new JButton("Add to Slingshot");

              Add.addActionListener(new ActionListener() {
        			public void actionPerformed(ActionEvent e) {
        				featurefilename = dlm.toString();
        				featurefileList=featurefilename.replace(".feature","").replace("[","").replace("]","");
        				 append(new File(userDir+"/slingshot/slingshot.properties"),featurefileList);
        			}

					
					private void append(File aFile, String featurefileList) {
						try {
						      PrintStream p = new PrintStream(new BufferedOutputStream(new FileOutputStream(aFile, true)));
						      Properties prop = new Properties();
						  	InputStream input = null;

						  	try {

						  		input = new FileInputStream(System.getProperty("user.dir")+"/slingshot/slingshot.properties");

						  		// load a properties file
						  		prop.load(input);
                                System.out.println("input file"+input);
						  		// get the property value and print it out
						  		System.out.println("this is "+prop.getProperty("specifications.to.execute"));
						  		prop.setProperty("specifications.to.execute",featurefileList);
						  		prop.save(new FileOutputStream(new File(System.getProperty("user.dir")+"/slingshot/slingshot.properties")), "");

						  	} catch (IOException ex) {
						  		ex.printStackTrace();
						  	} finally {
						  		if (input != null) {
						  			try {
						  				input.close();
						  			} catch (IOException e) {
						  				e.printStackTrace();
						  			}
						  		}
						  	}
//						      p.println(featurefileList);
//						      p.close();

						    } catch (Exception e) {
						      e.printStackTrace();
						      System.err.println(aFile);
						    }
						
					}
        		});
              ButtonPanel.add(Add);
              JButton clear = new JButton("Clear");

              clear.addActionListener(new ActionListener() {
        			public void actionPerformed(ActionEvent e) {
        				
        				featurefilename = dlm.toString();
        				featurefileList=featurefilename.replace(".feature","").replace("[","").replace("]","");
        				
        			   removeLineFromFile(new File(userDir+"/slingshot/slingshot.properties"),featurefileList);
        			   dlm.removeAllElements();
        			   System.out.println(jcb);
        			  
        				 
        				
        			}
        				
        			  public void removeLineFromFile(File aFile, String featurefileList) {
        				  
        				  try {
						      PrintStream p = new PrintStream(new BufferedOutputStream(new FileOutputStream(aFile, true)));
						      Properties prop = new Properties();
						  	InputStream input = null;

						  	try {

						  		input = new FileInputStream(System.getProperty("user.dir")+"/slingshot/slingshot.properties");

						  		// load a properties file
						  		prop.load(input);
                                System.out.println("input file"+input);
						  		// get the property value and print it out
						  		System.out.println("this is "+prop.getProperty("specifications.to.execute"));
						  		prop.setProperty("specifications.to.execute","");
						  		prop.save(new FileOutputStream(new File(System.getProperty("user.dir")+"/slingshot/slingshot.properties")), "");

						  	} catch (IOException ex) {
						  		ex.printStackTrace();
						  	} finally {
						  		if (input != null) {
						  			try {
						  				input.close();
						  			} catch (IOException e) {
						  				e.printStackTrace();
						  			}
						  		}
						  	}
//						      p.println(featurefileList);
//						      p.close();

						    } catch (Exception e) {
						      e.printStackTrace();
						      System.err.println(aFile);
						    }
        				    
        				  }
        				 

        				
        		});
              ButtonPanel.add(clear);
        		
 // f2.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
            	
            	
                f2.setLayout(new GridLayout(1, 0));
                f2.add(new JScrollPane(table));
                f2.add(new DisplayPanel(model));
                f2.add(ButtonPanel);
                f2.pack();
                f2.setLocationRelativeTo(null);
                f2.setVisible(true);
            }
        });
    }

    private static class DisplayPanel extends JPanel {

        
        


        public DisplayPanel(final CheckModel model) {
            super(new GridLayout());
            File aFile=null;
            this.setBorder(BorderFactory.createTitledBorder("Selected Feature files"));
            try {
			      
			      Properties prop = new Properties();
			  	InputStream input = null;

			  	try {

			  		input = new FileInputStream(System.getProperty("user.dir")+"/slingshot/slingshot.properties");

			  		// load a properties file
			  		prop.load(input);
                  System.out.println("input file"+input);
			  		// get the property value and print it out
			  		System.out.println("this is "+prop.getProperty("specifications.to.execute"));
			  		
			  		dlm.addElement(prop.getProperty("specifications.to.execute"));

			  	} catch (IOException ex) {
			  		ex.printStackTrace();
			  	} finally {
			  		if (input != null) {
			  			try {
			  				input.close();
			  			} catch (IOException e) {
			  				e.printStackTrace();
			  			}
			  		}
			  	}
//			      p.println(featurefileList);
//			      p.close();

			    } catch (Exception e) {
			      e.printStackTrace();
			      System.err.println(aFile);
			    }
            this.add(new JScrollPane(list));
            model.addTableModelListener(new TableModelListener() {
            	
                @Override
                public void tableChanged(TableModelEvent e) {
                    dlm.removeAllElements();
                    for (Integer col : model.checked) {
                        dlm.addElement(files[col].getName());
                       
                        
                    }
                    
                }
            });
        }
    }
   

    public static class CheckModel extends AbstractTableModel {

        public final int rows;
        public Set<Integer> checked = new TreeSet<Integer>();
        

        public CheckModel(int rows) {
            this.rows = rows;
            rowList = new ArrayList<Boolean>(rows);
           
            for (int i = 0; i < rows; i++) {
                rowList.add(Boolean.FALSE);
              
                
            }
           
    		
        }

        @Override
        public int getRowCount() {
            return rows;
        }

        @Override
        public int getColumnCount() {
            return 1;
        }

        @Override
        public String getColumnName(int col) {
            return "Column " + col;
        }

        @Override
        public Object getValueAt(int row, int col) {
        	
                return rowList.get(row);
                
        }

        @Override
        public void setValueAt(Object aValue, int row, int col) {
            boolean b = (Boolean) aValue;
            rowList.set(row, b);
            if (b) {
            	
                checked.add(row);
                
               
                
                
                
            } else {
                checked.remove(row);
                
                
            }
            fireTableRowsUpdated(row, row);
        }

        @Override
        public Class<?> getColumnClass(int col) {
            return getValueAt(0, col).getClass();
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return true;
        }
        
    }
}


