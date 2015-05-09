package com.figuro.main.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameBoardWindowUI extends VBox {
	public GameBoardWindowUI(Label messageLabel) {
		Rectangle gameBoard = new Rectangle(10, 10, 300, 200);
		gameBoard.setFill(Color.GRAY);

		this.setSpacing(10);
		this.setPadding(new Insets(20));
		this.getChildren().addAll(messageLabel, gameBoard);

	}
}
