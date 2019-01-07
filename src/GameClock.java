import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Sets the timing of the game.
 * Constructor cannot be private as it is accessed from Launcher.
 * Is public because it needs to be used by Launcher.
 */
public final class GameClock
{
    /**
     * Length of a tick (ms).
     */
    public static final int TICKTIME = 17;


    // Needs a public constructor as it is initialized from Launcher.
    public GameClock(Game game) {

	final Action doOneStep = new AbstractAction()
	{
	    public void actionPerformed(ActionEvent e) {
		// Action goes here.
		game.tick();
	    }
	};
	final Timer clockTimer = new Timer(TICKTIME, doOneStep);

	clockTimer.setCoalesce(true);
	clockTimer.start();
    }
}
