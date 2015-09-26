/**
 * Created by Tess Daughton, September 21st 2015
 * LightSource creates a Polygon to render a light source around the player
 */
import model.*;

import java.awt.Polygon;
import java.awt.geom.Point2D;

public class LightSource extends Polygon
{
  private Tile[][] grid;
  private Tile playerTile;
  private double playerSight;
  private Player player;
  private int originX;
  private int originY;
  private Polygon light;
  private Point2D center;
  private float radius;

  /**
   * Constructor
   *
   * @param player takes the Player object to obtain current tile and player's sight abilities for radii of polygon
   * @param map    takes the Tile[][] map to check for obstacles to create shadows around
   */
  public LightSource(Player player, Tile[][] map, int x, int y)
  {
    this.originX = x;
    this.originY = y;
    this.grid = map;
    this.playerTile = player.getCurrentTile();
    this.player = player;
    this.playerSight = player.getPlayerSight();
  }

  /**
   * Creates a polygon based on the player's sight and position in the board, as well as surrounding tiles
   * (i.e. possible obstacles and walls need shadows)
   */
  protected void setPolygon(int width, int height)
  {
    this.playerTile = player.getCurrentTile();
    this.light = new Polygon();
    this.originX = width/2;
    this.originY = height/2;
    this.center = new Point2D.Float(originX, originY);
    this.radius = (float) (playerSight * Math.sqrt(((Math.pow(playerTile.getHeight(), 2))
        + (Math.pow(playerTile.getWidth(), 2)))));

    for (int deg = 0; deg < 360;deg++ )
    {
      double x = originX + (Math.cos(Math.toRadians(deg))) * radius;
      double y = originY + (Math.sin(Math.toRadians(deg))) * radius;
      light.addPoint((int) x, (int) y);
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
