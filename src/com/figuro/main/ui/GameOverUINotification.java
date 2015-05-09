package com.figuro.main.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import com.figuro.common.IBuilder;
import com.figuro.engine.IGameoverCallback;

public class GameOverUINotification implements IGameoverCallback {
	private BorderPane scene;
	private VBox mainScreenVBox;
	private IBuilder builder;

	public GameOverUINotification(BorderPane scene,VBox mainScreenVBox,IBuilder builder) {
		this.scene = scene;
		this.mainScreenVBox = mainScreenVBox;
		this.builder = builder;
	}
	
	@Override
	public void gameFinishedWith(String score) {
		//1. display dialog with message: score
		//2. Ok button with event to showStartScreen()
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Game finished");
		alert.setHeaderText(null);
		alert.setContentText(score);
		alert.showAndWait();
		
		builder.free();
		scene.setCenter(mainScreenVBox);
	}

}
