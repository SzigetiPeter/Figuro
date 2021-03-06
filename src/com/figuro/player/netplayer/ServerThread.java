package com.figuro.player.netplayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.application.Platform;

import com.figuro.common.IMessageSender;

public class ServerThread extends Thread {

	private Socket mSocket;
	private ServerSocket mServerSocket;
	private IThreadDelegate mDelegate;
	private ISetupDialog mDialog;
	private IMessageSender mSender;

	public ServerThread(IThreadDelegate delegate, ISetupDialog dialog, IMessageSender sender) {
		mDelegate = delegate;
		mDialog = dialog;

	}

	public void closeSocket() {
		try {
			mServerSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			mServerSocket = new ServerSocket(0);
			int mPort = mServerSocket.getLocalPort();

			Platform.runLater(new Runnable() {

				@Override
				public void run() {

					System.out.println("Listening on: "
							+ mServerSocket.getInetAddress().getHostAddress()
							+ " port: " + mPort);
					mDialog.startedListening(mPort);

				}
			});

			mSocket = mServerSocket.accept();
			mDelegate.setClientSocket(mSocket);

			String enemyIP = mSocket.getInetAddress().getHostAddress();
			int enemyPort = mSocket.getPort();

			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					mDialog.connectedToPlayer(enemyIP, enemyPort);
					System.out.println("Incoming connection from: " + enemyIP
							+ " port " + enemyPort);
				}
			});

			ProcessMessages pro = new ProcessMessages(mSocket, mDialog);
			mDelegate.setProcessor(pro);
			pro.start();

		} catch (UnknownHostException e) {

			e.printStackTrace();
			mSender.displayMessage("Could not create connection [E2]");
			return;

		} catch (IOException e) {

			// e.printStackTrace();
			mSender.displayMessage("Socket closed");
			return;
		}
	}
}
