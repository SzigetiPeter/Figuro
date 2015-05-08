package tests.figuro.common;

import static org.junit.Assert.*;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import org.junit.BeforeClass;
import org.junit.Test;

import com.figuro.common.Builder;
import com.figuro.common.IMessageSender;
import com.figuro.game.Checkers;
import com.figuro.game.Game;
import com.figuro.game.boardGraphics.BoardGraphics;
import com.figuro.game.rules.CheckersRules;
import com.figuro.game.stepEvaluator.CheckersEvaluator;
import com.figuro.main.ui.UIMessage;
import com.figuro.player.IPlayer;
import com.figuro.player.botplayer.BotPlayer;
import com.figuro.player.netplayer.NetPlayer;
import com.figuro.player.uiplayer.UIPlayer;



public class BuilderTests {
	
	private static Stage stage2;
	
	public static class BuilderAsNonApp extends Application {
	    @Override
	    public void start(Stage primaryStage) throws Exception {
	        // noop
	    	stage2=primaryStage;
	    }
	}
	
	@BeforeClass
	public static void initJFX() throws InterruptedException {
		Thread t = new Thread("JavaFX Init Thread") {
	        public void run() {
	        	try {
	        		Application.launch(BuilderAsNonApp.class, new String[0]);	
				} catch (Exception e) {
					System.out.println("End...");
				}
	        }
	    };
	    t.setDaemon(true);
	    t.start();
	}
	
	@Test
	public void testGetGameTypes() {
		//fail("Not yet implemented");
		Label messageLabel = new Label();
		Label scoreLabel = new Label();
		IMessageSender messages = new UIMessage(messageLabel, scoreLabel);
		
		Builder builder = new Builder(stage2, messages);
		builder.getGameTypes();
		
		assertTrue("Game type incorrect", builder.getGameTypes()[0].equals("game.checkers"));
	}

	@Test
	public void testCreateGame_Checkers(){
		Label messageLabel = new Label();
		Label scoreLabel = new Label();
		IMessageSender messages = new UIMessage(messageLabel, scoreLabel);
		
		Builder builder = new Builder(stage2, messages);
		Game g1 = builder.createGame("game.checkers");
		
		assertTrue("Game checkers creation incorrect", g1.getClass().equals(Checkers.class));
	}
	
	@Test
	public void testCreateGame_Null(){
		Label messageLabel = new Label();
		Label scoreLabel = new Label();
		IMessageSender messages = new UIMessage(messageLabel, scoreLabel);
		
		Builder builder = new Builder(stage2, messages);
		Game g1 = builder.createGame("nogame");
		
		assertTrue("Game checkers creation incorrect", g1 == null);
	}
	
	@Test
	public void testCreatePlayer_UIPlayer(){
		Label messageLabel = new Label();
		Label scoreLabel = new Label();
		IMessageSender messages = new UIMessage(messageLabel, scoreLabel);
		
		Builder builder = new Builder(stage2, messages);
		
		IPlayer p_ui = builder.createPlayer("player.ui");
		
		assertTrue("PlayerUI creation incorrect", p_ui.getClass().equals(UIPlayer.class));
	}
	
	@Test
	public void testCreatePlayer_NetPlayer(){
		Label messageLabel = new Label();
		Label scoreLabel = new Label();
		IMessageSender messages = new UIMessage(messageLabel, scoreLabel);
		
		Builder builder = new Builder(stage2, messages);
		
		IPlayer p_net = builder.createPlayer("player.net");
		
		assertTrue("NetPlayer creation incorrect", p_net.getClass().equals(NetPlayer.class));
	}
	
	@Test
	public void testCreatePlayer_AlphabetaPlayer(){
		Label messageLabel = new Label();
		Label scoreLabel = new Label();
		IMessageSender messages = new UIMessage(messageLabel, scoreLabel);
		
		Builder builder = new Builder(stage2, messages);
		
		IPlayer p_alphabeta = builder.createPlayer("player.alphabeta");
		
		assertTrue("BotPlayer creation incorrect", p_alphabeta.getClass().equals(BotPlayer.class));
	}
	
	@Test
	public void testCreatePlayer_UnknownPlayer(){
		Label messageLabel = new Label();
		Label scoreLabel = new Label();
		IMessageSender messages = new UIMessage(messageLabel, scoreLabel);
		
		Builder builder = new Builder(stage2, messages);
		
		IPlayer p_unknown = builder.createPlayer("player.unknown");
		
		assertTrue("Unknown player creation incorrect", p_unknown == null);
	}
	
	@Test
	public void testGet_Game(){
		Label messageLabel = new Label();
		Label scoreLabel = new Label();
		IMessageSender messages = new UIMessage(messageLabel, scoreLabel);
		
		Builder builder = new Builder(stage2, messages);
		Game g1 = builder.createGame("game.checkers");
		
		Object o1 = builder.get("game.checkers");
		assertTrue("Get incorrect", g1.equals(o1));
	}
	
	@Test
	public void testGet_Player(){
		Label messageLabel = new Label();
		Label scoreLabel = new Label();
		IMessageSender messages = new UIMessage(messageLabel, scoreLabel);
		
		Builder builder = new Builder(stage2, messages);
		IPlayer p1 = builder.createPlayer("player.ui");
		
		Object o1 = builder.get("player.ui");
		assertTrue("Get incorrect", p1.equals(o1));
	}
	
	@Test
	public void testFree(){
		Label messageLabel = new Label();
		Label scoreLabel = new Label();
		IMessageSender messages = new UIMessage(messageLabel, scoreLabel);
		
		Builder builder = new Builder(stage2, messages);
		builder.createGame("game.checkers");
		builder.createPlayer("player.net");
		
		builder.free();
		
		Object o1 = builder.get("game.checkers");
		Object o2 = builder.get("player.net");
		
		assertTrue("Free incorrect", o1 == null && o2 == null);
	}
}
