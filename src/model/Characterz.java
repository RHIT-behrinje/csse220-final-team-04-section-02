package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;



public class Characterz {
	
	int x;
	int y;
	int dx = 0;
	int dy = 0;
	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;
	int height = 96;
	int width = 64;
	
	public Characterz(int x, int y) {
		this.x = x;
		this.y = y;
		
		loadSpriteOnce();
		}
	
	private static void loadSpriteOnce() {
		if (triedLoad) return;
		triedLoad = true;

		try {
		// tennis.png must be in the SAME package as Ball*-.java
		sprite = ImageIO.read(Characterz.class.getResource("Minecraft character.png"));
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
		g2.fillOval(x, y, width, height);
		}
		}
	public void stop() {
		this.dy = 0;
		this.dx = 0;
	}
	public void movepx() {
		this.dx = 20;
	}
	public void movenx() {
		this.dx = -20;
	}
	public void movepy() {
		this.dy = 20;
	}
	public void moveny() {
		this.dy = -20;
	}
	
	public void update(int worldWidth, int worldHeight) {
		x = x + dx;
		y = y + dy;


	


	// Left wall
	if (x < 0) {
	x = 0; // clamp
	dx = -dx;
	}
	// Right wall
	else if (x + width > worldWidth) {
	x = worldWidth - width;
	dx = -dx;
	}


	// Top wall
	if (y < 0) {
	y = 0;
	dy = -dy;
	}
	// Bottom wall
	else if (y + height > worldHeight) {
	y = worldHeight - height;
	dy = -dy;

	}
	}


}
