package tests.figuro.engine.persistency;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.figuro.common.BoardState;
import com.figuro.common.IMessageSender;
import com.figuro.engine.persistency.Persistency;

/**
 * @author Dalyay Kinga
 */

public class PersistencyTest {

	@Test
	public void checkFileExistAfterSave()
	{
		IMessageSender messageSender = mock(IMessageSender.class);
		Persistency persistency = new Persistency(messageSender);
		
		List<String> players = new ArrayList<String>();
		
		players.add("player1");		
		players.add("player2");
		
		BoardState state = new BoardState(8, 8);
		
		persistency.save("Checkers", players, state);
		
		File file = new File("gameData.ser");		
		boolean savedFileFound = false;
		
		if(file.exists())
			savedFileFound = true;
		
		assertEquals(true, savedFileFound);			
	}
	
	@Test
	public void checkResultAfterFileLoaded()
	{
		IMessageSender messageSender = mock(IMessageSender.class);
		Persistency persistency = new Persistency(messageSender);
		
		List<String> players = new ArrayList<String>();
		
		players.add("player1");		
		players.add("player2");		
	
		boolean loaded = persistency.load();
		
		if(loaded)
		{
			assertEquals("Checkers", persistency.getGame());
			assertEquals(players, persistency.getPlayers());			
		}		
	}
}
