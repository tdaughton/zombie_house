/**
 * Created by Tess Daughton, September 21st 2015
 * Will use this class in combination with LinearGradientPaint class (Hopefully!)
 * to render Player's light source
 */
import java.awt.*;

import model.Player;
import model.Tile;
import java.awt.geom.Point2D;
//import java.awt.MultipleGradientPaint.CycleMethod;

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

  public LightSource(Player player, Tile[][] map)
  {
    this.grid = map;
    this.playerTile = player.getCurrentTile();
    this.playerSight = player.getPlayerSight();

  }

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
      System.out.println("(" + (((int) (Math.cos(Math.toRadians(deg))) * radius) + playerX) + ", " + (((int) (Math.sin(Math.toRadians(deg))) * radius) + playerY) + ")");
      double x = playerX + (Math.cos(Math.toRadians(deg))) * radius;
      double y = playerY + (Math.sin(Math.toRadians(deg))) * radius;
      light.addPoint((int)x, (int) y);

    }
  }

  protected Polygon getPolygon()
  {
    return this.light;
  }

  protected float getRadius()
  {
    return this.radius;
  }

  protected Point2D getCenter()
  {
    return this.center;
  }

}
