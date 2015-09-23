package model;

import java.util.HashMap;
import java.util.PriorityQueue;


//==============================================================================
// Miri Ryu
// CS351L-001
//
// Pathfinder will pass path for each zombies to move. Zombies should get a
// pathfinder when they are 'around' player. While zombies are 'around' player,
// and player moves, zombies should get new path from their current tile to the
// player. (use aStarSearch method)
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

      for(Tile next :map.getNeighbors((int)current.getX(), (int)current.getY()))
      {// If there are tiles with different cost, add next.getCost() instead of 1.
        int newCost = costSoFar.get(current) + 1;

        if(!costSoFar.containsKey(next) || newCost < costSoFar.get(next))
        {
          //TODO: It should be able to find out when there is no way to get to the end.
          costSoFar.put(next, newCost);

          int heuristic = heuristic((int) end.getX(), (int) end.getY(),
                                    (int) next.getX(), (int) next.getY());
          int priority = newCost + heuristic;

          next.setPriority(priority);
          frontier.add(next);
          cameFrom.put(next, current);
        }
      }
    }
    //System.out.println(costSoFar.get(end));

    return cameFrom;
  }

  //============================================================================
  // This will calculate the heuristic for each path.
  //============================================================================
  private int heuristic(int x1, int y1, int x2, int y2)
  {
    return Math.abs(x1 - x2) + Math.abs(y1 - y2);
  }
}
