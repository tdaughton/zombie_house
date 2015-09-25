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

  private BufferedImage trap;
  private BufferedImage explosionSheet;

  private BufferedImage explosion1;
  private BufferedImage explosion2;
  private BufferedImage explosion3;
  private BufferedImage explosion4;
  private BufferedImage explosion5;
  private BufferedImage explosion6;
  private BufferedImage explosion7;
  private BufferedImage explosion8;
  private BufferedImage explosion9;
  private BufferedImage explosion10;
  private BufferedImage explosion11;
  private BufferedImage explosion12;
  private BufferedImage explosion13;
  private BufferedImage explosion14;
  private BufferedImage explosion15;
  private BufferedImage explosion16;
  private BufferedImage explosion17;
  private BufferedImage explosion18;
  private BufferedImage explosion19;
  private BufferedImage explosion20;
  private BufferedImage explosion21;
  private BufferedImage explosion22;
  private BufferedImage explosion23;
  private BufferedImage explosion24;
  private BufferedImage explosion25;
  private BufferedImage explosion26;
  private BufferedImage explosion27;
  private BufferedImage explosion28;
  private BufferedImage explosion29;
  private BufferedImage explosion30;
  private BufferedImage explosion31;
  private BufferedImage explosion32;
  private BufferedImage explosion33;
  private BufferedImage explosion34;
  private BufferedImage explosion35;
  private BufferedImage explosion36;
  private BufferedImage explosion37;
  private BufferedImage explosion38;
  private BufferedImage explosion39;
  private BufferedImage explosion40;
  private BufferedImage explosion41;
  private BufferedImage explosion42;
  private BufferedImage explosion43;
  private BufferedImage explosion44;
  private BufferedImage explosion45;
  private BufferedImage explosion46;
  private BufferedImage explosion47;

  private BufferedImage currentTrapImage;
  private int iterator=0;


  /**
   * Constructor
   * Loads the explosion animation sheet and the image for the un-detonated trap
   * Creates subimages from the explosion animation sheet
   * Sets a variable called currentTrapImage to keep track of which image is currently being used to represent
   * the trap (useful for when the animation is underway)
   */
  public TrapLoader()
  { this.readImages();
    this.setExplosionSheet();
    this.currentTrapImage=trap;

  }

  /**
   * Uses a resource stream to load the animation sheet and trap image
   */
  private void readImages()
  {
    try
    {
      trap = ImageIO.read(this.getClass().getResourceAsStream("/Resources/trap_resources/bomb.png"));
      explosionSheet = ImageIO.read(this.getClass().getResourceAsStream("/Resources/trap_resources/explosion.png"));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
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
    explosion1 = explosionSheet.getSubimage(25,36, 211, 214);
    explosion2 = explosionSheet.getSubimage(279,36, 211, 214);
    explosion3 = explosionSheet.getSubimage(530,36, 211, 214);
    explosion4 = explosionSheet.getSubimage(782,36, 211, 214);
    explosion5 = explosionSheet.getSubimage(1023,36, 211, 214);
    explosion6 = explosionSheet.getSubimage(1273,36, 211, 214);
    explosion7 = explosionSheet.getSubimage(1519,36, 211, 214);
    explosion8 = explosionSheet.getSubimage(1769,36, 211, 214);
    explosion9 = explosionSheet.getSubimage(25, 270, 211, 214);
    explosion10 = explosionSheet.getSubimage(279, 270, 211, 214);
    explosion11 = explosionSheet.getSubimage(530, 270, 211, 214);
    explosion12 = explosionSheet.getSubimage(782, 270, 211, 214);
    explosion13 = explosionSheet.getSubimage(1023, 270, 211, 214);
    explosion14 = explosionSheet.getSubimage(1273, 270, 211, 214);
    explosion15 = explosionSheet.getSubimage(1519, 270, 211, 214);
    explosion16 = explosionSheet.getSubimage(1769, 270, 211, 214);
    explosion17 = explosionSheet.getSubimage(25, 518, 211, 214);
    explosion18 = explosionSheet.getSubimage(279, 518, 211, 214);
    explosion19 = explosionSheet.getSubimage(530, 518, 211, 214);
    explosion20 = explosionSheet.getSubimage(782, 518, 211, 214);
    explosion21 = explosionSheet.getSubimage(1023, 518, 211, 214);
    explosion22 = explosionSheet.getSubimage(1273, 518, 211, 214);
    explosion23 = explosionSheet.getSubimage(1519, 518, 211, 214);
    explosion24 = explosionSheet.getSubimage(1769, 518, 211, 214);
    explosion25 = explosionSheet.getSubimage(25, 765, 211, 214);
    explosion26 = explosionSheet.getSubimage(279, 765, 211, 214);
    explosion27 = explosionSheet.getSubimage(530, 765, 211, 214);
    explosion28 = explosionSheet.getSubimage(782, 765, 211, 214);
    explosion29 = explosionSheet.getSubimage(1023, 765, 211, 214);
    explosion30 = explosionSheet.getSubimage(1273, 765, 211, 214);
    explosion31 = explosionSheet.getSubimage(1519,765, 211, 214);
    explosion32 = explosionSheet.getSubimage(1769, 765, 211, 214);
    explosion33 = explosionSheet.getSubimage(25, 1022, 211, 214);
    explosion34 = explosionSheet.getSubimage(279, 1022, 211, 214);
    explosion35 = explosionSheet.getSubimage(530, 1022, 211, 214);
    explosion36 = explosionSheet.getSubimage(782, 1022, 211, 214);
    explosion37 = explosionSheet.getSubimage(1023, 1022, 211, 214);
    explosion38 = explosionSheet.getSubimage(1273, 1022, 211, 214);
    explosion39 = explosionSheet.getSubimage(1519, 1022, 211, 214);
    explosion40 = explosionSheet.getSubimage(1769, 1022, 211, 214);
    explosion41 = explosionSheet.getSubimage(25, 1273, 211, 214);
    explosion42 = explosionSheet.getSubimage(279, 1273, 211, 214);
    explosion43 = explosionSheet.getSubimage(530, 1273, 211, 214);
    explosion44 = explosionSheet.getSubimage(782, 1273, 211, 214);
    explosion45 = explosionSheet.getSubimage(1023, 1273, 211, 214);
    explosion46 = explosionSheet.getSubimage(1273, 1273, 211, 214);
    explosion47 = explosionSheet.getSubimage(1519, 1273, 211, 214);
    }

  /**
   * Cycles through the BufferedImages of the explosion animation using a class variable iterator
   */
  public void getExplosionEffect(Tile trap)
  {
    if (iterator == 1) currentTrapImage = explosion1;
    else if (iterator == 2) currentTrapImage = explosion2;
    else if (iterator == 3) currentTrapImage = explosion3;
    else if (iterator == 4) currentTrapImage = explosion4;
    else if (iterator == 5) currentTrapImage = explosion5;
    else if (iterator == 6) currentTrapImage = explosion6;
    else if (iterator == 7) currentTrapImage = explosion7;
    else if (iterator == 8) currentTrapImage = explosion8;
    else if (iterator == 9) currentTrapImage = explosion9;
    else if (iterator == 10) currentTrapImage = explosion10;
    else if (iterator == 11) currentTrapImage = explosion11;
    else if (iterator == 12) currentTrapImage = explosion12;
    else if (iterator == 13) currentTrapImage = explosion13;
    else if (iterator == 14) currentTrapImage = explosion14;
    else if (iterator == 15) currentTrapImage = explosion15;
    else if (iterator == 16) currentTrapImage = explosion16;
    else if (iterator == 17) currentTrapImage = explosion17;
    else if (iterator == 18) currentTrapImage = explosion18;
    else if (iterator == 19) currentTrapImage = explosion19;
    else if (iterator == 20) currentTrapImage = explosion20;
    else if (iterator == 21) currentTrapImage = explosion21;
    else if (iterator == 22) currentTrapImage = explosion22;
    else if (iterator == 23) currentTrapImage = explosion23;
    else if (iterator == 24) currentTrapImage = explosion24;
    else if (iterator == 25) currentTrapImage = explosion25;
    else if (iterator == 26) currentTrapImage = explosion26;
    else if (iterator == 27) currentTrapImage = explosion27;
    else if (iterator == 28) currentTrapImage = explosion28;
    else if (iterator == 29) currentTrapImage = explosion29;
    else if (iterator == 30) currentTrapImage = explosion30;
    else if (iterator == 31) currentTrapImage = explosion31;
    else if (iterator == 32) currentTrapImage = explosion32;
    else if (iterator == 33) currentTrapImage = explosion33;
    else if (iterator == 34) currentTrapImage = explosion34;
    else if (iterator == 35) currentTrapImage = explosion35;
    else if (iterator == 36) currentTrapImage = explosion36;
    else if (iterator == 37) currentTrapImage = explosion37;
    else if (iterator == 38) currentTrapImage = explosion38;
    else if (iterator == 39) currentTrapImage = explosion39;
    else if (iterator == 40) currentTrapImage = explosion40;
    else if (iterator == 41) currentTrapImage = explosion41;
    else if (iterator == 42) currentTrapImage = explosion42;
    else if (iterator == 43) currentTrapImage = explosion43;
    else if (iterator == 44) currentTrapImage = explosion44;
    else if (iterator == 45) currentTrapImage = explosion45;
    else if (iterator == 46)
    {
      currentTrapImage = explosion46;
      trap.getTrap().setExplosionFinished();

    }
    if (iterator < 47) iterator++;
    else if (iterator == 47)
    {
      currentTrapImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
      iterator = 0;

    }
  }
}
