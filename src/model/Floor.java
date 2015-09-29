/**
 * Created by Tess Daughton, September 13th, 2015
 */

package model;

public class Floor extends Tile
{
  public Floor(int x, int y, int gridRow, int gridCol)
  {
    super(x, y, gridRow, gridCol);
    super.setType(2);
    movable = true;
  }
}
