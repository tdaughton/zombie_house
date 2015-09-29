/**
 * Created by Tess Daughton, September 13th, 2015
 */
package model;

import javafx.scene.shape.Circle;

import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * This class represents the basic functionality of moving or movable entities in the Zombie House game.
 * This class provides the model for how entities move within the game tiles and enforces collision detection.
 */
public class Movable implements Alive
{
  private Circle circle;
  private Tile location;
  private ZombieHouseModel zModel;
  private Enum playerOrientation;
  private boolean running;
  private boolean dead;
  private boolean levelUp;

  //static copy used in boundary checking to avoid multiple instantiation
  private static Movable moveChecker = new Movable();

  /**
   * Default constructor. Mostly for static moveChecker only.
   */
  public Movable()
  {
    this.circle = new Circle(0, 0, 1);
  }

  /**
   * Full constructor
   * @param zhModel              Reference to Zombie House Model
   * @param loc                  Tile location containing center point
   * @param running              Initial running status
   */
  public Movable(ZombieHouseModel zhModel, Tile loc, Boolean running)
  {
    this.location = loc;
    this.zModel = zhModel;
    this.running = running;
    this.dead = false;
    this.levelUp = false;
    this.circle = new Circle(this.location.getCenterX(), this.location.getCenterY(), Math.floor((this.zModel.getTileHeight() - 1) / 2));
  }

  /**
   * Getter for X coordinate
   * @return  X coordinate as integer pixels
   */
  public int getX()
  {
    return (int)this.circle.getCenterX();
  }

  /**
   * Getter for Y coordinate
   * @return  Y coordinate as integer pixels
   */
  public int getY()
  {
    return (int)this.circle.getCenterY();
  }

  /**
   * Getter for radius length
   * @return  radius length in pixels
   */
  public int getRadius()
  {
    return (int)this.circle.getRadius();
  }

  public ZombieHouseModel getZModel()
  {
    return this.zModel;
  }

  /**
   * Getter for orientation
   * @return  compass orientation
   */
  public Enum getPlayerOrientation()
  {
    return this.playerOrientation;
  }

  /**
   * Getter for bounding circle object
   * @return  reference to this movable's bounding circle
   */
  public Circle getBoundingCircle()
  {
    return this.circle;
  }

  /**
   * Getter for current location
   * @return  reference to current Tile
   */
  public Tile getCurrentTile()
  {
    return this.location;
  }

  /**
   * Method to calculate collision detection with other Movables.
   * If the distance between center pixels are less than either Movable's radius, then there is a collision.
   * @param otherMovable  any other Movable
   * @return  true/false if collision
   */
  public boolean intersects(Movable otherMovable)
  {
    boolean intersects = false;
    double r1 = Math.pow(otherMovable.getRadius() - this.circle.getRadius(), 2);
    double r2 = Math.pow(otherMovable.getRadius() + this.circle.getRadius(), 2);
    double distance = Math.pow((otherMovable.getX() - this.circle.getCenterX()), 2) +
            Math.pow((otherMovable.getY() - this.circle.getCenterY()), 2);

    if (distance >= r1 && distance <= r2) intersects = true;
    return intersects;
  }

  /**
   * Method to calculate collision detection with a generic axis-parallel Rectangle
   * The algorithm is adapted from the following web page:
   * http://stackoverflow.com/questions/401847/circle-rectangle-collision-detection-intersection
   * @param immovableSpace  bounding Rectangle for post-movement candidate tile
   * @return  true if this circle and that rectangle don't intersect
   */
  public boolean intersects(Rectangle immovableSpace)
  {
    double halfWidth = immovableSpace.getWidth() / 2;
    double halfHeight = immovableSpace.getHeight() / 2;
    double r1 = Math.abs(this.circle.getCenterX() - immovableSpace.getCenterX());
    double r2 = Math.abs(this.circle.getCenterY() - immovableSpace.getCenterY());

    if (r1 > (halfWidth + this.circle.getRadius()) || r2 > (halfHeight + this.circle.getRadius())) return false;
    if (r1 <= (halfWidth) || r2 <= (halfHeight)) return true;
    double cd = Math.pow(r1 - halfWidth, 2) + Math.pow(r2 - halfHeight, 2);
    return (cd <= Math.pow(this.circle.getRadius(), 2));
  }

