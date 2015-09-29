package model;

import java.util.ArrayList;

//==============================================================================
// Miri Ryu
// CS351L-001
// Proj1 Zombie House
//
// RoomGenerator will create 10 rooms on the map(indicated by the GameMap).
// RoomGenerator will create a special kind of room, a exit.
//==============================================================================
public class RoomGenerator implements GameMap
{
  private final int roomNum;
  private ArrayList<Room> tempRooms;
  private Room[] rooms;

  private Tile[][] map;
  private int col, row;

  public RoomGenerator(Tile[][] map, int roomNum)
  {
    this.map = map;
    this.roomNum=roomNum;

    row = map.length;
    col = map[0].length;

    tempRooms = new ArrayList<>();
    rooms = new Room[roomNum + 1];

    generateRandomRoom();
  }

  public Tile[][] getMap()
  {
    return map;
  }
  public Room[] getRooms() { return rooms; }

  //============================================================================
  // Instead of picking random numbers for a rectangle and hoping for the
  // rectangle to fit into the map eventually, this method will call a
  // recursive method getQuadSect which divide given Room class into 4 smaller
  // tempRooms. The recursion will stop when the room gets too small or when the
  // iteration seems to be happened enough to generate sufficient number of
  // tempRooms.
  //============================================================================
  private void generateRandomRoom()
  {
    int exitRoomNumber, exitX, exitY, exitWid, exitHei;
    while (true)
    { // generate smaller squares for border
      if (getQuadSect(new Room(0, 0, col - 2, row - 2), 0) > roomNum + 1)
      {
        break;
      }

      tempRooms.clear();
    }

    /** Create an exit here. Exit is a special kind of room. **/
    exitRoomNumber = RANDOM.nextInt(tempRooms.size());

    exitWid = tempRooms.get(exitRoomNumber).width / 2;
    exitHei = tempRooms.get(exitRoomNumber).height / 2;

    exitX = tempRooms.get(exitRoomNumber).x1 + exitWid;
    exitY = tempRooms.get(exitRoomNumber).y1 + exitHei;

    //System.out.println(exitX + "," + exitY);

    locateExit(new Room(exitX, exitY, exitX + 1, exitY + 1), roomNum);

    tempRooms.remove(exitRoomNumber);

    while (tempRooms.size() > roomNum)
    { // Pick random tempRooms.
      tempRooms.remove(RANDOM.nextInt(tempRooms.size()));
    }

    for (int i = 0; i < roomNum; i++) addNewRoom(tempRooms.get(i), i);
  }

  //============================================================================
  // Divide given rectangle into 4 rectangles. Check how deep the iteration
  // went through and if it's enough to generate given number of tempRooms then
  // stops.
  //============================================================================
  private int getQuadSect(Room room, int it)
  {
    Room[] sects = new Room[4];
    int xAxis, yAxis;

    if (room.width < 5 || room.height < 5) return 0; // 5x5 is too small.

    if (room.width < 8 || room.height < 8 ||
        Math.pow(4.0, (double)it) > roomNum)
    { // If the dimension of room gets too small it can't be divided further.
      tempRooms.add(room);
      return 1;
    }

    xAxis = room.x1 + 5 + RANDOM.nextInt(room.width / 2 - 3) * 2;
    yAxis = room.y1 + 5 + RANDOM.nextInt(room.height / 2 - 3) * 2;

    sects[0] = new Room(room.x1, room.y1, xAxis, yAxis);
    sects[1] = new Room(room.x1, yAxis + 1, xAxis, room.y2);
    sects[2] = new Room(xAxis + 1, room.y1, room.x2, yAxis);
    sects[3] = new Room(xAxis + 1, yAxis + 1, room.x2, room.y2);

    return getQuadSect(sects[0], it + 1) + getQuadSect(sects[1], it + 1) +
           getQuadSect(sects[2], it + 1) + getQuadSect(sects[3], it + 1);
  }

  //============================================================================
  // Exit is a special kind of room with 1x1 dimension.
  //============================================================================
  private void locateExit(Room exit, int roomNumber)
  {
    rooms[roomNumber] = exit;
    map[exit.y1][exit.x1] = new Floor(exit.x1, exit.y1, null);
    map[exit.y1][exit.x1].setExitFlag();
  }

  //============================================================================
  // This method will add the new room into the map.
  //============================================================================
  private void addNewRoom(Room room, int roomNum)
  {
    int x, y, width, height;
    int rX, rY, rWidth, rHeight;

    rX = room.x1 + 1;
    rY = room.y1 + 1;

    rWidth = room.width;
    rHeight = room.height;

    width = rWidth > 6 ? 5 + RANDOM.nextInt((rWidth - 5) / 2) * 2 : 5;
    height = rHeight > 6 ? 5 + RANDOM.nextInt((rHeight - 5) / 2) * 2 : 5;

    x = rWidth > width + 1 ? rX + RANDOM.nextInt((rWidth - width) / 2) * 2 : rX;
    y = rHeight > height + 1 ? rY +
                               RANDOM.nextInt((rHeight - height) / 2) * 2 : rY;

    rooms[roomNum] = new Room(x, y, x + width, y + height);

    for (int i = y - 1 ; i <= y + height; i++)
    {
      for (int j = x - 1; j <= x + width; j++)
      {
        if (i >= y && i < y + height && j >= x && j < x + width)
        {
          map[i][j] = new Floor(i, j, null);
        } else
        {
          map[i][j] = new Wall(i, j, null);
        }
      }
    }
  }
}
