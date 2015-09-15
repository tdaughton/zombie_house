package model;

/**
 * Created by Tess Daughton, September 13th 2015
 * Takes in a tile map given by ZombieHouseModel
 */
public class Map
{

  private final int COLS;
  private final int ROWS;
  private Tile[][] grid;
  private int tileHeight;
  private int tileWidth;

  public Map(Tile[][] grid, int rows , int cols, int width, int height)
  {
    this.COLS=cols;
    this.ROWS=rows;
    this.grid = grid;
    this.tileHeight=height;
    this.tileWidth=width;
    this.setBounds();
  }

  private void setBounds()
  {
    for(int i=0; i<ROWS; i++)
    {
      for (int j = 0; j < COLS; j++)
      {
        grid[i][j].setBounds(j * tileWidth, (j + 1) * tileWidth, i * tileHeight, (i + 1) * tileHeight);
//        System.out.print(i+ " tile bounds: " + (i * tileWidth) + " , " + ((i + 1) * tileWidth)+" | ");
//        System.out.print(j+ " tile bounds: " + j * tileHeight + "," + ((j + 1) * tileHeight));
//        System.out.println();
      }
    }
  }

  public Tile[][] getGrid()
  {
    return this.grid;
  }
}
