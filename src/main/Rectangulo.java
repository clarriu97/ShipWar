package main;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangulo extends DibujoColor {

	public Rectangulo(double width, double height, Color color, Graphics g) {
		super(width, height, color, g);
	}
	
	@Override
	public void draw() {
		g.setColor(color);
		g.fillRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
	}
}
