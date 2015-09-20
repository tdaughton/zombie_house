/**
 * Created by Tess Daughton, Sunday September 13th, 2015
 * This class will start the game and utilize a thread manager to run it.
 */

import Resources.SoundLoader;
import model.GridOrientation;
import model.ZombieHouseModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ZombieHouseFrame extends JFrame implements ActionListener, ComponentListener, KeyListener
{
  private final static Dimension userScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
  private ZombieHouseModel zModel;
  private ZombieHouseViewer zView;
  private SoundLoader gameSounds;
  private Timer timer;
  private boolean[] keysPressed;
  private long prevSeconds;
  private long currSeconds;
  private double deltaSeconds;
  private int stepCount;

  public ZombieHouseFrame()
  {
    super();
    this.zModel = new ZombieHouseModel();
    this.zView  = new ZombieHouseViewer(zModel, userScreenSize);
    this.gameSounds = new SoundLoader();
    this.gameSounds.playBackgroundMusic();

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setPreferredSize(userScreenSize);
    this.setSize(userScreenSize);
    this.setBackground(Color.black);
    this.getContentPane().add(zView, BorderLayout.CENTER);
    this.setVisible(true);

    this.timer = new Timer(16, this);
    this.timer.start();
    this.currSeconds = System.nanoTime();

    this.keysPressed = new boolean[128];
    this.stepCount = 0;
    this.addKeyListener(this);

    this.addComponentListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    prevSeconds = currSeconds;
    currSeconds = System.nanoTime();
    deltaSeconds = (currSeconds - prevSeconds) / 1000000000;
    zModel.update(deltaSeconds);
    repaint();
  }

  //TODO: ComponentListener to resize Viewer/Panel
  @Override
  public void componentResized(ComponentEvent e)
  {
    //System.out.println(zView.getWidth() + " " + zView.getHeight());
  }

  @Override
  public void componentMoved(ComponentEvent e)
  {
    //do nothing
  }

  @Override
  public void componentHidden(ComponentEvent e)
  {
    //do nothing
  }

  @Override
  public void componentShown(ComponentEvent e)
  {
    //do nothing
  }

  /**
   * When a valid key is pressed, set the keyCode's index in keysPressed to true
   * then call moveKeys to do appropriate actions.
   * @param e  KeyEvent required by interface
   */
  @Override
  public void keyPressed(KeyEvent e)
  {
    int keyCode = e.getKeyCode();

    switch (keyCode)
    {
      case KeyEvent.VK_UP:
      case KeyEvent.VK_DOWN:
      case KeyEvent.VK_LEFT:
      case KeyEvent.VK_RIGHT:
      case KeyEvent.VK_W:
      case KeyEvent.VK_A:
      case KeyEvent.VK_S:
      case KeyEvent.VK_D:
      case KeyEvent.VK_R:
      case KeyEvent.VK_SHIFT:
      case KeyEvent.VK_T:
        keysPressed[keyCode] = true;
        moveKeys();
        break;
      default:
        break;
    }
  }

  /**
   * When a valid key is released, set the keyCode's index in keysPressed to false
   * then call moveKeys to do appropriate actions (for other keys)
   * @param e  KeyEvent required by interface
   */
  @Override
  public void keyReleased(KeyEvent e)
  {
    int keyCode = e.getKeyCode();

    switch (keyCode)
    {
      case KeyEvent.VK_UP:
      case KeyEvent.VK_DOWN:
      case KeyEvent.VK_LEFT:
      case KeyEvent.VK_RIGHT:
      case KeyEvent.VK_W:
      case KeyEvent.VK_A:
      case KeyEvent.VK_S:
      case KeyEvent.VK_D:
      case KeyEvent.VK_R:
      case KeyEvent.VK_SHIFT:
      case KeyEvent.VK_T:
        keysPressed[keyCode] = false;
        moveKeys();
        break;
      default:
        break;
    }
  }

  @Override
  public void keyTyped(KeyEvent e)
  {
    //do nothing
  }

  /**
   * This method processes currently pressed keys to signal the Player to move. A step counter is maintained to play footstep sounds.
   */
  private void moveKeys()
  {
    int movement = 5;
    if (keysPressed[KeyEvent.VK_R] || keysPressed[KeyEvent.VK_SHIFT]) movement = 3 * movement;
    if (keysPressed[KeyEvent.VK_UP] || keysPressed[KeyEvent.VK_W]) zModel.movePlayer(0 ,-movement, GridOrientation.NORTH);
    if (keysPressed[KeyEvent.VK_DOWN] || keysPressed[KeyEvent.VK_S]) zModel.movePlayer(0, movement, GridOrientation.SOUTH);
    if (keysPressed[KeyEvent.VK_LEFT] || keysPressed[KeyEvent.VK_A]) zModel.movePlayer(-movement, 0, GridOrientation.WEST);
    if (keysPressed[KeyEvent.VK_RIGHT] || keysPressed[KeyEvent.VK_D]) zModel.movePlayer(movement, 0, GridOrientation.EAST);

    if (stepCount % 16 == 0)
    {
      this.gameSounds.leftFootStep();
      stepCount = 0;
    }
    if (stepCount % 16 == 8)
    {
      this.gameSounds.rightFootStep();
    }
    stepCount++;
  }
}
