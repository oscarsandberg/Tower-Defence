import java.awt.image.BufferedImage;

/**
 * The main enemy class.
 */
public abstract class Enemy extends HealthEntity
{
    protected int speed;
    protected int tickCounter = 0;
    protected int attackspeed;
    protected boolean readyToFire;

    protected Enemy(final int enemyX, final int enemyY, final int speed, final int health, final int damage,
		    final int priceReward, final BufferedImage img, final String name, final Game game, final int attackspeed)
    {
	super(enemyX, enemyY, img, health, name, damage, priceReward, game);
	this.speed = speed;
	this.attackspeed = attackspeed;
    }

    public void tick() {
	checkHealth();
	tickCounter += 1;
	readyToFire = tickCounter % attackspeed == 0;
	move();
    }

    protected void checkHealth() {
	if (health <= 0) {
	    game.getTempRemove(this);
	    game.getEnemies().remove(this);
	    game.setMoney(game.getMoney() + priceReward);
	}
    }
    /**
     * Damages a tower if enemy is ready to fire.
     * @param object Tower or obstacle.
     */
    public void attack(HealthEntity object) {
	if (readyToFire) {
	    object.setHealth(object.getHealth() - damage);
	}
    }
    /**
     * Moves the enemy if no collision with tower is detected.
     */
    public void move() {
	boolean towerCollision = false;
	for (Tower tower : game.getTowers()) {
	    towerCollision = hasCollision(tower);
	    if (towerCollision) {
		attack(tower);
		break;
	    }
	}
	boolean obstacleCollision = false;
	for (Obstacle obstacle : game.getObstacles()) {
	    obstacleCollision = hasCollision(obstacle);
	    if (obstacleCollision) {
		attack(obstacle);
		break;
	    }
	}
	if (!obstacleCollision && !towerCollision) {
	    x += speed;
	}
    }

    public int getSpeed() {
	return speed;
    }
}
