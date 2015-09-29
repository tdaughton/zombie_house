/**
 * Created by Tess Daughton, September 13th 2015
 * ImageLoader loads the Tile images and creates a BufferedImage map
 *  http://forum.halomaps.org/index.cfm?page=topic&topicID=43046
 *  http://nutriphobia.com/images/img-5/white-ceramic-tile-texture-design-inspiration-2.jpg
 */

package Resources;

import model.*;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This class provides image loading and background generation methods for graphics that do
 * not change often (more than 1000 deltaSeconds in between changes (probably)).
 */
public class ImageLoader
{
  private static BufferedImage floorImage1;
  private static BufferedImage floorImage2;
  private static BufferedImage floorImage3;

  private static BufferedImage charredFloorImage;
  private static BufferedImage wallImage1;
  private static BufferedImage wallImage2;
  private static BufferedImage wallImage3;

  private static BufferedImage outsideImage;

  private static BufferedImage background;
  private static BufferedImage randomZombieSheet;
  private static BufferedImage lineZombieSheet;
  private static BufferedImage masterZombieSheet;
  private static BufferedImage exitImage;
  private static BufferedImage trap;
  private static BufferedImage explosionSheet;
  private static BufferedImage playerSheetRun;
  private static BufferedImage tombObstacleSheet;
  private static BufferedImage tombs[];
  private static BufferedImage treeObstacle;
  private static int levelCount = 0;

  int obstacleIterator = 0;

  private ZombieHouseModel zModel;
  private int tileWidth;
  private int tileHeight;

  /**
   * Constructor
   * ImageLoader reads in the three image files for tiles necessary to create a map
   * Then creates the background BufferedImage
   *
   * @param zModel  ZombieHouseModel
   * @param tWidth  Tile width (in pixels)
   * @param tHeight Tile height (in pixels)
   */
  public ImageLoader(ZombieHouseModel zModel, int tWidth, int tHeight)
  {
    this.zModel = zModel;
    levelCount = zModel.level;
    this.tileWidth = tWidth;
    this.tileHeight = tHeight;
    tombs = new BufferedImage[8];
    background = new BufferedImage(tileWidth * ZombieHouseModel.COLS, tileHeight * ZombieHouseModel.ROWS, BufferedImage.TYPE_INT_RGB);
  }

