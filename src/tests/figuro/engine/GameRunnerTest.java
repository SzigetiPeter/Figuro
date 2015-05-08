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
		
		GameRunner gr = new GameRunner (gameJob, builder, null, null, null);
		IPlayer thePlayer = mock(IPlayer.class);
		
		when(builder.createPlayer("testPlayer")).thenReturn(thePlayer);

		gr.addPlayer("testPlayer");
		
		verify(gameJob).addPlayer(thePlayer); 
	}
	
	@Test
	public void runGameMethod() {		
		GameRunner gameRunner = mock(GameRunner.class);
		IGameoverCallback callback = mock(IGameoverCallback.class);		
	
		gameRunner.runGame("Checkers", callback);		
	}
}
