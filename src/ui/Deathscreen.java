package ui;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Deathscreen extends JPanel{
	
	static JLabel gameover;
	
	public Deathscreen() {
		gameover = new JLabel("GAME OVER");
		
		add(gameover);
	}

}
