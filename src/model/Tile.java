
/**
 * Created by Miri on 9/8/15.
 * Edits: Tess Daughton, September 13th, 2015
 */

package model;

import java.awt.*;

public class Tile
{
  private int x, y;
  private int type;

  private int xMin;
  private int xMax;
  private int yMin;
  private int yMax;
  private Rectangle bounds;
  private int gridRow;
  private int gridCol;
  private boolean occupied;
  private boolean movable;

  private Trap trap;

  public Tile(int gridRow, int gridCol, Tile[][] grid)
  {

    this.gridRow = gridRow;
    this.gridCol = gridCol;
    this.movable = false;

    this.trap = null;
  }

  private void setOccupied(boolean occupationStatus)
  {
    this.occupied = occupationStatus;
  }

  public boolean contains(int x, int y)
  {
    boolean contains = false;
    if (this.bounds.contains(x, y)) contains = true;
    if(x>=xMin && x<=xMax && y>=yMin && y<=yMax) contains = true;
    return contains;
  }

  public boolean isOccupied()
  {
    return this.occupied;
  }

  public boolean isMovable()
  {
    return this.movable;
  }

  public String getTileType()
  {
    return "Tile";
  }

  public int getCenterTileX()
  {
    return (int)this.bounds.getCenterX();
    //return (xMin+xMax)/2;
  }

  public int getCenterTileY()
  {
    return (int)this.bounds.getCenterY();
    //return (yMin+yMax)/2;
  }

  public int getGridRow()
  {
    return this.gridRow;
  }

  public int getGridCol()
  {
    return this.gridCol;
  }

  public int getXMin()
  {
    return (int)this.bounds.getMinX();
    //return this.xMin;
  }

  public int getXMax()
  {
    return (int)this.bounds.getMaxX();
    //return this.xMax;
  }

  public int getYMin()
  {
    return (int)this.bounds.getMinY();
    //return this.yMin;
  }

  public int getYMax()
  {
    return (int)this.bounds.getMaxY();
    //return this.yMax;
  }

  public Rectangle getBounds()
  {
    return bounds;
  }

  public void setBounds(int xMin, int xMax, int yMin, int yMax)
  {
    this.xMin = xMin;
    this.xMax = xMax;
    this.yMin = yMin;
    this.yMax = yMax;
    this.bounds = new Rectangle(xMin, yMin, xMax - xMin, yMax - yMin);
  }

  public int getType()
  {
    return type;
  }

  public void setType(int type)
  {
    this.type = type;
  }

  public int getX()
  {
    return x;

  }

  public int getY()
  {
    return y;
  }

  //============================================================================
  // The traps are managed with following methods. All the methods are named
  // kind of straightforward so I abbreviate the explanations.
  //============================================================================
  public void throwGrabbableTrap()
  {
    trap = new Trap(x, y, true);
  }

  public void removeTrap()
  {
    trap = null;
  }

  public void installTrap()
  {
    trap = new Trap(x, y, false);
  }

  public Trap getTrap()
  {
    return trap;
  }

  public boolean hasTrap()
  {
    return trap != null;
  }
  //============================================================================
  // The traps are managed with following methods. All the methods are named
  // kind of straightforward so I abbreviate the explanations.
  //============================================================================
//  public void throwGrabbableTrap()
//  {
//    trap = new Trap(x, y, true);
//  }
//
//  public void removeTrap()
//  {
//    trap = null;
//  }
//
//  public void installTrap()
//  {
//    trap = new Trap(x, y, false);
//  }
//
//  public Trap getTrap()
//  {
//    return trap;
//  }
//
//  public boolean hasTrap()
//  {
//    return trap != null;
//  }
}
