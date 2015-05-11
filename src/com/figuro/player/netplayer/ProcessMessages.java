package com.figuro.player.netplayer;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import javafx.application.Platform;

public class ProcessMessages extends Thread {

	private Socket mSocket;
	private SetupDialog mDialog;
<<<<<<< Updated upstream
=======
<<<<<<< HEAD
=======
>>>>>>> Stashed changes
	@SuppressWarnings("unused")
	private int orderTemp;
>>>>>>> origin/master

	public ProcessMessages(Socket socket, SetupDialog dialog) {
		mSocket = socket;
		mDialog = dialog;
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

<<<<<<< Updated upstream
							int index = Integer.parseInt(lineString
									.substring(7));
							orderTemp = (index == 1 ? 2 : 1);
=======
<<<<<<< HEAD
							int index = Integer.parseInt(lineString.substring(7));
							//int orderTemp = (index == 1? 2 : 1);
=======
							int index = Integer.parseInt(lineString
									.substring(7));
							orderTemp = (index == 1 ? 2 : 1);
>>>>>>> origin/master
>>>>>>> Stashed changes

							mDialog.playerOrderRequestReceived(index);

						} else if (lineString.startsWith("OK")) {

							mDialog.startGame();

						} else if (lineString.startsWith("CANCEL")) {

							mDialog.connectedToPlayer(mSocket.getInetAddress()
									.getHostAddress(), mSocket.getPort());

						}
					}
				});
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
