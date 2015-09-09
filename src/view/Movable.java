package view;

/**
 * Created by L301126 on 9/9/15.
 */
import javafx.scene.shape.Circle;
import java.awt.Rectangle;

public abstract class Movable
{
  protected double x;
  protected double y;
  protected double radius;
  protected Circle circle;
  
  public Movable(double x, double y, double radius)
  {
    this.x=x;
    this.y=y;
    this.radius=radius;
    circle = new Circle(x,y,radius);
  }

  public double getX() { return x; }
  public double getY() { return y; }
  public double getRadius(){ return radius; }


  public Circle getBoundingCircle()
  {
    return this.circle;
  }

  public boolean interests(Circle otherMovable)
  {
    boolean intersects = false;
    double r1 = Math.pow(otherMovable.getRadius()-this.getRadius(),2);
    double r2 = Math.pow(otherMovable.getRadius()+this.getRadius(),2);

    double distance = Math.pow((otherMovable.getCenterX()-this.getX()),2) +
                      Math.pow((otherMovable.getCenterY()-this.getY()),2);

    if(distance>=r1 && distance<=r2)
    {
      intersects=true;
    }
    return intersects;
  }
//http://stackoverflow.com/questions/401847/circle-rectangle-collision-detection-intersection
  public boolean intersects (Rectangle immovableSpace)
  {
    boolean intersects = false;
    double r1 = Math.abs(this.getX() - immovableSpace.getX());
    double r2 = Math.abs(this.getY()-immovableSpace.getY());

    int rectWidth = immovableSpace.width/2;
    int rectHeight = immovableSpace.height/2;

    if(r1> rectWidth+this.getRadius()) intersects=false;
    if(r2> rectHeight+this.getRadius()) intersects=false;
    if(r1<= rectWidth+this.getRadius()) intersects=true;
    if(r2<=rectHeight+this.getRadius()) intersects=true;

    double cd = Math.pow(r1 - rectWidth,2) + Math.pow(r2 - rectHeight,2);
    double cd_sqrt = Math.sqrt(cd);

    if(cd_sqrt <= Math.pow(this.getRadius(),2)) intersects = true;


    return intersects;
  }

  public void move(int xNew, int yNew)
  {
    this.x += xNew;
    this.y += yNew;
  }

}
