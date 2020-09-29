package main;

import java.awt.*;

public class Enemy extends Sprite {
	public static final double[] PROB_SHOT = { 0.002, 0.004, 0.006, 0.008, 0.012 };
	public static final int ENEMIES_PER_LEVEL = G.TEST ? 3 : 30;

    private static final EnemyInfo[] INFOS = {
        new EnemyInfo("enemigo01", 10),
        new EnemyInfo("enemigo02", 50),
        new EnemyInfo("enemigo03", 150),
        new EnemyInfo("enemigo04", 750),
        new EnemyInfo("enemigo05", 2500),
    };

    public static final int[] LEVEL_SCORES = new int[] {
    		INFOS[0].health * ENEMIES_PER_LEVEL,
    		INFOS[1].health * ENEMIES_PER_LEVEL,
    		INFOS[1].health * ENEMIES_PER_LEVEL,
    		INFOS[2].health * ENEMIES_PER_LEVEL,
    		INFOS[2].health * ENEMIES_PER_LEVEL,
    		INFOS[3].health * ENEMIES_PER_LEVEL,
    		INFOS[3].health * ENEMIES_PER_LEVEL,
    		INFOS[4].health * ENEMIES_PER_LEVEL,
	};
    static {
    	// Acumulamos los puntos necesarios para cambio de nivel
    	int total = 0;
    	for (int i = 0; i < LEVEL_SCORES.length; i++) {
    		total += LEVEL_SCORES[i];
    		LEVEL_SCORES[i] = total;
		}
	}


    private final Shot.Builder shotBuilder;
    private final int power;            // [1,5]
    private int health;                 // 0 -> ...
    private int score;
	private Flames flames;


    private Enemy(int power, int health, String filename, Graphics g) {
        super(filename, g);
        this.power = power;
        this.health = health;
        score = health;

        // Posición
		flames = new Flames(true, g);
        double x = Rnd.get().nextDouble() * (G.WIDTH - getWidth()) + getWidth() / 2.0;
        setCenter(new Vector(x,-getHeight()));
        // Velocidad
        setVel(G.VEL_ENEMY);

        // Shot Builder
        shotBuilder = new Shot.Factory(g).createBuilder(this);
        shotBuilder.setPower(power);
    }


	@Override
	public void setPos(Vector pos) {
		super.setPos(pos);
		Vector offset = new Vector(0, - getHeight() / 2.0 - flames.getHeight() / 2.0);
		Vector centerFlames = getCenter().add(offset);
		flames.setCenter(centerFlames);
	}


	@Override
	public void draw() {
		super.draw();
		flames.draw();
		flames.nextImage();
	}


    public Shot tryToShoot() {
        Shot shot = null;
        if (Rnd.get().nextDouble() < PROB_SHOT[power -1]) {
            shot = shotBuilder.setCenter(getCenter()).build();
        }
        return shot;
    }

    public boolean impact(Shot shot) {
        if (!shot.isFromShip()) { return false; }
        health -= shot.getDamage();
        if (health < 0) { health = 0; }
        return true;
    }

    public boolean isDeath() { return health == 0; }

    public int getScore() { return score; }

	public boolean isBeyond() { return frontera.isBeyondBottom(getY()); }


    // ---------------------------------------------------------------

    private static class EnemyInfo {
        public String filename;
        public int health;

        public EnemyInfo(String filename, int health) {
            this.filename = filename;
            this.health = health;
        }
    }


    public static class Builder {
        private Graphics g;
        private int power;

        public Builder(Graphics g) {
            this.g = g;
            power = 1;
        }

        public Builder setPower(int power) {
            if (power > 5) { return this; }
            this.power = power;
            return this;
        }

        public Enemy build() {
            EnemyInfo info = INFOS[power -1];
            String filename = "sprites/enemigos/" + info.filename + ".png";
            return new Enemy(power, info.health, filename, g);
        }


    }
}
