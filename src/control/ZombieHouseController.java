/**
 * Created by Thien-Cam Nguyen, September 16th, 2015
 * This class will update game status and relay commands to Zombies and Traps
 */

package control;

import model.*;

import java.util.List;

public class ZombieHouseController
{
  private static final double zombieSpawnRate = 0.01;
  private static final double trapSpawnRate = 0.01;
  private Tile[][] grid;
  private List<Zombie> zombies;
  private List<Trap> traps;
  private Player pc;

}
