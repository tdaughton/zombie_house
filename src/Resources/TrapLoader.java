package Resources;

import model.Tile;
import model.Movable;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Graphics;

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

  public BufferedImage getTrap()
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

  public void getExplosionEffect(Graphics g, Tile tile, ImageLoader imageLoader, Movable mover )
  {
    if (iterator == 1) g.drawImage(explosion1, mover.getX(), mover.getY(), null);
    else if (iterator == 2) g.drawImage(explosion2,  mover.getX(), mover.getY(), null);
    else if (iterator == 3) g.drawImage(explosion3,  mover.getX(), mover.getY(), null);
    else if (iterator == 4) g.drawImage(explosion4,  mover.getX(), mover.getY(), null);
    else if (iterator == 5) g.drawImage(explosion5,  mover.getX(), mover.getY(), null);
    else if (iterator == 6) g.drawImage(explosion6,  mover.getX(), mover.getY(), null);
    else if (iterator == 7) g.drawImage(explosion7,  mover.getX(), mover.getY(), null);
    else if (iterator == 8) g.drawImage(explosion8,  mover.getX(), mover.getY(), null);
    else if (iterator == 9) g.drawImage(explosion9,  mover.getX(), mover.getY(), null);
    else if (iterator == 10) g.drawImage(explosion10,  mover.getX(), mover.getY(), null);
    else if (iterator == 11) g.drawImage(explosion11,  mover.getX(), mover.getY(), null);
    else if (iterator == 12) g.drawImage(explosion12,  mover.getX(), mover.getY(), null);
    else if (iterator == 13) g.drawImage(explosion13,  mover.getX(), mover.getY(), null);
    else if (iterator == 14) g.drawImage(explosion14,  mover.getX(), mover.getY(), null);
    else if (iterator == 15) g.drawImage(explosion15,  mover.getX(), mover.getY(), null);
    else if (iterator == 16) g.drawImage(explosion16,  mover.getX(), mover.getY(), null);
    else if (iterator == 17) g.drawImage(explosion17,  mover.getX(), mover.getY(), null);
    else if (iterator == 18) g.drawImage(explosion18,  mover.getX(), mover.getY(), null);
    else if (iterator == 19) g.drawImage(explosion19,  mover.getX(), mover.getY(), null);
    else if (iterator == 20) g.drawImage(explosion20,  mover.getX(), mover.getY(), null);
    else if (iterator == 21) g.drawImage(explosion21,  mover.getX(), mover.getY(), null);
    else if (iterator == 22) g.drawImage(explosion22,  mover.getX(), mover.getY(), null);
    else if (iterator == 23) g.drawImage(explosion23,  mover.getX(), mover.getY(), null);
    else if (iterator == 24) g.drawImage(explosion24,  mover.getX(), mover.getY(), null);
    else if (iterator == 25) g.drawImage(explosion25,  mover.getX(), mover.getY(), null);
    else if (iterator == 26) g.drawImage(explosion26,  mover.getX(), mover.getY(), null);
    else if (iterator == 27) g.drawImage(explosion27,  mover.getX(), mover.getY(), null);
    else if (iterator == 28) g.drawImage(explosion28,  mover.getX(), mover.getY(), null);
    else if (iterator == 29) g.drawImage(explosion29,  mover.getX(), mover.getY(), null);
    if(iterator<30)
    {
      iterator++;
      getExplosionEffect(g,tile,imageLoader, mover);
    }

    else if (iterator == 30)
    { g.drawImage(explosion30, (int) tile.getCenterX(), (int) tile.getCenterY(), null);
      tile.removeTrap();
      imageLoader.createBackground();
      iterator=0;
    }
  }
}
