/**
 * Created by Tess Daughton, September 13th, 2015
 */
package model;
import javafx.scene.shape.Circle;
import java.awt.Rectangle;

/**
 * This class represents the basic functionality of moving or movable entities in the Zombie House game.
 * This class provides the model for how entities move within the game tiles and enforces collision detection.
 */
public class Movable
{
  private Circle circle;
  private Tile location;
  //private Tile[][] tiles;
  private ZombieHouseModel zModel;
  private Enum playerOrientation;

  //static copy used in boundary checking to avoid multiple instantiation
  private static Movable moveChecker = new Movable();

  /**
   * Default constructor. Mostly for static moveChecker only.
   */
  public Movable()
  {
    circle = new Circle(0, 0, 1);
  }

  /**
   * Full constructor
   * @param x                    X coordinate of center point (in pixels)
   * @param y                    Y coordinate of center point (in pixels)
   * @param radius               Radius of bounding circle (in pixels)
   * @param loc                  Tile location containing center point
   * @param zhModel              Reference to Zombie House map
   * @param playerOrientation    8-way orientation
   */
  public Movable(double x, double y, double radius, Tile loc, ZombieHouseModel zhModel, Enum playerOrientation)
  {
    location = loc;
    zModel = zhModel;
    //tiles = grid;
    circle = new Circle(x, y, radius);
  }

  /**
   * Getter for X coordinate
   * @return  X coordinate as integer pixels
   */
  public int getX()
  {
    return (int)circle.getCenterX();
  }

  /**
   * Getter for Y coordinate
   * @return  Y coordinate as integer pixels
   */
  public int getY()
  {
    return (int)circle.getCenterY();
  }

  /**
   * Getter for radius length
   * @return  radius length in pixels
   */
  public int getRadius()
  {
    return (int)circle.getRadius();
  }

  /**
   * Getter for orientation
   * @return  compass orientation
   */
  public Enum getPlayerOrientation()
  {
    return playerOrientation;
  }

  /**
   * Getter for bounding circle object
   * @return  reference to this movable's bounding circle
   */
  public Circle getBoundingCircle()
  {
    return circle;
  }

  /**
   * Getter for current location
   * @return  reference to current Tile
   */
  public Tile getCurrentTile()
  {
    return location;
  }

  /**
   * Method to calculate collision detection with other Movables.
   * If the distance between center pixels are less than either Movable's radius, then there is a collision.
   * @param otherMovable  any other Movable
   * @return  true/false if collision
   */
  public boolean intersects(Circle otherMovable)
  {
    boolean intersects = false;
    double r1 = Math.pow(otherMovable.getRadius() - circle.getRadius(), 2);
    double r2 = Math.pow(otherMovable.getRadius() + circle.getRadius(), 2);
    double distance = Math.pow((otherMovable.getCenterX() - circle.getCenterX()), 2) +
                       Math.pow((otherMovable.getCenterY() - circle.getCenterY()), 2);

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
    double r1 = Math.abs(circle.getCenterX() - immovableSpace.getCenterX());
    double r2 = Math.abs(circle.getCenterY() - immovableSpace.getCenterY());

    if (r1 > (halfWidth + circle.getRadius()) || r2 > (halfHeight + circle.getRadius())) return false;
    if (r1 <= (halfWidth) || r2 <= (halfHeight)) return true;
    double cd = Math.pow(r1 - halfWidth, 2) + Math.pow(r2 - halfHeight, 2);
    return (cd <= Math.pow(circle.getRadius(), 2));
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
      double xPos = circle.getCenterX() + dX;
      double yPos = circle.getCenterY();
      int gCol = (int)(xPos / zModel.getTileWidth());
      int gRow = (int)(yPos / zModel.getTileHeight());
      if(gRow<40 && gCol<40 && gRow>=0 && gCol>=0)
      {
        playerOrientation = orientation;
        circle.setCenterX(xPos);
        circle.setCenterY(yPos);
        location = zModel.getTile(gRow,gCol);

        if (location.hasTrap)
        {
          zModel.getSoundLoader().playExplosionEffect();
          location.getTrap().setExplosionTrigger();
        }
      }
    }
    
    boolean successY = canMoveTo(0, dY);
    if (successY)
    {
      double xPos = circle.getCenterX();
      double yPos = circle.getCenterY() + dY;
      int gCol = (int)(xPos / zModel.getTileWidth());
      int gRow = (int)(yPos / zModel.getTileHeight());
      if(gRow<40 && gCol<40 && gRow>=0 && gCol>=0)
      {
        playerOrientation = orientation;
        circle.setCenterX(xPos);
        circle.setCenterY(yPos);
        location = zModel.getTile(gRow,gCol);

        if (location.hasTrap)
        {
          zModel.getSoundLoader().playExplosionEffect();
          location.getTrap().setExplosionTrigger();
        }
      }
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
    double xNew = circle.getCenterX() + dX;
    double yNew = circle.getCenterY() + dY;
    moveChecker.circle.setCenterX(xNew);
    moveChecker.circle.setCenterY(yNew);
    moveChecker.circle.setRadius(circle.getRadius());

    int gCol = location.getGridCol();
    int gRow = location.getGridRow();

    if (dX < 0) gCol = gCol - 1;
    else if (dX > 0) gCol = gCol + 1;
    if (dY < 0) gRow = gRow - 1;
    else if (dY > 0) gRow = gRow + 1;
    if (gRow < 40 && gCol < 40 && gRow >= 0 && gCol >= 0)
    {
      Tile nextTile = zModel.getTile(gRow,gCol);;
      boolean canMove = nextTile.isMovable();
      boolean intersectsWall = moveChecker.intersects(nextTile);
      return (canMove || !intersectsWall);
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
    double dist;

    dist = Math.sqrt(Math.pow((circle.getCenterX() - xNew), 2) + Math.pow((circle.getCenterY() - yNew), 2));

    return dist;
  }
}
