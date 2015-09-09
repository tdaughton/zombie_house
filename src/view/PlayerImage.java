package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by L301126 on 9/9/15.
 */
public class PlayerImage extends Movable implements KeyListener
{
  public PlayerImage(double x, double y, double radius)
  {
    super(x,y,radius);
  }

  @Override
  public void keyTyped(KeyEvent e)
  {
    if(e.getKeyCode()==KeyEvent.VK_UP) move(1,0);
    if(e.getKeyCode()==KeyEvent.VK_DOWN) move(-1,0);
    if(e.getKeyCode()==KeyEvent.VK_UP) move(1,0);
    if(e.getKeyCode()==KeyEvent.VK_LEFT) move(0,-1);
    if(e.getKeyCode()==KeyEvent.VK_LEFT) move(0,1);
  }

  @Override
  public void keyPressed(KeyEvent e)
  { if(e.getKeyCode()==KeyEvent.VK_UP) move(1,0);
    if(e.getKeyCode()==KeyEvent.VK_DOWN) move(-1,0);
    if(e.getKeyCode()==KeyEvent.VK_UP) move(1,0);
    if(e.getKeyCode()==KeyEvent.VK_LEFT) move(0,-1);
    if(e.getKeyCode()==KeyEvent.VK_LEFT) move(0,1);
  }

  @Override
  public void keyReleased(KeyEvent e) {}
}
