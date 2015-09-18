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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MapGenerator
{
  // If the number of rooms are too big compared to the dimension of the map
  // the tempRooms won't fit into the map so try not to use such a number.
  private final int NUMBER_OF_ROOMS = 10;

  private int[][] map;
  private Room[] rooms;

  private ArrayList<Room> tempRooms;

  private Random random;

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
  // 4. Connect tempRooms and hallways. Each room can have multiple doors but
  //    must have at least one.
  // 5. locate exit on hallway.
  // 6. Erase unused hallways.
  //============================================================================
  public MapGenerator(int col, int row)
  {
    random = new Random();

    map = new int[row][col];
    rooms = new Room[NUMBER_OF_ROOMS];

    tempRooms = new ArrayList<>();

    this.col = col;
    this.row = row;

    initiateHouse();
    generateRandomRoom();
    generateRandomHallway();
    generateDoorways();
    //printMap();
    removeUnusedHallways();
    //printMap();
    translateIntoDisplayableMap();
    //printMap();
  }

  private void translateIntoDisplayableMap()
  {
    for(int i=0; i<row; i++)
    {
      for(int j=0; j<col; j++)
      {
        if(map[i][j] != 0) map[i][j] = 1;
      }
    }
  }


  //============================================================================
  // Search for end of the hallway and start removing.
  //============================================================================
  private void removeUnusedHallways()
  {
    countEndofHallways();
  }

  //============================================================================
  // Search for end of the hallway and start removing.
  //============================================================================
  private int countEndofHallways()
  {
    int countEndOfHallway = 0;
    for(int i=0; i<row; i++)
    {
      for(int j=0; j<col; j++)
      {
        if(map[i][j] == 4)
        {
          removeHallways(j, i);
        }
      }
    }
    return countEndOfHallway;
  }

  private void removeHallways(int x, int y)
  {
    map[y][x] = 0;
    if(x-1 >= 0 && map[y][x-1] == 2) removeHallways(x-1, y);
    else if(x+1 < col-1 && map[y][x+1] == 2) removeHallways(x+1, y);
    else if(y-1 >= 0 && map[y-1][x] == 2) removeHallways(x, y-1);
    else if(y+1 < row-1 && map[y+1][x] == 2) removeHallways(x, y+1);

    return;
  }

  //============================================================================
  // This method will build doors.
  //============================================================================
  private void generateDoorways()
  {
    for(int i=0; i<NUMBER_OF_ROOMS; i++) buildDoorFor(rooms[i]);
  }

  //============================================================================
  // Each room will have at least one door to exit the room. Each side of the
  // room hold only one door.
  //============================================================================
  private void buildDoorFor(Room room)
  {
    ArrayList<Character> availableSides = getAvailableSides(room);

    int numDoors = 1 + random.nextInt(availableSides.size()-1);
    int x, y;

    Collections.shuffle(availableSides);

    for(int i = 0; i < numDoors ; i++) drill(room, availableSides.get(i));
    availableSides.clear();
  }

  private void drill(Room room, char direction)
  {
    int x, y, xInc = 0, yInc = 0;
    switch(direction)
    {
      case 'N' :
        x = 1 + room.x1 + random.nextInt(room.width-2);
        y = room.y1;
        yInc = -1;
        break;
      case 'E' :
        y = 1 + room.y1 + random.nextInt(room.height-2);
        x = room.x2-1;
        xInc = 1;
        break;
      case 'W' :
        y = 1 + room.y1 + random.nextInt(room.height-2);
        x = room.x1;
        xInc = -1;
        break;
      case 'S' :
        x = 1 + room.x1 + random.nextInt(room.width-2);
        y = room.y2-1;
        yInc = 1;
        break;
      default:
        return;
    }

    map[y][x] = 8;
    map[y+yInc][x+xInc] = 8;
    map[y+yInc*2][x+xInc*2] = 8;
  }

  //============================================================================
  // This will get how many sides can have access to the hallway.
  //============================================================================
  private ArrayList<Character> getAvailableSides(Room room)
  {
    ArrayList<Character> availableSides = new ArrayList<>();
    if(room.x1 > 2) availableSides.add('W');
    if(room.x2 < col - 3) availableSides.add('E');
    if(room.y1 > 1) availableSides.add('N');
    if(room.y2 < row - 3) availableSides.add('S');
    return availableSides;
  }

  //============================================================================
  // Initiate House method will start TileModel Array but I am planning to
  // change it to int array soon.
  //============================================================================
  private void initiateHouse()
  {
    for (int i = 0; i < row; i++)
    {
      for (int j = 0; j < col; j++)
      {
        map[i][j] = 0;
      }
    }
  }

  //============================================================================
  // Instead of picking random numbers for a rectangle and hoping for the
  // rectangle to fit into the map eventually, this method will call a
  // recursive method getQuadSect which divide given Room class into 4 smaller
  // tempRooms. The recursion will stop when the room gets too small or when the
  // iteration seems to be happened enough to generate sufficient number of
  // tempRooms.
  //============================================================================
  private void generateRandomRoom()
  {
    while (true)
    { // generate smaller squares for border
      if (getQuadSect(new Room(0, 0, col - 2, row - 2), 0) > NUMBER_OF_ROOMS) break;
      tempRooms.clear();
    }

    while (tempRooms.size() > NUMBER_OF_ROOMS)
    { // Pick random tempRooms.
      tempRooms.remove(random.nextInt(tempRooms.size()));
    }

    for (int i = 0; i < NUMBER_OF_ROOMS; i++) addNewRom(tempRooms.get(i), i);
  }

  //============================================================================
  // At each point found to be alone (a wall tile that has all 8 adjacent tiles
  // are also walls) start a hallway and extend it as far as it can go.
  //============================================================================
  private void generateRandomHallway()
  {
    int x, y;

    for (int i = 0; i < row / 2 - 1; i++)
    {
      for (int j = 0; j < col / 2 - 1; j++)
      {
        x = 1 + (j * 2);
        y = 1 + (i * 2);

        if (isAlone(x, y))
        {
          map[y][x] = 4;
          extendHallway(x, y);
        }
      }
    }
  }

  //============================================================================
  // This is a method to extend hallway as far as it can go but each time the
  // direction is kind of randomized. If the hallway can't be extended anymore
  // it will just stop.
  //============================================================================
  public void extendHallway(int x, int y)
  {
    int xInc = (random.nextInt(1) > 0) ? 1 : -1;
    int yInc = (random.nextInt(1) > 0) ? 1 : -1;

    if (isExtendable(x, y, 0, yInc))
    {
      map[y + yInc][x] = 2;
      map[y + yInc * 2][x] = 2;

      extendHallway(x, y + yInc * 2);
    }
    else if (isExtendable(x, y, xInc, 0))
    {
      map[y][x + xInc] = 2;
      map[y][x + xInc * 2] = 2;

      extendHallway(x + xInc * 2, y);
    }
    else if (isExtendable(x, y, -xInc, 0))
    {
      map[y][x - xInc] = 2;
      map[y][x - xInc * 2] = 2;

      extendHallway(x - xInc * 2, y);
    }
    else if (isExtendable(x, y, 0, -yInc))
    {
      map[y - yInc][x] = 2;
      map[y - yInc * 2][x] = 2;

      extendHallway(x, y - yInc * 2);
    }
    else map[y][x] = 4;
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
  public boolean isExtendable(int x, int y, int xInc, int yInc)
  {
    int newX = x + xInc * 3;
    int newY = y + yInc * 3;

    if (newX < 0 || newX >= col || newY < 0 || newY >= row) return false;

    for (int i = -1; i < 2; i++)
    {
      for (int j = -1; j < 2; j++)
      {
        if (map[y + i + yInc * 2][x + j + xInc * 2] > 0) return false;
      }
    }

    return true;
  }

  //============================================================================
  // Divide given rectangle into 4 rectangles. Check how deep the iteration
  // went through and if it's enough to generate given number of tempRooms then
  // stops.
  //============================================================================
  private int getQuadSect(Room room, int it)
  {
    Room[] sects = new Room[4];
    int xAxis, yAxis;

    if (room.width < 5 || room.height < 5) return 0; // 5x5 is too small.

    if (room.width < 8 || room.height < 8 ||
            Math.pow(4.0, (double)it) > NUMBER_OF_ROOMS)
    { // If the dimension of room gets too small it can't be divided further.
      tempRooms.add(room);
      return 1;
    }

    xAxis = room.x1 + 5 + random.nextInt(room.width / 2 - 3) * 2;
    yAxis = room.y1 + 5 + random.nextInt(room.height / 2 - 3) * 2;

    sects[0] = new Room(room.x1, room.y1, xAxis, yAxis);
    sects[1] = new Room(room.x1, yAxis + 1, xAxis, room.y2);
    sects[2] = new Room(xAxis + 1, room.y1, room.x2, yAxis);
    sects[3] = new Room(xAxis + 1, yAxis + 1, room.x2, room.y2);

    return getQuadSect(sects[0], it + 1) + getQuadSect(sects[1], it + 1) +
           getQuadSect(sects[2], it + 1) + getQuadSect(sects[3], it + 1);
  }

  //============================================================================
  // This method will add the new room into the map.
  //============================================================================
  private void addNewRom(Room room, int roomNum)
  {
    int x, y, width, height;
    int rX, rY, rWidth, rHeight;

    rX = room.x1 + 1;
    rY = room.y1 + 1;

    rWidth = room.width;
    rHeight = room.height;

    width = rWidth > 6 ? 5 + random.nextInt((rWidth - 5) / 2) * 2 : 5;
    height = rHeight > 6 ? 5 + random.nextInt((rHeight - 5) / 2) * 2 : 5;

    x = rWidth > width + 1 ? rX + random.nextInt((rWidth - width) / 2) * 2 : rX;
    y = rHeight > height + 1 ? rY + random.nextInt((rHeight - height) / 2) * 2 : rY;

    rooms[roomNum] = new Room(x, y, x + width, y + height);

    for (int i = y; i < y + height; i++)
    {
      for (int j = x; j < x + width; j++)
      {
        map[i][j] = 1;
      }
    }
  }

  //============================================================================
  // Returns true all the adjacent tiles are walls and the tile itself is also
  // a wall.
  //============================================================================
  public boolean isAlone(int x, int y)
  {
    return map[y][x] == 0 && map[y - 1][x - 1] == 0 && map[y - 1][x] == 0 &&
           map[y - 1][x + 1] == 0 && map[y][x - 1] == 0 && map[y][x] == 0 &&
           map[y][x + 1] == 0 && map[y + 1][x - 1] == 0 && map[y + 1][x] == 0 &&
           map[y + 1][x + 1] == 0;
  }

  public void printMap()
  {
    String ln = "";
    for (int i = 0; i < row; i++)
    {
      ln = "";
      for (int j = 0; j < col; j++)
      {
        switch (map[i][j])
        {
          case 0:
            ln += " * ";
            break;
          case 1:
            ln += "[1]";
            break;
          case 4:
            ln += "[4]";
            break;
          case 8:
            ln += "[&]";
            break;
          default:
            ln += "[ ]";
            break;
        }
      }
      System.out.println(ln);
    }
    System.out.println();
  }

  //============================================================================
  // Simple class to store information for rectangle. I thought it will be
  // lighter using my own class rather than using rectangle.
  //============================================================================
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
      System.out.println("(" + x1 + ", " + y1 + ")->(" + x2 + ", " + y2 +
              "): width = " + width + ", height = " + height);
    }
  }

  /**
   * This is just to see if this class is working fine
   * @param args do nothing
   */
  public static void main(String[] args)
  {
    MapGenerator mg = new MapGenerator(48, 41);
  }
}
