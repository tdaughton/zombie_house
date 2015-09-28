package model;

import java.util.Random;

//==============================================================================
// Miri Ryu
// CS351L-001
// Proj1 Zombie House
//
// While splitting my MapGenerator, I realized I keep calling some values that
// never changes. So I just chosen to create interface for them.
//==============================================================================
public interface GameMap
{
  int NUMBER_OF_ROOMS = 10;
  int COL = 48;
  int ROW = 41;

  Random RANDOM = new Random();

  int[][] getMap();
}
