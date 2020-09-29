package main;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public abstract class Dibujo implements Dibujable {
	protected static final Frontera frontera = Frontera.get();
	private Vector pos, vel, acel;
	private Rectangle2D.Double bounds;	// Redundante con pos. Cada vez que cambie pos, cambiar bounds
	protected Graphics g;

	
	public Dibujo(double width, double height, Graphics g) {
		pos = new Vector();
		vel = new Vector();
		acel = new Vector();
		bounds = new Rectangle2D.Double(pos.x,pos.y,width,height);
		this.g = g;
	}
	
	// --------------------------------------------------------
	
	public Vector getPos() { return pos.createCopy(); }		// Copia defensiva para no liarla en el examen
	
	public Vector getVel() { return vel.createCopy(); }		// Copia defensiva para no liarla en el examen
	
	public Vector getAcel() { return acel.createCopy(); }	// Copia defensiva para no liarla en el examen
	
	public double getX() { return pos.x; }
	
	public double getY() { return pos.y; }
	
	public double getWidth() { return bounds.getWidth(); }
	
	public double getHeight() {	return bounds.getHeight(); }

	public double getRight() { return getX() + getWidth(); }

	public double getBottom() { return getY() + getHeight(); }

	public Vector getCenter() {
		return new Vector(getX() + getWidth() / 2.0, getY() + getHeight() / 2.0);
	}

	
	// FullyInside: el dibujo esta totalmente dentro
	// FullyOutside: el dibujo esta totalmente fuera
	//	
	// Inside: el dibujo esta parcial o totalmente dentro
	// Outside: el dibujo esta parcial o totalmente fuera
	//
	public boolean isFullyInsideH() { return frontera.isInsideH(getX()) && frontera.isInsideH(getRight()); }
	public boolean isFullyInsideV() { return frontera.isInsideV(getY()) && frontera.isInsideV(getBottom()); }
	public boolean isFullyInside() { return isFullyInsideH() && isFullyInsideV(); }
	
	public boolean isFullyOutsideH() { return frontera.isOutsideH(getX()) && frontera.isOutsideH(getRight()); }
	public boolean isFullyOutsideV() { return frontera.isOutsideV(getY()) && frontera.isOutsideV(getBottom()); }
	public boolean isFullyOutside() { return isFullyOutsideH() && isFullyOutsideV(); }

	public boolean isInsideH() { return frontera.isInsideH(getX()) || frontera.isInsideH(getRight()); }
	public boolean isInsideV() { return frontera.isInsideV(getY()) || frontera.isInsideV(getBottom()); }
	public boolean isInside() { return isInsideH() || isInsideV(); }

	public boolean isOutsideH() { return frontera.isOutsideH(getX()) || frontera.isOutsideH(getRight()); }
	public boolean isOutsideV() { return frontera.isOutsideV(getY()) || frontera.isOutsideV(getBottom()); }
	public boolean isOutside() { return isOutsideH() || isOutsideV(); }



	// --------------------------------------------------------
	
	public void setPos(Vector pos) {
		this.pos = pos.createCopy();		// Copia defensiva para no liarla en el examen
		bounds.x = pos.x;
		bounds.y = pos.y;
	}
	
	public void setVel(Vector vel) {
		this.vel = vel.createCopy();		// Copia defensiva para no liarla en el examen
	}
	
	public void setAccel(Vector acel) { this.acel = acel.createCopy(); }	// Copia defensiva para no liarla en el examen
	
	public void setCenter(Vector center) {
		double dx = getWidth() / 2.0;
		double dy = getHeight() / 2.0;
		Vector offset = new Vector(dx, dy);
		setPos(center.createCopy().sub(offset));
	}
	
	@Override
	public void move(){
		vel.add(acel);
		setPos(pos.add(vel));
	}
	
	public boolean hasCollision(Dibujo dibujo) {
		return (bounds.intersects(dibujo.bounds));
	}
	

}
