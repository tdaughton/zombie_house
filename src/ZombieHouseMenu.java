import javax.swing.*;

import model.ZombieHouseModel;

import java.awt.*;
import java.awt.event.*;

/**
 * Created by Tess Daughton on 9/26/15.
 */
public class ZombieHouseMenu extends JPanel
{

  private ZombieHouseModel zModel;
  private JToggleButton pause = new JToggleButton("Pause");
  private JLabel traps;
  private JLabel sight_Capability;
  private JLabel hear_Capability;
  private JLabel stamina;



  public ZombieHouseMenu(ZombieHouseModel zModel, int screenWidth)
  {
    super();
    this.zModel=zModel;
    this.sight_Capability = new JLabel("Sight Radius: " + zModel.getPlayer().getPlayerSight() +" \t" );
    this.hear_Capability = new JLabel("Hearing Radius: " + zModel.getPlayer().getPlayerHearing() + " \t");
    this.stamina = new JLabel("\t Stamina: " + zModel.getPlayer().getPlayerStamina());
    this.traps = new JLabel(("Number of Traps: " + zModel.getPlayer().getNumberOfTraps()) +  "\t");
    this.sight_Capability.setFont(new Font("Lucida Console", Font.BOLD, 20));
    this.hear_Capability.setFont(new Font("Lucida Console", Font.BOLD, 20));
    this.stamina.setFont(new Font("Lucida Console", Font.BOLD, 20));
    this.traps.setFont(new Font("Lucida Console", Font.BOLD, 20));
    this.pause.setFont(new Font("Lucida Console", Font.BOLD, 20));



    this.add(sight_Capability);
    this.add(hear_Capability);
    this.add(traps);
    this.add(pause);
    this.add(stamina);

    this.requestFocus();
    this.setPreferredSize(new Dimension(screenWidth, 50));
    this.setSize(super.getWidth(), 50);
    this.setBackground(Color.darkGray);
    pause.addItemListener(new ItemListener()
    {
      public void itemStateChanged(ItemEvent e)
      {
        if (e.getStateChange() == ItemEvent.SELECTED)
        {
          ZombieHouseFrame.timer.stop();
          ZombieHouseModel.soundLoader.setPause(true);
          pause.setText("Start");
        }
        else
        {
          ZombieHouseFrame.timer.start();
          ZombieHouseModel.soundLoader.setPause(false);
          pause.setText("Pause");
        }
      }
    });
  }
  protected void updateLabels()
  {
    sight_Capability.setText("Sight Radius: " + zModel.getPlayer().getPlayerSight());
    hear_Capability.setText("Hearing Radius: " + zModel.getPlayer().getPlayerHearing());
    stamina.setText(("Stamina: " + zModel.getPlayer().getPlayerStamina()));
    traps.setText(("Number of Traps: " + zModel.getPlayer().getNumberOfTraps()));
  }
}
