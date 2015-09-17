/**
 * Created by Tess Daughton, September 13th, 2015
 */

package model;


public class Zombie extends Movable
{
  private int x;
  private int y;
  public Zombie(int x, int y, int radius, Tile location, Tile[][] grid)
  {
    super(x, y, radius,location,grid);
    this.x = x;
    this.y=y;
  }

  private void setX(int x1)
  {
    this.x=x1;
  }
  private void setY(int y1)
  {
    this.y=y1;
  }
}
