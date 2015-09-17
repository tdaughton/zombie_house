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
  public GamePanel(Tile[][] map, BufferedImage grid, Dimension userScreenSize)
  {
    super();
    this.sprite = this.getRandomStart(map);
    this.grid=grid;
    this.currentScreenWidth=(int)userScreenSize.getWidth();
    this.currentScreenHeight=(int)userScreenSize.getHeight();
    this.setFocusable(true);
    this.requestFocusInWindow(true);
    this.addKeyListener(this);
  }

  protected void setCurrentScreenWidth(int x)
  {
    this.currentScreenWidth=x;
  }
  protected void setCurrentScreenHeight(int y)
  {
    this.currentScreenHeight=y;
  }
  private Player getRandomStart(Tile[][] map)
  {
    int x= rand.nextInt(39);
    int y = rand.nextInt(39);

    if(map[x][y] instanceof Floor)
    {
      sprite = new Player(map[x][y].getCenterTileX(),map[x][y].getCenterTileY(),60,map[x][y],
                          map);
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

    if(e.getKeyCode()==KeyEvent.VK_R) move=3*move;
    if(e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_W) sprite.move(0,-(move),curr, GridOrientation.NORTH);
    if(e.getKeyCode()==KeyEvent.VK_DOWN ||e.getKeyCode()==KeyEvent.VK_S) sprite.move(0,move,curr, GridOrientation.SOUTH);
    if(e.getKeyCode()==KeyEvent.VK_RIGHT || e.getKeyCode()==KeyEvent.VK_D) sprite.move(move,0,curr, GridOrientation.EAST);
    if(e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_A) sprite.move(-(move),0,curr, GridOrientation.WEST);

    sprites.setRotatingRun();
  }

  @Override
  public void keyTyped(KeyEvent e)
  {}

  @Override
  public void keyReleased(KeyEvent e) {}
}
