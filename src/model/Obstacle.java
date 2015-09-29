package model;

/**
 * Created by Miri on 9/29/15.
 */
public class Obstacle extends Wall
{
  public Obstacle(int y, int x, int gridRow, int gridCol, Tile[][] grid)
  {
    super(y, x, null);
    super.setType(1);

    this.movable = false;
  }
}
