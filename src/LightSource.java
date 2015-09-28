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
  private int centerX;
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
  public LightSource(Player player, Tile[][] map)
  {
    this.grid = map;
    this.playerTile = player.getCurrentTile();
    this.player = player;
    this.playerSight = player.getPlayerSight();
  }

  /**
   * Creates a polygon based on the player's sight and position in the board, as well as surrounding tiles
   * (i.e. possible obstacles and walls need shadows)
   */
  protected void setPolygon(int gridRow, int gridCol, int centerX, int centerY)
  {
    this.playerTile = player.getCurrentTile();
    Tile tile = playerTile;
    double lineOfSight = 0.75;
    double tileRadius = Math.sqrt(((Math.pow(playerTile.getHeight(), 2))
        + (Math.pow(playerTile.getWidth(), 2))));
    this.radius = (float) (playerSight * tileRadius);
    this.light = new Polygon();
    this.center = new Point2D.Float(centerX, centerY);
    double x;
    double y;
    double xMult;
    double yMult;
    int pointCounter = 0;

    for (int deg = 0; deg <= 360; deg++)
    {
      xMult = Math.cos(Math.toRadians(deg));
      yMult = Math.sin(Math.toRadians(deg));
      tile=playerTile;
      lineOfSight = .01;

      while (lineOfSight < playerSight && tile instanceof Floor)
      {
        radius = (float) (lineOfSight * tileRadius);
        x = player.getX() + (xMult * radius);
        y = player.getY() + (yMult * radius);

        for (int i = 0; i < playerSight; i++)
        {
          if ((gridRow - i >= 0 && gridCol - i >= 0) && grid[gridRow - i][gridCol - i].contains(x, y))
          {
            tile = grid[gridRow - i][gridCol - i];
          }
          else if ((gridRow - i >= 0 && gridCol >= 0) && grid[gridRow - i][gridCol].contains(x, y))
          {
            tile = grid[gridRow - i][gridCol];
          }
          else if ((gridRow - i >= 0 && gridCol + i < 40) && grid[gridRow - i][gridCol + i].contains(x, y))
          {
            tile = grid[gridRow - i][gridCol + i];
          }
          else if ((gridCol - i >= 0) && grid[gridRow][gridCol - i].contains(x, y))
          {
            tile = grid[gridRow][gridCol - i];
          }
          else if ((gridCol + i < 40) && grid[gridRow][gridCol + i].contains(x, y))
          {
            tile = grid[gridRow][gridCol + i];
          }
          else if ((gridRow + i < 40 && gridCol - i >= 0) && grid[gridRow + i][gridCol - i].contains(x, y))
          {
            tile = grid[gridRow + i][gridCol - i];
          }
          else if ((gridRow + i < 40) && grid[gridRow + i][gridCol].contains(x, y))
          {
            tile = grid[gridRow + i][gridCol];
          }
          else if ((gridRow + i < 40 && gridCol + i < 40) && grid[gridRow + i][gridCol + i].contains(x, y))
          {
            tile = grid[gridRow + i][gridCol + i];
          }
          if (tile instanceof Wall) break;
        }
        if(tile instanceof Wall) break;
        lineOfSight += .1;
      }
      if(tile instanceof Wall)
      {
        System.out.println("(" + (int) tile.getCenterX() + ", " + (int) tile.getCenterY() + ")");
        light.addPoint((int) tile.getCenterX(), (int) tile.getCenterY());
      }
      else
      {
        x = centerX + (xMult * radius);
        y = centerY + (yMult * radius);
        System.out.println("(" + x + ", " + y + ")");

        light.addPoint((int) x, (int) y);
      }
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
