package main;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;


public class Sprite extends Dibujo {
	private Image image;
	
	public Sprite(String filename, Graphics g) {
		super(new ImageIcon(filename).getIconWidth(), new ImageIcon(filename).getIconHeight(), g);
		image = new ImageIcon(filename).getImage();
	}
	
	@Override
	public void draw() {
		g.drawImage(image, (int) getX(), (int) getY(), null);
	}
}