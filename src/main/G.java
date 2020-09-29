package main;



public class G {
	public static final boolean TEST = false;

	// De la animacion
	public static final int FPS = 40;

	// De la imagen (buffer)
	public static final int WIDTH = 600;
	public static final int HEIGHT = 900;
	public static final int EXTRA_WIDTH = 6;
	public static final int EXTRA_HEIGHT = 26;

	// Velocidades
	public static final Vector VEL_ENEMY = new Vector(0, 100 / FPS);
	public static final Vector VEL_SHOT_SHIP = new Vector(0, - 500 / FPS);
	public static final Vector VEL_SHOT_ENEMY = VEL_SHOT_SHIP.createCopy().reverse();
	public static final double VEL_FACTOR_SHIP = 2.0 / FPS;
}
