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

  boolean isDead();
}
