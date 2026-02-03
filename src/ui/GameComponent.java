package ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.Timer;

import model.Characterz;
import model.GameModel;



public class GameComponent extends JComponent {

	
	
	private GameModel model;
	Characterz player =  new Characterz(100,100);
	private Timer timer;
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;


	public GameComponent(GameModel model) {
	this.model = model;
	}


	@Override
	protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2 = (Graphics2D) g;
	player.draw(g2);
	}
	
	public GameComponent() {
	
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
	  

	// Minimal placeholder to test  it’s running
	//g2.drawString("Final Project Starter: UI is running ✅", 20, 30);
	
	



	// TODO: draw based on model state
	}
}
