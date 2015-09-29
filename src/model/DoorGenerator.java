package model;

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
  int roomNum;
  private Tile[][] map;
  private Room[] rooms;
  private ArrayList<Tile> doors;

  public DoorGenerator(Tile[][] map, Room[] rooms, int roomNum)
  {
    this.map = map;
    this.roomNum = roomNum;
    this.rooms = rooms;
    doors = new ArrayList<>();

    generateDoorways();
  }

  public Tile[][] getMap()
  {
    return map;
  }

  public ArrayList<Tile> getDoors()
  {
    return doors;
  }

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
    if(room.y1 > 2) availableSides.add('N');
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

    if (map[y + yInc * 2][x + xInc * 2].type != 1)
    {
      map[y + yInc][x + xInc] = new Floor(y + yInc, x + xInc);
      map[y + yInc * 2][x + xInc * 2] = new Floor(y + yInc * 2, x + xInc * 2);
      map[y + yInc * 2][x + xInc * 2].setHallway(true);

      doors.add(map[y + yInc * 2][x + xInc * 2]);
    }
    else
    {
      map[y + yInc][x + xInc] = new Wall(y + yInc, x + xInc);
    }
  }

  //============================================================================
  // Exit will be surrounded by walls and only one side will be open to be
  // connected to the hallway.
  //============================================================================
  private void connectExit(ArrayList<Character> availableSides)
  {
    Collections.shuffle(availableSides);

    int x = rooms[roomNum].x1;
    int y = rooms[roomNum].y1;

    for(int i=-1; i<2; i++)
    {
      for(int j=-1; j<2; j++)
      {
        if (i != 0 || j != 0)
        {
          map[y + i][x + j] = new Wall(y + i, x + j);
        }
      }
    }

    switch(availableSides.get(0))
    {
      case 'W': x -= 1;
        break;
      case 'S': y += 1;
        break;
      case 'N': y -= 1;
        break;
      case 'E': x += 1;
        break;
      default: return;
    }

    map[y][x] = new Floor(y, x);
    doors.add(map[y][x]);
  }
}
