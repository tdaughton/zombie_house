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
  public int level = 1;
  private final static Dimension userScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
  private final static int MAX_SCREEN_WIDTH = (int) userScreenSize.getWidth();
  private final static int MAX_SCREEN_HEIGHT = (int) userScreenSize.getHeight();
  private final static int VISIBLE_X_TILES = 12;
  private final static int VISIBLE_Y_TILES = 10;
  private final static double ZOMBIE_SPAWN_RATE = 0.01f;
  private final static double TRAP_SPAWN_RATE = 0.00f;

  public static SoundLoader soundLoader;
  public static ImageLoader imageLoader;
  private Random rand;
  private MapGenerator mapGen;
  private Player playerCharacter;
  private Tile playerSub;
  private ArrayList<Zombie> zombies;
  private ArrayList<Tile> zombieSub;
  private ArrayList<Trap> traps;
  private ArrayList<Tile> trapSub;
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
    traps = new ArrayList<>();
    zombies = new ArrayList<>();
    trapSub = new ArrayList<>();
    zombieSub = new ArrayList<>();
    ZombieHouseModel.soundLoader =soundLoader;
    this.currentScreenWidth = MAX_SCREEN_WIDTH;
    this.currentScreenHeight = MAX_SCREEN_HEIGHT;
    this.tileWidth = currentScreenWidth / VISIBLE_X_TILES;
    this.tileHeight = currentScreenHeight / VISIBLE_Y_TILES;
    this.mapGen = new MapGenerator(2);
    this.rand = new Random();
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
        else if (grid[i][j] == 4)
        {
          tiles[i][j] = new Floor(i,j,tiles);
          tiles[i][j].setExitFlag();// implement exit tile
        }
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
      y = rand.nextInt(ROWS);
      x = rand.nextInt(COLS);
    }
    for(Zombie zombie: zombies)
    {
      if (grid[x][y].contains(zombie.getX(), zombie.getY())) getRandomStart();
      break;
    }
    this.playerCharacter = new Player(this.grid[x][y].getCenterTileX(), this.grid[x][y].getCenterTileY(), this.tileHeight / 2, this.grid[x][y],
        this, imageLoader, false, 20);
    playerSub = grid[x][y];
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
          Trap trap = new Trap((int) this.grid[i][j].getCenterX(), (int) this.grid[i][j].getCenterY(), true);
          this.traps.add(trap);
          this.trapSub.add(grid[i][j]);
          this.grid[i][j].installTrap();
          System.out.println("Trap at [" + i + "][" + j + "]");
        }
      }
    }
  }

  private ArrayList<Trap> resetTraps()
  {
    System.out.println("Traps");
    traps = new ArrayList<>();
    for (Tile tile : trapSub)
    {
      Trap trap = new Trap((int) tile.getCenterX(), (int) tile.getCenterY(), true);
      this.traps.add(trap);
      tile.installTrap();
      System.out.println("(" + tile.getGridRow() + ", " + tile.getGridCol());

    }
    return traps;
  }

  private ArrayList<Zombie> resetZombies()
  {
    System.out.println("Zombie");

    zombies = new ArrayList<>();
    for (Tile tile : zombieSub)
    {
      Zombie zombie =  new Zombie((int) tile.getCenterX(), (int)tile.getCenterY(),
        this.tileHeight / 2, tile, this, GridOrientation.pickRandomOrientation(), imageLoader,true, 10);
      this.zombies.add(zombie);
      tile.installTrap();
      System.out.println("(" + tile.getGridRow() + ", " + tile.getGridCol());
    }
    return zombies;
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
        if (grid[i][j] instanceof Floor && !grid[i][j].hasTrap())
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
            Zombie zombone = new Zombie((int) grid[i][j].getCenterX(), (int) grid[i][j].getCenterY(),
                                        this.tileHeight / 2, grid[i][j], this, GridOrientation.pickRandomOrientation(), imageLoader,true, 10);
            zombies.add(zombone);
            zombieSub.add(grid[i][j]);
            System.out.println(zombone.getZType() + " Zombie at [" + i + "][" + j + "]");
          }
        }
      }
    }
    zombies.get(rand.nextInt(zombies.size())).setMasterZombie();
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
    Zombie master = null;
    for (Zombie zombie : zombies)
    {
      if (zombie.getZType().equals("Master"))
      {
        master = zombie;
      }
    }
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
          if (master != null)
          {
            path = pf.aStarSearch(master.getCurrentTile(), playerCharacter.getCurrentTile());
            master.setPath(path);
          }
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

  public void trapKill(Tile tile)
  {
    int y = tile.getGridRow();
    int x = tile.getGridCol();
    for(int i = y-1; i < y+2; i++)
    {
      for(int j = x-1; j < x+2; j++)
      {
        for (Zombie zombone : zombies)
        {
          if (zombone.intersects(grid[i][j])) zombone.setDead(true);
        }
        if (playerCharacter.intersects(grid[i][j])) playerCharacter.setDead(true);
      }
    }
  }

  /**
   * Burns tiles caught in trap explosions
   * @param tile  Tile containing trap
   */
  public void setCharredTile(Tile tile)
  {
    boolean hasTrap = false;
    int y = tile.getGridRow();
    int x = tile.getGridCol();
    for(int i = y-1; i < y+2; i++)
    {
      for(int j = x-1; j < x+2; j++)
      {
        if(i>=1 && j>=1 && i<ZombieHouseModel.ROWS-1 && j<ZombieHouseModel.COLS-1)
        {
          int xCount = 0;
          int yCount = 0;
          if (grid[i][j-1] instanceof Floor) xCount++;
          if (grid[i][j+1] instanceof Floor) xCount++;
          if (grid[i-1][j] instanceof Floor) yCount++;
          if (grid[i+1][j] instanceof Floor) yCount++;
          if (grid[i][j] instanceof Floor || xCount == 2 || yCount == 2)
          {
            if (grid[i][j].hasTrap) hasTrap = true;
            grid[i][j] = new CharredFloorTile(i, j, grid);
            grid[i][j].setBounds(j * tileWidth, i * tileHeight, tileWidth, tileHeight);
            if (hasTrap)
            {
              traps.add(new Trap((int) tile.getCenterX(),
                                (int) tile.getCenterY(), true));
              grid[i][j].installTrap();
            }
          }
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
    if(newLevel)
    {
      level++;
      traps = new ArrayList<>();
      zombies = new ArrayList<>();
      trapSub = new ArrayList<>();
      zombieSub = new ArrayList<>();
      this.mapGen = new MapGenerator(level+2);
      this.grid = this.translateTileImages(mapGen.getMap());
      this.setRandomTraps();
      this.initializeRandomZombies();
      this.playerCharacter = this.getRandomStart();
    }
    else
    {
      this.grid = this.translateTileImages(mapGen.getMap());
      this.playerCharacter = new Player(playerSub.getCenterTileX(), playerSub.getCenterTileY(), this.tileHeight / 2, playerSub,
          this, imageLoader, false, 20);
      this.zombies = resetZombies();
      this.traps = resetTraps();
    }

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
    boolean playerDead = false;
    for (Zombie zombone : zombies) playerDead |= zombone.intersects(playerCharacter);
    playerCharacter.setDead(playerDead);
  }
}
