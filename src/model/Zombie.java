/**
 * Created by Tess Daughton, September 13th, 2015
 */

package model;


public class Zombie extends Movable implements Alive
{
  private static final double SPEED_WALK = 0.5f;
  private static final double RATE_ACT = 2.0f;
  private static final double DIST_SMELL = 7.0f;

  // I thought maybe depending on the types of zombie, how much damage they take
  // and how much health they recover each second may differ. If makes the game
  // too difficult, then let's not do this. :D -Miri
  private int originalHealth;
  private int currentHeealth;
  private double healingRate;
  private double defenceRate;
  private double damage;
  private boolean dead;


  public Zombie(int x, int y, int radius, Tile location, Tile[][] grid)
  {
    super(x, y, radius, location, grid, GridOrientation.pickRandomOrientation());

  }

  private void setX(int x1)
  {
    this.circle.setCenterX(x1);
  }

  private void setY(int y1)
  {
    this.circle.setCenterY(y1);
  }


  //============================================================================
  // Zombies should take damage from trap.
  //============================================================================
  @Override
  public void getBurn(int damage)
  {
    this.damage = damage;
  }

  @Override
  public int getHealth()
  {
    return currentHeealth;
  }

  @Override
  public void updateCurrentStatus()
  {
    currentHeealth -= damage;
    damage -= damage*defenceRate;

    if(currentHeealth <= 0) dead = true;
  }

  @Override
  public boolean isDead()
  {
    return dead;
  }
}
