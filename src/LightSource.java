/**
 * Created by Tess Daughton, September 21st 2015
 * LightSource creates a Polygon to render a light source around the player
 */
import model.*;

import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.Point;

public class LightSource extends Polygon
{
  private Tile[][] grid;
  private int gridRow;
  private int gridCol;
  private Tile playerTile;
  private double playerSight;
  private Player player;
  private Polygon light;
  private Point2D center;
  private float radius;

  /**
   * Constructor
   *
   * @param player takes the Player object to obtain current tile and player's sight abilities for radii of polygon
   * @param map    takes the Tile[][] map to check for obstacles to create shadows around
   */
  public LightSource(Player player, Tile[][] map)
  {
    this.grid = map;
    this.playerTile = player.getCurrentTile();
    this.gridRow = playerTile.getGridRow();
    this.gridCol = playerTile.getGridCol();
    this.player = player;
    this.playerSight = player.getPlayerSight();
  }

  /**
   * Creates a polygon based on the player's sight and position in the board, as well as surrounding tiles
   * (i.e. possible obstacles and walls need shadows)
   */
  protected void setPolygon(int centerX, int centerY)
  {
    double tileRadius = Math.sqrt(((Math.pow(playerTile.getHeight(), 2))
        + (Math.pow(playerTile.getWidth(), 2))));
    this.radius = (float) (playerSight * tileRadius);
    this.light = new Polygon();
    double relativeX = player.getX();
    double relativeY = player.getY();
    this.center = new Point2D.Float(centerX, centerY);
    double x;
    double y;
    double xMult;
    double yMult;

    for (int deg = 0; deg <= 360; deg++)
    {
      xMult = Math.cos(Math.toRadians(deg));
      yMult = Math.sin(Math.toRadians(deg));
      x = relativeX + (xMult * radius);
      y = relativeY + (yMult * radius);
      Point newPoint = this.raytrace((int)relativeX, (int)relativeY, (int) x, (int) y);
      light.addPoint((int) (newPoint.getX()-relativeX + centerX), (int) (newPoint.getY()-relativeY + centerY));
    }
  }

  /**
   *    Author: James McNeill
   *    http://playtechs.blogspot.com/2007/03/raytracing-on-grid.html
   */
  private Point raytrace(int x0, int y0, int x1, int y1)
  {
    int dx = Math.abs(x1 - x0);
    int dy = Math.abs(y1 - y0);
    int x = x0;
    int y = y0;
    int n = 1 + dx + dy;
    int x_inc = (x1 > x0) ? 1 : -1;
    int y_inc = (y1 > y0) ? 1 : -1;
    int error = dx - dy;
    dx *= 2;
    dy *= 2;

    for (; n > 0; --n)
    {
      for (int j = (int) (gridCol - playerSight); j < gridCol + playerSight; j++)
      {
        for (int i = (int) (gridRow - playerSight); i < gridRow + playerSight; i++)
        {
          if (i >= 0 && j >= 0 && i < 41 && j < 48)
            if (grid[i][j] instanceof Wall && grid[i][j].contains(x, y))
            {
              return new Point(grid[i][j].getCenterTileX(), grid[i][j].getCenterTileY());
            }
        }
      }

      if (error > 0)
      {
        x += x_inc;
        error -= dy;
      }
      else
      {
        y += y_inc;
        error += dx;
      }
    }
    return new Point(x1,y1);
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
