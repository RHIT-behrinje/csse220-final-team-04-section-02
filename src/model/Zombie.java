package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import ui.GameComponent;

public class Zombie {
	
	public int x;
	public int y;
	int dx = 0;
	int dy = 0;
	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;
	int height = GameModel.tileSize;
	int width = GameModel.tileSize;
	
	public Zombie(int x, int y) {
		this.x = x;
		this.y = y;
		
		loadSpriteOnce();
		}
	
	private static void loadSpriteOnce() {
		if (triedLoad) return;
		triedLoad = true;

		try {
			// tennis.png must be in the SAME package as Ball*-.java
			sprite = ImageIO.read(Characterz.class.getResource("Zombies.png"));
		} catch (IOException | IllegalArgumentException ex) {
			sprite = null; 
		}
	}
	
	public void draw(Graphics2D g2) {
		


		if (sprite != null) {
		// sprite replaces the circle
		g2.drawImage(sprite, x, y, width, height, null);
		//Bounding box
		g2.setColor(Color.black);
		g2.draw(boundingBox());
		} else {
		// fallback if sprite failed to load
		g2.setColor(Color.RED);
		g2.fillOval(x, y, width, height);
		}
		}
	public void randmove() {
		double r = Math.random();

		if (r >= 0 && r < 0.2){
			movenx();
		}
		if (r >= 0.2 && r < 0.4){
			movepx();
		}
		if (r >= 0.4 && r < 0.6){
			moveny();
		}
		if (r >= 0.6 && r < 0.8){
			movepy();
		}
		if (r >= 0.8 && r < 1.0){
			stop();
		}
	}
	public void stop() {
		this.dy = 0;
		this.dx = 0;
	}
	public void movepx() {
		this.dx = GameModel.tileSize;
	}
	public void movenx() {
		this.dx = -GameModel.tileSize;
	}
	public void movepy() {
		this.dy = GameModel.tileSize;
	}
	public void moveny() {
		this.dy = -GameModel.tileSize;
	}
	
	public void update(int worldWidth, int worldHeight) {
		if(GameComponent.wallTest(x + dx, y + dy)) {
		x = x + dx;
		y = y + dy;
		}




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
		else if (y + height> worldHeight) {
			y = worldHeight - height;
			dy = -dy;

		}
	}
	
	public void revert(int oldX, int oldY) {
		x = oldX;
		y = oldY;
		dx = 0;
		dy = 0;
		//dx = - 30;
		//dy = -dy;
	}
	
	public Rectangle boundingBox() {
		Rectangle r = new Rectangle();
		r.x = x ;
		r.y = y ;
		r.width = width;
		r.height = height;
		return r;
	}
	

}
