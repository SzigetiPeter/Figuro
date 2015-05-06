package tests.figuro.player.netplayer;

import java.net.InetSocketAddress;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.junit.BeforeClass;
import org.junit.Test;

import com.figuro.common.BoardState;
import com.figuro.common.IMessageSender;
import com.figuro.engine.IMoveComplete;
import com.figuro.player.netplayer.NetPlayer;

/**
 * @author Keresztesi Tekla
 */
public class NetPlayerTest {

	//@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	static boolean stop = false;
	
	public static class AsNonApp extends Application implements IMessageSender, IMoveComplete {
		
		private String mGameStatus;
		private BoardState mBoardState;
		ArrayList<InetSocketAddress> listeningAddresses = new ArrayList<InetSocketAddress>();
			
		@Override
		public void start(Stage primaryStage) throws Exception {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					System.out.println("Test stopped");
					try {
						stop = true;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
			NetPlayer netPlayer = new NetPlayer(this);
			netPlayer.setup(new Group());

			NetPlayer netPlayer2 = new NetPlayer(this);
			netPlayer2.setup(new Group());
			
		}

		@Override
		public void setResult(BoardState result) {
			mBoardState = result;
			
		}

		@Override
		public void displayMessage(String message) {
			System.out.println(message);
		}

		@Override
		public void updateGameState(String gameStatus) {
			mGameStatus = gameStatus;
			
		}
	}
	
	@BeforeClass
	public static void initJFX() throws InterruptedException {
		Thread t = new Thread("JavaFX Init Thread") {
	        public void run() {
	        	try {
	        		Application.launch(AsNonApp.class, new String[0]);	
				} catch (Exception e) {
					System.out.println("End...");
				}
	        }
	    };
	    t.setDaemon(true);
	    t.start();
	    
	    while (!stop) {
	    	Thread.sleep(100);
	    }
	}
	
	@Test
	public void TestNetPlayer() {
		
		try {
			initJFX();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	

}
