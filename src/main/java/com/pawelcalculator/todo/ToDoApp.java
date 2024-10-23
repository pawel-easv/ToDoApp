package com.pawelcalculator.todo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ToDoApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ToDoApp.class.getResource("todo-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add("../resources/Main.css");
        stage.setTitle("My ToDo Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}