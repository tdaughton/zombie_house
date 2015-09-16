
//==============================================================================
// Miri Ryu
// CS351L-001
// Proj1 Zombie House
//
// MapGenerator class will create a 2D array int that indicates map. Each int on
// the map indicates a tile on the screen. Each tile represents different
// material such as floor tile, wall tile.
//
// #    Name     Passable
// 0    Wall     false
// 1    Floor    true
//
//==============================================================================
package model;

import java.util.Random;

public class MapGenerator
{
  private final int NUMBER_OF_ROOMS = 10, NUMBER_OF_HALLWAYS=4;
  private int[][] map;
  private Room[] rooms;
  Random random;

  private int col, row;


  //============================================================================
  // MapGenerating can be abstracted to following steps:
  //
  // 1. Take the dimension and created 2d array accordingly.
  // 2. Generate random room with different sizes. Each room must start and end
  //    at odd coordinates of of tile because that way hallways are going to
  //    look prettier.
  // 3. Generate random hallways. Hallways are going to change directions or end
  //    at odd number of coordinates only, too.
  // 4. Connect rooms and hallways. Each room can have multiple doors but must
  //    have at least one.
  // 5. locate exit on hallway.
  // 6. Erase unused hallways.
  //
  // @param col @param row represent dimension of the map.
  //============================================================================
  public MapGenerator(int col, int row)
  {
    map = new int[row][col];
    rooms = new Room[NUMBER_OF_ROOMS];
    random = new Random();

    this.col = col;
    this.row = row;

    initiateHouse();
    generateRandomRoom();
    //updateRoom();
    printMap();
    //generateRandomHallway();
    //printMap();

  }

  //============================================================================
  // Initiate House method will start TileModel Array but I am planning to
  // change it to int array soon.
  //============================================================================
  private void initiateHouse()
  {
    for(int i=0; i<row; i++)
    {
      for(int j=0; j<col; j++)
      {
        map[i][j] = 0;
      }
    }
  }

  //============================================================================
  // It will generate random room. It calls room class but I will also change it
  // to only use int array.
  //============================================================================
  private void generateRandomRoom()
  {
    Room newRoom;
    int roomWidth, roomHeight, roomX, roomY;

    for(int r=0; r<NUMBER_OF_ROOMS; r++)
    {
      while(rooms[r] == null)
      {
        roomWidth = (5 + random.nextInt(col/8) * 2);
        roomHeight = (5 + random.nextInt(row/8) * 2);

        roomX = 1 + random.nextInt(col - roomWidth - 2);
        roomY = 1 + random.nextInt(row - roomHeight - 2);

        System.out.println(roomWidth + ", " + roomHeight);

        newRoom = new Room(roomX, roomY, roomWidth, roomHeight);

        if(isOk(newRoom))
        {
          rooms[r] = newRoom;
        }
      }
    }
  }
/**
  //============================================================================
  // At each point found to be alone without any neighbor in adjacent 8 tiles
  // a hallway can be started. Every time such a tile is found call this
  // function and extend hallway in random direction and as long as it can go.
  //============================================================================
  private void generateRandomHallway()
  {
    TileModel start;
    while((start = getStartTile()) != null)
    {
      extendHallway(start);
    }
  }*/

  //============================================================================
  // Once I change all the tile classes into int array this method will not be
  // necessary but this is just to update the rooms on the actual map.
  //============================================================================
  public void updateRoom()
  {
    int x, y, x2, y2;

    for(int r=0; r<NUMBER_OF_ROOMS; r++)
    {
      x = rooms[r].getX();
      y = rooms[r].getY();
      x2 = x + rooms[r].getWidth();
      y2 = y + rooms[r].getHeight();

      for(int i=x; i<x2; i++)
      {
        for(int j=y; j<y2; j++)
        {
          map[j][i] = 1;
        }
      }
    }
  }

