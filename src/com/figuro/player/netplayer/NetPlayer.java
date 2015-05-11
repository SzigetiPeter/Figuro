package com.figuro.player.netplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;

import javafx.scene.Group;
import javafx.scene.control.Button;

import com.figuro.common.BoardState;
import com.figuro.common.IMessageSender;
import com.figuro.engine.IMoveComplete;
import com.figuro.player.IPlayer;

public class NetPlayer implements IPlayer, IDialogDelegate, IThreadDelegate {

	protected BoardState mBoardState;
	protected int mId;
	private String mIP;
	private int mPort;

	private SetupDialog mSetupDialog;

	private Socket mClientSocket;

	private IMessageSender mSender;

	private ServerThread serverThread;
	private ClientThread clientThread;

	private int orderTemp = 0;
	private int mPrefferedOrder = 0;

	public NetPlayer(IMessageSender sender) {

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
		// throw exception here, nem kene ilyen megtortenjen, hogy rossz lepes
		// van, nem tudom lekezelni
		// IllegalMoveException exc = new IllegalMoveException();
		// throw exc;

		mBoardState = board;
	}

	@Override
	public void setOrderTemp(int orderTemp) {
		this.orderTemp = orderTemp;
	}
	
	@Override
	public int getId() {
		return mId;
	}

	@Override
	public void setId(int id) { // ez nem fog meghivodni
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

	// helper to get external IP address
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
	}
        
        
	@Override
	public int getPrefferedOrder() {

		return mPrefferedOrder;
	}

	public void startListening() {
		serverThread = new ServerThread(this, mSetupDialog, mSender);
		serverThread.start();
	}

	public void stopListening() {

		if (serverThread != null) {
			serverThread.closeSocket();
		}
	}

	public void connectToPlayer(String ipString, int portNumber) {

		clientThread = new ClientThread(this, ipString, portNumber,
				mSetupDialog);
		clientThread.start();

	}

	@Override
	public void notify(BoardState counterMove) {
		mBoardState = counterMove;

	}

	@Override
	public void playerOrderRequest(int order) {

		orderTemp = order;
		(new SendMessage(mClientSocket, "player " + order)).start();

	}

	@Override
	public void playerOrderOK(boolean needToSend) {

		mPrefferedOrder = orderTemp;
		
		if (needToSend) {
			(new SendMessage(mClientSocket, "OK")).start();
			mSetupDialog.startGame();
		}

	}

	@Override
	public void playerOrderCancel() {

		orderTemp = 0;
		(new SendMessage(mClientSocket, "CANCEL")).start();

	}

	@Override
	public void setClientSocket(Socket socket) {
		mClientSocket = socket;
	}

}
