package tests.figuro.engine;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;

import org.junit.Test;

import com.figuro.common.IBuilder;
import com.figuro.engine.GameJob;
import com.figuro.engine.GameRunner;
import com.figuro.player.IPlayer;

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
}
