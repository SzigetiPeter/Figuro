package com.figuro.main.ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.figuro.common.Builder;
import com.figuro.common.IBuilder;
import com.figuro.engine.GameJob;
import com.figuro.engine.GameRunner;
import com.figuro.engine.IEngineHandler;
import com.figuro.engine.IGameoverCallback;
import com.figuro.engine.persistency.IPersistency;

public class MainWindowUI {

	private Stage primaryStage;

	private BorderPane root;

	private MainWindowScreen mainScreenVBox;

	private Builder builder;

	private UIMessage uiMessage;

	private Label messageLabel;
	
	private NewGameScreen ngs;

	public MainWindowUI(Stage primaryStage) {
		super();
		this.primaryStage = primaryStage;

		this.messageLabel = new Label();
		messageLabel.setText("I am the label for messages...");

		Label scoreLabel = new Label();
		scoreLabel.setText("I am the label for scores...");

		this.uiMessage = new UIMessage(messageLabel, scoreLabel);
		builder = new Builder(primaryStage, this.uiMessage);
		IPersistency iPersistency = builder.getPersistency();

		root = new BorderPane();

		Label labelGameTitle = new Label("FIGURO");
		labelGameTitle.setMaxWidth(Double.MAX_VALUE);
		labelGameTitle.setAlignment(Pos.CENTER);

		root.setTop(labelGameTitle);
		this.mainScreenVBox = new MainWindowScreen(iPersistency);

		this.mainScreenVBox.setNewGameAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ngs = new NewGameScreen(root, builder, mainScreenVBox,
						messageLabel, uiMessage, primaryStage);
				root.setCenter(ngs);
			}
		});

		this.mainScreenVBox.setLoadGameAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				IEngineHandler gameRunner = builder.createEngine();
				gameRunner.resumeGame(new GameOverUINotification(root, mainScreenVBox, builder,primaryStage));
				root.setCenter(new GameBoardWindowUI(messageLabel));
			}
		});

		root.setCenter(this.mainScreenVBox);

		Scene sceneMainWindow = new Scene(root, 600, 600);
		// sceneMainWindow.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		primaryStage.setTitle("Figuro");
		primaryStage.setScene(sceneMainWindow);

	}

	public void StartGameMenu() {
		Platform.setImplicitExit(false);

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent event) {
		    	if (ngs != null && ngs.GetIsGameStarted()==true)
		    	{   
		    		ngs.GameClosed();		    		
			    	builder.free();
					root.setCenter(mainScreenVBox);
					primaryStage.setScene(root.getScene());		    	
					event.consume();
		    	}
		    }
		});
		
		this.primaryStage.show();
	}
}