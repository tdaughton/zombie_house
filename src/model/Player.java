
/**
 * Created by Miri on 9/8/15.
 * edits: Tess Daughton, September 13th, 2015
 */
package model;

public class Player extends Movable
{
  private int x;
  private int y;
  private Tile location;
  private Tile[][] grid;
  private Enum playerOrientation;
  public Player(int x, int y, int radius, Tile location, Tile[][] grid, Enum playerOrientation)
  {
    super(x, y, radius,location,grid,playerOrientation);
    this.x=x;
    this.y=y;
    this.location = location;
    this.grid=grid;
    this.playerOrientation=playerOrientation;
  }

}
