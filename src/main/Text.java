package main;

import java.awt.*;

public class Text extends DibujoColor {
	private String text;
	private int size;
	private boolean alignCenter;

	public Text(String text, Color color, int size, boolean alignCenter, Graphics g) {
		super(100, 100, color, g);		// WxH fake!
		this.size = size;
		this.alignCenter = alignCenter;
		setText(text);
	}

	public void setText(String text) {
		this.text = text;
	}


	@Override
	public void draw() {
		Font font = new Font("Courier New", Font.BOLD, size);
		g.setFont(font);
		FontMetrics metrics = g.getFontMetrics();
		double textWidth = metrics.stringWidth(text);
		int x = (int) (getX() + (alignCenter ? - textWidth / 2 : 0));
		int y = (int) getY();
		g.setColor(color);
		g.drawString(text, x, y);
	}

}
