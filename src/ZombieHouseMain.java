/**
 * Created by Tess Daughton, Sunday September 13th, 2015
 * This class will start the game and utilize a thread manager to run it.
 */
import model.*;
import view.*;
import Resources.ImageLoader;

import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
public class ZombieHouseMain
{
  private final static Dimension userScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
  protected final static int MAX_SCREEN_WIDTH = (int) userScreenSize.getWidth();
  protected final static int MAX_SCREEN_HEIGHT = (int) userScreenSize.getHeight();
  private final static int ROWS = 40;
  private final static int COLS = 40;

  public static void main(String[] args)
  {
    ImageLoader imageLoader = new ImageLoader(MAX_SCREEN_WIDTH, MAX_SCREEN_HEIGHT);
    ZombieHouseModel zModel = new ZombieHouseModel();
    Tile[][] map = zModel.getMap().getGrid();
    imageLoader.initializeMap(map,ROWS,COLS);
    BufferedImage background = imageLoader.getBackground();
    ZombieHouseViewer zViewer = new ZombieHouseViewer(map,background,userScreenSize);
  }
}
