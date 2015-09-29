/**
 * Created by Tess Daughton, September 13th, 2015
 */

package model;

public class Wall extends Tile
{

  public Wall(int x, int y, int gridRow, int gridCol)
  {
    super(x, y, gridRow, gridCol);
    super.setType(1);

    this.movable = false;
  }
}
