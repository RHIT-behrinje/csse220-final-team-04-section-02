package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EXIT {
	
	int x;
	int y;
	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;
	int height = GameModel.tileSize;
	int width = GameModel.tileSize;
	
	public EXIT(int x, int y) {
		this.x = x;
		this.y = y;
		
		loadSpriteOnce();
		
	}
	
	public int getx() {
		return x;
	}
	
	public int gety() {
		return y;
	}
	
	private static void loadSpriteOnce() {
		if (triedLoad) return;
		triedLoad = true;

		try {
		// tennis.png must be in the SAME package as Ball*-.java
		sprite = ImageIO.read(Characterz.class.getResource("portal.png"));
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
	
	public Rectangle boundingBox() {
		Rectangle r = new Rectangle();
		r.x = x;
		r.y = y;
		r.width = width;
		r.height = height;
		return r;
	}

}
