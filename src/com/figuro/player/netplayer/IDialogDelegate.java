package com.figuro.player.netplayer;

public interface IDialogDelegate {

	public void startListening();

	public void stopListening();

	public void connectToPlayer(String ipString, int portNumber);

	public void playerOrderRequest(int order);

	public void playerOrderOK();

	public void playerOrderCancel();
}
