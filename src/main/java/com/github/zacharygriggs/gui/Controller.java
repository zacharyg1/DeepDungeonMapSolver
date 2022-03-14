package com.github.zacharygriggs.gui;

import com.github.zacharygriggs.deepdungeon.DeepDungeonMap;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

    private static final String SAMPLE = "*********************\n" +
            "*          **    ** *\n" +
            "* ******** ** ** ** *\n" +
            "* ******** ** ** ** *\n" +
            "* **D         **    *\n" +
            "* ***************** *\n" +
            "* ***************** *\n" +
            "*    **       **    *\n" +
            "**** ** ***** ** ****\n" +
            "**** ** ***** ** ****\n" +
            "* **    ** ** ** ** *\n" +
            "* ******** ***** ** *\n" +
            "* ******** ***** ** *\n" +
            "*            U**    *\n" +
            "* ******** ******** *\n" +
            "* ******** ******** *\n" +
            "*       ** **    ** *\n" +
            "* ***** ***** ** ** *\n" +
            "* ***** ***** ** ** *\n" +
            "*    **       **    *\n" +
            "*********************";

    private TextArea input;
    private TextField output;

    public Controller(TextArea input, TextField output) {
        this.input = input;
        this.output = output;
    }

    public void generate(ActionEvent e) {
        try {
            DeepDungeonMap map = new DeepDungeonMap(input.getText());
            output.setText(map.solve().toString());
        } catch (Exception ex) {
            output.setText(ex.getMessage());
        }
    }

    public void example(ActionEvent e) {
        input.setText(SAMPLE);
    }
}
