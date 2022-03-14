package com.github.zacharygriggs.deepdungeon;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.github.zacharygriggs.deepdungeon.DirectionUtils.verifyDirection;

/**
 * Represents a single room in the Deep Dungeon
 */
public class DeepDungeonMapNode {

    private Map<String, DeepDungeonMapNode> linkedMaps;

    private int x;
    private int y;

    private boolean isExit;
    private boolean isOrigin;

    /**
     * Makes a deep dungeon node with no attached maps
     */
    public DeepDungeonMapNode(int x, int y) {
        this.linkedMaps = new HashMap<>();
        this.x = x;
        this.y = y;
    }

    /**
     * Adds a node to this map
     *
     * @param direction Direction of the next map
     * @param newNode   The next map
     */
    public void addNode(String direction, DeepDungeonMapNode newNode) {
        direction = direction.toLowerCase();
        verifyDirection(direction);
        linkedMaps.put(direction, newNode);
    }

    /**
     * Removes an exit.
     *
     * @param direction Direction of the exit to remove
     */
    public void unlinkDirection(String direction) {
        direction = direction.toLowerCase();
        verifyDirection(direction);
        linkedMaps.remove(direction);
    }

    /**
     * Gets the next room
     *
     * @param direction Direction to get
     * @return Next room
     */
    public DeepDungeonMapNode traverse(String direction) {
        direction = direction.toLowerCase();
        verifyDirection(direction);
        if (linkedMaps.containsKey(direction)) {
            return linkedMaps.get(direction);
        } else {
            throw new RuntimeException("Attempted to go an illegal route. Last direction: " + direction);
        }
    }

    /**
     * Sets this as the origin space
     */
    public void markAsOrigin() {
        this.isOrigin = true;
    }

    /**
     * Sets this as the exit space
     */
    public void markAsExit() {
        this.isExit = true;
    }

    /**
     * Gets all valid directions/room exits
     *
     * @return All room exits
     */
    public Set<String> getValidDirections() {
        return linkedMaps.keySet();
    }

    /**
     * Returns whether or not this is the destination room
     *
     * @return True if destination; false if not
     */
    public boolean isExit() {
        return isExit;
    }

    /**
     * Provides a String representation of the
     * object, marking the x,y coordinates
     *
     * @return String representation
     */
    @Override
    public String toString() {
        return "DeepDungeonMapNode{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
