//==============================================================================
// Miri Ryu
// CS351L001
// Project 1 Zombie House
//
// Trap class only holds its x and y coordinates and grab-abl-ity :)
//==============================================================================

package model;

public class Trap
{
  private int x, y;
  private boolean grabbable;

  //============================================================================
  // Creates a trap class with x and y coordinates. Trap is only 1-tile size
  // big.
  //============================================================================
  public Trap(int x, int y, boolean grabbable)
  {
    this.x = x;
    this.y = y;
    this.grabbable = grabbable;
  }

  //============================================================================
  // We don't need setter for x and y because once it is installed it won't be
  // moved or removed unless the trap is exploded.
  //============================================================================
  public int getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
  }

  public boolean isGrabbable()
  {
    return grabbable;
  }
}
