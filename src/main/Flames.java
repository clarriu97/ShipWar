package main;

import java.awt.*;

public class Flames extends MultiSprite {
    private static final String[] FILENAMES_SHIP = { "fuego1", "fuego2", "fuego3", "fuego4", "fuego5", "fuego6", "fuego7", "fuego8" };
    private static final String[] FILENAMES_ENEMY = { "fuegoenemigo1", "fuegoenemigo2", "fuegoenemigo3", "fuegoenemigo4", "fuegoenemigo5", "fuegoenemigo6", "fuegoenemigo7", "fuegoenemigo8" };

    public Flames(boolean enemy, Graphics g) {
        super(ImageTools.readImages("fuegotrasero", enemy ? FILENAMES_ENEMY : FILENAMES_SHIP), g);
    }
}
