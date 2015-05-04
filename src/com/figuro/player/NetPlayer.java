package com.figuro.player;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.management.Query;
import javax.swing.SwingUtilities;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.figuro.common.BoardState;
import com.figuro.common.IMessageSender;
import com.figuro.engine.IMoveComplete;


public class NetPlayer implements IPlayer, IMessageSender, IMoveComplete {

	protected BoardState mBoardState;
	protected String mGameStatus;
	protected int mId;
	private String mIP;
	private int mPort;

	private String enemyIP;
	private int enemyPort;

	private ListenDialog mListenDialog;
	private ConnectDialog mConnectDialog;
	private ServerSocket mSocket;
	private Socket enemySocket;

	private IConnectDelegate mDelegate;

	public NetPlayer(IConnectDelegate delegate) {
		mDelegate = delegate;
	}

	@Override
	public void displayMessage(String message) {
		System.out.println(message);		
	}

	@Override
	public void updateGameState(String gameStatus) {

		mGameStatus = gameStatus;
		System.out.println(gameStatus);
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
	public void setId(int id) {
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

		if (mId == 1) {// listening

			try {

				mIP = "localhost";//getIp();
				System.out.println(mIP);

			} catch (Exception e) {

				e.printStackTrace();
				displayMessage("Could not create connection [E1]");
				return;

			}

			mListenDialog = new ListenDialog(parent, mIP);
			mListenDialog.sizeToScene();
			mListenDialog.show();

		} else if (mId == 2) {//connecting to someone already listening

			try {

				mIP = "localhost"; //getIp();
				System.out.println(mIP);

			} catch (Exception e) {

				e.printStackTrace();
				displayMessage("Could not create connection [E1]");
				return;

			}

			mConnectDialog = new ConnectDialog(parent);
			mConnectDialog.sizeToScene();
			mConnectDialog.show();

		} else { //spectator



		}


	} 

	@Override
	public int getPrefferedOrder() {

		return 0;
	}

	@Override
	public void setResult(BoardState result) {
		mBoardState = result;

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
						mDelegate.isListeningOn(mIP, mPort);

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
				displayMessage("Could not create connection [E2]");
				return;

			} catch (IOException e) {

				e.printStackTrace();
				displayMessage("Could not create connection [E3]");
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
			mDelegate.stoppedListening(mIP, mPort);

			if (mSocket != null) {
				mSocket.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
			displayMessage("Could not close connection [E4]");
			return;
		}
	}

	public void findPlayer() {
		InetSocketAddress enemyAddr = mDelegate.getPlayerInetAdress();

		if (enemyAddr != null) {
			enemyIP = enemyAddr.getHostString();
			enemyPort = enemyAddr.getPort();
			mConnectDialog.playerFound(enemyIP, enemyPort);
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
					if (enemyIP == null) {

						findPlayer();

					} else {

						connectToPlayer();

					}
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
		protected Button listenButton;

		protected GridPane gridPane;
		
		public ListenDialog(Group owner, String ipString) {
			super();
			setTitle("Setup");
			initModality(Modality.NONE);
			setResizable(false);

			setScene(new Scene(owner, 400, 200));

			gridPane = new GridPane();
			gridPane.setPadding(new Insets(5));
			gridPane.setHgap(5);
			gridPane.setVgap(5);


			ipLbl = new Label("Your IP: ");
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
					startListening();
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
