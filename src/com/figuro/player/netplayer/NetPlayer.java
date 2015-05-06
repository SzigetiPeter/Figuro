package com.figuro.player.netplayer;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Button;

import com.figuro.common.BoardState;
import com.figuro.common.IMessageSender;
import com.figuro.engine.IMoveComplete;
import com.figuro.player.IPlayer;


public class NetPlayer implements IPlayer, IDialogDelegate {

	protected BoardState mBoardState;
	protected int mId;
	private String mIP;
	private int mPort;

	private String enemyIP;
	private int enemyPort;

	private SetupDialog mSetupDialog;

	private ServerSocket mServerSocket;
	private Socket mClientSocket;

	private IMoveComplete  mComplete;
	private IMessageSender mSender;

	private ServerThread serverThread;
	private ClientThread clientThread;

	private String lineString;
	
	private int mPrefferedOrder = 0;

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
	public void setup(Group parent, Button okButton) {
		try {

			mIP = getIp();
			System.out.println(mIP);

		} catch (Exception e) {

			e.printStackTrace();
			mSender.displayMessage("Could not create connection [E1]");
			return;

		}

		mSetupDialog = new SetupDialog(parent, this, mIP);
		mSetupDialog.sizeToScene();
		mSetupDialog.show();
	} 

	@Override
	public int getPrefferedOrder() {

		return mPrefferedOrder;
	}

	public void startListening() {
		serverThread = new ServerThread();
		serverThread.start();
	}

	public void stopListening() {

		try {

			if (serverThread != null) {
				serverThread.interrupt();
			}

			if (mServerSocket != null) {
				mServerSocket.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
			mSender.displayMessage("Could not close connection [E4]");
			return;
		}
	}

	public void connectToPlayer(String ipString, int portNumber) {

		enemyIP = ipString;
		enemyPort = portNumber;

		clientThread = new ClientThread();
		clientThread.start();

	}



	@Override
	public void notify(BoardState counterMove) {
		mBoardState = counterMove;

	}


	public class ServerThread extends Thread {
	
		@Override
		public void run() {
			try {
				mServerSocket = new ServerSocket(0);

				mPort = mServerSocket.getLocalPort();

				Platform.runLater(new Runnable() {

					@Override
					public void run() {

						System.out.println("Listening on: " + mIP + " port: " + mPort);
						mSetupDialog.startedListening(mPort);

					}
				});

				mClientSocket = mServerSocket.accept();
				enemyIP = mClientSocket.getInetAddress().getHostAddress();
				enemyPort = mClientSocket.getPort();

				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						mSetupDialog.connectedToPlayer(enemyIP, enemyPort);
						System.out.println("Incoming connection from: " + enemyIP + " port " + enemyPort);				
					}
				});

				(new GetMessage(mClientSocket)).start();

			} catch (UnknownHostException e) {

				e.printStackTrace();
				mSender.displayMessage("Could not create connection [E2]");
				return;

			} catch (IOException e) {

				//e.printStackTrace();
				mSender.displayMessage("Socket closed");
				return;
			}
		}
	}

	public class ClientThread extends Thread {
		
		@Override
		public void run() {
			try {

				mClientSocket = new Socket(enemyIP, enemyPort);

				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						mSetupDialog.connectedToPlayer(enemyIP, enemyPort);
						System.out.println("Created socket " + mClientSocket.getInetAddress().getHostAddress() + " port " + mClientSocket.getPort());

					}
				});		

				(new GetMessage(mClientSocket)).start();

			} catch (Exception e) {
				System.out.println("ConnectTo error" + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private class GetMessage extends Thread {
		
		private Socket mSocket;
		
		public GetMessage(Socket socket) {
			mSocket = socket;
		}
		
		public void run() {
			Scanner sc;
			try {
				sc = new Scanner(mSocket.getInputStream());
				
				while (sc.hasNextLine()) {
					
					lineString = sc.nextLine();
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							System.out.println(lineString);

							if (lineString.startsWith("player ")) {
								
								int index = Integer.parseInt(lineString.substring(7));
								mSetupDialog.playerOrderRequestReceived(index);
								
							} else if (lineString.startsWith("OK")) {
								
								mSetupDialog.startGame();
								
							} else if (lineString.startsWith("CANCEL")) {
								
								mSetupDialog.connectedToPlayer(enemyIP, enemyPort);
								
							}
						}
					});
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}

			
		}
	}
	
	
	private static class SendMessage extends Thread {

		private Socket mSocket;
		private String mMessage;

		public SendMessage(Socket socket, String message)  {
			mSocket = socket;
			mMessage = message;
		}

		public void run() {
			PrintWriter pw;

			try {

				pw = new PrintWriter(mSocket.getOutputStream(), true);
				pw.println(mMessage);
				pw.flush();
				System.out.println("Message sent: " + mMessage);
				
				
			} catch (IOException e) {

				e.printStackTrace();
				System.out.println("Message send error: " + mMessage);

			}

		}
	}


	@Override
	public void playerOrderRequest(int order) {
	
		(new SendMessage(mClientSocket, "player " + order)).start();

	}

	@Override
	public void playerOrderOK() {
		
		mPrefferedOrder = 1;
		(new SendMessage(mClientSocket, "OK")).start();
		mSetupDialog.startGame();
		
	}

	@Override
	public void playerOrderCancel() {
		
		(new SendMessage(mClientSocket, "CANCEL")).start();
		
	}

}
