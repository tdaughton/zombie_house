/**
 * Created by Tess Daughton, September 13th 2015
 *
 * http://slick.ninjacave.com/forum/viewtopic.php?f=3&t=5364
 * http://opengameart.org/sites/default/files/styles/watermarked/public/orc_regular_0.png
 */

package Resources;

import model.GridOrientation;
import model.Zombie;
import model.Player;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class SpriteLoader
{
  private static BufferedImage playerSheetRun;
  private int playerIterator;
  private static BufferedImage[] run;
  private BufferedImage subPlayerImage;
  private BufferedImage currentPlayerImage;

  private static BufferedImage randomZombieSheet;
  private static BufferedImage lineZombieSheet;
  private static BufferedImage masterZombieSheet;

  private int randomZombieIterator;
  private static BufferedImage randomZombie[];
  private BufferedImage subRandomZombieImage;
  private BufferedImage currentRandomZombieImage;

  private int lineZombieIterator;
  private static BufferedImage lineZombie[];
  private BufferedImage subLineZombieImage;
  private BufferedImage currentLineZombieImage;

  private int masterZombieIterator;
  private static BufferedImage masterZombie[];
  private BufferedImage subMasterZombieImage;
  private BufferedImage currentMasterZombieImage;

  private static BufferedImage lineDeadZombie[];
  private static BufferedImage lineDeadSheet;


  /**
   * Constructor
   * Loads the running animation sheet and the idle image for the sprite
   * Creates subimages from the running animation sheet
   * Sets a variable called currentPlayerImage to keep track of which image is currently being used to represent
   * the player (useful for when the animation is underway)
   */
  public SpriteLoader(ImageLoader imageLoader)
  {
    randomZombieSheet = imageLoader.getRandomZombieSheet();
    lineZombieSheet = imageLoader.getLineZombieSheet();
    playerSheetRun = imageLoader.getPlayerSheetRun();
    masterZombieSheet=imageLoader.getMasterZombieSheet();
    lineDeadSheet = imageLoader.getLineZombieDeathSheet();

    run = new BufferedImage[15];
    randomZombie = new BufferedImage[30];
    lineZombie = new BufferedImage[30];
    masterZombie = new BufferedImage[30];
    lineDeadZombie = new BufferedImage[8];

    setIndividualPlayerFrames();
    setIndividualZombieFrames();
    randomZombieIterator = 0;
    lineZombieIterator = 0;
    playerIterator = 0;
    currentPlayerImage = run[5];
    subPlayerImage = currentPlayerImage;
    currentRandomZombieImage = randomZombie[0];
    subRandomZombieImage = currentRandomZombieImage;
    currentLineZombieImage = lineZombie[0];
    subLineZombieImage = currentLineZombieImage;
    currentMasterZombieImage = masterZombie[0];
    subMasterZombieImage = currentMasterZombieImage;
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
    randomZombie[0] = randomZombieSheet.getSubimage(28, 17, 105, 120);
    randomZombie[1] = randomZombieSheet.getSubimage(241, 17, 105, 120);
    randomZombie[2] = randomZombieSheet.getSubimage(454, 17, 105, 120);
    randomZombie[3] = randomZombieSheet.getSubimage(664, 17, 105, 120);
    randomZombie[4] = randomZombieSheet.getSubimage(877, 17, 105, 120);
    randomZombie[5] = randomZombieSheet.getSubimage(1087, 17, 105, 120);
    randomZombie[6] = randomZombieSheet.getSubimage(1296, 17, 105, 120);
    randomZombie[7] = randomZombieSheet.getSubimage(1507, 17, 105, 120);
    randomZombie[8] = randomZombieSheet.getSubimage(1718, 17, 105, 120);
    randomZombie[9] = randomZombieSheet.getSubimage(28, 228, 105, 120);
    randomZombie[10] = randomZombieSheet.getSubimage(241, 228, 105, 120);
    randomZombie[11] = randomZombieSheet.getSubimage(454, 228, 105, 120);
    randomZombie[12] = randomZombieSheet.getSubimage(664, 228, 105, 120);
    randomZombie[13] = randomZombieSheet.getSubimage(877, 228, 105, 120);
    randomZombie[14] = randomZombieSheet.getSubimage(1087, 228, 105, 120);
    randomZombie[15] = randomZombieSheet.getSubimage(1296, 228, 105, 120);
    randomZombie[16] = randomZombieSheet.getSubimage(1507, 228, 105, 120);
    randomZombie[17] = randomZombieSheet.getSubimage(1718, 228, 105, 120);
    randomZombie[18] = randomZombieSheet.getSubimage(28, 435, 105, 120);
    randomZombie[19] = randomZombieSheet.getSubimage(241, 435, 105, 120);
    randomZombie[20] = randomZombieSheet.getSubimage(454, 435, 105, 120);
    randomZombie[21] = randomZombieSheet.getSubimage(664, 435, 105, 120);
    randomZombie[22] = randomZombieSheet.getSubimage(877, 435, 105, 120);
    randomZombie[23] = randomZombieSheet.getSubimage(1087, 435, 105, 120);
    randomZombie[24] = randomZombieSheet.getSubimage(1296, 435, 105, 120);
    randomZombie[25] = randomZombieSheet.getSubimage(1507, 435, 105, 120);
    randomZombie[26] = randomZombieSheet.getSubimage(1718, 435, 105, 120);
    randomZombie[27] = randomZombieSheet.getSubimage(28, 648, 105, 120);
    randomZombie[28] = randomZombieSheet.getSubimage(241, 648, 105, 120);
    randomZombie[29] = randomZombieSheet.getSubimage(454, 648, 105, 120);

    lineZombie[0] = lineZombieSheet.getSubimage(11, 8, 95, 130);
    lineZombie[1] = lineZombieSheet.getSubimage(11, 8, 95, 130);

    lineZombie[2] = lineZombieSheet.getSubimage(188, 8, 95, 130);
    lineZombie[3] = lineZombieSheet.getSubimage(188, 8, 95, 130);

    lineZombie[4] = lineZombieSheet.getSubimage(376, 8, 95, 130);
    lineZombie[5] = lineZombieSheet.getSubimage(376, 8, 95, 130);

    lineZombie[6] = lineZombieSheet.getSubimage(732, 8, 95, 130);
    lineZombie[7] = lineZombieSheet.getSubimage(732, 8, 95, 130);

    lineZombie[8] = lineZombieSheet.getSubimage(917, 8, 95, 130);
    lineZombie[9] = lineZombieSheet.getSubimage(917, 8, 95, 130);

    lineZombie[10] = lineZombieSheet.getSubimage(1098, 8, 95, 130);
    lineZombie[11] = lineZombieSheet.getSubimage(1098, 8, 95, 130);

    lineZombie[12] = lineZombieSheet.getSubimage(1277, 8, 95, 130);
    lineZombie[13] = lineZombieSheet.getSubimage(1277, 8, 95, 130);

    lineZombie[14] = lineZombieSheet.getSubimage(1447, 8, 95, 130);
    lineZombie[15] = lineZombieSheet.getSubimage(1447, 8, 95, 130);

    lineZombie[16] = lineZombieSheet.getSubimage(1622, 8, 95, 130);
    lineZombie[17] = lineZombieSheet.getSubimage(1622, 8, 95, 130);

    lineZombie[18] = lineZombieSheet.getSubimage(1818, 8, 95, 130);
    lineZombie[19] = lineZombieSheet.getSubimage(1818, 8, 95, 130);

    lineZombie[20] = lineZombieSheet.getSubimage(2003, 8, 95, 130);
    lineZombie[21] = lineZombieSheet.getSubimage(2003, 8, 95, 130);

    lineZombie[22] = lineZombieSheet.getSubimage(2180, 8, 95, 130);
    lineZombie[23] = lineZombieSheet.getSubimage(2180, 8, 95, 130);

    lineZombie[24] = lineZombieSheet.getSubimage(2360, 8, 95, 130);
    lineZombie[25] = lineZombieSheet.getSubimage(2360, 8, 95, 130);

    lineZombie[26] = lineZombieSheet.getSubimage(2544, 8, 95, 130);
    lineZombie[27] = lineZombieSheet.getSubimage(2544, 8, 95, 130);

    lineZombie[28] = lineZombieSheet.getSubimage(2721, 8, 95, 130);
    lineZombie[29] = lineZombieSheet.getSubimage(2721, 8, 95, 130);

    lineZombie[15] = lineZombieSheet.getSubimage(2899, 8, 95, 130);
    lineZombie[15] = lineZombieSheet.getSubimage(2899, 8, 95, 130);



    masterZombie[0] = masterZombieSheet.getSubimage(28, 17, 105, 120);
    masterZombie[1] = masterZombieSheet.getSubimage(241, 17, 105, 120);
    masterZombie[2] = masterZombieSheet.getSubimage(454, 17, 105, 120);
    masterZombie[3] = masterZombieSheet.getSubimage(664, 17, 105, 120);
    masterZombie[4] = masterZombieSheet.getSubimage(877, 17, 105, 120);
    masterZombie[5] = masterZombieSheet.getSubimage(1087, 17, 105, 120);
    masterZombie[6] = masterZombieSheet.getSubimage(1296, 17, 105, 120);
    masterZombie[7] = masterZombieSheet.getSubimage(1507, 17, 105, 120);
    masterZombie[8] = masterZombieSheet.getSubimage(1718, 17, 105, 120);
    masterZombie[9] = masterZombieSheet.getSubimage(28, 228, 105, 120);
    masterZombie[10] = masterZombieSheet.getSubimage(241, 228, 105, 120);
    masterZombie[11] = masterZombieSheet.getSubimage(454, 228, 105, 120);
    masterZombie[12] = masterZombieSheet.getSubimage(664, 228, 105, 120);
    masterZombie[13] = masterZombieSheet.getSubimage(877, 228, 105, 120);
    masterZombie[14] = masterZombieSheet.getSubimage(1087, 228, 105, 120);
    masterZombie[15] = masterZombieSheet.getSubimage(1296, 228, 105, 120);
    masterZombie[16] = masterZombieSheet.getSubimage(1507, 228, 105, 120);
    masterZombie[17] = masterZombieSheet.getSubimage(1718, 228, 105, 120);
    masterZombie[18] = masterZombieSheet.getSubimage(28, 435, 105, 120);
    masterZombie[19] = masterZombieSheet.getSubimage(241, 435, 105, 120);
    masterZombie[20] = masterZombieSheet.getSubimage(454, 435, 105, 120);
    masterZombie[21] = masterZombieSheet.getSubimage(664, 435, 105, 120);
    masterZombie[22] = masterZombieSheet.getSubimage(877, 435, 105, 120);
    masterZombie[23] = masterZombieSheet.getSubimage(1087, 435, 105, 120);
    masterZombie[24] = masterZombieSheet.getSubimage(1296, 435, 105, 120);
    masterZombie[25] = masterZombieSheet.getSubimage(1507, 435, 105, 120);
    masterZombie[26] = masterZombieSheet.getSubimage(1718, 435, 105, 120);
    masterZombie[27] = masterZombieSheet.getSubimage(28, 648, 105, 120);
    masterZombie[28] = masterZombieSheet.getSubimage(241, 648, 105, 120);
    masterZombie[29] = masterZombieSheet.getSubimage(454, 648, 105, 120);


//    lineDeadZombie[0] = lineDeadSheet.getSubimage(0, 0, 232, 249);
//    lineDeadZombie[1] = lineDeadSheet.getSubimage(0, 0, 232, 249);
//    lineDeadZombie[2] = lineDeadSheet.getSubimage(256, 0, 232, 249);
//    lineDeadZombie[3] = lineDeadSheet.getSubimage(256, 0, 232, 249);
//
//    lineDeadZombie[4] = lineDeadSheet.getSubimage(511, 0, 232, 249);
//    lineDeadZombie[5] = lineDeadSheet.getSubimage(511, 0, 232, 249);
//    lineDeadZombie[6] = lineDeadSheet.getSubimage(758, 0, 232, 249);
//    lineDeadZombie[7] = lineDeadSheet.getSubimage(758, 0, 232, 249);
//
//    lineDeadZombie[8] = lineDeadSheet.getSubimage(1011, 0, 232, 249);
//    lineDeadZombie[9] = lineDeadSheet.getSubimage(1011, 0, 232, 249);
//
//    lineDeadZombie[10] = lineDeadSheet.getSubimage(1286, 0, 232, 249);
//    lineDeadZombie[11] = lineDeadSheet.getSubimage(1286, 0, 232, 249);
//
//    lineDeadZombie[12] = lineDeadSheet.getSubimage(1540, 0, 232, 249);
//    lineDeadZombie[13] = lineDeadSheet.getSubimage(1540, 0, 232, 249);
//
//    lineDeadZombie[14] = lineDeadSheet.getSubimage(1793, 0, 200, 249);
//    lineDeadZombie[15] = lineDeadSheet.getSubimage(1793, 0, 200, 249);


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
  public void getRotatingRandomZombieWalk()
  {
    currentRandomZombieImage = randomZombie[randomZombieIterator];
    randomZombieIterator++;
    if (randomZombieIterator > 29) randomZombieIterator = 0;
    subRandomZombieImage = currentRandomZombieImage;
  }

  public void getRotatingLineZombieWalk()
  {
    currentLineZombieImage = lineZombie[lineZombieIterator];
    lineZombieIterator++;
    if (lineZombieIterator > 29) lineZombieIterator = 0;
    subLineZombieImage = currentLineZombieImage;
  }

  public void getRotatingMasterZombieWalk()
  {
    currentMasterZombieImage = masterZombie[masterZombieIterator];
    masterZombieIterator++;
    if (masterZombieIterator > 29) masterZombieIterator = 0;
    subMasterZombieImage = currentMasterZombieImage;
  }


  public void playLineDeathSequence(Graphics g, Zombie zombie)
  {
    g.drawImage(masterZombie[masterZombieIterator], zombie.getX(), zombie.getY(), null);
    masterZombieIterator++;
    if (masterZombieIterator > 7) g.drawImage(masterZombie[7], zombie.getX(), zombie.getY(), null);
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
  public BufferedImage getCurrentRandomZombieImage(Zombie sprite)
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
    currentRandomZombieImage =rotate.filter(subRandomZombieImage,null);

    return currentRandomZombieImage;
  }

  public BufferedImage getCurrentLineZombieImage(Zombie sprite)
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
    currentLineZombieImage =rotate.filter(subLineZombieImage,null);

    return currentLineZombieImage;
  }

  public BufferedImage getCurrentMasterZombieImage(Zombie sprite)
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
    currentMasterZombieImage =rotate.filter(subMasterZombieImage,null);

    return currentMasterZombieImage;
  }
}
