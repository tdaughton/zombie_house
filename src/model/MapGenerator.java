
//==============================================================================
// Miri Ryu
// CS351L-001
// Proj1 Zombie House
//
// MapGenerator class will create a 2D array int that indicates map. Each int on
// the map indicates a tile on the screen. Each tile represents different
// material such as floor tile, wall tile.
//
// #    Name            Passable
// 0    Wall            false
// 1    Room            true
// 2    Hallways        true
// 4    End of Hallway  true
// 8    Doorway         true
// 16   Exit            true
//
// However, when the map generation is finished, they are going to be changed
// as follows:
//
// 0(Wall) ------------------> 0(Wall)
// 1(Room) ------------------> 1(Floor)
// 2(Hallways) --------------> 1(Floor)
// 4(End of Hallway) --------> 1(Floor)
// 8(Doorway) ---------------> 1(Floor)
// 16(Exit) -----------------> 2(Exit)
//
// The reason I change them into new notation is because once the map is
// generated the original notations are unnecessary.
//==============================================================================
package model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class MapGenerator
{
  private final int NUMBER_OF_ROOMS = 10;
  private int[][] map;
  private ArrayList<Rectangle> rooms;
  private ArrayList<Room> rooms2;
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
    rooms = new ArrayList<>();
    random = new Random();

    this.col = col;
    this.row = row;

    initiateHouse();
    while(true)
    {
      if(getQuadSect(new Room(0, 0, col, row), 0) > NUMBER_OF_ROOMS) break;
      System.out.println();
    }
    //generateRandomRoom();
    //updateRoom();
    //printMap();
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
  // Instead of picking random numbers for a rectangle and hoping for the
  // rectangle to fit into the map eventually, this method will call a
  // recursive method getQuadSect which divide given Room class into 4 smaller
  // rooms. The recursion will stop when the room gets too small or when the
  // iteration seems to be happened enough to generate sufficient number of
  // rooms.
  //============================================================================
  private void generateRandomRoom()
  {
    Rectangle newRoom;
    int roomWidth, roomHeight, roomX, roomY;

    for(int r=0; r<NUMBER_OF_ROOMS; r++)
    {
      while(true)
      {
        roomWidth = (5 + random.nextInt(col/12) * 2);
        roomHeight = (5 + random.nextInt(row/12) * 2);

        roomX = 1 + random.nextInt((col-roomWidth-2)/2) * 2;
        roomY = 1 + random.nextInt((row-roomHeight-2)/2) * 2;

        newRoom = new Rectangle(roomX, roomY, roomWidth, roomHeight);

        if(!intersectsWithAny(newRoom))
        {
          addNewRom(newRoom);
          //printMap();
          break;
        }
      }
    }
  }

  //============================================================================
  // Divide given rectangle into 4 rectangles. Check how deep the iteration
  // went through and if it's enough to generate given number of rooms then
  // stops.
  //============================================================================
  private int getQuadSect(Room room, int it)
  {
    Room[] sects = new Room[4];
    int xAxis, yAxis;

    // If the room dimension is smaller than 5x5, it's too small.
    if(room.width < 5 || room.height < 5) return 0;

    // If the room dimension is smaller than 8x8, it can't be divided further.
    if(room.width < 8 || room.height < 8)
    {
      room.print();
      return 1;
    }

    // If it iterated about the enough time, it should stop because I don't want
    // the room to be too small.
    if(Math.pow(4.0, (double) it) > NUMBER_OF_ROOMS)
    {
      room.print();
      return 1;
    }

    xAxis = room.x1 + 5 + random.nextInt(room.width/2 - 3) * 2;
    yAxis = room.y1 + 5 + random.nextInt(room.height/2 - 3) * 2;

    sects[0] = new Room(room.x1, room.y1, xAxis, yAxis);
    sects[1] = new Room(room.x1, yAxis, xAxis, room.y2);
    sects[2] = new Room(xAxis, room.y1, room.x2, yAxis);
    sects[3] = new Room(xAxis, yAxis, room.x2, room.y2);

    return getQuadSect(sects[0], it+1) + getQuadSect(sects[1], it+1) +
           getQuadSect(sects[2], it+1) + getQuadSect(sects[3], it+1);
  }

  //============================================================================
  // This is to find if the new room intersects with any other rooms
  //============================================================================
  private boolean intersectsWithAny(Rectangle other)
  {
    for(Rectangle current : rooms)
    {
      if(other.intersects(other)) return true;
    }
    return false;
  }

  //============================================================================
  // This method will add the new room into the map.
  //============================================================================
  private void addNewRom(Rectangle newRoom)
  {
    rooms.add(newRoom);

    for(int i=newRoom.y; i<newRoom.y+newRoom.height; i++)
    {
      for(int j=newRoom.x; j<newRoom.x+newRoom.width; j++)
      {
        map[i][j] = 1;
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

  public class Room
  {
    public int x1, x2, y1, y2, width, height;

    public Room(int x1, int y1, int x2, int y2)
    {
      this.x1 = x1;
      this.y1 = y1;
      this.x2 = x2;
      this.y2 = y2;
      width = Math.abs(x2 - x1);
      height = Math.abs(y2 - y1);
    }

    public void print()
    {
      System.out.println("(" + x1 + ", " + y1 + ")->(" + x2 + ", " + y2 + "): width = "
        + width + ", height = " + height);
    }
  }

  /**
   * This is just to see if this class is working fine
   *
   * @param args
   */
  public static void main(String[] args)
  {
    MapGenerator mg = new MapGenerator(48, 41);
  }
}
