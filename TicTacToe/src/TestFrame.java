import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Panel;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TestFrame extends JFrame implements ActionListener {

	private JPanel contentPane;

	public void actionPerformed(ActionEvent e) {
		GameFrame gf = new GameFrame();
		gf.setVisible(true);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestFrame frame = new TestFrame();
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
	public TestFrame() {
		setResizable(false);
		setTitle("Demo Frame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		Panel panel = new Panel();
		panel.setBackground(Color.RED);
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btnOk = new JButton("OK");
		panel.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		panel.add(btnCancel);
		
		JButton btnPaulsGame = new JButton("Pauls Game");
		btnPaulsGame.addActionListener(this);
		panel.add(btnPaulsGame);
		
		Panel gridPanel = new Panel();
		gridPanel.setBackground(Color.YELLOW);
		contentPane.add(gridPanel, BorderLayout.CENTER);
		gridPanel.setLayout(new GridLayout(3, 3, 2, 2));
		
		JButton btnNewButton_1 = new JButton();
		gridPanel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton();
		gridPanel.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton();
		gridPanel.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton();
		gridPanel.add(btnNewButton_4);
		
		JButton btnNewButton = new JButton();
		gridPanel.add(btnNewButton);
		
		JButton btnNewButton_6 = new JButton();
		gridPanel.add(btnNewButton_6);
		
		JButton btnNewButton_5 = new JButton("New button");
		gridPanel.add(btnNewButton_5);
		
		JButton btnNewButton_7 = new JButton("New button");
		gridPanel.add(btnNewButton_7);
		
		JButton btnNewButton_8 = new JButton("New button");
		gridPanel.add(btnNewButton_8);
	}

}
