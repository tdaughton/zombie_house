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

/**
 * This class provides image loading and background generation methods for graphics that do
 * not change often (more than 1000 deltaSeconds in between changes (probably)).
 */
public class ImageLoader
{
  private static BufferedImage floorImage;
  private static BufferedImage charredFloorImage;
  private static BufferedImage wallImage;
  private static BufferedImage outsideImage;
  private static BufferedImage background;
  private static BufferedImage randomZombieSheet;
  private static BufferedImage lineZombieSheet;
  private static BufferedImage masterZombieSheet;
  private static BufferedImage lineZombieDeathSheet;
  private static BufferedImage trap;
  private static BufferedImage explosionSheet;
  private static BufferedImage playerSheetRun;

  private ZombieHouseModel zModel;
  private int tileWidth;
  private int tileHeight;

  /**
   * Constructor
   * ImageLoader reads in the three image files for tiles necessary to create a map
   * Then creates the background BufferedImage
   * @param zModel   ZombieHouseModel
   * @param tWidth   Tile width (in pixels)
   * @param tHeight  Tile height (in pixels)
   */
  public ImageLoader(ZombieHouseModel zModel, int tWidth, int tHeight)
  {
    this.zModel = zModel;
    this.tileWidth = tWidth;
    this.tileHeight = tHeight;
    background = new BufferedImage(tileWidth * ZombieHouseModel.COLS, tileHeight * ZombieHouseModel.ROWS, BufferedImage.TYPE_INT_RGB);
    this.readImages();
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
      randomZombieSheet = ImageIO.read(this.getClass().getResourceAsStream("/Resources/sprite_resources/zombie_sprite.png"));
      lineZombieSheet = ImageIO.read(this.getClass().getResourceAsStream("/Resources/sprite_resources/line.png"));
      masterZombieSheet = ImageIO.read(this.getClass().getResourceAsStream("/Resources/sprite_resources/master.png"));
      trap = ImageIO.read(this.getClass().getResourceAsStream("/Resources/trap_resources/bomb.png"));
      explosionSheet = ImageIO.read(this.getClass().getResourceAsStream("/Resources/trap_resources/explosion.png"));
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

    for (int i = 0; i < ZombieHouseModel.ROWS; i++)
    {
      for (int j = 0; j < ZombieHouseModel.COLS; j++)
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
   * @return  background
   */
  public BufferedImage getBackground()
  {
    return background;
  }

  /**
   * getter for random walk zombie sheet
   * @return  sprite sheet
   */
  public BufferedImage getRandomZombieSheet()
  {
    return randomZombieSheet;
  }

  /**
   * getter for line walk zombie sheet
   * @return  sprite sheet
   */
  public BufferedImage getLineZombieSheet()
  {
    return lineZombieSheet;
  }

  /**
   * getter for master zombie sheet
   * @return  sprite sheet
   */
  public BufferedImage getMasterZombieSheet()
  {
    return masterZombieSheet;
  }

  /**
   * getter for zombie death sheet
   * @return  sprite sheet
   */
  public BufferedImage getLineZombieDeathSheet()
  {
    return lineZombieDeathSheet;
  }

  /**
   * getter for trap explosion sheet
   * @return  sprite sheet
   */
  public BufferedImage getExplosionSheet()
  {
    return explosionSheet;
  }

  /**
   * getter for trap sprite
   * @return  sprite
   */
  public BufferedImage getTrap()
  {
    return trap;
  }

  /**
   * getter for player sprite sheet
   * @return  sprite sheet
   */
  public BufferedImage getPlayerSheetRun()
  {
    return playerSheetRun;
  }
}
