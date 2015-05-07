package com.figuro.main.ui;

import com.figuro.common.Builder;
import com.figuro.common.IBuilder;
import com.figuro.common.IMessageSender;
import com.figuro.engine.IEngineHandler;
import com.figuro.player.IPlayer;
import com.figuro.player.netplayer.NetPlayer;
import com.figuro.player.uiplayer.UIPlayer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainWindowUI {
	
	private Stage primaryStage;
	
	private BorderPane root;
	
	private VBox MainScreenVBox;
	
	private Builder builder;
	
	private UIMessage uiMessage;
	
	public UIMessage getUiMessage() {
		return uiMessage;
	}

	public void setUiMessage(UIMessage uiMessage) {
		this.uiMessage = uiMessage;
	}

	public Builder getBuilder() {
		return builder;
	}

	public void setBuilder(Builder builder) {
		this.builder = builder;
	}

	public VBox getMainScreenVBox() {
		return MainScreenVBox;
	}

	public void setMainScreenVBox(VBox mainScreenVBox) {
		MainScreenVBox = mainScreenVBox;
	}

	public BorderPane getRoot() {
		return root;
	}

	public void setRoot(BorderPane root) {
		this.root = root;
	}

	public MainWindowUI(Stage primaryStage) {
		super();
		this.primaryStage = primaryStage;
		
		Label messageLabel = new Label();
		messageLabel.setText("I am the label for messages...");
		
		Label scoreLabel = new Label();
		scoreLabel.setText("I am the label for scores...");
		
		this.setUiMessage(new UIMessage(messageLabel, scoreLabel));
		
		this.setBuilder(new Builder(primaryStage, this.getUiMessage()));
		
		this.setRoot(new BorderPane());
		
		Label labelGameTitle = new Label("FIGURO");
        labelGameTitle.setMaxWidth(Double.MAX_VALUE);
        labelGameTitle.setAlignment(Pos.CENTER);
	    
        root.setTop(labelGameTitle);
        this.setMainScreenVBox(this.createMainWindowScreen());
        
		root.setCenter(this.getMainScreenVBox());

		Scene sceneMainWindow = new Scene(root,600,600);
		//sceneMainWindow.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        
        primaryStage.setTitle("Figuro");		        			
		primaryStage.setScene(sceneMainWindow);
		
	}

	public VBox createMainWindowScreen()
	{
		Button btnResumeLastGame = new Button();
        btnResumeLastGame.setText("Resume last game");
        btnResumeLastGame.setMaxWidth(Double.MAX_VALUE);
        
        Button btnNewGame = new Button();
        btnNewGame.setText("New game");
        btnNewGame.setMaxWidth(Double.MAX_VALUE);
        
        btnNewGame.setOnAction(new EventHandler<ActionEvent>() {	       
            @Override
            public void handle(ActionEvent event) {                
            	root.setCenter(createNewGameScreen());
            }
        });
        
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
        
        VBox mainWindow = new VBox();
        mainWindow.setSpacing(10);
        mainWindow.setPadding(new Insets(20)); 
        mainWindow.getChildren().addAll(btnResumeLastGame,btnNewGame,btnExitGame);
        root.setCenter(mainWindow);
      
		return mainWindow;
	}
	
	public VBox createNewGameScreen()
	{
        GridPane gridGameTypes = new GridPane();
        gridGameTypes.setVgap(4);
        gridGameTypes.setPadding(new Insets(5, 5, 5, 5));
		
        final ComboBox priorityComboBox = new ComboBox();        
        
        //priorityComboBox.getItems().add("Please select game...");
        
        String[] gameTypes = this.getBuilder().getGameTypes();
        
        for (String string : gameTypes) {
        	priorityComboBox.getItems().add(string);
		}        
        
        //priorityComboBox.setValue("Please select game...");
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
            	root.setCenter(getMainScreenVBox());
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
            	
            	Button btnAddPlayer = new Button();              
                Group secondaryLayout = new Group();            	
                
            	String setupName = "";
            	if (rbNetGame.isSelected())
            		setupName = "net-game";
            	else if (rbPlayerBot.isSelected())
            		setupName = "player-vs-bot";
            	else 
            		setupName = "bot-vs-bot";
            	
            	PlayerSetupFactory m = new PlayerSetupFactory();
            	PlayerSetupTemplate playerTemplate = m.createSetup(setupName);
            	
            	String player1 = playerTemplate.getPlayer1();            	            	
            	String player2 = playerTemplate.getPlayer2();     
            	
            	Alert alert = new Alert(AlertType.INFORMATION);
            	alert.setTitle("Information");
            	
            	IPlayer playerType;
            	
            	switch (player1){
            		case IBuilder.NET_PLAYER:
            			 playerType = new NetPlayer(getUiMessage());
            			 getBuilder().createPlayer(IBuilder.NET_PLAYER);
            			if (playerType.needsSetup())
            			{
                			alert.setHeaderText("Much greater news for Player1");
                			alert.setContentText(player1 +" setup is coming...!");
                			alert.showAndWait();
                			playerType.setup(secondaryLayout, btnAddPlayer);            				
            			}
            			else
            			{
            				alert.setHeaderText("Great news for Player1");
                			alert.setContentText(player1 +" doesn't need setup!");
                			alert.showAndWait();
            			}
            			break;
            			
            		case IBuilder.UI_PLAYER:
            			playerType = new UIPlayer(primaryStage);
            			getBuilder().createPlayer(IBuilder.UI_PLAYER);
            			
            			if (playerType.needsSetup())
            			{
            				alert.setHeaderText("Much greater news for Player1");
                			alert.setContentText(player1 +" setup is coming...!");
                			alert.showAndWait();
                			playerType.setup(secondaryLayout, btnAddPlayer);
            			}
            			else
            			{
            				alert.setHeaderText("Great news for Player1");
                			alert.setContentText(player1 +" doesn't need setup!");
                			alert.showAndWait();
            			}
            			break;
            			
            		case IBuilder.ALPHABETA_PLAYER:
            			alert.setHeaderText("Even more greater news for Player1");
            			alert.setContentText(player1 +" doesn't exist at all in this project yet!");
            			//alert.setContentText(player1 +" doesn't need setup!");
            			alert.showAndWait();
            			break;
            			
            		default:            			            			
            			alert.setHeaderText("Who is this Player1");
            			alert.setContentText(null);
            			alert.showAndWait();
            			break;
            	}           	
            	
            	switch (player2){
        		case IBuilder.NET_PLAYER:
        			playerType = new NetPlayer(getUiMessage());
        			getBuilder().createPlayer(IBuilder.NET_PLAYER);
        			
        			if (playerType.needsSetup())
        			{
        				alert.setHeaderText("Much greater news for Player2");
            			alert.setContentText(player2 +" setup is coming...!");
            			alert.showAndWait();
            			playerType.setup(secondaryLayout, btnAddPlayer);
        			}
        			else
        			{
        				alert.setHeaderText("Great news for Player2");
            			alert.setContentText(player2 +" doesn't need setup!");
            			alert.showAndWait();
        			}        			
        			break;
        			
        		case IBuilder.UI_PLAYER:
        			playerType = new UIPlayer(primaryStage);
        			getBuilder().createPlayer(IBuilder.UI_PLAYER);
        			
        			if (playerType.needsSetup())
        			{
        				alert.setHeaderText("Much greater news for Player2");
            			alert.setContentText(player2 +" setup is coming...!");
            			alert.showAndWait();
            			playerType.setup(secondaryLayout, btnAddPlayer);
        			}
        			else
        			{
        				alert.setHeaderText("Great news for Player2");
            			alert.setContentText(player2 +" doesn't need setup!");
            			alert.showAndWait();
        			}
        			break;
        			
        		case IBuilder.ALPHABETA_PLAYER:
        			alert.setHeaderText("Even more greater news for Player2");
        			alert.setContentText(player2 +" doesn't exist at all in this project yet!");
        			//alert.setContentText(player2 +" doesn't need setup!");
        			alert.showAndWait();
        			break;
        			
        		default:
        			alert.setHeaderText("Who is this Player2");
        			alert.setContentText(null);
        			alert.showAndWait();
        			break;
        	}
            }
        });
        
        btnBox.getChildren().addAll(backButton,playerButton);
        gridGameTypes.add(btnBox, 0, 4);
        
        TitledPane gameTypeGroup = new TitledPane();
        gameTypeGroup.setCollapsible(false);
        gameTypeGroup.setText("New game");
        gameTypeGroup.setContent(gridGameTypes);

        VBox newGameWindow = new VBox();
        newGameWindow.setSpacing(10);
        newGameWindow.setPadding(new Insets(20)); 
        newGameWindow.getChildren().addAll(gameTypeGroup);

        return newGameWindow;
	}

	
	public void StartGameMenu()
	{
		this.primaryStage.show();
	}
}
