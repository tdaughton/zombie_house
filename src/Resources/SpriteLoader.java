/**
 * Created by Tess Daughton, September 13th 2015
 *
 * http://slick.ninjacave.com/forum/viewtopic.php?f=3&t=5364
 */

package Resources;

import model.GridOrientation;
import model.Zombie;
import model.Player;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class SpriteLoader
{
  private static BufferedImage playerSheetRun;
  private int playerIterator;
  private static BufferedImage[] run;
  private BufferedImage subPlayerImage;
  private BufferedImage currentPlayerImage;

  private static BufferedImage zombieSheet;
  private int zombieIterator;
  private static BufferedImage zombie[];
  private BufferedImage subZombieImage;
  private BufferedImage currentZombieImage;

  /**
   * Constructor
   * Loads the running animation sheet and the idle image for the sprite
   * Creates subimages from the running animation sheet
   * Sets a variable called currentPlayerImage to keep track of which image is currently being used to represent
   * the player (useful for when the animation is underway)
   */
  public SpriteLoader(ImageLoader imageLoader)
  {
    zombieSheet = imageLoader.getZombieSheet();
    playerSheetRun = imageLoader.getPlayerSheetRun();
    run = new BufferedImage[15];
    zombie = new BufferedImage[30];
    setIndividualPlayerFrames();
    setIndividualZombieFrames();
    zombieIterator = 0;
    playerIterator = 0;
    currentPlayerImage = run[5];
    subPlayerImage = currentPlayerImage;
    currentZombieImage = zombie[0];
    subZombieImage = currentZombieImage;
  }

  /**
   * sections the animation sheet into individual frames to loop
   * during the movement animation
   */
  private void setIndividualPlayerFrames()
  {
    run[0] = playerSheetRun.getSubimage(0, 0, 93, 100);
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
    run[14] = playerSheetRun.getSubimage(495, 279, 93, 110);
  }

  private void setIndividualZombieFrames()
  {
    zombie[0] = zombieSheet.getSubimage(28, 17, 105, 120);
    zombie[1] = zombieSheet.getSubimage(241, 17, 105, 120);
    zombie[2] = zombieSheet.getSubimage(454, 17, 105, 120);
    zombie[3] = zombieSheet.getSubimage(664, 17, 105, 120);
    zombie[4] = zombieSheet.getSubimage(877, 17, 105, 120);
    zombie[5] = zombieSheet.getSubimage(1087, 17, 105, 120);
    zombie[6] = zombieSheet.getSubimage(1296, 17, 105, 120);
    zombie[7] = zombieSheet.getSubimage(1507, 17, 105, 120);
    zombie[8] = zombieSheet.getSubimage(1718, 17, 105, 120);
    zombie[9] = zombieSheet.getSubimage(28, 228, 105, 120);
    zombie[10] = zombieSheet.getSubimage(241, 228, 105, 120);
    zombie[11] = zombieSheet.getSubimage(454, 228, 105, 120);
    zombie[12] = zombieSheet.getSubimage(664, 228, 105, 120);
    zombie[13] = zombieSheet.getSubimage(877, 228, 105, 120);
    zombie[14] = zombieSheet.getSubimage(1087, 228, 105, 120);
    zombie[15] = zombieSheet.getSubimage(1296, 228, 105, 120);
    zombie[16] = zombieSheet.getSubimage(1507, 228, 105, 120);
    zombie[17] = zombieSheet.getSubimage(1718, 228, 105, 120);
    zombie[18] = zombieSheet.getSubimage(28, 435, 105, 120);
    zombie[19] = zombieSheet.getSubimage(241, 435, 105, 120);
    zombie[20] = zombieSheet.getSubimage(454, 435, 105, 120);
    zombie[21] = zombieSheet.getSubimage(664, 435, 105, 120);
    zombie[22] = zombieSheet.getSubimage(877, 435, 105, 120);
    zombie[23] = zombieSheet.getSubimage(1087, 435, 105, 120);
    zombie[24] = zombieSheet.getSubimage(1296, 435, 105, 120);
    zombie[25] = zombieSheet.getSubimage(1507, 435, 105, 120);
    zombie[26] = zombieSheet.getSubimage(1718, 435, 105, 120);
    zombie[27] = zombieSheet.getSubimage(28, 648, 105, 120);
    zombie[28] = zombieSheet.getSubimage(241, 648, 105, 120);
    zombie[29] = zombieSheet.getSubimage(454, 648, 105, 120);
  }

  /**
   * Cycles through the BufferedImages of the movement animation using a class variable iterator
   * changes the value of currentPlayerImage based on the current point in the animation
   */
  public void getRotatingRun()
  {
    currentPlayerImage = run[playerIterator];
    playerIterator++;
    if (playerIterator > 14) playerIterator = 0;
    subPlayerImage = currentPlayerImage;
  }

  /**
   * Cycles through the BufferedImages of the movement animation using a class variable iterator
   * changes the value of currentPlayerImage based on the current point in the animation
   */
  public void getRotatingZombieWalk()
  {
    currentZombieImage = zombie[zombieIterator];
    zombieIterator++;
    if (zombieIterator > 29) zombieIterator = 0;
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

    return currentPlayerImage;
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

    if(currentOrientation == GridOrientation.NORTH) rotateDegrees=0;
    else if(currentOrientation == GridOrientation.SOUTH) rotateDegrees=180;
    else if(currentOrientation == GridOrientation.EAST) rotateDegrees=90;
    else if(currentOrientation == GridOrientation.WEST) rotateDegrees=270;
    else if(currentOrientation == GridOrientation.NORTHEAST) rotateDegrees=45;
    else if(currentOrientation == GridOrientation.SOUTHEAST) rotateDegrees=135;
    else if(currentOrientation == GridOrientation.NORTHWEST) rotateDegrees=315;
    else if(currentOrientation == GridOrientation.SOUTHWEST) rotateDegrees=225;


    transform.rotate(Math.toRadians(rotateDegrees), 60, 60);
    AffineTransformOp rotate = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
    currentZombieImage=rotate.filter(subZombieImage,null);

    return currentZombieImage;
  }
}
