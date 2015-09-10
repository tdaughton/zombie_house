package model;

/**
 * Created by Miri on 9/8/15.
 */
public class MapGenerator
{
  private final int NUMBER_OF_ROOMS = 10, NUMBER_OF_HALLWAYS=4;
  public boolean[][] map;

  public int col, row;

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

  private void generateRandomHallways()
  {
    int num = 0;
    while(isCapableOfMoreHallways())
    {
      num++;
    }

    System.out.println(num);
  }

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

  public static void main(String[] args)
  {
    MapGenerator mg = new MapGenerator(40, 30);
  }

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
}
