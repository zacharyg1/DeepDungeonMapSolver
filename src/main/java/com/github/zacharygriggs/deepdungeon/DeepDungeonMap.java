package com.github.zacharygriggs.deepdungeon;

import java.util.HashSet;

/**
 * Represents a deep dungeon map which can be loaded from
 * a String representation of the map, and solved to
 * discover a speedwalk to the end.
 */
public class DeepDungeonMap {

    private DeepDungeonMapNode origin;

    private static final char ORIGIN_SPACE = 'U';
    private static final char DESTINATION_SPACE = 'D';
    private static final char PLAYER_SPACE = '@';
    private static final char EMPTY_SPACE = ' ';
    private static final char WALL_SPACE = '*';

    private static final int HEIGHT = 21;
    private static final int WIDTH = 21;

    private boolean solved;
    private Speedwalk solution;

    /**
     * Creates a Deep Dungeon Map object from the
     * given String representation.
     *
     * @param representation String representation
     *                       Pulled from the UOSSMud game
     */
    public DeepDungeonMap(String representation) {
        this.origin = deserialize(representation);
        this.solution = new Speedwalk();
        this.solved = false;
    }

    /**
     * Gets the origin node.
     *
     * @return Origin node
     */
    public DeepDungeonMapNode getOrigin() {
        return origin;
    }

    /**
     * Recursive search for the next direction to move
     * towards to finish the floor.
     *
     * @param sw   Speedwalk
     * @param node This node
     * @return True if search succeeds; false if not.
     */
    public boolean findNextDirection(Speedwalk sw, DeepDungeonMapNode node) {
        // If we found an exit, return true.
        // Returning true will stop the search immediately and
        // recurse the result up the chain.
        if (node.isExit()) {
            System.out.println("Found an exit - returning");
            return true;
        }
        // Attempt to walk all valid directions
        for (String dir : new HashSet<>(node.getValidDirections())) {
            // Add the direction to the speedwalk optimistically
            // We don't know if it's right yet, but we'll remove
            // it later if it's not right.
            sw.addDirection(dir);
            System.out.println("Proceeding " + dir + " from " + node.toString());
            // Before we traverse forward, remove these exits so we don't take them again
            DeepDungeonMapNode nextNode = node.traverse(dir);
            node.unlinkDirection(dir);
            nextNode.unlinkDirection(DirectionUtils.reverseDirection(dir));
            // Recursively search the next room. This only returns
            // true if we found an exit.
            if (findNextDirection(sw, nextNode)) {
                // We found an exit
                System.out.println("Found an exit");
                return true;
            } // Else, we simply continue traversing. No action to take.
        }
        // Walked all paths and didn't find an answer.
        // That means this room is a dead end.
        // We remove the last entry in the speedwalk because this isn't the direction to go
        // and return false to continue the search
        System.out.println("Found a dead end - backtracking");
        sw.removeLast(1);
        return false;
    }

    /**
     * Solves the map.
     * At the end of solving, the only remaining path
     * through this map will lead to the stairs down.
     * <p>
     * Also returns a speedwalk to the stairs.
     *
     * @return Speedwalk to stairs down
     */
    public Speedwalk solve() {
        // If we already solved it, no need to repeat.
        if (!solved) {
            findNextDirection(solution, origin);
            // Finally, add the down direction to proceed to next floor.
            solution.addDirection("d");
        }
        return solution;
    }


