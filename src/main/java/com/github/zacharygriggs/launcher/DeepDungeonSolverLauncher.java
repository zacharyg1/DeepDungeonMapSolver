package com.github.zacharygriggs.launcher;

import com.github.zacharygriggs.cli.DeepDungeonSolverCLI;
import com.github.zacharygriggs.gui.DeepDungeonSolverGUI;

/**
 * Runs the DD Solver program.
 * This is the entry point for both GUI and CLI based.
 */
public class DeepDungeonSolverLauncher {

    /**
     * Main entry point
     * Decides whether to run CLI or GUI
     *
     * @param args "gui" if GUI based. Empty or any other value for CLi based.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            launchCli();
        } else {
            String arg = args[0];
            if (arg.equalsIgnoreCase("gui")) {
                launchGui();
            } else {
                launchCli();
            }
        }
    }

    private static void launchCli() {
        new DeepDungeonSolverCLI().run();
    }

    private static void launchGui() {
        DeepDungeonSolverGUI.main(new String[0]);
    }
}
