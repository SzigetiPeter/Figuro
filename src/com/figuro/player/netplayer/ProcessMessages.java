package com.figuro.player.netplayer;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import com.figuro.common.BoardState;
import com.figuro.engine.IMoveComplete;

import javafx.application.Platform;

public class ProcessMessages extends Thread implements IProcessor {

	private Socket mSocket;
	private ISetupDialog mDialog;
	private IMoveComplete mComplete;

	public ProcessMessages(Socket socket, ISetupDialog dialog) {
		mSocket = socket;
		mDialog = dialog;
	}
	
	public void setCallback(IMoveComplete callback) {
		mComplete = callback;
	}

	public void run() {
		Scanner sc;
		try {
			sc = new Scanner(mSocket.getInputStream());

			while (sc.hasNextLine()) {

				String lineString = sc.nextLine();
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						System.out.println(lineString);

						if (lineString.startsWith("player ")) {

							int index = Integer.parseInt(lineString.substring(7));
							
							mDialog.playerOrderRequestReceived(index);

						} else if (lineString.startsWith("OK")) {

							mDialog.startGame();

						} else if (lineString.startsWith("CANCEL")) {

							mDialog.connectedToPlayer(mSocket.getInetAddress()
									.getHostAddress(), mSocket.getPort());

						} else if (lineString.startsWith("NOTIFY ")) {
							
							String fromString = lineString.substring(7);
							BoardState bs = (BoardState) BoardState.fromString(fromString);
							
							mComplete.setResult(bs);
							
						}
					}
				});
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
