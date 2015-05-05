package com.figuro.player.netplayer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.figuro.common.BoardState;
import com.figuro.common.IMessageSender;
import com.figuro.engine.IMoveComplete;
import com.figuro.player.IPlayer;


public class NetPlayer implements IPlayer {

	protected BoardState mBoardState;
	protected int mId;
	private String mIP;
	private int mPort;

	private String enemyIP;
	private int enemyPort;

	private ListenDialog mListenDialog;
	private ConnectDialog mConnectDialog;
	private ServerSocket mSocket;
	private Socket enemySocket;

	private IMoveComplete  mComplete;
	private IMessageSender mSender;

	public NetPlayer(IMessageSender sender, IMoveComplete complete) {

		mComplete = complete;
		mSender = sender;
	}

	@Override
	public void setInitialState(BoardState board) {

		mBoardState = board;

	}

	@Override
	public void move(IMoveComplete callback) {

		try {
			callback.setResult(mBoardState);
		} catch (NullPointerException e) {
			System.out.println("Callback was null");
		}
	}

	@Override
	public void wrongMoveResetTo(BoardState board) {
		//throw exception here, nem kene ilyen megtortenjen, hogy rossz lepes van, nem tudom lekezelni
		//IllegalMoveException exc = new IllegalMoveException();
		//throw exc;

		mBoardState = board;
	}

	@Override
	public int getId() {
		return mId;
	}

	@Override
	public void setId(int id) { //ez nem fog meghivodni
		mId = id;
	}

	public String getIP() {
		return mIP;
	}

	public int getPort() {
		return mPort;
	}

	@Override
	public boolean needsSetup() {
		return true;
	}

	//helper to get external IP address
	public static String getIp() throws Exception {
		if (true) 
			return "127.0.0.1";

		URL whatismyip = new URL("http://checkip.amazonaws.com");
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(
					whatismyip.openStream()));
			String ip = in.readLine();
			return ip;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	@Override
	public void setup(Group parent) {
		try {

			mIP = getIp();
			System.out.println(mIP);

		} catch (Exception e) {

			e.printStackTrace();
			mSender.displayMessage("Could not create connection [E1]");
			return;

		}

		mListenDialog = new ListenDialog(parent, mIP);
		mListenDialog.sizeToScene();
		mListenDialog.show();
	} 

	@Override
	public int getPrefferedOrder() {

		return 0;
	}

