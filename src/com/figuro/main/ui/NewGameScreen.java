package com.figuro.main.ui;

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
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.figuro.common.IBuilder;
import com.figuro.engine.IEngineHandler;
import com.figuro.engine.IGameoverCallback;
import com.figuro.player.IPlayer;

public class NewGameScreen extends VBox {

	private IBuilder builder;
	private BorderPane root;
	private Label messageLabel;

	public NewGameScreen(
			BorderPane root,
			IBuilder builder,
			VBox mainScreenVBox,
			Label messageLabel, 
			UIMessage uiMessage) {
		this.root = root;
		this.builder = builder;
		this.messageLabel = messageLabel;

		GridPane gridGameTypes = new GridPane();
		gridGameTypes.setVgap(4);
		gridGameTypes.setPadding(new Insets(5, 5, 5, 5));

		final ComboBox<String> priorityComboBox = new ComboBox<String>();

		String[] gameTypes = builder.getGameTypes();

		for (String string : gameTypes) {
			priorityComboBox.getItems().add(string);
		}

		gridGameTypes.add(priorityComboBox, 0, 0);

		final ToggleGroup group = new ToggleGroup();

		RadioButton radio;
		radio = new RadioButton("Player vs. Bot");
		radio.setToggleGroup(group);
		radio.setUserData(PlayerSetupFactory.PLAYER_VS_BOT);
		gridGameTypes.add(radio, 0,1);
		radio = new RadioButton("Net game");
		radio.setToggleGroup(group);
		radio.setUserData(PlayerSetupFactory.NET_GAME);
		gridGameTypes.add(radio, 0, 2);
		
		radio = new RadioButton("Bot vs. Bot");
		radio.setToggleGroup(group);
		radio.setUserData(PlayerSetupFactory.BOT_VS_BOT);
		gridGameTypes.add(radio, 0, 3);

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
				if (priorityComboBox.getValue() == null) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setHeaderText("Please select game type first");
					alert.setContentText(null);
					alert.showAndWait();
					return;
				}

				Toggle toggle = group.getSelectedToggle();
				String setupName = (String)toggle.getUserData();

				PlayerSetupFactory m = new PlayerSetupFactory();
				PlayerSetupTemplate playerTemplate = m.createSetup(setupName);

				String player1 = playerTemplate.getPlayer1();
				String player2 = playerTemplate.getPlayer2();
				String spectator = playerTemplate.getSpectator();
				
				boolean startGame = true;
				
				IEngineHandler gameRunner = builder.createEngine();
				IGameoverCallback gameoverCallback = new GameOverUINotification(root, mainScreenVBox,builder);
				
				IPlayer iplayer1 = gameRunner.addPlayer(player1);
				IPlayer iplayer2 = gameRunner.addPlayer(player2);
				if (spectator != null) {
					gameRunner.addSpectator(spectator);
				}

				if (SetupPlayerIfNeeded(iplayer1,gameRunner,gameoverCallback,priorityComboBox.getValue().toString()) == true)
					startGame = false;
			
				if (SetupPlayerIfNeeded(iplayer2,gameRunner,gameoverCallback,priorityComboBox.getValue().toString()) == true)
					startGame = false;
				
				if (startGame) {
					gameRunner.runGame(priorityComboBox.getValue().toString(),
							gameoverCallback);
				}
			}
		});
		btnBox.getChildren().addAll(backButton, playerButton);
		gridGameTypes.add(btnBox, 0, 5);

		TitledPane gameTypeGroup = new TitledPane();
		gameTypeGroup.setCollapsible(false);
		gameTypeGroup.setText("New game");
		gameTypeGroup.setContent(gridGameTypes);

		this.setSpacing(10);
		this.setPadding(new Insets(20));
		this.getChildren().addAll(gameTypeGroup);
	}

	private boolean SetupPlayerIfNeeded(IPlayer playerType,IEngineHandler gameRunner,IGameoverCallback gameoverCallback,String gameType) {
		if (playerType.needsSetup()) {
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
					gameRunner.runGame(gameType,
							gameoverCallback);
				}
			});

			Group groupSetupPlayer = new Group();

			playerType.setup(groupSetupPlayer, btnAddPlayer);
			secondaryLayout.setCenter(groupSetupPlayer);
			secondaryLayout.setBottom(hb);
			Scene secondScene = new Scene(secondaryLayout, 300, 300);

			Stage secondStage = new Stage();
			secondStage.setTitle("Player setup");
			secondStage.setScene(secondScene);
			secondStage.centerOnScreen();
			secondStage.initModality(Modality.APPLICATION_MODAL);
			secondStage.show();
			return true;
		} 
		return false;
	}
}
