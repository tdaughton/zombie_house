/**
 * Created by Tess Daughton, September 13th 2015
 * Miri, I was using this class to read in my test case for the map, you can use if you need to read/write something for the MapGenerator, otherwise I can delete.
 */

package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GridReader
{
  private int[][] grid;

  public int[][] readGrid()
  {
    grid = new int[40][40];
    int x = 0;
    int y = 0;
    int tileID;
    try
    {
      InputStreamReader gridReader = new InputStreamReader(this.getClass().getResourceAsStream("test_grid.txt"));
      BufferedReader br = new BufferedReader(gridReader);
      while ((tileID = br.read()) != -1)
      {
        //System.out.print((char)tileID);
        if (tileID == '\n')
        {
          y++;
          x = 0;
          //System.out.println();
        }
        else
        {
          grid[y][x] = tileID;
          x++;
        }
      }
      br.close();
      gridReader.close();
    }
    catch (IOException e)
    {
      //e.printStackTrace();
    }
    return grid;
  }
}
