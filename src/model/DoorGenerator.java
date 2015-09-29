package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

//==============================================================================
// Miri Ryu
// CS351L-001
// Proj1 Zombie House
//
// Door Generator will create 1 or more doors for each room.
//==============================================================================
public class DoorGenerator implements GameMap
{
  private int[][] map;
  private Room[] rooms;
  private ArrayList<Point> doors;
  int roomNum;

  public DoorGenerator(int[][] map, Room[] rooms, int roomNum)
  {
    this.map = map;
    this.roomNum = roomNum;
    this.rooms = rooms;
    doors = new ArrayList<>();

    generateDoorways();
  }

  public int[][] getMap() { return map; }
  public ArrayList getDoors() { return doors; }

  //============================================================================
  // This method will build doors.
  //============================================================================
  private void generateDoorways()
  {
    for (int i = 0; i < roomNum; i++) getDoorsFor(rooms[i]);

    /** For Exit, special algorithm is needed! **/
    connectExit(getAvailableSides(rooms[roomNum]));
  }

  //============================================================================
  // Each room will have at least one door to exit the room. Each side of the
  // room hold only one door.
  //============================================================================
  private void getDoorsFor(Room room)
  {
    ArrayList<Character> availableSides = getAvailableSides(room);

    int numDoors = 1 + RANDOM.nextInt(availableSides.size()-1);

    Collections.shuffle(availableSides);

    for(int i = 0; i < numDoors ; i++) installDoor(room, availableSides.get(i));
    availableSides.clear();
  }

  //============================================================================
  // This will get how many sides can have access to the hallway.
  //============================================================================
  private ArrayList<Character> getAvailableSides(Room room)
  {
    ArrayList<Character> availableSides = new ArrayList<>();

    if(room.x1 > 2) availableSides.add('W');
    if(room.x2 < COL - 3) availableSides.add('E');
    if(room.y1 > 1) availableSides.add('N');
    if(room.y2 < ROW - 3) availableSides.add('S');

    return availableSides;
  }

  //============================================================================
  // This will install the door at the indicated side of the wall of the room.
  //============================================================================
  private void installDoor(Room room, char direction)
  {
    int x, y, xInc = 0, yInc = 0;
    switch(direction)
    {
      case 'N' :
        x = room.x1 + RANDOM.nextInt((room.width-1)/2) * 2;
        y = room.y1;
        yInc = -1;
        break;
      case 'E' :
        y = room.y1 + RANDOM.nextInt((room.height-1)/2) * 2;
        x = room.x2-1;
        xInc = 1;
        break;
      case 'W' :
        y = room.y1 + RANDOM.nextInt((room.height-1)/2) * 2;
        x = room.x1;
        xInc = -1;
        break;
      case 'S' :
        x = room.x1 + RANDOM.nextInt((room.width-1)/2) * 2;
        y = room.y2-1;
        yInc = 1;
        break;
      default:
        return;
    }

    if(map[y+yInc*2][x+xInc*2] != 1)
    {
      map[y+yInc][x+xInc] = 2;
      map[y+yInc*2][x+xInc*2] = 2;
      doors.add(new Point(x+xInc*2, y+yInc*2));
    }
    else
    {
      map[y+yInc][x+xInc] = 1;
    }
/*
    if(yInc == 0)
    {
      if(map[y-1][x+xInc*2] != 1) map[y-1][x+xInc*2] = 32;
      if(map[y+1][x+xInc*2] != 1) map[y+1][x+xInc*2] = 32;
    }
    else
    {
      if(map[y+yInc*2][x-1] != 1) map[y+yInc*2][x-1] = 32;
      if(map[y+yInc*2][x+1] != 1) map[y+yInc*2][x+1] = 32;
    }*/
  }

  private void connectExit(ArrayList<Character> availableSides)
  {
    Collections.shuffle(availableSides);

    int x = rooms[roomNum].x1;
    int y = rooms[roomNum].y1;

    for(int i=-1; i<2; i++)
    {
      for(int j=-1; j<2; j++)
      {
        if(i != 0 || j != 0) map[y+i][x+j] = 16;
      }
    }

    switch(availableSides.get(0))
    {
      case 'W':
        map[y][x-1] = 2;
        doors.add(new Point(x-1, y));
        break;
      case 'S':
        map[y+1][x] = 2;
        doors.add(new Point(x, y+1));
        break;
      case 'N':
        map[y-1][x] = 2;
        doors.add(new Point(x, y-1));
        break;
      case 'E':
        map[y][x+1] = 2;
        doors.add(new Point(x+1, y));
        break;
      default: return;
    }
  }
}
