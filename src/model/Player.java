
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

  // Inventory is going to take traps only. If there will be any other items
  // then we can use HashTable<String itemName, ArrayList<Item>> or something
  // like that.
  private int numberOfTraps;

  public Player(int x, int y, int radius, Tile location, Tile[][] grid)
  {
    super(x, y, radius,location,grid,GridOrientation.pickRandomOrientation());
    this.x=x;
    this.y=y;
    this.location = location;

    numberOfTraps = 0;
    this.grid=grid;
    this.playerOrientation=playerOrientation;
  }

  //============================================================================
  // This method will allow the player to grab a trap from the tile if there
  // exists a trap spawned in that tile.
  //============================================================================
  public void grabTrap()
  {
    numberOfTraps++;
  }

  public void installTrap()
  {
    numberOfTraps--;
  }

  public int getNumberOfTraps()
  {
    return numberOfTraps;
  }

  private void setX(int x1)
  {
    this.x = x1;
  }

  private void setY(int y1)
  {
    this.y = y1;
  }

  private void setCurrentTile(int x, int y)
  {
    this.location = grid[x][y];
  }
}
