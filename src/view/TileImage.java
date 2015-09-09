package view;
/**
 * Created by L301126 on 9/8/15.
 */

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class TileImage
{

  private BufferedImage TILE_IMAGE;


  protected void setTileImage(String fileName)
  {
      File file = new File(fileName);
    try
    {
      TILE_IMAGE = ImageIO.read(file);
    }
    catch (IOException e)
    {
      System.out.println("File cannot be found");
    }
  }

  protected BufferedImage getTileImage()
  {
    return this.TILE_IMAGE;
  }
}
