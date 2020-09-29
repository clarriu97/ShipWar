package main;

// Singleton
public class Frontera {
	private static Frontera singleton = new Frontera();
	public static Frontera get() { return singleton; }
	// ---

	private Frontera() { }

	public Vector getCenter() { return new Vector(G.WIDTH/2, G.HEIGHT/2); }

	public boolean isBeyondLeft(double x) { return x < 0; }
	public boolean isBeyondRight(double x) { return x >= G.WIDTH; }
	public boolean isBeyondTop(double y) { return y < 0; }
	public boolean isBeyondBottom(double y) { return y >= G.HEIGHT; }

	public boolean isInsideH(double x) { return !isBeyondLeft(x) && !isBeyondRight(x); }
	public boolean isInsideV(double y) { return !isBeyondTop(y) && !isBeyondBottom(y); }
	public boolean isInside(double x, double y) { return isInsideH(x) && isInsideV(y); }

	public boolean isOutsideH(double x) { return !isInsideH(x); }
	public boolean isOutsideV(double y) { return !isInsideV(y); }
	public boolean isOutside(double x, double y) { return isOutsideH(x) && isOutsideV(y); }


}
