package com.figuro.engine;

import com.figuro.common.BoardState;

/**
 * @author Dalyay Kinga
 */

public class MoveComplete implements IMoveComplete {

	private BoardState state = null;

	private RunningState runningState;
	
	public MoveComplete(RunningState runningState) {
		this.runningState = runningState;
	}

	public void listen() {
		runningState.await();
	}

	@Override
	public void setResult(BoardState result) {
		state = result;
		runningState.stopWaiting();
	}

	public BoardState getResult() {
		return state;
	}
}
