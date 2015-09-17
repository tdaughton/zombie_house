/**
 * Created by Tess Daughton, September 13th, 2015
 */

package model;

public class Floor extends Tile
{
  public Floor(int gridRow, int gridCol, Tile[][] grid)
  {
    super(gridRow, gridCol, grid);
    movable = true;
  }

  public String getTileType()
  {
    return "F";
  }
}
