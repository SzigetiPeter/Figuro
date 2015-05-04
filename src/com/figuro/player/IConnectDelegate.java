package com.figuro.player;

import java.net.InetSocketAddress;

public interface IConnectDelegate {
	
	public void isListeningOn(String ipString, int portNumber);
	public void stoppedListening(String ipString, int portNumber);
	public InetSocketAddress getPlayerInetAdress();
}
