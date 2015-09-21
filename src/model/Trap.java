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
  private boolean explosionTriggered;
  private Movable movableTrigger;

  //============================================================================
  // Creates a trap class with x and y coordinates. Trap is only 1-tile size
  // big.
  //============================================================================
  public Trap(int x, int y, boolean grabbable)
  {
    this.x = x;
    this.y = y;
    this.grabbable = grabbable;
    this.explosionTriggered = false;
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

  public void setExplosionTrigger()
  {
    this.explosionTriggered=true;
  }
  public void setMovableTrigger(Movable mover)
  {
    this.movableTrigger=mover;
  }
  public Movable getMovableTrigger()
  {
    return this.movableTrigger;
  }
  public boolean explosionTriggered()
  {
    return this.explosionTriggered;
  }
}
