import javax.swing.*;
import model.*;
import view.*;

public class ZombieHouseMain
{

  public static void main(String[]args)
  {
    ZombieHouseModel zModel;
    ZombieHouseViewer zView;

  SwingUtilities.invokeLater(new Runnable()
  {

    public void run()
    {
     new ZombieHouseFrame().setVisible(true);
    }
  });
  }
}
