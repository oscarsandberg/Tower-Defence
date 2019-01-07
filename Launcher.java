/**
 * Used to start the game.
 */
public final class Launcher
{
    private static final String TITLE = "Tower Defence";

    private Launcher() {}

    public static void main(String[] args) {
	Game game = new Game(TITLE); // Only makes a new game, has no "result".
	GameClock gc = new GameClock(game);// Initializes the game clock, result is not of interest. GameClock is not ment to be altered and is therefore static.
    }
}
