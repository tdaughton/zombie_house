package view;
/**
 * Tess Daughton
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements ActionListener
{
  private static Dimension userScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
  protected final static int MAX_SCREEN_WIDTH= (int) userScreenSize.getWidth();
  protected final static int MAX_SCREEN_HEIGHT=  (int) userScreenSize.getHeight();
  private Timer timer;
  private HouseImage grid;


  public GameFrame(int [][] intGrid)
  {
    super();
    grid = new HouseImage(intGrid);
    grid.setSize(userScreenSize);
    grid.setPreferredSize(userScreenSize);
    grid.setBackground(Color.black);


    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setPreferredSize(userScreenSize);
    this.setSize(userScreenSize);
    this.getContentPane().add(grid, BorderLayout.CENTER);
    this.setVisible(true);

    timer=new Timer(10,this);
    timer.start();

  }


  @Override
  public void actionPerformed(ActionEvent e)
  {
    grid.setCurrentScreenWidth(this.getWidth());
    grid.setCurrentScreenHeight(this.getHeight());
    repaint();
  }

}
