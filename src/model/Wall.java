/**
 * Created by Tess Daughton, September 13th, 2015
 */

package model;

public class Wall extends Tile
{
  private boolean obstacle = false;

  public Wall(int gridRow, int gridCol, Tile[][] grid)
  {
    super(gridRow, gridCol, grid);
    this.movable = false;
  }

  public String getTileType()
  {
    return "W";
  }

  public boolean isObstacle() { return obstacle; }

  public void setObstacle(boolean obstacle)
  {
    this.obstacle = obstacle;
  }
}
