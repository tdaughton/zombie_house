package model;

/**
 * Created by Tess Daughton, September 13th 2015
 * Takes in a tile map given by ZombieHouseModel
 */
public class Map extends ZombieHouseModel
{
  private Tile[][] grid;

  public Map(Tile[][] grid)
  {
    this.grid = grid;
  }
}
