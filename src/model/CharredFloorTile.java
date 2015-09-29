/**
 * Tess Daughton, September 24th, 2015
 */

package model;

public class CharredFloorTile extends Tile
{

  public CharredFloorTile(int gridRow, int gridCol)
  {
    super(gridRow, gridCol);
  }

  public String getTileType()
  {
    return "F";
  }
}
