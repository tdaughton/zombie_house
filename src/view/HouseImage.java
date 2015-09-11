/**
 * Created by L301126 on 9/8/15.
 */

package view;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class HouseImage extends JPanel implements KeyListener
{
  protected static final int GRID_HEIGHT = 40;
  protected static final int GRID_WIDTH = 40;
  private int currentScreenWidth = GameFrame.MAX_SCREEN_WIDTH;
  private int currentScreenHeight = GameFrame.MAX_SCREEN_HEIGHT;
  private TileImage[][] tileImages = new TileImage[GRID_HEIGHT][GRID_WIDTH];
  private final int MAX_VISIBLE_TILE_X = 12;
  private final int MAX_VISIBLE_TILE_Y = 10;
  private OutsideImage outside = new OutsideImage();
  private FloorImage floor = new FloorImage();
  private WallImage wall = new WallImage();
  private int tHeight = this.getTileHeight();
  private int tWidth = this.getTileWidth();
  private PlayerImage sprite;



  public HouseImage(int[][] grid)
  {
    super();
    sprite = this.spawnSprite();
    tileImages = this.translateTileImages(grid);
    this.setFocusable(true);
    this.requestFocusInWindow(true);
    this.addKeyListener(this);
  }

  private void drawTile(int xMin, int yMin,int xMax, int yMax,  int xAdj, int yAdj,  Graphics g)
  {

    for (int i = 0; i < yMax; i++)
    {
      for (int j = 0; j < xMax; j++)
      {
        g.drawImage(tileImages[yMin+i][xMin+j].getTileImage(), this.tWidth*j-xAdj, this.tHeight*i-yAdj, this.tWidth, this.tHeight, null);
      }
    }
  }

  private boolean checkMovable(int xNew, int yNew)
  { xNew = sprite.getX() + xNew;
    yNew = sprite.getY() + yNew;
    int xNewTile = Math.round(xNew / this.tWidth);
    int yNewTile = Math.round(yNew / this.tHeight);
    System.out.println("xNewTile " + xNewTile);
    System.out.println("yNewTile " + yNewTile);
    System.out.println(tileImages[xNewTile][yNewTile].getTileType());

    return tileImages[xNewTile][yNewTile].getIsMovable();
  }

  private void setVisibleFrame(Graphics g)
  {
    int xPlayer = sprite.getX();
    int yPlayer = sprite.getY();

    int xPlayerTile = Math.round(xPlayer / tHeight);
    int yPlayerTile = Math.round(yPlayer / tWidth);

    int visXTile = Math.min((currentScreenWidth / tWidth), GRID_WIDTH);
    int visYTile = Math.min((currentScreenHeight / tHeight), GRID_HEIGHT);

    int xMin = Math.max((xPlayerTile - (visXTile / 2)), 0);
    int yMin = Math.max((yPlayerTile - (visYTile / 2)), 0);
    visXTile = Math.min(visXTile+1,GRID_WIDTH);
    visYTile = Math.min(visYTile+1,GRID_HEIGHT);

    int tileAdjustmentX = xPlayer % this.tWidth;
    int tileAdjustmentY = yPlayer % this.tHeight;

    drawTile(xMin, yMin, visXTile, visYTile, tileAdjustmentX, tileAdjustmentY, g);

  }


  private int getTileHeight()
  {
    return currentScreenHeight / MAX_VISIBLE_TILE_Y;
  }

  private int getTileWidth() {return currentScreenWidth /MAX_VISIBLE_TILE_X; }

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
    return new PlayerImage(900,900,20, this);
  }

  protected int getCurrentScreenWidth()
  {
    return this.currentScreenWidth;
  }
  protected int getCurrentScreenHeight()
  {
    return this.currentScreenHeight;
  }


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
    g.fillOval(sprite.getRelativeX(),sprite.getRelativeY(), sprite.getRadius(), sprite.getRadius());
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
    int x = 0;
    int y =0;

    if(e.getKeyCode()==KeyEvent.VK_UP) y=-5;
    if(e.getKeyCode()==KeyEvent.VK_DOWN)y=5;
    if(e.getKeyCode()==KeyEvent.VK_RIGHT)x=5;
    if(e.getKeyCode()==KeyEvent.VK_LEFT) x=-5;
    if(this.checkMovable(x,y)) { sprite.move(x,y); }
  }

  @Override
  public void keyPressed(KeyEvent e)
  {
    int x = 0;
    int y =0;

    if(e.getKeyCode()==KeyEvent.VK_UP) y=-5;
    if(e.getKeyCode()==KeyEvent.VK_DOWN) y=5;
    if(e.getKeyCode()==KeyEvent.VK_RIGHT) x=5;
    if(e.getKeyCode()==KeyEvent.VK_LEFT) x=-5;
    if(this.checkMovable(x,y)) { sprite.move(x,y); }
  }

  @Override
  public void keyReleased(KeyEvent e) {}
}