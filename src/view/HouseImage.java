/**
 * Created by L301126 on 9/8/15.
 */

package view;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import javax.swing.JPanel;

public class HouseImage extends JPanel implements KeyListener
{
  protected static final int GRID_HEIGHT = 40;
  protected static final int GRID_WIDTH = 40;
  private int currentScreenWidth = GameFrame.MAX_SCREEN_WIDTH;
  private int currentScreenHeight = GameFrame.MAX_SCREEN_HEIGHT;
  private TileImage[][] tileImages = new TileImage[GRID_HEIGHT][GRID_WIDTH];
  private OutsideImage outside = new OutsideImage();
  private FloorImage floor = new FloorImage();
  private WallImage wall = new WallImage();
  private TreeImage trees = new TreeImage();
  private int zoomFactor = 1;
  private PlayerImage sprite;



  public HouseImage(int[][] grid)
  {
    super();
    sprite = this.spawnSprite();
    tileImages = this.translateTileImages(grid);
    this.setFocusable(true);
    this.requestFocusInWindow(true);
    this.addMouseWheelListener(new MouseWheelListener()
    {

      @Override
      public void mouseWheelMoved(MouseWheelEvent e)
      {
        if (e.getWheelRotation() < 0) zoomFactor += -5;
        else zoomFactor += 5;
      }
    });
    addKeyListener(this);
  }

  private void drawTile(Graphics g)
  {
    int tHeight = this.getTileHeight();
    int tWidth = this.getTileWidth();
    int x = 0;
    int y = 0;
    for (int i = 0; i < GRID_WIDTH; i++)
    {
      for (int j = 0; j < GRID_HEIGHT; j++)
      {
        g.drawImage(tileImages[i][j].getTileImage(), x, y, tWidth, tHeight, null);
        x += tWidth;
      }
      y += tHeight;
      x = 0;
    }
  }

  private int getTileHeight()
  {
    return currentScreenHeight / GRID_HEIGHT + zoomFactor;
  }

  private int getTileWidth()
  {
    return currentScreenWidth / GRID_WIDTH + zoomFactor;
  }

  private TileImage[][] translateTileImages(int[][] grid)
  {
    for (int i = 0; i < HouseImage.GRID_WIDTH; i++)
    {
      for (int j = 0; j < HouseImage.GRID_HEIGHT; j++)
      {
        if (grid[i][j] == 48) tileImages[i][j] = outside;
        else if (grid[i][j] == 49) tileImages[i][j] = floor;
        else tileImages[i][j] = wall;
      }
    }
    return tileImages;
  }

  private PlayerImage spawnSprite()
  {
    for (int i = 0; i < GRID_WIDTH; i++)
      for (int j = 0; j < GRID_HEIGHT; j++)
        if(tileImages[i][j] instanceof FloorImage)
        {
           return new PlayerImage(i*getTileWidth(),j*getTileHeight(), 10);
        }
    return new PlayerImage(0,0,10);
  }
  /**
   * needs more work to optimize loading speed
   */
//  private void decorateOutside(Graphics g)
//  {
//    int tHeight = this.getTileHeight();
//    int tWidth = this.getTileWidth();
//    int x = 0;
//    int y = 0;
//    for (int i = 0; i < GRID_WIDTH; i++)
//    {
//      for (int j = 0; j < GRID_HEIGHT; j++)
//      {
//        if (tileImages[i][j] instanceof OutsideImage)
//        {
//          g.drawImage(trees.getRandomTrees(), x, y, tWidth, tHeight, null);
//        }
//        x += tWidth;
//      }
//      y += tHeight;
//      x = 0;
//    }
//  }

  protected void setCurrentScreenWidth(int width)
  {
    currentScreenWidth = width;
  }

  protected void setCurrentScreenHeight(int height)
  {
    currentScreenHeight = height;
  }

  protected void drawSprite(Graphics g)
  {
    g.setColor(Color.red);
    g.fillOval((int) sprite.getX(), (int) sprite.getY(), (int) sprite.getRadius(), (int) sprite.getRadius());
  }

  @Override
  public void paintComponent(Graphics g)
  {
    drawTile(g);
    //decorateOutside(g);
    drawSprite(g);
  }




  @Override
  public void keyTyped(KeyEvent e)
  {
    if(e.getKeyCode()==KeyEvent.VK_UP) sprite.move(0, -5);
    if(e.getKeyCode()==KeyEvent.VK_DOWN) sprite.move(0, 5);
    if(e.getKeyCode()==KeyEvent.VK_RIGHT) sprite.move(5,0);
    if(e.getKeyCode()==KeyEvent.VK_LEFT) sprite.move(-5,0);
  }

  @Override
  public void keyPressed(KeyEvent e)
  { if(e.getKeyCode()==KeyEvent.VK_UP) sprite.move(0,-5);
    if(e.getKeyCode()==KeyEvent.VK_DOWN) sprite.move(0,5);
    if(e.getKeyCode()==KeyEvent.VK_RIGHT) sprite.move(5,0);
    if(e.getKeyCode()==KeyEvent.VK_LEFT) sprite.move(-5,0);
  }

  @Override
  public void keyReleased(KeyEvent e) {}
}