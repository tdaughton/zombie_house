package model;

/**
 * Created by Miri on 9/8/15.
 */

public class Tile extends Grid
{

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
    super(grid);
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

  public boolean getIsMovable()
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

  public void setBounds(int xMin, int xMax, int yMin, int yMax)
  {
    this.xMin = xMin;
    this.xMax=xMax;
    this.yMin = yMin;
    this.yMax=yMax;
  }
}
