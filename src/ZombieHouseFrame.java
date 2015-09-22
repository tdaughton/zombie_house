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
   * Player orientation is calculated by (computer, as opposed to phone) Numpad position.
   */
  private void moveKeys()
  {
    double movement = 5;
    int dir = 5;

    if (keysPressed[KeyEvent.VK_R] || keysPressed[KeyEvent.VK_SHIFT]) movement = 2 * movement;
    if (keysPressed[KeyEvent.VK_UP] || keysPressed[KeyEvent.VK_W]) dir += 3;
    if (keysPressed[KeyEvent.VK_DOWN] || keysPressed[KeyEvent.VK_S]) dir -= 3;
    if (keysPressed[KeyEvent.VK_LEFT] || keysPressed[KeyEvent.VK_A]) dir -= 1;
    if (keysPressed[KeyEvent.VK_RIGHT] || keysPressed[KeyEvent.VK_D]) dir += 1;

    if (dir % 2 != 0) movement = movement / Math.sqrt(2);
    switch (dir)
    {
      case 7:
        zModel.movePlayer(-movement, -movement, GridOrientation.NORTHWEST);
        break;
      case 8:
        zModel.movePlayer(0, -movement, GridOrientation.NORTH);
        break;
      case 9:
        zModel.movePlayer(movement, -movement, GridOrientation.NORTHEAST);
        break;
      case 4:
        zModel.movePlayer(-movement, 0, GridOrientation.WEST);
        break;
      case 5:
      default:
        return;
      case 6:
        zModel.movePlayer(movement, 0, GridOrientation.EAST);
        break;
      case 1:
        zModel.movePlayer(-movement, movement, GridOrientation.SOUTHWEST);
        break;
      case 2:
        zModel.movePlayer(0, movement, GridOrientation.SOUTH);
        break;
      case 3:
        zModel.movePlayer(movement, movement, GridOrientation.SOUTHEAST);
        break;
    }

    if (keysPressed[KeyEvent.VK_R] || keysPressed[KeyEvent.VK_SHIFT]) stepCount += 2;
    else stepCount += 1;

    if (stepCount > 14)
    {
      this.gameSounds.leftFootStep();
      stepCount = 0;
    }
    if (stepCount % 15 == 7)
    {
      this.gameSounds.rightFootStep();
    }
  }
}
