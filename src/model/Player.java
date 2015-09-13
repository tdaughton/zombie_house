
/**
 * Created by Miri on 9/8/15.
 * edits: Tess Daughton, September 13th, 2015
 */
package model;

import view.HouseImage;

public class Player extends Movable
{
  private int x;
  private int y;
  public Player(int x, int y, int radius, Tile location, Tile[][] grid, HouseImage house)
  {
    super(x, y, radius,location,grid,house);
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
