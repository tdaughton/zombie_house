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
  protected final int MAX_TILE_AREA = (GameFrame.MAX_SCREEN_WIDTH/GRID_WIDTH) * (GameFrame.MAX_SCREEN_WIDTH/GRID_HEIGHT);
  private TileImage[][] tileImages = new TileImage[GRID_HEIGHT][GRID_WIDTH];
  private OutsideImage outside = new OutsideImage();
  private FloorImage floor = new FloorImage();
  private WallImage wall = new WallImage();
  private TreeImage trees = new TreeImage();
  private int tHeight = this.getTileHeight();
  private int tWidth = this.getTileWidth();
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

  private void drawTile(int xMin, int yMin, int xMax, int yMax, int xAdj, int yAdj,  Graphics g)
  {
    int x = -xAdj;
    int y = -yAdj;
    for (int i = xMin; i < xMax; i++)
    {
      for (int j = yMin; j < yMax; j++)
      {
        g.drawImage(tileImages[i][j].getTileImage(), x, y, this.tWidth, this.tHeight, null);
        x += this.tWidth;
      }
      y += this.tHeight;
      x = -xAdj;
    }
  }

  private void setVisibleFrame(Graphics g)
  {
    this.tHeight = this.getTileHeight();
    this.tWidth = this.getTileWidth();

    //what do we want the zoom level to be automatically?
    //maybe like 10*10 tiles?

    if(tHeight*tWidth>MAX_TILE_AREA) zoomFactor=0;
    //else if((getTileHeight()*getTileWidth()>80)) {}
    double xPlayer = sprite.getX();
    double yPlayer = sprite.getY();

    int xPlayerTile = (int) xPlayer/tWidth;
    int yPlayerTile = (int) yPlayer/tHeight;


    // i dont think anything related to the tiles can be a double
    //its fucking it up

    int visXTile = Math.min((currentScreenWidth/tWidth), GRID_WIDTH);
    int visYTile = Math.min((currentScreenHeight/tHeight),GRID_HEIGHT);

    int xMin = Math.max ((xPlayerTile-(visXTile/2)-1),0);
    int yMin = Math.max ((yPlayerTile-(visYTile/2)-1),0);
    int xMax = Math.min ((xPlayerTile + (visXTile / 2) + 1), GRID_WIDTH);
    int yMax = Math.min ((yPlayerTile + (visYTile / 2) + 1), GRID_HEIGHT);


    double offX = xPlayer - (currentScreenWidth/2);
    double offY = yPlayer - (currentScreenHeight/2);

    int tileAdjustmentX = (int) offX%tWidth;
    int tileAdjustmentY = (int) offY%tHeight;
    int x = xMax-xMin;
    int y = yMax-yMin;
    System.out.print("visx " + x + " visy " +y+ "\n");
    System.out.println("xPlayer: " + xPlayer);
    System.out.println("yPlayer: " + yPlayer);


    drawTile(xMin,yMin,xMax,yMax,tileAdjustmentX,tileAdjustmentY,g);

  }


  private int getTileHeight()
  {
    return 95;
        //currentScreenHeight / GRID_HEIGHT + zoomFactor;
  }

  private int getTileWidth() {return 168;}
  // currentScreenWidth / GRID_WIDTH + zoomFactor;}

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

    return new PlayerImage(900,900,20);
  }
  /**
   * needs more work to optimize loading speed/ get rid of randomization
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
    setVisibleFrame(g);
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