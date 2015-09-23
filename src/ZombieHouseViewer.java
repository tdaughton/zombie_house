/**
 * Created by L301126 on 9/18/15.
 */

import Resources.*;
import model.*;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ZombieHouseViewer extends JPanel
{
  //TODO:  establish screen width/height somehow
  private int currentScreenWidth;
  private int currentScreenHeight;
  private BufferedImage background;
  private ZombieHouseModel zModel;
  private ImageLoader imageLoader;
  private SoundLoader soundLoader;
  private TrapLoader trapLoader;
  private LightSource lightSource;
  private Player playerSprite;
  private int negXOffSet = 0;
  private int negYOffSet = 0;

  /**
   * Full constructor.
   * @param zModel          the ZombieHouseModel this viewer is supposed to visually present to the user
   * @param userScreenSize  current resolution at launch
   */
  public ZombieHouseViewer(ZombieHouseModel zModel, Dimension userScreenSize)
  {
    super();
    this.zModel = zModel;
    this.currentScreenWidth = (int)userScreenSize.getWidth();
    this.currentScreenHeight = (int)userScreenSize.getHeight();
    imageLoader = new ImageLoader(this.zModel, this.currentScreenWidth, this.currentScreenHeight);
    trapLoader=new TrapLoader();
    soundLoader=new SoundLoader();
    this.background = imageLoader.getBackground();
    this.playerSprite = this.zModel.getPlayer();
    this.lightSource = new LightSource(playerSprite,zModel.getMap().getGrid());

  }

  /**
   * Setter for window width
   * @param x  new width (in pixels)
   */
  protected void setCurrentScreenWidth(int x)
  {
    this.currentScreenWidth = x;
  }

  /**
   * Setter for window height
   * @param y  new height (in pixels)
   */
  protected void setCurrentScreenHeight(int y)
  {
    this.currentScreenHeight = y;
  }

  /**
   * This method calculates the viewport to display the area surrounding the Player's current position.
   * @return  BufferedImage of viewable play area
   */
  private BufferedImage getVisibleBuffer()
  {
    int xMin, xMax, yMin, yMax;
    this.negXOffSet = 0;
    this.negYOffSet = 0;

    xMin = playerSprite.getX() - (this.getWidth() / 2);
    if (xMin < 0)
    {
      negXOffSet = Math.abs(xMin);
      xMin = 0;
    }

    yMin = playerSprite.getY() - (this.getHeight() / 2);
    if (yMin < 0)
    {
      negYOffSet = Math.abs(yMin);
      yMin = 0;
    }

    xMax = xMin + this.getWidth();
    if ((xMin + xMax) >= background.getWidth()) xMax = background.getWidth() - xMin;

    yMax = yMin + this.getHeight();
    if ((yMin + yMax) >= background.getHeight()) yMax = background.getHeight() - yMin;

    background=imageLoader.getBackground();
    return background.getSubimage(xMin, yMin, xMax, yMax);
  }

  /**
   * This method draws the graphical representation of the Player at the center of the screen
   * @param g  Graphics system reference
   */
  private void drawSprite(Graphics g)
  {
    SpriteLoader sprites = playerSprite.getFrames();
    g.drawImage(sprites.getCurrentPlayerImage(playerSprite), this.getWidth() / 2, this.getHeight() / 2, null);

  }

  private void drawTraps(Graphics g)
  {
    g = background.getGraphics();
    Tile[][] tiles = zModel.getMap().getGrid();
    for (Tile[] tileRow : tiles)
    {
      for (Tile tile : tileRow)
      {
        if (tile.hasTrap())
        {
          g.drawImage(trapLoader.getTrap(), (int) tile.getCenterX(), (int) tile.getCenterY(), null);
          if (tile.getTrap().explosionTriggered())
          {
            trapLoader.getExplosionEffect(g, tile, imageLoader, tile.getTrap().getMovableTrigger());
          }
        }
      }
    }
  }

  private void drawLight(Graphics g)
  {
    lightSource.setPolygon(this.currentScreenWidth, this.currentScreenHeight);
    Polygon light = lightSource.getPolygon();
    Graphics2D g2 = (Graphics2D) g;
    g2.draw(light);
    GradientPaint gradientPaint=  new GradientPaint(this.getWidth()/2,this.getHeight()/2,
                                                Color.WHITE, this.getWidth()/2+300, this.getHeight()/2+300, Color.BLACK, true );
    g2.setPaint(gradientPaint);
    g2.fill(light);

  }

  /**
   * Overrides JPanel paintComponent to display gameplay elements
   * @param g
   */
  @Override
  public void paintComponent(Graphics g)
  {
    g.drawImage(this.getVisibleBuffer(), negXOffSet, negYOffSet, null);
    drawLight(g);
    drawSprite(g);
    drawTraps(g);
  }
}
