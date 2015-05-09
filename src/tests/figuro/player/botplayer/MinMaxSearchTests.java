package tests.figuro.player.botplayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.figuro.common.BoardState;
import com.figuro.common.Evaluation;
import com.figuro.game.rules.IGameRules;
import com.figuro.game.stepEvaluator.IStepEvaluator;
import com.figuro.player.botplayer.EvaluatedBoardState;
import com.figuro.player.botplayer.GameOverException;
import com.figuro.player.botplayer.MinMaxSearch;

public class MinMaxSearchTests {
	private IStepEvaluator _stepEvaluator;
	private IGameRules _gameRules;
	private int _currentPlayerId = 1;
	private int _opponentPlayerId = 2;
	private MinMaxSearch _minMaxSearch;
	private MinMaxSearch _minMaxSearchSpy;

	@Before
	public void initialize() throws Exception {
		_stepEvaluator = mock(IStepEvaluator.class);
		_gameRules = mock(IGameRules.class);

		_minMaxSearch = new MinMaxSearch(_stepEvaluator, _gameRules, 2);
		_minMaxSearchSpy = Mockito.spy(_minMaxSearch);
	}

	@Test(expected = Exception.class)
	public void contructorShouldThrowExceptionForSearchLevelOf0()
			throws Exception {
		new MinMaxSearch(_stepEvaluator, _gameRules, 0);
	}

	@Test(expected = GameOverException.class)
	public void itShouldThrowExceptionIfGameIsOver() throws Exception {
		when(_gameRules.isGameOver(any(BoardState.class), any(Integer.class)))
				.thenReturn(true);

		_minMaxSearch.search(null, _currentPlayerId, _opponentPlayerId);
	}

	@Test
	public void searchMethodShouldInvokeDeepSearchMethodAndReturnBestState()
			throws Exception {
		BoardState currentBoardState = mock(BoardState.class);
		BoardState expectedBoardState = mock(BoardState.class);
		EvaluatedBoardState evaluatedBoardState = new EvaluatedBoardState(
				expectedBoardState, null);

		Mockito.doReturn(evaluatedBoardState).when(_minMaxSearchSpy)
				.deepSearch(currentBoardState, _currentPlayerId, 2);

		BoardState resultBoardState = _minMaxSearchSpy.search(
				currentBoardState, _currentPlayerId, _opponentPlayerId);

		assertEquals(expectedBoardState, resultBoardState);
	}

	@Test
	public void evaluateGameOverStateMethodShouldReturnWinnerEvaluationIfBotPlayerWins()
			throws Exception {
		BoardState current = mock(BoardState.class);
		Evaluation expectedEvaluation = mock(Evaluation.class);

		when(_gameRules.getFinalState(current, _currentPlayerId)).thenReturn(
				_currentPlayerId);
		when(_stepEvaluator.getMinimum()).thenReturn(expectedEvaluation);
		Mockito.doReturn(true).when(_minMaxSearchSpy)
				.isCurrentPlayerId(_currentPlayerId);

		Evaluation evaluation = _minMaxSearchSpy.evaluateGameOverState(current,
				_currentPlayerId);

		assertEquals(expectedEvaluation, evaluation);
	}

	@Test
	public void evaluateGameOverStateMethodShouldReturnWinnerEvaluationIfOpponentPlayerWins()
			throws Exception {
		BoardState current = mock(BoardState.class);
		Evaluation expectedEvaluation = mock(Evaluation.class);

		when(_gameRules.getFinalState(current, _opponentPlayerId)).thenReturn(
				_opponentPlayerId);
		when(_stepEvaluator.getMaximum()).thenReturn(expectedEvaluation);
		Mockito.doReturn(false).when(_minMaxSearchSpy)
				.isCurrentPlayerId(_opponentPlayerId);

		Evaluation evaluation = _minMaxSearch.evaluateGameOverState(current,
				_opponentPlayerId);

		assertEquals(expectedEvaluation, evaluation);
	}

