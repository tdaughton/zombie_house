package view;
/**
 * Tess Daughton
 */
import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class GameFrame extends JFrame implements ActionListener
{

  private Timer timer;
  private GamePanel gamePanel;

  public GameFrame(Tile[][] grid, BufferedImage gameBoard, Dimension userScreenSize)
  {
    super();


    gamePanel = new GamePanel(grid,gameBoard,userScreenSize);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setPreferredSize(userScreenSize);
    this.setSize(userScreenSize);
    this.setBackground(Color.black);
    this.getContentPane().add(gamePanel, BorderLayout.CENTER);
    this.setVisible(true);

    timer=new Timer(10,this);
    timer.start();

  }


  @Override
  public void actionPerformed(ActionEvent e)
  {
    gamePanel.setCurrentScreenWidth(this.getWidth());
    gamePanel.setCurrentScreenHeight(this.getHeight());
    repaint();
  }

}
