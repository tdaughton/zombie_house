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
public class Zombie extends Movable
{
  /**
   * Enumerator to give zombie types fancy names.
   */
  private enum WalkType
  {
    RANDOM, LINE, PATH
  }

  public static final double DIST_SMELL = 7.0f;
  private static final double SPEED_WALK = 0.5f;
  private static final double RATE_ACT = 2.0f;
  private static final Random rng = new Random();
  private ArrayList<Tile> path;
  private WalkType wType;
  private double theta;
  private double timeSinceUpdate;
  private boolean bumped;
  private Enum zombieOrientation;
  private SpriteLoader frames;

  /**
   * Full constructor
   * @param x                    X coordinate of center point (in pixels)
   * @param y                    Y coordinate of center point (in pixels)
   * @param radius               Radius of bounding circle (in pixels)
   * @param location             Tile location containing center point
   * @param zhModel              Reference to Zombie House Model
   */
  public Zombie(int x, int y, int radius, Tile location, ZombieHouseModel zhModel, Enum direction, ImageLoader imageLoader, boolean running, int health)
  {
    super(x, y, radius, location, zhModel, running, health);
    wType = (rng.nextBoolean() ? WalkType.RANDOM : WalkType.LINE);
    path = new ArrayList<>();
    frames = new SpriteLoader(imageLoader);
    zombieOrientation = direction;
    theta = rng.nextDouble() * 2.0f * Math.PI;
    timeSinceUpdate = 0.0f;
  }

  /**
   * Getter for Player's animation frames (sprites)
   * @return  frames
   */
  public SpriteLoader getFrames()
  {
    return frames;
  }

  /**
   * Gets zombie's type as a string
   * @return  String representation of zombie type
   */
  public String getZType()
  {
    if (wType == WalkType.LINE) return "Line";
    else if (wType == WalkType.PATH) return "Master";
    return "Random";
  }

  /**
   * Walk in predetermined direction or path
   */
  protected void walk(double timeElapsed)
  {
    double xDistance = (SPEED_WALK * super.getCurrentTile().getWidth() * Math.sin(theta) * timeElapsed);
    double yDistance = (SPEED_WALK * super.getCurrentTile().getHeight() * Math.cos(theta) * timeElapsed);
    //System.out.println("zxd "+xDistance+" zyd "+yDistance+" theta "+theta);
    if (5.8905f < theta || theta < 0.3927f) zombieOrientation = GridOrientation.SOUTH;
    else if (0.3927f < theta && theta < 1.1781f) zombieOrientation = GridOrientation.SOUTHEAST;
    else if (1.1781f < theta && theta < 1.9635f) zombieOrientation = GridOrientation.EAST;
    else if (1.9635f < theta && theta < 2.7489f) zombieOrientation = GridOrientation.NORTHEAST;
    else if (2.7489f < theta && theta < 3.5343f) zombieOrientation = GridOrientation.NORTH;
    else if (3.5343f < theta && theta < 4.3197f) zombieOrientation = GridOrientation.NORTHWEST;
    else if (4.3197f < theta && theta < 5.1051f) zombieOrientation = GridOrientation.WEST;
    else if (5.1051f < theta && theta < 5.8905f) zombieOrientation = GridOrientation.SOUTHWEST;
    bumped = super.move(xDistance, yDistance, zombieOrientation);
    if(wType == WalkType.LINE) getFrames().getRotatingLineZombieWalk();
    else getFrames().getRotatingRandomZombieWalk();
  }

  /**
   * Receives timeElapsed from ZombieHouseModel and when a threshhold has been passed, do interesting things.
   * @param timeElapsed  time elapsed since last update call
   */
  public void updateZombie(double timeElapsed)
  {
    timeSinceUpdate += timeElapsed;
    if (timeSinceUpdate > RATE_ACT)
    {
      timeSinceUpdate = 0.0f;
      decisionEngine();
    }
    walk(timeElapsed);
  }

  /**
   * Decide how to walk based on WalkType and distance to player.
   */
  private void decisionEngine()
  {
    if (!path.isEmpty())
    {
      Tile nextTile = path.get(0);
      theta = Math.atan2(nextTile.getCenterX() - this.getX(), nextTile.getCenterY() - this.getY());
      //System.out.println("Z["+this.getTileRow()+"]["+this.getTileCol()+"] tracking ["+this.getZModel().getPlayer().getTileRow()+"]["+this.getZModel().getPlayer().getTileCol()+"] via ["+nextTile.getGridRow()+"]["+nextTile.getGridCol()+"]");
      if (theta < 0.0f) theta += Math.PI * 2.0f;
    }
    else if (wType == WalkType.RANDOM || !bumped)
    {
      theta = rng.nextDouble() * Math.PI * 2.0f;
      bumped = true;
    }
  }

  /**
   * Sets path of Tiles to follow
   * @param fastPath  path of tiles as array list
   */
  public void setPath(ArrayList<Tile> fastPath)
  {
    path = fastPath;
  }

  /**
   * Clears path
   */
  public void deletePath()
  {
    path.clear();
  }

  /**
   * Turns into a master zombie
   */
  public void setMasterZombie()
  {
    wType = WalkType.PATH;
  }
}
