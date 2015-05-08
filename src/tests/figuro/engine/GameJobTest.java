package tests.figuro.engine;

import static org.mockito.Mockito.*;
import org.junit.Test;

import com.figuro.engine.GameJob;
import com.figuro.engine.persistency.IPersistency;
import com.figuro.player.IPlayer;

/**
 * @author Dalyay Kinga
 */

public class GameJobTest {

	@Test(expected=Exception.class)
	public void addPlayerMethodToAddThreePlayersShouldThrowException() throws Exception {
		IPersistency persistency = mock(IPersistency.class);
		GameJob gameJob = new GameJob(persistency);
		
		IPlayer thePlayer = mock(IPlayer.class);
		
		gameJob.addPlayer(thePlayer);		
		gameJob.addPlayer(thePlayer);		
		gameJob.addPlayer(thePlayer);
	}
}
