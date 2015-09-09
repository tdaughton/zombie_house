package view;

/**
 * Created by L301126 on 9/9/15.
 */

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class TreeImage
{
  private Random rand = new Random();
  private String[] fileNames = {"tree1.png", "tree2.png", "deadtree.png"};
  private BufferedImage tree1 = null;
  private BufferedImage tree2 = null;
  private BufferedImage tree3 = null;

  protected TreeImage()
  {
    initializeTrees();
  }

    protected BufferedImage getRandomTrees()
    {
      BufferedImage returnTree = null;
      int randInt = rand.nextInt();
      if(randInt%2==0)
      {
        if (randInt % 3 == 0) returnTree=tree1;
        else if (randInt%5 == 0) returnTree=tree2;
        else  returnTree=tree3;
      }
      return returnTree;
    }

    protected void initializeTrees()
    {
      try
      {
        tree1 = ImageIO.read(ClassLoader.getSystemResource(fileNames[0]));
        tree2 = ImageIO.read(ClassLoader.getSystemResource(fileNames[1]));
        tree3 = ImageIO.read(ClassLoader.getSystemResource(fileNames[2]));

      } catch (IOException e)
      {
        e.printStackTrace();
      }
    }
}
