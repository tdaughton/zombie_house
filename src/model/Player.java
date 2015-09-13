package model;

import view.HouseImage;

/**
 * Created by Miri on 9/8/15.
 */
public class Player extends Movable
{
  public Player(int x, int y, int radius, Tile location, Tile[][] grid, HouseImage house)
  {
    super(x, y, radius,location,grid,house);
  }
}
