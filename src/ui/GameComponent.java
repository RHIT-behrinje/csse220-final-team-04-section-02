package ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.Timer;

import model.Characterz;
import model.EXIT;
import model.Floor;
import model.GameModel;
import model.Wall;
import model.Zombie;



public class GameComponent extends JComponent {
	Characterz player;
	EXIT exit;
	
	private GameModel model;
	
	private Timer timer;
	public static final int WIDTH = 20*GameModel.tileSize;
	public static final int HEIGHT = 20*GameModel.tileSize;
	ArrayList zombie = new ArrayList<Zombie>();
	ArrayList walls = new ArrayList<Wall>();
	ArrayList floors = new ArrayList<Floor>();


	public GameComponent(GameModel model) {
	this.model = model;
	
	File file = new File("level1.txt"); 
    
    
    try {
    	  Scanner scanner = new Scanner(file);
	    
	    int row = 0;

	    while (scanner.hasNextLine()) {
	      String line = scanner.nextLine();

	      for (int col = 0; col < line.length(); col++) {
	        char c = line.charAt(col);

	        if (c == 'P') {
	        	this.player =  new Characterz(col * GameModel.tileSize,row*GameModel.tileSize);
	    	      
	      }
	        if (c == 'Z') {
	        	this.zombie.add(new Zombie(col * GameModel.tileSize,row*GameModel.tileSize));
	        }
	        
	        if (c == '*') {
	        	this.walls.add(new Wall(col * GameModel.tileSize,row*GameModel.tileSize));
	        }
	        //stuff below this is before pull
	        if (c == '.' || c == 'Z' || c == 'P'|| c == 'C') {
	        	this.floors.add(new Floor(col * GameModel.tileSize,row*GameModel.tileSize));
	        }
	        if (c == 'E') {
	        	this.exit =  new EXIT(col * GameModel.tileSize,row*GameModel.tileSize);
	        }

	    	
	      }

	      row++;
	    }

	    scanner.close();
    
    	 
    	} catch (FileNotFoundException e) {
    	  System.out.println("level1.txt not found");
    	}
	
    timer = new Timer(20, e -> {
    	  player.update(WIDTH, HEIGHT);
    	  repaint();
    	});
    	timer.start();
    	setFocusable(true);
  	
  	  addKeyListener(new KeyAdapter() {
    	    @Override
    	    public void keyPressed(KeyEvent e) {
    	      if (e.getKeyCode() == KeyEvent.VK_D) {
    	        player.movepx();
    	      }
    	      if (e.getKeyCode() == KeyEvent.VK_A) {
      	        player.movenx();
      	      }
    	      if (e.getKeyCode() == KeyEvent.VK_W) {
      	        player.moveny();
      	      }
    	      if (e.getKeyCode() == KeyEvent.VK_S) {
      	        player.movepy();
      	      }
    	      
    	      
    	    }
    	    
    	    public void keyReleased(KeyEvent e) {
      	      if (e.getKeyCode() == KeyEvent.VK_D) {
      	        player.stop();
      	      }
      	      if (e.getKeyCode() == KeyEvent.VK_A) {
        	        player.stop();
        	      }
      	      if (e.getKeyCode() == KeyEvent.VK_W) {
        	        player.stop();
        	      }
      	      if (e.getKeyCode() == KeyEvent.VK_S) {
        	        player.stop();
        	      }
      	      
      	      
      	    }
    	  });
  	  
	    timer = new Timer(70, e -> {
	    	for (int i = 0; i < zombie.size(); i++) {
				Zombie z = (Zombie) zombie.get(i);
				z.update(WIDTH, HEIGHT);
				z.randmove();
			}
//	    	  zombie.update(WIDTH, HEIGHT);
//	    	  zombie.randmove();
	    	  repaint();
	    	  
	    	});
	    	timer.start();
	    	
	    	
	    	
	}


	@Override
	protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2 = (Graphics2D) g;
	for (int i = 0; i < walls.size(); i++) {
		Wall wall = (Wall) walls.get(i);
		wall.draw(g2);
	}
	for (int i = 0; i < floors.size(); i++) {
		Floor floor = (Floor) floors.get(i);
		floor.draw(g2);
	}
	exit.draw(g2);
	player.draw(g2);
	for (int i = 0; i < zombie.size(); i++) {
		Zombie z = (Zombie) zombie.get(i);
		z.draw(g2);
	}
	
	}
	
//	public GameComponent() {
	

	 
	    
	  

	// Minimal placeholder to test  it’s running
	//g2.drawString("Final Project Starter: UI is running ✅", 20, 30);
	
	



	// TODO: draw based on model state
//	}
//	
//	public GameComponent(String name) {
//		
//
//		
//		
//	}
}
