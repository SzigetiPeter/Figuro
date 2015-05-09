package com.figuro.main.ui;

import com.figuro.common.IBuilder;
import com.figuro.engine.GameJob;
import com.figuro.engine.GameRunner;
import com.figuro.engine.IGameoverCallback;
import com.figuro.engine.persistency.Persistency;
import com.figuro.player.IPlayer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NewGameScreen extends VBox {
	
	private  IBuilder builder;
	private BorderPane root;
	private Label messageLabel;
	private boolean startGame = false;
	
	public NewGameScreen(BorderPane root, IBuilder builder,VBox mainScreenVBox, Label messageLabel,UIMessage uiMessage){
		this.root = root;
		this.builder = builder;
		this.messageLabel = messageLabel;
		
		GridPane gridGameTypes = new GridPane();
        gridGameTypes.setVgap(4);
        gridGameTypes.setPadding(new Insets(5, 5, 5, 5));
		
        final ComboBox priorityComboBox = new ComboBox();
        
        String[] gameTypes = builder.getGameTypes();
        
        for (String string : gameTypes) {
        	priorityComboBox.getItems().add(string);
		}        
        
        gridGameTypes.add(priorityComboBox, 0, 0);
        
        final ToggleGroup group = new ToggleGroup();
        RadioButton rbNetGame = new RadioButton("Net game");
        rbNetGame.setToggleGroup(group);
        rbNetGame.setSelected(true);
        gridGameTypes.add(rbNetGame, 0, 1);
        
        RadioButton rbPlayerBot = new RadioButton("Player vs. Bot");
        rbPlayerBot.setToggleGroup(group);
        gridGameTypes.add(rbPlayerBot, 0, 2);
        
        RadioButton rbBotBot = new RadioButton("Bot vs. Bot");
        rbBotBot.setToggleGroup(group);
        gridGameTypes.add(rbBotBot, 0, 3);
        
        HBox btnBox = new HBox();
        Button backButton = new Button();
        backButton.setText("Back");
        
        backButton.setOnAction(new EventHandler<ActionEvent>() {
	        
            @Override
            public void handle(ActionEvent event) {                
            	root.setCenter(mainScreenVBox);
            }
        });
        
        Button playerButton = new Button();
        playerButton.setText("Next");
        
        playerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { 
            	if (priorityComboBox.getValue() == null)
            	{
            		Alert alert = new Alert(AlertType.INFORMATION);
                	alert.setTitle("Information");
                	alert.setHeaderText("Please select game type first");
        			alert.setContentText(null);
        			alert.showAndWait();
            		return;
            	}            	       	
            	
            	String setupName = "";
            	if (rbNetGame.isSelected())
            		setupName = PlayerSetupFactory.NET_GAME;
            	else if (rbPlayerBot.isSelected())
            		setupName = PlayerSetupFactory.PLAYER_VS_BOT;
            	else 
            		setupName = PlayerSetupFactory.BOT_VS_BOT;
            	
            	PlayerSetupFactory m = new PlayerSetupFactory();
            	PlayerSetupTemplate playerTemplate = m.createSetup(setupName);
            	
            	String player1 = playerTemplate.getPlayer1();            	            	
            	String player2 = playerTemplate.getPlayer2();     
            	String spectator = playerTemplate.getSpectator();
            	
            	SetupPlayerIfNeeded(player1);
            	SetupPlayerIfNeeded(player2);
            	
		           GameJob gameJob = new GameJob();
		           
		           GameRunner gameRunner = new GameRunner(gameJob, builder, null, uiMessage, new Persistency(uiMessage));
		           gameRunner.addPlayer(player1);
		           gameRunner.addPlayer(player2);
		           
					if (spectator != null)
					{
						gameRunner.addSpectator(spectator);	
					}		
					IGameoverCallback gameoverCallback = new GameOverUINotification(root, mainScreenVBox,builder);
					gameRunner.runGame(priorityComboBox.getValue().toString(),  gameoverCallback);
					
					if (startGame)
	            	{
	            		ShowGameBoard();	
	            	}
            }
    });
        btnBox.getChildren().addAll(backButton,playerButton);
        gridGameTypes.add(btnBox, 0, 4);
        
        TitledPane gameTypeGroup = new TitledPane();
        gameTypeGroup.setCollapsible(false);
        gameTypeGroup.setText("New game");
        gameTypeGroup.setContent(gridGameTypes);

        this.setSpacing(10);
        this.setPadding(new Insets(20)); 
        this.getChildren().addAll(gameTypeGroup);            
	}
	
	private void SetupPlayerIfNeeded(String player)
	{
		IPlayer playerType;
		playerType = this.builder.createPlayer(player);
	   	if (playerType.needsSetup())
	   	{
	   		startGame = false;
	   		BorderPane secondaryLayout = new BorderPane();
	   		HBox hb = new HBox();
	   		hb.setAlignment(Pos.CENTER);
	   		Button btnAddPlayer = new Button();
	   		btnAddPlayer.setPadding(new Insets(5));
	   		hb.getChildren().add(btnAddPlayer);
	   		btnAddPlayer.setText("Add player");
	   		btnAddPlayer.setAlignment(Pos.CENTER);
	   		
	   		btnAddPlayer.setOnAction(new EventHandler<ActionEvent>() {	        
	            @Override
	            public void handle(ActionEvent event) {
	            	Stage stage = (Stage) btnAddPlayer.getScene().getWindow();
	                stage.close();
	            	ShowGameBoard();
	            }
	        });
	   		
	   		Group groupSetupPlayer = new Group(); 
	   			   		
            playerType.setup(groupSetupPlayer, btnAddPlayer);
            secondaryLayout.setCenter(groupSetupPlayer);
            secondaryLayout.setBottom(hb);            
            Scene secondScene = new Scene(secondaryLayout, 300, 300);

            Stage secondStage = new Stage();
            secondStage.setTitle(player + " setup");
            secondStage.setScene(secondScene);
            secondStage.centerOnScreen();
            secondStage.initModality(Modality.APPLICATION_MODAL);
            secondStage.show();
	   	}	
	   	else
	   	{
	   	 startGame = true;
	   	}
	   	
	}

	private void ShowGameBoard()
	{
		root.setCenter(new GameBoardWindowUI(messageLabel));
	}
}
