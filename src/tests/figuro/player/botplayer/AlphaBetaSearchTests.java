package tests.figuro.player.botplayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.figuro.common.BoardState;
import com.figuro.common.Evaluation;
import com.figuro.game.rules.IGameRules;
import com.figuro.game.stepEvaluator.IStepEvaluator;
import com.figuro.player.botplayer.AlphaBetaSearch;
import com.figuro.player.botplayer.EvaluatedBoardState;
import com.figuro.player.botplayer.GameOverException;

// we should test functionality that is different from the minmax search
public class AlphaBetaSearchTests {
	private IStepEvaluator _stepEvaluator;
	private IGameRules _gameRules;
	private int _currentPlayerId = 1;
	private int _opponentPlayerId = 2;
	private AlphaBetaSearch _abSearch;
	private AlphaBetaSearch _abSearchSpy;
	
	@Before 
	public void initialize() throws Exception {
		_stepEvaluator = mock(IStepEvaluator.class);
		_gameRules = mock(IGameRules.class);
		
		_abSearch = new AlphaBetaSearch (_stepEvaluator, _gameRules, 2);
		_abSearchSpy = Mockito.spy(_abSearch);
    }
	
	@Test
	public void searchMethodShouldInvokeDeepSearchMethodWithProperParametersAndReturnState() throws GameOverException{
		BoardState currentBoardState = mock(BoardState.class); 
		BoardState expectedBoardState = mock(BoardState.class); 
		EvaluatedBoardState evaluatedBoardState = new EvaluatedBoardState(expectedBoardState, null);
		
		Evaluation min = mock(Evaluation.class);
		Evaluation max = mock(Evaluation.class);
		
		when(_stepEvaluator.getMinimum()).thenReturn(min);
		when(_stepEvaluator.getMaximum()).thenReturn(max);
		
		Mockito.doReturn(evaluatedBoardState).when(_abSearchSpy).deepSearch(currentBoardState, _currentPlayerId, 2, min, max);

		BoardState resultBoardState = _abSearchSpy.search(currentBoardState, _currentPlayerId, _opponentPlayerId);
		
		assertEquals(expectedBoardState, resultBoardState);
	}
	
	@Test
	public void deepSearchMethodShouldCutInCaseAlphaIsLargerThanBetaAndItsMinsTurn () {
		BoardState current = mock(BoardState.class);
		BoardState firstPossibleState = mock(BoardState.class); 
		BoardState secondPossibleState = mock(BoardState.class);
		BoardState thirdPossibleState = mock(BoardState.class);
		List<BoardState> possibleBoardStates = new ArrayList<BoardState>();
		int level = 3;
		
		possibleBoardStates.add(firstPossibleState);
		possibleBoardStates.add(secondPossibleState);
		possibleBoardStates.add(thirdPossibleState);
		
		when(_gameRules.getPossibleMoves(current, _currentPlayerId)).thenReturn(possibleBoardStates);
		when(_gameRules.isGameOver(any(BoardState.class), anyInt())).thenReturn(false);
		Mockito.doReturn(_opponentPlayerId).when(_abSearchSpy).getOtherPlayerId(_currentPlayerId);
		
		Evaluation firstEvaluation = mock(Evaluation.class); // let's say this would be 30
		Evaluation secondEvaluation = mock(Evaluation.class); // let's say this would be -10
		EvaluatedBoardState firstEvaluatedState = new EvaluatedBoardState(firstPossibleState, firstEvaluation);
		EvaluatedBoardState secondEvaluatedState = new EvaluatedBoardState(secondPossibleState, secondEvaluation);
		
		Evaluation alpha = mock(Evaluation.class); // let's say this would be 10
		Evaluation beta = mock(Evaluation.class); // let's say this would be 50
		
		Mockito.doReturn(firstEvaluatedState).when(_abSearchSpy).deepSearch(firstPossibleState, _opponentPlayerId, level - 1, alpha, beta);
		Mockito.doReturn(secondEvaluatedState).when(_abSearchSpy).deepSearch(secondPossibleState, _opponentPlayerId, level - 1, alpha, firstEvaluation);
		
		Mockito.doReturn(true).when(_abSearchSpy).isCurrentPlayerId(_currentPlayerId);
		
		// 30 < 50 so beta would be overwritten with 30
		when(_stepEvaluator.min(firstEvaluation, beta, _currentPlayerId)).thenReturn(firstEvaluation);
		// -10 < 30 so beta would be overwritten with -10
		when(_stepEvaluator.min(secondEvaluation, firstEvaluation, _currentPlayerId)).thenReturn(secondEvaluation);
		
		// alpha < beta for the firstEvaluation so no cutting will take place
		when(_stepEvaluator.min(alpha, firstEvaluation, _currentPlayerId)).thenReturn(alpha);
		
		// alpha >= beta for the secondEvaluation so we need to cut
		when(_stepEvaluator.min(alpha, secondEvaluation, _currentPlayerId)).thenReturn(secondEvaluation);
		
		EvaluatedBoardState evaluatedBoardState = _abSearchSpy.deepSearch(current, _currentPlayerId, level, alpha, beta);
		
		assertNotNull(evaluatedBoardState);
		assertEquals(secondEvaluation, evaluatedBoardState.getEvaluation());
		assertEquals(secondPossibleState, evaluatedBoardState.getBoardState());
		
		// no other method should be invoked
		verify(_abSearchSpy, times(3)).deepSearch(any(BoardState.class), anyInt(), anyInt(), any(Evaluation.class), any(Evaluation.class));
	}
	
