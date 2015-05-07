package com.figuro.main.ui;

import javafx.scene.control.Label;

import com.figuro.common.IMessageSender;

public class UIMessage implements IMessageSender {
	private Label message;
	private Label score;
	
	public UIMessage(Label message, Label score) {
		super();
		this.message = message;
		this.score = score;
	}

	@Override
	public void displayMessage(String message) {
		this.message.setText(message);
	}

	@Override
	public void updateGameState(String gameStatus) {
		this.score.setText(gameStatus);
	}
}
