import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.LineBorder;

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
 * need a help menu for tips on how to play the game
 * 
 * USE A CONTENT PANE, ADD A JLABEL IN THE UPPER PART IN THE MESAGE BAR
 * ADD A JLABEL IN THE LOWER PART FOR USE AS A STATUS BAR
 */
public class BoardGame extends JFrame {
	// Constants for window size
	final int WINDOW_WIDTH = 1300;
	final int WINDOW_LENGTH = 800;

	// objects needed for GUI
	private Container contentPane;
	private JPanel computerBoard;
	private JPanel userBoard;

	private JButton[][] compBoardSquares = new JButton[10][10];
	private JButton[][] userBoardSquares = new JButton[10][10];

	private HashMap<String, Integer> userSelection;
	private HashMap<String, Integer> computerSelection;

	private int userPoints;
	private int computerPoints;

	private JPanel bBoard;
	private JLabel statusBar;
	private static final String COLS = "ABCDEFGHIJ";
	public final static int EMPTY = 0;
	public final static int EMPTY_HIT = 2;
	public final static int OCCUPIED = 3;
	public final static int OCCUPIED_HIT = 4;

	public BoardGame() {

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
		
		JMenuItem helpAction = new JMenuItem("HELP");
		helpMenu.add(helpAction);
		helpAction.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null,"1.Fire at the center of the board" +
						"Statistically, you are more likely to hit a ship if you aim for the center\n" +
						"2. Use parity to up your chances.\n" +
						"3.Move away when you have two misses in the same segment." +
						"If you strike out twice when firing, try firing into a different segment of the board.\n" );
			}
		});

		contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		buildComputerPanel();
		contentPane.add(computerBoard, BorderLayout.EAST);

		buildUserPanel();
		contentPane.add(userBoard, BorderLayout.WEST);
		
		statusBar = new JLabel("Status",SwingConstants.CENTER);
		contentPane.add(statusBar, BorderLayout.SOUTH);
		
	}

	private void buildUserPanel() {
		userSelection = new HashMap<String, Integer>();
		userPoints = 0;
		userBoard = new JPanel();
		userBoard.setBackground(Color.lightGray);
		userBoard.setLayout(new BorderLayout());
		JLabel title = new JLabel("User Board", SwingConstants.CENTER);
		userBoard.add(title, BorderLayout.NORTH);

		bBoard = new JPanel(new GridLayout(12, 12, 2, 2));
		bBoard.setBorder(new LineBorder(Color.black));
		userBoard.add(bBoard, BorderLayout.CENTER);

		contentPane.add(userBoard, BorderLayout.CENTER);

		Insets squareMargin = new Insets(0, 0, 0, 0);
		int k = 0;
		for (int ii = 0; ii < userBoardSquares.length; ii++) {
			for (int jj = 0; jj < userBoardSquares[ii].length; jj++) {
				JButton b = new JButton();
				b.setMargin(squareMargin);
				b.setName(String.valueOf(k));
				k++;
				// Add the action when push the button in the userBoard
				b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JButton jb = ((JButton) e.getSource()); // Retreive the
																// button pushed
																// in the
																// computerBoard
						if (jb.getBackground() != Color.YELLOW) { // If the
																	// color is
																	// not
																	// yellow
							if (userSelection.size() < 10) { // here we check the
																// amount of
																// selected
																// positions.
								jb.setBackground(Color.YELLOW); // If it is bellow
																// ten, color to
																// yellow and
																// save in the
																// userMap
								userSelection.put(jb.getName(),
										Integer.valueOf(1));
							}
						} else { // If the current button is yellow unselect,
									// painting in white and erasing form the
									// userSelection HashMap
							jb.setBackground(Color.WHITE);
							userSelection.remove(jb.getName());
						}
					}
				});
				ImageIcon icon = new ImageIcon(new BufferedImage(48, 48,
						BufferedImage.TYPE_INT_ARGB));
				b.setIcon(icon);
				b.setBackground(Color.WHITE);
				userBoardSquares[jj][ii] = b;
			}
		}

		bBoard.add(new JLabel(""));
		for (int ii = 0; ii < 10; ii++) {
			bBoard.add(new JLabel(COLS.substring(ii, ii + 1),
					SwingConstants.CENTER));
		}

		for (int ii = 0; ii < 10; ii++) {
			for (int jj = 0; jj < 10; jj++) {
				switch (jj) {
				case 0:
					bBoard.add(new JLabel("" + (ii + 1), SwingConstants.CENTER));
				default:
					bBoard.add(userBoardSquares[jj][ii]);
				}
			}
		}

	}

	/*
	 * Method to select the random shot for the computer
	 */
	private void makeComputerShot() {

		Random randomGenerator = new Random();
		int randomInt;
		// Make random while the square no has selected before
		do {
			randomInt = randomGenerator.nextInt(100);
		} while (userBoardSquares[randomInt % 10][randomInt / 10]
				.getBackground() == Color.RED
				|| userBoardSquares[randomInt % 10][randomInt / 10]
						.getBackground() == Color.BLUE);
		System.out.println("Computer shot at : " + randomInt);

		// if the selected square is yellow the computer hit, and change the
		// square color to red ant incresse computerPoint.
		if (userBoardSquares[randomInt % 10][randomInt / 10].getBackground() == Color.YELLOW) {
			userBoardSquares[randomInt % 10][randomInt / 10]
					.setBackground(Color.RED);
			computerPoints++;
			// if reach 10 point show the dialog
			if (computerPoints == 10) {
				JOptionPane.showMessageDialog(null, "You lose!!!");
			}
		} else {
			// if the squeare is not yellow, the computer don't hit.
			userBoardSquares[randomInt / 10][randomInt % 10]
					.setBackground(Color.BLUE);
		}
	}

	private void buildComputerPanel() {
		computerSelection = new HashMap<String, Integer>();
		computerPoints = 0;
		computerBoard = new JPanel();
		computerBoard.setBackground(Color.lightGray);
		computerBoard.setLayout(new BorderLayout());

		JLabel title = new JLabel("Computer Board", SwingConstants.CENTER);
		computerBoard.add(title, BorderLayout.NORTH);

		bBoard = new JPanel(new GridLayout(12, 12, 2, 2));
		bBoard.setBorder(new LineBorder(Color.black));
		computerBoard.add(bBoard, BorderLayout.CENTER);

		contentPane.add(computerBoard, BorderLayout.CENTER);

		// Make the computer Random Selections
		Random randomGenerator = new Random();
		for (int idx = 1; idx <= 10; ++idx) {
			int randomInt;
			do {
				randomInt = randomGenerator.nextInt(100);
			} while (computerSelection.containsKey(String.valueOf(randomInt)));
			System.out.println("Generated : " + randomInt);
			computerSelection
					.put(String.valueOf(randomInt), Integer.valueOf(1));
		}

		int k = 0;
		Insets squareMargin = new Insets(0, 0, 0, 0);
		for (int ii = 0; ii < compBoardSquares.length; ii++) {
			for (int jj = 0; jj < compBoardSquares[ii].length; jj++) {
				JButton b = new JButton();
				b.setMargin(squareMargin);
				b.setName(String.valueOf(k));
				k++;
				ImageIcon icon = new ImageIcon(new BufferedImage(48, 48,
						BufferedImage.TYPE_INT_ARGB));
				b.setIcon(icon);
				b.setBackground(Color.WHITE);
				// Add the action when push the button in the computerBoard
				b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JButton jb = ((JButton) e.getSource()); // Retreive the
																// button pushed
																// in the
																// computerBoard
						if (computerSelection.containsKey(jb.getName())) { // If
																			// the
																			// button
																			// pushed
																			// exists
																			// in
																			// the
							jb.setBackground(Color.RED);
							userPoints++;
							statusBar.setText("HIT. " + "(USER: " + userPoints
									+ " COMPUTER: " + computerPoints + ")");
							if (userPoints == 10) {
								JOptionPane.showMessageDialog(null,
										"You win!!!");
							}
						} else {
							jb.setBackground(Color.BLUE);
							statusBar.setText("MISS" + "(USER: " + userPoints
									+ " COMPUTER: " + computerPoints + ")");
						}
						makeComputerShot();
					}
				});
				compBoardSquares[jj][ii] = b;
			}
		}

		bBoard.add(new JLabel(""));
		for (int ii = 0; ii < 10; ii++) {
			bBoard.add(new JLabel(COLS.substring(ii, ii + 1),
					SwingConstants.CENTER));
		}

		for (int ii = 0; ii < 10; ii++) {
			for (int jj = 0; jj < 10; jj++) {
				switch (jj) {
				case 0:
					bBoard.add(new JLabel("" + (ii + 1), SwingConstants.CENTER));
				default:
					bBoard.add(compBoardSquares[jj][ii]);
				}
			}
		}

	}

}
