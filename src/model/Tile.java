/**
 * Created by Miri on 9/8/15.
 * Edits: Tess Daughton, September 13th, 2015
 */

package model;

import java.awt.Rectangle;
import java.util.ArrayList;

//==============================================================================
// I implemented comparable interface to implement priority queue. - Miri :)
//==============================================================================
public class Tile extends Rectangle implements Comparable<Tile>
{
  protected int x, y;
  protected int type;

  protected double priority; // priority will be calculated by the pathfinder.
  protected int gridRow;
  protected int gridCol;
  protected boolean occupied;
  protected boolean movable;
  protected boolean hasTrap;
  boolean hallway;
  private boolean obstacle;
  private boolean exitFlag = false;
  private Trap trap;

  public Tile(int gridRow, int gridCol, boolean movable, int type)
  {
    super(gridCol, gridRow, 1, 1);

    this.gridRow = gridRow;
    this.gridCol = gridCol;
    this.movable = movable;
    this.type = type;
    this.hasTrap = false;
    this.trap = null;

    x = gridCol;
    y = gridRow;

    hallway = false;
    obstacle = false;
  }

  public boolean isHallway()
  {
    return hallway;
  }

  public void setHallway(boolean hallway)
  {
    this.hallway = hallway;
  }

  public boolean isOccupied()
  {
    return this.occupied;
  }

  private void setOccupied(boolean occupationStatus)
  {
    this.occupied = occupationStatus;
  }

  public boolean isMovable()
  {
    return this.movable;
  }

  public void setMovable(boolean movable)
  { this.movable=movable;
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

  public boolean isObstacle()
  {
    return obstacle;
  }

  public void setObstacle(boolean obstacle)
  {
    this.obstacle = obstacle;
  }

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
    this.trap = null;
  }

  public void installTrap(Trap trap)
  {
    this.trap = trap;
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

  public boolean hasZombie(ArrayList<Zombie> zombies)
  {
    for (Zombie zombie : zombies)
    {
      if (this.contains(zombie.getX(), zombie.getY())) return true;
    }
    return false;
  }

  //============================================================================
  // Setter and getter of priority. Priority will be set and called by the
  // pathfinder.
  //============================================================================
  public double getPriority()
  {
    return this.priority;
  }

  public void setPriority(double priority)
  {
    this.priority = priority;
  }

  //============================================================================
  // Compare priority with another tile.
  //============================================================================
  @Override
  public int compareTo(Tile anotherTile)
  {
    if (this.priority < anotherTile.getPriority()) return -1;
    if (this.priority > anotherTile.getPriority()) return 1;
    return 0;
  }

  public boolean hasExitFlag()
  {
    return this.exitFlag;
  }

  public void setExitFlag()
  {
    this.exitFlag = true;
  }
}
