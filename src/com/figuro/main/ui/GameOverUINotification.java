package com.figuro.main.ui;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.figuro.common.IBuilder;
import com.figuro.engine.IGameoverCallback;

public class GameOverUINotification implements IGameoverCallback {
	private BorderPane root;
	private VBox mainScreenVBox;
	private IBuilder builder;
	private Stage primaryStage;
	
	public GameOverUINotification(BorderPane root,VBox mainScreenVBox,IBuilder builder,Stage primaryStage) {
		this.root = root;
		this.mainScreenVBox = mainScreenVBox;
		this.builder = builder;
		this.primaryStage = primaryStage;
	}

	@Override
	public void gameFinishedWith(String score) {
		//1. display dialog with message: score
		//2. Ok button with event to showStartScreen()
		
		Platform.runLater(new Runnable() {
            @Override
            public void run() {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Game finished");
				alert.setHeaderText(null);
				alert.setContentText(score);
				alert.showAndWait();
				
				builder.free();
				root.setCenter(mainScreenVBox);
				primaryStage.setScene(root.getScene());
            }
        });
	}

}