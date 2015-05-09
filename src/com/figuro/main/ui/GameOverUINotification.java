package com.figuro.main.ui;

import com.figuro.engine.IGameoverCallback;

public class GameOverUINotification implements IGameoverCallback {

	public GameOverUINotification() {
	}

	@Override
	public void gameFinishedWith(String score) {
		// TODO:
		// 1. display dialog with message: score
		// 2. Ok button with event to showStartScreen()
	}

}
