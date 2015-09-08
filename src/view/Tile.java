package view; /**
 * Created by L301126 on 9/8/15.
 */

import javax.swing.ImageIcon;

public class Tile
{

  public ImageIcon TILE_IMAGE;

  public void setTileImage(String fileName)
  {
    TILE_IMAGE = new ImageIcon(fileName);
  }

  public ImageIcon getTileImage()
  {
    return this.TILE_IMAGE;
  }




}
