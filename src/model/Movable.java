/**
 * Created by Tess Daughton, September 13th, 2015
 */
package model;

import javafx.scene.shape.Circle;

import java.awt.*;

public class Movable
{
  protected Circle circle;
  protected Tile location;
  protected Tile[][] grid;
  protected Enum playerOrientation;

  private static Movable moveChecker = new Movable();

  public Movable()
  {
    this.circle = new Circle(0, 0, 1);
  }

  public Movable(int x, int y, int radius, Tile location, Tile[][] grid, Enum playerOrientation)
  {
    this.location = location;
    this.grid = grid;
    this.playerOrientation = playerOrientation;
    this.circle = new Circle(x, y, radius);
  }

  public int getX()
  {
    return (int)this.circle.getCenterX();
  }

  public int getY()
  {
    return (int)this.circle.getCenterY();
  }

  public int getRadius()
  {
    return (int)this.circle.getRadius();
  }

  public Enum getPlayerOrientation()
  {
    return this.playerOrientation;
  }

  public Circle getBoundingCircle()
  {
    return this.circle;
  }

  public Tile getCurrentTile()
  {
    return this.location;
  }

  public boolean intersects(Circle otherMovable)
  {
    boolean intersects = false;
    double r1 = Math.pow(otherMovable.getRadius() - this.circle.getRadius(), 2);
    double r2 = Math.pow(otherMovable.getRadius() + this.circle.getRadius(), 2);

    double distance = Math.pow((otherMovable.getCenterX() - this.circle.getCenterX()), 2) +
                      Math.pow((otherMovable.getCenterY() - this.circle.getCenterY()), 2);

    if (distance >= r1 && distance <= r2) intersects = true;

    return intersects;
  }

  //http://stackoverflow.com/questions/401847/circle-rectangle-collision-detection-intersection
  public boolean intersects(Rectangle immovableSpace)
  {
    boolean result = false;
    double circR = this.circle.getRadius();
    double r1 = Math.abs(this.circle.getCenterX() - immovableSpace.getCenterX());
    double r2 = Math.abs(this.circle.getCenterY() - immovableSpace.getCenterY());
    double halfWidth = immovableSpace.getWidth() / 2;
    double halfHeight = immovableSpace.getHeight() / 2;

    if (r1 > (halfWidth + circR)) result = false;
    else if (r2 > (halfHeight + circR)) result = false;
    else if (r1 <= (halfWidth)) result = true;
    else if (r2 <= (halfHeight)) result = true;

    double cd = Math.pow(r1 - halfWidth, 2) + Math.pow(r2 - halfHeight, 2);
    if (cd <= Math.pow(circR, 2)) result = true;

    return result;
  }

  public void move(int xNew, int yNew, Tile current, Enum orientation)
  {
    double xPos = this.circle.getCenterX() + xNew;
    double yPos = this.circle.getCenterY() + yNew;
    int gCol = (int)(xPos / this.location.getBounds().getWidth());
    int gRow = (int)(yPos / this.location.getBounds().getHeight());

    moveChecker.circle.setCenterX(xPos);
    moveChecker.circle.setCenterY(yPos);
    moveChecker.circle.setRadius(this.circle.getRadius());
    Tile nextTile = this.grid[gRow][gCol];

    boolean mova = nextTile.isMovable();
    boolean inte = moveChecker.intersects(nextTile.getBounds());
    if (mova || !inte)
    {
      this.playerOrientation = orientation;
      this.circle.setCenterX(xPos);
      this.circle.setCenterY(yPos);
      this.location = this.grid[gRow][gCol];
    }
  }

  public void setCurrentTile(Tile next)
  {
    this.location = next;
  }

  public double getDistanceTo(int xNew, int yNew)
  {
    double dist;

    dist = Math.sqrt(Math.pow((this.circle.getCenterX() - xNew), 2) + Math.pow((this.circle.getCenterY() - yNew), 2));

    return dist;
  }
}