	@Test
	public void evaluateGameOverStateMethodShouldReturnDrawEvaluationInCaseItIsADraw()
			throws Exception {
		BoardState current = mock(BoardState.class);
		Evaluation expectedEvaluation = mock(Evaluation.class);

		when(_gameRules.getFinalState(current, _currentPlayerId)).thenReturn(0);
		when(_stepEvaluator.evaluate(current, _currentPlayerId)).thenReturn(
				expectedEvaluation);

		Evaluation evaluation = _minMaxSearch.evaluateGameOverState(current,
				_currentPlayerId);

		assertEquals(expectedEvaluation, evaluation);
	}

	@Test
	public void deepSearchMethodShouldReturnEvaluationInCaseTheLevelIsZero()
			throws Exception {
		Evaluation expectedEvaluation = mock(Evaluation.class);
		BoardState current = mock(BoardState.class);

		when(_stepEvaluator.evaluate(current, _currentPlayerId)).thenReturn(
				expectedEvaluation);

		EvaluatedBoardState evaluatedBoardState = _minMaxSearchSpy.deepSearch(
				current, _currentPlayerId, 0);

		assertNotNull(evaluatedBoardState);
		assertEquals(expectedEvaluation, evaluatedBoardState.getEvaluation());

		// no other method should be invoked
		verify(_gameRules, never()).getPossibleMoves(any(BoardState.class),
				anyInt());
		verify(_minMaxSearchSpy, times(1)).deepSearch(any(BoardState.class),
				anyInt(), anyInt());
	}

	@Test
	public void deepSearchMethodShouldReturnStateIfGameIsOver() {
		BoardState current = mock(BoardState.class);
		BoardState firstPossibleState = mock(BoardState.class);
		Evaluation expectedEvaluation = mock(Evaluation.class);
		List<BoardState> possibleBoardStates = new ArrayList<BoardState>();

		possibleBoardStates.add(firstPossibleState);

		Mockito.doReturn(_opponentPlayerId).when(_minMaxSearchSpy)
				.getOtherPlayerId(_currentPlayerId);
		when(_gameRules.getPossibleMoves(current, _currentPlayerId))
				.thenReturn(possibleBoardStates);
		when(_gameRules.isGameOver(firstPossibleState, _currentPlayerId))
				.thenReturn(true);
		Mockito.doReturn(expectedEvaluation).when(_minMaxSearchSpy)
				.evaluateGameOverState(firstPossibleState, _currentPlayerId);

		EvaluatedBoardState evaluatedBoardState = _minMaxSearchSpy.deepSearch(
				current, _currentPlayerId, 2);

		assertNotNull(evaluatedBoardState);
		assertEquals(expectedEvaluation, evaluatedBoardState.getEvaluation());
		assertEquals(firstPossibleState, evaluatedBoardState.getBoardState());

		// no other method should be invoked
		verify(_minMaxSearchSpy, times(1)).deepSearch(any(BoardState.class),
				anyInt(), anyInt());
	}

