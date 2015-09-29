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
  public static final double SPEED_WALK = 0.5f;
  public static final double RATE_ACT = 2.0f;
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
   * @param zhModel              Reference to Zombie House Model
   * @param loc                  Tile location containing center point
   * @param running              Initial running status
   */
  public Zombie(ZombieHouseModel zhModel, Tile loc, boolean running)
  {
    super(zhModel, loc, running);
    this.wType = (rng.nextBoolean() ? WalkType.RANDOM : WalkType.LINE);
    this.path = new ArrayList<>();
    this.frames = new SpriteLoader(ZombieHouseModel.imageLoader);
    this.theta = rng.nextDouble() * 2.0f * Math.PI;
    this.decodeOrientation();
    this.timeSinceUpdate = 0.0f;
  }

  /**
   * Getter for Player's animation frames (sprites)
   * @return  frames
   */
  public SpriteLoader getFrames()
  {
    return this.frames;
  }

  /**
   * Gets zombie's type as a string
   * @return  String representation of zombie type
   */
  public String getZType()
  {
    if (this.wType == WalkType.LINE) return "Line";
    else if (this.wType == WalkType.PATH) return "Master";
    return "Random";
  }

  private void decodeOrientation()
  {
    if (5.8905f < this.theta || this.theta < 0.3927f) this.zombieOrientation = GridOrientation.SOUTH;
    else if (0.3927f < this.theta && this.theta < 1.1781f) this.zombieOrientation = GridOrientation.SOUTHEAST;
    else if (1.1781f < this.theta && this.theta < 1.9635f) this.zombieOrientation = GridOrientation.EAST;
    else if (1.9635f < this.theta && this.theta < 2.7489f) this.zombieOrientation = GridOrientation.NORTHEAST;
    else if (2.7489f < this.theta && this.theta < 3.5343f) this.zombieOrientation = GridOrientation.NORTH;
    else if (3.5343f < this.theta && this.theta < 4.3197f) this.zombieOrientation = GridOrientation.NORTHWEST;
    else if (4.3197f < this.theta && this.theta < 5.1051f) this.zombieOrientation = GridOrientation.WEST;
    else if (5.1051f < this.theta && this.theta < 5.8905f) this.zombieOrientation = GridOrientation.SOUTHWEST;
  }

  /**
   * Walk in predetermined direction or path
   */
  protected void walk(double timeElapsed)
  {
    double xDistance = (SPEED_WALK * super.getCurrentTile().getWidth() * Math.sin(this.theta) * timeElapsed);
    double yDistance = (SPEED_WALK * super.getCurrentTile().getHeight() * Math.cos(this.theta) * timeElapsed);
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
    this.timeSinceUpdate += timeElapsed;
    if (this.timeSinceUpdate > RATE_ACT)
    {
      this.timeSinceUpdate = 0.0f;
      this.decisionEngine();
    }
    this.walk(timeElapsed);
  }

  /**
   * Decide how to walk based on WalkType and distance to player.
   */
  private void decisionEngine()
  {
    if (!this.path.isEmpty())
    {
      Tile nextTile = this.path.get(0);
      this.theta = Math.atan2(nextTile.getCenterX() - this.getX(), nextTile.getCenterY() - this.getY());
      //System.out.println("Z["+this.getTileRow()+"]["+this.getTileCol()+"] tracking ["+this.getZModel().getPlayer().getTileRow()+"]["+this.getZModel().getPlayer().getTileCol()+"] via ["+nextTile.getGridRow()+"]["+nextTile.getGridCol()+"]");
      if (this.theta < 0.0f) this.theta += Math.PI * 2.0f;
    }
    else if (this.wType == WalkType.RANDOM || !this.bumped)
    {
      this.theta = rng.nextDouble() * Math.PI * 2.0f;
      this.bumped = true;
    }
    this.decodeOrientation();
  }

  /**
   * Sets path of Tiles to follow
   * @param fastPath  path of tiles as array list
   */
  public void setPath(ArrayList<Tile> fastPath)
  {
    this.path = fastPath;
  }

  /**
   * Clears path
   */
  public void deletePath()
  {
    this.path.clear();
  }

  /**
   * Turns into a master zombie
   */
  public void setMasterZombie()
  {
    this.wType = WalkType.PATH;
  }
}
