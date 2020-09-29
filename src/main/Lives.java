package main;

import java.awt.*;

public class Lives implements Dibujable {
    private int numLives;
    private Sprite[] sprites;


    public Lives(Graphics g) {
        numLives = 3;
        double xBase = G.WIDTH - 100;
        sprites = new Sprite[3];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = new Sprite("sprites/vida.png", g);
            sprites[i].setPos(new Vector(xBase + 30 * i, 0));
        }
    }


    public boolean decLives() {
        if (numLives == 0) { return false; }
        numLives--;
        return (numLives > 0);
    }


    public boolean isDeath() { return numLives == 0; }

    @Override
    public void move() { }

    @Override
    public void draw() {
        for (int i = 0; i < numLives; i++) { sprites[i].draw(); }
    }
}