	@Test
	public void deepSearchMethodShouldCutInCaseAlphaIsLargerThanBetaAndItsMaxsTurn () {
		BoardState current = mock(BoardState.class);
		BoardState firstPossibleState = mock(BoardState.class); 
		BoardState secondPossibleState = mock(BoardState.class);
		BoardState thirdPossibleState = mock(BoardState.class);
		List<BoardState> possibleBoardStates = new ArrayList<BoardState>();
		int level = 3;
		
		possibleBoardStates.add(firstPossibleState);
		possibleBoardStates.add(secondPossibleState);
		possibleBoardStates.add(thirdPossibleState);
		
		when(_gameRules.getPossibleMoves(current, _opponentPlayerId)).thenReturn(possibleBoardStates);
		when(_gameRules.isGameOver(any(BoardState.class), anyInt())).thenReturn(false);
		Mockito.doReturn(_currentPlayerId).when(_abSearchSpy).getOtherPlayerId(_opponentPlayerId);
		
		Evaluation firstEvaluation = mock(Evaluation.class); // let's say this would be 30
		Evaluation secondEvaluation = mock(Evaluation.class); // let's say this would be 60
		EvaluatedBoardState firstEvaluatedState = new EvaluatedBoardState(firstPossibleState, firstEvaluation);
		EvaluatedBoardState secondEvaluatedState = new EvaluatedBoardState(secondPossibleState, secondEvaluation);
		
		Evaluation alpha = mock(Evaluation.class); // let's say this would be 10
		Evaluation beta = mock(Evaluation.class); // let's say this would be 50
		
		Mockito.doReturn(firstEvaluatedState).when(_abSearchSpy).deepSearch(firstPossibleState, _currentPlayerId, level - 1, alpha, beta);
		Mockito.doReturn(secondEvaluatedState).when(_abSearchSpy).deepSearch(secondPossibleState, _currentPlayerId, level - 1, firstEvaluation, beta);
		
		Mockito.doReturn(false).when(_abSearchSpy).isCurrentPlayerId(_opponentPlayerId);
		
		// 30 > 10 so alpha would be overwritten with 30
		when(_stepEvaluator.min(alpha, firstEvaluation, _opponentPlayerId)).thenReturn(alpha);
		// 60 > 30 so alpha would be overwritten with 60
		when(_stepEvaluator.min(firstEvaluation, secondEvaluation, _opponentPlayerId)).thenReturn(firstEvaluation);
		
		// alpha < beta for the firstEvaluation so no cutting will take place
		when(_stepEvaluator.min(firstEvaluation, beta, _opponentPlayerId)).thenReturn(firstEvaluation);
		
		// alpha >= beta for the secondEvaluation so we need to cut
		when(_stepEvaluator.min(secondEvaluation, beta, _opponentPlayerId)).thenReturn(beta);
		
		EvaluatedBoardState evaluatedBoardState = _abSearchSpy.deepSearch(current, _opponentPlayerId, level, alpha, beta);
		
		assertNotNull(evaluatedBoardState);
		assertEquals(secondEvaluation, evaluatedBoardState.getEvaluation());
		assertEquals(secondPossibleState, evaluatedBoardState.getBoardState());
		
		// no other method should be invoked
		verify(_abSearchSpy, times(3)).deepSearch(any(BoardState.class), anyInt(), anyInt(), any(Evaluation.class), any(Evaluation.class));
	}
}
