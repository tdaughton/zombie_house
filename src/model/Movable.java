/**
 * Created by Tess Daughton, September 13th, 2015
 */
package model;

import javafx.scene.shape.Circle;
import java.awt.Rectangle;

/**
 * This class represents the basic functionality of moving or movable entities in the Zombie House game.
 * This class provides the model for how entities move within the game grid and enforces collision detection.
 */
public class Movable
{
  protected int x;
  protected int y;
  protected int radius;
  protected Circle circle;
  protected Tile location;
  protected Tile[][] grid;
  protected Enum playerOrientation;
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
   * @param location             Tile location containing center point
   * @param grid                 Reference to Zombie House map
   * @param playerOrientation    8-way orientation
   */
  public Movable(int x, int y, int radius, Tile location, Tile[][] grid, Enum playerOrientation)
  {

    this.x = x;
    this.y = y;
    this.radius = radius;
    this.location = location;
    this.grid = grid;
    this.playerOrientation = playerOrientation;
    circle = new Circle(x, y, radius);
  }
  
  /**
   * Getter for X coordinate
   * @return  X coordinate as integer pixels
   */
  public int getX()
  {
    return x;
  }

  /**
   * Getter for Y coordinate
   * @return  Y coordinate as integer pixels
   */
  public int getY()
  {
    return y;
  }

  /**
   * Getter for radius length
   * @return  radius length in pixels
   */
  public int getRadius()
  {
    return radius;
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
  public boolean intersects(Circle otherMovable)
  {
    boolean intersects = false;
    double r1 = Math.pow(otherMovable.getRadius() - radius, 2);
    double r2 = Math.pow(otherMovable.getRadius() + radius, 2);

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
    double r1 = Math.abs(this.circle.getCenterX() - immovableSpace.getCenterX());
    double r2 = Math.abs(this.circle.getCenterY() - immovableSpace.getCenterY());


    if (r1 > (halfWidth + this.radius) || r2 > (halfHeight + this.radius)) return false;
    if (r1 <= (halfWidth) || r2 <= (halfHeight)) return true;
    double cd = Math.pow(r1 - halfWidth, 2) + Math.pow(r2 - halfHeight, 2);
    return (cd <= Math.pow(radius, 2));
  }

  /**
   * This is the intended way to move Movables. This method calls canMoveTo with the precalculated
   * displacement to check the resultant Tile's type and boundaries.
   * @param xNew         X-displacement (in pixels)
   * @param yNew         Y-displacement (in pixels)
   * @param current      reference to current Tile
   * @param orientation  Orientation after moving
   */
  public void move(int xNew, int yNew, Tile current, Enum orientation)
  {
    double xPos = x + xNew;
    double yPos = y + yNew;

    if (canMoveTo(xPos, yPos))
    {
      int gCol = (int)(xPos / location.getBounds().getWidth());
      int gRow = (int)(yPos / location.getBounds().getHeight());
      playerOrientation = orientation;
      x = (int)xPos;
      circle.setCenterX(x);
      y = (int)yPos;
      circle.setCenterY(y);
      location = grid[gRow][gCol];
    }
  }

  /**
   * Checks whether the calling Movable will "fit" at a precalculated destination specified by xy-coordinate.
   * @param xNew  X coordinate of new position (in pixels)
   * @param yNew  Y coordinate of new position (in pixels)
   * @return  true if destination is movable or does not intersect; false otherwise
   */
  protected boolean canMoveTo(double xNew, double yNew)
  {
    int gCol = (int)(xNew / location.getWidth());
    int gRow = (int)(yNew / location.getHeight());
    Tile nextTile = grid[gRow][gCol];

    moveChecker.circle.setCenterX(xNew);
    moveChecker.circle.setCenterY(yNew);
    moveChecker.circle.setRadius(circle.getRadius());

    boolean canMove = nextTile.isMovable();
    boolean intersectsWall = moveChecker.intersects(nextTile);
    return (!intersectsWall || canMove);
  }

  /**
   * Arbitrarily move to a Tile by reference.
   * @param next  target Tile
   */
  public void setCurrentTile(Tile next)
  {
    location = next;
  }

  /**
   * Calculate straight-line distance to arbitary xy-coordinate.
   * @param xNew  X-coordinate of interest (in pixels)
   * @param yNew  Y-coordinate of interest (in pixels)
   * @return  Cartesian straight-line distance between center point and target point
   */
  public double getDistanceTo(int xNew, int yNew)
  {
    double dist;

    dist = Math.sqrt(Math.pow((x - xNew), 2) + Math.pow((y - yNew), 2));

    return dist;
  }
}
