import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Handles the creation of enemies.
 * Fields are not made local in order to avoid having to use different names.
 * All numbers are enemy stats and therefore has to be "magic".
 */
public class EnemyMaker {
    private final static int X_POS = 1;
    private final static int Y_BOUND = Game.HEIGHT - 80;
    private final static int SPAWN_MARGIN = 40;
    private static int healthMultiplier = 0;
    private static int rewardMultiplier = 0;

    private int yPosition() {
        Random random = new Random();
        return random.nextInt(Y_BOUND) + SPAWN_MARGIN;
    }

    public Enemy getEnemy(int n, Game game) {
        switch (n) {
            case 0:
                int speed = 2;
                int attackspeed = 50;
                int health = 7 * healthMultiplier;
                int damage = 10;
                int reward = 10 * rewardMultiplier;
                BufferedImage img = ImageLoader.loadImage("textures/Enemy_Yee.png");
                String name = "Enemy Basic";
                return new EnemyBasic(X_POS, yPosition(), speed, health, damage, reward, img, name, game, attackspeed);
            case 1:
                speed = 4;
                attackspeed = 15;
                health = 3 * healthMultiplier;
                damage = 5;
                reward = 20 * rewardMultiplier;
                img = ImageLoader.loadImage("textures/Enemy_Sanic.png");
                name = "Enemy Seeker";
                return new EnemySeeker(X_POS, yPosition(), speed, health, damage, reward, img, name, game, attackspeed);

            default:
                throw new IllegalArgumentException("Invalid index: " + n);
        }
    }

    public static void setHealthMultiplier(int adder) {
        healthMultiplier += adder;
    }

    public static void setRewardMultiplier(int rewardMultiplier) {
	EnemyMaker.rewardMultiplier += rewardMultiplier;
    }
}
