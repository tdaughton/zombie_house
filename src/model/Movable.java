/**
 * Created by Tess Daughton, September 13th, 2015
 */
package model;

import javafx.scene.shape.Circle;

import java.awt.*;

public class Movable
{
  protected int x;
  protected int y;
  protected int radius;
  protected Circle circle;
  protected Tile location;
  protected Tile[][] grid;
  protected Enum playerOrientation;

  private static Movable moveChecker = new Movable();

  public Movable()
  {
    circle = new Circle(0, 0, 1);
  }

  public Movable(int x, int y, int radius, Tile location, Tile[][] grid, Enum playerOrientation)
  {
    this.x = x;
    this.y = y;
    this.radius = radius;
    this.location = location;
    this.grid = grid;
    this.playerOrientation = playerOrientation;
    this.circle = new Circle(x, y, radius);
  }

  public int getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
  }

  public int getRadius()
  {
    return radius;
  }

  public Enum getPlayerOrientation()
  {
    return playerOrientation;
  }

  public Circle getBoundingCircle()
  {
    return circle;
  }

  public Tile getCurrentTile()
  {
    return location;
  }

  public boolean intersects(Circle otherMovable)
  {
    boolean intersects = false;
    double r1 = Math.pow(otherMovable.getRadius() - radius, 2);
    double r2 = Math.pow(otherMovable.getRadius() + radius, 2);

    double distance = Math.pow((otherMovable.getCenterX() - x), 2) +
                      Math.pow((otherMovable.getCenterY() - x), 2);

    if (distance >= r1 && distance <= r2) intersects = true;

    return intersects;
  }

  //http://stackoverflow.com/questions/401847/circle-rectangle-collision-detection-intersection
  public boolean intersects(Rectangle immovableSpace)
  {
    boolean result = false;
    double r1 = Math.abs(x - immovableSpace.getCenterX());
    double r2 = Math.abs(y - immovableSpace.getCenterY());
    double halfWidth = immovableSpace.getWidth() / 2;
    double halfHeight = immovableSpace.getHeight() / 2;

    if (r1 > (halfWidth + radius)) result = false;
    else if (r2 > (halfHeight + radius)) result = false;
    else if (r1 <= (halfWidth)) result = true;
    else if (r2 <= (halfHeight)) result = true;

    double cd = Math.pow(r1 - halfWidth, 2) + Math.pow(r2 - halfHeight, 2);
    if (cd <= Math.pow(radius, 2)) result = true;

    return result;
  }

  public void move(int xNew, int yNew, Tile current, Enum orientation)
  {
    double xPos = x + xNew;
    double yPos = y + yNew;
    int gCol = (int)(xNew / location.getBounds().getWidth());
    int gRow = (int)(yNew / location.getBounds().getHeight());

    if (canMoveTo(xPos, yPos))
    {
      playerOrientation = orientation;
      x = (int)xPos;
      circle.setCenterX(x);
      y = (int)yPos;
      circle.setCenterY(y);
      location = grid[gRow][gCol];
    }
  }

  protected boolean canMoveTo(double xNew, double yNew)
  {
    int gCol = (int)(xNew / location.getBounds().getWidth());
    int gRow = (int)(yNew / location.getBounds().getHeight());
    Tile nextTile = grid[gRow][gCol];

    moveChecker.circle.setCenterX(xNew);
    moveChecker.circle.setCenterY(yNew);
    moveChecker.circle.setRadius(circle.getRadius());

    boolean canMove = nextTile.isMovable();
    boolean intersectsWall = moveChecker.intersects(nextTile.getBounds());
    return (canMove || !intersectsWall);
  }

  public void setCurrentTile(Tile next)
  {
    location = next;
  }

  public double getDistanceTo(int xNew, int yNew)
  {
    double dist;

    dist = Math.sqrt(Math.pow((x - xNew), 2) + Math.pow((y - yNew), 2));

    return dist;
  }
}
