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
  private ZombieHouseModel zModel;
  private Tile[][] grid;
  private int cost = 0;

  //============================================================================
  // This will need to see the map. The map needs to have tile with different
  // cost.
  //============================================================================
  public Pathfinder(ZombieHouseModel zhModel)
  {
    zModel = zhModel;
    grid = zModel.getGrid();
  }

  private ArrayList<Tile> getNeighbors(Tile tile)
  {
    ArrayList<Tile> neighbors = new ArrayList<>();
    int gCol = tile.getGridCol();
    int gRow = tile.getGridRow();

    for (int i = Math.max(0, gRow-1); i < Math.min(ZombieHouseModel.ROWS, gRow+2); i++)
    {
      for (int j = Math.max(0, gCol-1); j < Math.min(ZombieHouseModel.COLS, gCol+2); j++)
      {
        if (i != gRow || j != gCol)
        {
          Tile neighbor = grid[i][j];
          if (neighbor.isMovable())
          {
            neighbors.add(neighbor);
          }
        }
      }
    }

    return neighbors;
  }

  //============================================================================
  // This is the a star search algorithm.
  //============================================================================
  public ArrayList<Tile> aStarSearch(Tile start, Tile target)
  {
    HashMap<Tile, Tile> cameFrom = new HashMap<>();
    PriorityQueue<Tile> frontier = new PriorityQueue<>();
    ArrayList<Tile> neighbors;

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

      neighbors = this.getNeighbors(current);
      for(Tile next : neighbors)
      {// If there are tiles with different cost, add next.getCost() instead of 1.
        int newCost = costSoFar.get(current) + 1;

        if(!costSoFar.containsKey(next) || newCost < costSoFar.get(next))
        {
          costSoFar.put(next, newCost);

          double heuristic = heuristic(target.getX(), target.getY(), next.getX(), next.getY());
          double priority = newCost + heuristic;

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
  private double heuristic(double x1, double y1, double x2, double y2)
  {
    return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
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
      path.add(0,current);
      current = next;
    }

    return path;
  }

  //============================================================================
  // Convert cameFrom into Stack of Tile because it is easier to control.
  //============================================================================
  private Stack<Tile> convertIntoStack(HashMap<Tile, Tile> cameFrom, Tile target)
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
