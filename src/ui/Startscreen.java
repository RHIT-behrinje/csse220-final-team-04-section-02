package ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Startscreen extends JPanel{
	
	static JButton start;
	static JLabel gamestart;
	
	public Startscreen() {
		
		setLayout(new BorderLayout());
		
		
		start = new JButton("Start");
		gamestart = new JLabel("Game Start");
		
		add(start, BorderLayout.SOUTH);
		add(gamestart, BorderLayout.CENTER);
		
	}

}
