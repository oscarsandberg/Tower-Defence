import java.util.ArrayList;
import java.util.List;

/**
 * The class HighscoreList creates a list of highscores.
 */
public final class HighscoreList
{
    private List<Highscore> highscores;				// List of highscores
    private static final HighscoreList INSTANCE = new HighscoreList();  //

    /**
     * The constructor creates an ArrayList for the highscores.
     */
    private HighscoreList() {
	highscores = new ArrayList<>();
    }

    /**
     * The method takes a name and a score, creates an highscore object of it, adds it to the list of highscores and sorts the
     * list.
     * @param name, is the users name or nickname.
     * @param score, is hat the user got in score before game over.
     */
    public void addHighscore(String name, int score) {
        Highscore highScore = new Highscore(name, score);
	highscores.add(highScore);
	highscores.sort(new ScoreComparator());
    }

    public static HighscoreList getInstance() {
	return INSTANCE;
    }

    public void getHighscores() {
	System.out.println("Highscore:");
	for (Highscore highscore : highscores) {
	    System.out.println((highscore.getName() + ": " + highscore.getScore()));
	}
    }
}
