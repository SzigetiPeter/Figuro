package com.figuro.player.botplayer;

import javafx.scene.Group;
import javafx.scene.control.Button;

import com.figuro.common.BoardState;
import com.figuro.engine.IMoveComplete;
import com.figuro.player.IPlayer;

public class BotPlayer implements IPlayer {
	private BoardState _board;
	private IStepSearch _stepSearch;
	private int _playerId;

	public BotPlayer(IStepSearch stepSearch) {
		_stepSearch = stepSearch;
	}

	@Override
	public int getId() {
		return _playerId;
	}

	@Override
	public void setId(int id) {
		_playerId = id;
	}

	@Override
	public void setInitialState(BoardState board) {
		_board = board;
	}

	@Override
	public void move(IMoveComplete callback) {
		try {
			BoardState evaluatedState = _stepSearch.search(_board, getId(),
					getId() == 1 ? 2 : 1);

			callback.setResult(evaluatedState);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void wrongMoveResetTo(BoardState board) {
		_board = board;
		setInitialState(board);

	}

	@Override
	public void notify(BoardState counterMove) {
		_board = counterMove;
	}

	@Override
	public void update(BoardState state) {
		_board = state;
	}

	@Override
	public boolean needsSetup() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getPrefferedOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setup(Group parent, Button okButton) {
		// TODO Auto-generated method stub

	}

}
