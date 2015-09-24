/**
 * Created by Tess Daughton, September 13th 2015
 * ImageLoader loads the Tile images and creates a BufferedImage map
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
  private ZombieHouseModel zModel;
  private Tile[][] grid;

  private int cols;
  private int rows;

  /**
   * Constructor
   * ImageLoader reads in the three image files for tiles necessary to create a map
   * Then creates the background BufferedImage
   * @param zModel
   * @param MAX_SCREEN_WIDTH
   * @param MAX_SCREEN_HEIGHT
   */
  public ImageLoader(ZombieHouseModel zModel, int MAX_SCREEN_WIDTH, int MAX_SCREEN_HEIGHT)
  {
    this.MAX_SCREEN_HEIGHT = MAX_SCREEN_HEIGHT;
    this.MAX_SCREEN_WIDTH = MAX_SCREEN_WIDTH;
    this.zModel = zModel;
    this.grid = zModel.getMap().getGrid();
    this.cols = ZombieHouseModel.COLS;
    this.rows = ZombieHouseModel.ROWS;
    this.readImages();
    this.createBackground();
  }


  /**
   * Uses a resource stream to load the tile images
   */
  protected void readImages()
  {
    try
    {
      floorImage = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/floor2.jpg"));
      wallImage = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/wall.png"));
      outsideImage = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/deadgrass.png"));
      charredFloorImage = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/burntfloor.jpg"));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Creates the background BufferedImage based on screen dimensions and tile rows/cols
   */
  public void createBackground()
  {
    int tileWidth = this.MAX_SCREEN_WIDTH / 12;
    int tileHeight = this.MAX_SCREEN_HEIGHT / 10;
    int xCoord = 0;
    int yCoord = 0;
    this.background = new BufferedImage(tileWidth * this.rows, tileHeight * this.cols, BufferedImage.TYPE_INT_RGB);
    Graphics g = this.background.getGraphics();

    for (int i = 0; i < this.rows; i++)
    {
      for (int j = 0; j < this.cols; j++)
      {
        if (grid[i][j] instanceof Floor)
        {
          g.drawImage(this.floorImage, xCoord, yCoord, tileWidth, tileHeight, null);
        }
        else if (grid[i][j] instanceof Wall)
        {
          g.drawImage(this.wallImage, xCoord, yCoord, tileWidth, tileHeight, null);
        }
        else if (grid[i][j] instanceof Outside)
        {
          g.drawImage(this.outsideImage, xCoord, yCoord, tileWidth, tileHeight, null);
        }
        else
        {
          g.drawImage(this.charredFloorImage, xCoord, yCoord, tileWidth, tileHeight, null);
        }
        xCoord += tileWidth;
      }
      yCoord += tileHeight;
      xCoord = 0;
    }
  }

  /**
   * getter for background image
   * @return
   */
  public BufferedImage getBackground()
  {
    return this.background;
  }
}
