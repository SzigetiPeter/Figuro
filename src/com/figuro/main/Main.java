package com.figuro.main;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			
			Label labelGameTitle = new Label("FIGURO");
	        labelGameTitle.setMaxWidth(Double.MAX_VALUE);
	        labelGameTitle.setAlignment(Pos.CENTER);
		    
	        root.setTop(labelGameTitle);
			root.setCenter(createMainWindowScreen(root));

			Scene sceneMainWindow = new Scene(root,400,400);
			sceneMainWindow.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        
	        primaryStage.setTitle("Figuro");		        			
			primaryStage.setScene(sceneMainWindow);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private VBox createMainWindowScreen(BorderPane root)
	{
		Button btnResumeLastGame = new Button();
        btnResumeLastGame.setText("Resume last game");
        btnResumeLastGame.setMaxWidth(Double.MAX_VALUE);
        
        Button btnNewGame = new Button();
        btnNewGame.setText("New game");
        btnNewGame.setMaxWidth(Double.MAX_VALUE);
        
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
       
		btnNewGame.setOnAction(new EventHandler<ActionEvent>() {
	        
            @Override
            public void handle(ActionEvent event) {
                //System.out.println("Hello World!");
            	root.setCenter(createNewGameScreen(root));
            }
        });

		return mainWindow;
	}
	
	private VBox createNewGameScreen(BorderPane root)
	{
        GridPane gridGameTypes = new GridPane();
        gridGameTypes.setVgap(4);
        gridGameTypes.setPadding(new Insets(5, 5, 5, 5));
		
        final ComboBox priorityComboBox = new ComboBox();
        priorityComboBox.getItems().addAll(
            "Please select game...",
            "Checkers" 
        );   
        
        priorityComboBox.setValue("Please select game...");
        gridGameTypes.add(priorityComboBox, 0, 0);
        
        final ToggleGroup group = new ToggleGroup();
        RadioButton rb1 = new RadioButton("Net game");
        rb1.setToggleGroup(group);
        rb1.setSelected(true);
        gridGameTypes.add(rb1, 0, 1);
        
        RadioButton rb2 = new RadioButton("Player vs. Bot");
        rb2.setToggleGroup(group);
        gridGameTypes.add(rb2, 0, 2);
        
        RadioButton rb3 = new RadioButton("Bot vs. Bot");
        rb3.setToggleGroup(group);
        gridGameTypes.add(rb3, 0, 3);
        
        HBox btnBox = new HBox();
        Button backButton = new Button();
        backButton.setText("Back");
        
        backButton.setOnAction(new EventHandler<ActionEvent>() {
	        
            @Override
            public void handle(ActionEvent event) {                
            	root.setCenter(createMainWindowScreen(root));
            }
        });
        
        Button playerButton = new Button();
        playerButton.setText("Players");
        
        playerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {                
            	root.setCenter(createNewPlayerScreen(root));
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

	private VBox createNewPlayerScreen(BorderPane root)
	{
		TitledPane addPlayerGroup = new TitledPane();
        addPlayerGroup.setCollapsible(false);	  
        GridPane gridAddPlayer = new GridPane();
        gridAddPlayer.setVgap(4);
        gridAddPlayer.setPadding(new Insets(5, 5, 5, 5));			
		
        HBox containerPlayer = new HBox(); 
        Rectangle rect1 = new Rectangle(10, 10, 300, 200);
        rect1.setFill(Color.BLUE);
        containerPlayer.getChildren().addAll(rect1);
        
        gridAddPlayer.add(containerPlayer, 0, 0);	        
		
        HBox boxAddPlayer = new HBox();
        
		BorderPane bp = new BorderPane();
		
		Button btnBackToGameSelection = new Button();
		btnBackToGameSelection.setText("Back");
		
		btnBackToGameSelection.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {                
            	root.setCenter(createNewGameScreen(root));
            }
        });
		
		Button btnAddPlayer = new Button();
        btnAddPlayer.setText("Start game");
        
        btnAddPlayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {                
            	root.setCenter(createGameBoardScreen(root));
            }
        });
        
        boxAddPlayer.getChildren().addAll(btnBackToGameSelection,btnAddPlayer);
        
        gridAddPlayer.add(boxAddPlayer, 0, 1);
        
        addPlayerGroup.setText("New XXX player");
        addPlayerGroup.setContent(gridAddPlayer);
        
        bp.setCenter(addPlayerGroup);
        bp.setBottom(boxAddPlayer);
        
        VBox newPlayerWindow = new VBox();
        newPlayerWindow.setSpacing(10);
        newPlayerWindow.setPadding(new Insets(20)); 
        newPlayerWindow.getChildren().addAll(bp);

        return newPlayerWindow;
	}

	private VBox createGameBoardScreen(BorderPane root)
	{
		TextField messageTextField = new TextField ();
		messageTextField.setText("Waiting for the other player move...");
		
		Rectangle gameBoard = new Rectangle(10, 10, 300, 200);
		gameBoard.setFill(Color.GRAY);
		
		VBox gameBoardWindow = new VBox();
		gameBoardWindow.setSpacing(10);
		gameBoardWindow.setPadding(new Insets(20)); 
		gameBoardWindow.getChildren().addAll(messageTextField,gameBoard);

        return gameBoardWindow;
	}
}

