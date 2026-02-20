package ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.GameModel;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;


public class GameWindow {
	private static Clip clip;
	
	
	

	public static void show() {
		// Minimal model instance (empty for now, by design)
		GameModel model = new GameModel();
		


		JFrame frame = new JFrame("CSSE220 Final Project");
	
		
		frame = new JFrame("Moving Ball");
		//frame.setContentPane(new Controller());
		
		JPanel cards = new JPanel(new CardLayout());
		Startscreen start = new Startscreen();
		Winner win = new Winner();
		Deathscreen death = new Deathscreen();
		//GameComponent game2 = new GameComponent(model,2);
		cards.add(start, "START");
		cards.add(win,"Winner");
		cards.add(death, "death");
		frame.setContentPane(cards);
		CardLayout cl = (CardLayout) cards.getLayout();
		GameComponent game = new GameComponent(
			    model,
			    1,
			    () -> {cl.show(cards,"death");
			    playSound("/Sounds/dead.wav",true);
			    
			    },
			    () -> {
			        cl.show(cards, "Winner");
			        playSound("/Sounds/end.wav",true);
			       
			    }
			);
		cards.add(game, "GAME");
		cl.show(cards, "START");
		playSound("/Sounds/startmusic.wav",true);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Startscreen.start.addActionListener(e -> {
		    game.startGame();
		    cl.show(cards, "GAME");
		    playSound("/Sounds/background.wav", true);
		   
		    game.requestFocusInWindow();
		    System.out.println(game.GetLives());

		});
		

		

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//frame.add(new GameComponent(model));
		//frame.add(new GameComponent());
		//frame.add(new GameComponent("jack"));
		

		//frame.setSize(1200, 1200);
		frame.pack();
		frame.setLocationRelativeTo(null); // center on screen (nice UX, still minimal)
		frame.setVisible(true);
		
		

		
		}
	

	public static void playSound(String soundFile, boolean loop) {
	    try {

	        if (clip != null) {
	            clip.stop();
	            clip.close();
	        }

	        URL soundURL = GameWindow.class.getResource(soundFile);

	        if (soundURL == null) {
	            System.out.println("Sound file not found: " + soundFile);
	            return;
	        }

	        AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);

	        clip = AudioSystem.getClip();
	        clip.open(audioStream);

	        if (loop) {
	            clip.loop(Clip.LOOP_CONTINUOUSLY);
	        }

	        clip.start();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	


}
