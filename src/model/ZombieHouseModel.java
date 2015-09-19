/**
 * Created by Tess Daughton, September 13th, 2015
 * This class will utilize a MapGenerator object to get a random Tile[][] map for the game.
 */

package model;

import java.awt.*;

public class ZombieHouseModel
{
  private static Dimension userScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
  protected final static int MAX_SCREEN_WIDTH = (int)userScreenSize.getWidth();
  protected final static int MAX_SCREEN_HEIGHT = (int)userScreenSize.getHeight();
  protected final static int ROWS = 40;
  protected final static int COLS = 40;
  protected Map map;

  private Tile grid[][];

  public ZombieHouseModel()
  {
    MapGenerator mapGen = new MapGenerator(40, 40);
    grid = this.translateTileImages(mapGen.getMap(), ROWS, COLS);
    //grid = this.translateTileImages(new GridReader().readGrid(), ROWS, COLS);

    map = new Map(grid, ROWS, COLS, MAX_SCREEN_WIDTH / 12, MAX_SCREEN_HEIGHT / 10);
  }

  private Tile[][] translateTileImages(int[][] grid, int rows, int cols)
  {
    Tile[][] tiles = new Tile[rows][cols];


    for (int i = 0; i < rows; i++)
    {
      for (int j = 0; j < cols; j++)
      {
        /*if (grid[i][j] == 48) tiles[i][j] = new Outside(i, j, tiles);
        else if (grid[i][j] == 49) tiles[i][j] = new Floor(i, j, tiles);
        else tiles[i][j] = new Wall(i, j, tiles);*/
        if (grid[i][j] != 0) tiles[i][j] = new Floor(i, j, tiles);
        else tiles[i][j] = new Wall(i, j, tiles);
      }
    }
    return tiles;
  }

  public Map getMap()
  {
    return this.map;
  }
}
