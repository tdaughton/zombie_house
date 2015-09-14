package model;

/**
 * Created by Miri on 9/13/15.
 */
public class TileModel
{
  private int x, y;
  private int type;


  public TileModel(int x, int y)
  {
    this.x = x;
    this.y = y;

    type = 0; // 0 = wall
  }

  public int getType()
  {
    return type;
  }

  public void setType(int type)
  {
    this.type = type;
  }

  public int getX()
  {
    return x;

  }

  public int getY()
  {
    return y;
  }
}
