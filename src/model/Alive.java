package model;


//==============================================================================
// Miri Ryu
// CS351L-001
//
// Alive interface is for all the things that has health and that can be hurt.
// Even though zombies are not technically alive, let's not think about that. :)
// I hope this is not completely useless. If you guys are never going to use it,
// you guys are totally free to throw this away.
//==============================================================================
public interface Alive
{


  //============================================================================
  // Fire trap can hurt both player and zombie, I guess. Once the fire hits
  // the player or zombie, they should be taking damages for awhile. Damage will
  // gradually be reduced.
  //============================================================================


  //============================================================================
  // Return the current health. Upper class should check if the health is
  // greater than 0. If it is less than or equal to 0, the living thing should
  // die. Also it can be used to display the health on the top of each thing.
  //============================================================================
  double getHealth();

  void decrementHealth(int damage);

  boolean isDead();
}
