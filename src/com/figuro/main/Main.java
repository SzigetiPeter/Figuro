package com.figuro.main;

import javafx.application.Application;
import javafx.stage.Stage;

import com.figuro.main.ui.MainWindowUI;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			MainWindowUI mui = new MainWindowUI(primaryStage);
			mui.StartGameMenu();
			// primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
