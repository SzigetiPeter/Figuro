package com.figuro.main.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.figuro.engine.persistency.IPersistency;

public class MainWindowScreen extends VBox {
	private Button btnNewGame;
	private Button btnResumeLastGame;

	public MainWindowScreen(IPersistency iPersistency) {
		btnResumeLastGame = new Button();
		btnResumeLastGame.setText("Resume last game");
		btnResumeLastGame.setMaxWidth(Double.MAX_VALUE);

		btnResumeLastGame.setDisable(!iPersistency.isGameSaved());

		btnResumeLastGame.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

			}
		});

		this.btnNewGame = new Button();
		btnNewGame.setText("New game");
		btnNewGame.setMaxWidth(Double.MAX_VALUE);

		Button btnExitGame = new Button();
		btnExitGame.setText("Exit");
		btnExitGame.setMaxWidth(Double.MAX_VALUE);

		btnExitGame.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = (Stage) btnExitGame.getScene().getWindow();
				stage.close();
			}
		});

		this.setSpacing(10);
		this.setPadding(new Insets(20));
		this.getChildren().addAll(btnResumeLastGame, btnNewGame, btnExitGame);
	}

	public void setNewGameAction(EventHandler<ActionEvent> event) {
		btnNewGame.setOnAction(event);
	}

	public void setLoadGameAction(EventHandler<ActionEvent> event) {
		btnResumeLastGame.setOnAction(event);
	}

}
