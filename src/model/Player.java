
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
  public Player(int x, int y, int radius, Tile location, Tile[][] grid)
  {
    super(x, y, radius,location,grid);
    this.x=x;
    this.y=y;
    this.location = location;
  }

private void setX(int x1)
{
  this.x=x1;
}
  private void setY(int y1)
  {
    this.y=y1;
  }
  private void setCurrentTile(int x, int y) {
    this.location = grid[x][y];
  }


}
