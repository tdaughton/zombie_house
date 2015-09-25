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
  private ImageLoader imageLoader;
  private BufferedImage playerSheetRun;
  private int playerIterator = 1;
  private int zombieIterator = 1;
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
  private BufferedImage subZombieImage;
  private BufferedImage currentZombieImage;
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
  public SpriteLoader(ImageLoader imageLoader)
  {
    this.imageLoader=imageLoader;
    this.zombieSheet = imageLoader.getZombieSheet();
    this.playerSheetRun = imageLoader.getPlayerSheetRun();
    this.setIndividualPlayerFrames();
    this.setIndividualZombieFrames();
    run6 = playerSheetRun.getSubimage(0, 144, 93, 110);
    currentPlayerImage = run6;
    subPlayerImage = currentPlayerImage;
    currentZombieImage = zombie1;
    subZombieImage=currentZombieImage;
  }

  /**
   * sections the animation sheet into individual frames to loop
   * during the movement animation
   */
  private void setIndividualPlayerFrames()
  { run1 = playerSheetRun.getSubimage(0, 0, 93, 100);
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
  { zombie1 = zombieSheet.getSubimage(28, 17, 105, 120);
    zombie2 = zombieSheet.getSubimage(241, 17, 105, 120);
    zombie3 = zombieSheet.getSubimage(454, 17, 105, 120);
    zombie4 = zombieSheet.getSubimage(664, 17, 105, 120);
    zombie5 = zombieSheet.getSubimage(877, 17, 105, 120);
    zombie6 = zombieSheet.getSubimage(1087, 17, 105, 120);
    zombie7 = zombieSheet.getSubimage(1296, 17, 105, 120);
    zombie8 = zombieSheet.getSubimage(1507, 17, 105, 120);
    zombie9 = zombieSheet.getSubimage(1718, 17, 105, 120);
    zombie10 = zombieSheet.getSubimage(28, 228, 105, 120);
    zombie11 = zombieSheet.getSubimage(241, 228, 105, 120);
    zombie12 = zombieSheet.getSubimage(454, 228, 105, 120);
    zombie13 = zombieSheet.getSubimage(664, 228, 105, 120);
    zombie14 = zombieSheet.getSubimage(877, 228, 105, 120);
    zombie15 = zombieSheet.getSubimage(1087, 228, 105, 120);
    zombie16 = zombieSheet.getSubimage(1296, 228, 105, 120);
    zombie17 = zombieSheet.getSubimage(1507, 228, 105, 120);
    zombie18 = zombieSheet.getSubimage(1718, 228, 105, 120);
    zombie19 = zombieSheet.getSubimage(28, 435, 105, 120);
    zombie20 = zombieSheet.getSubimage(241, 435, 105, 120);
    zombie21 = zombieSheet.getSubimage(454, 435, 105, 120);
    zombie22 = zombieSheet.getSubimage(664, 435, 105, 120);
    zombie23 = zombieSheet.getSubimage(877, 435, 105, 120);
    zombie24 = zombieSheet.getSubimage(1087, 435, 105, 120);
    zombie25 = zombieSheet.getSubimage(1296, 435, 105, 120);
    zombie26 = zombieSheet.getSubimage(1507, 435, 105, 120);
    zombie27 = zombieSheet.getSubimage(1718, 435, 105, 120);
    zombie28 = zombieSheet.getSubimage(28, 648, 105, 120);
    zombie29 = zombieSheet.getSubimage(241, 648, 105, 120);
    zombie30 = zombieSheet.getSubimage(454, 648, 105, 120);
  }

  /**
   * Cycles through the BufferedImages of the movement animation using a class variable iterator
   * changes the value of currentPlayerImage based on the current point in the animation
   */
  public void getRotatingRun()
  { if (playerIterator == 1) currentPlayerImage = run1;
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
      playerIterator = 0;
    }

    playerIterator++;
    subPlayerImage = currentPlayerImage;
  }

  /**
   * Cycles through the BufferedImages of the movement animation using a class variable iterator
   * changes the value of currentPlayerImage based on the current point in the animation
   */
  public void getRotatingZombieWalk()
  { if (zombieIterator == 1) currentZombieImage = zombie1;
    else if (zombieIterator == 2) currentZombieImage = zombie2;
    else if (zombieIterator == 3) currentZombieImage = zombie3;
    else if (zombieIterator == 4) currentZombieImage = zombie4;
    else if (zombieIterator == 5) currentZombieImage = zombie5;
    else if (zombieIterator == 6) currentZombieImage = zombie6;
    else if (zombieIterator == 7) currentZombieImage = zombie7;
    else if (zombieIterator == 8) currentZombieImage = zombie8;
    else if (zombieIterator == 9) currentZombieImage = zombie9;
    else if (zombieIterator == 10) currentZombieImage = zombie10;
    else if (zombieIterator == 11) currentZombieImage = zombie11;
    else if (zombieIterator == 12) currentZombieImage = zombie12;
    else if (zombieIterator == 13) currentZombieImage = zombie13;
    else if (zombieIterator == 14) currentZombieImage = zombie14;
    else if (zombieIterator == 15) currentZombieImage = zombie15;
    else if (zombieIterator == 16) currentZombieImage = zombie16;
    else if (zombieIterator == 17) currentZombieImage = zombie17;
    else if (zombieIterator == 18) currentZombieImage = zombie18;
    else if (zombieIterator == 19) currentZombieImage = zombie19;
    else if (zombieIterator == 20) currentZombieImage = zombie20;
    else if (zombieIterator == 21) currentZombieImage = zombie21;
    else if (zombieIterator == 22) currentZombieImage = zombie22;
    else if (zombieIterator == 23) currentZombieImage = zombie23;
    else if (zombieIterator == 24) currentZombieImage = zombie24;
    else if (zombieIterator == 25) currentZombieImage = zombie25;
    else if (zombieIterator == 26) currentZombieImage = zombie26;
    else if (zombieIterator == 27) currentZombieImage = zombie27;
    else if (zombieIterator == 28) currentZombieImage = zombie28;
    else if (zombieIterator == 29) currentZombieImage = zombie29;
    else if (zombieIterator == 30)
    {
      currentZombieImage = zombie30;
      zombieIterator = 0;
    }

    zombieIterator++;
    subZombieImage = currentZombieImage;
  }

  /**
   * currentPlayerImage is rotated depending on the map direction the player is facing
   *
   * @param sprite
   * @return Returns the currentPlayerImage based on the sprite's physical orientation in the map
   */
  public BufferedImage getCurrentPlayerImage(Player sprite)
  {
    Enum currentOrientation = sprite.getPlayerOrientation();
    AffineTransform transform = new AffineTransform();
    int rotateDegrees = 0;

    if (currentOrientation == GridOrientation.NORTH) rotateDegrees = 0;
    else if (currentOrientation == GridOrientation.SOUTH) rotateDegrees = 180;
    else if (currentOrientation == GridOrientation.EAST) rotateDegrees = 90;
    else if (currentOrientation == GridOrientation.WEST) rotateDegrees = 270;
    else if (currentOrientation == GridOrientation.NORTHEAST) rotateDegrees = 45;
    else if (currentOrientation == GridOrientation.SOUTHEAST) rotateDegrees = 135;
    else if (currentOrientation == GridOrientation.NORTHWEST) rotateDegrees = 315;
    else if (currentOrientation == GridOrientation.SOUTHWEST) rotateDegrees = 225;


    transform.rotate(Math.toRadians(rotateDegrees), 55, 55);
    AffineTransformOp rotate = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
    currentPlayerImage = rotate.filter(subPlayerImage, null);

    return this.currentPlayerImage;
  }


  /**
   * currentPlayerImage is rotated depending on the map direction the player is facing
   * @param sprite
   * @return Returns the currentPlayerImage based on the sprite's physical orientation in the map
   */
  public BufferedImage getCurrentZombieImage(Zombie sprite)
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
    currentZombieImage=rotate.filter(subZombieImage,null);

    return this.currentZombieImage;
  }
}



