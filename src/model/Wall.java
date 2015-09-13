package model;

/**
 * Created by L301126 on 9/12/15.
*/
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
