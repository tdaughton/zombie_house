/**
 * Created by Tess Daughton, September 13th 2015
 *
 * http://slick.ninjacave.com/forum/viewtopic.php?f=3&t=5364
 */

package Resources;

import model.GridOrientation;
import model.Movable;
import model.Zombie;
import model.Player;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpriteLoader
{
  private BufferedImage playerSheetRun;
  private int playerIterator = 1;
  private int zombieIterator = 1;
  //public BufferedImage[] run;
  private BufferedImage run1;
  private BufferedImage run2;
  private BufferedImage run3;
  private BufferedImage run4;
  private BufferedImage run5;
  private BufferedImage run6;
  private BufferedImage run7;
  private BufferedImage run8;
  private BufferedImage run9;
  private BufferedImage run10;
  private BufferedImage run11;
  private BufferedImage run12;
  private BufferedImage run13;
  private BufferedImage run14;
  private BufferedImage run15;
  private BufferedImage currentPlayerImage;
  private BufferedImage subPlayerImage;

  private BufferedImage zombieSheet;
  private BufferedImage subZombiePlayerImage;
  private ArrayList<BufferedImage> currentZombieImage = new ArrayList<>(10);
  private BufferedImage zombie1;
  private BufferedImage zombie2;
  private BufferedImage zombie3;
  private BufferedImage zombie4;
  private BufferedImage zombie5;
  private BufferedImage zombie6;
  private BufferedImage zombie7;
  private BufferedImage zombie8;
  private BufferedImage zombie9;
  private BufferedImage zombie10;
  private BufferedImage zombie11;
  private BufferedImage zombie12;
  private BufferedImage zombie13;
  private BufferedImage zombie14;
  private BufferedImage zombie15;
  private BufferedImage zombie16;
  private BufferedImage zombie17;
  private BufferedImage zombie18;
  private BufferedImage zombie19;
  private BufferedImage zombie20;
  private BufferedImage zombie21;
  private BufferedImage zombie22;
  private BufferedImage zombie23;
  private BufferedImage zombie24;
  private BufferedImage zombie25;
  private BufferedImage zombie26;
  private BufferedImage zombie27;
  private BufferedImage zombie28;
  private BufferedImage zombie29;
  private BufferedImage zombie30;

  /**
   * Constructor
   * Loads the running animation sheet and the idle image for the sprite
   * Creates subimages from the running animation sheet
   * Sets a variable called currentPlayerImage to keep track of which image is currently being used to represent
   * the player (useful for when the animation is underway)
   */
  public SpriteLoader()
  {
    //run = new BufferedImage[15];
    this.loadSprites();
    this.setIndividualPlayerFrames();
    this.setIndividualZombieFrames();
    run6 = playerSheetRun.getSubimage(0, 144, 93, 110);
    //currentPlayerImage = run[5];
    currentPlayerImage = run6;
    subPlayerImage = currentPlayerImage;
   //TODO currently testing zombie sprites need to implement both
//    currentPlayerImage = zombie1;
//    subZombiePlayerImage=currentPlayerImage;
  }

  /**
   * Uses a resource stream to load the animation sheet and player image
   */
  private void loadSprites()
  {
    try
    {
      playerSheetRun = ImageIO.read(this.getClass().getResourceAsStream("/Resources/sprite_resources/running.png"));
      zombieSheet = ImageIO.read(this.getClass().getResourceAsStream("/Resources/sprite_resources/zombie_sprite.png"));

    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * sections the animation sheet into individual frames to loop
   * during the movement animation
   */
  private void setIndividualPlayerFrames()
  {
    /*run[0] = playerSheetRun.getSubimage(0, 0, 93, 100);
    run[1] = playerSheetRun.getSubimage(123, 0, 93, 110);
    run[2] = playerSheetRun.getSubimage(249, 0, 93, 110);
    run[3] = playerSheetRun.getSubimage(371, 0, 93, 110);
    run[4] = playerSheetRun.getSubimage(495, 0, 93, 110);
    run[5] = playerSheetRun.getSubimage(0, 144, 93, 110);
    run[6] = playerSheetRun.getSubimage(123, 144, 93, 110);
    run[7] = playerSheetRun.getSubimage(249, 144, 93, 110);
    run[8] = playerSheetRun.getSubimage(371, 144, 93, 110);
    run[9] = playerSheetRun.getSubimage(495, 144, 93, 110);
    run[10] = playerSheetRun.getSubimage(0, 279, 93, 110);
    run[11] = playerSheetRun.getSubimage(123, 279, 93, 110);
    run[12] = playerSheetRun.getSubimage(249, 279, 93, 110);
    run[13] = playerSheetRun.getSubimage(371, 279, 93, 110);
    run[14] = playerSheetRun.getSubimage(495, 279, 93, 110);*/
    run1 = playerSheetRun.getSubimage(0, 0, 93, 100);
    run2 = playerSheetRun.getSubimage(123, 0, 93, 110);
    run3 = playerSheetRun.getSubimage(249, 0, 93, 110);
    run4 = playerSheetRun.getSubimage(371, 0, 93, 110);
    run5 = playerSheetRun.getSubimage(495, 0, 93, 110);
    run7 = playerSheetRun.getSubimage(123, 144, 93, 110);
    run8 = playerSheetRun.getSubimage(249, 144, 93, 110);
    run9 = playerSheetRun.getSubimage(371, 144, 93, 110);
    run10 = playerSheetRun.getSubimage(495, 144, 93, 110);
    run11 = playerSheetRun.getSubimage(0, 279, 93, 110);
    run12 = playerSheetRun.getSubimage(123, 279, 93, 110);
    run13 = playerSheetRun.getSubimage(249, 279, 93, 110);
    run14 = playerSheetRun.getSubimage(371, 279, 93, 110);
    run15 = playerSheetRun.getSubimage(495, 279, 93, 110);
  }

  private void setIndividualZombieFrames()
  {
    zombie1 = zombieSheet.getSubimage(55,9, 110, 130);
    zombie2 = zombieSheet.getSubimage(241, 9, 110, 130);
    zombie3 = zombieSheet.getSubimage(456, 9, 110, 130);
    zombie4 = zombieSheet.getSubimage(666, 9, 110, 130);
    zombie5 = zombieSheet.getSubimage(878, 9, 110, 130);
    zombie6 = zombieSheet.getSubimage(1084, 9, 110, 130);
    zombie7 = zombieSheet.getSubimage(1299, 9, 110, 130);
    zombie8 = zombieSheet.getSubimage(1511, 9, 110, 130);
    zombie9 = zombieSheet.getSubimage(1714, 9, 110, 130);
    zombie10 = zombieSheet.getSubimage(55, 223, 110, 130);
    zombie11 = zombieSheet.getSubimage(241, 223, 110, 130);
    zombie12 = zombieSheet.getSubimage(456, 223, 110, 130);
    zombie13 = zombieSheet.getSubimage(666, 223, 110, 130);
    zombie14 = zombieSheet.getSubimage(878, 223, 110, 130);
    zombie15 = zombieSheet.getSubimage(1084, 223, 110, 130);
    zombie16 = zombieSheet.getSubimage(1299, 223, 110, 130);
    zombie17 = zombieSheet.getSubimage(1511, 223, 110, 130);
    zombie18 = zombieSheet.getSubimage(1714, 223, 110, 130);
    zombie19 = zombieSheet.getSubimage(55, 431, 110, 130);
    zombie20 = zombieSheet.getSubimage(241, 431, 110, 130);
    zombie21 = zombieSheet.getSubimage(456, 431, 110, 130);
    zombie22 = zombieSheet.getSubimage(666, 431, 110, 130);
    zombie23 = zombieSheet.getSubimage(878, 431, 110, 130);
    zombie24 = zombieSheet.getSubimage(1084, 431, 110, 130);
    zombie25 = zombieSheet.getSubimage(1299, 431, 110, 130);
    zombie26 = zombieSheet.getSubimage(1511, 431, 110, 130);
    zombie27 = zombieSheet.getSubimage(1714, 431, 110, 130);
    zombie28 = zombieSheet.getSubimage(55, 641, 110, 130);
    zombie29 = zombieSheet.getSubimage(241, 641, 110, 130);
    zombie30 = zombieSheet.getSubimage(456, 641, 110, 130);
  }

  /**
   * Cycles through the BufferedImages of the movement animation using a class variable iterator
   * changes the value of currentPlayerImage based on the current point in the animation
   */
  public void getRotatingRun()
  {
    //if (iterator > 14) iterator = 0;
    //currentPlayerImage = run[iterator];
    if (playerIterator == 1) currentPlayerImage = run1;
    else if (playerIterator == 2) currentPlayerImage = run2;
    else if (playerIterator == 3) currentPlayerImage = run3;
    else if (playerIterator == 4) currentPlayerImage = run4;
    else if (playerIterator == 5) currentPlayerImage = run5;
    else if (playerIterator == 6) currentPlayerImage = run6;
    else if (playerIterator == 7) currentPlayerImage = run7;
    else if (playerIterator == 8) currentPlayerImage = run8;
    else if (playerIterator == 9) currentPlayerImage = run9;
    else if (playerIterator == 10) currentPlayerImage = run10;
    else if (playerIterator == 11) currentPlayerImage = run11;
    else if (playerIterator == 12) currentPlayerImage = run12;
    else if (playerIterator == 13) currentPlayerImage = run13;
    else if (playerIterator == 14) currentPlayerImage = run14;
    else if (playerIterator == 15)
    {
      currentPlayerImage = run15;
      playerIterator=0;
    }

    playerIterator++;
    subPlayerImage = currentPlayerImage;
  }

  /**
   * Cycles through the BufferedImages of the movement animation using a class variable iterator
   * changes the value of currentPlayerImage based on the current point in the animation
   */
  public void getRotatingZombieWalk()
  {
    if (zombieIterator == 1) currentPlayerImage = zombie1;
    else if (zombieIterator == 2) currentPlayerImage = zombie2;
    else if (zombieIterator == 3) currentPlayerImage = zombie3;
    else if (zombieIterator == 4) currentPlayerImage = zombie4;
    else if (zombieIterator == 5) currentPlayerImage = zombie5;
    else if (zombieIterator == 6) currentPlayerImage = zombie6;
    else if (zombieIterator == 7) currentPlayerImage = zombie7;
    else if (zombieIterator == 8) currentPlayerImage = zombie8;
    else if (zombieIterator == 9) currentPlayerImage = zombie9;
    else if (zombieIterator == 10) currentPlayerImage = zombie10;
    else if (zombieIterator == 11) currentPlayerImage = zombie11;
    else if (zombieIterator == 12) currentPlayerImage = zombie12;
    else if (zombieIterator == 13) currentPlayerImage = zombie13;
    else if (zombieIterator == 14) currentPlayerImage = zombie14;
    else if (zombieIterator == 15) currentPlayerImage = zombie15;
    else if (zombieIterator == 16) currentPlayerImage = zombie16;
    else if (zombieIterator == 17) currentPlayerImage = zombie17;
    else if (zombieIterator == 18) currentPlayerImage = zombie18;
    else if (zombieIterator == 19) currentPlayerImage = zombie19;
    else if (zombieIterator == 20) currentPlayerImage = zombie20;
    else if (zombieIterator == 21) currentPlayerImage = zombie21;
    else if (zombieIterator == 22) currentPlayerImage = zombie22;
    else if (zombieIterator == 23) currentPlayerImage = zombie23;
    else if (zombieIterator == 24) currentPlayerImage = zombie24;
    else if (zombieIterator == 25) currentPlayerImage = zombie25;
    else if (zombieIterator == 26) currentPlayerImage = zombie26;
    else if (zombieIterator == 27) currentPlayerImage = zombie27;
    else if (zombieIterator == 28) currentPlayerImage = zombie28;
    else if (zombieIterator == 29) currentPlayerImage = zombie29;
    else if (zombieIterator == 30)
    {
      currentPlayerImage = zombie30;
      zombieIterator=0;
    }

    zombieIterator++;
    subPlayerImage = currentPlayerImage;
  }

  /**
   * currentPlayerImage is rotated depending on the map direction the player is facing
   * @param sprite
   * @return Returns the currentPlayerImage based on the sprite's physical orientation in the map
   */
  public BufferedImage getCurrentPlayerImage(Movable sprite)
  {
    Enum currentOrientation = sprite.getPlayerOrientation();
    AffineTransform transform = new AffineTransform();
    int rotateDegrees = 0;

    if(currentOrientation== GridOrientation.NORTH) rotateDegrees=0;
    else if(currentOrientation== GridOrientation.SOUTH) rotateDegrees=180;
    else if(currentOrientation== GridOrientation.EAST) rotateDegrees=90;
    else if(currentOrientation== GridOrientation.WEST) rotateDegrees=270;
    else if(currentOrientation== GridOrientation.NORTHEAST) rotateDegrees=45;
    else if(currentOrientation== GridOrientation.SOUTHEAST) rotateDegrees=135;
    else if(currentOrientation== GridOrientation.NORTHWEST) rotateDegrees=315;
    else if(currentOrientation== GridOrientation.SOUTHWEST) rotateDegrees=225;


    transform.rotate(Math.toRadians(rotateDegrees), 55, 55);
    AffineTransformOp rotate = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
    currentPlayerImage=rotate.filter(subPlayerImage,null);

    return this.currentPlayerImage;
  }
}



