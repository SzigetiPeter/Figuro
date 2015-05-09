package com.figuro.player.botplayer;

import java.util.List;

import com.figuro.common.BoardState;
import com.figuro.common.Evaluation;
import com.figuro.game.rules.IGameRules;
import com.figuro.game.stepEvaluator.IStepEvaluator;

public class MinMaxSearch implements IStepSearch {
	private IStepEvaluator _stepEvaluator;
	private IGameRules _gameRules;
	private int _maxLevel;
	private int _currentPlayerId; // this is MIN
	private int _opponentPlayerId; // this is MAX

	public MinMaxSearch(IStepEvaluator stepEvaluator, IGameRules gameRules,
			int maxLevel) throws Exception {
		if (maxLevel < 1)
			throw new Exception(
					"The level of the search must be larger then 0.");

		_stepEvaluator = stepEvaluator;
		_gameRules = gameRules;

		_maxLevel = maxLevel;
	}

	public boolean isCurrentPlayerId(int playerId) {
		return playerId == _currentPlayerId;
	}

	public int getOtherPlayerId(int playerId) {
		return playerId == _currentPlayerId ? _opponentPlayerId
				: _currentPlayerId;
	}

	@Override
	public BoardState search(BoardState current, int currentPlayerId,
			int opponentPlayerId) throws GameOverException {
		if (_gameRules.isGameOver(current, opponentPlayerId))
			throw new GameOverException();

		_currentPlayerId = currentPlayerId;
		_opponentPlayerId = opponentPlayerId;

		EvaluatedBoardState evaluatedState = deepSearch(current,
				_currentPlayerId, _maxLevel);

		return evaluatedState.getBoardState();
	}

	public EvaluatedBoardState deepSearch(BoardState current, int playerId,
			int level) {
		if (level == 0)
			return new EvaluatedBoardState(null, _stepEvaluator.evaluate(
					current, playerId));

		int otherPlayerId = getOtherPlayerId(playerId);
		List<BoardState> possibleStates = _gameRules.getPossibleMoves(current,
				playerId);
		Evaluation bestEvaluation = null;
		BoardState bestState = null;

		for (BoardState possibleState : possibleStates)
			if (_gameRules.isGameOver(possibleState, playerId)) {
				bestEvaluation = evaluateGameOverState(possibleState, playerId);
				bestState = possibleState;

				break;
			} else {
				EvaluatedBoardState evaluatedState = deepSearch(possibleState,
						otherPlayerId, level - 1);
				Evaluation evaluation = evaluatedState.getEvaluation();

				if (bestEvaluation == null
						||
						// if it's MIN's turn then check if the current state's
						// evaluation is less than the actual best one
						// (evaluation < bestEvaluation)
						isCurrentPlayerId(playerId)
						&& _stepEvaluator.min(evaluation, bestEvaluation,
								playerId) == evaluation
						||
						// if it's MAX's turn then check if the current state's
						// evaluation is larger than the actual best one
						// (evaluation > bestEvaluation)
						!isCurrentPlayerId(playerId)
						&& _stepEvaluator.min(bestEvaluation, evaluation,
								playerId) == bestEvaluation) {

					bestEvaluation = evaluation;
					bestState = possibleState;
				}
			}

		return new EvaluatedBoardState(bestState, bestEvaluation);
	}

	public Evaluation evaluateGameOverState(BoardState current, int playerId) {
		Evaluation evaluation;
		int state = _gameRules.getFinalState(current, playerId);

		if (state != 0) {
			if (isCurrentPlayerId(playerId))
				evaluation = _stepEvaluator.getMinimum();
			else
				// !isCurrentPlayerId(playerId)
				evaluation = _stepEvaluator.getMaximum();
		} else
			evaluation = _stepEvaluator.evaluate(current, playerId);

		return evaluation;
	}
}
