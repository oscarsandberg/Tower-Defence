import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Makes a new window.
 */
public class Display extends JFrame
{
    private static final Logger LOGGER = Logger.getLogger(Display.class.getName());

    private String title;
    private static final int RADIUS = 23;

    public Display(final String title, Game game) throws HeadlessException {
	this.title = title;
	createDisplay(game);
    }

    private void createDisplay(Game game) {
	final JFrame frame = new JFrame(title); // Creates a new frame.
	frame.setDefaultCloseOperation(
		WindowConstants.EXIT_ON_CLOSE); // Makes sure program is terminated when window is closed.
	frame.setResizable(false);

	TowerDefenceComponent comp = new TowerDefenceComponent(game); // Creates a new component.


	final JMenuBar menuBar = new JMenuBar(); // New menu bar.

	// Add more menus here:
	final JMenu towers = new JMenu("Towers"); // New menu to purchase towers.
	final JMenu obstacles = new JMenu("Obstacles"); // New menu to purchase obstacles.

	// Add more towers here:
	JMenuItem tower1 = new JMenuItem("Allround, 50$"); // New menu item.
	JMenuItem tower2 = new JMenuItem("Short Range, 150$"); // New menu item.
	JMenuItem tower3 = new JMenuItem("Sniper, 300$"); // New menu item.
	JMenuItem tower4 = new JMenuItem("Shotgun, 250$"); // New menu item.

	// Add more obstacles here:
	JMenuItem obstacle1 = new JMenuItem("Nails, 30$"); // New menu item.
	JMenuItem obstacle2 = new JMenuItem("Blocker, 20$"); // New menu item.

	// Add towers to the menu "Towers".
	towers.add(tower1);
	towers.add(tower2);
	towers.add(tower3);
	towers.add(tower4);

	// Add obstacles to the menu "Towers".
	obstacles.add(obstacle1);
	obstacles.add(obstacle2);

	// Add actionlisteners for menu items here:
	tower1.addActionListener(a -> placeObject("Tower", 0, comp, game));

	tower2.addActionListener(a -> placeObject("Tower", 1, comp, game));

	tower3.addActionListener(a -> placeObject("Tower", 2, comp, game));

	tower4.addActionListener(a -> placeObject("Tower", 3, comp, game));

	obstacle1.addActionListener(a -> placeObject("Obstacle", 0, comp, game));
	obstacle2.addActionListener(a -> placeObject("Obstacle", 1, comp, game));

	menuBar.add(towers); // Add "Towers" to the menu bar.
	menuBar.add(obstacles); // Add "Obstacles" to the menu bar.

	frame.setJMenuBar(menuBar); // Adds the menu bar to the frame.
	frame.add(comp); // Adds it to the frame.
	frame.pack();
	frame.setVisible(true);
    }

    /**
     * Places tower if money and collision check passes.
     * @param objectName describes if it is a tower or obstacle to place.
     * @param id         id of tower to place.
     * @param comp       the component on which to add / remove listeners.
     * @param game       the game (needed for checks).
     */
    private void placeObject(String objectName, int id, Component comp, Game game) {

	while (comp.getMouseListeners().length > 0) {
	    comp.removeMouseListener(comp.getMouseListeners()[0]);
	}
	MouseAdapter mA = new MouseAdapter()
	{
	    @Override public void mouseReleased(final MouseEvent e) {
	    // Check if user gives invalid mouseinput.
	    if (e.getX() > Game.WIDTH || e.getX() < 0 || e.getY() > Game.WIDTH || e.getY() < 0) {
		LOGGER.log(Level.WARNING, "Invalid coordinates.", new IOException(e.toString()));
	    }
	    HealthEntity object;
	    if (objectName.equals("Tower")) {
		object = game.createTower(id, e.getX() - RADIUS, e.getY() - RADIUS);

	    } else {
		object = game.createObstacle(id, e.getX() - RADIUS, e.getY() - RADIUS);

	    }
	    if (game.getMoney() >= object.getPriceReward()) {
		boolean collision = false;
		for (Entity hEnt : game.getEntities()) {
		    if (object.hasCollision(hEnt)) {
			collision = true;
		    }
		}
		if (!collision) {
		    game.addObject(object, objectName);
		    game.setMoney(game.getMoney() - object.getPriceReward());
		} else {
		    System.out.println("Can't place " + objectName + " here.");
		}
	    }
	    }
	};
	comp.addMouseListener(mA);
    }
}
