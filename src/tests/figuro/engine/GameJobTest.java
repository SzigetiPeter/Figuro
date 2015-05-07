package tests.figuro.engine;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.figuro.engine.GameJob;
import com.figuro.engine.IGameoverCallback;
import com.figuro.game.Game;
import com.figuro.player.IPlayer;
import com.figuro.player.Player;
import com.figuro.player.uiplayer.UIPlayer;

public class GameJobTest {

	@Test
	public void testAddPlayerMethodToAddTwoPlayers() throws Exception {
		GameJob gameJob = new GameJob();
		
		IPlayer thePlayer = mock(IPlayer.class);
		
		gameJob.addPlayer(thePlayer);
		gameJob.addPlayer(thePlayer);
	}

	@Test(expected=Exception.class)
	public void testAddPlayerMethodToAddThreePlayersShouldThrowException() throws Exception {
		GameJob gameJob = new GameJob();
		
		IPlayer thePlayer = mock(IPlayer.class);
		
		gameJob.addPlayer(thePlayer);		
		gameJob.addPlayer(thePlayer);		
		gameJob.addPlayer(thePlayer);
	}
	
	@Test
	public void testRunMethodRunGame() {
		GameJob gameJob = new GameJob();
		
		IPlayer thePlayer = mock(IPlayer.class);
		
		
	}
}
