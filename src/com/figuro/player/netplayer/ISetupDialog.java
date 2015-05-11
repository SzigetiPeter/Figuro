package com.figuro.player.netplayer;

public interface ISetupDialog {

	public void connectedToPlayer(String ipString, int portNumber);
	public void playerOrderRequestReceived(int index);
	public void startGame();
	public void startedListening(int port);
	
}
