//==============================================================================
// Miri Ryu
// CS351L-001
// Proj1 Zombie House
//
// MapGenerator class will create a 2D array int that indicates map. Each int on
// the map indicates a tile on the screen. Each tile represents different
// material such as floor tile, wall tile.
//
// #    Name            Passable
// 0    Nowhere         false
// 1    Room            true
// 2    Hallways        true
// 8    Exit            true
// 16   Wall            false
//
// However, when the map generation is finished, they are going to be changed
// as follows:
//
// 0(Nowhere) ---------------> 0(Nowhere)
// 1(Room) ------------------> 2(Floor)
// 2(Hallways) --------------> 2(Floor)
// 8(Exit) ------------------> 4(Exit)
// 16(Wall) -----------------> 1(Wall)
//
// The reason I change them into new notation is because once the map is
// generated the original notations are unnecessary.
//==============================================================================

package model;

public class MapGenerator implements GameMap
{
  // If the number of rooms are too big compared to the dimension of the map
  // the tempRooms won't fit into the map so try not to use such a number.


  private int[][] map;
  private Room[] rooms;

  //============================================================================
  // MapGenerating can be abstracted to following steps:
  //
  // 1. Take the dimension and created 2d array accordingly.
  // 2. Generate RANDOM room with different sizes. Each room must start and end
  //    at odd coordinates of of tile because that way hallways are going to
  //    look prettier.
  // 3. Generate RANDOM hallways. Hallways are going to change directions or end
  //    at odd number of coordinates only, too.
  // 4. Connect tempRooms and hallways. Each room can have multiple doors but
  //    must have at least one.
  // 5. locate exit on hallway.
  // 6. Erase unused hallways.
  //============================================================================
  public MapGenerator(int roomNum)
  {
    map = new int[ROW][COL];
    rooms = new Room[roomNum + 1];

    initiateHouse();
    RoomGenerator rg = new RoomGenerator(map,roomNum);
    DoorGenerator dg = new DoorGenerator(rg.getMap(), rg.getRooms(), roomNum);
    //printMap();
    HallwayGenerator hg = new HallwayGenerator(dg.getMap(), dg.getDoors());
    //printMap();
    convertIntoDisplayableMap(hg.getMap());
    printMap();

  }

  //============================================================================
  // This will only return the finalized map composed only with
  // 0(Wall), 1(Floor), 2(Exit), 4(Nowhere, not added yet).
  //============================================================================
  public int[][] getMap() { return map; }

  //============================================================================
  // Map can be printed from any other class for testing.
  //============================================================================
  public void printMap()
  {
    String ln;
    for (int i = 0; i < ROW; i++)
    {
      ln = "";
      for (int j = 0; j < COL; j++)
      {
        switch (map[i][j])
        {
          case 0:
            ln += "   ";
            break;
          case 1:
            ln += " / ";
            break;
          case 2:
            ln += "[2]";
            break;
          case 4:
            ln += "[4]";
            break;
          case 8:
            ln += "[&]";
            break;
          case 16:
            ln += "[=]";
            break;
          default:
            ln += "[ ]";
            break;
        }
      }
      System.out.println(ln);
    }
    System.out.println();
  }

  //============================================================================
  // Initiate House method will start TileModel Array but I am planning to
  // change it to int array soon.
  //============================================================================
  private void initiateHouse()
  {
    for (int i = 0; i < ROW; i++)
    {
      for (int j = 0; j < COL; j++)
      {
        map[i][j] = 0;
      }
    }
  }

  //============================================================================
  // Search for end of the hallway and start removing.
  //============================================================================
  private void convertIntoDisplayableMap(int[][] finalMap)
  {
    for(int i=0; i<ROW; i++)
    {
      for(int j=0; j<COL; j++)
      {
        switch (finalMap[i][j])
        {
          case 16: //if it's wall
            this.map[i][j] = 1;
            break;
          case 8: //if it's exit
            this.map[i][j] = 4;
            break;
          case 0: //if it's nowhere
            continue;
          default:
            this.map[i][j] = 2;
        }
      }
    }
  }

  /**
   * Test script. This is how to use this class.
   *
   * @param args do nothing
   */
}
