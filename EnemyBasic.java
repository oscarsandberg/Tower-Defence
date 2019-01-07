import java.awt.image.BufferedImage;

/**
 * Basic enemy (inherits all functionallity form Enemy).
 */
public class EnemyBasic extends Enemy
{

    public EnemyBasic(final int enemyX, final int enemyY, final int speed, final int health, final int damage, final int reward,
                 final BufferedImage img, final String name, final Game game, final int attackspeed)
    {
        super(enemyX, enemyY, speed, health, damage, reward, img, name, game, attackspeed);
    }
}
