import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Basic projectile which moves in a straight line at a constant speed.
 */
public class ProjectileBasic extends Projectile
{
    private final Random rnd = new Random();
    private final static int INACCURACY = 25;
    private final int xPoint = en.getX() + en.getSpeed() + (en.getImg().getWidth() / 2);
    private final int yPoint = en.getY() + (en.getImg().getHeight() / 2);
    private final int dx = (x + img.getWidth() / 2) - xPoint;
    private final int dy = (y + img.getHeight() / 2) - yPoint;
    private final double rangeToEnemy = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    private final double normalizedY = dy / rangeToEnemy;
    private final double normalizedX = dx / rangeToEnemy;
    private final double inaccuracy = (double) rnd.nextInt(INACCURACY) / 100;	// Generate an inacuracy.

    protected ProjectileBasic(final int projectileX, final int projectileY, final int speed, final BufferedImage img,
    final Tower tw, final Enemy en, final Game game)
    {
	super(projectileX, projectileY, speed, img, tw, en, game);
    }

    /**
     * Checks for collision with all enemies and moves projectile in a straight line at a constant speed.
     */
    @Override protected void move() {
	for (Enemy enemy : game.getEnemies()) {
	    boolean collision = hasCollision(enemy);
	    if (collision) {
		damage(enemy);
		return;
	    }
	}
	y -= (int) Math.round((normalizedY - inaccuracy) * speed);
	x -= (int) Math.round((normalizedX - inaccuracy) * speed);
    }
}
