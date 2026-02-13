package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Characterz;



public class Startscreen extends JPanel {

	static JButton start;
	
	private Image backgroundImage;


	public Startscreen() {

		setLayout(new BorderLayout());

		start = new JButton("Start");
		

		add(start, BorderLayout.SOUTH);
				
		
		try {
            // Load the image using ImageIO
            backgroundImage = ImageIO.read(Startscreen.class.getResource("startnew.png"));
        } catch (IOException e) {
            System.err.println("Failed to load image: " + "startnew.png");
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
