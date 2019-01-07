import java.awt.image.BufferedImage;

/**
 * Handles the creation of obstacles.
 * Fields are not made local in order to avoid having to use different names.
 * All numbers are enemy stats and therefore has to be "magic".
 */
public class ObstacleMaker
{
    private int health;
    private int damage;
    private int damagerate;
    private BufferedImage img; // img = null is catched and handled by ImageLoader.
    private int price;
    private String name; // Initialized when specific tower is created

    public Obstacle getObstacle(int n, int xPos, int yPos, Game game) {
	switch (n) {
	    case 0:
		health = 15;
		damage = 1;
		damagerate = 10;
		img = ImageLoader.loadImage("textures/Nails.png");
		price = 30;
		name = "Nails";
		return new Obstacle(xPos, yPos, health, damage, damagerate, price, img, name, game);
	    case 1:
	        health = 20;
	        damage = 0;
		damagerate = 1;
		img = ImageLoader.loadImage("textures/Blocker.png");
		price = 20;
		name = "Blocker";
		return new Obstacle(xPos, yPos, health, damage, damagerate, price, img, name, game);
	    default:
		throw new IllegalArgumentException("Invalid index: " + n);
	}
    }
}
