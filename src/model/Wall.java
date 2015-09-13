
/**
 * Created by Tess Daughton, September 13th, 2015
*/
package model;

public class Wall extends Tile
{
  private boolean movable;
  public Wall(int gridRow, int gridCol, Tile[][] grid)
  {
    super(gridRow,gridCol,grid);
    this.movable = false;
  }

  public boolean isMovable()
  {
    return this.movable;
  }

  public String getTileType()
  {
    return "Wall";
  }
}
