package main;

import java.awt.*;

// Clase "inmutable"
public class Shot extends MultiSprite {
    private static final ShotInfo[] INFOS = {
    		// Power 1,2,3
            new ShotInfo(new String[] { "disparo1-1", "disparo1-2" }, 10),
            new ShotInfo(new String[] { "disparo1-1", "disparo1-2" }, 20),
            new ShotInfo(new String[] { "disparo1-1", "disparo1-2" }, 30),
			// Power 4,5,6,7
            new ShotInfo(new String[] { "disparo4-1", "disparo4-2", "disparo4-3", "disparo4-4" }, 40),
            new ShotInfo(new String[] { "disparo4-1", "disparo4-2", "disparo4-3", "disparo4-4" }, 50),
            new ShotInfo(new String[] { "disparo4-1", "disparo4-2", "disparo4-3", "disparo4-4" }, 60),
            new ShotInfo(new String[] { "disparo4-1", "disparo4-2", "disparo4-3", "disparo4-4" }, 70),
			// Power 8
            new ShotInfo(new String[] { "disparo1", "disparo2", "disparo3", "disparo4" }, 80),
			// Enemigos
            new ShotInfo(new String[] { "disparoenemigo1-1", "disparoenemigo1-2" }, 1),
            new ShotInfo(new String[] { "disparoenemigo1-1", "disparoenemigo1-2" }, 2),
            new ShotInfo(new String[] { "disparoenemigo1-1", "disparoenemigo1-2" }, 4),
            new ShotInfo(new String[] { "disparoenemigo1-1", "disparoenemigo1-2" }, 8),
            new ShotInfo(new String[] { "disparoenemigo1-1", "disparoenemigo1-2" }, 16),
    };


    public final boolean fromShip;
    public final int damage;


    private Shot(Image[] images, boolean fromShip, int damage, Vector center, Graphics g) {
        super(images, g);
        this.fromShip = fromShip;
        this.damage = damage;
        setCenter(center);
        setVel(fromShip ? G.VEL_SHOT_SHIP : G.VEL_SHOT_ENEMY);
    }


    public boolean isFromShip() { return fromShip; }

    public int getDamage() { return damage; }

    @Override
    public void move() {
        super.move();
        nextImage();
    }

    public boolean isOpposite(Shot s) { return this.fromShip ^ s.fromShip; }

	public boolean isBeyond() { return fromShip ? frontera.isBeyondTop(getBottom()) : frontera.isBeyondBottom(getY()); }


    // ---------------------------------------------------------------

    private static class ShotInfo {
        public String[] filenames;
        public int damage;

        public ShotInfo(String[] filenames, int damage) {
            this.filenames = filenames;
            this.damage = damage;
        }
    }


    public interface Builder {
        Builder setCenter(Vector center);
        Builder setPower(int power);
        Shot build();
    }


    public static class ShipBuilder implements Builder {
        private final Graphics g;
        private int power;
        private Vector center;

        public ShipBuilder(Graphics g) {
            power = 1;
            this.g = g;
            center = new Vector();
        }

        @Override
        public Builder setCenter(Vector center) { this.center = center.createCopy(); return this; }

        @Override
        public Builder setPower(int power) { this.power = power; return this; }

        @Override
        public Shot build() {
            ShotInfo info = INFOS[power-1];
            Image[] images = ImageTools.readImages("disparos", info.filenames);
            return new Shot(images, true, info.damage, center, g);
        }
    }


    public static class EnemyBuilder implements Builder {
        private final Graphics g;
        // ---
        private Vector center;
        private int power;

        public EnemyBuilder(Graphics g) {
            this.g = g;
            center = new Vector();
            power = 1;
        }

        @Override
        public Builder setPower(int power) { this.power = power; return this; }

		@Override
        public Builder setCenter(Vector center) { this.center = center.createCopy(); return this; }

        @Override
        public Shot build() {
            ShotInfo info = INFOS[8 + power - 1];
            Image[] images = ImageTools.readImages("disparos", info.filenames);
            return new Shot(images, false, info.damage, center, g);
        }
    }



    public static class Factory {
        private final Graphics g;

        public Factory(Graphics g) {
            this.g = g;
        }

        public Builder createBuilder(Dibujo d) {
            if (d instanceof Ship) { return new ShipBuilder(g); }
            if (d instanceof Enemy) { return new EnemyBuilder(g); }
            return null;
        }
    }

}
