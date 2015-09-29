/**
 * Created by Tess Daughton, September 18th, 2015
 * The ZombieHouse panel class performs the following functions:
 * - drawing the relevant subimage of the BufferedImage background
 * - renders the Player in the center of the subimage
 * - renders a circular light source with radius of player sight around the player
 * - keeps track of the ZombieHouseFrame's current height and width
 */

import Resources.SpriteLoader;
import model.Player;
import model.Tile;
import model.Zombie;
import model.ZombieHouseModel;
import model.Trap;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
public class ZombieHouseViewer extends JPanel
{
  private int currentScreenWidth;
  private int currentScreenHeight;
  private BufferedImage background;
  private BufferedImage foreground1;
  private BufferedImage foreground2;
  private BufferedImage currentForegroundSubImage;
  private ZombieHouseModel zModel;
  private LightSource lightSource;
  private Player playerSprite;
  private int switchForeground = 0;
  private int negXOffSet = 0;
  private int negYOffSet = 0;
  private int backgroundWidth;
  private int backgroundHeight;
  private int xMin;
  private int xMax;
  private int yMin;
  private int yMax;

  /**
   * Full constructor.
   *
   * @param zModel         the ZombieHouseModel this viewer is supposed to visually present to the user
   * @param userScreenSize current resolution at launch
   */
  public ZombieHouseViewer(ZombieHouseModel zModel, Dimension userScreenSize)
  {
    super();
    this.zModel = zModel;
    this.currentScreenWidth = (int) userScreenSize.getWidth();
    this.currentScreenHeight = (int) userScreenSize.getHeight();
    ZombieHouseModel.imageLoader.createBackground();
    this.background = ZombieHouseModel.imageLoader.getBackground();
    this.backgroundWidth = background.getWidth();
    this.backgroundHeight = background.getHeight();
    this.foreground1 = new BufferedImage(backgroundWidth, backgroundHeight, BufferedImage.TYPE_INT_ARGB);
    this.foreground2 = new BufferedImage(backgroundWidth, backgroundHeight, BufferedImage.TYPE_INT_ARGB);
    this.playerSprite = this.zModel.getPlayer();
    this.lightSource = new LightSource(playerSprite, zModel.getGrid());
  }

  /**
   * Called inside actionPerformed in ZombieHouseFrame to periodically update frame width
   * Setter for window width
   *
   * @param x new width (in pixels)
   */
  protected void setCurrentScreenWidth(int x)
  {
    this.currentScreenWidth = x;
  }

  /**
   * Called inside actionPerformed in ZombieHouseFrame to periodically update frame height
   * Setter for window height
   *
   * @param y new height (in pixels)
   */
  protected void setCurrentScreenHeight(int y)
  {
    this.currentScreenHeight = y;
  }

  /**
   * This method calculates the viewport to display the area surrounding the Player's current position.
   *
   * @return BufferedImage of viewable play area
   */
  private BufferedImage getVisibleBuffer()
  {
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
    this.background = ZombieHouseModel.imageLoader.getBackground();

    return background.getSubimage(xMin, yMin, xMax, yMax);
  }

  private BufferedImage getVisibleTransparentBuffer(int xMin, int yMin, int xMax, int yMax)
  {
    BufferedImage currentForeground;
    if (switchForeground == 0)
    {
      currentForeground = this.foreground1;
      switchForeground = 1;
    }
    else
    {
      currentForeground = this.foreground2;
      switchForeground = 0;
    }
    return currentForeground.getSubimage(xMin, yMin, xMax, yMax);
  }

  /**
   * This method draws the graphical representation of the Player at the center of the screen
   *
   * @param g Graphics system reference
   */
  private void drawSprite(Graphics g)
  {
    SpriteLoader sprites = playerSprite.getFrames();
//    g.setColor(Color.CYAN);
//    g.drawOval(this.getWidth() / 2 - playerSprite.getRadius(), this.getHeight() / 2 - playerSprite.getRadius(), 2 * playerSprite.getRadius(), 2 * playerSprite.getRadius());
    g.drawImage(sprites.getCurrentPlayerImage(playerSprite), this.getWidth() / 2 - playerSprite.getRadius(), this.getHeight() / 2 - playerSprite.getRadius(), null);
  }

  /**
   * switches between foreground bufferedimages
   * clears out the past frame inside the foreground bufferedimage previously used
   * @return  g2 Graphics reference for relevant foreground buffer
   */
  private Graphics getForegroundGraphics()
  {
    Graphics2D g2;
    if (switchForeground == 1)
    {
      g2 = (Graphics2D) foreground1.getGraphics();
      g2.setBackground(new Color(255, 255, 255, 0));
      g2.clearRect(0, 0, backgroundWidth, backgroundHeight);
      g2 = (Graphics2D) foreground2.getGraphics();
    }
    else
    {
      g2 = (Graphics2D) foreground2.getGraphics();
      g2.setBackground(new Color(255, 255, 255, 0));
      g2.clearRect(0, 0, backgroundWidth, backgroundHeight);
      g2 = (Graphics2D) foreground1.getGraphics();
    }
    return g2;
  }

