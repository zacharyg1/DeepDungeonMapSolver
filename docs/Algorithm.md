# Algorithm

This describes a generalized/language-neutral algorithm for solving a Deep Dungeon map. 
This algorithm takes place in two parts: loading the map, and solving the map.

This algorithm does not describe any error checking you will need. That is left up to the reader. My implementation also does
several additional things that aren't strictly necessary for solving the map.

## Loading the map

The load step converts the text map into an internal representation of a series of nodes. 
This can be thought of as a specialized type of Graph.

Before we start the algorithm, you will need to accept text from the user (the map). This will be
referred to as "text".

First, we will convert the map into a 2D Array of Character, and remember the origin location.

```
initialize(text) =

- Declare a 2D array of CHARACTER, 21 x 21 (INDICES)
- Separate the INPUT into lines (delimited by \n)
- For each line:
    - For each character in line:
        - Save the character in INDICES
        - If character == 'U'
            - Save location of ORIGIN
```

Now, we will iterate through the INDICES, discovering all routes, and going until we have discovered all rooms.
Assuming that "node" is the origin graph node, which will eventually be the root of a graph containing
all rooms of the dungeon floor.

```
explore(node,indices,x,y) = 

- Set the current INDICIES value to '*'
- For each direction (N/S/E/W):
    - Verify that one space in the desired direction is an empty (space1 = ' '), else continue with next direction
    - Verify that another space in the desired direction is an empty (space2 = ' '), else continue with next direction
    - Select the third space in the desired direction
    - Verify selected space is either ' ', '@', 'U', or 'D'
    - Create a new node, with the location being the selected space
    - Attach the new node to this node, in the searched direction
    - Attach this node to the new node, in the opposite of the searched direction
    - If this space is 'D', mark as the destination space.
    - Recursively explore the new node, i.e. explore(newNode,indices,...)
```

After running these algorithms, you will have produced an origin node which links
to the other nodes in the dungeon floor. You can now proceed to solve the map.

## Solving the Map

The Solving step will remove all nodes except the ones that lead to the end of the dungeon.
It will also produce a speedwalk to the end of the floor.
You can assume for simplicity's sake that a Speedwalk is an in-order list of directions.

This step assumes you already have a build map, with a known origin and exit room.

```
findNext(speedwalk, node) =

- If node is exit:
    - Return true
- Loop through each direction (N/E/S/W):
    - If the room has this direction as an exit:
        - Add this direction to your speedwalk
        - Select next node by moving in the current search direction from this node
        - Remove current search direction from this node
        - Remove opposite of search direction from next node
        - If findNext(speedwalk,nextNode) returns true:
            - Return true
- Remove the last added speedwalk direction
- Return false
```

And the top-level method to solve from the origin:

```
solve() =

- findNext(speedwalk, origin)
- Add final direction, 'd'
```