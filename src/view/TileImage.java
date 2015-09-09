/**
 * Created by L301126 on 9/8/15.
 */
package view;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class TileImage
{

  private BufferedImage TILE_IMAGE;


  protected void setTileImage(String fileName)
  {
    try
    {

      TILE_IMAGE = ImageIO.read(ClassLoader.getSystemResource(fileName));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  protected BufferedImage getTileImage()
  {
    return this.TILE_IMAGE;
  }
}
