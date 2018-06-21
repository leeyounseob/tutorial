import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

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
		displayLabel.setPreferredSize(new Dimension(100,16));
		displayLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
		JLabel threadLabel = new JLabel("Thread:0");
		threadLabel.setPreferredSize(new Dimension(100,16));
		threadLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		JProgressBar loading = new JProgressBar();
		loading.setMinimum(0);
		loading.setMaximum(253);
		loading.setPreferredSize(new Dimension(200, 16));
		loading.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		statusPanel.add(readyLabel);
		statusPanel.add(displayLabel);
		statusPanel.add(threadLabel);
		statusPanel.add(loading);
		
		//상태바 끝 
		
		//툴바 코드 시작
		
				Font myFont = new Font("Serif",Font.BOLD,16);
				JToolBar toolbar1 = new JToolBar();
				toolbar1.setLayout(new FlowLayout(FlowLayout.LEFT));
				JToolBar toolbar2 = new JToolBar();
				toolbar2.setLayout(new FlowLayout(FlowLayout.LEFT));
				
				JLabel rangeStartLabel=new JLabel("IP Range: ");
				JTextField rangeStartTextField = new JTextField(10);
				JLabel rangeEndLabel=new JLabel("to");
				JTextField rangeEndTextField = new JTextField(10);
				String myIP = null;
				String myHostname = null;
				try {
					InetAddress ia = InetAddress.getLocalHost();
					myIP = ia.getHostAddress();
					myHostname = ia.getHostName();
				}catch(Exception e) {
					
				}
				String fixedIp = myIP.substring(0,myIP.lastIndexOf(".") +1);
				rangeStartTextField.setText(fixedIp +"1");
				rangeEndTextField.setText(fixedIp +"254");
				
				
				
				JComboBox cb = new JComboBox();
				cb.addItem("IP Range");
				cb.addItem("Random");
				cb.addItem("Text File");
				
				ImageIcon seticon = new ImageIcon("./icon/setting.png");
				JButton setbutton = new JButton(seticon);
				setbutton.setBorderPainted(false);
				setbutton.setContentAreaFilled(false);
				setbutton.setOpaque(false);
				
				ImageIcon menuicon = new ImageIcon("./icon/menu (1).png");
				JButton menubutton = new JButton(menuicon);
				menubutton.setBorderPainted(false);
				menubutton.setContentAreaFilled(false);
				menubutton.setOpaque(false);
				
				
				rangeStartLabel.setFont(myFont);
				rangeStartLabel.setPreferredSize(new Dimension(80,30));
				rangeEndLabel.setFont(myFont);
				rangeEndLabel.setPreferredSize(new Dimension(15,30));
				
				toolbar1.add(rangeStartLabel);
				toolbar1.add(rangeStartTextField);
				toolbar1.add(rangeEndLabel);
				toolbar1.add(rangeEndTextField);
				toolbar1.add(cb);
				toolbar1.add(setbutton);
				
				
				JLabel hostNameLabel= new JLabel("Hostname");
				JTextField hostNameTextField = new JTextField(10);
				try {
					hostNameTextField.setText(InetAddress.getLocalHost().getHostName());
				} catch (UnknownHostException e) {
					hostNameTextField.setText("N/A");
				}
				JButton upButton = new JButton("IP");
				JComboBox optionComboBox = new JComboBox();
				optionComboBox.addItem("/24");
				optionComboBox.addItem("/26");
				JButton startButton = new JButton("▶Start");
				
				hostNameLabel.setFont(myFont);
				hostNameTextField.setPreferredSize(new Dimension(90,30));
				upButton.setPreferredSize(new Dimension(50,30));
				optionComboBox.setPreferredSize(new Dimension(90,30));
				startButton.setPreferredSize(new Dimension(70,30));
				
				toolbar2.add(hostNameLabel);
				toolbar2.add(hostNameTextField);
				toolbar2.add(upButton);
				toolbar2.add(optionComboBox);
				toolbar2.add(startButton);
				toolbar2.add(menubutton);
				
				JPanel pane = new JPanel(new BorderLayout());
				pane.add(toolbar1,BorderLayout.NORTH);
				pane.add(toolbar2,BorderLayout.SOUTH);
				
				add(pane,BorderLayout.NORTH);
				
				//툴바 코드 끝
		
		//테이블 시작
		
		String[] titles = new String[] {
			"IP","Ping","Hostname","TTL","Port"	
		};
		Object[][] stats= initTable();
		JTable jTable = new JTable(stats,titles);
		startButton.addActionListener(new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==startButton) {
			startButton.setText("■Stop");
			loading.setValue(0);
		}
		for(int i = 1; i <= 254; i++) {
			final int I = i;
			String myIP = null;
			try {
				InetAddress ia = InetAddress.getLocalHost();
				myIP = ia.getHostAddress();
			}catch(Exception e1) {
				
			}
			String ip = myIP.substring(0,myIP.lastIndexOf(".") +1)+I;
			String msg[] = {null, null, null, null, null};
			msg[0] = ip;
			Thread thread = new Thread() {
				@Override
				public void run() {
					InputStream is = null;
					BufferedReader br = null;
					try {
						Runtime runtime = Runtime.getRuntime();
						Process pro = runtime.exec("ping -a "+ip);
						is = pro.getInputStream();
						br = new BufferedReader(new InputStreamReader(is));
						String line ;
				        InetAddress address = InetAddress.getByName(ip);
						boolean reachable = address.isReachable(200);
						
						if(reachable) {
							jTable.setValueAt(msg[0], I-1, 0);
							while((line = br.readLine()) != null) {
								if(line.indexOf("[") >= 0) {
									Pattern pattern_hostname = Pattern.compile("Ping\\s+(.+)\\s+\\[", Pattern.CASE_INSENSITIVE);
									Matcher matcher_hostname = pattern_hostname.matcher(line);
								
									while(matcher_hostname.find()) {
										msg[1]=matcher_hostname.group(1);
										jTable.setValueAt(msg[1], I-1, 2);
									}
								}
								if(line.indexOf("ms") >= 0) { 
									Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=)(\\d+)", Pattern.CASE_INSENSITIVE);
									Matcher matcher = pattern.matcher(line);
									while(matcher.find()) {
										msg[2] = matcher.group(1);
										msg[3] = matcher.group(4);
										jTable.setValueAt(msg[2], I-1, 1);
										jTable.setValueAt(msg[3], I-1, 3);
									}
								break;
								}
							}
							if(msg[1]==null) {
								jTable.setValueAt("[n/a]", I-1, 2);
							}
							ExecutorService es = Executors.newFixedThreadPool(20);
							int timeout = 1;
							List<Future<ScanResult>> futures = new ArrayList<>();
							int openPorts = 0;
							String openPortNumber = "";
							for(int port = 1; port <= 1024; port++) {
								try {
									Socket socket = new Socket();
									socket.connect(new InetSocketAddress(ip,port), timeout);
									socket.close();
									if(openPortNumber=="")
										openPortNumber+=port;
									else
										openPortNumber+=", "+port;
							} catch(Exception ex) {
							}
							}
							es.awaitTermination(200L, TimeUnit.MILLISECONDS);
							msg[4]=openPortNumber;
							jTable.setValueAt(msg[4], I-1, 4);

							loading.setValue(I-1);
							loading.repaint();
						}
						else {
							jTable.setValueAt(ip, I-1, 0);
							jTable.setValueAt("[n/s]", I-1, 1);
							jTable.setValueAt("[n/s]", I-1, 2);
							jTable.setValueAt("[n/s]", I-1, 3);
							jTable.setValueAt("[n/s]", I-1, 4);
						}
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			};
			
			thread.start();
		}
		}
		});
		
		JScrollPane sp = new JScrollPane(jTable);
		add(sp,BorderLayout.CENTER);
		
		//테이블 끝
		
		
		setSize(700,700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
			
		}
	
	public Object[][] initTable(){
		Object[][] result = new Object[254][5];
		return result;
	}
	public static void main(String[] args) {
		OutlinePing op = new OutlinePing();

	}

}
