import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Entity is at the top of the heirachy for all drawables and ensures basic functionallity for these.
 * Has coordinates, image and access to game.
 */
public abstract class Entity {
    protected int x;
    protected int y;
    protected BufferedImage img;
    protected Game game;

    protected Entity(final int x, final int y, final BufferedImage img, final Game game) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.game = game;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void drawImg(Graphics g) {
        g.drawImage(img, x, y, null);    // Draw image of object.
    }

    /**
     * Makes sure all entities can check for collision.
     * @param ent An entity.
     * @return boolean value of collision.
     */
    public abstract boolean hasCollision(Entity ent);	// Method is used.

    /**
     * Used for calling methods neccessary for entity behaviour.
     */
    public abstract void tick();
}
