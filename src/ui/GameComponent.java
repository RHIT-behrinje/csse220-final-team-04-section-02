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
	Characterz player = new Characterz(0,0);
	EXIT exit;

	private GameModel model;
	public int tileSize;
	public Runnable gameover;
	public Runnable Win;
	private Timer timer;
	public static final int WIDTH = 20 * GameModel.tileSize;
	public static final int HEIGHT = 20 * GameModel.tileSize;
	private int fileWidth, fileHeight, worldWidth, worldHeight;
	ArrayList<Zombie> zombie;
	static ArrayList<Wall> walls;
	ArrayList<Floor> floors;
	ArrayList<Coin> coins;
	private Font mainFont;
	boolean alive = true;
	boolean win = false;
	int level = 1;
	int coincount;
	

	public GameComponent(GameModel model, int num, Runnable death, Runnable win) {
		this.model = model;
		this.gameover = death;
		this.Win = win;
		tileSize = GameModel.tileSize;
		mainFont = new Font("Verdana", Font.BOLD, 16);
		

		loadLevel(num);
		worldWidth = tileSize * fileWidth;
		worldHeight = tileSize * fileHeight;
		setPreferredSize(new Dimension(worldWidth, worldHeight));


//		timer = new Timer(70, e -> {
//			player.update(WIDTH, HEIGHT);
//			repaint();
//		});
////		 timer.start();
		
		//setFocusable(true);
				
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				//System.out.println("clicked");
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
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					System.out.println("launch");
				}
			}

			public void keyReleased(KeyEvent e) {
				//System.out.println("realeased");
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
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					System.out.println("stop");
				}

			}
		});

		timer = new Timer(100, e -> {
			// Zombie collision and movement
			
			player.update(WIDTH, HEIGHT);
			repaint();
			
			for (int i = 0; i < zombie.size(); i++) {
				Zombie z1 = zombie.get(i);
				int tempX = z1.x;
				int tempY = z1.y;
				z1.randmove();
				for (int j = i + 1; j < zombie.size(); j++) {
					Zombie z2 = zombie.get(j);
					//z1.randmove();
					if (z1.boundingBox().intersects(z2.boundingBox())) {
						z1.x = tempX;
						z1.y = tempY;
					}
				}
				z1.update(worldWidth, worldHeight);
				if (z1.boundingBox().intersects(player.boundingBox()))
					player.handleKilled();
			}

			// Coin stuff (these variables names are about to get PG-13 if things don't
			// start working)
			for (int i = 0; i < coins.size(); i++) {
				Coin c = coins.get(i);
				if (c.boundingBox().intersects(player.boundingBox())) {
					player.handleScore();
					coins.remove(i);
				}
			}
			if (player.lives <= 0) {
				stopgame();
				System.out.println("died");
				gameover.run();
				return;
			}
			
			if ( player.boundingBox().intersects(exit.boundingBox()) && coincount == player.score) {
				level ++;
				loadLevel(level);
				System.out.println(level);
			}
			
			repaint();
			

			

		});
		// timer.start();

	}

	public void startGame() {
		timer.start();
	}
	
	public void stopgame() {
		timer.stop();
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
		g2.setColor(Color.BLACK);
		g2.drawString("Score: " + player.score, 4, 16);
		g2.drawString("Lives: " + player.lives, 4, 32);
	}

	public static boolean wallTest(int x, int y) {
		for (int i = 0; i < walls.size(); i++) {
			Wall wall = (Wall) walls.get(i);
			if (wall.x == x && wall.y == y) {
				return false;
			}
		}
		return true;
	}
	
	public int GetLives() {
		return player.lives;
	}
	
	public int Launchable() {
		
		return zombie.size() + 1;
	}
	

	private void loadLevel(int num) {
		if (num < 3) {
		File file = model.getlevel(num);
		coins = new ArrayList<Coin>();
		walls = new ArrayList<Wall>();
		floors = new ArrayList<Floor>();
		zombie = new ArrayList<Zombie>();

		try {
			Scanner scanner = new Scanner(file);
			
			

			int row = 0;

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				fileWidth = line.length();

				for (int col = 0; col < line.length(); col++) {
					char c = line.charAt(col);

					if (c == 'P') {
						player.x = col * GameModel.tileSize;
						player.y = row * GameModel.tileSize;
						player.startX = col * GameModel.tileSize;
						player.startY = row * GameModel.tileSize;

					}
					if (c == 'Z') {
						zombie.add(new Zombie(col * GameModel.tileSize, row * GameModel.tileSize));
					}

					if (c == '*') {
						walls.add(new Wall(col * GameModel.tileSize, row * GameModel.tileSize));
					}
					// stuff below this is before pull
					if (c == '.' || c == 'Z' || c == 'P' || c == 'C') {
						floors.add(new Floor(col * GameModel.tileSize, row * GameModel.tileSize));
					}
					if (c == 'E') {
						exit = new EXIT(col * GameModel.tileSize, row * GameModel.tileSize);
					}
					if (c == 'C') {
						coins.add(new Coin(col * GameModel.tileSize, row * GameModel.tileSize));
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
		else {
			Win.run();
		}
		coincount = coincount + coins.size();
		
	}

}
