package main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static main.G.HEIGHT;
import static main.G.WIDTH;


public class Model extends AbstractModel {
	private static final int[] MAX_ENEMY_POWER = { 1, 2, 2, 3, 3, 4, 4, 5 };	// En funcion del power del ship
	public static final int NUM_STAR_PLANES = 3;
	public static final double PROB_STAR = 2;
	public static final double PROB_ENEMY = G.TEST ? 0.1 : 0.02;



	private static final int STATE_DISPLAY = 0;
	private static final int STATE_PLAY = 1;
	private static final int STATE_GAMEOVER = 2;

	// ---------------------------------------------------

	private final Rectangulo fondo;
	private final Rectangulo statusBar;
	private final StarPlane[] starPlanes;
	private final List<Enemy> enemies;
	private final Enemy.Builder enemyBuilder;
	private Ship ship;
	private final List<Shot> shots;
	private final HealthBar healthBar;
	private final Lives lives;
	private final List<Explosion> exp;
	private final Score score;
	private final Ship.Builder shipBuilder;
	private final Text gameover;

	private int state;

	// Working fields
	private List<Shot> shotsToRemove = new ArrayList<>();
	private List<Enemy> enemiesToRemove = new ArrayList<>();




	public Model(Graphics g) {
		super(g);
		fondo = new Rectangulo(WIDTH, HEIGHT, Color.BLACK, g);
		statusBar = new Rectangulo(WIDTH, 30, new Color(0,0,80), g);
		starPlanes = new StarPlane[] {
				new StarPlane(),
				new StarPlane(),
				new StarPlane(),
		};
		enemies = new ArrayList<>();
		enemyBuilder = new Enemy.Builder(g);
		shots = new ArrayList<>();
		lives = new Lives(g);
		exp = new ArrayList<>();
		score = new Score(g);
		shipBuilder = new Ship.Builder(150, g);
		ship = shipBuilder.build();		// Dummy
		healthBar = new HealthBar(ship, g);
		gameover = new Text("GAME OVER", Color.YELLOW, 50, true, g);
		gameover.setPos(new Vector(G.WIDTH / 2.0, G.HEIGHT / 2.0));
		state = STATE_DISPLAY;
	}
	
	
	@Override
	public void update() {
		removeStars();
		removeEnemies();
		removeShots();
		removeBooms();

		addNewStars();
		if (state == STATE_PLAY) { addNewEnemies(); }
		addShots();

		for (StarPlane p: starPlanes) { p.move(); }
		for (Enemy e: enemies) { e.move(); }
		for (Shot s: shots) { s.move(); }
		for (Explosion b: exp) { b.nextImage(); }

		ship.move();
		healthBar.move();

		checkCollisions();
	}
	


	@Override
	public void draw() {
		fondo.draw();
		for (StarPlane p: starPlanes) { p.draw(); }
		for (Shot s: shots) { s.draw(); }
		if (state != STATE_DISPLAY) { ship.draw(); }
		for (Enemy e: enemies) { e.draw(); }
		for (Explosion b: exp) { b.draw(); }
		statusBar.draw();
		score.draw();
		healthBar.draw();
		lives.draw();
		if (state == STATE_DISPLAY) { shipBuilder.draw(); }
		if (state == STATE_GAMEOVER) { gameover.draw(); }
	}


	public void play(int skin) {
		if (state != STATE_DISPLAY) { return; }
		state = STATE_PLAY;
		ship = shipBuilder.setChooser(skin).build();
	}


	public void setShipTarget(Vector center) {
		ship.setShipTarget(center);
	}


	public void shipShoot() {
		if (state != STATE_PLAY) { return; }
		List<Shot> shots = ship.shoot();
		this.shots.addAll(shots);
	}


	public void nextShip() {
		if (ship.getPower() == Ship.getMaxPower()) { return; }
		ship = shipBuilder.setReference(ship).build();
		healthBar.setShip(ship);
	}


	private void checkCollisions() {
		if (state == STATE_PLAY) {
			checkShotsAndSpaceships();
			checkBetweenSpaceships();
			checkBetweenShots();
			checkHealth();
		}
	}

	// -------------------------------------------------------------------------------------

