package com.github.zacharygriggs.deepdungeon;

/**
 * Utility class for game directions
 */
public class DirectionUtils {

    /**
     * Verifies that this is a legitimate direction.
     *
     * @param originalDirection Direction to check
     */
    public static void verifyDirection(String originalDirection) {
        originalDirection = originalDirection.toLowerCase();
        if (!originalDirection.equalsIgnoreCase("n") && !originalDirection.equalsIgnoreCase("w") &&
                !originalDirection.equalsIgnoreCase("e") && !originalDirection.equalsIgnoreCase("s") &&
                !originalDirection.equalsIgnoreCase("d") && !originalDirection.equalsIgnoreCase("u")) {
            throw new RuntimeException("Invalid direction: " + originalDirection);
        }
    }

    /**
     * Gets the opposite of this direction.
     *
     * @param originalDirection Direction to reverse
     * @return Opposite direction
     */
    public static String reverseDirection(String originalDirection) {
        originalDirection = originalDirection.toLowerCase();
        if (originalDirection.equals("s")) {
            return "n";
        } else if (originalDirection.equals("n")) {
            return "s";
        } else if (originalDirection.equals("w")) {
            return "e";
        } else if (originalDirection.equals("e")) {
            return "w";
        }
        throw new RuntimeException("Invalid direction (irreversible): " + originalDirection);
    }
}
