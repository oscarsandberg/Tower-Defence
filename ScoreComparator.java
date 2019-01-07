import java.util.Comparator;

/**
 * The class ScoreComparator compares highscores.
 */
public class ScoreComparator implements Comparator<Highscore>
{
    /**
     * The method has two highscores as parameters and compares them with eachother and returns an integer based on the highest
     * score.
     */
    public int compare(Highscore o1, Highscore o2) {
	return Integer.compare(o2.getScore(), o1.getScore());
    }
}
