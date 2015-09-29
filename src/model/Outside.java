/**
 * Created by Tess Daughton, September 13th, 2015
 */

package model;

public class Outside extends Tile
{

  public Outside(int x, int y, int gridRow, int gridCol)
  {
    super(x, y, gridRow, gridCol);
    super.setType(0);

    this.movable = true;
  }
}
