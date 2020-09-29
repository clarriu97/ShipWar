package main;

import java.util.ArrayList;

public class StarPlane extends ArrayList<Star> implements Dibujable {
    @Override
    public void move() {
        for (Star s: this) { s.move(); }
    }

    @Override
    public void draw() {
        for (Star s: this) { s.draw(); }
    }

    public void removeStars() {
        int l = size();
        for (int i = l-1; i >= 0; i--) {
            if (get(i).isBeyond()) {
                remove(i);
				//System.out.println("star beyond");
            }
        }
    }
}
