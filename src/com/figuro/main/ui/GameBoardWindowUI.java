package com.figuro.main.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class GameBoardWindowUI {
	public static VBox createGameBoardScreen(BorderPane root,Label messageLabel )
	{
		//TextField messageTextField = new TextField ();
	//	messageTextField.setText("Waiting for the other player move...");
		
		//messageLabel.setText("nini megy is");
		
		Rectangle gameBoard = new Rectangle(10, 10, 300, 200);
		gameBoard.setFill(Color.GRAY);
		
		VBox gameBoardWindow = new VBox();
		gameBoardWindow.setSpacing(10);
		gameBoardWindow.setPadding(new Insets(20)); 
		gameBoardWindow.getChildren().addAll(messageLabel,gameBoard);

        return gameBoardWindow;
	}
}
