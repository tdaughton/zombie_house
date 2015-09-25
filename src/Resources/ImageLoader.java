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
  private static BufferedImage floorImage;
  private static BufferedImage charredFloorImage;
  private static BufferedImage wallImage;
  private static BufferedImage outsideImage;
  private static BufferedImage background;
  private static BufferedImage zombieSheet;
  private static BufferedImage playerSheetRun;
  private ZombieHouseModel zModel;
  private int tileWidth;
  private int tileHeight;
  private int cols;
  private int rows;

  /**
   * Constructor
   * ImageLoader reads in the three image files for tiles necessary to create a map
   * Then creates the background BufferedImage
   * @param zModel
   * @param tWidth
   * @param tHeight
   */
  public ImageLoader(ZombieHouseModel zModel, int tWidth, int tHeight)
  {
    this.zModel = zModel;
    this.cols = zModel.getCols();
    this.rows = zModel.getRows();
    this.tileWidth = tWidth;
    this.tileHeight = tHeight;
    background = new BufferedImage(tileWidth * this.rows, tileHeight * this.cols, BufferedImage.TYPE_INT_RGB);
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
      charredFloorImage = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/burntfloor.png"));
      playerSheetRun = ImageIO.read(this.getClass().getResourceAsStream("/Resources/sprite_resources/running.png"));
      zombieSheet = ImageIO.read(this.getClass().getResourceAsStream("/Resources/sprite_resources/zombie_sprite.png"));
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
    Tile[][] grid = this.zModel.getGrid();
    int xCoord = 0;
    int yCoord = 0;
    Graphics g = background.getGraphics();

    for (int i = 0; i < this.rows; i++)
    {
      for (int j = 0; j < this.cols; j++)
      {
        if (grid[i][j] instanceof Floor)
        {
          g.drawImage(floorImage, xCoord, yCoord, tileWidth, tileHeight, null);
        }
        else if (grid[i][j] instanceof Wall)
        {
          g.drawImage(wallImage, xCoord, yCoord, tileWidth, tileHeight, null);
          g.setColor(Color.BLUE);
          g.drawRect((int)grid[i][j].getX(), (int)grid[i][j].getY(), (int)grid[i][j].getWidth(), (int)grid[i][j].getHeight());
        }
        else if (grid[i][j] instanceof Outside)
        {
          g.drawImage(outsideImage, xCoord, yCoord, tileWidth, tileHeight, null);
        }
        else if (grid[i][j] instanceof CharredFloorTile )
        {
          g.drawImage(charredFloorImage, xCoord, yCoord, tileWidth, tileHeight, null);
          /*Graphics2D g2 = (Graphics2D) g;
          g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
          g2.drawImage(this.charredFloorImage, xCoord, yCoord, tileWidth, tileHeight, null);*/
        }
        else
        {
          g.drawImage(outsideImage, xCoord, yCoord, tileWidth, tileHeight, null);
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
    return background;
  }

  public BufferedImage getZombieSheet()
  {
    return zombieSheet;
  }

  public BufferedImage getPlayerSheetRun()
  {
    return playerSheetRun;
  }
}
