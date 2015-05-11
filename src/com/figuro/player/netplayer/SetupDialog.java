package com.figuro.player.netplayer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

class SetupDialog extends Group implements ISetupDialog {

	private Label ipLbl, ipFld, portLbl, portFld, confirmLabel, waitLabel;
	private Button listenButton, connectButton, okButton, cancelButton;
	private Label enemyIpLbl, enemyPortLbl;
	private TextField enemyIpFld, enemyPortFld;

	private GridPane gridPane;
	private IDialogDelegate mDelegate;
	private boolean toConnect = false;

	private Button player1, player2;

	private int mOrder = 0;

	public SetupDialog(Group owner, IDialogDelegate delegate, String ipString) {
		super();

		mDelegate = delegate;

		gridPane = new GridPane();
		gridPane.setPadding(new Insets(5));
		gridPane.setHgap(5);
		gridPane.setVgap(5);

		ipLbl = new Label("My IP: ");
		gridPane.add(ipLbl, 0, 1);

		ipFld = new Label(ipString);
		gridPane.add(ipFld, 1, 1);

		portLbl = new Label("Port: ");
		gridPane.add(portLbl, 0, 2);

		portFld = new Label();
		portFld.setText("0");
		gridPane.add(portFld, 1, 2);

		listenButton = new Button("Listen");
		listenButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("Listen 1");
				toConnect = false;
				gridPane.getChildren().removeAll(enemyIpLbl, enemyIpFld,
						enemyPortLbl, enemyPortFld);
				mDelegate.startListening();
			}

		});
		gridPane.add(listenButton, 0, 5);

		connectButton = new Button("Connect");
		connectButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (toConnect) {

					// TODO check if ip and port is valid
					if (enemyIpFld.getText().length() > 0
							&& enemyPortFld.getText().length() > 0) {
						String ipString = enemyIpFld.getText();
						int portNumber = Integer.parseInt(enemyPortFld
								.getText());

						mDelegate.connectToPlayer(ipString, portNumber);
					}

				} else {

					toConnect = true;
					System.out.println("Connect 1");

					portFld.setText("0");

					mDelegate.stopListening();

					enemyIpLbl = new Label("Player IP: ");
					gridPane.add(enemyIpLbl, 0, 3);
					enemyIpFld = new TextField();
					gridPane.add(enemyIpFld, 1, 3);
					enemyPortLbl = new Label("Player port: ");
					gridPane.add(enemyPortLbl, 0, 4);
					enemyPortFld = new TextField();
					gridPane.add(enemyPortFld, 1, 4);

					listenButton.setDisable(false);
				}
			}
		});
		gridPane.add(connectButton, 1, 5);

		owner.getChildren().add(gridPane);

		/*
		 * setOnCloseRequest(new EventHandler<WindowEvent>() {
		 * 
		 * @Override public void handle(WindowEvent event) {
		 * System.out.println("Dialog closed"); mDelegate.stopListening(); } });
		 */

	}

	public void startedListening(int portNumber) {
		portFld.setText(Integer.toString(portNumber));
		listenButton.setDisable(true);
	}

	public void connectedToPlayer(String ipString, int portNumber) {

		gridPane.getChildren().removeAll(player1, player2, waitLabel,
				confirmLabel, okButton, cancelButton, listenButton,
				connectButton, enemyIpLbl, enemyIpFld, enemyPortLbl,
				enemyPortFld);

		ipLbl.setText("Connected to: ");
		ipFld.setText(ipString);

		portLbl.setText("On port: ");
		portFld.setText(Integer.toString(portNumber));

		player1 = new Button("Be player 1");
		gridPane.add(player1, 0, 3);

		player2 = new Button("Be player 2");
		gridPane.add(player2, 1, 3);

		player1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				player1.setDisable(true);
				player2.setDisable(true);

				waitLabel = new Label("Waiting for player 2...");
				gridPane.add(waitLabel, 0, 4);

				mOrder = 2;
				mDelegate.playerOrderRequest(mOrder);
			}
		});

		player2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				player1.setDisable(true);
				player2.setDisable(true);

				waitLabel = new Label("Waiting for player 1...");
				gridPane.add(waitLabel, 0, 4);

				mOrder = 1;
				mDelegate.playerOrderRequest(mOrder);
			}

		});
	}

	public void playerOrderRequestReceived(int order) {
		System.out.println("Order request: " + order);

		mOrder = (order == 1 ? 2 : 1);
		
		mDelegate.setOrderTemp(mOrder);
		
		gridPane.getChildren().removeAll(player1, player2);
		confirmLabel = new Label("Do you accept to be player " + order + "?");
		okButton = new Button("OK");
		cancelButton = new Button("Cancel");

		gridPane.add(confirmLabel, 0, 3);
		gridPane.add(okButton, 0, 4);
		gridPane.add(cancelButton, 1, 4);

		okButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				okButton.setDisable(true);
				cancelButton.setDisable(true);
				
				mDelegate.playerOrderOK(true);

			}
		});

		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				connectedToPlayer(ipFld.getText(),
						Integer.parseInt(portFld.getText()));

				mDelegate.playerOrderCancel();

			}
		});
	}

	public void startGame() {
		
		System.out.println("Game can be started");
		
		mDelegate.playerOrderOK(false);
		gridPane.getChildren().removeAll(okButton, cancelButton, player1, player2, waitLabel, confirmLabel);
		int nOrder = (mOrder ==1 ? 2 : 1);
		Label startLabel = new Label("You are player " + nOrder);
		gridPane.add(startLabel, 0, 3);
	}
}