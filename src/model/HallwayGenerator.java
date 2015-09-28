package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

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
  private int[][] map;
  private int[][] priorityMap;
  private  ArrayList<Point> doors;

  public HallwayGenerator(int[][] map, ArrayList<Point> doors)
  {
    this.map = map;
    this.doors = doors;

    this.priorityMap = new int[map.length][map[0].length];
    generateRandomHallway();
  }

  public int[][] getMap() { return map; }

  //============================================================================
  // At each point found to be alone (a wall tile that has all 8 adjacent tiles
  // are also walls) start a hallway and extend it as far as it can go.
  //============================================================================
  private void generateRandomHallway()
  {
    Point target = doors.get(0); // select any door.

    for(Point door: doors)
    {
      if(target != door) putHallway(findPath(door, target), target);
    }

    putWalls(target);
  }

  private void putWalls(Point target)
  {
    int x = target.x, y = target.y;
    for(int i=-1; i<2; i++)
    {
      for(int j=-1; j<2; j++)
      {
        if(x+i >= 0 && x+i < COL && y+j >= 0 && y+j < ROW && (i != 0 || j !=0))
        {
          if(map[y+j][x+i] == 0) map[y+j][x+i] = 16;
          else if(map[y+j][x+i] == 2)
          {
            map[y+j][x+i] = 1;
            putWalls(new Point(x+i, y+j));
          }
        }
      }
    }
  }

  private HashMap<Point, Point> findPath(Point start, Point target)
  {
    //System.out.println("("+start.x + ", " + start.y+") => (" +target.x + ", " + target.y + ")");

    HashMap<Point, Point> cameFrom = new HashMap<>();
    ArrayList<Point> frontier = new ArrayList<>();
    HashMap<Point, Integer> costSoFar = new HashMap<>();

    Point current;

    int newCost, priority;

    frontier.add(start);
    priorityMap[start.y][start.x] = 0;

    cameFrom.put(start, null);
    costSoFar.put(start, 0);

    while(!frontier.isEmpty())
    {
      current = poll(frontier);
      frontier.remove(current);

      if(current == target) break;

      for(Point next: getNeighbors(current))
      {
        newCost = costSoFar.get(current) + cost(current, next);
        if(!costSoFar.containsKey(next) || newCost < costSoFar.get(next))
        {
          costSoFar.put(next, newCost);
          priority = newCost;

          frontier.add(next);
          priorityMap[next.y][next.x] = priority;

          cameFrom.put(next, current);
        }
      }
    }

    return cameFrom;
  }

  private int cost(Point current, Point next)
  {
    if(next != null)
    {
      switch(map[next.y][next.x])
      {
        case 0: return 2;
        case 1: return 2;
        case 2: return 1;
        case 4: return 4;
        case 8: return 4;
        case 16: default: return 4;
      }
    }

    return 100;
  }

  private void putHallway(HashMap<Point, Point> cameFrom, Point target)
  {
    Point current = target;

    while(cameFrom.get(current) != null)
    {
      map[current.y][current.x] = 2;
      current = cameFrom.get(current);
    }
  }

  private Point poll(ArrayList<Point> frontier)
  {
    Point priorPoint = null;
    int priority = 0, tempPriority = 0;

    for(Point p: frontier)
    {
      tempPriority = priorityMap[p.y][p.x];

      if(priorPoint == null || priority < tempPriority)
      {
        priorPoint = p;
        priority = tempPriority;
      }
    }

    return priorPoint;
  }

  private void setPriority(Point p, int priority)
  {
    priorityMap[p.y][p.x] = priority;
  }

  private ArrayList<Point> getNeighbors(Point p)
  {
    ArrayList<Point> neighbors = new ArrayList<>();

    if(p.y-1>0 && map[p.y-1][p.x] != 16) neighbors.add(new Point(p.x, p.y-1));
    if(p.y+1<ROW && map[p.y+1][p.x] != 16) neighbors.add(new Point(p.x, p.y+1));
    if(p.x-1>0 && map[p.y][p.x-1] != 16) neighbors.add(new Point(p.x-1, p.y));
    if(p.x+1<COL && map[p.y][p.x+1] != 16) neighbors.add(new Point(p.x+1, p.y));

    return neighbors;
  }

  private Point findNearestDoor(Point start)
  {
    Point nearest = null;
    int nearestHeuristic = 0;
    int tempHeuristic = 0;

    for(Point p: doors)
    {
      tempHeuristic = heuristic(start, p);

      if((start != p ) && (nearest == null || nearestHeuristic > tempHeuristic))
      {
        nearest = p;
        nearestHeuristic = tempHeuristic;
      }
    }

    return nearest;
  }

  private int heuristic(Point p1, Point p2)
  {
    return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
  }

  //============================================================================
  // Returns true all the adjacent tiles are walls and the tile itself is also
  // a wall.
  //============================================================================
  private boolean isAlone(int x, int y)
  {
    return map[y][x] == 0 && map[y - 1][x - 1] == 0 && map[y - 1][x] == 0 &&
           map[y - 1][x + 1] == 0 && map[y][x - 1] == 0 && map[y][x] == 0 &&
           map[y][x + 1] == 0 && map[y + 1][x - 1] == 0 && map[y + 1][x] == 0 &&
           map[y + 1][x + 1] == 0;
  }

  //============================================================================
  // returns true if the tile is "extendable". (I will have to come up with
  // better name though).
  // Extendability indicates([ ] = wall, * = hallway trying to extend):
  //
  //    *  *  *       The tile wants to check extendability of adjacent tiles.
  //    * [ ] *   =>  Adjacent tiles here only mean tiles located north, west,
  //    *  *  *       east, south.
  //
  //    @  @  @
  //    @ [?] @
  //    @ [?] @  =>  Say that the tile wants to extend to north, then we want
  //    * [ ] *      @ marked tiles to be wall.
  //    *  *  *
  //
  // Since the cell will only move to 4 directions, either xInc(x increment) or
  // yInc(y increment) must be 0 but not both.
  //============================================================================
  private boolean isExtendable(int x, int y, int xInc, int yInc)
  {
    int newX = x + xInc * 3;
    int newY = y + yInc * 3;

    if (newX < 0 || newX >= COL || newY < 0 || newY >= ROW) return false;

    for (int i = -1; i < 1; i++)
    {
      for (int j = -1; j < 1; j++)
      {
        //if (map[y + i + yInc * 2][x + j + xInc * 2] > 0) return false;
        if (map[y + i + yInc][x + j + xInc] > 0) return false;
      }
    }

    return true;
  }

  //============================================================================
  // This is a method to extend hallway as far as it can go but each time the
  // direction is kind of randomized. If the hallway can't be extended anymore
  // it will just stop.
  //============================================================================
  private void extendHallway(int x, int y)
  {
    int xInc = (RANDOM.nextInt(1) > 0) ? 1 : -1;
    int yInc = (RANDOM.nextInt(1) > 0) ? 1 : -1;

    if (!isExtendable(x, y, 0, yInc) && !isExtendable(x, y, xInc, 0) &&
        !isExtendable(x, y, -xInc, 0) && !isExtendable(x, y, 0, -yInc))
    {
      map[y][x] = 4;
      return;
    }

    if (isExtendable(x, y, 0, yInc))
    {
      map[y + yInc][x] = 2;
      //map[y + yInc * 2][x] = 2;

      //extendHallway(x, y + yInc * 2);
      extendHallway(x, y + yInc);
    }
    if (isExtendable(x, y, xInc, 0))
    {
      map[y][x + xInc] = 2;
      //map[y][x + xInc * 2] = 2;

      //extendHallway(x + xInc * 2, y);
      extendHallway(x + xInc, y);
    }
    if (isExtendable(x, y, -xInc, 0))
    {
      map[y][x - xInc] = 2;
      //map[y][x - xInc * 2] = 2;

      //extendHallway(x - xInc * 2, y);
      extendHallway(x - xInc, y);
    }
    if (isExtendable(x, y, 0, -yInc))
    {
      map[y - yInc][x] = 2;
      //map[y - yInc * 2][x] = 2;

      //extendHallway(x, y - yInc * 2);
      extendHallway(x, y - yInc);
    }
  }


  /************************* Hallway Removing Methods *************************/
  //============================================================================
  // If the end of hallway tile is only the one connected to the room, it should
  // not be removed.
  //============================================================================
  private boolean isConnectingDoorway(int x, int y)
  {
    return ((y-1 > 0 && map[y-1][x] == 8) || (y+2 < ROW && map[y+1][x] == 8) ||
            (x-1 > 0 && map[y][x-1] == 8) || (x+2 < COL && map[y][x+1] == 8)) &&
           ((y-1 > 0 && map[y-1][x] == 2) || (y+2 < ROW && map[y+1][x] == 2) ||
            (x-1 > 0 && map[y][x-1] == 2) || (x+2 < COL && map[y][x+1] == 2));
  }

  //============================================================================
  // If the hallway goes to multiple direction at the point, it will stop
  // removing hallway tile there.
  //============================================================================
  private boolean isIntersection(int x, int y)
  {
    boolean a, b, c, d;

    // is the hallway continuing
    a = y-1 > 0 && map[y-1][x] == 2; // to the north?
    b = y+2 < ROW && map[y+1][x] == 2; // to the south?
    c = x-1 > 0 && map[y][x-1] == 2; // to the west?
    d = x+2 < COL && map[y][x+1] == 2; // to the east?

    // return true if at least 2 of them are true
    return a?  b || c || d : b? c || d : c && d;
  }

  //============================================================================
  // This will remove hallways at
  // 1. intersection (where two or more hallways intersect each other)
  // 2. door (the end of the hallway)
  //============================================================================
  private void removeHallways(int x, int y)
  {
    if(isIntersection(x, y)) return;

    map[y][x] = 0;

    if(x-1 >= 0 && map[y][x-1] == 2)
    {
      removeHallways(x-1, y);
    }
    else if(x+1 < COL-1 && map[y][x+1] == 2)
    {
      removeHallways(x+1, y);
    }
    else if(y-1 >= 0 && map[y-1][x] == 2)
    {
      removeHallways(x, y-1);
    }
    else if(y+1 < ROW-1 && map[y+1][x] == 2)
    {
      removeHallways(x, y+1);
    }
  }

  //============================================================================
  // Search for end of the hallway and start removing.
  //============================================================================
  private void removeUnusedHallways()
  {
    for(int i=0; i<ROW; i++)
    {
      for(int j=0; j<COL; j++)
      {
        if(map[i][j] == 4 && !isConnectingDoorway(j, i))
        {
          removeHallways(j, i);
        }
      }
    }
  }

}
