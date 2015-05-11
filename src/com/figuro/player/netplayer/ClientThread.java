package com.figuro.player.netplayer;

import java.net.Socket;

import javafx.application.Platform;

public class ClientThread extends Thread {

	String mIpString;
	int mPortNumber;
	Socket mSocket;
	SetupDialog mDialog;
	IThreadDelegate mDelegate;

	public ClientThread(IThreadDelegate delegate, String ipString,
			int portNumber, SetupDialog dialog) {
		mIpString = ipString;
		mPortNumber = portNumber;
		mDialog = dialog;
		mDelegate = delegate;
	}

	@Override
	public void run() {
		try {

			mSocket = new Socket(mIpString, mPortNumber);
			mDelegate.setClientSocket(mSocket);

			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					mDialog.connectedToPlayer(mIpString, mPortNumber);
					System.out.println("Created socket "
							+ mSocket.getInetAddress().getHostAddress()
							+ " port " + mSocket.getPort());

				}
			});

			ProcessMessages pro = new ProcessMessages(mSocket, mDialog);
			mDelegate.setProcessor(pro);		
			pro.start();

		} catch (Exception e) {
			System.out.println("ConnectTo error" + e.getMessage());
			e.printStackTrace();
		}
	}
}
