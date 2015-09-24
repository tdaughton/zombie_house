
/**
 * Created by Tess Daughton, September 16th, 2015
 */
package model;
import java.util.Random;

/**
 * Contains all possible grid orientations for a movable object inside of the ZombieHouseModel
 */
public enum GridOrientation
{ NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST,SOUTHWEST;

  /**
   * Used to randomly orient Zombies and Player upon spawning
   * @return some grid orientation
   */
  public static GridOrientation pickRandomOrientation()
  {
    Random rand = new Random();
    int randX = rand.nextInt(7);
    if(randX==0) return NORTH;
    if(randX==1) return SOUTH;
    if(randX==2) return EAST;
    if(randX==3) return WEST;
    if(randX==4) return NORTHEAST;
    if(randX==5) return NORTHWEST;
    if(randX==6) return SOUTHEAST;
    if(randX==7) return SOUTHWEST;
    return NORTH;
  }
}

