/**
 * Created by Tess, TC, and Miri
 * Jar entry point for Zombie House
 */

import javax.swing.*;

public class ZombieHouseMain
{
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
