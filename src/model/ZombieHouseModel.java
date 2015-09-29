/**
 * Created by Tess Daughton, September 13th, 2015
 * This class will utilize a MapGenerator object to get a random Tile[][] map for the game.
 */

package model;

import Resources.ImageLoader;
import Resources.SoundLoader;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class models the Zombie House behavior
 */
public class ZombieHouseModel
{
  public final static int ROWS = 41;
  public final static int COLS = 48;
  private final static Dimension userScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
  private final static int MAX_SCREEN_WIDTH = (int) userScreenSize.getWidth();
  private final static int MAX_SCREEN_HEIGHT = (int) userScreenSize.getHeight();
  private final static int VISIBLE_X_TILES = 12;
  private final static int VISIBLE_Y_TILES = 10;
  private final static double ZOMBIE_SPAWN_RATE = 0.05f;
  private final static double TRAP_SPAWN_RATE = 0.00f;

  public static SoundLoader soundLoader;
  public static ImageLoader imageLoader;
  private Random rand;
  private MapGenerator mapGen;
  private Player playerCharacter;
  private ArrayList<Zombie> zombies;
  private ArrayList<Trap> traps;
  private Tile grid[][];
  private Pathfinder pf;
  private double deltaSeconds;
  private int currentScreenWidth;
  private int currentScreenHeight;
  private int tileWidth;
  private int tileHeight;

  /**
   * Constructor
   * @param soundLoader  SoundLoader
   */
  public ZombieHouseModel(SoundLoader soundLoader)
  {
    rand = new Random();
    mapGen = new MapGenerator();
    traps = new ArrayList<>();
    zombies = new ArrayList<>();
    ZombieHouseModel.soundLoader = soundLoader;
    this.currentScreenWidth = MAX_SCREEN_WIDTH;
    this.currentScreenHeight = MAX_SCREEN_HEIGHT;
    this.tileWidth = currentScreenWidth / VISIBLE_X_TILES;
    this.tileHeight = currentScreenHeight / VISIBLE_Y_TILES;
    this.grid = this.translateTileImages(mapGen.getMap());
    imageLoader = new ImageLoader(this, tileWidth, tileHeight);
    pf = new Pathfinder(this);
    this.setRandomTraps();
    this.initializeRandomZombies();
    this.playerCharacter = this.getRandomStart();
  }

  /**
   * This method translates a 2D int array into a 2D Tile array
   *
   * @param grid input 2D int array representing some tile types
   * @return a 2D Tile array representing the Zombie House
   */
  private Tile[][] translateTileImages(int[][] grid)
  {
    Tile[][] tiles = new Tile[ROWS][COLS];

    for (int i = 0; i < ROWS; i++)
    {
      for (int j = 0; j < COLS; j++)
      {
        if (grid[i][j] == 0) tiles[i][j] = new Outside(i,j,tiles);
        else if (grid[i][j] == 2) tiles[i][j] = new Floor(i,j,tiles);
        else if (grid[i][j] == 4) tiles[i][j] = new Floor(i,j,tiles); // implement exit tile
        else tiles[i][j] = new Wall(i, j, tiles);
        tiles[i][j].setBounds(j * this.tileWidth, i * this.tileHeight, this.tileWidth, this.tileHeight);
      }
    }

    return tiles;
  }

  /**
   * Parses the Zombie House map and places the Player at a random location
   *
   * @return new Player located at random tile
   */
  private Player getRandomStart()
  {
    int x = 0;
    int y = 0;

    while (!(grid[y][x] instanceof Floor))
    {
      x = rand.nextInt(COLS);
      y = rand.nextInt(ROWS);
      this.playerCharacter = new Player(this.grid[y][x].getCenterTileX(), this.grid[y][x].getCenterTileY(), this.tileHeight / 2, this.grid[y][x],
                                        this, imageLoader, false, 20);
    }
    return playerCharacter;
  }


  /**
   * Called inside actionPerformed in ZombieHouseFrame to periodically update frame width
   * Setter for window width
   *
   * @param x new width (in pixels)
   */
  public void setCurrentScreenWidth(int x)
  {
    this.currentScreenWidth = x;
  }

  /**
   * Called inside actionPerformed in ZombieHouseFrame to periodically update frame height
   * Setter for window height
   *
   * @param y new height (in pixels)
   */
  public void setCurrentScreenHeight(int y)
  {
    this.currentScreenHeight = y;
  }

  /**
   * Places random traps throughout the map
   */
  private void setRandomTraps()
  {
    for (int i = 0; i < ROWS; i++)
    {
      for (int j = 0; j < COLS; j++)
      {
        if (grid[i][j] instanceof Floor && rand.nextDouble() < TRAP_SPAWN_RATE)
        {
          this.traps.add(new Trap((int) this.grid[i][j].getCenterX(), (int) this.grid[i][j].getCenterY(), true));
          this.grid[i][j].installTrap();
          System.out.println("Trap at [" + i + "][" + j + "]");
        }
      }
    }
  }

