package main;

import java.awt.*;

public class Explosion extends MultiSprite {
    private static final String[] FILENAMES = { "explosion-01", "explosion-02", "explosion-03", "explosion-04", "explosion-05", "explosion-06", "explosion-07" };
    private static final String[] SIMPLE_FILENAMES = { "explosion-01", "explosion-07" };

    private boolean readyToFinish;

    public Explosion(Vector center, boolean simple, Graphics g) {
        super(ImageTools.readImages("explosion", simple ? SIMPLE_FILENAMES : FILENAMES), g);
        readyToFinish = false;
        setCenter(center);
    }


    public Explosion(Vector center, Graphics g) {
        this(center, false, g);
    }

    @Override
    public void setImage(int i) {
        super.setImage(i);
        if (i > 0) { readyToFinish = true; }
    }


    public boolean hasFinished() {
        return readyToFinish && getImageIndex() == 0;
    }
}