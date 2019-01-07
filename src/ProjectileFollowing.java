import java.awt.image.BufferedImage;

/**
 * Target seeking projectile (inherits all functionallity from Projectile).
 */
public class ProjectileFollowing extends Projectile
{
    protected ProjectileFollowing(final int projectileX, final int projectileY, final int speed, final BufferedImage img,
				  final Tower tw, final Enemy en, final Game game)
    {
	super(projectileX, projectileY, speed, img, tw, en, game);
    }
}
