/**
 * Created by Tess Daughton, Sunday September 13th, 2015
 * ZombieHouseFrame contains both the ZombieHouseModel and ZombieHouseViewer objects
 * and acts as an intermediary between the two.
 */

import Resources.SoundLoader;
import model.GridOrientation;
import model.Player;
import model.ZombieHouseModel;
import model.Zombie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class is the parent JComponent for the Zombie House game. It holds the major Controller elements
 * including timer, key listener, and resize listener.
 */
public class ZombieHouseFrame extends JFrame implements ActionListener, ComponentListener, KeyListener
{
  private final static Dimension USER_SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
  public final static Random RAND = new Random();
  public final static SoundLoader GAME_SOUNDS = new SoundLoader(RAND);

  public static Timer timer;
  private ZombieHouseModel zModel;
  private ZombieHouseViewer zView;
  private ZombieHouseMenu zMenu;
  private boolean[] keysPressed;
  private long prevSeconds;
  private long currSeconds;
  private double deltaSeconds;
  private int stepCount;
  private int curScreenWidth;
  private int curScreenHeight;
  private boolean pause;
  private int pauseTracker = 2;
  private double inputWait;

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
    this.zModel = new ZombieHouseModel(GAME_SOUNDS);
    this.zView  = new ZombieHouseViewer(this.zModel, USER_SCREEN_SIZE);
    this.zMenu = new ZombieHouseMenu(this.zModel, (int) USER_SCREEN_SIZE.getWidth());
    GAME_SOUNDS.playBackgroundMusic();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setPreferredSize(USER_SCREEN_SIZE);
    this.setSize(USER_SCREEN_SIZE);
    this.setBackground(Color.BLACK);
    this.add(this.zMenu, BorderLayout.PAGE_END);
    this.add(this.zView, BorderLayout.CENTER);
    this.zView.requestFocusInWindow();
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
   * @param e  ActionEvent required by interface
   */
  public void actionPerformed(ActionEvent e)
  {
    this.requestFocus();

    if(zModel.getPlayer().isDead() || zModel.getPlayer().isLevelUp())
    {
      //System.out.println(zModel.getPlayer().isLevelUp());
      this.restartLevel(this.zModel.getPlayer().isLevelUp());
      return;
    }
    if(this.pause && this.pauseTracker > 0)
    {
      this.pauseTracker--;
    }
    else
    {
      this.pauseTracker = 2;
      this.pause = false;
    }
    this.playRandomZombieSounds();
    this.zMenu.updateLabels();
    this.prevSeconds = this.currSeconds;
    this.currSeconds = System.nanoTime();
    this.deltaSeconds = (this.currSeconds - this.prevSeconds) / 1000000000.0f;
    this.zModel.update(this.deltaSeconds);
    if (this.inputWait > 0)
    {
      this.inputWait -= this.deltaSeconds;
      if (this.inputWait < 0) GAME_SOUNDS.stopSettingTrap();
    }

    repaint();
  }

  @Override
  /**
   * updates currentScreen dimensions for resizing inside of ZombieHouseViewer
   * @param e  ComponentEvent required by interface
   */
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

  @Override
  /**
   * When a valid key is pressed, set the keyCode's index in keysPressed to true
   * then call moveKeys to do appropriate actions.
   * @param e  KeyEvent required by interface
   */
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
        this.keysPressed[keyCode] = true;
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
    this.curScreenWidth = this.zView.getWidth();
    this.curScreenHeight = this.zView.getHeight();
    this.zView.setCurrentScreenHeight(this.curScreenHeight);
    this.zView.setCurrentScreenWidth(this.curScreenWidth);
    this.zModel.setCurrentScreenHeight(this.curScreenHeight);
    this.zModel.setCurrentScreenWidth(this.curScreenWidth);
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
        this.keysPressed[keyCode] = false;
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
    if(!timer.isRunning() || this.pause || this.inputWait > 0) return;

    // Handle trap keys
    if(this.keysPressed[KeyEvent.VK_P] || this.keysPressed[KeyEvent.VK_T])
    {
      this.zModel.attemptTrapAction();
      GAME_SOUNDS.playSettingTrap();
      this.inputWait = 5.0f;
    }

