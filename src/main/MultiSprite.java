package main;

import javax.swing.*;
import java.awt.*;

public class MultiSprite extends Dibujo {
	private Image[] images;
	private int iImage;					//La imagen que está visible

	public MultiSprite(Image[] images, Graphics g) {
		super(images[0].getWidth(null), images[0].getHeight(null), g);
		this.images = images;
		iImage = 0;
	}

	public int getImageIndex() { return iImage; }
	
	public void setImage(int i) {
		if ((i < 0) || (i >= images.length)) { return; }
		iImage = i;
	}
	
	public void nextImage() {
		int i = iImage;
		i++;
		if (i == images.length) { i = 0; }
		setImage(i);
	}
	
	@Override
	public void draw() {
		g.drawImage(images[iImage], (int) getX(), (int) getY(), null);
	}
	
	

}
