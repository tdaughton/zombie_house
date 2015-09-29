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


public class ZombieHouseModel
{
  private static Dimension userScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
  protected final static int MAX_SCREEN_WIDTH = (int) userScreenSize.getWidth();
  protected final static int MAX_SCREEN_HEIGHT = (int) userScreenSize.getHeight();
  protected final static int VISIBLE_X_TILES = 12;
  protected final static int VISIBLE_Y_TILES = 10;
  public static SoundLoader soundLoader;
  public static ImageLoader imageLoader;
  public final static int ROWS = 41;
  public final static int COLS = 48;
  private static final double ZOMBIE_SPAWN_RATE = 0.05f;
  private static final double TRAP_SPAWN_RATE = 0.05f;
  private Random rand = new Random();
  MapGenerator mapGen = new MapGenerator();
  private Player playerCharacter;
  private Tile playerSub;
  private ArrayList<Zombie> zombies;
  private ArrayList<Tile> zombieSub;
  private ArrayList<Trap> traps;
  private ArrayList<Tile> trapSub;
  private Tile grid[][];
  private double deltaSeconds;
  private int currentScreenWidth;
  private int currentScreenHeight;
  private int tileWidth;
  private int tileHeight;

  public ZombieHouseModel(SoundLoader soundLoader)
  {
    this.traps = new ArrayList<>();
    this.zombies = new ArrayList<>();
    this.zombieSub = new ArrayList<>();
    this.trapSub = new ArrayList<>();
    ZombieHouseModel.soundLoader =soundLoader;
    this.currentScreenWidth = MAX_SCREEN_WIDTH;
    this.currentScreenHeight = MAX_SCREEN_HEIGHT;
    this.tileWidth = currentScreenWidth / VISIBLE_X_TILES;
    this.tileHeight = currentScreenHeight / VISIBLE_Y_TILES;
    this.grid = this.translateTileImages(mapGen.getMap());
    imageLoader = new ImageLoader(this, tileWidth, tileHeight);
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

    while (!(grid[x][y] instanceof Floor))
    {
      x = rand.nextInt(ROWS);
      y = rand.nextInt(COLS);
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
            Zombie zombone = new Zombie((int) grid[i][j].getCenterX(), (int) grid[i][j].getCenterY(),
                                        this.tileHeight / 2, grid[i][j], this, GridOrientation.pickRandomOrientation(), imageLoader,true, 10);
            zombies.add(zombone);
            zombieSub.add(grid[i][j]);
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
   * This method takes a displacement as int xy-coordinate pair and an orientation and tells the ArrayList of Zombie objects to move
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
//        int r = rand.nextInt(100);
//        if (r % 99 == 0) SOUNDLOADER.playRandomDialogue(zombie, playerCharacter);
//        else if (r % 88 == 0) SOUNDLOADER.playRandomGrunt(zombie, playerCharacter);
        zombie.updateZombie(deltaSeconds);
      }
    }

  }


  public void attemptTrapAction()
  {
    if (playerCharacter.getCurrentTile().hasTrap())
    {
      for (Trap trap : traps)
        if ((trap.getX() == playerCharacter.getCurrentTile().getTrap().getX()) && (trap.getY() == playerCharacter.getCurrentTile().getTrap().getY()))
        {
          playerCharacter.grabTrap();
          traps.remove(trap);
          soundLoader.playPickUpTrap();
          break;
        }
    }
    else if (playerCharacter.getNumberOfTraps() > 0)
    {
      playerCharacter.installTrap();
      traps.add(new Trap((int) playerCharacter.getCurrentTile().getCenterX(), (int) playerCharacter.getCurrentTile()
          .getCenterY(), true));
      soundLoader.playDropTrap();

    }
  }

  /**
   * This method takes a displacement as int xy-coordinate pair and an orientation and tells the ArrayList of Zombie objects to move
   */
  public void checkTraps()
  {
    /*for(Trap trap: traps)
    {
      if(trap.explosionTriggered())
      {
        //
      }
    }*/
  }

  /**
   * Getter for the tile tiles
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
    return imageLoader;
  }

  public void setCharredTile(Tile tile)
  {
    boolean hasTrap = false;
    int x = tile.getGridRow();
    int y = tile.getGridCol();
    for(int i = x-1; i < x+2; i++)
    {
      for(int j=y-1; j< y+2; j++)
      {
        if(i>=0 && j>=0 && i<40 && j<40)
        {
          if(grid[i][j].hasTrap) hasTrap=true;
          grid[i][j] = new CharredFloorTile(i,j,grid);
          grid[i][j].setBounds(j * this.tileWidth, i * this.tileHeight, this.tileWidth, this.tileHeight);
          if(hasTrap)
          { traps.add(new Trap((int) tile.getCenterX(), (int) tile.getCenterY(), true));
            grid[i][j].installTrap();
          }
        }
      }
    }
  }

  public void restart()
  {
    this.grid = this.translateTileImages(mapGen.getMap());
    this.playerCharacter = new Player(playerSub.getCenterTileX(), playerSub.getCenterTileY(), this.tileHeight / 2,playerSub,
        this, imageLoader, false, 20);
    this.zombies = resetZombies();
    this.traps = resetTraps();

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
    this.checkTraps();
    //TODO: update zombies
    //TODO: update player
    //TODO: check zombie-zombie intersections
    //TODO: check zombie-player intersections
    //TODO: check movable-trap intersections
  }
}
