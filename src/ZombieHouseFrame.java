/**
 * Created by Tess Daughton, Sunday September 13th, 2015
 * This class will start the game and utilize a thread manager to run it.
 */

import Resources.*;
import model.*;
import view.*;
import model.ZombieHouseModel;
import view.ZombieHouseViewer;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class ZombieHouseFrame extends JFrame implements ActionListener
{

  private final static Dimension userScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
  protected final static int MAX_SCREEN_WIDTH = (int)userScreenSize.getWidth();
  protected final static int MAX_SCREEN_HEIGHT = (int)userScreenSize.getHeight();
  private Timer timer;
  private ZombieHouseModel zModel = new ZombieHouseModel();
  private ZombieHouseViewer zView = new ZombieHouseViewer(zModel,userScreenSize);

  public ZombieHouseFrame()
  {
    super();

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setPreferredSize(userScreenSize);
    this.setSize(userScreenSize);
    this.setBackground(Color.black);
    this.getContentPane().add(zView, BorderLayout.CENTER);
    this.setVisible(true);

    this.timer = new Timer(16, this);
    this.timer.start();
  }

  //TODO: ComponentListener to resize Viewer/Panel
  //TODO: KeyEvent handling (either here or in Viewer/Panel

  @Override
  public void actionPerformed(ActionEvent e)
  {
    //TODO: deltaSeconds handling
    //zModel.update(deltaSeconds);
    repaint();
  }
}