  /**
   * Renders the traps with the graphics object of the background BufferedImage
   * If a trap has a true value for boolean explosion triggered, an explosion animation is played
   * and the trap is removed from the abstract Tile tiles.
   * The background BufferedImage is re-created so that the detonated trap will no longer be rendered.
   *
   * @param g Graphics system reference
   */
  private void drawTraps(Graphics g)
  {
    Tile[][] tiles = zModel.getGrid();
    for (Tile[] tileRow : tiles)
    {
      for (Tile tile : tileRow)
      {
        if (tile.hasTrap())
        {
          if (tile.getTrap().explosionTriggered())
          {
            this.drawLightTrap(this.getForegroundGraphics(),lightSource,tile.getTrap());
            zModel.trapKill(tile);
            tile.getTrap().getTrapLoader().getExplosionEffect(tile);
            if (tile.getTrap() != null)
            {
              g.drawImage(tile.getTrap().getTrapLoader().getCurrentTrapImage(), (int) tile.getX()-100, (int) tile.getY()-100, null);
            }
            if(tile.getTrap().getExplosionFinished())
            {
              tile.removeTrap();
              zModel.setCharredTile(tile);
              ZombieHouseModel.imageLoader.createBackground();
              this.background = ZombieHouseModel.imageLoader.getBackground();
            }
          }
          else
          {
            g.drawImage(tile.getTrap().getTrapLoader().getCurrentTrapImage(), (int) tile.getCenterX()-30,
                        (int) tile.getCenterY()-30, null);
          }
        }
      }
    }
  }

  /**
   * Iterates through the ArrayList of zombies located inside of zModel and draws them at their current location inside the bufferedimage
   * on the relevant transparent buffer
   * @param g
   */
  private void drawZombies(Graphics g)
  {
    ArrayList<Zombie> zombies = zModel.getZombieList();
    if (zombies != null)
    {
      for (Zombie zombie : zombies)
      {
        SpriteLoader zombieSprite = zombie.getFrames();
        if(zombie.isDead()) zombieSprite.playLineDeathSequence(g,zombie);

        if (zombie.getZType().equals("Random"))
        {
//          g.setColor(Color.GREEN);
//          g.drawOval(zombie.getX() - zombie.getRadius(), zombie.getY() - zombie.getRadius(), 2 * zombie.getRadius(),
//              2 * zombie.getRadius());

          g.drawImage(zombieSprite.getCurrentRandomZombieImage(zombie), zombie.getX() - zombie.getRadius(),
                     zombie.getY() - zombie.getRadius(), null);
        }
        else if (zombie.getZType().equals("Line"))
        {
//          g.setColor(Color.RED);
//          g.drawOval(zombie.getX() - zombie.getRadius(), zombie.getY() - zombie.getRadius(), 2 * zombie.getRadius(),
//              2 * zombie.getRadius());

          g.drawImage(zombieSprite.getCurrentLineZombieImage(zombie), zombie.getX() - zombie.getRadius(),
                     zombie.getY() - zombie.getRadius(), null);
        }
        else
        {
//          g.setColor(Color.MAGENTA);
//          g.drawOval(zombie.getX() - zombie.getRadius(), zombie.getY() - zombie.getRadius(), 2 * zombie.getRadius(),
//                  2 * zombie.getRadius());
//
          g.drawImage(zombieSprite.getCurrentMasterZombieImage(zombie), zombie.getX() - zombie.getRadius(),
                  zombie.getY() - zombie.getRadius(), null);
        }
      }
    }
  }

  /**
   * Utilizes a LightSource object to obtain a Polygon around the Player
   * Fills Polygon in with RadialGradientPaint to render light source around Player
   * Utilizes Area class to paint the area outside of the Polygon black (to render darkness)
   * @param a  Graphics system reference
   */
  public void drawLight(Graphics a, LightSource lightSource)
  {
    Graphics2D g2 = (Graphics2D) a;
    lightSource.setPolygon(currentScreenWidth/2, currentScreenHeight/2);
    Polygon light = lightSource.getPolygon();
    Area darkness = new Area(new Rectangle(0,0,currentScreenWidth,currentScreenHeight));
    darkness.subtract(new Area(light));
    g2.setColor(Color.black);
    g2.fill(darkness);
    Color [] fade = {new Color(0f,0f,0f,0f),new Color(0f,0f,0f,1f), Color.BLACK};
    float [] fCen = {0.0f, 0.7f, 1.0f};
    RadialGradientPaint radialGradientPaint =  new RadialGradientPaint(lightSource.getCenter(), lightSource.getRadius(), fCen, fade,
        MultipleGradientPaint.CycleMethod.NO_CYCLE);
    g2.setPaint(radialGradientPaint);
    g2.draw(light);
    g2.fill(light);
  }

  public void drawLightTrap(Graphics a, LightSource lightsource, Trap trap)
  {
    Graphics2D g2 = (Graphics2D) a;
    lightSource.setPolygonTrap(trap.getX(), trap.getY(), trap);
    Polygon light = lightSource.getPolygon();
    Color [] fade = {new Color(0f,0f,0f,0f),new Color(0f,0f,0f,1f), Color.BLACK};
    float [] fCen = {0.0f, 0.7f, 1.0f};
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
    g.drawImage(currentForegroundSubImage, negXOffSet, negYOffSet, null);
    this.drawSprite(g);
    this.drawBufferedComponents(g);
    this.drawLight(g, lightSource);
  }
  public void drawBufferedComponents(Graphics g)
  {
    Graphics2D g2 = (Graphics2D) this.getForegroundGraphics();
    this.drawZombies(g2);
    this.drawTraps(g2);
  }


  public void restart(boolean nextLevel)
  {
    this.zModel.restart(nextLevel);
    Graphics2D g2 = (Graphics2D)background.getGraphics();
    g2.setBackground(new Color(255, 255, 255, 0));
    g2.clearRect(0, 0, backgroundWidth, backgroundHeight);
    ZombieHouseModel.imageLoader.createBackground();
    this.background = ZombieHouseModel.imageLoader.getBackground();
    this.playerSprite = zModel.getPlayer();
    this.lightSource = new LightSource(playerSprite, zModel.getGrid());
    repaint();
  }
}