  /**
   * Uses a resource stream to load the tile images
   */
  public void readImages()
  {
    try
    {
        floorImage1 = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/floor2.jpg"));
        wallImage1 = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/wall.png"));
        outsideImage = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/deadgrass.png"));
        floorImage2 = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/floor3.png"));
        wallImage2 = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/wall2.png"));
        treeObstacle = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/obstacle2.png"));
        floorImage3 = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/floor4.jpg"));
        wallImage3 = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/wall4.png"));

        charredFloorImage = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/burntfloor.png"));
        exitImage = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/exit.png"));
        playerSheetRun = ImageIO.read(this.getClass().getResourceAsStream("/Resources/sprite_resources/running.png"));
        //randomZombieSheet = ImageIO.read(this.getClass().getResourceAsStream("/Resources/sprite_resources/zombie_sprite.png"));
        randomZombieSheet = ImageIO.read(this.getClass().getResourceAsStream("/Resources/sprite_resources/zombie.png"));
        lineZombieSheet = ImageIO.read(this.getClass().getResourceAsStream("/Resources/sprite_resources/line.png"));
        masterZombieSheet = ImageIO.read(this.getClass().getResourceAsStream("/Resources/sprite_resources/master.png"));
        trap = ImageIO.read(this.getClass().getResourceAsStream("/Resources/trap_resources/bomb.png"));
        explosionSheet = ImageIO.read(this.getClass().getResourceAsStream("/Resources/trap_resources/explosion.png"));
        tombObstacleSheet = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/tombs.png"));



    } catch (IOException e)
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
    levelCount = this.zModel.getLevel();
    //if(levelCount>1) this.setRandomObstacles();
    int xCoord = 0;
    int yCoord = 0;
    BufferedImage floorImage;
    BufferedImage wallImage;
    if(levelCount==1)
    { floorImage = floorImage1;
      wallImage = wallImage1;
    }
    else if(levelCount==2)
    {
      floorImage = floorImage2;
      wallImage = wallImage2;
    }
    else
    {
      floorImage = floorImage3;
      wallImage = wallImage3;
    }

    Graphics g = background.getGraphics();

    for (int i = 0; i < ZombieHouseModel.ROWS; i++)
    {
      for (int j = 0; j < ZombieHouseModel.COLS; j++)
      {
        if (grid[i][j] instanceof CharredFloorTile)
        {
          g.drawImage(charredFloorImage, xCoord, yCoord, tileWidth, tileHeight, null);
        }
        else if (grid[i][j] instanceof Floor && grid[i][j].hasExitFlag())
        {
          g.drawImage(floorImage, xCoord, yCoord, tileWidth, tileHeight, null);
          g.drawImage(exitImage, xCoord, yCoord, tileWidth, tileHeight, null);
        }
        else if (grid[i][j] instanceof Floor && !grid[i][j].isMovable())
        {
          g.drawImage(floorImage, xCoord, yCoord, tileWidth, tileHeight, null);
          //g.drawImage(this.getObstacle(), xCoord, yCoord, tileWidth, tileHeight, null);
        }
        else if (grid[i][j] instanceof Floor)
        {
          g.drawImage(floorImage, xCoord, yCoord, tileWidth, tileHeight, null);
        }
        else if (grid[i][j] instanceof Wall)
        {
          if (grid[i][j].isObstacle())
          {
            g
            .drawImage(tombObstacleSheet, xCoord, yCoord, tileWidth, tileHeight,
                      null);
          } else
          {
            g.drawImage(wallImage, xCoord, yCoord, tileWidth, tileHeight, null);
          }
//          g.setColor(Color.BLUE);
//          g.drawRect((int) grid[i][j].getX(), (int) grid[i][j].getY(), (int) grid[i][j].getWidth(), (int) grid[i][j]
//              .getHeight());

        }
        else if (grid[i][j] instanceof Outside)
        {
          g.drawImage(outsideImage, xCoord, yCoord, tileWidth, tileHeight, null);
        }
        xCoord += tileWidth;
      }
      yCoord += tileHeight;
      xCoord = 0;
    }
  }


  public void setTombSheet()
  {
    int height = 0;
    for (int i = 0; i < 8; i++)
    {
      tombs[i] = tombObstacleSheet.getSubimage(0, height, 50, 80);
      height += 126;
    }
  }

  /**
   * getter for background image
   *
   * @return background
   */
  public BufferedImage getBackground()
  {
    return background;
  }

  /**
   * getter for random walk zombie sheet
   *
   * @return sprite sheet
   */
  public BufferedImage getRandomZombieSheet()
  {
    return randomZombieSheet;
  }

  /**
   * getter for line walk zombie sheet
   *
   * @return sprite sheet
   */
  public BufferedImage getLineZombieSheet()
  {
    return lineZombieSheet;
  }

  /**
   * getter for master zombie sheet
   *
   * @return sprite sheet
   */
  public BufferedImage getMasterZombieSheet()
  {
    return masterZombieSheet;
  }

  /**
   * getter for trap explosion sheet
   *
   * @return sprite sheet
   */
  public BufferedImage getExplosionSheet()
  {
    return explosionSheet;
  }

  /**
   * getter for trap sprite
   *
   * @return sprite
   */
  public BufferedImage getTrap()
  {
    return trap;
  }

  /**
   * getter for player sprite sheet
   *
   * @return sprite sheet
   */
  public BufferedImage getPlayerSheetRun()
  {
    return playerSheetRun;
  }

//  public void setRandomObstacles()
//  {
//    for (Tile[] tiles : zModel.getGrid())
//      for (Tile tile : tiles)
//      {
//        obstacleIterator++;
//        if(obstacleIterator%7==0)
//        if (tile instanceof Floor && !tile.hasExitFlag() && tile.isMovable()) tile.setMovable(false);
//      }
//  }
//
//
//  public BufferedImage getObstacle()
//  {
//    obstacleIterator++;
//    if (obstacleIterator > 7) obstacleIterator = 0;
//    else if (obstacleIterator % 5 == 0) return treeObstacle;
//    else return tombs[obstacleIterator];
//    return tombs[0];
//  }
}
