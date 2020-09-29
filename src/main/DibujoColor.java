package main;

import java.awt.Color;
import java.awt.Graphics;

public abstract class DibujoColor extends Dibujo {
	protected Color color;
	
	public DibujoColor(double width, double height, Color color, Graphics g) {
		super(width, height, g);
		this.color = color;
	}

	public Color getColor() { return color; }
	
	public void setColor(Color color) { this.color = color; }
}
