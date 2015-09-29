/**
 * Created by Tess Daughton, September 13th, 2015
 */

package model;

public class Outside extends Tile
{
  public Outside(int gridRow, int gridCol)
  {
    super(gridRow, gridCol, true, 0);
  }

  public String getTileType()
  {
    return "O";
  }
}
