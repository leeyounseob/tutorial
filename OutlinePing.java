import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.net.InetAddress;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;

public class OutlinePing extends JFrame {
	
	public OutlinePing() {
		//메뉴 시작 부분//
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);
		
		JMenu scanMenu = new JMenu("Scan");
		JMenu gotoMenu = new JMenu("Go to");
		JMenu commandsMenu = new JMenu("Commands");
		JMenu favoritesMenu = new JMenu("Favorites");
		JMenu toolsMenu = new JMenu("Tools");
		JMenu helpMenu = new JMenu("Help");
		
		menubar.add(scanMenu);
		menubar.add(gotoMenu);
		menubar.add(commandsMenu);
		menubar.add(toolsMenu);
		menubar.add(helpMenu);
		
		JMenuItem loadFromFileAction = new JMenuItem("Load from file...");
		JMenuItem exportAllAction = new JMenuItem("Export All...");
		JMenuItem expoartSelectionAction = new JMenuItem("Export selection...");
		JMenuItem quitAction = new JMenuItem("Quit");
		
		scanMenu.add(loadFromFileAction);
		scanMenu.add(exportAllAction);
		scanMenu.add(expoartSelectionAction);
		scanMenu.addSeparator();
		scanMenu.add(quitAction);
		
		JMenuItem nextAilveHostAction = new JMenuItem("Next alive host");
		JMenuItem nextOpenPortAction = new JMenuItem("Next open port");
		JMenuItem nextDeadHostAction = new JMenuItem("Next dead host");
		JMenuItem previousAilveHostAction = new JMenuItem("Previous alive host");
		JMenuItem previousOpenPortAction = new JMenuItem("Previous open port");
		JMenuItem previousDeadHostAction = new JMenuItem("Previous dead host");
		JMenuItem findAction = new JMenuItem("Find...");
		
		gotoMenu.add(nextAilveHostAction);
		gotoMenu.add(nextOpenPortAction);
		gotoMenu.add(nextDeadHostAction);
		gotoMenu.add(previousAilveHostAction);
		gotoMenu.add(previousOpenPortAction );
		gotoMenu.add(previousDeadHostAction);
		gotoMenu.add(findAction);
		
		JMenuItem showDetailsAction = new JMenuItem("Show details");
		JMenuItem rescanIPsAction = new JMenuItem("Rescan IP(s)");
		JMenuItem deleteIPsAction = new JMenuItem("Delete IP(s)");
		JMenuItem copyIPSAction = new JMenuItem("Copy IP");
		JMenuItem copyDetailsAction = new JMenuItem("Copy details");
		JMenuItem openAction = new JMenuItem("Open");
		
		commandsMenu.add(showDetailsAction);
		commandsMenu.add(rescanIPsAction);
		commandsMenu.add(deleteIPsAction);
		commandsMenu.add(copyIPSAction);
		commandsMenu.add(copyDetailsAction);
		commandsMenu.add(openAction);
		
		JMenuItem addCurrentAction = new JMenuItem("Add current");
		JMenuItem manageFavoriteAction = new JMenuItem("Manage Favorites...");
		
		favoritesMenu.add(addCurrentAction);
		favoritesMenu.add(manageFavoriteAction);
		
		JMenuItem prefrencesAction = new JMenuItem("Preferences...");
		JMenuItem fetchersAction = new JMenuItem("Fetchers...");
		JMenuItem selctionAction = new JMenuItem("Selction...");
		JMenuItem scanStatisticsAction = new JMenuItem("Scan statistics...");
		
		toolsMenu.add(prefrencesAction);
		toolsMenu.add(fetchersAction);
		toolsMenu.add(selctionAction);
		toolsMenu.add(scanStatisticsAction);
		
		JMenuItem gettingStartedAction = new JMenuItem("Getting Started");
		JMenuItem officialWedsiteAction = new JMenuItem("Official Website");
		JMenuItem faqAction = new JMenuItem("FAQ");
		JMenuItem reportanIssueAction = new JMenuItem("Report an issue");
		JMenuItem pluginsAction = new JMenuItem("Plugins");
		JMenuItem commandLineAction = new JMenuItem("Command-line usage");
		JMenuItem chekVersionAction = new JMenuItem("Chek for newer version...");
		JMenuItem aboutAction = new JMenuItem("About");
		
		helpMenu.add(gettingStartedAction);
		helpMenu.add(officialWedsiteAction);
		helpMenu.add(faqAction);
		helpMenu.add(reportanIssueAction);
		helpMenu.add(pluginsAction);
		helpMenu.add(commandLineAction);
		helpMenu.add(chekVersionAction);
		helpMenu.add(aboutAction);
		
		
		//메뉴 끝
		
		
		//상태바 코드 시작 부분
		
		JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		add(statusPanel,BorderLayout.SOUTH);
		JLabel readyLabel = new JLabel("Ready");
		readyLabel.setPreferredSize(new Dimension(200,16));
		readyLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
		JLabel displayLabel = new JLabel("Display:All");
		displayLabel.setPreferredSize(new Dimension(200,16));
		displayLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
		JLabel threadLabel = new JLabel("Thread:0");
		threadLabel.setPreferredSize(new Dimension(200,16));
		threadLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
		statusPanel.add(readyLabel);
		statusPanel.add(displayLabel);
		statusPanel.add(threadLabel);
		
		//상태바 끝 
		
		
		//테이블 시작
		
		String[] titles = new String[] {
			"IP","Ping","Hostname","TTL","Port"	
		};
		
		Object[][] stats= initTable();
		JTable jTable = new JTable(stats,titles);

		for(int i = 1; i <= 255; i++) {
			final int I = i;
			
			Thread thread = new Thread() {
				@Override
				public void run() {
					try {
						String ip = "192.168.2." + I;
						long finish = 0;
						long start = new GregorianCalendar().getTimeInMillis();
				        InetAddress address = InetAddress.getByName(ip);
						boolean reachable = address.isReachable(200);
						finish = new GregorianCalendar().getTimeInMillis();
				    	
						if(reachable) {
							jTable.setValueAt(ip, I-1, 0);
							jTable.setValueAt((finish-start+"ms"), I-1, 1);
							jTable.setValueAt(address.getHostName(), I-1, 2);
							jTable.setValueAt("[N/S]", I-1, 4);
						}
						else {
							jTable.setValueAt(ip, I-1, 0);
							jTable.setValueAt("[N/A]", I-1, 1);
							jTable.setValueAt("[N/A]", I-1, 2);
							jTable.setValueAt("[N/S]", I-1, 4);
						}
							
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			};
			
			thread.start();
		}
		
		JScrollPane sp = new JScrollPane(jTable);
		add(sp,BorderLayout.CENTER);
		
		//테이블 끝
		
		
		//toolbar begin
		
		Font myFont = new Font("Serif",Font.BOLD,16);
		JToolBar toolbar1 = new JToolBar();
		toolbar1.setLayout(new FlowLayout(FlowLayout.LEFT));
		JToolBar toolbar2 = new JToolBar();
		toolbar2.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel rangeStartLabel=new JLabel("IP Range: ");
		JTextField rangeStartTextField = new JTextField(10);
		JLabel rangeEndLabel=new JLabel("to");
		JTextField rangeEndTextField = new JTextField(10);
		JComboBox cb = new JComboBox();
		cb.addItem("IP Range");
		cb.addItem("Random");
		cb.addItem("Text File");
		
		rangeStartLabel.setFont(myFont);
		rangeStartLabel.setPreferredSize(new Dimension(80,30));
		rangeEndLabel.setFont(myFont);
		rangeEndLabel.setPreferredSize(new Dimension(15,30));
		
		toolbar1.add(rangeStartLabel);
		toolbar1.add(rangeStartTextField);
		toolbar1.add(rangeEndLabel);
		toolbar1.add(rangeEndTextField);
		toolbar1.add(cb);
		
		JLabel hostNameLabel= new JLabel("Hostname");
		JTextField hostNameTextField = new JTextField(10);
		JButton upButton = new JButton("IP");
		JComboBox optionComboBox = new JComboBox();
		optionComboBox.addItem("/24");
		optionComboBox.addItem("/26");
		JButton startButton = new JButton("Start");
		
		hostNameLabel.setFont(myFont);
		hostNameTextField.setPreferredSize(new Dimension(90,30));
		upButton.setPreferredSize(new Dimension(50,30));
		optionComboBox.setPreferredSize(new Dimension(90,30));
		startButton.setPreferredSize(new Dimension(50,30));
		
		toolbar2.add(hostNameLabel);
		toolbar2.add(hostNameTextField);
		toolbar2.add(upButton);
		toolbar2.add(optionComboBox);
		toolbar2.add(startButton);
		
		JPanel pane = new JPanel(new BorderLayout());
		pane.add(toolbar1,BorderLayout.NORTH);
		pane.add(toolbar2,BorderLayout.SOUTH);
		
		add(pane,BorderLayout.NORTH);
		
		//toolbar end
		
		setSize(700,700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
			
		}
	public Object[][] initTable(){
		Object[][] result = new Object[255][5];
		return result;
	}
	public static void main(String[] args) {
		OutlinePing op = new OutlinePing();

	}

}
