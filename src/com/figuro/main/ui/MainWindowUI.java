package com.figuro.main.ui;

import com.figuro.common.Builder;
import com.figuro.engine.GameJob;
import com.figuro.engine.GameRunner;
import com.figuro.engine.IGameoverCallback;
import com.figuro.engine.persistency.IPersistency;
import com.figuro.engine.persistency.Persistency;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainWindowUI {

	private Stage primaryStage;
	
	private BorderPane root;
	
	private MainWindowScreen mainScreenVBox;
	
	private Builder builder;
	
	private UIMessage uiMessage;
	
	private Label messageLabel;
	
	private IPersistency iPersistency;

	public MainWindowUI(Stage primaryStage) {
		super();
		this.primaryStage = primaryStage;
		
		this.messageLabel = new Label();
		messageLabel.setText("I am the label for messages...");
		
		Label scoreLabel = new Label();
		scoreLabel.setText("I am the label for scores...");
		
		
		this.uiMessage = new UIMessage(messageLabel, scoreLabel);
		builder = new Builder(primaryStage, this.uiMessage);
		iPersistency = new Persistency(uiMessage);
		
		root = new BorderPane();
		
		Label labelGameTitle = new Label("FIGURO");
        labelGameTitle.setMaxWidth(Double.MAX_VALUE);
        labelGameTitle.setAlignment(Pos.CENTER);
	    
        root.setTop(labelGameTitle);
        this.mainScreenVBox= new MainWindowScreen(iPersistency);
        
        this.mainScreenVBox.setNewGameAction(new EventHandler<ActionEvent>() {	       
            @Override
            public void handle(ActionEvent event) {                
            	root.setCenter(new NewGameScreen(root, builder, mainScreenVBox, messageLabel, uiMessage));
            }
        });
        
        this.mainScreenVBox.setLoadGameAction(new EventHandler<ActionEvent>() {	       
            @Override
            public void handle(ActionEvent event) {                
            	GameJob gameJob = new GameJob();
		           
 	           GameRunner gameRunner = new GameRunner(gameJob, builder, null, uiMessage, new Persistency(uiMessage));
 	           
 	           for (String player : iPersistency.getPlayers()) {
 	        	   gameRunner.addPlayer(player);
 	           }
 	           		
 				IGameoverCallback gameoverCallback = new IGameoverCallback(){

 					@Override
 					public void gameFinishedWith(String score) {
 						// TODO Auto-generated method stub
 						
 					}
 				};
 				gameRunner.runGame(iPersistency.getGame(),  gameoverCallback);
 				root.setCenter(new GameBoardWindowUI(messageLabel));
            }
        });
        
		root.setCenter(this.mainScreenVBox);

		Scene sceneMainWindow = new Scene(root,600,600);
		//sceneMainWindow.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        
        primaryStage.setTitle("Figuro");		        			
		primaryStage.setScene(sceneMainWindow);
		
	}
	
	public void StartGameMenu()
	{
		this.primaryStage.show();
	}
	
	
}