    /**
     * Converts the String representation
     * of the map into a deep dungeon map node
     *
     * @param data map data
     * @return Origin node
     */
    private DeepDungeonMapNode deserialize(String data) {
        String[] lines = data.split("\n");
        checkMapIntegrity(lines);

        // Holds the starting locations (the 'U' character)
        int startX = -1;
        int startY = -1;

        boolean foundOrigin = false;
        boolean foundExit = false;

        // Populate an array holding the characters from the map
        // for easy lookup later.
        char[][] indices = new char[HEIGHT][WIDTH];
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                indices[y][x] = lines[y].charAt(x);
                if (indices[y][x] == ORIGIN_SPACE) {
                    startX = x;
                    startY = y;
                    foundOrigin = true;
                } else if (indices[y][x] == DESTINATION_SPACE) {
                    foundExit = true;
                }
            }
        }

        if (!foundExit || !foundOrigin) {
            throw new RuntimeException("Map cannot be solved (no entrance/exit)");
        }

        System.out.println("Found origin at " + startX + ", " + startY);

        DeepDungeonMapNode origin = new DeepDungeonMapNode(startX, startY);
        origin.markAsOrigin();
        explore(origin, indices, startX, startY);
        return origin;
    }

    /**
     * Explores all directions from this one
     *
     * @param thisNode Node to explore from
     * @param indices  Current map indices
     * @param x        X location of node
     * @param y        Y location of node
     */
    private void explore(DeepDungeonMapNode thisNode, char[][] indices, int x, int y) {
        // Starting at this node, we will explore all directions
        // We'd rather not go back to this node.
        // So we block it off on the indices
        indices[y][x] = WALL_SPACE;

        // Let's explore each of the four directions.
        exploreDirection(thisNode, indices, x, y, 0, 1, "s"); // South
        exploreDirection(thisNode, indices, x, y, 0, -1, "n"); // North
        exploreDirection(thisNode, indices, x, y, 1, 0, "e"); // East
        exploreDirection(thisNode, indices, x, y, -1, 0, "w"); // West
    }

    /**
     * Generic explore direction method
     * Can explore s/w/n/e (implemented)
     * and also nw/ne/sw/se (not used)
     *
     * @param thisNode      Node to explore from
     * @param indices       Current map indices
     * @param x             X location of node
     * @param y             Y location of node
     * @param xDiff         Amount to move in the x dir (1 or -1)
     * @param yDiff         Amount to move in the y dir (1 or -1)
     * @param thisDirection The letter representation of this direction
     */
    private void exploreDirection(DeepDungeonMapNode thisNode, char[][] indices, int x, int y,
                                  int xDiff, int yDiff, String thisDirection) {
        try {
            // In order for this to be valid, the two spaces below this one (y + 1) and (y+ 2) must be empty
            char emptySpace1 = indices[y + yDiff][x + xDiff];
            char emptySpace2 = indices[y + (yDiff * 2)][x + (xDiff * 2)];
            if (emptySpace1 == EMPTY_SPACE && emptySpace2 == EMPTY_SPACE) {
                // The origin space can be any valid space delimiter
                char roomSpace = indices[y + (yDiff * 3)][x + (xDiff * 3)];
                if (roomSpace == EMPTY_SPACE || roomSpace == PLAYER_SPACE ||
                        roomSpace == DESTINATION_SPACE || roomSpace == ORIGIN_SPACE) {
                    // This is a valid map square.
                    DeepDungeonMapNode newNode = new DeepDungeonMapNode(x + (xDiff * 3), y + (yDiff * 3));
                    // Add exits to the maps that need it
                    thisNode.addNode(thisDirection, newNode);
                    newNode.addNode(DirectionUtils.reverseDirection(thisDirection), thisNode);
                    // Mark this as origin or exit as needed
                    if (roomSpace == ORIGIN_SPACE) {
                        newNode.markAsOrigin();
                    } else if (roomSpace == DESTINATION_SPACE) {
                        newNode.markAsExit();
                    }
                    // Explore routes from this one
                    explore(newNode, indices, x + (xDiff * 3), y + (yDiff * 3));
                }
            }
        } catch (Exception ex) {
            // We went out of bounds, so it's not traversable.
            // This isn't a bad thing.
        }
    }

    /**
     * Checks that the map has the proper amount of lines,
     * and proper amount of characters per line.
     *
     * @param lines Map lines
     */
    private void checkMapIntegrity(String[] lines) {
        if (lines.length != HEIGHT) {
            throw new RuntimeException("Invalid map data (height == " + lines.length + ")");
        }
        for (int y = 0; y < HEIGHT; y++) {
            String line = lines[y];
            if (line.length() != WIDTH) {
                throw new RuntimeException("Invalid map data (width == " + line.length() + ")");
            }
        }
    }
}
