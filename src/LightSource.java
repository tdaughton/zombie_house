/**
 * Created by Tess Daughton, September 21st 2015
 * LightSource creates a Polygon to render a light source around the player
 */
import model.Player;
import model.Tile;
import java.awt.Polygon;
import java.awt.geom.Point2D;

public class LightSource extends Polygon
{
  private Tile[][] grid;
  private Tile playerTile;
  private double playerSight;
  private int playerX;
  private int playerY;
  private Polygon light;
  private Point2D center;
  private float radius;

  /**
   * Constructor
   * @param player  takes the Player object to obtain current tile and player's sight abilities for radii of polygon
   * @param map     takes the Tile[][] map to check for obstacles to create shadows around
   */
  public LightSource(Player player, Tile[][] map)
  {
    this.grid = map;
    this.playerTile = player.getCurrentTile();
    this.playerSight = player.getPlayerSight();
  }

  /**
   * Creates a polygon based on the player's sight and position in the board, as well as surrounding tiles
   * (i.e. possible obstacles and walls need shadows)
   * @param width  of the JPanel
   * @param height of the JPanel
   */
  protected void setPolygon(int width, int height)
  {
    this.light = new Polygon();
    this.playerX = width / 2;
    this.playerY = height / 2;
    this.center = new Point2D.Float(playerX, playerY);
    int frameWidth = width;
    int frameHeight = height;
    radius = (float) (playerSight * Math.sqrt(((Math.pow(playerTile.getHeight(), 2))
                                     + (Math.pow(playerTile.getWidth(), 2)))));

    for (int deg = 0; deg < 360; deg++)
    {
//      System.out.println("(" + (((int) (Math.cos(Math.toRadians(deg))) * radius) + playerX) + ", " + (((int) (Math.sin(Math.toRadians(deg))) * radius) + playerY) + ")");
      double x = playerX + (Math.cos(Math.toRadians(deg))) * radius;
      double y = playerY + (Math.sin(Math.toRadians(deg))) * radius;
      light.addPoint((int)x, (int) y);

    }
  }

  /**
   * getter for the ZombieHouseViewer class to utilize when rendering the light source
   * @return lightSource polygon
   */
  protected Polygon getPolygon()
  {
    return this.light;
  }

  /**
   * getter for the ZombieHouseViewer class to utilize when rendering the light source
   * @return radius
   */
  protected float getRadius()
  {
    return this.radius;
  }

  /**
   * getter for the ZombieHouseViewer class to utilize when rendering the light source
   * @return centerPoint
   */
  protected Point2D getCenter()
  {
    return this.center;
  }

}
