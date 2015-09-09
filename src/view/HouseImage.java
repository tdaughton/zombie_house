/**
 * Created by L301126 on 9/8/15.
 */

package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class HouseImage extends JPanel
{
  protected static final int GRID_HEIGHT = 39;
  protected static final int GRID_WIDTH = 39;
  private int currentScreenWidth = GameFrame.MAX_SCREEN_WIDTH;
  private int currentScreenHeight = GameFrame.MAX_SCREEN_HEIGHT;
  private TileImage[][] tileImages = new TileImage[GRID_HEIGHT][GRID_WIDTH];

  public HouseImage(int[][] grid)
  {
    super();
    tileImages = this.translateTileImages(grid);
  }

  private void drawTile(Graphics g)
  {
    int tHeight = this.getTileHeight();
    int tWidth = this.getTileWidth();
    int i = 0;
    int j = 0;
    for(int x=0; x<currentScreenWidth; x+=tWidth)
      for(int y=0; y<currentScreenHeight; y+=tHeight)
        g.drawImage(tileImages[i][j].getTileImage(), x, y, null);
  }

  private int getTileHeight()
  {
    return currentScreenHeight/GRID_HEIGHT;
  }
  private int getTileWidth()
  {
    return currentScreenWidth/GRID_WIDTH;
  }

  private TileImage[][] translateTileImages(int[][] grid)
  {
    int x;
    for (int i = 0; i < HouseImage.GRID_WIDTH; i++)
    {
      for (int j = 0; j < HouseImage.GRID_HEIGHT; j++)
      {
        x=grid[i][j];
        if (x == 0) tileImages[i][j]= new OutsideImage();
        else if (x == 1) tileImages[i][j]= new FloorImage();
        else tileImages[i][j]=new WallImage();
      }
    }
    return tileImages;
  }

  protected void setCurrentScreenWidth(int width)
{
  currentScreenWidth=width;
}
  protected void setCurrentScreenHeight (int height)
  {
    currentScreenHeight=height;
  }

  @Override
  public void paintComponent(Graphics g)
  {
    drawTile(g);
  }
}
