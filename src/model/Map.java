/**
 * Created by Tess Daughton, September 13th 2015
 * Takes in a tile map given by ZombieHouseModel
 */

package model;

import java.util.Random;

public class Map
{
  private Random random;
  private final int COLS;
  private final int ROWS;
  private Tile[][] grid;
  private int tileHeight;
  private int tileWidth;

  public Map(Tile[][] grid, int rows, int cols, int width, int height)
  {
    this.COLS = cols;
    this.ROWS = rows;
    this.grid = grid;
    this.tileHeight = height;
    this.tileWidth = width;

    random = new Random();
  }


  //============================================================================
  // This will throw one or more traps on the floor(not wall).
  //============================================================================
  public void throwGrabbableTraps(int numberOfTraps)
  {
    int x, y;
    for (int i = 0; i < numberOfTraps; i++)
    {
      while (true)
      { //Find x and y that we can throw a trap
        x = random.nextInt(COLS);
        y = random.nextInt(ROWS);

        if (grid[y][x].isMovable() && !grid[y][x].hasTrap()) break;
      }

      grid[y][x].throwGrabbableTrap();
    }
  }

  //============================================================================
  // This will grabTrap from the floor.
  //============================================================================
  public void grabTrap(int x, int y)
  {
    if (grid[y][x].getTrap().isGrabbable())
    {
      grid[y][x].removeTrap();
      //player.grapTrap();
    }
  }

  //============================================================================
  // This will install trap.
  //============================================================================
  private void installTrap(int x, int y)
  {
    //if(player.getNumberOfTraps() > 0)
    grid[y][x].installTrap();
    //player.installTrap();
  }

  //============================================================================
  // It will make the trap installed in given x and y coordinates on the map
  // explode. It should hurt zombies around it.
  //============================================================================
  private void fireTrap(int x, int y)
  {
    // zombie.getHurt(); or something like that
    grid[y][x].removeTrap();
  }

  public Tile[][] getGrid()
  {
    return this.grid;
  }
}
