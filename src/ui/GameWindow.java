package ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.GameModel;


public class GameWindow {
	
	
	

	public static void show() {
		// Minimal model instance (empty for now, by design)
		GameModel model = new GameModel();
		


		JFrame frame = new JFrame("CSSE220 Final Project");
	
		
		frame = new JFrame("Moving Ball");
		//frame.setContentPane(new Controller());
		
		JPanel cards = new JPanel(new CardLayout());
		Startscreen start = new Startscreen();
		
		Deathscreen death = new Deathscreen();
		//GameComponent game2 = new GameComponent(model,2);
		cards.add(start, "START");
		
		cards.add(death, "death");
		frame.setContentPane(cards);
		CardLayout cl = (CardLayout) cards.getLayout();
		GameComponent game = new GameComponent(model,1,()-> cl.show(cards,"death"));
		cards.add(game, "GAME");
		cl.show(cards, "START");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Startscreen.start.addActionListener(e -> {
		    game.startGame();
		    cl.show(cards, "GAME");
		    game.requestFocusInWindow();
		    System.out.println(game.GetLives());

		});
		
		if (!game.alive) {
			cl.show(cards, "death");
			System.out.println(game.player.lives);
		}

		

		

		
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//frame.add(new GameComponent(model));
		//frame.add(new GameComponent());
		//frame.add(new GameComponent("jack"));
		

		//frame.setSize(1200, 1200);
		frame.pack();
		frame.setLocationRelativeTo(null); // center on screen (nice UX, still minimal)
		frame.setVisible(true);
		
		

		
		}
	


}
