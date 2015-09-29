package model;

/**
 * Created by Miri on 9/29/15.
 */
public class Obstacle extends Wall
{
  public Obstacle(int gridRow, int gridCol, Tile[][] grid)
  {
    super(gridRow, gridCol, grid);
    super.setType(1);
    super.setObstacle(true);
    this.movable = false;
  }
}
