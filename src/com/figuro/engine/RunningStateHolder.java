package com.figuro.engine;

public class RunningStateHolder {
	private boolean running = true;
	
	public void terminate() {
		running = false;
	}
	
	public boolean isRunning() {
		return running;
	}
}