  /**
   * This is the intended way to move Movables. This method calls canMoveTo with the precalculated
   * displacement to check the resultant Tile's type and boundaries.
   * @param dX           X-displacement (in pixels)
   * @param dY           Y-displacement (in pixels)
   * @param orientation  Orientation after moving
   */
  public boolean move(double dX, double dY, Enum orientation)
  {
    boolean successX = canMoveTo(dX, 0);
    if (successX)
    {
      double xPos = this.circle.getCenterX() + dX;
      double yPos = this.circle.getCenterY();
      int gCol = (int)(xPos / this.location.getWidth());
      int gRow = (int)(yPos / this.location.getHeight());
      if(gRow<ZombieHouseModel.ROWS && gCol<ZombieHouseModel.COLS && gRow>=0 && gCol>=0)
      {
        this.playerOrientation = orientation;
        this.circle.setCenterX(xPos);
        this.circle.setCenterY(yPos);
        this.location = this.zModel.getTile(gRow, gCol);
      }
    }

    boolean successY = canMoveTo(0, dY);
    if (successY)
    {
      double xPos = circle.getCenterX();
      double yPos = circle.getCenterY() + dY;
      int gCol = (int)(xPos / this.location.getWidth());
      int gRow = (int)(yPos / this.location.getHeight());
      if(gRow<ZombieHouseModel.ROWS && gCol<ZombieHouseModel.COLS && gRow>=0 && gCol>=0)
      {
        this.playerOrientation = orientation;
        this.circle.setCenterX(xPos);
        this.circle.setCenterY(yPos);
        this.location = this.zModel.getTile(gRow, gCol);
      }
    }

    if (this.location.hasTrap())
    {
      if(this.isRunning())
      {
        this.location.getTrap().setExplosionTrigger();
        this.dead = true;

        if (!this.location.getTrap().getSoundPlayed())
        {
          ZombieHouseModel.soundLoader.playExplosionEffect();
          this.location.getTrap().setSoundPlayed();
        }
      }
    }

    if(this instanceof Player && this.location.hasExitFlag())
    {
      this.levelUp = true;
      //this.zModel.restart(true);
    }

    return (successX & successY);
  }

  /**
   * Checks whether the calling Movable will "fit" at a precalculated destination specified by xy-coordinate.
   * @param dX  X coordinate of new position (in pixels)
   * @param dY  Y coordinate of new position (in pixels)
   * @return  true if destination is movable or does not intersect bounded Tiles; false otherwise
   */
  protected boolean canMoveTo(double dX, double dY)
  {
    double xNew = this.circle.getCenterX() + dX;
    double yNew = this.circle.getCenterY() + dY;
    moveChecker.circle.setCenterX(xNew);
    moveChecker.circle.setCenterY(yNew);
    moveChecker.circle.setRadius(this.circle.getRadius());

    int gCol = this.location.getGridCol();
    int gRow = this.location.getGridRow();

    if (dX < 0) gCol = gCol - 1;
    else if (dX > 0) gCol = gCol + 1;
    if (dY < 0) gRow = gRow - 1;
    else if (dY > 0) gRow = gRow + 1;
    if (gRow < ZombieHouseModel.ROWS && gCol < ZombieHouseModel.COLS && gRow >= 0 && gCol >= 0)
    {
      Tile nextTile = this.zModel.getTile(gRow, gCol);
      boolean canMove = nextTile.isMovable();
      boolean intersectsWall = moveChecker.intersects(nextTile);
      boolean intersectsMovable = false;
      ArrayList<Zombie> zList = this.zModel.getZombieList();
      for (Zombie zombone : zList)
      {
        if (!zombone.equals(this)) intersectsMovable |= moveChecker.intersects(zombone);
      }
      return ((canMove || !intersectsWall) && !intersectsMovable);
    }
    else return false;
  }

  /**
   * Calculate straight-line distance to arbitary xy-coordinate.
   * @param xNew  X-coordinate of interest (in pixels)
   * @param yNew  Y-coordinate of interest (in pixels)
   * @return  Cartesian straight-line distance between center point and target point
   */
  public double getDistanceTo(double xNew, double yNew)
  {
    return Math.sqrt(Math.pow((this.circle.getCenterX() - xNew), 2) + Math.pow((this.circle.getCenterY() - yNew), 2));
  }

  /**
   * Calculate straight-line distance to other Movable
   * @param otherMovable  other Movable of interest
   * @return  Cartesian straight-line distance between center point and other Movable's center point
   */
  public double getDistanceTo(Movable otherMovable)
  {
    return Math.sqrt(Math.pow((this.circle.getCenterX() - otherMovable.getX()), 2) + Math.pow((this.circle.getCenterY() - otherMovable.getY()), 2));
  }

  /**
   * Getter for running status
   * @return  true if running, false otherwise
   */
  public boolean isRunning()
  {
    return this.running;
  }

  /**
   * Setter for running status
   * @param running  new running status
   */
  public void setRunning(boolean running)
  {
    this.running = running;
  }

  /**
   * Getter for dead status
   * @return  true if dead, false otherwise
   */
  public boolean isDead()
  {
    return this.dead;
  }

  /**
   * Setter for dead status
   * @param dead  new dead status
   */
  public void setDead(boolean dead)
  {
    this.dead = dead;
  }

  /**
   * Getter for level up status
   * @return  true if leveled up, false otherwise
   */
  public boolean isLevelUp()
  {
    return this.levelUp;
  }
}
