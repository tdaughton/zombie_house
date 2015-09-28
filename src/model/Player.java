/**
 * Created by Miri on 9/8/15.
 * edits: Tess Daughton, September 13th, 2015
 */
package model;

import Resources.SpriteLoader;
import Resources.ImageLoader;

public class Player extends Movable
{
  private static final double DIST_SIGHT = 5.0f;
  private static final double DIST_HEAR = 10.0f;
  private static final double SPEED_WALK = 1.0f;
  private static final double SPEED_MULT = 2.0f;
  private static final double STAM_MAX = 5.0f;
  private static final double STAM_REGEN = 0.2f;


  private SpriteLoader frames;
  // Inventory is going to take traps only. If there will be any other items
  // then we can use HashTable<String itemName, ArrayList<Item>> or something
  // like that.
  private int numberOfTraps;
  private double stamina;
  private boolean running;

  /**
   * Full constructor
   * @param x                    X coordinate of center point (in pixels)
   * @param y                    Y coordinate of center point (in pixels)
   * @param radius               Radius of bounding circle (in pixels)
   * @param location             Tile location containing center point
   * @param grid                 Reference to Zombie House map
   */
  public Player(double x, double y, double radius, Tile location, Tile[][] grid, ImageLoader imageLoader, boolean running, int health)
  {
    super(x, y, radius,location,grid,running, health);
    this.frames = new SpriteLoader(imageLoader);
    this.numberOfTraps = 3;
    this.stamina = STAM_MAX;
  }

  /**
   * Getter for Player's animation frames (sprites)
   * @return sprite loader
   */
  public SpriteLoader getFrames()
  {
    return frames;
  }

  public void update(double timeElapsed)
  {
    if (!running) stamina = Math.min(STAM_MAX,stamina+STAM_REGEN);
    stamina = Math.max(0, stamina);
  }

  public void walk(double dX, double dY, Enum direction, boolean boost, double timeElapsed)
  {
    this.running = boost;
    stamina -= (running ? timeElapsed : 0.0f);
    double xDistance = (running && stamina > 0.0f ? SPEED_MULT : 1.0f) * SPEED_WALK * super.getCurrentTile().getWidth() * timeElapsed * dX;
    double yDistance = (running && stamina > 0.0f ? SPEED_MULT : 1.0f) * SPEED_WALK * super.getCurrentTile().getHeight() * timeElapsed * dY;
    //System.out.println("pxd "+xDistance+" pyd "+yDistance);
    super.move(xDistance, yDistance, direction);
    frames.getRotatingRun();
  }


  //============================================================================
  // This method will allow the player to grab a trap from the tile if there
  // exists a trap spawned in that tile.
  //============================================================================
  public void grabTrap()
  {
    numberOfTraps++;
    this.getCurrentTile().removeTrap();
  }

  public void installTrap()
  {
    numberOfTraps--;
    this.getCurrentTile().installTrap();
  }

  public int getNumberOfTraps()
  {
    return numberOfTraps;
  }

  public double getPlayerSight()
  {
    return DIST_SIGHT;
  }
  public double getPlayerHearing()
  {
    return DIST_HEAR;
  }

  public double getPlayerStamina()
  {
    return stamina;
  }



}
