import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.GridLayout;
import java.awt.Panel;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.text.AbstractDocument;

public class GameFrame extends JFrame implements ActionListener{

	private boolean xTurn = true;			// is 'X' next to play ?
	private JButton theButtons[][];
	private JButton btnReset, btnQuit, btnStartGame;
	
	private JPanel contentPane;
	private JTextField textFieldPlayer1Username;
	private JTextField textFieldPlayer2Username;
	private JTextField textFieldPlayer1Score;
	private JTextField textFieldPlayer2Score;
	
	private JLabel player1NameLabel;
	private JLabel player2NameLabel;
	
	private JRadioButton rdbtnP1Human;
	private JRadioButton rdbtnP1Computer;
	private JRadioButton rdbtnP2Human;
	private JRadioButton rdbtnP2Computer;
	
	boolean player1Human = true;			// is Player 1 Human?
	boolean player1Valid = false;			// is Player 1 User Name Valid?
	
	boolean player2Human = true;			// is Player 2 Human?
	boolean player2Valid = false;			// is Player 2 User Name Valid?
	
	int player1Score = 0;					// Player 1's Score
	int player2Score = 0;					// Player 2's Score
	
	
	
	

	/**
	 * Clears grid when Quit button clicked
	 */
	private void resetGame() {
		
		// Reset Grid
		resetGrid();
		
		// Reset Player's scores
		player1Score = 0;
		player2Score = 0;
		
		// Reset Score Panel
		player1NameLabel.setText("Player 1 Score:");
		player2NameLabel.setText("Player 2 Score:");
		textFieldPlayer1Score.setText(Integer.toString(player1Score));
		textFieldPlayer2Score.setText(Integer.toString(player2Score));
		
		// Disable Grid until new user names are validated
		disableAllButtons();
		
		// Re-enable user name text fields
		textFieldPlayer1Username.setText("");
		textFieldPlayer1Username.setEnabled(true);
		textFieldPlayer2Username.setText("");
		textFieldPlayer2Username.setEnabled(true);
		
		// Reset RadioButtons
		rdbtnP1Human.setSelected(true);
		rdbtnP2Human.setSelected(true);
		
		// Reset player type variables
		player1Human = true;
		player2Human = true;
		
		// Reset validation variables
		player1Valid = false;
		player2Valid = false;
		
		return;

	}
	
	/**
	 * Method called when an outcome has been identified, and a new round/game needs to start 
	 */
	private void resetGrid() {
		// Clears Grid
		clearButtons();
		
		// Sets X to first player (X always first)
		this.xTurn = true;
	}
	
	
	/**
	 * Method used to set all buttons on the grid back to empty text
	 */
	private void clearButtons() {
		for(int row=0;row<3;row++) {
			for(int col=0;col<3;col++) {
				JButton btn = theButtons[row][col];
				btn.setText("");
			}
		}
	}
	
	public void actionPerformed(ActionEvent arg0) {
		
		// Try to source the button. If an error occurs, we know a button wasn't clicked
		
		try {
			
			JButton btn = (JButton)arg0.getSource();
			
			// Performs action based on button source
			
			if ( btn == btnQuit ) {
				// Closes the Game
				System.exit(0);
			}
			else if ( btn == btnReset) {
				// Resets the game (clears grid, resets scores and user names)
				resetGame();
			}
			else if( btn == btnStartGame) {
				// Starts game (enables grid, sets scores to 0 and saves user names)
				enableAllButtons();
				player1NameLabel.setText(textFieldPlayer1Username.getText());
				player2NameLabel.setText(textFieldPlayer2Username.getText());
				btnStartGame.setBackground(Color.LIGHT_GRAY);
				
				
			}
			else if( btn.getText().equals("") ){
				if ( xTurn ) {
					btn.setText("X");
					xTurn = false;
				}
				else {
					btn.setText("O");
					xTurn = true;
				}
				
				checkGameFinished();
				
				// Check to see if next move is a computer move
				checkNextMoveIsCPU();
				
			}

		} catch (Exception e) {
			
			// Button wasn't pressed. We can assume a radio button was clicked

			JRadioButton btn = (JRadioButton)arg0.getSource();
			System.out.println(arg0.getActionCommand());
			
			if(btn == rdbtnP1Human) {
				// System knows player 1 is human and user name is required
				// (hence set to invalid until valid user name provided)
				player1Human = true;
				player1Valid = false;
				textFieldPlayer1Username.setText("");
				textFieldPlayer1Username.setEnabled(true);
			}
			else if(btn == rdbtnP1Computer){
				// System knows player 1 is computer and user name is not required
				// (hence set to valid as no further validation required)
				player1Human = false;
				player1Valid = true;
				textFieldPlayer1Username.setText("CPU");
				textFieldPlayer1Username.setEnabled(false);
				
				// If the CPU has been selected implying Player 1 is valid, the game may be ready to start
				checkIfReadyToStart();
			}
			else if(btn == rdbtnP2Human){
				// System knows player 2 is human and user name is required
				// (hence set to invalid until valid user name provided)
				player2Human = true;
				player2Valid = false;
				textFieldPlayer2Username.setText("");
				textFieldPlayer2Username.setEnabled(true);
			}
			else if(btn == rdbtnP2Computer){
				// System knows player 2 is computer and user name is not required
				// (hence set to valid as no further validation required)
				player2Human = false;
				player2Valid = true;
				textFieldPlayer2Username.setText("CPU");
				textFieldPlayer2Username.setEnabled(false);
				
				// If the CPU has been selected implying Player 2 is valid, the game may be ready to start
				checkIfReadyToStart();

			}
			
		}
		
	}
	
