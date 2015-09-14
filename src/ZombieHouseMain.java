import model.GridReader;
import view.GameFrame;

import javax.swing.SwingUtilities;

/**
 * Created by Tess Daughton, Sunday September 13th, 2015
 * This class will start the game and utilize a thread manager to run it.
 */
public class ZombieHouseMain
{
  public static void main(String [] args)
  {
    int [][] gameBoard = new GridReader().readGrid();

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
