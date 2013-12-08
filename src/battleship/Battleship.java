package battleship;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class Battleship extends JFrame {
	protected Config config;
	protected ImagePanel splashScreen = new ImagePanel("mainTitle2.png");
	private String selected;
	/**
	 * Construct a statically sized ConnectFour grid wrapper
	 */
	public Battleship() {
		setResizable(false);
		setTitle("Battleship");
		setJMenuBar(menuBar());
		setContentPane(splashScreen);
		pack();
		// Center on screen
		setLocationRelativeTo(null);

		config = getConfig();
	}

	public Config getConfig() {
		File directory = new File(System.getProperty("user.home"), ".battleship");
		if (!directory.exists()) {
			directory.mkdir();
		}
		File f = new File(directory, "config.dat");
		return new Config(f);
	}

	/**
	 * @return the JMenuBar
	 */
	public JMenuBar menuBar() {
		return new JMenuBar() {{
			final JLabel difficulty = new JLabel("Easy ");
			final ActionListener al = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					switch (e.getActionCommand()) {
//					case "newGame":
//						newGrid();
//						break;
//					case "undo":
//						if (getContentPane() instanceof Grid) ((Grid) getContentPane()).undoRound();
//						break;
//					case "redo":
//						if (getContentPane() instanceof Grid) ((Grid) getContentPane()).redoRound();
//						break;
					case "setEasy":
						difficulty.setText("Easy ");
						break;
					case "setHard":
						difficulty.setText("Hard ");
						break;
					case "showAbout":
						// The JOptionPane is a temporary means by which to display the about info
						JOptionPane.showMessageDialog(
							null,
							new JLabel("<html>Version 0.2<br><br>Created by:<br><br>Trevor Powless<br>Kurtis Reed</html>"),
							"Connect Four",
							JOptionPane.INFORMATION_MESSAGE
						);
						break;
					case "showHighScores":
						JOptionPane.showMessageDialog(null, "");
						break;
					case "quit":
//						if (getContentPane() instanceof Grid) {
//							int initializeGame = JOptionPane.NO_OPTION;
//							initializeGame = JOptionPane.showConfirmDialog(
//								null,
//								"You have a game running. Are you sure you want to quit?\nQuitting a game is scored as loss.",
//								"New Game",
//								JOptionPane.YES_NO_OPTION
//							);
//							if (initializeGame == JOptionPane.NO_OPTION || initializeGame == JOptionPane.DEFAULT_OPTION) return;
//						}
//						quit();
//						break;
					}
				}
			};
			add(new JMenu("Game") {{
				add(new JMenuItem("New Game") {{
					setActionCommand("newGame");
					setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
					addActionListener(al);
				}});
				
				addSeparator();
				
				// Undo option
				add(new JMenuItem("Undo") {{
					setActionCommand("undo");
					setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
					addActionListener(al);
				}});
				
				// Redo option
				add(new JMenuItem("Redo") {{
					setActionCommand("redo");
					setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
					addActionListener(al);
				}});
				
				addSeparator();
				
				// Difficulty options
				add(new JMenuItem("Difficulty:") {{
					setEnabled(false);
				}});

				final ButtonGroup difficultySelect = new ButtonGroup();

				// Easy option
				add(new JRadioButtonMenuItem("Easy") {{
					setActionCommand("setEasy");
					setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK));
					addActionListener(al);
					setSelected(true);
					difficultySelect.add(this);
				}});
				
				// Hard option
				add(new JRadioButtonMenuItem("Hard") {{
					setActionCommand("setHard");
					setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.CTRL_MASK));
					addActionListener(al);
					setSelected(false);
					difficultySelect.add(this);
				}});
			}});
			
			// Build the system menu
			add(new JMenu("System") {{
				add(new JMenuItem("About") {{
					setActionCommand("showAbout");
					setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
					addActionListener(al);
				}});
				
				add(new JMenuItem("High Scores") {{
					setActionCommand("showHighScores");
					setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
					addActionListener(al);
				}});
				
				addSeparator();

				add(new JMenuItem("Quit") {{
					setActionCommand("quit");
					setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
					addActionListener(al);
				}});
			}});
			
			// Create menu bar items at the right end
			add(Box.createHorizontalGlue());
			add(new JLabel("Difficulty: "));
			add(difficulty);
		}};
	}

	public JPanel playSelect(ActionListener actionListener, int style) {
		JPanel playerSelect = new JPanel();

		JButton game = new JButton("New Game");
		game.addActionListener(actionListener);
		
		playerSelect.add(game);

		DefaultListModel<String> playerNames = new DefaultListModel<String>();

//		for (HumanPlayer p : config.getPlayers()) {
//			playerNames.addElement(p.getName());
//		}
		
		JList<String> list = new JList<String>(playerNames);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {

			}
		});
		list.setVisibleRowCount(5);
			
		JScrollPane listScrollPane = new JScrollPane(list);
		listScrollPane.setSize(100, 50);
		
		/*
		JButton select = new JButton("Select Player");
		select.setSize(select.getPreferredSize());
		select.setLocation(50, 100);
		select.addActionListener(actionListener);
		*/
		
		JButton nPlayer = new JButton("New Player");
		nPlayer.setSize(nPlayer.getPreferredSize());
		nPlayer.setLocation(150,100);
		nPlayer.addActionListener(actionListener);
		
		if(style == 1)
		{
			playerSelect.add(listScrollPane);
			playerSelect.add(nPlayer);
			//playerSelect.add(select);
		}
		
		return playerSelect;
	}
	
	public JPanel gameMode(ButtonGroup buttonGroup, ActionListener actionListener) {
		JPanel mode = new JPanel();
		GroupLayout layout = new GroupLayout(mode);
		mode.setLayout(layout);
		
		layout.setAutoCreateGaps(true);				
		layout.setAutoCreateContainerGaps(true);

		JLabel gameMode = new JLabel("Select your gamemode");
		
		JRadioButton pvp = new JRadioButton("Player vs Player");
		pvp.setActionCommand("pvp");
		JRadioButton pvi = new JRadioButton("Player vs A.I.");
		pvi.setActionCommand("pvi");
		JRadioButton ivi = new JRadioButton("A.I. vs A.I.");
		ivi.setActionCommand("ivi");
		
		pvi.setSelected(true);
		buttonGroup.add(pvp);
		buttonGroup.add(pvi);
		buttonGroup.add(ivi);
		
		layout.setHorizontalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(gameMode)
					.addComponent(pvp)
					.addComponent(pvi)
					.addComponent(ivi))
		);
		
		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addComponent(gameMode)
				.addComponent(pvp)
				.addComponent(pvi)
				.addComponent(ivi)
		);
		
		return mode;
	}
	
	public void update() {
		
	}
	
	public JDialog infoPanel() {		
		final JDialog dialog = new JDialog();
		dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		
		final ButtonGroup buttonGroup = new ButtonGroup();
		
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
//		final ActionListener actionListener = new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if(e.getActionCommand().equals("New Game")) {
//					Player[] players = null;
//					selected = buttonGroup.getSelection().getActionCommand();
//					String selected = buttonGroup.getSelection().getActionCommand();
//					if (selected.equals("pvp")) {
//						players = new Player[]{new HumanPlayer("Player1", 0xFF0000), new HumanPlayer("Player2", 0x000000)};
//					} else if (selected.equals("pvi")) {
//						players = new Player[]{new HumanPlayer("Player", 0xFF0000), new AIPlayer(0x000000, false)};
//					} else if (selected.equals("ivi")) {
//						players = new Player[]{new AIPlayer(0xFF0000, false), new AIPlayer(0x000000, false)};
//					}
//					setContentPane(new Grid(players));
//					pack();
//					dialog.dispose();
//				}
//				if(e.getActionCommand().equals("New Player")) {
//					String nameIn = (String) JOptionPane.showInputDialog(null, "Enter the name for the new player","New Player",JOptionPane.DEFAULT_OPTION, null, null, "Untitled Player");
//					
//					config.addPlayer(new HumanPlayer(nameIn, 0xFF0000));
//					config.write();
//				}
//				}
//		};
		
//		JPanel gameTemp = gameMode(buttonGroup, actionListener);
//		final JPanel playTemp = playSelect(actionListener, 0);		
		
//		tabbedPane.addTab("Game Mode", gameTemp);
//		tabbedPane.addTab("Player", playTemp);
//		
//		tabbedPane.addFocusListener(new FocusListener(){
//			@Override
//			public void focusGained(FocusEvent arg0) {
//				//String selected = buttonGroup.getSelection().getActionCommand();
//				if(selected.equals("pvi") && playTemp.isVisible())
//				{
//					tabbedPane.removeAll();
//					tabbedPane.addTab("Game Mode", gameMode(buttonGroup, actionListener));
//					tabbedPane.addTab("Player", playSelect(actionListener, 1));
//				}
//			}

//			@Override
//			public void focusLost(FocusEvent arg0) {
//			}
//		});
		
		dialog.setSize(250,250);
		dialog.add(tabbedPane);
		return dialog;
	}


	/**
	 * Method to create a new game
	 */
//	public void newGrid() {
//		if (getContentPane() instanceof Grid) {
//			int initializeGame = JOptionPane.NO_OPTION;
//			initializeGame = JOptionPane.showConfirmDialog(
//				null,
//				"You have a game running. Do you want to abort and start a new game?\nAborting a game is scored as loss.",
//				"New Game",
//				JOptionPane.YES_NO_OPTION
//			);
//			if (initializeGame == JOptionPane.NO_OPTION || initializeGame == JOptionPane.DEFAULT_OPTION) return;
//		}
//		infoPanel().setVisible(true);
//		//setContentPane();
//		pack();
//		setLocationRelativeTo(null);
//	}
	
	/**
	 * This method exits the program after recording scores
	 */
	public static void quit() {
		System.exit(0);
	}
	
	/**
	 * 
	 * The main method of the program which runs the connect four game
	 * 
	 * @param args 
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Initialize a ConnectFour object and set default behavior
		Battleship frame = new Battleship();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}