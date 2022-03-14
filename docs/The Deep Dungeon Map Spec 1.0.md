# Map Specification

The Deep Dungeon Map (or just "map") is a graphical representation of a deep dungeon floor which can be traversed. To see some real samples of a Deep Dungeon map, see the Samples.md file in this folder.

The map's height is 21 lines.
The map's width is 21 lines.

The starting room is delimited by a single 'U' character.
The ending room is delimited by a single 'D' character.
A room which is not a starting/ending room is delimited by a single ' ' (space) character

There are only four allowed directions. North, East, South, and West

If a room has a RIGHT (east) exit, there will be two space characters ' ' to the right of it, followed by another ' ', 'D', or 'U' character which is the origin of the right exit room.
If a room has a LEFT (west) exit, there will be two space characters ' ' to the left of it, followed by another ' ', 'D', or 'U' character which is the origin of the left exit room
If a room has a SOUTH exit, there will be two space characters ' ' to the south of it, followed by another ' ', 'D', or 'U' character which is the origin of the south exit room.
If a room has a NORTH exit, there will be two space characters ' ' to the north of it, followed by another ' ', 'D', or 'U' character which is the origin of the north exit room.

The two space ' ' characters before the origin can be safely ignored, as long as you do confirm that they are there.

The '*' character means a wall is in the way.