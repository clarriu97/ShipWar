package main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;



public class Ship extends Sprite {
	private final int skin;
    private final int power;
    private int health;
    private Vector target;
    protected final Shot.Builder shotBuilder;
    private Flames flames;
    private boolean visible;


    private Ship(int skin, int power, int health, Graphics g) {
        super(getFilename(skin, power), g);
        if (power > getMaxPower()) { power = getMaxPower(); }
		this.power = power;
        this.skin = skin;
		flames = new Flames(false, g);
        shotBuilder = new Shot.Factory(g).createBuilder(this);
        shotBuilder.setPower(power);
        target = new Vector(G.WIDTH / 2.0, G.HEIGHT - getHeight());
        this.health = health;
        visible = true;
    }


    public List<Shot> shoot() {
        List<Shot> shots = new ArrayList<>();
        if (power <= 3 || power >= 8) { shots.add(shotBuilder.setCenter(createCenterShot()).build()); }
        if (power > 3) {
			shots.add(shotBuilder.setCenter(createLeftShot()).build());
			shots.add(shotBuilder.setCenter(createRightShot()).build());
		}
        return shots;
    }

    public int getSkin() { return skin; }

    public int getPower() { return power; }

    public static int getMaxPower() { return 8; }

    protected Vector createCenterShot() { return getCenter(); }

    protected Vector createLeftShot() { return getPos().add(new Vector(0,getHeight())); }

    protected Vector createRightShot() { return getPos().add(new Vector(getWidth(),getHeight())); }

    public void setShipTarget(Vector center) {
        target = center;
    }

    public int getHealth() { return health; }

    public void loseHealth() { health = 0; }

    public void resetHealth() { health = 100; }

    public boolean impact(Shot shot) {
        if (shot.isFromShip()) { return false; }
        health -= shot.getDamage();
        if (health < 0) { health = 0; }
        //System.out.println("Health: " + health);
        return true;
    }


	@Override
	public void setPos(Vector pos) {
		super.setPos(pos);
		Vector offset = new Vector(0, getHeight() / 2.0 + flames.getHeight() / 2.0);
		Vector centerFlames = getCenter().add(offset);
		flames.setCenter(centerFlames);
	}


	@Override
    public void move() {
        super.move();
		Vector vel = target.createCopy().sub(getCenter()).scale(G.VEL_FACTOR_SHIP);
		setVel(vel);
    }


    @Override
    public void draw() {
        if (!visible) { return; }
        super.draw();
        flames.draw();
        flames.nextImage();
    }


    public void setVisible(boolean b) { visible = b; }

    public boolean isVisible() { return visible; }


    // ------------------------------------------------------------------

	private static String getFilename(int type, int power) {
    	if (power > getMaxPower()) { power = getMaxPower(); }
    	if (power <= 0) { power = 1; }
    	return "sprites/nave " + type + "/nave" + type + "-" + power + ".png";
	}



	// ----------------------------------------------------------------------

	public static class Builder implements Dibujable {
		private static final Color COLOR = Color.YELLOW;
		private static final int SIZE = 30;
		private static final double SEP = 60;


		private final Text header;
		private final Ship[] ships;
		private final Text[] numbers;
		private final Text press;

		// Para el build
		private final Graphics g;
		private Ship reference;			// Referencia de skin, pos, vel, power y health (power se autoincrementa)


		public Builder(double y, Graphics g) {
			this.g = g;
			header = new Text("CHOOSE YOUR KILLING MACHINE", COLOR, SIZE, true, g);
			header.setPos(new Vector(G.WIDTH / 2, y));
			ships = new Ship[] {
					new Ship(1, 0, 100, g),
					new Ship(2, 0, 100, g),
					new Ship(3, 0, 100, g),
					new Ship(4, 0, 100, g),
					new Ship(5, 0, 100, g),
					new Ship(6, 0, 100, g),
					new Ship(7, 0, 100, g),
					new Ship(8, 0, 100, g),
			};
			numbers = new Text[ships.length];
			double yBase = header.getCenter().y;
			Vector c = new Vector((G.WIDTH - (ships.length - 1) * SEP) / 2, yBase + 50);
			int i = 0;
			for (Ship ship: ships) {
				numbers[i] = new Text("" + (i+1), Color.YELLOW, SIZE, true, g);
				numbers[i].setPos(new Vector(0,100).add(c));
				ship.setCenter(c);
				c.x += SEP;
				i++;
			}
			press = new Text("PRESS A NUMBER TO START", COLOR, SIZE, true, g);
			press.setPos(new Vector(G.WIDTH / 2, yBase + 300));
			// Builder
			reference = ships[0];
		}



		@Override
		public void move() {

		}


		@Override
		public void draw() {
			header.draw();
			for (Ship ship: ships) { ship.draw(); }
			for (Text n: numbers) { n.draw(); }
			press.draw();
		}


		public Builder setReference(Ship reference) {
			this.reference = reference;
			return this;
		}

		public Builder setChooser(int skin) {
			reference = ships[skin-1];
			return this;
		}

		public Ship build() {
			Ship ship;
			if (reference != null) {
				ship = new Ship(reference.skin, reference.power + 1, reference.health, g);
				ship.setPos(reference.getPos());
				ship.setVel(reference.getVel());
			} else {
				ship = new Ship(1, 1, 100, g);
			}
			return ship;
		}
	}
}
