package view;
/**
 * Created by L301126 on 9/8/15.
 */
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.net.URL;
import java.io.File;
import java.io.IOException;

public class TileImage
{

  private BufferedImage TILE_IMAGE;


  protected void setTileImage(String fileName)
  {
    try
    {

      TILE_IMAGE = ImageIO.read(ClassLoader.getSystemResource(fileName));
//      URL url = new URL("http://opengameart.org/sites/default/files/oga-textures/ancient%20flooring.jpg");
//      TILE_IMAGE = ImageIO.read(url);
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
