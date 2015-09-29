package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

//==============================================================================
// Miri Ryu
// CS351L-001
// Proj1 Zombie House
//
// HallwayGenerator will generate hallways from a door tile to another or
// from a door tile to a hallway tile.
//==============================================================================
public class HallwayGenerator implements GameMap
{
  private Tile[][] map;
  private ArrayList<Tile> doors;

  public HallwayGenerator(Tile[][] map, ArrayList<Tile> doors)
  {
    this.map = map;
    this.doors = doors;

    generateRandomHallway();
  }

  public Tile[][] getMap()
  {
    return map;
  }

  //============================================================================
  // At each point found to be alone (a wall tile that has all 8 adjacent tiles
  // are also walls) start a hallway and extend it as far as it can go.
  //============================================================================
  private void generateRandomHallway()
  {
    Tile target = doors.get(0); // select any door.
    doors.remove(0);

    for (Tile door : doors)
    {
      putHallway(findPath(door, target), target);
    }

    putWalls(target);
  }

  //============================================================================
  // At each point found to be alone (a wall tile that has all 8 adjacent tiles
  // are also walls) start a hallway and extend it as far as it can go.
  //============================================================================
  private void putWalls(Tile target)
  {
    int x = target.x, y = target.y;
    for(int i=-1; i<2; i++)
    {
      for(int j=-1; j<2; j++)
      {
        if(x+i >= 0 && x+i < COL && y+j >= 0 && y+j < ROW && (i != 0 || j !=0))
        {
          if (map[y + j][x + i].type == 0)
          {
            map[y + j][x + i] = new Wall(y + j, x + i, null);
          } else if (map[y + j][x + i].isHallway())
          {
            map[y + j][x + i].setHallway(false);
            putWalls(map[y + j][x + i]);
          }
        }
      }
    }
  }

  //============================================================================
  // Depth base search to install hallway.
  //============================================================================
  private HashMap<Tile, Tile> findPath(Tile start, Tile target)
  {
    HashMap<Tile, Integer> costSoFar = new HashMap<>();
    HashMap<Tile, Tile> cameFrom = new HashMap<>();

    PriorityQueue<Tile> frontier = new PriorityQueue<>();

    Tile current;

    frontier.add(start);
    start.setPriority(0);
    cameFrom.put(start, null);
    costSoFar.put(start, 0);

    while(!frontier.isEmpty())
    {
      current = frontier.poll();

      if(current == target) break;

      for (Tile next : getNeighbors(current))
      {// If there are tiles with different cost, add next.getCost() instead of 1.
        int newCost = costSoFar.get(current) + cost(next);

        if(!costSoFar.containsKey(next) || newCost < costSoFar.get(next))
        {
          costSoFar.put(next, newCost);

          int priority = newCost;

          next.setPriority(priority);
          frontier.add(next);
          cameFrom.put(next, current);
        }
      }
    }

    return cameFrom;
  }

  //============================================================================
  // Cost for each tile. Hallways are preferable compared to other tiles.
  //============================================================================
  private int cost(Tile next)
  {
    if (next.isHallway())
    {
      return 1;
    } else
    {
      return 2;
    }
  }

  //============================================================================
  // Putting Hallway using cameFram HashMap.
  //============================================================================
  private void putHallway(HashMap<Tile, Tile> cameFrom, Tile target)
  {
    Tile current = target;

    while(cameFrom.get(current) != null)
    {
      map[current.y][current.x] = new Floor(current.y, current.x, null);
      map[current.y][current.x].setHallway(true);

      current = cameFrom.get(current);
    }
  }

  //============================================================================
  // Returns all the neighbors that is not wall. Hallways can't cross walls.
  //============================================================================
  private ArrayList<Tile> getNeighbors(Tile p)
  {
    ArrayList<Tile> neighbors = new ArrayList<>();

    if (isFriendly(p.x, p.y - 1)) neighbors.add(map[p.y - 1][p.x]);
    if (isFriendly(p.x, p.y + 1)) neighbors.add(map[p.y + 1][p.x]);
    if (isFriendly(p.x - 1, p.y)) neighbors.add(map[p.y][p.x - 1]);
    if (isFriendly(p.x + 1, p.y)) neighbors.add(map[p.y][p.x + 1]);

    return neighbors;
  }

  //============================================================================
  // See if the adjacent Tile can be our neighbor.
  //============================================================================
  private boolean isFriendly(int x, int y)
  {
    if (x < 0 || x >= COL || y < 0 || y >= ROW) return false;
    return map[y][x].type != 1;
  }
}
