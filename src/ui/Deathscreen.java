package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Deathscreen extends JPanel{
	
	
	private Image backgroundImage;
	
	public Deathscreen() {
		
		try {
            // Load the image using ImageIO
            backgroundImage = ImageIO.read(Deathscreen.class.getResource("skulls.png"));
        } catch (IOException e) {
            System.err.println("Failed to load image: " + "skulls.png");
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        // This call is crucial: it clears the background and ensures proper painting
        super.paintComponent(g); 

        // Draw the background image, scaled to fill the entire panel
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
	}

}
