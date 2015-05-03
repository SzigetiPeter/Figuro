package com.figuro.player;

import javafx.scene.Group;

import com.figuro.common.BoardState;

public abstract class Player implements IPlayer {
	protected BoardState internalState;
	protected int id;
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void setInitialState(BoardState board) {
		internalState = board;
	}

	@Override
	public void wrongMoveResetTo(BoardState board) {
		internalState = board;
	}

	@Override
	public boolean needsSetup() {
		return false;
	}

	@Override
	public void setup(Group parent) {
		// do nothing
	}

	@Override
	public int getPrefferedOrder() {
		return 0;
	}
	
}
