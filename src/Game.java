import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Game class handles everything related to how the game should be played. That includes spawning enemies, handeling money,
 * game over and lists of entities which are used by Display when drawing the graphics of the game and doing various checks.
 * TODO: run inspection.
 * NEXT: Implementationsbeskrivning,
 */
public class Game
{
    /**
     * Width of the playingfield.
     */
    public static final int WIDTH = 1280;
    /**
     * Height of the playingfield.
     */
    public static final int HEIGHT = 720;
    /**
     * The offset used for the base in X-position.
     */
    private static final int SPAWN_BASIC_VARIABLE = 120;	// Variable used when to spawn a EnemyBasic.
    private static final int SPAWN_SEEKER_VARIABLE = 15;	// Variable used when to spawn a EnemySeeker.
    private static final int ADD_MONEY_VARIABLE = 40;		// Variable used when to get more money.
    private static final int INCREAMENT_MULTIPLIERS_VARIABLE = 2000; // Variable used when to increament multipliers.
    private static final int BASE_OFFSET = WIDTH - 50;
    private static final int STARTING_MONEY = 250;
    private int tickCounter = 0;			// Counts the number of ticks in the game clock has ran.
    private int money = STARTING_MONEY;                 // Amount of money to start with.
    private static final int BASE_HEALTH = 300;			// The helath of the base.
    private int spawnedEnemies = 1;			// The number of enemies that has spawned.
    private int score;					// The score the user has in the game.
    private List<Enemy> enemies;                        // List of enemies.
    private List<Tower> towers;                   	// List of towers.
    private List<Projectile> projectiles;		// List of projectiles.
    private List<Obstacle> obstacles;			// List of obstacles.
    private List<Entity> entities;                      // List of all entities
    private List<Entity> tempAdd;                       // Temporary list for adding entities.
    private List<Entity> tempRemove;                    // Temporary list for removing entities.
    private TowerDefenceComponent listener = null;	//

    private boolean spawnBasic;				// A boolean if to spawn EnemyBasic.
    private boolean spawnSeeker;			// A boolean if to spawn EnemySeeker.
    private boolean addMoney;				// A boolean if to add money to the user. Not local, used in multiple places.
    private boolean incrementMultipliers;		// A boolean if to increase the multipliers such as healt and reward.

    public Game(String title) {
	enemies = new ArrayList<>();			// Creates an arraylist for all the enemies.
	towers = new ArrayList<>();			// Creates an arraylist for all the towers.
	projectiles = new ArrayList<>();		// Creates an arraylist for all the projectiles.
	obstacles = new ArrayList<>();			// Creates an arraylist for all the obstacles.
	entities = new ArrayList<>();			// Creates an arraylist for all the entities.
	tempAdd = new ArrayList<>(entities);		// Creates an arraylist for temporary added entities.
	tempRemove = new ArrayList<>(entities);		// Creates an arraylist for temporary removed entites.

	// Creating the base for the game.
	Tower base =
		new TowerBasic(BASE_OFFSET, 0, BASE_HEALTH, 0, 0, 1, 0,
			       ImageLoader.loadImage("textures/Base.png"), "BASE", null, this);

	towers.add(base); // Adds the base to the list of towers.
	entities.add(base); // Adds the base to the list of entities.

	new Display(title, this); // Creates a new display (frame). Result is not of interest.
    }

    /**
     * Calls all enteties tick function, handels money and score, enemy spawning, entity adding and removing and updating the screen.
     */
    public void tick() {

        if (tickCounter % 10 == 0) {			// For every 10th tick the user gets one more score.
            score++;
	}
	spawnBasic = tickCounter % SPAWN_BASIC_VARIABLE == 0; 		// Spawn a basic enemy every 120th tick.
	spawnSeeker = spawnedEnemies % SPAWN_SEEKER_VARIABLE == 0;     	// Spawn a seeker for every 15 enemies spawned.
	addMoney = tickCounter % ADD_MONEY_VARIABLE == 0;     		// Add money every 40th tick.
	incrementMultipliers = tickCounter % INCREAMENT_MULTIPLIERS_VARIABLE == 0; // Increment the multipliers every 2000th tick.

	for (Entity entity : entities) {
	    entity.tick();                        	// Tick all entities to update their position, health etc.
	}

	entities.addAll(tempAdd);               	// Add concurrent entities.
	tempAdd.clear();                       		// Clear templist.
	entities.removeAll(tempRemove);        		// Remove concurrent entities.
	tempRemove.clear();                    		// Clear templist.

	if (addMoney) {					// The user gets more money if addMoney is true.
	    money++;
	}

	spawnEnemy();					// Spawns a new enemy.
	listener.updateImage();				// Updates the display
	tickCounter++;					// Increases tickCounter with one.
    }

