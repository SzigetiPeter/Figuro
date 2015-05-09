package com.figuro.engine;

import com.figuro.common.BoardState;

/**
 * @author Dalyay Kinga
 */

public class MoveComplete implements IMoveComplete {

	private BoardState state = null;

	private RunningStateHolder runningState;

	public MoveComplete(RunningStateHolder runningState) {
		this.runningState = runningState;
	}

	public void listen() {
		while (runningState.isRunning() && state == null) {
			try {
				wait(1000);
			} catch (InterruptedException e) {
				return;
			}
		}
	}

	@Override
	public void setResult(BoardState result) {
		state = result;
	}

	public BoardState getResult() {
		return state;
	}
}
