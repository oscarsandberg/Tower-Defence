import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * HealthEntity is below Entity and ensures there are healthrelated functionallity.
 */
public abstract class HealthEntity extends Entity
{
    protected int health;
    protected int priceReward;
    protected final int maxHealth;
    protected final String name;
    protected int damage;
    private static final int HP_BAR_HEIGHT = 11;
    private static final float HP_BAR_COLOR = 0.3f;

    protected HealthEntity(final int x, final int y, final BufferedImage img, final int health, final String name,
			   final int damage, final int priceReward, final Game game)
    {
	super(x, y, img, game);
	this.health = health;
	this.maxHealth = health;
	this.name = name;
	this.damage = damage;
	this.priceReward = priceReward;
    }

    @Override public void drawImg(Graphics g) {
	super.drawImg(g);
	int hpbarWidth = (int) (img.getHeight() * ((double) health /
						   (double) maxHealth));                        // Double casting is required to convert to int (results in int * float otherwise).
	g.setColor(Color.BLACK);
	g.drawRect(x, y + img.getHeight() + 3, img.getHeight() + 1, HP_BAR_HEIGHT);        // Draw outline for HP bar.
	g.setColor(Color.getHSBColor(HP_BAR_COLOR * (health / (float) maxHealth), 1.0f,
				     1.0f));                                                // Set color to ratio of max HP and current HP.
	g.fillRect(x + 1, y + img.getHeight() + 4, hpbarWidth, HP_BAR_HEIGHT -
							       1);                                                                // Draw inside of HP bar (1 px smaller than outline).
    }

    @Override public boolean hasCollision(final Entity ent) {
	if (x < 0 || x + img.getWidth() > Game.WIDTH) {                // Check that entity is within x bounds.
	    return true;
	}

	if (y < 0 || y + img.getHeight() > Game.HEIGHT) {        // Check that entity is within y bounds.
	    return true;
	}
	boolean overlapX = false;

	for (int xDiff = 0; xDiff < img.getWidth(); xDiff++) {        // Check that entity x does not overlap this x.
	    if ((ent.getX() <= this.getX() + xDiff && this.getX() <= ent.getX() + ent.getImg().getWidth())) {
		overlapX = true;
		break;
	    }
	}
	if (overlapX) {
	    for (int yDiff = 0;
		 yDiff < this.getImg().getHeight();
		 yDiff++) {        // Check that entity y does not overlap this y.
		if ((ent.getY() <= this.getY() + yDiff && this.getY() <= ent.getY() + ent.getImg().getHeight())) {
		    return true;
		}
	    }
	}
	return false; // No collision detected.
    }

    public int getHealth() {
	return health;
    }

    public void setHealth(final int health) {
	this.health = health;
    }

    public int getDamage() {
	return damage;
    }

    public int getPriceReward() {
	return priceReward;
    }

    /**
     * Ensures HealthEntities checks if they are dead.
     */
    protected abstract void checkHealth();	// Method is used.
}
