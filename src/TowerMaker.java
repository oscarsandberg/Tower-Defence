import java.awt.image.BufferedImage;

/**
 * Handles the creation of towers.
 * Fields are not made local in order to avoid having to use different names.
 * All numbers are tower stats and therefore has to be "magic".
 */
public class TowerMaker
{
    private int health;
    private int damage;
    private int range;
    private int reload;
    private BufferedImage img; // img = null is catched and handled by ImageLoader.
    private int price;
    private String name; // Initialized when specific tower is created
    private Projectile projectile;  // Initialized as null. Tower gets its projectile when attacking an enemy.

    public Tower getTower(int n, int xPos, int yPos, Game game) {
	switch (n) {
	    case 0:
		health = 100;
		damage = 1;
		range = 200;
		reload = 20;
		img = ImageLoader.loadImage("textures/Tower_Basic.png");
		price = 50;
		name = "Allround";
		projectile = null;
		return new TowerBasic(xPos, yPos, health, damage, range, reload, price, img, name, projectile, game);
	    case 1:
		health = 50;
		damage = 10;
		range = 50;
		reload = 20;
		img = ImageLoader.loadImage("textures/Tower_Shortrange.png");
		price = 150;
		name = "Short Range";
		projectile = null;
		return new TowerBasic(xPos, yPos, health, damage, range, reload, price, img, name, projectile, game);
	    case 2:
		health = 25;
		damage = 6;
		range = 700;
		reload = 100;
		img = ImageLoader.loadImage("textures/Tower_Sniper.png");
		price = 300;
		name = "Sniper";
		projectile = null;
		return new TowerSniper(xPos, yPos, health, damage, range, reload, price, img, name, projectile, game);
	    case 3:
		health = 75;
		damage = 2;
		range = 175;
		reload = 75;
		img = ImageLoader.loadImage("textures/Tower_Shotgun.png");
		price = 250;
		name = "Shotgun";
		projectile = null;
		return new TowerShotgun(xPos, yPos, health, damage, range, reload, price, img, name, projectile, game);
	    default:
		throw new IllegalArgumentException("Invalid index: " + n);
	}
    }
}
