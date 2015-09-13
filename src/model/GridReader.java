
/**
 * Created by Tess Daughton, September 13th 2015
 * Miri, I was using this class to read in my test case for the map, you can use if you need to read/write something for the MapGenerator, otherwise I can delete.
 */

import view.HouseImage;

import java.io.*;

public class GridReader
{
  private int[][] grid = new int[HouseImage.GRID_WIDTH][HouseImage.GRID_HEIGHT];

  protected int[][] readGrid()
  {
    int x = 0;
    int y = 0;
    int tileID;
    try
    {

      File file = new File("src/view/resources/test_grid.txt");
      FileReader gridReader = new FileReader(file);
      BufferedReader br = new BufferedReader(gridReader);
      while ((tileID = br.read()) != -1)
      {
        //System.out.print((char)tileID);
        if(tileID == '\n')
        { x++;
          y=0;
         // System.out.println();

        }
        else
        {
          grid[x][y] = tileID;
          y++;
        }
      }
    } catch (IOException e)
    {
      e.printStackTrace();
    }
    return grid;
  }
}

