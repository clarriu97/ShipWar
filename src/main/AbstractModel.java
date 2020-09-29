package main;

import java.awt.Graphics;

public abstract class AbstractModel {
	protected Graphics g;
	
	public AbstractModel (Graphics g){
		this.g = g;
	}
	
	public void updateAndDraw(){
		update();
		draw();
	}
	
	protected abstract void update();
	protected abstract void draw();	
}
