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
  private final static int VISIBLE_X_TILES = 20;
  private final static int VISIBLE_Y_TILES = 12;
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
    this.traps = new ArrayList<>();
    this.zombies = new ArrayList<>();
    this.trapSub = new ArrayList<>();
    this.zombieSub = new ArrayList<>();
    ZombieHouseModel.soundLoader =soundLoader;
    this.currentScreenWidth = MAX_SCREEN_WIDTH;
    this.currentScreenHeight = MAX_SCREEN_HEIGHT;
    this.tileWidth = currentScreenWidth / VISIBLE_X_TILES;
    this.tileHeight = currentScreenHeight / VISIBLE_Y_TILES;
    this.mapGen = new MapGenerator(2);
    this.rand = new Random();
    this.grid = mapGen.getMap();
    imageLoader = new ImageLoader(this, tileWidth, tileHeight);
    imageLoader.readImages();
    this.pf = new Pathfinder(this);
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
//  private Tile[][] translateTileImages(Tile[][] grid)
//  {
//    Tile[][] tiles = new Tile[ROWS][COLS];
//
//    for (int i = 0; i < ROWS; i++)
//    {
//      for (int j = 0; j < COLS; j++)
//      {
//        if (grid[i][j].type == 0)
//        {
//          tiles[i][j] = new Outside(i, j, tiles);
//        } else if (grid[i][j].type == 2)
//        {
//          tiles[i][j] = new Floor(i, j, tiles);
//          if (grid[i][j].hasExitFlag())
//          {
//            grid[i][j].setExitFlag();// implement exit tile
//          }
//        } else
//        {
//          tiles[i][j] = new Wall(i, j, tiles);
//        }
//        tiles[i][j]
//        .setBounds(j * this.tileWidth, i * this.tileHeight, this.tileWidth,
//                  this.tileHeight);
//      }
//    }
//
//    return tiles;
//  }

  /**
   * Parses the Zombie House map and places the Player at a random location
   *
   * @return new Player located at random tile
   */
  private Player getRandomStart()
  {
    int x = 0;
    int y = 0;
    boolean valid = false;

    while (!valid || !(this.grid[y][x] instanceof Floor))
    {
      y = this.rand.nextInt(ROWS);
      x = this.rand.nextInt(COLS);
      //valid = this.grid[y][x].hasZombie(this.zombies);
      valid = true;
      for (Zombie zombone : this.zombies)
      {
        valid &= (zombone.getDistanceTo(this.grid[y][x].getCenterX(), this.grid[y][x].getCenterY()) > Zombie.DIST_SMELL * this.tileWidth);
      }
    }
    this.playerCharacter = new Player(this, this.grid[y][x], false);
    this.playerSub = this.grid[y][x];
    return this.playerCharacter;
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
        if (this.grid[i][j] instanceof Floor && this.rand.nextDouble() < TRAP_SPAWN_RATE)
        {
          Trap trap = new Trap((int) this.grid[i][j].getCenterX(), (int) this.grid[i][j].getCenterY(), true);
          this.traps.add(trap);
          this.trapSub.add(this.grid[i][j]);
          this.grid[i][j].installTrap(trap);
          //System.out.println("Trap at [" + i + "][" + j + "]");
        }
      }
    }
  }

  private ArrayList<Trap> resetTraps()
  {
    //System.out.println("Traps");
    this.traps.clear();
    for (Tile tile : this.trapSub)
    {
      Trap trap = new Trap((int) tile.getCenterX(), (int) tile.getCenterY(), true);
      this.traps.add(trap);
      tile.installTrap(trap);
      //System.out.println("(" + tile.getGridRow() + ", " + tile.getGridCol() + ")");
    }
    return this.traps;
  }

  private ArrayList<Zombie> resetZombies()
  {
    //System.out.println("Zombie");

    this.zombies.clear();
    for (Tile tile : this.zombieSub)
    {
      Zombie zombie =  new Zombie(this, tile, true);
      this.zombies.add(zombie);
      //System.out.println("(" + tile.getGridRow() + ", " + tile.getGridCol() + ")");
    }
    return this.zombies;
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
        if (this.grid[i][j] instanceof Floor && !this.grid[i][j].hasTrap())
        {
          for (int k = i - 1; k < i + 2; k++)
          {
            for (int l = j - 1; l < j + 2; l++)
            {
              if (this.grid[k][l] instanceof Wall) valid++;
            }
          }
          if (valid < 4 && this.rand.nextDouble() < ZOMBIE_SPAWN_RATE)
          {
            Zombie zombone = new Zombie(this, this.grid[i][j], true);
            this.zombies.add(zombone);
            this.zombieSub.add(this.grid[i][j]);
            //System.out.println(zombone.getZType() + " Zombie at [" + i + "][" + j + "]");
          }
        }
      }
    }
    if (this.zombies.isEmpty())
    {
      int x = 0;
      int y = 0;
      while (!(this.grid[y][x] instanceof Floor) || this.grid[y][x].hasTrap())
      {
        y = this.rand.nextInt(ROWS);
        x = this.rand.nextInt(COLS);
      }
      Zombie master = new Zombie(this, this.grid[y][x], true);
      master.setMasterZombie();
      this.zombies.add(master);
      this.zombieSub.add(this.grid[y][x]);
    }
    else
    {
      this.zombies.get(this.rand.nextInt(this.zombies.size())).setMasterZombie();
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
    this.playerCharacter.walk(dX, dY, dir, running, this.deltaSeconds);
  }

  /**
   * This method tells the ArrayList of Zombie objects to move
   */
  public void updateZombies()
  {
    Zombie master = null;
    for (Zombie zombie : this.zombies)
    {
      if (zombie.getZType().equals("Master"))
      {
        master = zombie;
      }
    }
    for (Zombie zombie : this.zombies)
    {
      if (zombie.isDead())
      {
        this.zombies.remove(zombie);
        break;
      }
      else
      {
        if (zombie.getDistanceTo(this.playerCharacter) < Zombie.DIST_SMELL * this.tileWidth)
        {
          ArrayList<Tile> path = this.pf.aStarSearch(zombie.getCurrentTile(), this.playerCharacter.getCurrentTile());
          zombie.setPath(path);
          if (master != null)
          {
            path = this.pf.aStarSearch(master.getCurrentTile(), this.playerCharacter.getCurrentTile());
            master.setPath(path);
          }
        }
        else
        {
          zombie.deletePath();
        }
        zombie.updateZombie(this.deltaSeconds);
      }
    }
  }

  /**
   * Gets/sets traps according to Player's current position
   */
  public void attemptTrapAction()
  {
    if (this.playerCharacter.getCurrentTile().hasTrap())
    {
      Trap theTrap = this.playerCharacter.getCurrentTile().getTrap();
      this.playerCharacter.grabTrap();
      this.traps.remove(theTrap);
      soundLoader.playPickUpTrap();
      /*for (Trap trap : this.traps)
      {
        if ((trap.getX() == this.playerCharacter.getCurrentTile().getTrap().getX()) && (trap.getY() == this.playerCharacter.getCurrentTile().getTrap().getY()))
        {
          this.playerCharacter.grabTrap();
          this.traps.remove(trap);
          soundLoader.playPickUpTrap();
          break;
        }
      }*/
    }
    else if (this.playerCharacter.getNumberOfTraps() > 0)
    {
      Trap trap = new Trap((int) this.playerCharacter.getCurrentTile().getCenterX(), (int) this.playerCharacter.getCurrentTile().getCenterY(), true);
      this.playerCharacter.installTrap(trap);
      this.traps.add(trap);
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
   * Getter for single tile specified by row and column
   * @param gridRow  row of tile in grid
   * @param gridCol  column of tile in grid
   * @return  specified tile if it exists, otherwise null
   */
  public Tile getTile(int gridRow, int gridCol)
  {
    Tile res;
    if (0 <= gridRow && gridRow < ROWS && 0 <= gridCol && gridCol < COLS) res = this.grid[gridRow][gridCol];
    else res = null;
    return res;
  }

  public int getTileWidth()
  {
    return this.tileWidth;
  }

  public int getTileHeight()
  {
    return this.tileHeight;
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

  public int getLevel()
  {
    return this.level;
  }

  public void trapKill(Tile tile)
  {
    int y = tile.getGridRow();
    int x = tile.getGridCol();
    for(int i = y-1; i < y+2; i++)
    {
      for(int j = x-1; j < x+2; j++)
      {
        for (Zombie zombone : this.zombies)
        {
          if (zombone.intersects(this.grid[i][j])) zombone.setDead(true);
        }
        if (this.playerCharacter.intersects(this.grid[i][j])) this.playerCharacter.setDead(true);
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
        if(i>=1 && j>=1 && i<ROWS-1 && j<COLS-1)
        {
          int xCount = 0;
          int yCount = 0;
          if (this.grid[i][j-1] instanceof Floor) xCount++;
          if (this.grid[i][j+1] instanceof Floor) xCount++;
          if (this.grid[i-1][j] instanceof Floor) yCount++;
          if (this.grid[i+1][j] instanceof Floor) yCount++;
          if (this.grid[i][j] instanceof Floor || xCount == 2 || yCount == 2)
          {
            if (this.grid[i][j].hasTrap()) hasTrap = true;
            this.grid[i][j] = new CharredFloorTile(i, j);
            this.grid[i][j].setBounds(j * this.tileWidth, i * this.tileHeight, this.tileWidth, this.tileHeight);
            if (hasTrap)
            {
              Trap trap = new Trap((int) tile.getCenterX(), (int) tile.getCenterY(), true);
              this.traps.add(trap);
              this.grid[i][j].installTrap(trap);
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
      this.level++;
      traps = new ArrayList<>();
      zombies = new ArrayList<>();
      trapSub = new ArrayList<>();
      zombieSub = new ArrayList<>();
      this.mapGen = new MapGenerator(level+2);
      this.grid = mapGen.getMap();
      this.setRandomTraps();
      this.initializeRandomZombies();
      this.playerCharacter = this.getRandomStart();
    }
    else
    {
      this.grid = this.mapGen.getMap();
      this.playerCharacter = new Player(this, this.playerSub, false);
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
    for (Zombie zombone : this.zombies) playerDead |= zombone.intersects(this.playerCharacter);
    this.playerCharacter.setDead(playerDead);
  }
}
