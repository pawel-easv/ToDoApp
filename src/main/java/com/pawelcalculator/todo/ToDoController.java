package com.pawelcalculator.todo;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.w3c.dom.events.Event;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ToDoController implements Initializable {
    @FXML private Button btnNewTask;
    @FXML private VBox vbox1;
    @FXML private VBox vbox2;
    @FXML private VBox vbox3;
    @FXML private AnchorPane anchorPane;
    private List<TextField> tasks = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        makeVBoxAcceptDrop(vbox1);
        makeVBoxAcceptDrop(vbox2);
        makeVBoxAcceptDrop(vbox3);
        anchorPane.setOnMouseClicked(event -> {
            for(TextField task: tasks) {
                task.setDisable(true);
            }
        });
    }

    @FXML
    private void addNewTask() {
        HBox hbox = new HBox();
        TextField textField = new TextField("New Task");
        tasks.add(textField);
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                textField.setDisable(true);
            }
        });
        Button editTask = new Button("🖌️");
        Button removeTask = new Button("❌");

        editTask.setOnMouseClicked(event -> {
            textField.setDisable(false);
        });

        removeTask.setOnMouseClicked(event -> {
            VBox vbox = (VBox) hbox.getParent();
            vbox.getChildren().remove(hbox);
        });
        hbox.setOnDragDetected(event -> {
            Dragboard db = hbox.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString("HBox");
            db.setContent(content);
            event.consume();
        });
        //editTask.getStyleClass().add("superButton");
        //removeTask.getStyleClass().add("superButton");
        editTask.setMinWidth(40);
        editTask.setPrefHeight(40);
        removeTask.setMinWidth(40);
        removeTask.setPrefHeight(40);
        textField.setPrefWidth(Integer.MAX_VALUE);
        textField.setPrefHeight(40);
        hbox.getChildren().add(textField);
        hbox.getChildren().add(editTask);
        hbox.getChildren().add(removeTask);
        vbox1.getChildren().add(hbox);

    }

    private void makeVBoxAcceptDrop(VBox vbox) {
        vbox.setOnDragOver(event -> {
            if (event.getGestureSource() != vbox && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        vbox.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                HBox sourceHBox = (HBox) event.getGestureSource();
                VBox parentVBox = (VBox) sourceHBox.getParent();
                parentVBox.getChildren().remove(sourceHBox);
                vbox.getChildren().add(sourceHBox);
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }
}