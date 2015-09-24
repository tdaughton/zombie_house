/**
 * Created by Tess Daughton, September 18th, 2015
 * The ZombieHouse panel class performs the following functions:
 * - drawing the relevant subimage of the BufferedImage background
 * - renders the Player in the center of the subimage
 * - renders a circular light source with radius of player sight around the player
 * - keeps track of the ZombieHouseFrame's current height and width
 */

import Resources.*;
import model.*;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.Area;

public class ZombieHouseViewer extends JPanel
{
  private int currentScreenWidth;
  private int currentScreenHeight;
  private BufferedImage background;
  private BufferedImage foreground1;
  private BufferedImage foreground2;
  private BufferedImage currentForegroundSubImage;
  private ZombieHouseModel zModel;
  private ImageLoader imageLoader;
  private LightSource lightSource;
  private LightSource explosion;
  private SoundLoader soundLoader;
  private Player playerSprite;
  private int switchForeground = 0;
  private int negXOffSet = 0;
  private int negYOffSet = 0;
  private int backgroundWidth;
  private int backgroundHeight;
  /**
   * Full constructor.
   * @param zModel          the ZombieHouseModel this viewer is supposed to visually present to the user
   * @param userScreenSize  current resolution at launch
   */
  public ZombieHouseViewer(ZombieHouseModel zModel, Dimension userScreenSize)
  {
    super();
    this.zModel = zModel;
    this.currentScreenWidth = (int) userScreenSize.getWidth();
    this.currentScreenHeight = (int) userScreenSize.getHeight();
    this.imageLoader = new ImageLoader(this.zModel, this.currentScreenWidth, this.currentScreenHeight);
    this.background = imageLoader.getBackground();
    this.backgroundWidth = background.getWidth();
    this.backgroundHeight=background.getHeight();
    this.foreground1 = new BufferedImage(backgroundWidth,backgroundHeight, BufferedImage.TYPE_INT_ARGB);
    this.foreground2 = new BufferedImage(backgroundWidth,backgroundHeight, BufferedImage.TYPE_INT_ARGB);
    this.playerSprite = this.zModel.getPlayer();
    this.lightSource = new LightSource(playerSprite,zModel.getMap().getGrid(),backgroundWidth/2, backgroundHeight/2);
    this.soundLoader=new SoundLoader();

  }

  /**
   * Called inside actionPerformed in ZombieHouseFrame to periodically update frame width
   * Setter for window width
   * @param x  new width (in pixels)
   */
  protected void setCurrentScreenWidth(int x)
  {
    this.currentScreenWidth = x;
  }

  /**
   * Called inside actionPerformed in ZombieHouseFrame to periodically update frame height
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

    this.currentForegroundSubImage = this.getVisibleTransparentBuffer(xMin, yMin, xMax, yMax);
    return background.getSubimage(xMin, yMin, xMax, yMax);
  }

  private BufferedImage getVisibleTransparentBuffer(int xMin, int yMin, int xMax, int yMax)
  {
    BufferedImage currentForeground;
    if(switchForeground==0)
    { currentForeground=this.foreground1;
      switchForeground=1;
    }
    else
    { currentForeground = this.foreground2;
      switchForeground=0;
    }
    return currentForeground.getSubimage(xMin,yMin,xMax,yMax);
  }

  /**
   * This method draws the graphical representation of the Player at the center of the screen
   * @param g  Graphics system reference
   */
  private void drawSprite(Graphics g)
  { SpriteLoader sprites = playerSprite.getFrames();
    g.drawImage(sprites.getCurrentPlayerImage(playerSprite), this.getWidth() / 2 - playerSprite.getRadius(), this.getHeight() / 2 - playerSprite.getRadius(), null);
  }

  /**
   * Renders the traps with the graphics object of the background BufferedImage
   * If a trap has a true value for boolean explosion triggered, an explosion animation is played
   * and the trap is removed from the abstract Tile grid.
   * The background BufferedImage is re-created so that the detonated trap will no longer be rendered.
   * @param g   Graphics system reference
   */
  private void drawTraps(Graphics g)
  {
    Graphics2D g2;
    if(switchForeground==1)
    {
      g2 = (Graphics2D) foreground1.getGraphics();
      g2.setBackground(new Color(255,255,255,0));
      g2.clearRect(0,0,backgroundWidth,backgroundHeight);
      g = foreground2.getGraphics();
    }
    else
    { g2 = (Graphics2D) foreground2.getGraphics();
      g2.setBackground(new Color(255,255,255,0));
      g2.clearRect(0,0,backgroundWidth,backgroundHeight);
      g = foreground1.getGraphics();
    }
    Tile[][] tiles = zModel.getMap().getGrid();
    for (Tile[] tileRow : tiles)
    {
      for (Tile tile : tileRow)
      {
        if (tile.hasTrap())
        {
          if (tile.getTrap().explosionTriggered())
          {
            explosion = new LightSource(playerSprite,zModel.getMap().getGrid(),(int) tile.getCenterX(),(int) tile.getCenterY());
            drawLight(g,explosion);
            tile.getTrap().getTrapLoader().getExplosionEffect(tile);
            if(tile.getTrap()!=null)
            {
              g.drawImage(tile.getTrap().getTrapLoader().getCurrentTrapImage(), (int) tile.getX(), (int) tile.getY(), null);

            }
          }
          else
          {
            g.drawImage(tile.getTrap().getTrapLoader().getCurrentTrapImage(), (int) tile.getCenterX(), (int) tile.getCenterY(), null);
          }
        }
      }
    }
  }


  /**
   * Utilizes a LightSource object to obtain a Polygon around the Player
   * Fills Polygon in with RadialGradientPaint to render light source around Player
   * Utilizes Area class to paint the area outside of the Polygon black (to render darkness)
   * @param g  Graphics system reference
   */
  public void drawLight(Graphics g, LightSource lightSource)
  {
    Graphics2D g2 = (Graphics2D) g;
    lightSource.setPolygon(this.currentScreenWidth, this.currentScreenHeight);
    Polygon light = lightSource.getPolygon();
    Area darkness = new Area(new Rectangle(0,0,this.currentScreenWidth,this.currentScreenHeight));
    darkness.subtract(new Area(light));
    g2.setColor(Color.black);
    g2.fill(darkness);
    Color [] fade = {new Color(1f,1f,1f,0f), Color.BLACK};
    float [] fCen = {0.0f, 1.0f};
    RadialGradientPaint radialGradientPaint =  new RadialGradientPaint(lightSource.getCenter(), lightSource.getRadius(), fCen, fade,
        MultipleGradientPaint.CycleMethod.NO_CYCLE);
    g2.setPaint(radialGradientPaint);
    g2.draw(light);
    g2.fill(light);

  }




  /**
   * Overrides JPanel paintComponent to display gameplay elements
   * Draws visiblebuffer, the subimage of the background that the player is centered on
   * Renders the sprite in the center of this visible buffer
   * Draws a light source surrounding the sprite
   * Renders traps in the map
   * @param g
   */
  @Override
  public void paintComponent(Graphics g)
  {
    g.drawImage(this.getVisibleBuffer(), negXOffSet, negYOffSet, null);
    g.drawImage(currentForegroundSubImage, negXOffSet,negYOffSet,null);
    this.drawTraps(g);
    this.drawSprite(g);
    this.drawLight(g, lightSource);

  }
}
