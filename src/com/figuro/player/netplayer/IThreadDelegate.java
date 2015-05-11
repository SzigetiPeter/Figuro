package com.figuro.player.netplayer;

import java.net.Socket;

public interface IThreadDelegate {

	public void setClientSocket(Socket socket);
	public void setProcessor(IProcessor processor);

}
