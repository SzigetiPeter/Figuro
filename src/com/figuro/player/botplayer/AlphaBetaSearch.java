package com.figuro.player.botplayer;

import java.util.List;

import com.figuro.common.BoardState;
import com.figuro.common.Evaluation;
import com.figuro.game.rules.IGameRules;
import com.figuro.game.stepEvaluator.IStepEvaluator;

public class AlphaBetaSearch implements IStepSearch {
	private IStepEvaluator _stepEvaluator;
	private IGameRules _gameRules;
	private int _maxLevel;
	private int _currentPlayerId; // this is MIN
	private int _opponentPlayerId; // this is MAX
	
	public AlphaBetaSearch (IStepEvaluator stepEvaluator, IGameRules gameRules, int maxLevel) throws Exception {
		if (maxLevel < 1)
			throw new Exception ("The level of the search must be larger then 0.");
		
		_stepEvaluator = stepEvaluator;
		_gameRules = gameRules;
		
		_maxLevel = maxLevel;
	}
	
	// private 
	boolean isCurrentPlayerId (int playerId) {
		return playerId == _currentPlayerId;
	}
	
	// private
	int getOtherPlayerId (int playerId) {
		return playerId == _currentPlayerId ? _opponentPlayerId : _currentPlayerId;
	}
	
	@Override
	public BoardState search(BoardState current, int currentPlayerId, int opponentPlayerId) throws GameOverException {
		if (_gameRules.isGameOver(current, opponentPlayerId))
			throw new GameOverException();
		
		_currentPlayerId = currentPlayerId;
		_opponentPlayerId = opponentPlayerId;
		
		// alpha stores MAX's assured value and beta stores MIN's assured value
		// in the beginning of the search these values should be (alpha, beta) of (-Infinity, Infinity)
		EvaluatedBoardState evaluatedState = 
			deepSearch (current, _currentPlayerId, _maxLevel, _stepEvaluator.getMinimum(), _stepEvaluator.getMaximum());
		
		return evaluatedState.getBoardState();
	}

	// private 
	EvaluatedBoardState deepSearch (BoardState current, int playerId, int level, Evaluation alpha, Evaluation beta) {
		if (level == 0)
			return new EvaluatedBoardState (null, _stepEvaluator.evaluate(current, playerId));
		
		int otherPlayerId = getOtherPlayerId(playerId);
		List<BoardState> possibleStates = _gameRules.getPossibleMoves(current, playerId);
		BoardState bestState = null;
		
		for (BoardState possibleState : possibleStates) 
			if (_gameRules.isGameOver(possibleState, playerId)) {
				Evaluation evaluation = evaluateGameOverState(possibleState, playerId);
				
				return new EvaluatedBoardState (possibleState, evaluation);
			}
			else
			{		
				EvaluatedBoardState evaluatedState = deepSearch (possibleState, otherPlayerId, level - 1, alpha, beta);
				Evaluation evaluation = evaluatedState.getEvaluation();
				
				// MAX's turn
				if (!isCurrentPlayerId(playerId)) {
					if (_stepEvaluator.min(alpha, evaluation, playerId) == evaluation) {
						alpha = evaluation;
						bestState = evaluatedState.getBoardState();
					}
				}
				// MIN's turn
				else // isCurrentPlayerId(playerId)
					if (_stepEvaluator.min(evaluation, beta, playerId) == evaluation) {
						beta = evaluation;
						bestState = evaluatedState.getBoardState();
					}
				
				// alpha >= beta
				if (alpha.equals(beta) || _stepEvaluator.min(alpha, beta, playerId) == beta)
					return new EvaluatedBoardState(bestState, alpha);
			}
		
		return new EvaluatedBoardState (bestState, playerId == _opponentPlayerId ? alpha : beta);
	}
		
	// private 
	Evaluation evaluateGameOverState (BoardState current, int playerId) {
		Evaluation evaluation;
		int state = _gameRules.getFinalState(current, playerId);
		
		if (state != 0)
		{
			if (isCurrentPlayerId(playerId))
				evaluation = _stepEvaluator.getMinimum();
			else // !isCurrentPlayerId(playerId)
				evaluation = _stepEvaluator.getMaximum();
		}
		else
			evaluation = _stepEvaluator.evaluate(current, playerId);
		
		return evaluation;
	}
}
