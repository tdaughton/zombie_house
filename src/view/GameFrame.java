package view; /**
 * Tess Daughton
 */
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame
{
  private final int SCREEN_WIDTH;
  private final int SCREEN_HEIGHT;




  public GameFrame()
  {
    super();
    Dimension userScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    this.SCREEN_WIDTH = (int) userScreenSize.getWidth();
    this.SCREEN_HEIGHT= (int) userScreenSize.getHeight();
    this.setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
  }


}
