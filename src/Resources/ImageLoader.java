
/**
 * Created by Tess Daughton, September 13th 2015
 */
package Resources;

import model.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageLoader

{
  private final int MAX_SCREEN_HEIGHT;
  private final int MAX_SCREEN_WIDTH;
  private BufferedImage floorImage;
  private BufferedImage charredFloorImage;
  private BufferedImage wallImage;
  private BufferedImage outsideImage;
  private BufferedImage background;

  private int cols;
  private int rows;


  public ImageLoader(int MAX_SCREEN_WIDTH,int MAX_SCREEN_HEIGHT)
  {
    this.MAX_SCREEN_HEIGHT=MAX_SCREEN_HEIGHT;
    this.MAX_SCREEN_WIDTH=MAX_SCREEN_WIDTH;
    readImages();
  }

  public void initializeMap(Tile[][] grid, int cols, int rows)
  {
    this.cols = cols;
    this.rows = rows;

    createBackground(grid);

  }

  protected void readImages()
  {
    try
    {
      floorImage = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/floor2.jpg"));
      wallImage = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/wall.png"));
      outsideImage = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/deadgrass.png"));


    } catch (IOException e)
    {
      e.printStackTrace();
    }

  }

  protected void createBackground(Tile[][] grid)
  {
    int tileWidth = MAX_SCREEN_WIDTH/ 12;
    int tileHeight = MAX_SCREEN_HEIGHT / 10;

    int xCoord = 0;
    int yCoord = 0;
    background = new BufferedImage(tileWidth * rows, tileHeight * cols, BufferedImage.TYPE_INT_RGB);
    Graphics g = background.getGraphics();

    for (int j = 0; j < cols; j++)
    {
      for (int i = 0; i < rows; i++)
      {
        if (grid[i][j] instanceof Floor)
        {
          g.drawImage(floorImage, xCoord, yCoord, tileWidth, tileHeight, null);
        }
        else if (grid[i][j] instanceof Wall)
        {
          g.drawImage(wallImage, xCoord, yCoord, tileWidth, tileHeight, null);
        }
        else if (grid[i][j] instanceof Outside)
        {
          g.drawImage(outsideImage, xCoord, yCoord, tileWidth, tileHeight, null);
        }
        else
        {
          g.drawImage(charredFloorImage, xCoord, yCoord, tileWidth, tileHeight, null);
        }
        yCoord += tileHeight;
      }
      xCoord += tileWidth;
      yCoord = 0;
    }
  }

  public BufferedImage getBackground()
  {
    return this.background;
  }
}