  /**
   * Places zombies in random rooms throughout the house
   */
  private void initializeRandomZombies()
  {
    int valid;

    for (int i = 1; i < ROWS - 1; i++)
    {
      for (int j = 1; j < COLS - 1; j++)
      {
        valid = 0;
        if (grid[i][j] instanceof Floor)
        {
          for (int k = i - 1; k < i + 2; k++)
          {
            for (int l = j - 1; l < j + 2; l++)
            {
              if (this.grid[k][l] instanceof Wall) valid++;
            }
          }
          if (valid < 4 && rand.nextDouble() < ZOMBIE_SPAWN_RATE)
          {
            Zombie zombone = new Zombie((int) grid[i][j].getCenterX(), (int) grid[i][j].getCenterY(), this.tileHeight / 2,
                                        grid[i][j], this, GridOrientation.pickRandomOrientation(), imageLoader,true, 10);
            zombies.add(zombone);
            System.out.println(zombone.getZType() + " Zombie at [" + i + "][" + j + "]");
          }
        }
      }
    }
  }

  /**
   * This method takes a displacement as int xy-coordinate pair and an orientation and tells the Player object to move
   *
   * @param dX  X-displacement (in pixels)
   * @param dY  Y-displacement (in pixels)
   * @param dir orientation on the tiles
   */
  public void movePlayer(double dX, double dY, Enum dir, boolean running)
  {
    playerCharacter.walk(dX, dY, dir, running, deltaSeconds);
  }

  /**
   * This method tells the ArrayList of Zombie objects to move
   */
  public void updateZombies()
  {
    for (Zombie zombie : zombies)
    {
      if (zombie.isDead())
      {
        zombies.remove(zombie);
        break;
      }
      else
      {
        if (zombie.getDistanceTo(playerCharacter) < Zombie.DIST_SMELL * tileWidth)
        {
          ArrayList<Tile> path = pf.aStarSearch(zombie.getCurrentTile(), playerCharacter.getCurrentTile());
          zombie.setPath(path);
        }
        else
        {
          zombie.deletePath();
        }
        zombie.updateZombie(deltaSeconds);
      }
    }
  }

  /**
   * Gets/sets traps according to Player's current position
   */
  public void attemptTrapAction()
  {
    if (playerCharacter.getCurrentTile().hasTrap())
    {
      for (Trap trap : traps)
      {
        if ((trap.getX() == playerCharacter.getCurrentTile().getTrap().getX()) && (trap.getY() == playerCharacter.getCurrentTile().getTrap().getY()))
        {
          playerCharacter.grabTrap();
          traps.remove(trap);
          soundLoader.playPickUpTrap();
          break;
        }
      }
    }
    else if (playerCharacter.getNumberOfTraps() > 0)
    {
      playerCharacter.installTrap();
      traps.add(new Trap((int) playerCharacter.getCurrentTile().getCenterX(), (int) playerCharacter.getCurrentTile().getCenterY(), true));
      soundLoader.playDropTrap();
    }
  }

  /**
   * Getter for the tile array
   * @return  reference to the tile array
   */
  public Tile[][] getGrid()
  {
    return this.grid;
  }

  /**
   * Getter for the player object
   * @return  reference to the Player
   */
  public Player getPlayer()
  {
    return this.playerCharacter;
  }

  /**
   * Getter for the zombie list
   * @return  reference to the zombie list
   */
  public ArrayList<Zombie> getZombieList()
  {
    return this.zombies;
  }

  /**
   * Getter for the trap list
   * @return  reference to the trap list
   */
  public ArrayList<Trap> getTrapList()
  {
    return this.traps;
  }

  /**
   * Getter for the image loader
   * @return  reference to the ImageLoader
   */
  public ImageLoader getImageLoader()
  {
    return imageLoader;
  }

  /**
   * Burns tiles caught in trap explosions
   * @param tile  Tile containing trap
   */
  public void setCharredTile(Tile tile)
  {
    int y = tile.getGridRow();
    int x = tile.getGridCol();
    for(int i = y-1; i < y+2; i++)
    {
      for(int j = x-1; j < x+2; j++)
      {
        if(i>=0 && j>=0 && i<ROWS && j<COLS)
        {
          grid[i][j] = new CharredFloorTile(i, j, grid);
          grid[i][j].setBounds(j * this.tileWidth, i * this.tileHeight, this.tileWidth, this.tileHeight);
        }
      }
    }
  }

  /**
   * Resets the model to game start conditions
   * @param newLevel  Flag to generate new level map
   */
  public void restart(boolean newLevel)
  {
    this.traps.clear();
    this.zombies.clear();
    this.grid = this.translateTileImages(mapGen.getMap());
    imageLoader = new ImageLoader(this, tileWidth, tileHeight);
    this.setRandomTraps();
    this.initializeRandomZombies();
    this.playerCharacter = this.getRandomStart();
  }

  /**
   * This method dispatches time-based events among game agents
   * @param timeElapsed  time elapsed since last update
   */
  public void update(double timeElapsed)
  {
    this.deltaSeconds = timeElapsed;
    this.playerCharacter.update(timeElapsed);
    this.updateZombies();
  }
}
