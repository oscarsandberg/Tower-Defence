/**
 * The class Highscore creates an Highscore object.
 */
public class Highscore
{
    private final String name;
    private final int score;

    /**
     * The constructor in the class which creates objects of the class Highscore.
     * @param name, is the users name or nickname.
     * @param score, the score the user got before game over.
     */
    public Highscore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
