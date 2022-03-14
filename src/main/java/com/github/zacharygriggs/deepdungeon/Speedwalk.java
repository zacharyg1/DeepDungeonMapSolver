package com.github.zacharygriggs.deepdungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a speedwalk which stores
 * directions to the end of the floor.
 */
public class Speedwalk {

    private List<String> speedwalk;

    /**
     * Makes an empty speedwalk
     */
    public Speedwalk() {
        this.speedwalk = new ArrayList<>();
    }

    /**
     * Adds a direction to this speedwalk on the end
     *
     * @param newDirection Direction to add
     */
    public void addDirection(String newDirection) {
        newDirection = newDirection.toLowerCase();
        DirectionUtils.verifyDirection(newDirection);
        speedwalk.add(newDirection);
    }

    /**
     * Removes last directions
     *
     * @param count Amount to remove
     */
    public void removeLast(int count) {
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                speedwalk.remove(speedwalk.size() - 1);
            }
        }
    }

    /**
     * Gets the final direction
     *
     * @return Final direction
     */
    public String getLastDirection() {
        return speedwalk.get(speedwalk.size() - 1);
    }

    /**
     * Converts speedwalk into usable String format
     *
     * @return UOSSMud compatible String
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String s : speedwalk) {
            builder.append(s).append(";");
        }
        if (builder.length() > 0) {
            builder.setLength(builder.length() - 1);
        }
        return builder.toString();
    }
}
