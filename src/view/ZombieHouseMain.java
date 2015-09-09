package view;

import javax.swing.*;

/**
 * Created by L301126 on 9/8/15.
 */
public class ZombieHouseMain
{

  public static void main(String [] args)
  {
    int[][] gameBoard = new GridReader().readGrid();

    SwingUtilities.invokeLater(new Runnable()
    {
      @Override
      public void run()
      {
        new GameFrame(gameBoard).setVisible(true);
      }
    });
  }
}
