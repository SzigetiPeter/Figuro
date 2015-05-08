package com.figuro.player.botplayer;

import com.figuro.common.BoardState;
import com.figuro.common.Evaluation;

public class EvaluatedBoardState {
	private BoardState _boardState;
	private Evaluation _evaluation;
	
	public EvaluatedBoardState (BoardState boardState, Evaluation evaluation) {
		this._boardState = boardState;
		this._evaluation = evaluation;
	}
	
	public BoardState getBoardState() {
		return _boardState;
	}
	
	public Evaluation getEvaluation() {
		return _evaluation;
	}
}
