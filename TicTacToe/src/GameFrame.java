import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Panel;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.Box;

public class GameFrame extends JFrame implements ActionListener{

	private boolean xTurn = true;			// is 'X' next to play ?
	private JButton theButtons[][];
	private JButton btnPlay, btnQuit;
	
	private JPanel contentPane;
	private JTextField textFieldPlayer1Username;
	private JTextField textFieldPlayer2;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Clears grid when Quit button clicked
	 * @param xTurn
	 */
	private void resetGame(boolean xTurn) {
		for(int row=0;row<3;row++) {
			for(int col=0;col<3;col++) {
				JButton btn = theButtons[row][col];
				btn.setText("");
			}
		}
		this.xTurn = true;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		// When button pressed, the button is identified and a related
		// functionality is performed
		
		JButton btn = (JButton)arg0.getSource();
		if ( btn == btnQuit ) {
			System.exit(0);
		}
		else if ( btn == btnPlay) {
			resetGame(true);
		}
		else {
			if ( btn.getText().equals("") ) {
				if ( xTurn ) {
					btn.setText("X");
					xTurn = false;
				}
				else {
					btn.setText("O");
					xTurn = true;
				}
			}
			
			checkGameFinished();
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
					outcomeMsg = "Player 1 (X) Wins!";
				}else {
					outcomeMsg = "Player 2 (O) Wins!";
				}

			}
			
			// Display Win Notification
			notifyOutcome(outcomeMsg);
			resetGame(xTurn);
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
			resetGame(xTurn);
			return;
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
		JOptionPane.showMessageDialog(null,  outcomeMsg,  "Game Finished",  JOptionPane.PLAIN_MESSAGE, new ImageIcon("images/noot.gif"));
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
		
		JLabel lblPlayer_2 = new JLabel("Player 1:");
		scorePanel.add(lblPlayer_2);
		
		textField = new JTextField();
		scorePanel.add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("|");
		scorePanel.add(label);
		
		JLabel lblPlayer_3 = new JLabel("Player 2:");
		scorePanel.add(lblPlayer_3);
		
		textField_1 = new JTextField();
		scorePanel.add(textField_1);
		textField_1.setColumns(10);
		
		Panel optionsPanel = new Panel();
		infoOptionsPanel.add(optionsPanel, BorderLayout.SOUTH);
		
		JButton btnStartGame = new JButton("Start Game");
		optionsPanel.add(btnStartGame);
		
		btnPlay = new JButton("Reset");
		optionsPanel.add(btnPlay);
		
		btnQuit = new JButton("Quit");
		optionsPanel.add(btnQuit);
		btnQuit.addActionListener(this);
		btnPlay.addActionListener(this);
		
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
		
		JLabel lblPlayer_1 = new JLabel("Player 1");
		player1Options.add(lblPlayer_1);
		
		textFieldPlayer1Username = new JTextField();
		player1Options.add(textFieldPlayer1Username);
		textFieldPlayer1Username.setColumns(10);
		
		JRadioButton rdbtnP1Human = new JRadioButton("Human");
		player1Options.add(rdbtnP1Human);
		rdbtnP1Human.setSelected(true);
		
		JRadioButton rdbtnP1Computer = new JRadioButton("Computer");
		player1Options.add(rdbtnP1Computer);
		
		ButtonGroup bg1 = new ButtonGroup();
		bg1.add(rdbtnP1Human);
		bg1.add(rdbtnP1Computer);
		
		Panel player2Options = new Panel();
		playersInfo.add(player2Options);
		
		JLabel lblPlayer = new JLabel("Player 2");
		player2Options.add(lblPlayer);
		
		textFieldPlayer2 = new JTextField();
		player2Options.add(textFieldPlayer2);
		textFieldPlayer2.setColumns(10);
		
		JRadioButton rdbtnP2Human = new JRadioButton("Human");
		player2Options.add(rdbtnP2Human);
		rdbtnP2Human.setSelected(true);
		
		JRadioButton rdbtnP2Computer = new JRadioButton("Computer");
		player2Options.add(rdbtnP2Computer);
		
		ButtonGroup bg2 = new ButtonGroup();
		bg2.add(rdbtnP2Human);
		bg2.add(rdbtnP2Computer);
	}

}
