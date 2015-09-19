/**
 * Created by Tess Daughton, September 13th, 2015
 */

package model;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class serves as the model for Zombies in Zombie House. It specifies behavior unique to
 * Zombies among Movables including automatic walking, olfactory sense, and chasing down the Player.
 */
public class Zombie extends Movable implements Alive
{
  /**
   * Enumerator to give zombie types fancy names.
   */
  private enum WalkType
  {
    RANDOM, LINE
  }

  private static final double SPEED_WALK = 0.5f;
  private static final double RATE_ACT = 2.0f;
  private static final double DIST_SMELL = 7.0f;
  private static final Random rng = new Random();
  private ArrayList<Tile> path;
  private WalkType wType;
  private double theta;

  // I thought maybe depending on the types of zombie, how much damage they take
  // and how much health they recover each second may differ. If makes the game
  // too difficult, then let's not do this. :D -Miri
  private int originalHealth;
  private int currentHealth;
  private double healingRate;
  private double defenceRate;
  private double damage;
  private boolean dead;

  /**
   * Full constructor
   * @param x                    X coordinate of center point (in pixels)
   * @param y                    Y coordinate of center point (in pixels)
   * @param radius               Radius of bounding circle (in pixels)
   * @param location             Tile location containing center point
   * @param grid                 Reference to Zombie House map
   */
  public Zombie(int x, int y, int radius, Tile location, Tile[][] grid, Enum zombieOrientation)
  {
    super(x, y, radius, location, grid, zombieOrientation);
    wType = (rng.nextBoolean() ? WalkType.RANDOM : WalkType.LINE);
    path = new ArrayList<>();
  }

  /**
   * Walk in predetermined direction or path
   */
  private void walk()
  {
    int xNew = (int)(SPEED_WALK * Math.sin(theta));
    int yNew = (int)(SPEED_WALK * Math.cos(theta));
  }

  /**
   * Decide how to walk based on WalkType and distance to player.
   */
  private void decisionEngine()
  {
    /*int pcX = player.getX();
    int pcY = player.getY();
    if (getDistanceTo(pcX, pcY) < DIST_SMELL)
    {
      int pCol = (int)(pcX / location.getBounds().getWidth());
      int pRow = (int)(pcY / location.getBounds().getHeight());
      path = aStar(location, grid[pRow][pCol]);
    }
    else
    {*/
    if (this.wType==WalkType.RANDOM)
    {
      theta = rng.nextDouble() * Math.PI * 2.0;
    }
    double nextX = SPEED_WALK * Math.sin(Math.toRadians(theta) + x);
    double nextY = SPEED_WALK * Math.cos(Math.toRadians(theta) + y);
    if (canMoveTo(nextX, nextY))
    {
      int nextCol = (int)(nextX / location.getBounds().getWidth());
      int nextRow = (int)(nextY / location.getBounds().getHeight());
      path.add(grid[nextRow][nextCol]);
    }
    else if (this.wType == WalkType.LINE)
    {
      theta = rng.nextDouble() * Math.PI * 2.0;
    }
    //}
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
    return currentHealth;
  }

  @Override
  public void updateCurrentStatus()
  {
    currentHealth -= damage;
    damage -= damage*defenceRate;

    if(currentHealth <= 0) dead = true;
  }

  @Override
  public boolean isDead()
  {
    return dead;
  }
}
