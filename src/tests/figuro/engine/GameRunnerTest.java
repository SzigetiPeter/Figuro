package tests.figuro.engine;

import static org.mockito.Mockito.*;
import org.junit.Test;

import com.figuro.common.IBuilder;
import com.figuro.engine.GameJob;
import com.figuro.engine.GameRunner;
import com.figuro.engine.IGameoverCallback;
import com.figuro.player.IPlayer;

/**
 * @author Dalyay Kinga
 */

public class GameRunnerTest {
	
	@Test
	public void addPlayerMethodCreatesAndAddsPlayer () throws Exception {
		GameJob gameJob = mock(GameJob.class);
		IBuilder builder = mock(IBuilder.class);
		
		GameRunner gr = new GameRunner (gameJob, builder, null, null);
		IPlayer thePlayer = mock(IPlayer.class);
		
		when(builder.createPlayer("testPlayer")).thenReturn(thePlayer);

		gr.addPlayer("testPlayer");
		
		verify(gameJob).addPlayer(thePlayer); 
	}
	
	@Test
	public void runGameMethod() {
		//GameJob gameJob = mock(GameJob.class);
		//IBuilder builder = mock(IBuilder.class);
		//IGameRules gameRules = mock(IGameRules.class);
		//IMessageSender message = mock(IMessageSender.class);
		//IPersistency persistency = mock(IPersistency.class);
		
		//GameRunner gr = new GameRunner (gameJob, builder, gameRules, message, persistency);
		GameRunner gameRunner = mock(GameRunner.class);
		IGameoverCallback callback = mock(IGameoverCallback.class);
		
		//IGameRules rules = mock(IGameRules.class); 
		//IStepEvaluator evaluator = mock(IStepEvaluator.class);
		//IBoardGraphics graphics = mock(IBoardGraphics.class);
		
	//	Game game = mock(Game.class);
		gameRunner.runGame("Checkers", callback);
		
	//	boolean bla = gameRunner.isGameResumable();
		//assertEquals(bla, false);
		
		//Game game;
		
	    //when(builder.createGame("gameType")).thenReturn(game);
	}
}
