package com.figuro.player.botplayer;

import com.figuro.common.BoardState;

public interface IStepSearch {
	public BoardState search (BoardState current, int currentPlayerId, int opponentPlayerId) throws GameOverException;
}