	private void checkShotsAndSpaceships() {
		boolean impacto;

		// Primero chequear impactos e instanciar explosiones
		for (Shot shot: shots) {

			// ¿Impacto con nuestra nave?
			if (ship.hasCollision(shot)) {
				impacto = ship.impact(shot);
				if (impacto) {
					shotsToRemove.add(shot);
					exp.add(new Explosion(shot.getCenter(), g));
				}
			}

			// ¿Impacto con enemigos?
			for (Enemy enemy: enemies) {
				if (enemy.hasCollision(shot)) {
					impacto = enemy.impact(shot);
					if (impacto) {
						shotsToRemove.add(shot);
						if (enemy.isDeath()) {
							score.add(enemy.getScore());
							enemiesToRemove.add(enemy);
						}
						exp.add(new Explosion(enemy.getCenter(), !enemy.isDeath(), g));
					}
				}
			}
		}


	}


	private void checkBetweenSpaceships() {
		for (Enemy enemy: enemies) {
			if (enemy.hasCollision(ship)) {
				enemiesToRemove.add(enemy);
				exp.add(new Explosion(enemy.getCenter(), g));
				ship.loseHealth();
			}
		}
	}


	private void checkBetweenShots() {
		for (int i = 0; i < shots.size(); i++) {
			Shot a = shots.get(i);
			for (int j = i+1; j < shots.size(); j++) {
				Shot b = shots.get(j);
				if (a.hasCollision(b) && a.isOpposite(b)) {
					exp.add(new Explosion(a.getCenter(), g));
					shotsToRemove.add(a);
					shotsToRemove.add(b);
				}
			}
		}
	}


	private void checkHealth() {
		// Health & Lives & Power
		if (ship.getHealth() == 0 && ship.isVisible()) {
			boolean alive = lives.decLives();
			if (alive) {
				ship.resetHealth();
			} else {
				exp.add(new Explosion(ship.getCenter(), g));
				ship.setVisible(false);
				state = STATE_GAMEOVER;
			}
		}
		// ¿Subir de nivel?
		if (!lives.isDeath()) {
			int p = ship.getPower();
			if (score.getScore() > Enemy.LEVEL_SCORES[p-1]) { nextShip(); }
		}
	}


	private void removeStars() {
		for (StarPlane p: starPlanes) { p.removeStars(); }
	}

	private void removeEnemies() {
		for (Enemy enemy: enemies) {
			if (enemy.isBeyond()) {
				enemiesToRemove.add(enemy);
				//System.out.println("enemy beyond");
			}
		}
		for (Enemy enemy: enemiesToRemove) { enemies.remove(enemy); }
		enemiesToRemove.clear();
	}

	private void removeShots() {
		for (Shot shot: shots) {
			if (shot.isBeyond()) {
				shotsToRemove.add(shot);
			}
		}
		for (Shot shot: shotsToRemove) { shots.remove(shot); }
		shotsToRemove.clear();
	}


	private void removeBooms() {
		int l = exp.size();
		for (int i = l-1; i >= 0; i--) {
			if (exp.get(i).hasFinished()) { exp.remove(i); }
		}
	}


	private void addNewStars() {
		if (Rnd.get().nextDouble() < PROB_STAR) {
			int z = Rnd.get().nextInt(NUM_STAR_PLANES);
			Star star = new Star(z,g);
			StarPlane plane = starPlanes[z];
			plane.add(star);
		}
	}


	private void addNewEnemies() {
		Enemy e;

		boolean canAdd = true;
		int l = enemies.size();	e = l > 0 ? enemies.get(l-1) : null;
		if ((e != null) && (e.isOutsideV())) {
			canAdd = false;
		}

		if (canAdd && (Rnd.get().nextDouble() < PROB_ENEMY)) {
			int maxPower = MAX_ENEMY_POWER[ship.getPower() - 1];
			int power = Rnd.get().nextInt(maxPower) + 1;
			enemies.add(enemyBuilder.setPower(power).build());
		}
	}


	private void addShots() {
		for (Enemy e: enemies) {
			Shot shot = e.tryToShoot();
			if (shot != null) { shots.add(shot); }
		}
	}


}
