/**
 * Created by Tess Daughton, September 13th, 2015
 */

package model;

public class Floor extends Tile
{
  public Floor(int gridRow, int gridCol)
  {
    super(gridRow, gridCol, true, 2);
  }

  public String getTileType()
  {
    return "F";
  }
}
