/**
 * Created by Miri on 9/8/15.
 * edits: Tess Daughton, September 13th, 2015
 */
package model;

import Resources.SpriteLoader;
import Resources.ImageLoader;

public class Player extends Movable
{
  public static final double DIST_SIGHT = 5.0f;
  public static final double DIST_HEAR = 10.0f;
  public static final double SPEED_WALK = 1.0f;
  public static final double SPEED_MULT = 2.0f;
  public static final double STAM_MAX = 5.0f;
  public static final double STAM_REGEN = 0.2f;

  private SpriteLoader frames;
  private int numberOfTraps;
  private double stamina;

  /**
   * Full constructor
   * @param zhModel              Reference to Zombie House Model
   * @param loc                  Tile location containing center point
   * @param running              Initial running status
   */
  public Player(ZombieHouseModel zhModel, Tile loc, boolean running)
  {
    super(zhModel, loc, running);
    this.frames = new SpriteLoader(ZombieHouseModel.imageLoader);
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
    if (!this.isRunning()) stamina = Math.min(STAM_MAX,stamina+STAM_REGEN*timeElapsed);
    stamina = Math.max(0, stamina);
  }

  public void walk(double dX, double dY, Enum direction, boolean boost, double timeElapsed)
  {
    stamina -= (boost ? timeElapsed : 0.0f);
    double xDistance = ((boost && stamina > 0.0f) ? SPEED_MULT : 1.0f) * SPEED_WALK * super.getCurrentTile().getWidth() * timeElapsed * dX;
    double yDistance = ((boost && stamina > 0.0f) ? SPEED_MULT : 1.0f) * SPEED_WALK * super.getCurrentTile().getHeight() * timeElapsed * dY;
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
    Trap trap = new Trap((int) this.getCurrentTile().getCenterX(), (int) this.getCurrentTile().getCenterY(), true);
    this.getCurrentTile().installTrap(trap);
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
