/*============================================================================*
 | Name:        Thien-Cam Nguyen                                              |
 | UNM ID#:     100156674                                                     |
 | Class:       CS 351L-001                                                   |
 | Assignment:  Lab 2: Multithreaded Game of Life                             |
 *============================================================================*/

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

/**
 * This class is the Viewer and Controller for multithreaded Game of Life
 *
 * @author Thien-Cam Nguyen
 */
public class Lab2_LifeGUI extends JPanel implements ActionListener, ChangeListener, MouseListener, MouseMotionListener, MouseWheelListener
{
  private static final int GRID_LINE_MIN = 4;
  private static final int GRID_SCALE_MAX = 50;
  private static final Color deadColor = Color.LIGHT_GRAY;
  private static final Color liveColor = Color.BLUE;
  private static JPanel controlPane;
  private JButton playButton;
  private JButton nextButton;
  private JButton resetButton;
  private JComboBox presetBox;
  private JSpinner threadSpinner;
  private GameOfLife game;
  private Timer tick;
  private IntegerLocation2D mouseLoc;
  private IntegerLocation2D mouseLast;
  private IntegerLocation2D mouseDiff;
  private int curScale;
  private int gridLine;
  private IntegerLocation2D offsetLoc;
  private IntegerLocation2D gridOffset;
  private IntegerLocation2D pixOffset;
  private IntegerLocation2D gridVisible;

  /**
   * Default class constructor
   */
  public Lab2_LifeGUI ()
  {
    super(new GridLayout());
    setDoubleBuffered(true);
    setPreferredSize(new Dimension(640, 480));

    game = new GameOfLife();
    initLocations();

    playButton = new JButton("Pause");
    playButton.addActionListener(this);
    nextButton = new JButton("Next");
    nextButton.addActionListener(this);
    resetButton = new JButton("Reset");
    resetButton.addActionListener(this);
    String[] presets = {"Random", "Preset 1", "Preset 2", "Preset 3", "Preset 4", "Preset 5"};
    presetBox = new JComboBox<>(presets);
    presetBox.addActionListener(this);
    SpinnerNumberModel threadNumsModel = new SpinnerNumberModel(game.getThreadsMax(), 1, 8, 1);
    threadSpinner = new JSpinner(threadNumsModel);
    threadSpinner.addChangeListener(this);
    controlPane = new JPanel(new GridLayout(1, 5));
    controlPane.add(playButton);
    controlPane.add(nextButton);
    controlPane.add(resetButton);
    controlPane.add(presetBox);
    controlPane.add(threadSpinner);

    addMouseListener(this);
    addMouseWheelListener(this);
    addMouseMotionListener(this);

    tick = new Timer(16, this);
    tick.setRepeats(true);
    tick.start();
  }

  /**
   * Action handler for most of the GUI elements
   *
   * @param e Actions
   */
  public void actionPerformed (ActionEvent e)
  {
    Object actSource = e.getSource();
    if (actSource == tick)
    {
      validateLocations();
      repaint();
    }
    else if (actSource == playButton)
    {
      if (game.isSimPaused()) playButton.setText("Pause");
      else playButton.setText("Play");
      game.toggleSimPaused();
    }
    else if (actSource == nextButton)
    {
      if (game.isSimPaused()) game.setSimStep();
    }
    else if ((actSource == resetButton) || (actSource == presetBox))
    {
      game.restart(presetBox.getSelectedIndex());
    }
  }

  /**
   * Action handler for the thread count selector and display (JSpinner)
   *
   * @param e Action
   */
  public void stateChanged (ChangeEvent e)
  {
    game.setThreadsMax(((Number) threadSpinner.getModel().getValue()).intValue());
  }

  /**
   * Action handler for clicks. Clicking on a tile when simulation is paused will toggle life in that tile.
   *
   * @param e Click
   */
  public void mouseClicked (MouseEvent e)
  {
    if (game.isSimPaused())
    {
      int clickX = gridOffset.getX() + ((e.getX() + pixOffset.getX()) / curScale);
      int clickY = gridOffset.getY() + ((e.getY() + pixOffset.getY()) / curScale);
      if ((clickX <= game.getGridSize()) && (clickY <= game.getGridSize()))
      {
        game.getCurGen()[clickX][clickY] = !game.getCurGen()[clickX][clickY];
      }
    }
  }

  /**
   * Action handler for Click'n
   *
   * @param e Click
   */
  public void mousePressed (MouseEvent e)
  {
    mouseLast.move(e.getX(), e.getY());
  }

  /**
   * Action handler for n'Drag'n
   *
   * @param e Click
   */
  public void mouseDragged (MouseEvent e)
  {
    mouseLoc.move(e.getX(), e.getY());
    mouseDiff = mouseLoc.subtract(mouseLast);
    mouseLast.move(mouseLoc);
    offsetLoc.subtractFrom(mouseDiff);
  }

