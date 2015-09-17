package view;
/**
 * Tess Daughton
 */

import model.Tile;

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

    this.gamePanel = new GamePanel(grid, gameBoard, userScreenSize);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setPreferredSize(userScreenSize);
    this.setSize(userScreenSize);
    this.setBackground(Color.black);
    this.getContentPane().add(gamePanel, BorderLayout.CENTER);
    this.setVisible(true);

    this.timer = new Timer(16, this);
    this.timer.start();
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    this.gamePanel.setCurrentScreenWidth(this.getWidth());
    this.gamePanel.setCurrentScreenHeight(this.getHeight());
    repaint();
  }
}
