
/**
 * Created by L301126 on 9/9/15.
 */
package view;

import model.Player;

public class PlayerImage extends Player
{

  private HouseImage house;
  public PlayerImage(int x, int y, int radius)
  { super(x, y, radius);
  }

  public PlayerImage(int x, int y, int radius, HouseImage house)
  { super(x,y,radius);
    this.house = house;
  }
  protected int getRelativeX()
  { return this.house.getCurrentScreenWidth()/2;
  }
  protected int getRelativeY()
  {
    return this.house.getCurrentScreenHeight()/2;
  }

}


