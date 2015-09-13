package model;

/**
 * Created by L301126 on 9/12/15.
 */
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
      return "Floor";
    }
}
