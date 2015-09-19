

/**
 * Created by L301126 on 9/18/15.
 */


import Resources.*;
import model.*;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Graphics;
public class ZombieHouseViewer extends JPanel implements KeyListener
{
  //TODO:  establish screen width/height somehow
  private int height;
  private int width;
  private final static int ROWS = 40;
  private final static int COLS = 40;
  private int currentScreenWidth;
  private int currentScreenHeight;
  private BufferedImage background;
  private SoundLoader gameSounds = new SoundLoader();
  private SpriteLoader sprites = new SpriteLoader();
  private ZombieHouseModel zModel;
  private Player sprite;
  private int negXOffSet = 0;
  private int negYOffSet = 0;


  public ZombieHouseViewer(ZombieHouseModel zModel, Dimension userScreenSize)
  {
    super();
    width = (int)userScreenSize.getWidth();
    height = (int) userScreenSize.getHeight();
    ImageLoader imageLoader = new ImageLoader(zModel,width, height);
    background = imageLoader.getBackground();
    this.sprite = zModel.getPlayer();
    this.zModel = zModel;
    this.currentScreenWidth = (int) userScreenSize.getWidth();
    this.currentScreenHeight = (int) userScreenSize.getHeight();
    this.setFocusable(true);
    this.requestFocusInWindow(true);
    this.addKeyListener(this);
  }

  protected void setCurrentScreenWidth(int x)
  {
    this.currentScreenWidth = x;
  }

  protected void setCurrentScreenHeight(int y)
  {
    this.currentScreenHeight = y;
  }

  private BufferedImage getVisibleBuffer()
  {
    int xMin, xMax, yMin, yMax;

    this.resetOffsets();

    xMin = sprite.getX() - (this.currentScreenWidth / 2);
    if (xMin < 0)
    {
      negXOffSet = Math.abs(xMin);
      xMin = 0;
    }

    yMin = sprite.getY() - (this.currentScreenHeight / 2);
    if (yMin < 0)
    {
      negYOffSet = Math.abs(yMin);
      yMin = 0;
    }

    xMax = xMin + this.currentScreenWidth;
    if ((xMin + xMax) >= background.getWidth()) xMax = background.getWidth() - xMin;

    yMax = yMin + this.currentScreenHeight;
    if ((yMin + yMax) >= background.getHeight()) yMax = background.getHeight() - yMin;

    return background.getSubimage(xMin, yMin, xMax, yMax);

  }

  private void drawSprite(Graphics g)
  {
    g.drawImage(sprites.getCurrentPlayerImage(sprite), this.currentScreenWidth / 2, this.currentScreenHeight / 2, null);
  }

  @Override
  public void paintComponent(Graphics g)
  {
    g.drawImage(this.getVisibleBuffer(), negXOffSet, negYOffSet, null);
    drawSprite(g);
  }

  private void resetOffsets()
  {
    this.negXOffSet = 0;
    this.negYOffSet = 0;
  }

  private void movePlayer()
  {
    int movement = 5;
    if (ZombieHouseModel.runKey) movement = 3 * movement;
    if (ZombieHouseModel.upKey) sprite.move(0, -movement, sprite.getCurrentTile(), GridOrientation.NORTH);
    if (ZombieHouseModel.downKey) sprite.move(0, movement, sprite.getCurrentTile(), GridOrientation.SOUTH);
    if (ZombieHouseModel.leftKey) sprite.move(-movement, 0, sprite.getCurrentTile(), GridOrientation.WEST);
    if (ZombieHouseModel.rightKey) sprite.move(movement, 0, sprite.getCurrentTile(), GridOrientation.EAST);
    sprites.getRotatingRun();

  }

  @Override
  public void keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_UP) ZombieHouseModel.upKey = true;
    if (e.getKeyCode() == KeyEvent.VK_DOWN) ZombieHouseModel.downKey = true;
    if (e.getKeyCode() == KeyEvent.VK_LEFT) ZombieHouseModel.leftKey = true;
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) ZombieHouseModel.rightKey = true;
    if (e.getKeyCode() == KeyEvent.VK_A) ZombieHouseModel.leftKey = true;
    if (e.getKeyCode() == KeyEvent.VK_S) ZombieHouseModel.downKey = true;
    if (e.getKeyCode() == KeyEvent.VK_W) ZombieHouseModel.upKey = true;
    if (e.getKeyCode() == KeyEvent.VK_D) ZombieHouseModel.rightKey = true;
    if (e.getKeyCode() == KeyEvent.VK_W) ZombieHouseModel.runKey = true;
    this.movePlayer();

  }

  @Override
  public void keyReleased(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_UP) ZombieHouseModel.upKey = false;
    if (e.getKeyCode() == KeyEvent.VK_DOWN) ZombieHouseModel.downKey = false;
    if (e.getKeyCode() == KeyEvent.VK_LEFT) ZombieHouseModel.leftKey = false;
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) ZombieHouseModel.rightKey = false;
    if (e.getKeyCode() == KeyEvent.VK_A) ZombieHouseModel.leftKey = false;
    if (e.getKeyCode() == KeyEvent.VK_S) ZombieHouseModel.downKey = false;
    if (e.getKeyCode() == KeyEvent.VK_W) ZombieHouseModel.upKey = false;
    if (e.getKeyCode() == KeyEvent.VK_D) ZombieHouseModel.rightKey = false;
    if (e.getKeyCode() == KeyEvent.VK_W) ZombieHouseModel.runKey = false;
    this.movePlayer();
  }

  @Override
  public void keyTyped(KeyEvent e)
  {
  }
}