import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/*
 * Board Game need two 10*10 boards 1 for user 1 for Comp
 * at the start the comp chooses 10 random squares on its board
 * after the user chooses their 10 square which will turn yellow after it has been chosen
 * User gets the first  shot, if they make a hit they get to go again and the square will turn RED.
 * and they cannot choose the same square again. If they miss the square will turn BLUE.
 * 
 * Game needs to provide a message Board for displaying messages like 
 * "hit", "miss", "Player victory", "computer victory"
 * needs a point recording status
 * needs a file menu to cancel the game
 * 
 * could have a difficulty setting
 * 
 * need a help menu for tips on how to play the game
 * 
 * 
 * USE A CONTENT PANE, ADD A JLABEL IN THE UPPER PART IN THE MESAGE BAR
 * ADD A JLABEL IN THE LOWER PART FOR USE AS A STATUS BAR
 *  
 */
public class BoardGame extends JFrame {

	// Constants for window size
	final int WINDOW_WIDTH = 650;
	final int WINDOW_LENGTH = 800;

	// objects needed for GUI
	private Container contentPane;
	private JPanel statusPanel;
	private JPanel topPanel;
	private JPanel bottomPanel;
	private JLabel statusBar;
	public BoardGame() {

		int[][] compBoard = new int[10][10];
		int[][] userBoard = new int[10][10];
		int shotsHit = 0;
		int shotsMissed = 0;

		setTitle("BattleShip");
		setSize(WINDOW_WIDTH, WINDOW_LENGTH);

		JMenuBar menuBar = new JMenuBar();

		setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("File");
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);

		JMenuItem exitAction = new JMenuItem("EXIT");
		fileMenu.add(exitAction);
		exitAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});

		contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		
		buildTopPanel();
		contentPane.add(topPanel, BorderLayout.NORTH);
		
		buildStatusPanel();

		buildBottomPanel();
		contentPane.add(bottomPanel, BorderLayout.SOUTH);

	}

	private void buildTopPanel() {
		// create top panel
		topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(200,300));
		topPanel.setBackground(Color.lightGray);
		contentPane.add(topPanel, BorderLayout.NORTH);
		
		JPanel[][] compBoard = new JPanel[10][10];

	}
	
	private void buildStatusPanel(){
		statusPanel = new JPanel();
		statusPanel.setPreferredSize(new Dimension(200,200));
		statusPanel.setBackground(Color.white);
		contentPane.add(statusPanel, BorderLayout.CENTER);
		
	}

	private void buildBottomPanel() {
		bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(200,300));
		bottomPanel.setBackground(Color.lightGray);
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
		
		JPanel[][] userBoard = new JPanel[10][10];

	}

}
