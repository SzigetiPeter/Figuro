package com.figuro.engine;

/**
 * @author Dalyay Kinga
 */

public class RunningStateHolder {
	private boolean running = true;

	public void terminate() {
		running = false;
	}

	public boolean isRunning() {
		return running;
	}
}
