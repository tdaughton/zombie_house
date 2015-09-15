
/**
 * Created by Tess Daughton, September 13th, 2015
 */
package view;

import model.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.Dimension;

public class ZombieHouseViewer
{
  public ZombieHouseViewer(Tile[][] grid, BufferedImage gameBoard, Dimension userScreenSize)
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      @Override
      public void run()
      {
        new GameFrame(grid,gameBoard, userScreenSize).setVisible(true);
      }
    });
  }
}
