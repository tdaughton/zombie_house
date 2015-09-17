This is Miri changing this line
# zombie_house
CS351 Project 1

Everyone I am updating the structure that I've set up for the project so please wait for a  bit before changing anything. I sent you a picture of the new structure so that you can get an idea of what it will look like and what each of us will be doing.

TODO:
Model
- Connect MapGenerator to working code
- Level up
- Movable
  - Make intersections work
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
  - Sprites + animations
  - Must be same size as tiles
  - Player
    - Light source
    - Walking sound
    - Running sound
    - Directional sound
  - Zombies
    - Walking sound (different from player)
    - Ambient sound
  - Trap
    - Light source
    - Trigger sound


Control
- Timing (deltaSeconds) for decisions
- Relay messages between model components and to view components
- Movable
  - Player
    - Refactor movement from GamePanel to some control class (forward the key events and relay to Player model, maybe)
    - Running controls
    - Trap controls
  - Zombies
    - Decision rate
    - Spawning
    - Pathfinding (A*)
    - Synchronization (Master zombie sense)
  - Traps
    - Spawning
