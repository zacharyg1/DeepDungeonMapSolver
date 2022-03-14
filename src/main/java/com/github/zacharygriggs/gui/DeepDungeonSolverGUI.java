package com.github.zacharygriggs.gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class DeepDungeonSolverGUI extends Application {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;

    private TextArea mapArea;
    private TextField results;
    private Button generateButton;
    private Button example;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new VBox();
        root.setMinWidth(WIDTH);
        root.setMaxWidth(WIDTH);
        root.setPrefWidth(WIDTH);
        root.setMaxHeight(HEIGHT);
        root.setMinHeight(HEIGHT);
        root.setPrefHeight(HEIGHT);
        Scene scene = new Scene(root);
        primaryStage.setTitle("Speedwalk Solver");
        primaryStage.setScene(scene);
        primaryStage.show();

        mapArea = new TextArea();
        generateButton = new Button("Solve Map");
        example = new Button("Show Example Map");
        results = new TextField();
        results.setText("No results yet");
        root.getChildren().add(new Label(" "));
        root.getChildren().add(new Label("Enter map data:"));
        root.getChildren().add(mapArea);
        root.getChildren().add(new Label(" "));
        root.getChildren().add(generateButton);
        root.getChildren().add(new Label(" "));
        root.getChildren().add(example);
        root.getChildren().add(new Label(" "));
        root.getChildren().add(new Label("Results:"));
        root.getChildren().add(results);

        Controller controller = new Controller(mapArea, results);
        generateButton.setOnAction(controller::generate);
        example.setOnAction(controller::example);

        primaryStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