	/**
	 * Method checks for a game outcome (i.e. all win possibilities)
	 * - If all grid buttons have been clicked, there will be an outcome, otherwise the game will continue
	 * - If we know the game has ended, the method checks every win possibility and identifies a winner
	 * - If no winner can be found, we know it is a draw
	 */
	public void checkGameFinished() {
		
		// Check if a player has won
		boolean playerWon = checkWinner();
		
		// Message variable informing outcome
		String outcomeMsg = "";
		
		// If player has won, output win message
		if(playerWon) {
			
			// Find which player made the winning move
			if(playerWon) {
				if(!xTurn) {
					// Winning message set
					outcomeMsg = player1NameLabel.getText() + " (X) Wins!";
					// Increase Player 1's Score
					player1Score +=1;
					textFieldPlayer1Score.setText(Integer.toString(player1Score));
				}else {
					// Winning message set
					outcomeMsg = player2NameLabel.getText() + " (O) Wins!";
					// Increase Player 1's Score
					player2Score +=1;
					textFieldPlayer2Score.setText(Integer.toString(player2Score));
				}

			}
			
			
			// Display Win Notification
			notifyOutcome(outcomeMsg);
			resetGrid();
			return;
		}

			
		// If a player has not won, check for draw (all buttons "claimed")
		boolean foundEmpty = false;
		for(int row=0;row<3;row++) {
			for(int col=0;col<3;col++) {
				JButton btn = theButtons[row][col];
				if(btn.getText().equals("")) {
					foundEmpty = true;
				}
			}
		}
		
		// If all buttons "claimed", draw notification displayed
		if(!foundEmpty) {
			outcomeMsg = "Draw!";
			notifyOutcome(outcomeMsg);
			resetGrid();
			return;
		}
			
	}
	
	
	
	/**
	 * Method disables all buttons on the grid
	 */
	public void disableAllButtons() {
		for(int row=0;row<3;row++) {
			for(int col=0;col<3;col++) {
				JButton btn = theButtons[row][col];
				btn.setEnabled(false);
			}
		}
	}
	
	/**
	 * Method enables all buttons on the grid
	 */
	public void enableAllButtons() {
		for(int row=0;row<3;row++) {
			for(int col=0;col<3;col++) {
				JButton btn = theButtons[row][col];
				btn.setEnabled(true);
			}
		}
	}
	
	
	
