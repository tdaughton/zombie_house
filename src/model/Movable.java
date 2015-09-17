/**
 * Created by Tess Daughton, September 13th, 2015
 */
package model;

import javafx.scene.shape.Circle;
import java.awt.Rectangle;

public class Movable
{
  protected int x;
  protected int y;
  protected int radius;
  protected Circle circle;
  protected Tile location;
  protected Tile[][] grid;
  private Enum playerOrientation;


  public Movable(int x, int y, int radius, Tile location, Tile[][] grid, Enum playerOrientation)
  {

    this.x=x;
    this.y=y;
    this.radius=radius;
    this.location = location;
    this.grid = grid;
    this.playerOrientation=playerOrientation;
    circle = new Circle(x,y,radius);

  }

  public int getX() { return this.x; }
  public int getY() { return this.y; }
  public int getRadius(){ return this.radius; }
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
    double r1 = Math.pow(otherMovable.getRadius() - this.getRadius(), 2);
    double r2 = Math.pow(otherMovable.getRadius()+this.getRadius(),2);

    double distance = Math.pow((otherMovable.getCenterX()-this.getX()),2) +
                      Math.pow((otherMovable.getCenterY()-this.getY()),2);

    if(distance>=r1 && distance<=r2)
    {
      intersects=true;
    }
    return intersects;
  }
//http://stackoverflow.com/questions/401847/circle-rectangle-collision-detection-intersection
  public boolean intersects (Rectangle immovableSpace)
  {
    boolean intersects = false;
    double r1 = Math.abs(this.getX() - immovableSpace.getX());
    double r2 = Math.abs(this.getY()-immovableSpace.getY());

    int rectWidth = immovableSpace.width/2;
    int rectHeight = immovableSpace.height/2;

    if(r1> rectWidth+this.getRadius()) intersects=false;
    if(r2> rectHeight+this.getRadius()) intersects=false;
    if(r1<= rectWidth+this.getRadius()) intersects=true;
    if(r2<=rectHeight+this.getRadius()) intersects=true;

    double cd = Math.pow(r1 - rectWidth,2) + Math.pow(r2 - rectHeight,2);
    double cd_sqrt = Math.sqrt(cd);

    if(cd_sqrt <= Math.pow(this.getRadius(),2)) intersects = true;


    return intersects;
  }


  public void move(int xNew, int yNew, Tile current, Enum orientation)
  {
    if(this.checkMovable(xNew,yNew, current))
    {
      this.playerOrientation=orientation;
      this.x += xNew;
      this.y += yNew;
    }
  }

  private boolean checkMovable(int xNew, int yNew, Tile current)
  {
    xNew = this.getX() + xNew;
    yNew = this.getY() + yNew;

    for (int j = (current.getGridCol() - 1); j < (current.getGridCol() + 2); j++)
      for (int i = (current.getGridRow() - 1); i < (current.getGridRow() + 2); i++)
      {
        if(i>=0 && i<ZombieHouseModel.ROWS && j>=0 && j<ZombieHouseModel.COLS)
        {
          if (grid[i][j].contains(xNew, yNew) && grid[i][j].isMovable())
          {
            System.out.println(grid[i][j].getTileType());
            this.location=grid[i][j];
            return true;
          }
        }
      }
    return false;
  }
}
