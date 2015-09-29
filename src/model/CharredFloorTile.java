
/**
 * Tess Daughton, September 24th, 2015
 */

package model;

public class CharredFloorTile extends Floor
{

  public CharredFloorTile(int x, int y, int gridRow, int gridCol)
  {
    super(x, y, gridRow, gridCol);
    movable = true;
  }

  public String getTileType()
  {
    return "F";
  }
}