	@Test
	public void deepSearchMethodShouldInvokeItselfProperlyIfGameIsNotOverAndItsMinsTurn() {
		BoardState current = mock(BoardState.class);
		BoardState firstPossibleState = mock(BoardState.class);
		BoardState secondPossibleState = mock(BoardState.class);
		List<BoardState> possibleBoardStates = new ArrayList<BoardState>();
		int level = 3;

		possibleBoardStates.add(firstPossibleState);
		possibleBoardStates.add(secondPossibleState);

		when(_gameRules.getPossibleMoves(current, _currentPlayerId))
				.thenReturn(possibleBoardStates);
		when(_gameRules.isGameOver(any(BoardState.class), anyInt()))
				.thenReturn(false);
		Mockito.doReturn(_opponentPlayerId).when(_minMaxSearchSpy)
				.getOtherPlayerId(_currentPlayerId);

		// secondEvaluatedBoardState should be better than the first one
		Evaluation firstEvaluation = mock(Evaluation.class);
		Evaluation secondEvaluation = mock(Evaluation.class);
		EvaluatedBoardState firstEvaluatedState = new EvaluatedBoardState(null,
				firstEvaluation);
		EvaluatedBoardState secondEvaluatedState = new EvaluatedBoardState(
				null, secondEvaluation);

		Mockito.doReturn(firstEvaluatedState).when(_minMaxSearchSpy)
				.deepSearch(firstPossibleState, _opponentPlayerId, level - 1);
		Mockito.doReturn(secondEvaluatedState).when(_minMaxSearchSpy)
				.deepSearch(secondPossibleState, _opponentPlayerId, level - 1);

		Mockito.doReturn(true).when(_minMaxSearchSpy)
				.isCurrentPlayerId(_currentPlayerId);

		when(
				_stepEvaluator.min(secondEvaluation, firstEvaluation,
						_currentPlayerId)).thenReturn(secondEvaluation);

		EvaluatedBoardState evaluatedBoardState = _minMaxSearchSpy.deepSearch(
				current, _currentPlayerId, level);

		assertNotNull(evaluatedBoardState);
		assertEquals(secondEvaluation, evaluatedBoardState.getEvaluation());
		assertEquals(secondPossibleState, evaluatedBoardState.getBoardState());

		// no other method should be invoked
		verify(_minMaxSearchSpy, times(3)).deepSearch(any(BoardState.class),
				anyInt(), anyInt());
	}

	@Test
	public void deepSearchMethodShouldInvokeItselfProperlyIfGameIsNotOverAndItsMaxsTurn() {
		BoardState current = mock(BoardState.class);
		BoardState firstPossibleState = mock(BoardState.class);
		BoardState secondPossibleState = mock(BoardState.class);
		List<BoardState> possibleBoardStates = new ArrayList<BoardState>();
		int level = 3;

		possibleBoardStates.add(firstPossibleState);
		possibleBoardStates.add(secondPossibleState);

		when(_gameRules.getPossibleMoves(current, _opponentPlayerId))
				.thenReturn(possibleBoardStates);
		when(_gameRules.isGameOver(any(BoardState.class), anyInt()))
				.thenReturn(false);
		Mockito.doReturn(_currentPlayerId).when(_minMaxSearchSpy)
				.getOtherPlayerId(_opponentPlayerId);

		// secondEvaluatedBoardState should be better than the first one
		Evaluation firstEvaluation = mock(Evaluation.class);
		Evaluation secondEvaluation = mock(Evaluation.class);
		EvaluatedBoardState firstEvaluatedState = new EvaluatedBoardState(null,
				firstEvaluation);
		EvaluatedBoardState secondEvaluatedState = new EvaluatedBoardState(
				null, secondEvaluation);

		Mockito.doReturn(firstEvaluatedState).when(_minMaxSearchSpy)
				.deepSearch(firstPossibleState, _currentPlayerId, level - 1);
		Mockito.doReturn(secondEvaluatedState).when(_minMaxSearchSpy)
				.deepSearch(secondPossibleState, _currentPlayerId, level - 1);

		Mockito.doReturn(false).when(_minMaxSearchSpy)
				.isCurrentPlayerId(_opponentPlayerId);

		when(
				_stepEvaluator.min(firstEvaluation, secondEvaluation,
						_opponentPlayerId)).thenReturn(firstEvaluation);

		EvaluatedBoardState evaluatedBoardState = _minMaxSearchSpy.deepSearch(
				current, _opponentPlayerId, level);

		assertNotNull(evaluatedBoardState);
		assertEquals(secondEvaluation, evaluatedBoardState.getEvaluation());
		assertEquals(secondPossibleState, evaluatedBoardState.getBoardState());

		// no other method should be invoked
		verify(_minMaxSearchSpy, times(3)).deepSearch(any(BoardState.class),
				anyInt(), anyInt());
	}
}
