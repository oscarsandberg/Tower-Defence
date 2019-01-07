import java.awt.image.BufferedImage;

/**
 * Enemytype Seeker, seeks out towers instead of just moving forward.
 */
public class EnemySeeker extends Enemy
{

    /**
     * Used to find smallest range to a tower and needs to be a large number initially.
     */
    public static final int BIG_NUMBER = 9000;
    private int smallestRange = BIG_NUMBER;
    private Tower closest = null; // Initialized when closest tower is found.
    public EnemySeeker(int enemyX, int enemyY, int speed, int health, int damage, int reward, BufferedImage img, String name,
		       Game game, int attackspeed)
    {
	super(enemyX, enemyY, speed, health, damage, reward, img, name, game, attackspeed);
	findClosest();
    }

    @Override public void move() {
	if (closest == null || closest.isDead()) {
	    smallestRange = BIG_NUMBER;
	    findClosest();
	}
	boolean collision = hasCollision(closest);
	if (!collision) {
	    int dx = x - closest.getX() - closest.getImg().getWidth() / 2;
	    int dy = y - closest.getY() - closest.getImg().getHeight() / 2;
	    int rangeToEnemy = (int) Math.round(Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2))) + 1;
	    x += -speed * dx / rangeToEnemy;
	    y += -speed * dy / rangeToEnemy;
	} else {
	    attack(closest);
	}
    }

    /**
     * Finds the closest tower.
     */
    private void findClosest() {
	for (Tower tw : game.getTowers()) {
	    int rangeToEnemy = (int) Math.round(Math.sqrt(Math.pow(x - tw.getX(), 2) + Math.pow(y - tw.getY(), 2))) + 1;
	    if (rangeToEnemy < smallestRange) {
	        closest = tw;
	        smallestRange = rangeToEnemy;
	    }
	}
    }
}
