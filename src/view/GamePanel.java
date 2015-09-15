/**
 * Created by L301126 on 9/8/15.
 */

package view;

import model.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener
{
  private int currentScreenWidth;
  private int currentScreenHeight;
  private BufferedImage grid;
  private Player sprite;
  private Random rand = new Random();
  private int negXOffSet=0;
  private int negYOffSet=0;
  private int posXOffSet=0;
  private int posYOffSet=0;

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
      sprite = new Player(map[x][y].getCenterTileX(),map[x][y].getCenterTileY(),20,map[x][y],map);
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

    System.out.println("("+sprite.getX() + ", " + sprite.getY()+ ")");
    System.out.println("("+xMin + ", " + xMax + ")");
    System.out.println("("+yMin + ", " + yMax + ")");
//    System.out.println("(" + grid.getRaster().getHeight() + ", " + grid.getRaster().getWidth() + ")");

    return grid.getSubimage(xMin, yMin, xMax, yMax);

  }

  private void drawSprite(Graphics g)
  {
    g=grid.getGraphics();
    g.setColor(Color.RED);
    g.fillOval(sprite.getX(),sprite.getY(), sprite.getRadius(), sprite.getRadius());
  }

  @Override
  public void paintComponent(Graphics g)
  {
    g.drawImage(this.getVisibleBuffer(),negXOffSet , negYOffSet,null);
    g.setColor(Color.RED);
    g.fillOval(this.currentScreenWidth/2, this.currentScreenHeight/2, sprite.getRadius(),sprite.getRadius());
  }

  private void resetOffsets()
  {
    this.negXOffSet=0;
    this.negYOffSet=0;
  }

  @Override
  public void keyPressed(KeyEvent e)
  {
    int x = 0;
    int y =0;
    if(e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_W) y=-5;
    if(e.getKeyCode()==KeyEvent.VK_DOWN ||e.getKeyCode()==KeyEvent.VK_S) y=5;
    if(e.getKeyCode()==KeyEvent.VK_RIGHT || e.getKeyCode()==KeyEvent.VK_D) x=5;
    if(e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_A) x=-5;
    if(e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_W) y=-5;
    if(e.getKeyCode()==KeyEvent.VK_DOWN ||e.getKeyCode()==KeyEvent.VK_S) y=5;
    if(e.getKeyCode()==KeyEvent.VK_RIGHT || e.getKeyCode()==KeyEvent.VK_D) x=5;
    if(e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_A) x=-5;

    //running
    if(e.getKeyCode()==KeyEvent.VK_R && (e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_W)) y=-10;
    if(e.getKeyCode()==KeyEvent.VK_R && (e.getKeyCode()==KeyEvent.VK_DOWN ||e.getKeyCode()==KeyEvent.VK_S)) y=10;
    if(e.getKeyCode()==KeyEvent.VK_R && (e.getKeyCode()==KeyEvent.VK_RIGHT || e.getKeyCode()==KeyEvent.VK_D)) x=10;
    if(e.getKeyCode()==KeyEvent.VK_R && (e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_A)) x=-10;

    sprite.move(x, y, sprite.getCurrentTile());
  }

  @Override
  public void keyTyped(KeyEvent e)
  {
    int x = 0;
    int y =0;
    if(e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_W) y=-5;
    if(e.getKeyCode()==KeyEvent.VK_DOWN ||e.getKeyCode()==KeyEvent.VK_S) y=5;
    if(e.getKeyCode()==KeyEvent.VK_RIGHT || e.getKeyCode()==KeyEvent.VK_D) x=5;
    if(e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_A) x=-5;
    if(e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_W) y=-5;
    if(e.getKeyCode()==KeyEvent.VK_DOWN ||e.getKeyCode()==KeyEvent.VK_S) y=5;
    if(e.getKeyCode()==KeyEvent.VK_RIGHT || e.getKeyCode()==KeyEvent.VK_D) x=5;
    if(e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_A) x=-5;

    //running
    if(e.getKeyCode()==KeyEvent.VK_R && (e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_W)) y=-20;
    if(e.getKeyCode()==KeyEvent.VK_R && (e.getKeyCode()==KeyEvent.VK_DOWN ||e.getKeyCode()==KeyEvent.VK_S)) y=20;
    if(e.getKeyCode()==KeyEvent.VK_R && (e.getKeyCode()==KeyEvent.VK_RIGHT || e.getKeyCode()==KeyEvent.VK_D)) x=20;
    if(e.getKeyCode()==KeyEvent.VK_R && (e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_A)) x=-20;

    sprite.move(x, y, sprite.getCurrentTile());
  }

  @Override
  public void keyReleased(KeyEvent e) {}
}