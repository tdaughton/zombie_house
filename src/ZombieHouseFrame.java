/**
 * Created by Tess Daughton, Sunday September 13th, 2015
 * ZombieHouseFrame contains both the ZombieHouseModel and ZombieHouseViewer objects
 * and acts as an intermediary between the two.
 */

import Resources.SoundLoader;
import Resources.ImageLoader;
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
  private ZombieHouseMenu zMenu;
  private SoundLoader gameSounds;
  public static Timer timer;
  private boolean[] keysPressed;
  private long prevSeconds;
  private long currSeconds;
  private double deltaSeconds;
  private int stepCount;
  private int curScreenWidth;
  private int curScreenHeight;
  private boolean pause;
  private int pauseTracker = 2;

  /**
   * Constructor
   * Creates a ZombieHouseModel and a ZombieHouseViewer
   * Creates a JFrame with the maximum possible dimensions of the current user
   * Adds the ZombieHouseViewer panel into the frame
   * Initializes and starts a timer
   * Listens for keys pressed to dictate movement to model
   */
  public ZombieHouseFrame()
  {
    super("ZombieHouse");
    this.zModel = new ZombieHouseModel();
    this.zView  = new ZombieHouseViewer(zModel, userScreenSize);
    this.zMenu = new ZombieHouseMenu(zModel, (int) userScreenSize.getWidth());
    this.gameSounds = new SoundLoader();
    this.gameSounds.playBackgroundMusic();

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setPreferredSize(userScreenSize);
    this.setSize(userScreenSize);
    this.setBackground(Color.black);
    this.getContentPane().add(zMenu, BorderLayout.PAGE_END);
    this.getContentPane().add(zView, BorderLayout.CENTER);
    zView.requestFocusInWindow();
    this.setVisible(true);


    timer = new Timer(16, this);
    timer.setInitialDelay(1000);
    timer.start();
    this.currSeconds = System.nanoTime();

    this.keysPressed = new boolean[128];
    this.stepCount = 0;
    this.addKeyListener(this);

    this.addComponentListener(this);
  }


  @Override
  /**
   * actionPerformed updates currentScreen dimensions for resizing inside of ZombieHouseViewer
   * keeps track of time specifications
   * updates the model every 1/60 of a second
   * repaints the viewer when an update has been made inside of model
   **/
  public void actionPerformed(ActionEvent e)
  {
    this.requestFocus();
    this.playerPickUpTrap();
    this.zMenu.updateLabels();
    prevSeconds = currSeconds;
    currSeconds = System.nanoTime();
    deltaSeconds = (currSeconds - prevSeconds) / 1000000000.0f;
    zModel.update(deltaSeconds);
    if(pause && pauseTracker>0)
    {
      pauseTracker--;
    }
    if(pauseTracker==0)
    {
      pauseTracker = 2;
      pause = false;
    }
    repaint();
  }

  //TODO: ComponentListener to resize Viewer/Panel
  @Override
  public void componentResized(ComponentEvent e)
  {
    this.notifyScreenSizes();
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
      case KeyEvent.VK_P:
        keysPressed[keyCode] = true;
        moveKeys();
        break;
      default:
        break;
    }
  }

  /**
   * Called inside actionPerformed to periodically update the
   * currentScreenWidth and currentScreenHeight inside of the Viewer and Model
   */
  private void notifyScreenSizes()
  {
    curScreenWidth = zView.getWidth();
    curScreenHeight = zView.getHeight();
    this.zView.setCurrentScreenHeight(curScreenHeight);
    this.zView.setCurrentScreenWidth(curScreenWidth);
    this.zModel.setCurrentScreenHeight(curScreenHeight);
    this.zModel.setCurrentScreenWidth(curScreenWidth);
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
      case KeyEvent.VK_P:
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
    double movement = 1.0f;
    boolean boost = false;
    int dir = 5;
    if(pause) return;
    if (keysPressed[KeyEvent.VK_R] || keysPressed[KeyEvent.VK_SHIFT])
    {
      boost = true;      //movement = 2 * movement;
      zModel.setPlayerRunning(true);
    }
    if (keysPressed[KeyEvent.VK_UP] || keysPressed[KeyEvent.VK_W]) dir += 3;
    if (keysPressed[KeyEvent.VK_DOWN] || keysPressed[KeyEvent.VK_S]) dir -= 3;
    if (keysPressed[KeyEvent.VK_LEFT] || keysPressed[KeyEvent.VK_A]) dir -= 1;
    if (keysPressed[KeyEvent.VK_RIGHT] || keysPressed[KeyEvent.VK_D]) dir += 1;
    if (!boost) zModel.setPlayerRunning(false);

    if (dir % 2 != 0) movement = movement / Math.sqrt(2);
    switch (dir)
    {
      case 7:
        zModel.movePlayer(-movement, -movement, GridOrientation.NORTHWEST, boost);
        break;
      case 8:
        zModel.movePlayer(0, -movement, GridOrientation.NORTH, boost);
        break;
      case 9:
        zModel.movePlayer(movement, -movement, GridOrientation.NORTHEAST, boost);
        break;
      case 4:
        zModel.movePlayer(-movement, 0, GridOrientation.WEST, boost);
        break;
      case 5:
      default:
        return;
      case 6:
        zModel.movePlayer(movement, 0, GridOrientation.EAST, boost);
        break;
      case 1:
        zModel.movePlayer(-movement, movement, GridOrientation.SOUTHWEST, boost);
        break;
      case 2:
        zModel.movePlayer(0, movement, GridOrientation.SOUTH, boost);
        break;
      case 3:
        zModel.movePlayer(movement, movement, GridOrientation.SOUTHEAST, boost);
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

  private void playerPickUpTrap()
  {
    if(keysPressed[KeyEvent.VK_P])
    {
      zModel.attemptTrapAction();
      pause = true;
    }
  }
}
