import javax.swing.*;
import java.awt.*;

/**
 * Handels the rendering of the game.
 */
public class TowerDefenceComponent extends JComponent
{
    private Game game;
    private final static int MONEY_DISPLAY_X = Game.WIDTH - 250;
    private final static int MONEY_DISPLAY_Y = Game.HEIGHT - 10;

    public TowerDefenceComponent(Game game) {
	game.setListener(this);
	this.game = game;
    }

    @Override public Dimension getPreferredSize() {
	return new Dimension(Game.WIDTH, Game.HEIGHT);
    }

    @Override public String toString() {
	return "Money: " + game.getMoney() + "$ Score:" + game.getScore();
    }

    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);

	g.drawImage(ImageLoader.loadImage("textures/grass.png"), 0, 0, null); // Sets background.
	g.drawString(toString(), MONEY_DISPLAY_X, MONEY_DISPLAY_Y); // Shows amount of money

	for (Entity ent : game.getEntities()) {
	    ent.drawImg(g);
	}
    }

    public void updateImage() {
	repaint();
    }
}
