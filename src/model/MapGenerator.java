package model;

/**
 * Miri Ryu
 * CS351L-001
 * Proj1 Zombie House
 *
 * MapGenerator will generate and will have getMap() who returns 2D arrays of
 * tile map that has walls and movable tile and etc.
 * It will have 10 rooms and many hallways.
 */
public class MapGenerator
{
  private final int NUMBER_OF_ROOMS = 10, NUMBER_OF_HALLWAYS=4;
  public boolean[][] map;

  public int col, row;


  /**
   * It will generate new map. col must be at least 40 and row must be at least
   * 30 because I can't make sure it will always be able to generate 10 rooms in
   * smaller map. Smallest room will be 4x4; all rooms are rectangular shape.
   *
   * @param col
   * @param row
   */
  public MapGenerator(int col, int row)
  {
    map = new boolean[row][col];
    this.col = col;
    this.row = row;

    for(int i=0; i<NUMBER_OF_ROOMS; i++)
    {
      generateRandomRoom();
    }

    printMap(map);
    generateRandomHallways();
    printMap(map);

  }

  /**
   * Generating new map
   */
  private void generateRandomRoom()
  {
    int roomWidth = 0, roomHeight = 0, roomX, roomY;

    while(true)
    {
      roomX = 1 + (int) (col * Math.random());
      roomY = 1 + (int) (row * Math.random());
      roomWidth = (int) (4 + (col/4) * Math.random());
      roomHeight = (int) (4 + (row/4) * Math.random());

      if(isFit(roomX, roomY, roomWidth, roomHeight))
      {
        for(int i=0; i < roomWidth; i++)
        {
          for(int j=0; j < roomHeight; j++)
          {
            map[roomY+j][roomX+i] = true;
          }
        }
        break;
      }
    }
  }

  /**
   * Generating Hallways that completely fills the map.
   */
  private void generateRandomHallways()
  {
    int num = 0;
    while(isCapableOfMoreHallways())
    {
      num++;
    }

    System.out.println(num);
  }

  /**
   * Using A* pathfinding method it will find the fastest way to go through each
   * map.
   */
  private void connectHallways()
  {
  }

  /**
   * After every room gets at least one entrance it will erase unused hallways.
   */
  private void eraseUnusedPath()
  {
  }

  /**
   * It will check if new random map is fittable to the current map.
   *
   * @param x x coordinate of new room
   * @param y y coordinate of new room
   * @param width must be greater than 4, less than or equal to 1/4 of map width
   * @param height must be greater than 4, less than or equal to 1/4 of map hei-
   *               ght
   * @return true if it fits, false otherwise
   */
  private boolean isFit(int x, int y, int width, int height)
  {
    if(x + width + 1 >= col) return false;
    if(y + height + 1 >= row) return false;
    for(int i=0; i < width + 2; i++)
    {
      for(int j=0; j < height + 2; j++)
      {
        if(map[y+j-1][x+i-1]) return false;
      }
    }

    return true;
  }

  /**
   * This method is to check if there is any room for more hallways but it's not
   * working as I meant so don't use it yet..
   *
   * @return true if there is such a wall tile that its 8 neighbors are all wal-
   *         ls, false otherwise.
   */
  public boolean isCapableOfMoreHallways()
  {
    for(int i=1; i<col-1; i++)
    {
      for(int j=1; j<row-1; j++)
      {
        if(!map[j-1][i-1] && !map[j-1][i] && !map[j-1][i+1] &&  !map[j][i-1] &&
                !map[j][i+1] && !map[j+1][i-1] && !map[j+1][i] && !map[j+1][i+1])
        {
          map[j][i] = true;
          return true;
        }
      }
    }
    return false;
  }

  /**
   * print 2D boolean map to see how the map looks like.
   *
   * @param map
   */
  public void printMap(boolean[][] map)
  {
    int col = map[0].length;
    int row = map.length;

    String line = "";

    for(int i=0; i<row; i++)
    {
      line ="";
      for(int j=0; j<col; j++)
      {
        if(map[i][j]) line += "[ ]";
        else line += " * ";
      }
      System.out.println(line);
    }
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
