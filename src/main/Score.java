package main;

import java.awt.*;

public class Score extends Text {
    private static final Color SCORE_COLOR = Color.YELLOW;

    private int puntuacion;

    public Score(Graphics g) {
        super("0000000", SCORE_COLOR, 20, false, g);
        puntuacion = 0;
        setPos(new Vector(25,20));
    }


    public void add(int score) {
        this.puntuacion += score;
        setText(String.format("%07d", this.puntuacion));
    }

    public int getScore() { return puntuacion; }
}
