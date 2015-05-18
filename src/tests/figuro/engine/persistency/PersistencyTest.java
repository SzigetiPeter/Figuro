package tests.figuro.engine.persistency;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.figuro.common.BoardState;
import com.figuro.common.IMessageSender;
import com.figuro.engine.persistency.Persistency;

/**
 * @author Dalyay Kinga
 */

public class PersistencyTest {

	@Test
	public void checkFileExistAfterSave() {
		IMessageSender messageSender = mock(IMessageSender.class);
		Persistency persistency = new Persistency(messageSender);

		List<String> players = new ArrayList<String>();

		players.add("player1");
		players.add("player2");

		BoardState state = new BoardState(8, 8);

		persistency.setGame("game.checkers");
		persistency.setPlayers(players);
		persistency.save(state, 1);

		File file = new File("gameData.ser");
		boolean savedFileFound = false;

		if (file.exists())
			savedFileFound = true;

		assertEquals(true, savedFileFound);
	}

	@Test
	public void checkResultAfterFileLoaded() {
		IMessageSender messageSender = mock(IMessageSender.class);
		Persistency persistency = new Persistency(messageSender);

		List<String> players = new ArrayList<String>();

		players.add("player1");
		players.add("player2");

		boolean loaded = persistency.load();

		if (loaded) {
			assertEquals("game.checkers", persistency.getGame());
			assertEquals(players, persistency.getPlayers());
		}
	}
}
