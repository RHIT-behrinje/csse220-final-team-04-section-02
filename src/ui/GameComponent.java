package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import model.Coin;
import model.EXIT;
import model.Floor;
import model.GameModel;
import model.Wall;
import model.Zombie;



public class GameComponent extends JComponent {
	static Characterz player;
	EXIT exit;
	
	private GameModel model;
	public int tileSize;
	private Timer timer;
	public static final int WIDTH = 20*GameModel.tileSize;
	public static final int HEIGHT = 20*GameModel.tileSize;
	private int fileWidth , fileHeight , worldWidth, worldHeight;
	ArrayList<Zombie> zombie = new ArrayList<Zombie>();
	static ArrayList<Wall> walls = new ArrayList<Wall>();
	ArrayList<Floor> floors = new ArrayList<Floor>();
	ArrayList<Coin> coins = new ArrayList<Coin>();
	private Font mainFont;
	


	public GameComponent(GameModel model) {
	this.model = model;
	tileSize = GameModel.tileSize;
	mainFont = new Font("Verdana", Font.BOLD, 16);
	
	loadLevel();
	worldWidth = tileSize * fileWidth;
	worldHeight = tileSize * fileHeight;
	setPreferredSize(new Dimension(worldWidth, worldHeight));
	
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
	    	//Zombie collision and movement
	    	for (int i = 0; i < zombie.size(); i++) {
				Zombie z1 = zombie.get(i);
				int tempX = z1.x;
				int tempY = z1.y;
				for (int j = i+1; j < zombie.size(); j++) {
					Zombie z2 = zombie.get(j);
					z1.randmove();
					if(z1.boundingBox().intersects(z2.boundingBox())) {
						z1.revert(tempX, tempY);
					}
				}
				z1.update(worldWidth, worldHeight);
				if(z1.boundingBox().intersects(player.boundingBox())) player.handleKilled();
	    	}
	    	
	    	//Coin stuff (these variables names are about to get PG-13 if things don't start working)
	    	for(int i = 0; i < coins.size(); i++) {
	    		Coin c = coins.get(i);
	    		if(c.boundingBox().intersects(player.boundingBox())) {
	    			player.handleScore();
	    			coins.remove(i);
	    		}
	    	}

	    	  repaint();
	    	  
	    	});
	    	timer.start();
	    	
	    	
	    	
	}


	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(mainFont);
		
		for (int i = 0; i < walls.size(); i++) {
			Wall wall = (Wall) walls.get(i);
			wall.draw(g2);
		}
		for (int i = 0; i < floors.size(); i++) {
			Floor floor = (Floor) floors.get(i);
			floor.draw(g2);
		}
		for (int i = 0; i < coins.size(); i++) {
			Coin coin = (Coin) coins.get(i);
			coin.draw(g2);
		}
		exit.draw(g2);
		player.draw(g2);
		for (int i = 0; i < zombie.size(); i++) {
			Zombie z = (Zombie) zombie.get(i);
			z.draw(g2);
		}
		g2.setColor(Color.black);
		g2.drawString("Score: " + player.score, 4, 16);
		g2.drawString("Lives: " + player.lives, 4, 32);
	}
	public static boolean wallTest(int x,int y) {
		for(int i = 0; i < walls.size(); i++) {
			Wall wall = (Wall) walls.get(i);
			if(wall.x == x && wall.y == y) {
				return false;
			}
		}
		return true;
	}
	
	private void loadLevel() {
		File file = new File("level1.txt"); 


		try {
			Scanner scanner = new Scanner(file);

			int row = 0;

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				fileWidth = line.length();

				for (int col = 0; col < line.length(); col++) {
					char c = line.charAt(col);

					if (c == 'P') {
						player =  new Characterz(col * GameModel.tileSize,row*GameModel.tileSize);

					}
					if (c == 'Z') {
						zombie.add(new Zombie(col * GameModel.tileSize,row*GameModel.tileSize));
					}

					if (c == '*') {
						walls.add(new Wall(col * GameModel.tileSize,row*GameModel.tileSize));
					}
					//stuff below this is before pull
					if (c == '.' || c == 'Z' || c == 'P'|| c == 'C') {
						floors.add(new Floor(col * GameModel.tileSize,row*GameModel.tileSize));
					}
					if (c == 'E') {
						exit =  new EXIT(col * GameModel.tileSize,row*GameModel.tileSize);
					}
					if (c == 'C') {
						coins.add(new Coin(col * GameModel.tileSize,row*GameModel.tileSize));
					}


				}

				row++;
			}
			fileHeight = row;

			scanner.close();


		} catch (FileNotFoundException e) {
			System.out.println("level1.txt not found");
		}
	}
	


}