    // Handle movement keys
    double movement = 1.0f;
    int dir = 5;

    if ((this.keysPressed[KeyEvent.VK_R] || this.keysPressed[KeyEvent.VK_SHIFT]) && this.zModel.getPlayer().getPlayerStamina() > 0)
    {
      this.zModel.getPlayer().setRunning(true);
    }
    else
    {
      this.zModel.getPlayer().setRunning(false);
    }

    if (this.keysPressed[KeyEvent.VK_UP] || this.keysPressed[KeyEvent.VK_W]) dir += 3;
    if (this.keysPressed[KeyEvent.VK_DOWN] || this.keysPressed[KeyEvent.VK_S]) dir -= 3;
    if (this.keysPressed[KeyEvent.VK_LEFT] || this.keysPressed[KeyEvent.VK_A]) dir -= 1;
    if (this.keysPressed[KeyEvent.VK_RIGHT] || this.keysPressed[KeyEvent.VK_D]) dir += 1;

    boolean boost= this.zModel.getPlayer().isRunning();

    if (dir % 2 != 0) movement = movement / Math.sqrt(2);
    switch (dir)
    {
      case 7:
        this.zModel.movePlayer(-movement, -movement, GridOrientation.NORTHWEST, boost);
        break;
      case 8:
        this.zModel.movePlayer(0, -movement, GridOrientation.NORTH, boost);
        break;
      case 9:
        this.zModel.movePlayer(movement, -movement, GridOrientation.NORTHEAST, boost);
        break;
      case 4:
        this.zModel.movePlayer(-movement, 0, GridOrientation.WEST, boost);
        break;
      case 5:
      default:
        return;
      case 6:
        this.zModel.movePlayer(movement, 0, GridOrientation.EAST, boost);
        break;
      case 1:
        this.zModel.movePlayer(-movement, movement, GridOrientation.SOUTHWEST, boost);
        break;
      case 2:
        this.zModel.movePlayer(0, movement, GridOrientation.SOUTH, boost);
        break;
      case 3:
        this.zModel.movePlayer(movement, movement, GridOrientation.SOUTHEAST, boost);
        break;
    }

    // Play footsteps
    if (boost) this.stepCount += 2;
    else this.stepCount += 1;

    if (this.stepCount > 14)
    {
      GAME_SOUNDS.leftFootStep();
      this.stepCount = 0;
    }
    if (this.stepCount % 15 == 7)
    {
      GAME_SOUNDS.rightFootStep();
    }
  }

  /**
   * Plays random zombie sounds with stereo panning
   */
  private void playRandomZombieSounds()
  {
    ArrayList<Zombie> zombies = this.zModel.getZombieList();
    Player player = this.zModel.getPlayer();
    for (Zombie zombie : zombies)
    {
      if (player.getDistanceTo(zombie) < Player.DIST_HEAR * this.zModel.getTileWidth())
      {
        if (zombie.getBumped()) GAME_SOUNDS.playRandomGrunt(zombie, player);
        if (RAND.nextInt() % 83 == 0) GAME_SOUNDS.playRandomDialogue(zombie, player);
        if (zombie.getStepCount() % 30 == 0) GAME_SOUNDS.zombieLeftFootStep(zombie, player);
        else if (zombie.getStepCount() % 30 == 15) GAME_SOUNDS.zombieRightFootStep(zombie, player);
      }
    }
  }

  /**
   * Resets running state when the game is restarted
   */
  private void resetKeys()
  {
    for (int i = 0; i < 128; i++)
    {
      this.keysPressed[i] = false;
    }
  }

  /**
   * Restart the level when the Player dies
   */
  private void restartLevel(boolean levelUp)
  {
    if (levelUp)
    {
      GAME_SOUNDS.playLevelUpSound();
      JOptionPane.showMessageDialog(this, "You reached the next level in Zombie House.\nNext level loading.");
      this.zModel.restart(true);
    }
    else
    {
      GAME_SOUNDS.playLosingSound();
      JOptionPane.showMessageDialog(this, "You died in the Zombie House.\nLevel reloading.");
      this.zModel.restart(false);
    }

    this.resetKeys();
    timer.setInitialDelay(500);
    timer.restart();
    this.zView.restart();
  }
}
