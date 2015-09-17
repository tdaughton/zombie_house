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
  protected int px;
  protected int py;
  protected double theta;
  protected int radius;
  protected Circle circle;
  protected Tile location;
  protected Tile[][] grid;
  protected Enum playerOrientation;


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

  public int getX()
  {
    return this.x;
  }

  public int getY()
  {
    return this.y;
  }

  public int getRadius()
  {
    return this.radius;
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
    double r1 = Math.pow(otherMovable.getRadius() - this.radius, 2);
    double r2 = Math.pow(otherMovable.getRadius() + this.radius, 2);

    double distance = Math.pow((otherMovable.getCenterX() - this.x), 2) +
                      Math.pow((otherMovable.getCenterY() - this.y), 2);

    if (distance >= r1 && distance <= r2) intersects = true;

    return intersects;
  }

  //http://stackoverflow.com/questions/401847/circle-rectangle-collision-detection-intersection
  public boolean intersects(Rectangle immovableSpace)
  {
    double circR = this.circle.getRadius();
    double r1 = Math.abs(this.circle.getCenterX() - immovableSpace.getBounds().getCenterX());
    double r2 = Math.abs(this.circle.getCenterY() - immovableSpace.getBounds().getCenterY());
    System.out.print(r1 + " " + r2 + "\n");
    double rectWidth = immovableSpace.getBounds().getWidth() / 2;
    double rectHeight = immovableSpace.getBounds().getHeight() / 2;
    System.out.print(rectWidth + " " + rectHeight + "\n");
    if (r1 > (rectWidth + circR)) return false;
    if (r2 > (rectHeight + circR)) return false;
    if (r1 <= (rectWidth)) return true;
    if (r1 <= (rectHeight)) return true;

    double cd = Math.pow(r1 - rectWidth, 2) + Math.pow(r2 - rectHeight, 2);
    double cd_sqrt = Math.sqrt(cd);

    return (cd_sqrt <= Math.pow(circR, 2));
  }


  public void move(int xNew, int yNew, Tile current, Enum orientation)
  {
    if (this.checkMovable(xNew, yNew, current))
    {
      this.px = this.x;
      this.py = this.y;
      this.playerOrientation = orientation;
      this.x += xNew;
      this.y += yNew;
      this.circle.setCenterX(this.x);
      this.circle.setCenterY(this.y);
      this.theta = Math.atan2((this.y - this.py), (this.x - this.px));
      this.location = grid[y/(int)location.getBounds().getHeight()][x/(int)location.getBounds().getWidth()];
    }
  }

  public void setCurrentTile(Tile next)
  {
    this.location = next;
  }

  private boolean checkMovable(int xNew, int yNew, Tile current)
  {
    boolean movable = false;
    xNew = this.x + xNew;
    yNew = this.x + yNew;

    for (int j = (current.getGridCol() - 1); j < (current.getGridCol() + 2); j++)
    {
      for (int i = (current.getGridRow() - 1); i < (current.getGridRow() + 2); i++)
      {
        if (i >= 0 && i < ZombieHouseModel.ROWS && j >= 0 && j < ZombieHouseModel.COLS)
        {
          boolean cont = grid[i][j].contains(xNew, yNew);
          boolean mova = grid[i][j].isMovable();
          boolean inte = new Movable(xNew, yNew, this.radius, this.location, this.grid, GridOrientation.pickRandomOrientation()).intersects(grid[i][j].getBounds());
          if ((cont || inte) && mova)
          {
            if (grid[i][j].contains(xNew, yNew) && grid[i][j].isMovable())
            {
              this.setCurrentTile(grid[i][j]);
              this.location = grid[i][j];
              return true;
            }
          }
        }
      }
    }
    return movable;
  }

  public double getDistanceTo(int xNew, int yNew)
  {
    double dist;

    dist = Math.sqrt(Math.pow((this.x - xNew), 2) + Math.pow((this.y - yNew), 2));

    return dist;
  }
}
