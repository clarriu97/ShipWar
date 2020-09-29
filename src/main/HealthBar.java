package main;

import java.awt.*;

public class HealthBar implements Dibujable {
    private static final Color HEALTH_COLOR = Color.BLUE;

    private Rectangulo fondo;
    private Rectangulo bar;

    private Ship ship;              // Alias
    private Graphics g;             // Alias
    private int lastHealth;


    public HealthBar(Ship ship, Graphics g) {
        this.ship = ship;
        this.g = g;
        fondo = new Rectangulo(300, 15, Color.RED, g);
        fondo.setPos(new Vector((G.WIDTH - fondo.getWidth()) / 2.0, 7.5));
        bar = new Rectangulo(300, 15, HEALTH_COLOR, g);
        bar.setPos(fondo.getPos());
        lastHealth = 100;
    }


    public void setShip(Ship ship) {
        this.ship = ship;
    }


    @Override
    public void move() {
        int h = ship.getHealth();
        if (h != lastHealth) {
            bar = new Rectangulo(3 * h, 15, HEALTH_COLOR, g);
            bar.setPos(fondo.getPos());
            lastHealth = h;
        }
    }

    @Override
    public void draw() {
        fondo.draw();
        bar.draw();
    }
}
