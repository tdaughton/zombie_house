//==============================================================================
// Miri Ryu
// CS351L001
// Project 1 Zombie House
//
// Trap class only holds its x and y coordinates and grab-abl-ity :)
//==============================================================================

package model;
import Resources.TrapLoader;

public class Trap extends Movable
{
  private int x, y;
  private boolean grabbable;
  private boolean explosionFinished;
  private boolean explosionTriggered;
  private boolean soundPlayed;
  private static Movable movableTrigger;
  private TrapLoader trapLoader;

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
    this.explosionFinished = false;
    this.soundPlayed = false;
    this.trapLoader = new TrapLoader();
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

  public void setExplosionFinished()
  {
    this.explosionFinished=true;
  }

  public void setMovableTrigger(Movable mover)
  {
    movableTrigger=mover;
  }

  public void setSoundPlayed()
  {
    this.soundPlayed=true;
  }

  public Boolean getExplosionFinished()
  {
    return this.explosionFinished;
  }

  public boolean explosionTriggered()
  {
    return this.explosionTriggered;
  }

  public TrapLoader getTrapLoader()
{
  return this.trapLoader;
}

  public Boolean getSoundPlayed()
  {
    return this.soundPlayed;
  }
}
