package com.github.zacharygriggs.cli;

import com.github.zacharygriggs.deepdungeon.DeepDungeonMap;
import com.github.zacharygriggs.deepdungeon.Speedwalk;

import java.util.Scanner;

public class DeepDungeonSolverCLI {

    public DeepDungeonSolverCLI() {

    }

    public void run() {
        Scanner in = new Scanner(System.in);
        System.out.println("---- Deep Dungeon Solver ----");
        System.out.println("Please paste in a Deep Dungeon map.");
        System.out.println("The program will automatically solve it after all lines have been pasted.");
        System.out.println("-----------------------------");

        StringBuilder map = new StringBuilder();
        for(int i = 0; i < 21; i++) {
            map.append(in.nextLine()).append("\n");
        }
        if(map.length() > 0) {
            map.setLength(map.length() - 1);
        }

        DeepDungeonMap ddMap = new DeepDungeonMap(map.toString());
        Speedwalk sw = ddMap.solve();
        System.out.println("------    Solution    ------");
        System.out.println(sw);
        System.out.println("----------------------------");
        String answer = "";
        while(!answer.equalsIgnoreCase("n") && !answer.equalsIgnoreCase("y")) {
            System.out.println("Would you like to solve another map? (y/n)");
            answer = in.nextLine();
        }
        if(answer.equalsIgnoreCase("y")) {
            this.run();
        }
    }

}
