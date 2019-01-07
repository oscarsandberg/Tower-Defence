import java.awt.image.BufferedImage;

/**
 * Projectile which is shot by towers.
 */
public abstract class Projectile extends Entity
{
    protected final Tower tw;
    protected final Enemy en;
    protected final int speed;

    protected Projectile(final int projectileX, final int projectileY, final int speed, final BufferedImage img, final Tower tw,
			 final Enemy en, final Game game)
    {
	super(projectileX, projectileY, img, game);
	this.speed = speed;
	this.tw = tw;
	this.en = en;
    }

    /**
     * Carries out functionallity of a projectile.
     */
    @Override public void tick() {
	move();
    }

    /**
     * Moves the projectile to follow the enemy.
     */
    protected void move() {
	boolean collision = hasCollision(en);
	if (!collision) {
	    int dx = x - en.getX() - en.getSpeed() - en.getImg().getWidth() / 2;
	    int dy = y - en.getY() - en.getSpeed() - en.getImg().getWidth() / 2;
	    int rangeToEnemy = (int) Math.round(Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2))) + 1;
	    x += -speed * dx / rangeToEnemy;
	    y += -speed * dy / rangeToEnemy;
	} else {
	    damage(en);
	}
    }

    /**
     * Applies damage to enemy it hits and removes projectile.
     */
    protected void damage(Enemy enemy) {
	enemy.setHealth(enemy.getHealth() - tw.getDamage());
	game.getProjectiles().remove(this);
	game.getTempRemove(this);
    }

    /**
     * Checks if projectile has a collision and removes it if out of bounds.
     * @param ent An enemy.
     * @return Boolean value of collision.
     */
    @Override public boolean hasCollision(final Entity ent) {
	if (x < 0 || x > Game.WIDTH || y < 0 || y > Game.HEIGHT) {
	    game.getProjectiles().remove(this);
	    game.getTempRemove(this);
	}
	boolean overlapX = (ent.getX() <= x && x <= ent.getX() + ent.getImg().getWidth());

	return (ent.getY() <= y && y <= ent.getY() + ent.getImg().getHeight()) && overlapX;
    }
}
