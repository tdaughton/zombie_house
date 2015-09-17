/**
 * Created by Miri on 9/8/15.
 * Edits: Tess Daughton, September 13th, 2015
 */

package model;

import java.awt.*;

public class Tile
{
  protected int type;
  protected Rectangle bounds;
  protected int gridRow;
  protected int gridCol;
  protected boolean occupied;
  protected boolean movable;
  protected Trap trap;

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
    return this.bounds.contains(x, y);
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
  }

  public int getCenterTileY()
  {
    return (int)this.bounds.getCenterY();
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
  }

  public int getXMax()
  {
    return (int)this.bounds.getMaxX();
  }

  public int getYMin()
  {
    return (int)this.bounds.getMinY();
  }

  public int getYMax()
  {
    return (int)this.bounds.getMaxY();
  }

  public Rectangle getBounds()
  {
    return bounds;
  }

  public void setBounds(int xMin, int xMax, int yMin, int yMax)
  {
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

  //============================================================================
  // The traps are managed with following methods. All the methods are named
  // kind of straightforward so I abbreviate the explanations.
  //============================================================================
  public void throwGrabbableTrap()
  {
    trap = new Trap(gridCol, gridRow, true);
  }

  public void removeTrap()
  {
    trap = null;
  }

  public void installTrap()
  {
    trap = new Trap(gridCol, gridRow, false);
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
