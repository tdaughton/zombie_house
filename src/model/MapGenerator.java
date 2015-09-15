
/**
 * Miri Ryu
 * CS351L-001
 * Proj1 Zombie House
 *
 * MapGenerator will generate and will have getMap() who returns 2D arrays of
 * tile map that has walls and movable tile and etc.
 * It will have 10 rooms and many hallways.
 */
package model;

public class MapGenerator
{
  private final int NUMBER_OF_ROOMS = 10, NUMBER_OF_HALLWAYS=4;
  private TileModel[][] map;
  private Room[] rooms;

  private int col, row;

  /**
   *
   * 1. Generate random rooms
   * 2. Generate random hallways
   * 3. make doors
   * 4. Travel from each room to another, remove unused hallways
   *
   * @param col
   * @param row`
   */
  public MapGenerator(int col, int row)
  {
    map = new TileModel[row][col];
    rooms = new Room[NUMBER_OF_ROOMS];

    this.col = col;
    this.row = row;

    initiateHouse();
    generateRandomRoom();
    updateRoom();
    printMap();
    generateRandomHallway();
    printMap();

  }

  private void initiateHouse()
  {
    for(int i=0; i<row; i++)
    {
      for(int j=0; j<col; j++)
      {
        map[i][j] = new TileModel(j, i);
      }
    }
  }

  private void generateRandomRoom()
  {
    Room newRoom;
    int roomWidth, roomHeight, roomX, roomY;

    for(int r=0; r<NUMBER_OF_ROOMS; r++)
    {
      while(rooms[r] == null)
      {
        roomWidth = (int) (4 + (col/4) * Math.random());
        roomHeight = (int) (4 + (row/4) * Math.random());

        roomX = 1 + (int) ((col - roomWidth-1) * Math.random());
        roomY = 1 + (int) ((row - roomHeight-1) * Math.random());

        newRoom = new Room(roomX, roomY, roomWidth, roomHeight);

        if(isOk(newRoom))
        {
          rooms[r] = newRoom;
        }
      }
    }
  }

  private void generateRandomHallway()
  {
    TileModel start;
    while((start = getStartTile()) != null)
    {
      extendHallway(start);
    }
  }

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
          map[j][i].setType(1);
        }
      }
    }
  }

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

  public void extendHallway(TileModel current)
  {
    int xInc, yInc;
    int x = current.getX(), y = current.getY();

    xInc = (Math.random() < .5)? 1: -1;
    yInc = (Math.random() < .5)? 1: -1;

    map[y][x].setType(4);

    //System.out.println("map "+ x + "," + y + " = " + map[y][x].getType());

    if(x+xInc <= 0 || x+xInc >= col-1 || x-xInc <= 0 || x-xInc >= col-1) return;
    if(y+yInc <= 0 || y+yInc >= row-1 || y-yInc <= 0 || y-yInc >= row-1) return;

    if(isExtendable(x, y+yInc, 0, yInc)) extendHallway(map[y+yInc][x]);
    if(isExtendable(x+xInc, y, xInc, 0)) extendHallway(map[y][x+xInc]);
    if(isExtendable(x-xInc, y, -xInc, 0)) extendHallway(map[y][x-xInc]);
    if(isExtendable(x, y-yInc, 0, -yInc)) extendHallway(map[y-yInc][x]);

    return;
  }

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

  public boolean isWall(int x, int y)
  {
    return map[y][x].getType() == 0;
  }

  public boolean isAlone(int x, int y)
  {
    return isWall(x, y) && map[y-1][x-1].getType() == 0 &&
           map[y-1][x].getType() == 0 && map[y-1][x+1].getType() == 0 &&
           map[y][x-1].getType() == 0 && map[y][x+1].getType() == 0 &&
           map[y+1][x-1].getType() == 0 && map[y+1][x].getType() == 0 &&
           map[y+1][x+1].getType() == 0;
  }

  /**
   * Either xInc or yInc must be 0 but not both.
   *map[y+yInc][x].getType() == 0 &&
   * map[y][x+xInc].getType() == 0 &&
   * @param x
   * @param y
   * @param xInc
   * @param yInc
   * @return
   */
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

  public void printMap()
  {
    String ln ="";
    for(int i=0; i<row; i++)
    {
      ln = "";
      for(int j=0; j<col; j++)
      {
        switch (map[i][j].getType())
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
    MapGenerator mg = new MapGenerator(40, 30);
  }
}
