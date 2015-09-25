/**
 * Created by Tess Daughton, September 13th, 2015
 * This class will utilize a MapGenerator object to get a random Tile[][] map for the game.
 */

package model;

import Resources.ImageLoader;
import Resources.SoundLoader;
import java.awt.*;
import java.util.Random;
import java.util.ArrayList;


public class ZombieHouseModel
{
  private static Dimension userScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
  protected final static int MAX_SCREEN_WIDTH = (int)userScreenSize.getWidth();
  protected final static int MAX_SCREEN_HEIGHT = (int)userScreenSize.getHeight();
  protected final static int VISIBLE_X_TILES = 12;
  protected final static int VISIBLE_Y_TILES = 10;
  protected final static SoundLoader SOUNDLOADER = new SoundLoader();
  private ImageLoader imageLoader;
  public final static int ROWS = 40;
  public final static int COLS = 40;
  private static final double zombieSpawnRate = 0.01;
  private static final double trapSpawnRate = 0.01;
  private Random rand = new Random();
  private Player playerCharacter;
  private ArrayList<Zombie> zombies;
  private ArrayList<Trap> traps;
  private Map map;
  private Tile grid[][];
  private double deltaSeconds;
  private int currentScreenWidth;
  private int currentScreenHeight;

  public ZombieHouseModel()
  {
    traps = new ArrayList<>();
    zombies = new ArrayList<>();
    MapGenerator mapGen = new MapGenerator(40, 40);
    this.currentScreenWidth = MAX_SCREEN_WIDTH;
    this.currentScreenHeight = MAX_SCREEN_HEIGHT;
    grid = this.translateTileImages(mapGen.getMap(), currentScreenWidth/VISIBLE_X_TILES, currentScreenHeight/VISIBLE_Y_TILES );
    map = new Map(grid, ROWS, COLS, currentScreenWidth/VISIBLE_X_TILES, currentScreenHeight/VISIBLE_Y_TILES);
    this.imageLoader = new ImageLoader(this, MAX_SCREEN_WIDTH, MAX_SCREEN_HEIGHT);
    this.setRandomTraps(grid);
    this.initializeRandomZombies(grid);
    playerCharacter=this.getRandomStart(grid);

  }

  /**
   * This method translates a 2D int array into a 2D Tile array
   * @param grid  input 2D int array representing some tile types
   * @param tileWidth  height of tile
   * @param tileHeight  width of tile
   * @return  a 2D Tile array representing the Zombie House
   */
  private Tile[][] translateTileImages(int[][] grid, int tileWidth, int tileHeight)
  {
    Tile[][] tiles = new Tile[ROWS][COLS];

    for (int i = 0; i < ROWS; i++)
    {
      for (int j = 0; j < COLS; j++)
      { if (grid[i][j] != 1) tiles[i][j] = new Wall(i, j, tiles);
        else tiles[i][j] = new Floor(i, j, tiles);
        tiles[i][j].setBounds(j * tileWidth, i * tileHeight, tileWidth, tileHeight);
      }
    }

    return tiles;
  }

  /**
   * Parses the Zombie House map and places the Player at a random location
   * @param map  2D Tile array of the Zombie House
   * @return  new Player located at random tile
   */
  private Player getRandomStart(Tile[][] map)
  {
    int x= rand.nextInt(40);
    int y = rand.nextInt(40);

    if(map[x][y] instanceof Floor)
    {
      playerCharacter = new Player(map[x][y].getCenterTileX(),map[x][y].getCenterTileY(),40,map[x][y],
          map,GridOrientation.pickRandomOrientation(), imageLoader);
    }
    else getRandomStart(map);
    return playerCharacter;
  }



  /**
   * Called inside actionPerformed in ZombieHouseFrame to periodically update frame width
   * Setter for window width
   * @param x  new width (in pixels)
   */
  public void setCurrentScreenWidth(int x)
  {
    this.currentScreenWidth = x;
  }

  /**
   * Called inside actionPerformed in ZombieHouseFrame to periodically update frame height
   * Setter for window height
   * @param y  new height (in pixels)
   */
  public void setCurrentScreenHeight(int y)
  {
    this.currentScreenHeight = y;
  }

  /**
   * Places random traps throughout the map
   * @param map  2D Tile array of the Zombie House
   */
  private void setRandomTraps(Tile[][] map)
{
  int x= rand.nextInt(40);
  int y = rand.nextInt(40);

  if(map[x][y] instanceof Floor)
  {
    Trap trap = new Trap( (int) map[x][y].getCenterX(),(int) map[x][y].getCenterY(), true);
    traps.add(trap);
    map[x][y].installTrap();
  }
  if(traps.size()<10) setRandomTraps(map);
}

  /**
   * Places zombies in random rooms throughout the house
   * @param map     2D Tile array of the Zombie House
   */
  private void initializeRandomZombies(Tile[][] map)
  {
    int x= rand.nextInt(40);
    int y = rand.nextInt(40);
    boolean valid = true;

    if(map[x][y] instanceof Floor)
    {
      for (int i = x; i < x + 2; i++)
      {
        for (int j = y; j < x + 2; j++)
        {
          if ((map[i][j]) instanceof Wall) valid = false;
        }
      }
      if(valid)
      {
        zombies.add(new Zombie((int) map[x][y].getCenterX(), (int) map[x][y].getCenterY(), 40,
            map[x][y], map, GridOrientation.pickRandomOrientation(), imageLoader));
      }
    }

    if(zombies.size()<10) initializeRandomZombies(map);
  }

  public void setMap(Tile[][] grid)
  {
    map.setMapGrid(grid);
  }


  /**
   * This method takes a displacement as int xy-coordinate pair and an orientation and tells the Player object to move
   * @param dX       X-displacement (in pixels)
   * @param dY       Y-displacement (in pixels)
   * @param dir      orientation on the grid
   */
  public void movePlayer(double dX, double dY, Enum dir, boolean running)
  {
    playerCharacter.walk(dX, dY, dir, running, deltaSeconds);
  }

  /**
   * This method takes a displacement as int xy-coordinate pair and an orientation and tells the ArrayList of Zombie objects to move
   */
  public void moveZombies()
  {
    for(Zombie zombie: zombies)
    {
      zombie.walk(zombie.getPlayerOrientation(), deltaSeconds);
    }
  }

  /**
   * Getter for the map object
   * @return  reference to the Map
   */
  public Map getMap()
  {
    return this.map;
  }

  /**
   * Getter for the player object
   * @return  reference to the Player
   */
  public Player getPlayer()
  {
    return this.playerCharacter;
  }
  public ArrayList<Zombie> getZombieList()
  {
    return this.zombies;
  }
  public ArrayList<Trap> getTrapList()
  {
    return this.traps;
  }
  public ImageLoader getImageLoader()
  {
    return this.imageLoader;
  }



  /**
   * This method dispatches time-based events among game agents
   * @param timeElapsed  time elapsed since last update
   */
  public void update(double timeElapsed)
  {
    this.deltaSeconds = timeElapsed;
    this.moveZombies();
    //TODO: update zombies
    //TODO: update player
    //TODO: check zombie-zombie intersections
    //TODO: check zombie-player intersections
    //TODO: check movable-trap intersections
  }
}
