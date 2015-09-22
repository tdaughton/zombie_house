# zombie_house
CS351 Project 1

Everyone I am updating the structure that I've set up for the project so please wait for a  bit before changing anything. I sent you a picture of the new structure so that you can get an idea of what it will look like and what each of us will be doing.

Model
- Level up
- Movable
  - Fix intersections when moving diagonally
  - Player
    - Stamina + regen
    - Set traps
    - Pick up traps
    - Die
    - Win
  - Zombies
    - Smell
    - Random walk
    - Line walk
    - Master zombie
    - Die
  - Traps
    - Trigger placed traps

View
- Resizing frame must resize graphics
  - Tiles must be 80x80 when frame resolution 1920x1080
- Movable
  - Must be same size as tiles
  - Player
    - Light source
    - Directional sound
  - Zombies
    - Sprites + animations
    - Walking sound (different from player)
    - Ambient sound
  - Trap
    - Fix explosions
    - Light source
    - Trigger sound


Control
- Relay messages between model components and to view components
- Pathfinding (A*)
- Movable
  - Player
    - Trap controls
  - Zombies
    - Decision rate
    - Spawning
    - Synchronization (Master zombie sense)
  - Traps
    - Trigger
