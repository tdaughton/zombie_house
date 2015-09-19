package model;

import java.util.HashMap;


//==============================================================================
// Miri Ryu
// CS351L-001
//
// This is the pathfinder class. This is not completely implemented to for our
// project. I commentized everthing because it will return error. I will try to
// implement this for zombies.
//==============================================================================
public class Pathfinder
{
  private HashMap<Tile, Integer> costSoFar;
  private Map map;
  private int cost = 0;

  //============================================================================
  // This will need to see the map. The map needs to have tile with different
  // cost.
  //============================================================================
  public Pathfinder(Map map)
  {
    this.map = map;
  }
/**
  //============================================================================
  // This is the a star search algorithm.
  //============================================================================
  public HashMap<Tile, Tile> aStarSearch(Tile start, Tile end)
  {
    HashMap<Tile, Tile> cameFrom = new HashMap<>();
    PriorityQueue<Tile> frontier = new PriorityQueue<>();

    Tile current;
    costSoFar = new HashMap<>();

    frontier.add(start);
    start.setPriority(0);
    cameFrom.put(start, null);
    costSoFar.put(start, 0);

    while(!frontier.isEmpty())
    {
      current = frontier.poll();

      if(current == end) break;

      for(Tile next : map.getNeighbors(current.getX(), current.getY()))
      {
        int newCost = costSoFar.get(current) + next.getCost();

        if(!costSoFar.containsKey(next) || newCost < costSoFar.get(next))
        {
          costSoFar.put(next, newCost);
          int priority = newCost + heuristic(end.getX(), end.getY(), next.getX(), next.getY());
          next.setPriority(priority);
          frontier.add(next);
          cameFrom.put(next, current);
        }
      }
    }

    System.out.println(costSoFar.get(end));

    return cameFrom;
  }
*/

  //============================================================================
  // This only helps to print out the cost. This is not very important part
  // of the class. I will leave this to test code in the future.
  //============================================================================
  public int getCost()
  {
    return cost;
  }

  //============================================================================
  // This will calculate the heuristic for each path.
  //============================================================================
  private int heuristic(int x1, int y1, int x2, int y2)
  {
    return Math.abs(x1 - x2) + Math.abs(y1 - y2);
  }
}
