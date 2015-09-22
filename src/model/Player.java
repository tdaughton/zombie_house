/**
 * Created by Miri on 9/8/15.
 * edits: Tess Daughton, September 13th, 2015
 */
package model;

import Resources.SpriteLoader;

public class Player extends Movable
{
  private static final double DIST_SIGHT = 5.0f;
  private static final double DIST_HEAR = 10.0f;
  private static final double SPEED_WALK = 1.0f;
  private static final double SPEED_MULT = 2.0f;
  private static final double STAM_MAX = 5.0f;
  private static final double STAM_REGEN = 0.2f;
  //Player keeps track of its own animation frames (sprites)
  private SpriteLoader frames;
  // Inventory is going to take traps only. If there will be any other items
  // then we can use HashTable<String itemName, ArrayList<Item>> or something
  // like that.
  private int numberOfTraps;
  private int x, y;

  /**
   * Full constructor
   * @param x                    X coordinate of center point (in pixels)
   * @param y                    Y coordinate of center point (in pixels)
   * @param radius               Radius of bounding circle (in pixels)
   * @param location             Tile location containing center point
   * @param grid                 Reference to Zombie House map
   */
  public Player(int x, int y, int radius, Tile location, Tile[][] grid, Enum playerOrientation)
  {
    super(x, y, radius,location,grid,GridOrientation.pickRandomOrientation());
    //currently SpriteLoader only handles player sprites
    frames = new SpriteLoader();
    numberOfTraps = 0;
  }

  /**
   * Getter for Player's animation frames (sprites)
   * @return
   */
  public SpriteLoader getFrames()
  {
    return frames;
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
