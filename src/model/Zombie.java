/**
 * Created by Tess Daughton, September 13th, 2015
 */

package model;

import Resources.SpriteLoader;
import Resources.ImageLoader;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class serves as the model for Zombies in Zombie House. It specifies behavior unique to
 * Zombies among Movables including automatic walking, olfactory sense, and chasing down the Player.
 */
public class Zombie extends Movable implements Alive
{
  /**
   * Enumerator to give zombie types fancy names.
   */
  private enum WalkType
  {
    RANDOM, LINE
  }

  private static final double SPEED_WALK = 0.5f;
  private static final double RATE_ACT = 2.0f;
  private static final double DIST_SMELL = 7.0f;
  private static final Random rng = new Random();
  private ArrayList<Tile> path;
  private WalkType wType;
  private double theta;
  private double timeSinceUpdate;
  private boolean bumped;

  // I thought maybe depending on the types of zombie, how much damage they take
  // and how much health they recover each second may differ. If makes the game
  // too difficult, then let's not do this. :D -Miri
  private int originalHealth;
  private int currentHealth;
  private double healingRate;
  private double defenceRate;
  private double damage;
  private boolean dead;
  private Enum zombieOrientation;
  private SpriteLoader frames;

  /**
   * Full constructor
   * @param x                    X coordinate of center point (in pixels)
   * @param y                    Y coordinate of center point (in pixels)
   * @param radius               Radius of bounding circle (in pixels)
   * @param location             Tile location containing center point
   * @param grid                 Reference to Zombie House map
   */
  public Zombie(int x, int y, int radius, Tile location, Tile[][] grid, Enum direction, ImageLoader imageLoader)
  {
    super(x, y, radius, location, grid, direction);
    wType = (rng.nextBoolean() ? WalkType.RANDOM : WalkType.LINE);
    path = new ArrayList<>();
    frames = new SpriteLoader(imageLoader);
    zombieOrientation = direction;
    theta = rng.nextDouble() * 2.0f * Math.PI;
    timeSinceUpdate = 0.0f;
  }

  /**
   * Getter for Player's animation frames (sprites)
   * @return
   */
  public SpriteLoader getFrames()
  {
    return frames;
  }

  public String getZType()
  {
    if (wType == WalkType.RANDOM) return "Random";
    return "Line";
  }

  /**
   * Walk in predetermined direction or path
   */
  protected void walk(Enum direction, double timeElapsed)
  {
    double xDistance = (SPEED_WALK * super.getCurrentTile().getWidth() * Math.sin(theta) * timeElapsed);
    double yDistance = (SPEED_WALK * super.getCurrentTile().getHeight() * Math.cos(theta) * timeElapsed);
    //System.out.println("zxd "+xDistance+" zyd "+yDistance);
    if (theta < 0.40f || theta > 5.90f) direction = GridOrientation.EAST;
    else if (1.19f < theta && theta < 0.40f) direction = GridOrientation.NORTHEAST;
    else if (1.97f < theta && theta < 1.19f) direction = GridOrientation.NORTH;
    else if (2.76f < theta && theta < 1.97f) direction = GridOrientation.NORTHWEST;
    else if (3.54f < theta && theta < 2.76f) direction = GridOrientation.WEST;
    else if (4.33f < theta && theta < 3.54f) direction = GridOrientation.SOUTHWEST;
    else if (5.11f < theta && theta < 4.33f) direction = GridOrientation.SOUTH;
    else if (5.90f < theta && theta < 5.11f) direction = GridOrientation.SOUTHEAST;
    bumped = super.move(xDistance, yDistance, direction);
    getFrames().getRotatingZombieWalk();
  }

  public void update(double timeElapsed)
  {
    timeSinceUpdate += timeElapsed;
    if (timeSinceUpdate > RATE_ACT)
    {
      timeSinceUpdate = 0.0f;
      decisionEngine();
    }
    walk(zombieOrientation, timeElapsed);
  }

  /**
   * Decide how to walk based on WalkType and distance to player.
   */
  private void decisionEngine()
  {
    // Just in case you want to use Pathfinder here:
    // Pathfinder pf = new Pathfinder(tiles);
    // ArrayList<Tile> path = pf.aStarSearch(starting tile, target tile);
    // for(int i=path.size(); i > 0; i++) nextTile = path.get(i) //iterate backward.
    //
    // Or we can use stack instead. I think stack may be easier to use.
    // Stack path = pf.aStarSearch(starting tile, target tile)
    // path.pop => will return the next tile to go.
    // If you want to test stack, go to pathfinder and modify return type and
    // return statement of aStarPathfinder. - Miri

    /*int pcX = player.getX();
    int pcY = player.getY();
    if (getDistanceTo(pcX, pcY) < DIST_SMELL)
    {
      int pCol = (int)(pcX / location.getWidth());
      int pRow = (int)(pcY / location.getHeight());
      path = aStar(location, tiles[pRow][pCol]);
    }
    else
    {*/
    if (wType == WalkType.RANDOM || !bumped)
    {
      theta = rng.nextDouble() * Math.PI * 2.0f;
      bumped = true;
    }
    /*double nextX = this.SPEED_WALK * this.location.getWidth() * Math.sin(this.theta);// + x);
    double nextY = this.SPEED_WALK * this.location.getHeight() * Math.cos(this.theta);// + y);
    if (canMoveTo(nextX, nextY))
    {
      int nextCol = Math.min(0, Math.max(40,(int)(nextX / this.location.getWidth())));
      int nextRow = Math.min(0,Math.max(40,(int)(nextY / this.location.getHeight())));
      this.path.add(this.tiles[nextRow][nextCol]);
    }
    else if (this.wType == WalkType.LINE)
    {
      this.theta = rng.nextDouble() * Math.PI * 2.0;
    }*/
    //}
  }

  //============================================================================
  // Zombies should take damage from trap.
  //============================================================================
  @Override
  public void getBurn(int damage)
  {
    damage = damage;
  }

  @Override
  public int getHealth()
  {
    return currentHealth;
  }

  @Override
  public void updateCurrentStatus()
  {
    currentHealth -= damage;
    damage -= damage*defenceRate;

    if(currentHealth <= 0) dead = true;
  }

  @Override
  public boolean isDead()
  {
    return dead;
  }
}