    /**
     * Spawns an enemy and increments health- and reward-multipliers.
     * Type of enemy depends on tickCounter and amount of spawned enemies.
     * Enemy health- and reward-multipliers are to scale the difficulty and depend on tickCounter.
     */
    private void spawnEnemy() {
	if (incrementMultipliers) {				// If incrementMultipliers is true health and reward
	    EnemyMaker.setHealthMultiplier(1);			// multipliers increases with one.
	    EnemyMaker.setRewardMultiplier(1);
	}
	Enemy en;
	if (spawnSeeker) { 					// If spawnSeeker is true, one EnemySeeker spawns.
	    en = new EnemyMaker().getEnemy(1, this);	// Creates the new enemy, EnemySeeker.
	    enemies.add(en);					// Adds the enemy to the list of enemies.
	    entities.add(en);					// Adds the enemy to the list of entities.
	    spawnedEnemies++;					// Increases the int spawnedEnemies with one.
	} else if (spawnBasic) {				// If spawnBasic is true, one EnemyBasic spawns.
	    en = new EnemyMaker().getEnemy(0, this);	// Creates the new enemy, EnemyBasic.
	    enemies.add(en);					// Adds the enemy to the list of enemies.
	    entities.add(en);					// Adds the enemy to the list of entities.
	    spawnedEnemies++;					// Increases the int spawnedEnemies with one.
	}
    }

    /**
     * The method handles what happens when game over.
     * Prompts the user to input their name for highscore and whether to restart the game or quit.
     */
    public void gameOver() {
	String name = JOptionPane.showInputDialog("Game over! Write your name");
	HighscoreList.getInstance().addHighscore(name, score);			// Adds a highscore.
	if (askUser("Do you want to restart the game?")) {
	    resetGame();							// Resets the game.
	} else {
	    System.exit(1);						// Exit the game.
	}
    }

    /**
     * The method shows the highscores in the highscorelist.
     */
    public void showHighscoreList() {
	    HighscoreList.getInstance().getHighscores();
    }

    /**
     * Ask the user a question and returns a boolean.
     * @param seq is the question the askUser will ask the user.
     * @return a frame with the question and possible answers.
     */
    private static boolean askUser(String seq) {
	return JOptionPane.showConfirmDialog(null, seq, "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    /**
     * Resets all the values to starting conditions.
     */
    private void resetGame() {
	enemies = new ArrayList<>();
	towers = new ArrayList<>();
	projectiles = new ArrayList<>();
	obstacles = new ArrayList<>();
	entities = new ArrayList<>();
	tempAdd = new ArrayList<>(entities);
	tempRemove = new ArrayList<>(entities);

	spawnedEnemies = 1;
	money = STARTING_MONEY;
	score = 0;					// Resets the score.
	showHighscoreList();				// Shows the current highscores in the game.

	Tower base =
		new TowerBasic(BASE_OFFSET, 0, BASE_HEALTH, 0, 0, 1, 0,
			       ImageLoader.loadImage("textures/Base.png"), "BASE", null, this);

	towers.add(base);
	entities.add(base);
    }

    /**
     * Creates a new tower.
     * @param towerID is which type of tower to create.
     * @param x, the x position to place the tower.
     * @param y, the y position to place the tower.
     * @return a new tower.
     */
    public Tower createTower(int towerID, int x, int y) {
	return new TowerMaker().getTower(towerID, x, y, this);
    }

    /**
     * Creates a new obstacle.
     * @param obstacleID, is which obstacle to create.
     * @param x, the x position to place the obstacle.
     * @param y, the y position to place the obstacle.
     * @return a new obstacle.
     */
    public Obstacle createObstacle(int obstacleID, int x, int y) {
        return new ObstacleMaker().getObstacle(obstacleID, x, y, this);
    }

    public void addObject(HealthEntity object, String name) {
        if (name.equals("Tower")) {
            towers.add((Tower)object);
	    entities.add(object);
	}
	else if (name.equals("Obstacle")) {
            obstacles.add((Obstacle)object);
	    entities.add(object);
	}
    }

    public void getTempAdd(Entity ent) {
	tempAdd.add(ent);
    }

    public void getTempRemove(Entity ent) {
	tempRemove.add(ent);
    }

    public void setListener(final TowerDefenceComponent listener) {
	this.listener = listener;
    }

    public int getMoney() {
	return money;
    }

    public void setMoney(final int money) {
	this.money = money;
    }

    public List<Enemy> getEnemies() {
	return enemies;
    }

    public List<Tower> getTowers() {
	return towers;
    }

    public List<Obstacle> getObstacles() {
	return obstacles;
    }

    public List<Entity> getEntities() {
	return entities;
    }

    public List<Projectile> getProjectiles() {
	return projectiles;
    }

    public int getScore() {
	return score;
    }
}