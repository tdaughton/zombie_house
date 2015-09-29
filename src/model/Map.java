/**
 * Created by Tess Daughton, September 13th 2015
 * Takes in a tile map given by ZombieHouseModel
 */

package model;

import java.util.ArrayList;
import java.util.Random;

public class Map
{
  private Random random;
  private final int COLS;
  private final int ROWS;
  private Tile[][] grid;

  /**
   * Represents the house
   * @param grid
   * @param rows
   * @param cols
   */
  public Map(Tile[][] grid, int rows, int cols)
  {
    this.COLS = cols;
    this.ROWS = rows;
    this.grid = grid;

    random = new Random();
  }

  protected void setMapGrid (Tile[][] grid)
  {
    this.grid = grid;
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
    Trap trap = new Trap(x, y, true);
    grid[y][x].installTrap(trap);
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

  //============================================================================
  // This will return adjacent tiles of tiles[y][x]. One tile will have maximum
  // 4 neighboring tiles. (Zombies move only in 4 directions.)
  //============================================================================
  public ArrayList<Tile> getNeighbors(int x, int y)
  {
    ArrayList<Tile> neighbors = new ArrayList<>();

    if(x-1 >= 0 && grid[y][x-1].isMovable()) neighbors.add(grid[y][x-1]);
    if(x+1 < COLS && grid[y][x+1].isMovable()) neighbors.add(grid[y][x+1]);
    if(y-1 >= 0 && grid[y-1][x].isMovable()) neighbors.add(grid[y-1][x]);
    if(y+1 < ROWS && grid[y+1][x].isMovable()) neighbors.add(grid[y+1][x]);

    return neighbors;
  }
}
