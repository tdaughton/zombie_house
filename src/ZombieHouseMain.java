/**
 * Created by Tess, TC, and Miri
 * Jar entry point for Zombie House
 */

import javax.swing.*;

/**
 * This class is the entry point for ZombieHouse.
 */
public class ZombieHouseMain
{
  /**
   * Creates ZombieHouseFrame and runs it.
   * @param args  unused
   */
  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        new ZombieHouseFrame().setVisible(true);
      }
    });
  }
}
