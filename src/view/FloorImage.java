package view;

/**
 * Created by L301126 on 9/8/15.
 */
public class FloorImage extends TileImage
{

  String filename = "floor2.jpg";

  public FloorImage()
  {
    this.setTileImage(filename,true);
  }
  protected String getTileType()
  {
    return "Floor";
  }

}
