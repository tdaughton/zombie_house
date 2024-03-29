/**
 * Created by Tess Daughton, September 13th 2015
 * ImageLoader loads the Tile images and creates a BufferedImage map
 *  http://forum.halomaps.org/index.cfm?page=topic&topicID=43046
 *  http://nutriphobia.com/images/img-5/white-ceramic-tile-texture-design-inspiration-2.jpg
 */

package Resources;

import model.*;

import javax.imageio.ImageIO;
import java.awt.Color;
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
  private static BufferedImage floorImage4;
  private static BufferedImage floorImage5;

  private static BufferedImage charredFloorImage1;
  private static BufferedImage charredFloorImage2;
  private static BufferedImage charredFloorImage3;


  private static BufferedImage wallImage1;
  private static BufferedImage wallImage2;
  private static BufferedImage wallImage3;
  private static BufferedImage wallImage4;
  private static BufferedImage wallImage5;

  private static BufferedImage outsideImage1;
  private static BufferedImage outsideImage2;
  private static BufferedImage outsideImage3;


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
      floorImage2 = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/floor3.png"));
      floorImage3 = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/floor4.png"));
      floorImage4 = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/floor5.jpg"));
      floorImage5 = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/floor6.jpg"));

      wallImage1 = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/wall.png"));
      wallImage2 = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/wall2.png"));
      wallImage3 = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/wall4.png"));
      wallImage4 = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/wall5.jpg"));
      wallImage5 = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/wall6.jpg"));

      outsideImage1 = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/deadgrass.png"));
      outsideImage2 = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/outside4.jpg"));
      outsideImage3 = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/outside3.jpg"));



        treeObstacle = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/obstacle2.png"));

        charredFloorImage1 = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/burntfloor.png"));
        charredFloorImage2 = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/burntfloor2.png"));
      charredFloorImage3 = ImageIO.read(this.getClass().getResourceAsStream("/Resources/image_resources/burntfloor3.jpg"));


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
    BufferedImage charredFloorImage;
    BufferedImage outsideImage;
    if(levelCount==1)
    { floorImage = floorImage1;
      wallImage = wallImage1;
      outsideImage=outsideImage1;
      charredFloorImage=charredFloorImage1;
    }
    else if(levelCount==2)
    {
      floorImage = floorImage2;
      wallImage = wallImage2;
      outsideImage=outsideImage3;
      charredFloorImage=charredFloorImage2;
    }
    else if(levelCount==3)
    {
      floorImage = floorImage3;
      wallImage = wallImage3;
      outsideImage = outsideImage2;
      charredFloorImage=charredFloorImage2;
    }
    else if(levelCount==4)
    {
      floorImage = floorImage4;
      wallImage = wallImage4;
      outsideImage = outsideImage3;
      charredFloorImage=charredFloorImage2;
    }
    else
    {
      floorImage = floorImage5;
      wallImage=wallImage5;
      outsideImage = outsideImage2;
      charredFloorImage=charredFloorImage3;


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
          g.drawImage(floorImage, xCoord, yCoord, tileWidth, tileHeight, null);
          g.drawImage(wallImage, xCoord, yCoord, tileWidth, tileHeight, null);
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
