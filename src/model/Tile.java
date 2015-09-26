/**
 * Created by Miri on 9/8/15.
 * Edits: Tess Daughton, September 13th, 2015
 */

package model;

import java.awt.Rectangle;


//==============================================================================
// I implemented comparable interface to implement priority queue. - Miri :)
//==============================================================================
public class Tile extends Rectangle implements Comparable<Tile>
{
  protected int x, y;
  protected int type;

  protected int priority; // priority will be calculated by the pathfinder.

  //private Rectangle bounds;
  protected int gridRow;
  protected int gridCol;
  protected boolean occupied;
  protected boolean movable;
  protected boolean hasTrap;

  private Trap trap;

  public Tile(int gridRow, int gridCol, Tile[][] grid)
  {
    this.gridRow = gridRow;
    this.gridCol = gridCol;
    this.movable = false;
    this.hasTrap=false;
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
    return this.type;
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
    this.trap = new Trap(x, y, true);
  }

  public void removeTrap()
  {
    this.hasTrap=false;
  }

  public void installTrap()
  {
    this.trap = new Trap((int) this.getCenterX(),(int)this.getCenterY(),true);
    this.hasTrap = true;
  }

  public Trap getTrap()
  {
    return this.trap;
  }

  public boolean hasTrap()
  {
    return this.hasTrap;
  }

  //============================================================================
  // Setter and getter of priority. Priority will be set and called by the
  // pathfinder.
  //============================================================================
  public int getPriority()
  {
    return this.priority;
  }

  public void setPriority(int priority)
  {
    this.priority = priority;
  }

  //============================================================================
  // Compare priority with another tile.
  //============================================================================
  @Override
  public int compareTo(Tile anotherTile)
  {
    if(this.priority < anotherTile.getPriority()) return -1;
    if(this.priority > anotherTile.getPriority()) return 1;
    return 0;
  }
}
