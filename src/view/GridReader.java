package view;

/**
 * Created by L301126 on 9/9/15.
 */

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
        if(tileID == '\n')
        { x++;
          y=0;
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

