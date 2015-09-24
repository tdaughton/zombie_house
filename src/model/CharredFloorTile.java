
/**
 * Tess Daughton, September 24th, 2015
 */

package model;

public class CharredFloorTile extends Tile
{

  public CharredFloorTile(int gridRow, int gridCol, Tile[][] grid)
  {
    super(gridRow, gridCol, grid);
    movable = true;
  }

  public String getTileType()
  {
    return "F";
  }
}