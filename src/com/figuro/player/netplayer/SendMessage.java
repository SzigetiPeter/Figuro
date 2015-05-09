package com.figuro.player.netplayer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SendMessage extends Thread {

	private Socket mSocket;
	private String mMessage;

	public SendMessage(Socket socket, String message) {
		mSocket = socket;
		mMessage = message;
	}

	public void run() {
		PrintWriter pw;

		try {

			pw = new PrintWriter(mSocket.getOutputStream(), true);
			pw.println(mMessage);
			System.out.println("Message sent: " + mMessage);

		} catch (IOException e) {

			e.printStackTrace();
			System.out.println("Message send error: " + mMessage);

		}

	}
}
