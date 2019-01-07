import java.awt.image.BufferedImage;

/**
 * The main Tower class. All towers knows which enemy to attack and what projectile to use.
 * Keeps track of how often to fire, what it costs, its range and if it is alive.
 */
public abstract class Tower extends HealthEntity
{
    protected int range;
    protected int firerate;
    protected Projectile projectile;
    protected boolean readyToFire;
    protected int tickCounter = 0;
    protected boolean dead = false;

    protected Tower(final int towerX, final int towerY, final int health, final int damage, final int range, final int firerate,
		 final int price, final BufferedImage img, final String name, final Projectile projectile, final Game game)
    {
	super(towerX, towerY, img, health, name, damage, price, game);
	this.range = range;
	this.firerate = firerate;
	this.projectile = projectile;
    }

    /**
     * Carries out functionallity of a tower.
     */
    @Override public void tick() {
	checkHealth();
	tickCounter += 1;
	readyToFire = tickCounter % firerate == 0;
	shoot();
    }

    /**
     * Checks if tower is dead. Removes if dead. Game over if tower is base and dead.
     */
    protected void checkHealth() {
	if (health <= 0) {
	    if (name.equals("BASE")) {
		game.gameOver();
	    }
	    dead = true;
	    game.getTowers().remove(this);
	    game.getTempRemove(this);
	}
    }

    /**
     * Targets the closest enemy and shoots a projectile.
     */
    public void shoot() {
	if (readyToFire) {
	    for (Enemy en : game.getEnemies()) {
		float rangeToTower = (float) Math.sqrt(Math.pow(x - en.getX(), 2) + Math.pow(y - en.getY(), 2));
		if (rangeToTower <= range) {
		    projectile(en);
		    break; // Break to only shoot at one target at a time.
		}
	    }
	}
    }

    /**
     * Abstract as diffrent tower has diffrent projectiletypes.
     * @param en Enemy which to target.
     */
    public abstract void projectile(Enemy en);

    /**
     * Lets others check if tower is dead.
     * @return Whether tower is dead.
     */
    public boolean isDead() {
	return dead;
    }
}