  //============================================================================
  // This is to find if the new rectangular room intersects with any other rooms
  // I couldn't think of better name for the method but I will probably come up
  // with something better and specific later on.
  //============================================================================
  public boolean isOk(Room room)
  {
    int i = 0;
    while(rooms[i] != null)
    {
      if(rooms[i].intersects(room)) return false;
      i++;
    }
    return true;
  }
/**
  //============================================================================
  // This is a method to extend hallway as far as it can go but each time the
  // direction is kind of randomized. If the hallway can't be extended anymore
  // it will just stop.
  //============================================================================
  public void extendHallway(TileModel current)
  {
    int xInc, yInc;
    int x = current.getX(), y = current.getY();

    xInc = (Math.random() < .5)? 1: -1;
    yInc = (Math.random() < .5)? 1: -1;

    map[y][x]=4;

    //System.out.println("map "+ x + "," + y + " = " + map[y][x].getType());

    if(x+xInc <= 0 || x+xInc >= col-1 || x-xInc <= 0 || x-xInc >= col-1) return;
    if(y+yInc <= 0 || y+yInc >= row-1 || y-yInc <= 0 || y-yInc >= row-1) return;

    if(isExtendable(x, y+yInc, 0, yInc)) extendHallway(y+yInc, x);
    if(isExtendable(x+xInc, y, xInc, 0)) extendHallway(y, x+xInc);
    if(isExtendable(x-xInc, y, -xInc, 0)) extendHallway(y, x-xInc);
    if(isExtendable(x, y-yInc, 0, -yInc)) extendHallway(y-yInc, x);

    return;
  }

  //============================================================================
  // This is returning a tile that stands alone but I will think of better way
  // to adapt this method for 2D int.
  //============================================================================
  public TileModel getStartTile()
  {
    for(int i=1; i<row-1; i++)
    {
      for(int j=1; j<col-1; j++)
      {
        if(isAlone(j, i)) return map[i][j];
      }
    }
    return null;
  }


  //============================================================================
  // Returns true if the given coordinates of the map is a map tile, false
  // otherwise.
  //============================================================================
  public boolean isWall(int x, int y)
  {
    return map[y][x].getType() == 0;
  }

  //============================================================================
  // Returns true all the adjacent tiles are walls and the tile itself is also
  // a wall.
  //============================================================================
  public boolean isAlone(int x, int y)
  {
    return isWall(x, y) && map[y-1][x-1].getType() == 0 &&
           map[y-1][x].getType() == 0 && map[y-1][x+1].getType() == 0 &&
           map[y][x-1].getType() == 0 && map[y][x+1].getType() == 0 &&
           map[y+1][x-1].getType() == 0 && map[y+1][x].getType() == 0 &&
           map[y+1][x+1].getType() == 0;
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
  //    @ [?] @  =>  Say that the tile wants to extend to north, then we want
  //    * [ ] *      @ marked tiles to be wall.
  //    *  *  *
  //
  // Since the cell will only move to 4 directions, either xInc(x increment) or
  // yInc(y increment) must be 0 but not both.
  //============================================================================
  public boolean isExtendable(int x, int y, int xInc, int yInc)
  {
    if(xInc == 0)
    {
      return isWall(x, y) && map[y+yInc][x-1].getType() == 0 &&
             map[y+yInc][x+1].getType() == 0 &&
             map[y][x-1].getType() == 0 && map[y][x+1].getType() == 0;
    }
    else
    {
      return isWall(x, y) && map[y -1][x+xInc].getType() == 0 &&
             map[y+1][x+xInc].getType() == 0 &&
             map[y-1][x].getType() == 0 && map[y+1][x].getType() == 0;
    }
  }
*/
  public void printMap()
  {
    String ln ="";
    for(int i=0; i<row; i++)
    {
      ln = "";
      for(int j=0; j<col; j++)
      {
        switch (map[i][j])
        {
          case 0: ln += " * ";
            break;
          default: ln += "[ ]";
            break;
        }
      }
      System.out.println(ln);
    }
    System.out.println();
  }

  /**
   * This is just to see if this class is working fine
   *
   * @param args
   */
  public static void main(String[] args)
  {
    MapGenerator mg = new MapGenerator(41, 31);
  }
}
