package ui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

import model.GameModel;

public class GameWindow {
	
	
	

	public static void show() {
		// Minimal model instance (empty for now, by design)
		GameModel model = new GameModel();


		JFrame frame = new JFrame("CSSE220 Final Project");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(new GameComponent(model));
		//frame.add(new GameComponent());
		//frame.add(new GameComponent("jack"));
		

		frame.setSize(1200, 1200);
		frame.setLocationRelativeTo(null); // center on screen (nice UX, still minimal)
		frame.setVisible(true);
		}

}
