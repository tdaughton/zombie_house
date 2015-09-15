
/**
 * Created by Tess Daughton, September 13th 2015
 * Miri, I was using this class to read in my test case for the map, you can use if you need to read/write something for the MapGenerator, otherwise I can delete.
 */

package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GridReader
{
  private int[][] grid = new int[40][40];

  public int[][] readGrid()
  {
    int x = 0;
    int y = 0;
    int tileID;
    try
    {

      File file = new File("src/model/test_grid.txt");
      FileReader gridReader = new FileReader(file);
      BufferedReader br = new BufferedReader(gridReader);
      while ( (tileID = br.read()) != -1)
      {

        //System.out.print((char)tileID);
        if(tileID == '\n')
        { x++;
          y=0;
         //System.out.println();

        }
        else
        {
          grid[x][y] = tileID;
          y++;
        }
      }
      br.close();
      gridReader.close();
    } catch (IOException e)
    {
      //e.printStackTrace();
    }
    return grid;
  }
}