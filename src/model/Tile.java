/**
 * Created by Miri on 9/8/15.
 * Edits: Tess Daughton, September 13th, 2015
 */

package model;

import java.awt.*;

public class Tile extends Rectangle
{
  protected int x, y;
  protected int type;

  //private Rectangle bounds;
  protected int gridRow;
  protected int gridCol;
  protected boolean occupied;
  protected boolean movable;

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

//  public boolean contains(int x, int y)
//  {
//    boolean contains = false;
//    if (this.contains(x, y)) contains = true;
//    if(x>=this.xMin && x<=xMax && y>=yMin && y<=yMax) contains = true;
//    return contains;
//  }

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
    return (int)this.getCenterX();
  }

  public int getCenterTileY()
  {
    return (int)this.getCenterY();
  }

  public int getGridRow()
  {
    return this.gridRow;
  }

  public int getGridCol()
  {
    return this.gridCol;
  }


  public Rectangle getBounds()
  {
    return this;
  }

//  public void setBounds(int xMin, int xMax, int yMin, int yMax)
//  {
//    this.setBounds(new Rectangle(xMin, yMin, xMax - xMin, yMax - yMin));
//  }

  public int getType()
  {
    return type;
  }

  public void setType(int type)
  {
    this.type = type;
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
}
