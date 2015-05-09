package com.figuro.game.boardGraphics;

import com.figuro.common.BoardState;
import com.figuro.engine.IMoveComplete;

public interface IAnimation {
	public void enableMove(BoardState currentState, IMoveComplete movedCallback);

	public void disableMove();
}
