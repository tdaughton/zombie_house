/*============================================================================*
 | Name:        Thien-Cam Nguyen                                              |
 | UNM ID#:     100156674                                                     |
 | Class:       CS 351L-001                                                   |
 | Assignment:  Lab 2: Multithreaded Game of Life                             |
 *============================================================================*/

/**
 * This class provides a integer coordinate pair that is kind of useful
 *
 * @author Thien-Cam Nguyen
 */
public class IntegerLocation2D implements Comparable<IntegerLocation2D>
{
  protected int x;
  protected int y;

  /**
   * Default constructor
   */
  public IntegerLocation2D ()
  {
    x = 0;
    y = 0;
  }

  /**
   * Basic integer point constructor
   *
   * @param xPos x
   * @param yPos y
   */
  public IntegerLocation2D (int xPos, int yPos)
  {
    x = xPos;
    y = yPos;
  }

  /**
   * Constructor to copy another location
   *
   * @param loc other location
   */
  public IntegerLocation2D (IntegerLocation2D loc)
  {
    x = loc.x;
    y = loc.y;
  }

  /**
   * Getter for X
   *
   * @return x
   */
  public int getX ()
  {
    return x;
  }

  /**
   * Setter for X
   *
   * @param xPos x
   * @return the new x
   */
  public int setX (int xPos)
  {
    return x = xPos;
  }

  /**
   * Getter for Y
   *
   * @return y
   */
  public int getY ()
  {
    return y;
  }

  /**
   * Setter for Y
   *
   * @param yPos y
   * @return the new y
   */
  public int setY (int yPos)
  {
    return y = yPos;
  }

  /**
   * Move this to a different place by coordinates
   *
   * @param xPos x
   * @param yPos y
   * @return location
   */
  public IntegerLocation2D move (int xPos, int yPos)
  {
    x = xPos;
    y = yPos;
    return this;
  }

  /**
   * Move this to a different place by copying
   *
   * @param loc other location
   * @return location
   */
  public IntegerLocation2D move (IntegerLocation2D loc)
  {
    x = loc.x;
    y = loc.y;
    return this;
  }

  /**
   * Translate this to a different place by coordinates
   *
   * @param xPos x
   * @param yPos y
   */
  public void addTo (int xPos, int yPos)
  {
    x = x + xPos;
    y = y + yPos;
  }

  /**
   * Translate this to a different place by location
   *
   * @param loc other location
   */
  public void addTo (IntegerLocation2D loc)
  {
    x = x + loc.x;
    y = y + loc.y;
  }

  /**
   * Translate this to a more different place by coordinates
   *
   * @param xPos x
   * @param yPos y
   */
  public void subtractFrom (int xPos, int yPos)
  {
    x = x - xPos;
    y = y - yPos;
  }

  /**
   * Translate this to a more different place by location
   *
   * @param loc other location
   */
  public void subtractFrom (IntegerLocation2D loc)
  {
    x = x - loc.x;
    y = y - loc.y;
  }

  /**
   * Copy location and translate by coordinates
   *
   * @param xPos x
   * @param yPos y
   * @return copy of translated location
   */
  public IntegerLocation2D add (int xPos, int yPos)
  {
    return new IntegerLocation2D(x + xPos, y + yPos);
  }

  /**
   * Copy location and translate by location
   *
   * @param loc other location
   * @return copy of translated location
   */
  public IntegerLocation2D add (IntegerLocation2D loc)
  {
    return new IntegerLocation2D(x + loc.x, y + loc.y);
  }

  /**
   * Copy location and translate by more different coordinates
   *
   * @param xPos x
   * @param yPos y
   * @return copy of more different translated location
   */
  public IntegerLocation2D subtract (int xPos, int yPos)
  {
    return new IntegerLocation2D(x - xPos, y - yPos);
  }

  /**
   * Copy location and translate by more different location
   *
   * @param loc other location
   * @return copy of more different translated location
   */
  public IntegerLocation2D subtract (IntegerLocation2D loc)
  {
    return new IntegerLocation2D(x - loc.x, y - loc.y);
  }

  /**
   * Simple comparison by hash
   *
   * @param loc other location
   * @return -0+
   */
  public int compareTo (IntegerLocation2D loc)
  {
    return this.hashCode() - loc.hashCode();
  }

  /**
   * Hash code
   *
   * @return hash
   */
  public int hashCode ()
  {
    int res = 1;
    res = res * 31 + x;
    res = res * 31 + y;
    return res;
  }

  /**
   * Standard equality check
   *
   * @param obj other object
   * @return true or false
   */
  public boolean equals (Object obj)
  {
    if (obj == this) return true;
    if ((obj == null) || (obj.getClass() != this.getClass())) return false;

    IntegerLocation2D other = (IntegerLocation2D) obj;
    return hashCode() == other.hashCode() && x == other.x && y == other.y;
  }

  /**
   * String representation: (x,y)
   *
   * @return name
   */
  public String toString ()
  {
    return "(" + x + "," + y + ")";
  }
}
