/**
 * Created by L301126 on 9/8/15.
 */

package view;

import model.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import java.util.Random;

public class HouseImage extends JPanel implements KeyListener
{
  public static final int GRID_HEIGHT = 40;
  public static final int GRID_WIDTH = 40;
  private int currentScreenWidth = GameFrame.MAX_SCREEN_WIDTH;
  private int currentScreenHeight = GameFrame.MAX_SCREEN_HEIGHT;
  public  int visTileX = 12;
  public  int visTileY = 10;
  private int tHeight = this.getTileHeight();
  private int tWidth = this.getTileWidth();
  private Tile[][] tiles = new Tile[GRID_WIDTH][GRID_HEIGHT];
  private TileImage[][] tileImages = new TileImage[GRID_WIDTH][GRID_HEIGHT];
  private OutsideImage outside = new OutsideImage();
  private FloorImage floor = new FloorImage();
  private WallImage wall = new WallImage();
  private Player sprite;
  private Random rand = new Random();

  public HouseImage(int[][] grid)
  {
    super();
    tileImages = this.translateTileImages(grid);
    this.spawnPlayer();
    this.setFocusable(true);
    this.requestFocusInWindow(true);
    this.addKeyListener(this);
  }

  private void drawTile(int xMin, int yMin,int xMax, int yMax,  int xAdj, int yAdj,  Graphics g)
  {
    this.calculateTileBounds(xMin,xMax,yMin,yMax);

    int x =0;
    int y= 0;
    for (int i =yMin; i < yMax; i++)
    {
      for (int j = xMin; j < xMax; j++)
      {
        g.drawImage(tileImages[j][i].getTileImage(), this.tWidth * x - xAdj, this.tHeight * y - yAdj,
            this.tWidth, this.tHeight, null);
        x++;
      }
      y++;
      x=0;
    }
  }


  private void setVisibleFrame(Graphics g)
  {
    Tile current = sprite.getCurrentTile();

    int xMin = Math.max((current.getGridRow() - (visTileX / 2)-1), 0);
    int yMin = Math.max((current.getGridCol() - (visTileY / 2)-1), 0);

    int xMax = Math.min((xMin+visTileX)+1,GRID_WIDTH);
    int yMax = Math.min((yMin+visTileY)+1,GRID_HEIGHT);

    int tileAdjustmentX = sprite.getX() % this.tWidth;
    int tileAdjustmentY = sprite.getY() % this.tHeight;

//      System.out.println("xmin: " + xMin+ " xMax: " + xMax);
//
//    System.out.println("ymin: " + yMin + " yMax: " + yMax);
//
//    System.out.println("tWidth " + this.tWidth);
//      System.out.println("tHeight " + this.tHeight);
//      System.out.println("tileAdjustmentX: " + tileAdjustmentX);
//     System.out.println("tileAdjustmentY: " + tileAdjustmentY);
//    System.out.println("i= " + current.getGridRow() + " j= " + current.getGridCol());


    drawTile(xMin, yMin, xMax, yMax, tileAdjustmentX, tileAdjustmentY, g);

  }


  private int getTileHeight()
  {
    return this.currentScreenHeight/this.visTileY;
  }

  private int getTileWidth() {return this.currentScreenWidth/this.visTileX; }

  private TileImage[][] translateTileImages(int[][] grid)
  {
    for (int i = 0; i < HouseImage.GRID_WIDTH; i++)
    {
      for (int j = 0; j < HouseImage.GRID_HEIGHT; j++)
      {
        if (grid[i][j] == 48)
        {
          tileImages[i][j] = outside;
          tiles[i][j]=new Outside(i, j, tiles);
        }
        else if (grid[i][j] == 49)
        {
          tileImages[i][j] = floor;
          tiles[i][j]=new Floor(i, j, tiles);

        }
        else
        {
          tileImages[i][j] = wall;
          tiles[i][j]=new Wall(i, j, tiles);
        }
//        System.out.println("("+i +", " + j +") " +  tiles[i][j].getTileType());
//        System.out.println("xMin: " + i*this.getTileWidth() + " xMax : " + (i+1)*this.getTileWidth());
//        System.out.println("yMin: " + j*this.getTileHeight() + " yMax : " + (j+1)*this.getTileHeight());

      }
    }
    return tileImages;
  }

  private void calculateTileBounds(int xStart, int xStop, int yStart, int yStop)
  {
    int visX = 0;
    int visY = 0;
    for (int i = xStart; i < xStop; i++)
    {
      for (int j = yStart; j < yStop; j++)
      {
        tiles[i][j].setBounds((visX) * this.getTileWidth(), ((visX / 2) + 1) * this.getTileWidth(),
            (visY) * this.getTileHeight(), ((visY / 2) + 1) * this.getTileHeight());
        visX++;
      }
      visY++;
      visX = 0;
    }
  }


  private void spawnPlayer()
  {
    int x = rand.nextInt(GRID_WIDTH);
    int y = rand.nextInt(GRID_HEIGHT);
    if (tiles[x][y].getIsMovable())
    {
      tiles[x][y].setBounds((visTileX/2)*this.getTileWidth(),((visTileX/2)+1)*this.getTileWidth(),
                            (visTileY)*this.getTileHeight(), ((visTileY/2)+1)*this.getTileHeight());
      System.out.println("(" + x + " , " + y + ")");
      System.out.println(tiles[x][y].getTileType());
      sprite = new Player(tiles[x][y].getCenterTileX(), tiles[x][y].getCenterTileY(), 20, tiles[x][y], tiles, this);
      new PlayerImage(tiles[x][y].getCenterTileX(),tiles[x][y].getCenterTileY(), sprite.getRadius());
      System.out.println("("+tiles[x][y].getCenterTileX()+" , " + y + ")");

    }
    else spawnPlayer();
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
    g.fillOval(sprite.getX(), sprite.getY(), sprite.getRadius(), sprite.getRadius());
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
    sprite.move(x,y,sprite.getCurrentTile());
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
    sprite.move(x,y,sprite.getCurrentTile());
  }

  @Override
  public void keyReleased(KeyEvent e) {}
}