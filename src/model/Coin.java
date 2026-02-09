package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Coin {
	
	public int x;
	public int y;
	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;
	int height = GameModel.tileSize;
	int width = GameModel.tileSize;
	
	public Coin(int x, int y) {
		this.x = x;
		this.y = y;
		
		loadSpriteOnce();
		
	}
	
	private static void loadSpriteOnce() {
		if (triedLoad) return;
		triedLoad = true;

		try {
		// tennis.png must be in the SAME package as Ball*-.java
		sprite = ImageIO.read(Characterz.class.getResource("Coin.png"));
		} catch (IOException | IllegalArgumentException ex) {
		sprite = null; 
		}
		}
	
	public void draw(Graphics2D g2) {
		if (sprite != null) {
		// sprite replaces the circle
		g2.drawImage(sprite, x, y, width, height, null);
		} else {
		// fallback if sprite failed to load
		g2.setColor(Color.RED);
		g2.fillRect(x, y, width, height);
		}
	}

}
