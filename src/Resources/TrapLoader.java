package Resources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Tess Daughton, September 20th 2015
 */
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
  private BufferedImage currentTrapImage;
  private int iterator=0;


  public TrapLoader()
  {
    this.readImages();
    this.setExplosionSheet();
    currentTrapImage=trap;

  }


  private void readImages()
  {
    try
    {
      trap = ImageIO.read(this.getClass().getResourceAsStream("/Resources/trap_resources/bomb.png"));
      explosionSheet = ImageIO.read(this.getClass().getResourceAsStream("/Resources/trap_resources/explosion.png"));
    } catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  protected BufferedImage getTrap()
  {
    return this.trap;
  }


  private void setExplosionSheet()
  {
    explosion1 = explosionSheet.getSubimage(163,5, 135, 125);
    explosion2 = explosionSheet.getSubimage(319, 5, 135, 125);
    explosion3 = explosionSheet.getSubimage(472, 5, 135, 125);
    explosion4 = explosionSheet.getSubimage(613, 5, 135, 125);
    explosion5 = explosionSheet.getSubimage(761, 5, 135, 125);
    explosion6 = explosionSheet.getSubimage(907, 5, 135, 125);
    explosion7 = explosionSheet.getSubimage(1056, 5, 135, 125);
    explosion8 = explosionSheet.getSubimage(5, 163, 135, 125);
    explosion9 = explosionSheet.getSubimage(163, 163, 135, 125);
    explosion10 = explosionSheet.getSubimage(319, 163, 135, 125);
    explosion11 = explosionSheet.getSubimage(472, 163, 135, 125);
    explosion12 = explosionSheet.getSubimage(613, 163, 135, 125);
    explosion13 = explosionSheet.getSubimage(761, 163, 135, 125);
    explosion14 = explosionSheet.getSubimage(907, 163, 135, 125);
    explosion15 = explosionSheet.getSubimage(1056, 163, 135, 125);
    explosion16 = explosionSheet.getSubimage(5, 326, 135, 125);
    explosion17 = explosionSheet.getSubimage(163, 326, 135, 125);
    explosion18 = explosionSheet.getSubimage(319, 326, 135, 125);
    explosion19 = explosionSheet.getSubimage(472, 326, 135, 125);
    explosion20 = explosionSheet.getSubimage(613, 326, 135, 125);
    explosion21 = explosionSheet.getSubimage(761, 326, 135, 125);
    explosion22 = explosionSheet.getSubimage(907, 326, 135, 125);
    explosion23 = explosionSheet.getSubimage(1056, 326, 135, 125);
    explosion24 = explosionSheet.getSubimage(5, 478, 135, 125);
    explosion25 = explosionSheet.getSubimage(163, 478, 135, 125);
    explosion26 = explosionSheet.getSubimage(319, 478, 135, 125);
    explosion27 = explosionSheet.getSubimage(472, 478, 135, 125);
    explosion28 = explosionSheet.getSubimage(613, 478, 135, 125);
    explosion29 = explosionSheet.getSubimage(761, 478, 135, 125);
    explosion30 = explosionSheet.getSubimage(907, 478, 135, 125);
    }

  public BufferedImage getExplosionEffect()
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
    else if (iterator == 30)
    {
      currentTrapImage = explosion30;
      iterator=0;
    }
    iterator++;
    return currentTrapImage;
  }
}
