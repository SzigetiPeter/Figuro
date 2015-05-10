package tests.figuro.player.netplayer;

import java.net.InetSocketAddress;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

	// @Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	static boolean stop = false;

	public static class AsNonApp extends Application implements IMessageSender,
			IMoveComplete {

		private String mGameStatus;
		private BoardState mBoardState;
		ArrayList<InetSocketAddress> listeningAddresses = new ArrayList<InetSocketAddress>();

		@Override
		public void start(Stage primaryStage) throws Exception {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 400, 400);
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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

			Group parent = new Group();
			Stage newStage = new Stage();
			newStage.setScene(new Scene(parent, 300, 200));
			NetPlayer netPlayer = new NetPlayer(this);
			netPlayer.setup(parent, new Button());
			newStage.show();

			Group newGroup2 = new Group();
			Stage newStage2 = new Stage();
			newStage2.setScene(new Scene(newGroup2, 300, 200));
			NetPlayer netPlayer2 = new NetPlayer(this);
			netPlayer2.setup(newGroup2, new Button());
			newStage2.show();

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
