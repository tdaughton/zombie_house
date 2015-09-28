/**
 * Created by Tess Daughton, September 20th 2015
 * TrapLoader accesses an animation sheet for the explosion of the trap and
 * divides it into subimages to play as a loop for the animation of the explosion
 * TrapLoader also loads an image of a "bomb" which is how the trap is currently being represented, prior to explosion of course.
 *
 * http://rswhite.de/dgame5/tutorial/images/explosion.png
 */

package Resources;

import model.Tile;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TrapLoader
{

  private static BufferedImage trap;
  private static BufferedImage explosionSheet;

  private static BufferedImage explosions[];

  private BufferedImage currentTrapImage;
  private int iterator=0;


  /**
   * Constructor
   * Loads the explosion animation sheet and the image for the un-detonated trap
   * Creates subimages from the explosion animation sheet
   * Sets a variable called currentTrapImage to keep track of which image is currently being used to represent
   * the trap (useful for when the animation is underway)
   */
  public TrapLoader(ImageLoader loader)
  {
    explosionSheet = loader.getExplosionSheet();
    trap = loader.getTrap();
    explosions = new BufferedImage[47];
    this.setExplosionSheet();
    this.currentTrapImage=trap;

  }

  /**
   * getter for trap buffered image
   * @return trap image
   */
  public BufferedImage getCurrentTrapImage()
  {
    return this.currentTrapImage;
  }

  /**
   * sections the animation sheet into individual frames to loop
   * during the explosion animation
   */
  private void setExplosionSheet()
      {
//    explosions[0] = explosionSheet.getSubimage(25,36, 211, 214);
//    explosions[1] = explosionSheet.getSubimage(279,36, 211, 214);
//    explosions[2] = explosionSheet.getSubimage(530,36, 211, 214);
//    explosions[3] = explosionSheet.getSubimage(782,36, 211, 214);
//    explosions[4] = explosionSheet.getSubimage(1023,36, 211, 214);
//    explosions[5] = explosionSheet.getSubimage(1273,36, 211, 214);
//    explosions[6] = explosionSheet.getSubimage(1519,36, 211, 214);
//    explosions[7] = explosionSheet.getSubimage(1769,36, 211, 214);
//    explosions[8] = explosionSheet.getSubimage(25, 270, 211, 214);
//    explosions[9] = explosionSheet.getSubimage(279, 270, 211, 214);
//    explosions[10] = explosionSheet.getSubimage(530, 270, 211, 214);
//    explosions[11] = explosionSheet.getSubimage(782, 270, 211, 214);
//    explosions[12] = explosionSheet.getSubimage(1023, 270, 211, 214);
//    explosions[13] = explosionSheet.getSubimage(1273, 270, 211, 214);
//    explosions[14] = explosionSheet.getSubimage(1519, 270, 211, 214);
//    explosions[15] = explosionSheet.getSubimage(1769, 270, 211, 214);
//    explosions[16] = explosionSheet.getSubimage(25, 518, 211, 214);
//    explosions[17] = explosionSheet.getSubimage(279, 518, 211, 214);
//    explosions[18] = explosionSheet.getSubimage(530, 518, 211, 214);
//    explosions[19] = explosionSheet.getSubimage(782, 518, 211, 214);
//    explosions[20] = explosionSheet.getSubimage(1023, 518, 211, 214);
//    explosions[21] = explosionSheet.getSubimage(1273, 518, 211, 214);
//    explosions[22] = explosionSheet.getSubimage(1519, 518, 211, 214);
//    explosions[23] = explosionSheet.getSubimage(1769, 518, 211, 214);
//    explosions[24] = explosionSheet.getSubimage(25, 765, 211, 214);
//    explosions[25] = explosionSheet.getSubimage(279, 765, 211, 214);
//    explosions[26] = explosionSheet.getSubimage(530, 765, 211, 214);
//    explosions[27] = explosionSheet.getSubimage(782, 765, 211, 214);
//    explosions[28] = explosionSheet.getSubimage(1023, 765, 211, 214);
//    explosions[29] = explosionSheet.getSubimage(1273, 765, 211, 214);
//    explosions[30] = explosionSheet.getSubimage(1519,765, 211, 214);
//    explosions[31]= explosionSheet.getSubimage(1769, 765, 211, 214);
//    explosions[32] = explosionSheet.getSubimage(25, 1022, 211, 214);
//    explosions[33] = explosionSheet.getSubimage(279, 1022, 211, 214);
//    explosions[34] = explosionSheet.getSubimage(530, 1022, 211, 214);
//    explosions[35] = explosionSheet.getSubimage(782, 1022, 211, 214);
//    explosions[36] = explosionSheet.getSubimage(1023, 1022, 211, 214);
//    explosions[37] = explosionSheet.getSubimage(1273, 1022, 211, 214);
//    explosions[38] = explosionSheet.getSubimage(1519, 1022, 211, 214);
//    explosions[39] = explosionSheet.getSubimage(1769, 1022, 211, 214);
//    explosions[40] = explosionSheet.getSubimage(25, 1273, 211, 214);
//    explosions[41] = explosionSheet.getSubimage(279, 1273, 211, 214);
//    explosions[42] = explosionSheet.getSubimage(530, 1273, 211, 214);
//    explosions[43] = explosionSheet.getSubimage(782, 1273, 211, 214);
//    explosions[44] = explosionSheet.getSubimage(1023, 1273, 211, 214);
//    explosions[45] = explosionSheet.getSubimage(1273, 1273, 211, 214);
//    explosions[46] = explosionSheet.getSubimage(1519, 1273, 211, 214);



    explosions[0] = explosionSheet.getSubimage(77,73, 438, 464);
    explosions[1] = explosionSheet.getSubimage(566,73, 438, 464);
    explosions[2] = explosionSheet.getSubimage(1066,73, 438, 464);
    explosions[3] = explosionSheet.getSubimage(1545,73, 438, 464);
    explosions[4] = explosionSheet.getSubimage(2059,73, 438, 464);
    explosions[5] = explosionSheet.getSubimage(2554,73, 438, 464);
    explosions[6] = explosionSheet.getSubimage(3046,73, 438, 464);
    explosions[7] = explosionSheet.getSubimage(3548,73, 438, 464);
    explosions[8] = explosionSheet.getSubimage(77, 547, 438, 464);
    explosions[9] = explosionSheet.getSubimage(566, 547, 438, 464);
    explosions[10] = explosionSheet.getSubimage(1066, 547, 438, 464);
    explosions[11] = explosionSheet.getSubimage(1545, 547, 438, 464);
    explosions[12] = explosionSheet.getSubimage(2059, 547, 438, 464);
    explosions[13] = explosionSheet.getSubimage(2554, 547, 438, 464);
    explosions[14] = explosionSheet.getSubimage(3046, 547, 438, 464);
    explosions[15] = explosionSheet.getSubimage(3548, 547, 438, 464);
    explosions[16] = explosionSheet.getSubimage(77, 1038, 438, 464);
    explosions[17] = explosionSheet.getSubimage(566, 1038, 438, 464);
    explosions[18] = explosionSheet.getSubimage(1066, 1038, 438, 464);
    explosions[19] = explosionSheet.getSubimage(1545, 1038, 438, 464);
    explosions[20] = explosionSheet.getSubimage(2059, 1038, 438, 464);
    explosions[21] = explosionSheet.getSubimage(2554, 1038, 438, 464);
    explosions[22] = explosionSheet.getSubimage(3046, 1038, 438, 464);
    explosions[23] = explosionSheet.getSubimage(3548, 1038, 438, 464);
    explosions[24] = explosionSheet.getSubimage(77, 1519, 438,464);
    explosions[25] = explosionSheet.getSubimage(566, 1519, 438, 464);
    explosions[26] = explosionSheet.getSubimage(1066, 1519, 438, 464);
    explosions[27] = explosionSheet.getSubimage(1545, 1519, 438, 464);
    explosions[28] = explosionSheet.getSubimage(2059, 1519, 438, 464);
    explosions[29] = explosionSheet.getSubimage(2554, 1519, 438, 464);
    explosions[30] = explosionSheet.getSubimage(3046,1519, 438, 464);
    explosions[31]= explosionSheet.getSubimage(3548, 1519, 438, 464);
    explosions[32] = explosionSheet.getSubimage(77, 2044, 438, 464);
    explosions[33] = explosionSheet.getSubimage(566, 2044, 438, 464);
    explosions[34] = explosionSheet.getSubimage(1066, 2044, 438, 464);
    explosions[35] = explosionSheet.getSubimage(1545, 2044, 438, 464);
    explosions[36] = explosionSheet.getSubimage(2059, 2044, 438, 464);
    explosions[37] = explosionSheet.getSubimage(2554, 2044, 438, 464);
    explosions[38] = explosionSheet.getSubimage(3046, 2044, 438, 464);
    explosions[39] = explosionSheet.getSubimage(3548, 2044, 438, 464);
    explosions[40] = explosionSheet.getSubimage(77, 2565, 438, 430);
    explosions[41] = explosionSheet.getSubimage(566, 2565, 438, 430);
    explosions[42] = explosionSheet.getSubimage(1066, 2565, 438, 430);
    explosions[43] = explosionSheet.getSubimage(1545, 2565 , 438, 430);
    explosions[44] = explosionSheet.getSubimage(2059, 2565, 438, 430);
    explosions[45] = explosionSheet.getSubimage(2554, 2565, 438, 430);
    explosions[46] = explosionSheet.getSubimage(3046, 2565, 438, 430);
    }

  /**
   * Cycles through the BufferedImages of the explosion animation using a class variable iterator
   */
  public void getExplosionEffect(Tile trap)
  {
    currentTrapImage = explosions[iterator];
    if (iterator < 46) iterator++;
    else
    { trap.getTrap().setExplosionFinished();
      iterator=0;
    }
  }
}
