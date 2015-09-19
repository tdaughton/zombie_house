/**
 * Created by L301126 on 9/8/15.
 */

package view;


import model.*;
import Resources.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Random;
import java.awt.RadialGradientPaint;

public class GamePanel extends JPanel implements KeyListener
{
  private int currentScreenWidth;
  private int currentScreenHeight;
  private BufferedImage grid;
  private SoundLoader gameSounds = new SoundLoader();
  private SpriteLoader sprites = new SpriteLoader();
  private Player sprite;
  private Random rand = new Random();
  private int negXOffSet=0;
  private int negYOffSet=0;
  private int iterator=0;
  private boolean[] keys = new boolean[128];

  public GamePanel(Tile[][] map, BufferedImage grid, Dimension userScreenSize)
  {
    super();
    this.sprite = this.getRandomStart(map);
    this.grid=grid;
    this.currentScreenWidth=this.getSize().width;
    this.currentScreenHeight=this.getSize().height;
    this.setFocusable(true);
    this.requestFocusInWindow(true);
    this.addKeyListener(this);
  }

  protected void setCurrentScreenWidth(int x)
  {
    this.currentScreenWidth=this.getSize().width;
  }

  protected void setCurrentScreenHeight(int y)
  {
    this.currentScreenHeight=this.getSize().height;
  }

  private Player getRandomStart(Tile[][] map)
  {
    int x= rand.nextInt(39);
    int y = rand.nextInt(39);

    if(map[y][x] instanceof Floor)
    {
      sprite = new Player(map[y][x].getCenterTileX(),map[y][x].getCenterTileY(),60,map[y][x], map);
    }
    else getRandomStart(map);
    return sprite;
  }

  private BufferedImage getVisibleBuffer()
  {
    int xMin, xMax, yMin, yMax;

    this.resetOffsets();

    xMin = sprite.getX() - (this.currentScreenWidth / 2);
    if (xMin < 0)
    { negXOffSet=Math.abs(xMin);
      xMin = 0;
    }

    yMin = sprite.getY() - (this.currentScreenHeight / 2);
    if (yMin < 0)
    { negYOffSet=Math.abs(yMin);
      yMin = 0;
    }

    xMax = xMin+this.currentScreenWidth;
    if ((xMin+xMax) >= grid.getWidth()) xMax = grid.getWidth() - xMin;

    yMax = yMin+this.currentScreenHeight;
    if ((yMin+yMax) >= grid.getHeight()) yMax = grid.getHeight() - yMin;

    return grid.getSubimage(xMin, yMin, xMax, yMax);
  }

  private void drawSprite(Graphics g)
  {
    //g.setColor(Color.RED);
    //g.fillOval(this.currentScreenWidth / 2, this.currentScreenHeight / 2, (int)sprite.getBoundingCircle().getRadius(), (int)sprite.getBoundingCircle().getRadius());
    g.drawImage(sprites.getCurrentPlayerImage(sprite), this.currentScreenWidth / 2, this.currentScreenHeight / 2, null);
  }

  @Override
  public void paintComponent(Graphics g)
  {
   // RadialGradientPaint light = new RadialGradientPaint(this.currentScreenWidth/2, this.currentScreenHeight/2,);
    g.drawImage(this.getVisibleBuffer(), negXOffSet, negYOffSet, null);
    drawSprite(g);
  }

  private void resetOffsets()
  {
    this.negXOffSet=0;
    this.negYOffSet=0;
  }

  @Override
  public void keyPressed(KeyEvent e)
  {
    Tile curr = sprite.getCurrentTile();
    int move=5;

    if(iterator%8==0)
    {
      gameSounds.leftFootStep();
    }
    if(iterator%8==4)
    {
      gameSounds.rightFootStep();
    }
    iterator++;

    int keyPressed = e.getKeyCode();
    switch (keyPressed)
    {
      case KeyEvent.VK_R:
      case KeyEvent.VK_UP:
      case KeyEvent.VK_DOWN:
      case KeyEvent.VK_RIGHT:
      case KeyEvent.VK_LEFT:
      case KeyEvent.VK_W:
      case KeyEvent.VK_S:
      case KeyEvent.VK_D:
      case KeyEvent.VK_A:
      case KeyEvent.VK_SHIFT:
      case KeyEvent.VK_T:
        keys[e.getKeyCode()] = true;
        break;
      default:
        break;
    }

    if (this.keys[KeyEvent.VK_R] || this.keys[KeyEvent.VK_SHIFT]) move=3*move;
    if (this.keys[KeyEvent.VK_UP] || this.keys[KeyEvent.VK_W]) sprite.move(0,-(move),curr, GridOrientation.NORTH);
    if (this.keys[KeyEvent.VK_DOWN] || this.keys[KeyEvent.VK_S]) sprite.move(0,move,curr, GridOrientation.SOUTH);
    if (this.keys[KeyEvent.VK_RIGHT] || this.keys[KeyEvent.VK_D]) sprite.move(move,0,curr, GridOrientation.EAST);
    if (this.keys[KeyEvent.VK_LEFT] || this.keys[KeyEvent.VK_A]) sprite.move(-(move), 0, curr, GridOrientation.WEST);

    sprites.setRotatingRun();
  }

  @Override
  public void keyTyped(KeyEvent e)
  {
    Tile curr = sprite.getCurrentTile();
    int move=5;

    if(iterator%8==0)
    {
      gameSounds.leftFootStep();
    }
    if(iterator%8==4)
    {
      gameSounds.rightFootStep();
    }
    iterator++;

    int keyPressed = e.getKeyCode();
    switch (keyPressed)
    {
      case KeyEvent.VK_R:
      case KeyEvent.VK_UP:
      case KeyEvent.VK_DOWN:
      case KeyEvent.VK_RIGHT:
      case KeyEvent.VK_LEFT:
      case KeyEvent.VK_W:
      case KeyEvent.VK_S:
      case KeyEvent.VK_D:
      case KeyEvent.VK_A:
      case KeyEvent.VK_SHIFT:
      case KeyEvent.VK_T:
        keys[e.getKeyCode()] = true;
        break;
      default:
        break;
    }

    if (this.keys[KeyEvent.VK_R] || this.keys[KeyEvent.VK_SHIFT]) move=3*move;
    if (this.keys[KeyEvent.VK_UP] || this.keys[KeyEvent.VK_W]) sprite.move(0,-(move),curr, GridOrientation.NORTH);
    if (this.keys[KeyEvent.VK_DOWN] || this.keys[KeyEvent.VK_S]) sprite.move(0,move,curr, GridOrientation.SOUTH);
    if (this.keys[KeyEvent.VK_RIGHT] || this.keys[KeyEvent.VK_D]) sprite.move(move,0,curr, GridOrientation.EAST);
    if (this.keys[KeyEvent.VK_LEFT] || this.keys[KeyEvent.VK_A]) sprite.move(-(move), 0, curr, GridOrientation.WEST);

    sprites.setRotatingRun();
  }

  @Override
  public void keyReleased(KeyEvent e)
  {
    int keyPressed = e.getKeyCode();
    switch (keyPressed)
    {
      case KeyEvent.VK_R:
      case KeyEvent.VK_UP:
      case KeyEvent.VK_DOWN:
      case KeyEvent.VK_RIGHT:
      case KeyEvent.VK_LEFT:
      case KeyEvent.VK_W:
      case KeyEvent.VK_S:
      case KeyEvent.VK_D:
      case KeyEvent.VK_A:
      case KeyEvent.VK_SHIFT:
      case KeyEvent.VK_T:
        keys[e.getKeyCode()] = false;
        break;
      default:
        break;
    }
  }
}