	Thread serverThread = new Thread(new Runnable() {

		@Override
		public void run() {
			try {
				mSocket = new ServerSocket(0);

				mPort = mSocket.getLocalPort();

				Platform.runLater(new Runnable() {

					@Override
					public void run() {

						System.out.println("Listening on: " + mIP + " port: " + mPort);
						mListenDialog.startedListening(mPort);

					}
				});

				enemySocket = mSocket.accept();
				enemyIP = enemySocket.getInetAddress().getHostAddress();
				enemyPort = enemySocket.getPort();


				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						mListenDialog.connectedToPlayer();
						System.out.println("Incoming connection from: " + enemyIP + " port " + enemyPort);				
					}
				});


			} catch (UnknownHostException e) {

				e.printStackTrace();
				mSender.displayMessage("Could not create connection [E2]");
				return;

			} catch (IOException e) {

				e.printStackTrace();
				mSender.displayMessage("Could not create connection [E3]");
				return;
			}

		}
	});

	Thread clientThread = new Thread(new Runnable() {

		@Override
		public void run() {
			try {

				Socket s = new Socket(enemyIP, enemyPort);

				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						System.out.println("Created socket " + s.getInetAddress().getHostAddress() + " port " + s.getPort());
						mConnectDialog.connectedToPlayer(enemyIP, enemyPort);				
					}
				});

				s.close();

			} catch (Exception e) {
				System.out.println("ConnectTo error" + e.getMessage());
				e.printStackTrace();
			}
		}

	});

	public void startListening() {
		serverThread.start();
	}

	public void stopListening() {

		try {

			serverThread.interrupt();

			if (mSocket != null) {
				mSocket.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
			mSender.displayMessage("Could not close connection [E4]");
			return;
		}
	}

	public void connectToPlayer() {

		clientThread.start();

	}

	class ConnectDialog extends Stage {
		protected GridPane gridPane;
		protected Label ipLbl, ipFld, portLbl, portFld;
		protected Button listenButton;

		public ConnectDialog(Group owner) {
			super();
			setTitle("Setup");
			initModality(Modality.NONE);
			setResizable(false);

			setScene(new Scene(owner, 400, 200));

			gridPane = new GridPane();
			gridPane.setPadding(new Insets(5));
			gridPane.setHgap(5);
			gridPane.setVgap(5);


			ipLbl = new Label("Player IP: ");
			gridPane.add(ipLbl, 0, 1);

			ipFld = new Label("");
			gridPane.add(ipFld, 1, 1);

			portLbl = new Label("Player port: ");
			gridPane.add(portLbl, 0, 2);

			portFld = new Label();
			portFld.setText("0");
			gridPane.add(portFld, 1, 2);

			listenButton = new Button("Find");
			listenButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					connectToPlayer();
				}

			});

			GridPane.setHalignment(listenButton, HPos.CENTER);
			gridPane.add(listenButton, 0, 3);

			owner.getChildren().add(gridPane);

			setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					System.out.println("Dialog closed");
					stopListening();
				}
			});
		}

		public void playerFound(String ipString, int portNumber) {
			ipFld.setText(ipString);
			portFld.setText(Integer.toString(portNumber));

			listenButton.setText("Connect");
		}

		public void connectedToPlayer(String ipString, int portNumber) {
			gridPane.getChildren().removeAll(listenButton);

			ipLbl.setText("Connected to: ");
			ipFld.setText(ipString);

			portLbl.setText("On port: ");
			portFld.setText(Integer.toString(portNumber));

		}

	}



	class ListenDialog extends Stage {

		protected Label ipLbl, ipFld, portLbl, portFld;
		protected Button listenButton, connectButton;
		protected Label enemyIpLbl, enemyPortLbl;
		protected TextField enemyIpFld, enemyPortFld;

		protected GridPane gridPane;

		public ListenDialog(Group owner, String ipString) {
			super();
			setTitle("Setup");
			initModality(Modality.NONE);
			setResizable(false);

			setScene(new Scene(owner, 500, 300));

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

					gridPane.getChildren().removeAll(enemyIpLbl, enemyIpFld, enemyPortLbl, enemyPortFld);
					startListening();
				}

			});
			gridPane.add(listenButton, 0, 5);

			connectButton = new Button("Connect");
			connectButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					System.out.println("Connect 1");

					stopListening();

					enemyIpLbl = new Label("Player IP: ");
					gridPane.add(enemyIpLbl, 0, 3);
					enemyIpFld = new TextField();
					gridPane.add(enemyIpFld, 1, 3);
					enemyPortLbl = new Label("Player port: ");
					gridPane.add(enemyPortLbl, 0, 4);
					enemyPortFld = new TextField();
					gridPane.add(enemyPortFld, 1, 4);

				}
			});
			gridPane.add(connectButton, 1, 5);

			owner.getChildren().add(gridPane);

			setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					System.out.println("Dialog closed");
					stopListening();
				}
			});
		}

		public void startedListening(int portNumber) {
			portFld.setText(Integer.toString(portNumber));
			listenButton.setDisable(true);
		}

		public void connectedToPlayer() {
			gridPane.getChildren().removeAll(portLbl, portFld, listenButton);

			ipLbl.setText("Connected to: ");
			ipFld.setText(enemyIP);

		}
	}

	@Override
	public void notify(BoardState counterMove) {
		mBoardState = counterMove;

	}

}