  /**
   * Action handler for n'Drop except it does nothing in this program
   *
   * @param e Click
   */
  public void mouseReleased (MouseEvent e)
  {
    //do nothing
  }

  /**
   * Action handler for mouse wheeling
   *
   * @param e Click
   */
  public void mouseWheelMoved (MouseWheelEvent e)
  {
    int notches = e.getWheelRotation();
    curScale = curScale - notches;
  }

  /**
   * Assorted required methods that aren't used here.
   **/
  public void mouseMoved (MouseEvent e)
  {
    //do nothing
  }

  public void mouseEntered (MouseEvent e)
  {
    //do nothing
  }

  public void mouseExited (MouseEvent e)
  {
    //do nothing
  }

  /**
   * This method paints pretty pictures
   *
   * @param gfx it's required
   */
  public void paintComponent (Graphics gfx)
  {
    super.paintComponent(gfx);

    Graphics2D g = (Graphics2D) gfx;

    g.setColor(deadColor);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
    g.setColor(liveColor);
    for (int j = 0; j < gridVisible.getY(); j++)
    {
      for (int i = 0; i < gridVisible.getX(); i++)
      {
        if (game.getCurGen()[gridOffset.getX() + i][gridOffset.getY() + j])
        {
          g.fillRect(i * curScale - pixOffset.getX(), j * curScale - pixOffset.getY(), gridLine, gridLine);
        }
      }
    }
  }

  /**
   * Initializer for all IntegerLocation2D objects
   */
  private void initLocations ()
  {
    mouseLoc = new IntegerLocation2D(0, 0);
    mouseLast = new IntegerLocation2D(0, 0);
    mouseDiff = new IntegerLocation2D(0, 0);

    curScale = 1;
    gridLine = 0;
    offsetLoc = new IntegerLocation2D(0, 0);
    gridOffset = new IntegerLocation2D(0, 0);
    pixOffset = new IntegerLocation2D(0, 0);
    gridVisible = new IntegerLocation2D(0, 0);
  }

  /**
   * This method validates locations and does the arithmetic necessary to maintain the viewpoint.
   */
  private void validateLocations ()
  {
    //validate curScale
    if (curScale < 1) curScale = 1;
    else if (curScale > GRID_SCALE_MAX) curScale = GRID_SCALE_MAX;
    //enable grid drawing
    if (curScale > GRID_LINE_MIN) gridLine = curScale - 1;
    else gridLine = curScale;
    //validate gridVisible
    int gameGridSize = game.getGridSize();
    int visX = Math.min((this.getWidth() / curScale) + 2, gameGridSize);
    int visY = Math.min((this.getHeight() / curScale) + 2, gameGridSize);
    gridVisible.move(visX, visY);
    //validate offsetLoc
    int offX = offsetLoc.getX();
    int offY = offsetLoc.getY();
    int offsetMaxX = gameGridSize * curScale - this.getWidth();
    int offsetMaxY = gameGridSize * curScale - this.getHeight();
    if (offX < 0) offX = 0;
    else if (offX > offsetMaxX) offX = offsetMaxX;
    if (offY < 0) offY = 0;
    else if (offY > offsetMaxY) offY = offsetMaxY;
    offsetLoc.move(offX, offY);
    //validate pixOffset
    int pixX = offX % curScale;
    int pixY = offY % curScale;
    pixOffset.move(pixX, pixY);
    //validate gridOffset
    int gridX = offX / curScale;
    int gridY = offY / curScale;
    int gridMaxX = gameGridSize - visX;
    int gridMaxY = gameGridSize - visY;
    if (gridX < 0) gridX = 0;
    else if (gridX > gridMaxX) gridX = gridMaxX;
    if (gridY < 0) gridY = 0;
    else if (gridY > gridMaxY) gridY = gridMaxY;
    gridOffset.move(gridX, gridY);
  }

  /**
   * This method creates and shows the main GUI Frame
   */
  private static void createAndShowGUI ()
  {
    JFrame frame = new JFrame("Lab2_LifeGUI");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JComponent lifePane = new Lab2_LifeGUI();

    JComponent mainPane = new JPanel(new BorderLayout());
    mainPane.add(controlPane, BorderLayout.NORTH);
    mainPane.add(lifePane, BorderLayout.CENTER);
    mainPane.setOpaque(true);
    frame.setContentPane(mainPane);

    frame.pack();
    frame.setVisible(true);
  }

  /**
   * This is the class entry point
   *
   * @param args no meaningful arguments are used
   */
  public static void main (String[] args)
  {
    SwingUtilities.invokeLater(Lab2_LifeGUI::createAndShowGUI);
  }
}
