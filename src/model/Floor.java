
/**
 * Created by Tess Daughton, September 13th, 2015
 */
package model;

public class Floor extends Tile
{
  private boolean movable;
  public Floor(int gridRow, int gridCol, Tile[][] grid)
  {
    super(gridRow, gridCol, grid);
    movable = true;
  }

    public boolean isMovable()
    {
      return this.movable;
    }

    public String getTileType()
    {
      return "F";
    }
}
