
/**
 * Created by Miri on 9/8/15.
 * Edits: Tess Daughton, September 13th, 2015
 */
package model;

public class Tile
{
  private int x, y;
  private int type;

  private int xMin;
  private int xMax;
  private int yMin;
  private int yMax;
  private int gridRow;
  private int gridCol;
  private boolean occupied;
  private boolean movable;


  public Tile(int gridRow, int gridCol, Tile[][] grid)
  {

    this.gridRow = gridRow;
    this.gridCol = gridCol;
    this.movable=false;
  }
  private void setOccupied(boolean occupationStatus )
  {
   this.occupied=occupationStatus;
  }
  public boolean contains(int x, int y)
  {
    boolean contains = false;
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
    return (xMin+xMax)/2;
  }
  public int getCenterTileY()
  {
    return (yMin+yMax)/2;
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
    return this.xMin;
  }
  public int getXMax()
  {
    return this.xMax;
  }
  public int getYMin() { return this.yMin; }
  public int getYMax()
  {
    return this.yMax;
  }

  public void setBounds(int xMin, int xMax, int yMin, int yMax)
  {
    this.xMin = xMin;
    this.xMax = xMax;
    this.yMin = yMin;
    this.yMax = yMax;
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


}
