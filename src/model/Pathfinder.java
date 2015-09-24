package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;


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
  public ArrayList<Tile> aStarSearch(Tile start, Tile target)
  {
    HashMap<Tile, Tile> cameFrom = new HashMap<>();
    PriorityQueue<Tile> frontier = new PriorityQueue<>();
    ArrayList<Tile> path = new ArrayList<>();

    Tile current;
    costSoFar = new HashMap<>();

    frontier.add(start);
    start.setPriority(0);
    cameFrom.put(start, null);
    costSoFar.put(start, 0);

    while(!frontier.isEmpty())
    {
      current = frontier.poll();

      if(current == target) break;

      for(Tile next :map.getNeighbors((int)current.getX(), (int)current.getY()))
      {// If there are tiles with different cost, add next.getCost() instead of 1.
        int newCost = costSoFar.get(current) + 1;

        if(!costSoFar.containsKey(next) || newCost < costSoFar.get(next))
        {
          //TODO: It should be able to find out when there is no way to get to the target.
          costSoFar.put(next, newCost);

          int heuristic = heuristic((int) target.getX(), (int) target.getY(),
                                    (int) next.getX(), (int) next.getY());
          int priority = newCost + heuristic;

          next.setPriority(priority);
          frontier.add(next);
          cameFrom.put(next, current);
        }
      }
    }
    //System.out.println(costSoFar.get(target));

    //return convertIntoStack(cameFrom, target);
    return convertIntoArrayList(cameFrom, target);
  }

  //============================================================================
  // This will calculate the heuristic for each path.
  //============================================================================
  private int heuristic(int x1, int y1, int x2, int y2)
  {
    return Math.abs(x1 - x2) + Math.abs(y1 - y2);
  }

  //============================================================================
  // Convert cameFrom into ArrayList of Tile because it is easier to iterate for
  // zombie classes.
  //============================================================================
  private ArrayList<Tile> convertIntoArrayList(HashMap<Tile, Tile> cameFrom,
                                               Tile target)
  {
    ArrayList<Tile> path = new ArrayList<>();
    Tile current, next;
    current = target;

    while(cameFrom.get(current) != null)
    {
      // Iterate through the HashMap until it gets to the start point.
      // But the start point is not going to be included in the path.
      next = cameFrom.get(current);
      path.add(current);
      current = next;
    }

    return path;
  }

  //============================================================================
  // Convert cameFrom into Stack of Tile because it is easier to control.
  //============================================================================
  private Stack<Tile> convertIntoStack(HashMap<Tile, Tile> cameFrom,
                                       Tile target)
  {
    Stack<Tile> path = new Stack<>();
    Tile current, next;
    current = target;

    while(cameFrom.get(current) != null)
    {
      // Iterate through the HashMap until it gets to the start point.
      // But the start point is not going to be included in the path.
      next = cameFrom.get(current);
      path.push(current);
      current = next;
    }

    return path;
  }
}
