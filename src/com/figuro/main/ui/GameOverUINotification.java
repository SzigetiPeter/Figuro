package com.figuro.main.ui;

import javafx.scene.Scene;

import com.figuro.engine.IGameoverCallback;

public class GameOverUINotification implements IGameoverCallback {
	private Scene scene;

	public GameOverUINotification(Scene scene) {
		this.scene = scene;
	}

	@Override
	public void gameFinishedWith(String score) {
		// 1. display dialog with message: score
		// 2. Ok button with event to showStartScreen()
	}

}
