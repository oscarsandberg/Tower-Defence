import java.awt.image.BufferedImage;

/**
 * Sniper tower. Uses high velocity target seeking projectile.
 */
public class TowerSniper extends Tower
{
    private final static int PROJECTILE_SPEED = 25;

    public TowerSniper(final int towerX, final int towerY, final int health, final int damage, final int range,
		       final int firerate, final int price, final BufferedImage img, final String name,
		       final Projectile projectile, final Game game)
    {
	super(towerX, towerY, health, damage, range, firerate, price, img, name, projectile, game);
    }

    @Override public void projectile(final Enemy en) {
	projectile = new ProjectileFollowing(x + img.getWidth() / 2, y + img.getHeight() / 2, PROJECTILE_SPEED,
					 ImageLoader.loadImage("textures/Projectile1.png"), this, en, game);
	game.getProjectiles().add(projectile); // Add towers projectile to list.
	game.getTempAdd(projectile);
    }
}
