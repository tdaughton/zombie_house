package model;

/**
 * Created by L301126 on 9/12/15.
 */
public class Outside extends Tile
{
  private boolean movable;
  public Outside(int gridRow, int gridCol, Tile[][] grid)
  {
    super(gridRow,gridCol,grid);
    this.movable=true;
  }

  public boolean isMovable()
  {
    return this.movable;
  }

  public String getTileType()
  {
    return "Outside";
  }
}
