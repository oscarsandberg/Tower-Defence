import java.awt.image.BufferedImage;

/**
 * The main Obstacle class. Obstacles are in the way of enemies.
 * Keeps track of how often to apply damage.
 */
public class Obstacle extends HealthEntity
{
    private boolean readyToDamage;
    private int tickCounter;
    private final int damagerate;

    public Obstacle(final int obstacleX, final int obstacleY, final int health, final int damage, final int damagerate,
		    final int price, final BufferedImage img, final String name, final Game game)
    {
	super(obstacleX, obstacleY, img, health, name, damage, price, game);
	this.damagerate = damagerate;
    }

    @Override public void tick() {
	checkHealth();
	tickCounter += 1;
	readyToDamage = tickCounter % damagerate == 0;
	damage();
    }

    protected void checkHealth() {
	if (health <= 0) {
	    game.getObstacles().remove(this);
	    game.getTempRemove(this);
	}
    }

    protected void damage() {
        if (readyToDamage) {
	    for (Enemy enemy : game.getEnemies()) {
		if (hasCollision(enemy)) {
		    enemy.setHealth(enemy.getHealth() - damage);
		    break;
		}
	    }
	}
    }
}

