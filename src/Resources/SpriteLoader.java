/**
 * Created by Tess Daughton, September 13th 2015
 *
 * http://slick.ninjacave.com/forum/viewtopic.php?f=3&t=5364
 */

package Resources;

import model.GridOrientation;
import model.Player;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class SpriteLoader
{
  public BufferedImage playerSheetRun;
  private int iterator = 1;

  public BufferedImage run1;
  public BufferedImage run2;
  public BufferedImage run3;
  public BufferedImage run4;
  public BufferedImage run5;
  public BufferedImage run6;
  public BufferedImage run7;
  public BufferedImage run8;
  public BufferedImage run9;
  public BufferedImage run10;
  public BufferedImage run11;
  public BufferedImage run12;
  public BufferedImage run13;
  public BufferedImage run14;
  public BufferedImage run15;
  public BufferedImage currentPlayerImage;
  public BufferedImage subPlayerImage;

  public SpriteLoader()
  { this.loadSprites();
    this.setIndividualFrames();
    run6 = playerSheetRun.getSubimage(0, 144, 93, 110);
    currentPlayerImage = run6;
    subPlayerImage = currentPlayerImage;
  }

  private void loadSprites()
  {
    try
    {
      playerSheetRun = ImageIO.read(this.getClass().getResourceAsStream("/Resources/sprite_resources/running.png"));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  private void setIndividualFrames()
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

  public void getRotatingRun()
  {
    if (iterator == 1) currentPlayerImage = run1;
    else if (iterator == 2) currentPlayerImage = run2;
    else if (iterator == 3) currentPlayerImage = run3;
    else if (iterator == 4) currentPlayerImage = run4;
    else if (iterator == 5) currentPlayerImage = run5;
    else if (iterator == 6) currentPlayerImage = run6;
    else if (iterator == 7) currentPlayerImage = run7;
    else if (iterator == 8) currentPlayerImage = run8;
    else if (iterator == 9) currentPlayerImage = run9;
    else if (iterator == 10) currentPlayerImage = run10;
    else if (iterator == 11) currentPlayerImage = run11;
    else if (iterator == 12) currentPlayerImage = run12;
    else if (iterator == 13) currentPlayerImage = run13;
    else if (iterator == 14) currentPlayerImage = run14;
    else if (iterator == 15)
    {
      currentPlayerImage = run15;
      iterator=0;
    }

    iterator++;
    subPlayerImage = currentPlayerImage;
  }

  public BufferedImage getCurrentPlayerImage(Player sprite)
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
    else if(currentOrientation== GridOrientation.SOUTHWEST) rotateDegrees=-225;


    transform.rotate(Math.toRadians(rotateDegrees), currentPlayerImage.getWidth() / 2, currentPlayerImage
            .getHeight() / 2);
    AffineTransformOp rotate = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
    currentPlayerImage=rotate.filter(subPlayerImage,null);

    return this.currentPlayerImage;
  }
}



