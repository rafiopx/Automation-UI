package com.desktopapplication;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class Tabbedpane extends JPanel {
	static JFrame f = new JFrame("Automation");
	static String current;

	static JTextField textField;
	static JTextField textField2;
	static JTextArea textArea;
	static JTextField featureFileName;
	static DirectorySelector Directory = new DirectorySelector();
	static DirectorySelector Directory2 = new DirectorySelector();
	
	// Size of editing text area.
	public static final int NUM_ROWS = 30;
	public static final int NUM_COLS = 50;

	// Buttons to save and load files.
	public static JButton saveButton;
	public static JButton loadButton;

	// Area where the user does the editing
	public static JTextArea text;

	public Tabbedpane() {
		super(new GridLayout(1, 1));

		JTabbedPane tabbedPane = new JTabbedPane();
		ImageIcon icon = createImageIcon("images/icon.png");

		JPanel panel1 = new JPanel();

		panel1.add(Directory);

		textField = new JTextField(20);
		Directory.add(textField);
		JPanel buttonPanel1 = new JPanel();
		JButton run = new JButton("Gradle Run");

		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("path:" + textField.getText());

					Runtime.getRuntime().exec(
							"cmd /c start " + textField.getText()
									+ "/slingshot/run.bat");
				} catch (IOException exc) {
					exc.printStackTrace();
				}
			}
		});

		buttonPanel1.add(run);

		JButton create = new JButton("Gradle JAR");

		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File source = new File(
						textField.getText()
								+ "\\slingshot-core\\build\\libs\\slingshot-core-1.0.jar");
				File dest = new File(textField.getText()
						+ "\\slingshot\\libs\\slingshot-core-1.0.jar");
				source.delete();
				dest.delete();
				System.out.println("File got deleted");

				try {

					Runtime.getRuntime().exec(
							"cmd /c start " + textField.getText()
									+ "/slingshot-core/jar.bat");

					System.out.println("Execution Stated...");
					   
						watchDirectory();
						try{
							Files.copy(source.toPath(), dest.toPath());
							System.out.println("File got copied");
							} catch (IOException excep) {
								System.out.println("File does not get created");
								excep.printStackTrace();
								((AbstractButton) e.getSource()).setEnabled(true);
					            
							}
						
					
					// Runtime.getRuntime().exec(
					// "cmd /c start " + textField.getText() +
					// "/slingshot-core/copy.bat");

				} catch (Exception exc) {
					exc.printStackTrace();
					((AbstractButton) e.getSource()).setEnabled(true);
		            
				}
			}

			public void watchDirectory() {
				
				
				// define a folder root
				Path myDir = Paths.get(textField.getText()
						+ "\\slingshot-core\\build\\libs");

				try {
					WatchService watcher = myDir.getFileSystem()
							.newWatchService();
					myDir.register(watcher,
							StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);

					WatchKey watckKey = watcher.take();

					List<WatchEvent<?>> events = watckKey.pollEvents();
					for (WatchEvent event : events) {
						if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
							// check for Jar files only.
							// Copy the jar file to different location

							System.out.println("Created: "
									+ event.context().toString());
							
							 
						}
					}

				} catch (Exception e) {
					System.out.println("Error: " + e.toString());
				}
			}
		});
		buttonPanel1.add(create);
		panel1.add(buttonPanel1);

		// //Text Editor
		//
		// JPanel buttonPanel = new JPanel();
		// saveButton = new JButton("Save File");
		// loadButton = new JButton("Load File");
		// buttonPanel.add(saveButton);
		// buttonPanel.add(loadButton);
		// panel1.add(buttonPanel , BorderLayout.WEST);
		//
		//
		// text = new JTextArea(NUM_ROWS, NUM_COLS);
		// text.setFont(new Font("System", Font.PLAIN, 24));
		// JScrollPane textScroller = new JScrollPane(text);
		//
		// saveButton.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		//
		//
		// File file;
		//
		// // create and display dialog box to get file name
		// JFileChooser dialog = new JFileChooser();
		//
		// // Make sure the user didn't cancel the file chooser
		// if (dialog.showSaveDialog(text) == JFileChooser.APPROVE_OPTION) {
		//
		// // Get the file the user selected
		// file = dialog.getSelectedFile();
		//
		// try {
		// // Now write to the file
		// PrintWriter output = new PrintWriter(new FileWriter(file));
		// output.println(text.getText());
		// output.close();
		// } catch (IOException ex) {
		// JOptionPane.showMessageDialog(text, "Can't save file "
		// + ex.getMessage());
		// }
		// }
		//
		//
		//
		// }
		// });
		// panel1.add(textScroller);
		// loadButton.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		//
		//
		// String line;
		// File file;
		//
		// // create and display dialog box to get file name
		// JFileChooser dialog = new JFileChooser();
		//
		// // Make sure the user did not cancel.
		// if (dialog.showOpenDialog(text) == JFileChooser.APPROVE_OPTION) {
		// // Find out which file the user selected.
		// file = dialog.getSelectedFile();
		//
		// try {
		// // Open the file.
		// BufferedReader input = new BufferedReader(new FileReader(file));
		//
		// // Clear the editing area
		// text.setText("");
		//
		// // Fill up the ediitng area with the contents of the file being
		// // read.
		// line = input.readLine();
		// while (line != null) {
		// text.append(line + "\n");
		// line = input.readLine();
		// }
		//
		// // Close the file
		// input.close();
		// } catch (IOException ec) {
		// JOptionPane.showMessageDialog(text, "Can't load file "
		// + ec.getMessage());
		// }
		// }
		//
		//
		//
		// }
		// });
		// panel1.add(buttonPanel);

		JButton open = new JButton("Open slingshot.properties");

		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					current = new java.io.File(".").getCanonicalPath();
					System.out.println("current path :" + current);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				String fileName = current + "\\slingshot\\slingshot.properties";
				String[] commands = { "cmd", "/c", "start", "\"notepad++\"",
						fileName };
				try {
					Runtime.getRuntime().exec(commands);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		panel1.add(open);
		
		JButton edit = new JButton("Edit xpath.properties");
		
		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					current = new java.io.File(".").getCanonicalPath();
					System.out.println("current path :" + current);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				String fileName = current + "/slingshot/xpath.properties";
				String[] commands = { "cmd", "/c", "start", "\"notepad++\"",
						fileName };
				try {
					Runtime.getRuntime().exec(commands);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		panel1.add(edit);
		JButton report = new JButton("Automation Report");

		report.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					current = new java.io.File(".").getCanonicalPath();
					System.out.println("current path :" + current);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				String fileName = current
						+ "//slingshot//reports//slingshot//Automation_Result//feature-overview.html";
				String[] commands = { "cmd", "/c", "start", "\"report\"",
						fileName };
				try {
					Runtime.getRuntime().exec(commands);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		panel1.add(report);

		tabbedPane.addTab("Execution", icon, panel1, "Execution");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		JPanel panel2 = new JPanel();
		panel2.add(Directory2);

		textField2 = new JTextField(20);
		Directory2.add(textField2);

		JLabel label1 = new JLabel("Feature File Name:");
		panel2.add(label1, BorderLayout.AFTER_LINE_ENDS);
		featureFileName = new JTextField(20);
		panel2.add(featureFileName);
		JLabel label2 = new JLabel("Feature File Steps:");
		textArea = new JTextArea(20, 20);
		panel2.add(label2);
		panel2.add(textArea);

		final JButton save = new JButton("Save Feature File");

		textArea.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent event) {
				String content = textArea.getText();
				if (!content.equals("")) {
					save.setEnabled(true);
				} else {
					save.setEnabled(false);
				}
			}
		});
		panel2.add(save);

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.out.println("Feature File" + textArea.getText());

				FileOutputStream fop = null;
				File file;
				String content = textArea.getText();

				try {

					file = new File(textField2.getText() + "/"
							+ featureFileName.getText() + ".feature");
					fop = new FileOutputStream(file);

					// if file doesnt exists, then create it
					if (!file.exists()) {
						file.createNewFile();
					}

					// get the content in bytes
					byte[] contentInBytes = content.getBytes();

					fop.write(contentInBytes);
					fop.flush();
					fop.close();
					JOptionPane.showMessageDialog(null, "Feature File Saved");
					System.out.println("Done");

				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (fop != null) {
							fop.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		});
		tabbedPane.addTab("Add Feature File", icon, panel2,
				"Add Feature File to Slingshot");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		JPanel panel3 = new JPanel();

		JScrollPane thePane = new JScrollPane();

		final JButton select = new JButton("List of Feature");

		panel3.add(select);

		select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CheckTable.main(new String[0]);

			}
		});

		tabbedPane.addTab("Add Feature file to be Executed", icon, panel3,
				"Add Feature file to be Executed IN Slingshot.properties file");
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

		JComponent panel4 = makeTextPanel("Panel #4 (has a preferred size of 410 x 50).");
		panel4.setPreferredSize(new Dimension(410, 50));
		tabbedPane.addTab("Tab 4", icon, panel4, "Does nothing at all");
		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

		// Add the tabbed pane to this panel.
		add(tabbedPane);

		// The following line enables to use scrolling tabs.
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	protected JComponent makeTextPanel(String text) {
		JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
		filler.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new GridLayout(1, 1));
		panel.add(filler);
		return panel;
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = Tabbedpane.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 */
	public static void createAndShowGUI() {
		// Create and set up the window.

		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add content to the window.
		f.add(new Tabbedpane(), BorderLayout.CENTER);

		// Display the window.

		f.setVisible(true);
		f.setSize(600, 600);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
