package model;

//============================================================================
// Simple class to store information for rectangle. I thought it will be
// lighter using my own class rather than using rectangle.
//============================================================================
public class Room
{
  public int x1, x2, y1, y2, width, height;

  public Room(int x1, int y1, int x2, int y2)
  {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    width = Math.abs(x2 - x1);
    height = Math.abs(y2 - y1);
  }
}