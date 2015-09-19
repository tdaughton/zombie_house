/**
 * Created by Tess Daughton, September 13th, 2015
 * This class will utilize a MapGenerator object to get a random Tile[][] map for the game.
 */

package model;

import java.awt.*;
import java.util.Random;
import java.util.Collection;

public class ZombieHouseModel
{
  private static Dimension userScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
  protected final static int MAX_SCREEN_WIDTH = (int)userScreenSize.getWidth();
  protected final static int MAX_SCREEN_HEIGHT = (int)userScreenSize.getHeight();
  protected final static int ROWS = 40;
  protected final static int COLS = 40;
  public static boolean upKey = false;
  public static boolean downKey = false;
  public static boolean leftKey = false;
  public static boolean rightKey= false;
  public static boolean runKey = false;
  private static final double zombieSpawnRate = 0.01;
  private static final double trapSpawnRate = 0.01;
  public Random rand = new Random();
  private Player sprite;
  private Collection<Zombie> zombies;
  private Collection<Trap> traps;

  protected Map map;

  private Tile grid[][];

  public ZombieHouseModel()
  {
    MapGenerator mapGen = new MapGenerator(40, 40);
    //grid = this.translateTileImages(new GridReader().readGrid(), ROWS, COLS);
    grid = this.translateTileImages(mapGen.getMap(), ROWS, COLS);
    sprite = this.getRandomStart(grid);

    map = new Map(grid, ROWS, COLS, MAX_SCREEN_WIDTH / 12, MAX_SCREEN_HEIGHT / 10);
  }

  private Tile[][] translateTileImages(int[][] grid, int rows, int cols)
  {
    Tile[][] tiles = new Tile[rows][cols];
    int tileWidth = MAX_SCREEN_WIDTH/12;
    int tileHeight = MAX_SCREEN_HEIGHT/10;


    for (int i = 0; i < rows; i++)
    {
      for (int j = 0; j < cols; j++)
      {
//        if (grid[i][j] == 48) tiles[i][j] = new Outside(i, j, tiles);
//        else if (grid[i][j] == 49) tiles[i][j] = new Floor(i, j, tiles);
//        else tiles[i][j] = new Wall(i, j, tiles);
        if (grid[i][j] != 1) tiles[i][j] = new Wall(i, j, tiles);
        else tiles[i][j] = new Floor(i, j, tiles);
        tiles[i][j].setBounds(j * tileWidth, i * tileHeight, tileWidth, tileHeight);
      }
    }
    return tiles;
  }

  private Player getRandomStart(Tile[][] map)
  {
    int x= rand.nextInt(39);
    int y = rand.nextInt(39);

    if(map[x][y] instanceof Floor)
    {
      sprite = new Player(map[x][y].getCenterTileX(),map[x][y].getCenterTileY(),60,map[x][y],
          map,GridOrientation.pickRandomOrientation());
    }
    else getRandomStart(map);
    return sprite;
  }

  public Map getMap()
  {
    return this.map;
  }
  public Player getPlayer()
  {
    return this.sprite;
  }

  public void update()
  {
    //TODO: update zombies
    //TODO: update player
    //TODO: check zombie-zombie intersections
    //TODO: check zombie-player intersections
    //TODO: check movable-trap intersections
  }
}
