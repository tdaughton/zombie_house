/**
 * Created by Tess Daughton, September 13th, 2015
 */

package model;

public class Outside extends Tile
{
  public Outside(int gridRow, int gridCol, Tile[][] grid)
  {
    super(gridRow, gridCol, grid);
    this.movable = true;
  }

  public String getTileType()
  {
    return "O";
  }
}
