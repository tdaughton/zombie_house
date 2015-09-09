/**
 * Created by L301126 on 9/8/15.
 */

package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class HouseImage extends JPanel
{
  private BufferedImage houseGrid = new BufferedImage(GameFrame.MAX_SCREEN_WIDTH, GameFrame.MAX_SCREEN_HEIGHT,1);
  private Floor floor = new Floor();


  private void drawTile(Graphics g)
  {
    for(int x=0; x<houseGrid.getWidth(); x++)
      for(int y=0; y<houseGrid.getWidth(); y++)
        g.drawImage(floor.getTileImage(), x, y, null);
  }

  @Override
  public void paintComponent(Graphics g)
  {
      drawTile(g);
  }
}