	/**
	 * Method checks every possible game outcome. If a row of 3 X's/O's is found, return true knowing there is a winner.
	 * If there is no three rows of X's or O's, we know it is a draw and return false.
	 * @return (true) if there is a winner, (false) if draw.
	 */
	public boolean checkWinner() {
		
		JButton btn1, btn2, btn3;
		boolean playerWon = false;
		String currentPlayer;
		
		if(!xTurn) {
			currentPlayer = "X";
		}else {
			currentPlayer = "O";
		}
		
		// Check Row Win
		for(int row=0;row<3;row++) {
			btn1 = theButtons[row][0];
			btn2 = theButtons[row][1];
			btn3 = theButtons[row][2];
			if(btn1.getText().equals(currentPlayer) && btn2.getText().equals(currentPlayer) && btn3.getText().equals(currentPlayer)) {
				playerWon = true;
			}
		}
		
		// Check Column Win
		for(int col=0;col<3;col++) {
			btn1 = theButtons[0][col];
			btn2 = theButtons[1][col];
			btn3 = theButtons[2][col];
			if(btn1.getText().equals(currentPlayer) && btn2.getText().equals(currentPlayer) && btn3.getText().equals(currentPlayer)) {
				playerWon = true;
			}
		}
		
		// Check Diagonal Win (\)
		btn1 = theButtons[0][0];
		btn2 = theButtons[1][1];
		btn3 = theButtons[2][2];
		if(btn1.getText().equals(currentPlayer) && btn2.getText().equals(currentPlayer) && btn3.getText().equals(currentPlayer)) {
			playerWon = true;
		}
		
		// Check Diagonal Win(/)
		btn1 = theButtons[0][2];
		btn2 = theButtons[1][1];
		btn3 = theButtons[2][0];
		if(btn1.getText().equals(currentPlayer) && btn2.getText().equals(currentPlayer) && btn3.getText().equals(currentPlayer)) {
			playerWon = true;
		}
		
		return playerWon;
	}
	
	
	public void notifyOutcome(String outcomeMsg) {
		JOptionPane.showMessageDialog(null,  outcomeMsg,  "Game Finished!",  JOptionPane.PLAIN_MESSAGE, new ImageIcon("images/noot.gif"));
	}
	
	
	/**
	 * Method called when contents of either user name text field changes
	 * - Validates the new user name using a length check
	 * - Changes the colour of the text in the text field depending on it being valid or not
	 */
	public void checkIfReadyToStart() {
		
		// Validates contents of Player 1 User Name TextField
		player1Valid = validateUsername(textFieldPlayer1Username);
		
		// Validates contents of Player 2 User Name TextField	
		player2Valid = validateUsername(textFieldPlayer2Username);
		
		// Enables button if both names are valid. If not, start game button disabled
		if(player1Valid && player2Valid) {
			// If "ready to start", enable and change colour of start game button
			btnStartGame.setEnabled(true);
			btnStartGame.setBackground(Color.GREEN);
		}else {
			// If validation incomplete, start game button disabled and colour reversed
			btnStartGame.setEnabled(false);
			btnStartGame.setBackground(Color.LIGHT_GRAY);
		}
		
	}
	
	
	/**
	 * Method checks the contents of a text field and performs a length check.
	 * Font is highlighted accordingly and returns a true/false value depending on the contents being a valid user name
	 * @param textFieldToCheck takes the text field object as parameter
	 * @return returns true if contents are a valid user name
	 */
	public boolean validateUsername(JTextField textFieldToCheck) {
		
		boolean isValidUsername = false;
		String content = textFieldToCheck.getText();
		textFieldToCheck.setForeground(Color.RED);
		
		// Length Checks user name between 2 and 10 characters long
		if(content.length() > 2 && content.length() <=15) {
			isValidUsername = true;
			textFieldToCheck.setForeground(Color.GREEN);
		}else {
			textFieldToCheck.setForeground(Color.RED);
		}
		
		return isValidUsername;
		
	}
	
	
	public void checkNextMoveIsCPU() {
		boolean computerMakesMove = false;
		
		// Determine if the computer needs to make a move
		if(xTurn && !player1Human) {
			computerMakesMove = true;
		}
		else if(!xTurn && !player2Human) {
			computerMakesMove = true;
		}
		
		// If computer has to make a move, call method to make move
		if(computerMakesMove) {
			cpuMakesMove();
		}
		
		return;
	}
	
	
	public void cpuMakesMove() {
		int gridPosition;
		
		
		
	}
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameFrame frame = new GameFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	/**
	 * Create the frame.
	 */
	public GameFrame() {
		setResizable(false);
		setTitle("Tic Tac Toe");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(2, 2));
		
		Panel gameGrid = new Panel();
		contentPane.add(gameGrid, BorderLayout.CENTER);
		gameGrid.setLayout(new BorderLayout(0, 0));
		
		Panel gridPanel = new Panel();
		gridPanel.setBackground(Color.WHITE);
		gameGrid.add(gridPanel, BorderLayout.CENTER);
		gridPanel.setLayout(new GridLayout(3, 3, 0, 0));
		
		// Adding the game buttons
		theButtons = new JButton[3][3];
		for(int row=0;row<3;row++) {
			for(int col=0;col<3;col++) {
				JButton btn = new JButton();
				Font myFont = new Font("Serif", Font.BOLD, 50);
				btn.setFont(myFont);
				theButtons[row][col] = btn;
				gridPanel.add(btn );
				btn.addActionListener(this);
			}
		}
		
		Panel infoOptionsPanel = new Panel();
		contentPane.add(infoOptionsPanel, BorderLayout.SOUTH);
		infoOptionsPanel.setLayout(new BorderLayout(0, 0));
		
		Panel scorePanel = new Panel();
		infoOptionsPanel.add(scorePanel, BorderLayout.NORTH);
		
