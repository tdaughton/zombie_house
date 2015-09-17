/**
 * Created by Tess Daughton, September 13th, 2015
 */

package model;


public class Zombie extends Movable
{
  private static final double SPEED_WALK = 0.5f;
  private static final double RATE_ACT = 2.0f;
  private static final double DIST_SMELL = 7.0f;
  public Zombie(int x, int y, int radius, Tile location, Tile[][] grid)
  {
    super(x, y, radius, location, grid, GridOrientation.pickRandomOrientation());
  }

  private void setX(int x1)
  {
    this.x = x1;
  }

  private void setY(int y1)
  {
    this.y = y1;
  }
}
