/**
 * Created by Tess Daughton, September 21st 2015
 * LightSource creates a Polygon to render a light source around the player
 */

import model.Player;
import model.Tile;
import model.Wall;
import model.ZombieHouseModel;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.util.ArrayList;

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
  private Graphics graphics;
  private ArrayList<Point> points;

  /**
   * Constructor
   *
   * @param zModel  to obtain the grid and player
   */
  public LightSource(ZombieHouseModel zModel)
  {
    this.grid = zModel.getGrid();
    this.player = zModel.getPlayer();
    this.playerTile = player.getCurrentTile();
    this.gridRow = playerTile.getGridRow();
    this.gridCol = playerTile.getGridCol();
    this.playerSight = player.getPlayerSight();
    points = new ArrayList<>();
  }

  /**
   * Creates a polygon based on the player's sight and position in the board, as well as surrounding tiles
   * (i.e. possible obstacles and walls need shadows)
   */
  protected void setPolygon(int centerX, int centerY)
  {
    points.clear();

    double tileRadius = Math.sqrt(((Math.pow(playerTile.getHeight(), 2))
        + (Math.pow(playerTile.getWidth(), 2))));
    this.radius = (float) (playerSight * tileRadius);
    this.light = new Polygon();
    double relativeX = player.getX();
    double relativeY = player.getY();
    this.center = new Point2D.Double(centerX, centerY);
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
      light.addPoint( (int) (centerX + newPoint.getX()), (int)(centerY + newPoint.getY()));
    }
  }

  /**
   *    Author: James McNeill
   *    http://playtechs.blogspot.com/2007/03/raytracing-on-grid.html
   */
  private Point raytrace(int x0, int y0, int x1, int y1)
  {
    this.playerTile = player.getCurrentTile();
    this.gridRow = playerTile.getGridRow();
    this.gridCol = playerTile.getGridCol();
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
              points.add(new Point((int) grid[i][j].getCenterX() - x0,
                                  (int) grid[i][j].getCenterY() - y0));
              return new Point((int)grid[i][j].getCenterX()-x0, (int)grid[i][j].getCenterY()-y0);
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

    points.add(new Point(x1 - x0, y1 - y0));
    return new Point(x1-x0,y1-y0);
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

  public void drawRayTrace(Graphics g)
  {
    //System.out.println(points.size());
    for (Point p : points)
    {
      g.drawLine((int) center.getX(), (int) center.getY(), p.x, p.y);
    }
  }
}