		player1NameLabel = new JLabel("Player 1 Score:");
		scorePanel.add(player1NameLabel);
		
		textFieldPlayer1Score = new JTextField();
		textFieldPlayer1Score.setEditable(false);
		textFieldPlayer1Score.setText("0");
		scorePanel.add(textFieldPlayer1Score);
		textFieldPlayer1Score.setColumns(1);
		
		JLabel label = new JLabel("|");
		scorePanel.add(label);
		
		player2NameLabel = new JLabel("Player 2 Score:");
		scorePanel.add(player2NameLabel);
		
		textFieldPlayer2Score = new JTextField();
		textFieldPlayer2Score.setEditable(false);
		textFieldPlayer2Score.setText("0");
		scorePanel.add(textFieldPlayer2Score);
		textFieldPlayer2Score.setColumns(1);
		
		Panel optionsPanel = new Panel();
		infoOptionsPanel.add(optionsPanel, BorderLayout.SOUTH);
		
		btnStartGame = new JButton("Start Game");
		optionsPanel.add(btnStartGame);
		
		btnReset = new JButton("Reset");
		optionsPanel.add(btnReset);
		
		btnQuit = new JButton("Quit");
		optionsPanel.add(btnQuit);
		btnQuit.addActionListener(this);
		btnReset.addActionListener(this);
		btnStartGame.addActionListener(this);
		
		Panel LeftPanel = new Panel();
		contentPane.add(LeftPanel, BorderLayout.WEST);
		
		Panel RightPanel = new Panel();
		contentPane.add(RightPanel, BorderLayout.EAST);
		
		Panel playersInfo = new Panel();
		playersInfo.setBackground(Color.LIGHT_GRAY);
		contentPane.add(playersInfo, BorderLayout.NORTH);
		playersInfo.setLayout(new GridLayout(2, 1, 0, 2));
		
		Panel player1Options = new Panel();
		playersInfo.add(player1Options);
		
		JLabel player1Label = new JLabel("Player 1");
		player1Options.add(player1Label);
		
		textFieldPlayer1Username = new JTextField();
		textFieldPlayer1Username.setEnabled(true);
		player1Options.add(textFieldPlayer1Username);
		textFieldPlayer1Username.setColumns(10);
		
		// Add Document Listener to User Name Text Field
		textFieldPlayer1Username.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				  changeDetected();
			  }
			  public void removeUpdate(DocumentEvent e) {
				  changeDetected();
			  }
			  public void insertUpdate(DocumentEvent e) {
				  changeDetected();
			  }

			  public void changeDetected() {
				  if(player1Human) {
					  checkIfReadyToStart();
				  }
			  }
			  
			});
		
		rdbtnP1Human = new JRadioButton("Human");
		player1Options.add(rdbtnP1Human);
		rdbtnP1Human.addActionListener(this);
		rdbtnP1Human.setSelected(true);
		
		rdbtnP1Computer = new JRadioButton("Computer");
		player1Options.add(rdbtnP1Computer);
		rdbtnP1Computer.addActionListener(this);
		
		ButtonGroup bg1 = new ButtonGroup();
		bg1.add(rdbtnP1Human);
		bg1.add(rdbtnP1Computer);
		
		Panel player2Options = new Panel();
		playersInfo.add(player2Options);
		
		JLabel player2Label = new JLabel("Player 2");
		player2Options.add(player2Label);
		
		textFieldPlayer2Username = new JTextField();
		player2Options.add(textFieldPlayer2Username);
		textFieldPlayer2Username.setColumns(10);
		
		// Add Document Listener to User Name Text Field
		textFieldPlayer2Username.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				  changeDetected();
			  }
			  public void removeUpdate(DocumentEvent e) {
				  changeDetected();
			  }
			  public void insertUpdate(DocumentEvent e) {
				  changeDetected();
			  }

			  public void changeDetected() {
				  if(player2Human) {
					  checkIfReadyToStart();
				  }
			  }
			  
			});
		
		
		rdbtnP2Human = new JRadioButton("Human");
		player2Options.add(rdbtnP2Human);
		rdbtnP2Human.addActionListener(this);
		rdbtnP2Human.setSelected(true);
		
		rdbtnP2Computer = new JRadioButton("Computer");
		player2Options.add(rdbtnP2Computer);
		rdbtnP2Computer.addActionListener(this);
		
		ButtonGroup bg2 = new ButtonGroup();
		bg2.add(rdbtnP2Human);
		bg2.add(rdbtnP2Computer);
		
		// Immediately disables all buttons until game begins
		disableAllButtons();
		
		// Disables start game button until user names valid
		btnStartGame.setEnabled(false);
		
	}

}
