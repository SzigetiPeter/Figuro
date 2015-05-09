package com.figuro.engine;

import java.util.concurrent.CountDownLatch;

/**
 * @author Dalyay Kinga
 */

public class RunningState {

	private CountDownLatch latch;
	boolean isRunning = true;

	public RunningState() {
		latch = new CountDownLatch(1);	
	}
	
	public void await() {
		try {
			latch.await();
		} catch (InterruptedException e) {
			//
		}	
	}
	
	public void stopWaiting() {
		latch.countDown();
	}
	
	public void terminate() {
		latch.countDown();
		isRunning = false;
	}
	
	public boolean isRunning() {
		return isRunning;
	}
}
