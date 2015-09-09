package view; /**
 * Tess Daughton
 */
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame
{
  private static Dimension userScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
  protected final static int MAX_SCREEN_WIDTH= (int) userScreenSize.getWidth();
  protected final static int MAX_SCREEN_HEIGHT=  (int) userScreenSize.getHeight();


  public GameFrame()
  {
    super();

    this.setSize(new Dimension(MAX_SCREEN_WIDTH, MAX_SCREEN_WIDTH));
    this.getContentPane().add(new HouseImage());

  }


}
