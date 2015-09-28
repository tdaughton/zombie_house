# zombie_house
CS351 Project 1

**TD edit on September 27th, 2015**

Here is the rundown of what we need to get done guys:
I'm going to write names by who I think should do what, but that is by no means final. If you want to be/already are
working on something else just let me know.

1. Levels have to be completed - Miri
2. Player needs to win/die - Tess
3. Zombie pathfinding needs to incorporate smell - Miri
4. Master Zombie needs implementation - TC
5. Zombies need to face correct orientation during moving - TC
6. Traps AND player need light source with shadows - Tess
7. Resizing. - TC
8. Directional sound. - Tess
9. EXIT must be established from house. - Miri
10. Intersection between two movable objects. - TC


Model
- Level up
- Movable
  - Fix intersections when moving diagonally -> TC implemented
  - Player
    - Stamina + regen -> Working, but stamina is allowed to go negative as of now.
    - Set traps -> Tess implemented
    - Pick up traps -> Tess implemented
    - Die -> Not yet
    - Win -> Not yet
  - Zombies -> Zombies are moving in the incorrect orientation
    - Smell -> Not yet
    - Random walk -> Tess implemented sprites
    - Line walk -> Tess implemented sprites
    - Master zombie -> No implementation yet
    - Die -> Tess implemented
  - Traps
    - Trigger placed traps -> Tess implemented

View
- Resizing frame must resize graphics -> TC is working on this I think?
  - Tiles must be 80x80 when frame resolution 1920x1080 -> TC working on
- Movable
  - Must be same size as tiles -> I will fix this as soon as TC has the resizing working
  - Player
    - Light source -> Tess is struggling through this right now
    - Directional sound -> Tess working on
  - Zombies
    - Sprites + animations -> Tess implemented, need to implement MasterZombie when ready
    - Walking sound (different from player) -> Tess will implement
    - Ambient sound-> Tess implemented
  - Trap
    - Fix explosions -> Tess implemented
    - Light source ->  Tess working on light source still
    - Trigger sound -> Tess implemented


Control
- Relay messages between model components and to view components -> TC implemented
- Pathfinding (A*) -> Miri implemented
- Movable -> TC and Tess implemented
  - Player
    - Trap controls
  - Zombies -> TC implemented
    - Decision rate -> TC implemented
    - Spawning -> TC implemented
    - Synchronization (Master zombie sense) -> TC implementing??
  - Traps
    - Trigger-> Tess implemented
