/**
 * Created by Tess Daughton, September 21st 2015
 * Will use this class in combination with LinearGradientPaint class (Hopefully!)
 * to render Player's light source
 */
import java.awt.Polygon;
import java.awt.LinearGradientPaint;
import model.Player;
import model.Tile;
public class LightSource extends Polygon
{

  private Tile[][] grid;
  private Tile playerTile;
  private double playerSight;
  private int playerX;
  private int playerY;
  private Polygon light;

  public LightSource(Player player, Tile[][] map)
  {
    this.grid = map;
    this.playerTile=player.getCurrentTile();
    this.playerSight = player.getPlayerSight();

  }

  protected void setPolygon(int width, int height)
  {
    light = new Polygon();
    this.playerX = width / 2;
    this.playerY = height / 2;
    int frameWidth = width;
    int frameHeight = height;
    double diagonalLength = playerSight * Math.sqrt((Math.pow(playerTile.getHeight(), 2)) + (Math.pow(playerTile
        .getWidth(), 2)));
    for (int deg = 0; deg < 360; deg++)
    {
      System.out.println((int) Math.cos(Math.toRadians(deg)) * diagonalLength + playerX);
      System.out.println((int) Math.sin(Math.toRadians(deg)) * diagonalLength + playerY);
      int x = (int) Math.cos(Math.toRadians(deg));
      int y = (int) Math.sin(Math.toRadians(deg));
      addPoint((int) (x * diagonalLength + playerX), (int) (y * diagonalLength + playerY));

    }
  }

    protected Polygon getPolygon()
    {
      return this.light;
    }

}
