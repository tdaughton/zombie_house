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
  private final static int MAX_SCREEN_WIDTH = (int)userScreenSize.getWidth();
  private final static int MAX_SCREEN_HEIGHT = (int)userScreenSize.getHeight();
  private final static int VISIBLE_X_TILES = 23;
  private final static int VISIBLE_Y_TILES = 12;
  private final static int ROWS = 40;
  private final static int COLS = 40;
  private static final double ZOMBIE_SPAWN_RATE = 0.1f;
  private static final double TRAP_SPAWN_RATE = 0.01f;
  private SoundLoader soundLoader;
  private ImageLoader imageLoader;
  private Random rand = new Random();
  private Player playerCharacter;
  private ArrayList<Zombie> zombies;
  private ArrayList<Trap> traps;
  //private Map map;
  private Tile grid[][];
  private double deltaSeconds;
  private int currentScreenWidth;
  private int currentScreenHeight;
  private int tileWidth;
  private int tileHeight;

  public ZombieHouseModel()
  {
    traps = new ArrayList<>();
    zombies = new ArrayList<>();
    MapGenerator mapGen = new MapGenerator(ROWS, COLS);
    this.currentScreenWidth = MAX_SCREEN_WIDTH;
    this.currentScreenHeight = MAX_SCREEN_HEIGHT;
    this.tileWidth = currentScreenWidth/VISIBLE_X_TILES;
    this.tileHeight = currentScreenHeight/VISIBLE_Y_TILES;
    this.grid = this.translateTileImages(mapGen.getMap());
    //this.map = new Map(tiles, ROWS, COLS);
    this.imageLoader = new ImageLoader(this, tileWidth, tileHeight);
    this.soundLoader = new SoundLoader();
    this.setRandomTraps();
    this.initializeRandomZombies();
    playerCharacter=this.getRandomStart();

  }

  /**
   * This method translates a 2D int array into a 2D Tile array
   * @param grid  input 2D int array representing some tile types
   * @return  a 2D Tile array representing the Zombie House
   */
  private Tile[][] translateTileImages(int[][] grid)
  {
    Tile[][] tiles = new Tile[ROWS][COLS];

    for (int i = 0; i < ROWS; i++)
    {
      for (int j = 0; j < COLS; j++)
      { if (grid[i][j] != 1) tiles[i][j] = new Wall(i, j, tiles);
        else tiles[i][j] = new Floor(i, j, tiles);
        tiles[i][j].setBounds(j * this.tileWidth, i * this.tileHeight, this.tileWidth, this.tileHeight);
      }
    }

    return tiles;
  }

  /**
   * Parses the Zombie House map and places the Player at a random location
   * @return  new Player located at random tile
   */
  private Player getRandomStart()
  {
    int x = 0;
    int y = 0;

    while (!(grid[x][y] instanceof Floor))
    {
      x = rand.nextInt(COLS);
      y = rand.nextInt(ROWS);
      this.playerCharacter = new Player(this.grid[x][y].getCenterTileX(), this.grid[x][y].getCenterTileY(), this.tileHeight / 2,this.grid[x][y],
              this, GridOrientation.pickRandomOrientation(), this.imageLoader);
    }
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
    this.tileWidth = Math.max(currentScreenWidth/VISIBLE_X_TILES, currentScreenHeight/VISIBLE_Y_TILES);
    this.tileHeight = tileWidth;
  }

  /**
   * Called inside actionPerformed in ZombieHouseFrame to periodically update frame height
   * Setter for window height
   * @param y  new height (in pixels)
   */
  public void setCurrentScreenHeight(int y)
  {
    this.currentScreenHeight = y;
    this.tileWidth = Math.max(currentScreenWidth/VISIBLE_X_TILES, currentScreenHeight/VISIBLE_Y_TILES);
    this.tileHeight = tileWidth;
  }

  /**
   * Places random traps throughout the map
   */
  private void setRandomTraps()
  {
    for (int i = 1; i < ROWS-1; i++)
    {
      for (int j = 1; j < COLS-1; j++)
      {
        if (grid[i][j] instanceof Floor && rand.nextDouble() < TRAP_SPAWN_RATE)
        {
          this.traps.add(new Trap((int)this.grid[i][j].getCenterX(), (int)this.grid[i][j].getCenterY(), true));
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

    for (int i = 1; i < ROWS-1; i++)
    {
      for (int j = 1; j < COLS-1; j++)
      {
        valid = 0;
        if (grid[i][j] instanceof Floor)
        {
          for (int k = i-1; k < i+2; k++)
          {
            for (int l = j-1; l < j+2; l++)
            {
              if (this.grid[k][l] instanceof Wall) valid++;
            }
          }
          if (valid < 4 && rand.nextDouble() < ZOMBIE_SPAWN_RATE)
          {
            Zombie zombone = new Zombie((int)grid[i][j].getCenterX(), (int)grid[i][j].getCenterY(), this.tileHeight / 2, grid[i][j], this, GridOrientation.pickRandomOrientation(), imageLoader);
            zombies.add(zombone);
            System.out.println(zombone.getZType()+" Zombie at [" + i + "][" + j + "]");
          }
        }
      }
    }
  }

  /*public void setMap(Tile[][] tiles)
  {
    //map.setMapGrid(tiles);
  }*/

  /**
   * This method takes a displacement as int xy-coordinate pair and an orientation and tells the Player object to move
   * @param dX       X-displacement (in pixels)
   * @param dY       Y-displacement (in pixels)
   * @param dir      orientation on the tiles
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
    for(Zombie zombie: zombies)
    {
      //zombie.walk(zombie.getPlayerOrientation(), deltaSeconds);
      zombie.update(deltaSeconds);
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

  public int getRows()
  {
    return ROWS;
  }

  public int getCols()
  {
    return COLS;
  }

  /**
   * Getter for the tile tiles
   * @return  reference to the tile array
   */
  public Tile[][] getGrid()
  {
    return this.grid;
  }

  public Tile getTile(int gRow, int gCol)
  {
    return this.grid[gRow][gCol];
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

  public SoundLoader getSoundLoader()
  {
    return this.soundLoader;
  }

  public void setCharredTile(Tile tile)
  {
    //System.out.println(tile.getGridRow());
    int x = tile.getGridRow();
    int y = tile.getGridCol();
    for(int i = x-1; i < x+2; i++)
    {
      for(int j=y-1; j< y+2; j++)
      {
        if(i>=0 && j>=0 && i<40 && j<40)
        {
          grid[i][j] = new CharredFloorTile(i,j,grid);
          grid[i][j].setBounds(j * this.tileWidth, i * this.tileHeight, this.tileWidth, this.tileHeight);
        }
      }
    }
    System.out.println(tile.getGridRow());
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
