package main;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Star extends Sprite {
    private static final String[] FILENAMES = { "estrella1", "estrella2", "estrella3" };
    private static final Map<Integer,Vector> VEL_MAP = new HashMap<>();
    static {
        // Velocidades
        VEL_MAP.put(0, new Vector(0, 1000 * 3 / (40.0 * G.FPS)));
        VEL_MAP.put(1, new Vector(0, 1000 * 3 / (18.0 * G.FPS)));
        VEL_MAP.put(2, new Vector(0, 1000 * 3 / (14.0 * G.FPS)));
    }


    public Star(int z, Graphics g) {
        super("sprites/estrellas/" + FILENAMES[z] + ".png", g);
        // Posición
        double x = Rnd.get().nextDouble() * (G.WIDTH - getWidth());
        setPos(new Vector(x, 0));
        // Velocidad
        setVel(VEL_MAP.get(z));
    }

	public boolean isBeyond() { return frontera.isBeyondBottom(getY()); }
}
