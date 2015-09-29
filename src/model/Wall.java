/**
 * Created by Tess Daughton, September 13th, 2015
 */

package model;

public class Wall extends Tile
{
  public Wall(int gridRow, int gridCol)
  {
    super(gridRow, gridCol, false);
  }

  public String getTileType()
  {
    return "W";
  }
}
