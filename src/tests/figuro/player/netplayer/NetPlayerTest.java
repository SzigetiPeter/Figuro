package tests.figuro.player.netplayer;

import java.awt.Point;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.junit.*;

import static org.mockito.Mockito.mock;

import com.figuro.common.BoardState;
import com.figuro.common.IMessageSender;
import com.figuro.engine.IMoveComplete;
import com.figuro.game.boardGraphics.Board;
import com.figuro.game.rules.Cell;
import com.figuro.game.rules.CheckersUnit;
import com.figuro.game.rules.UnitEnum;
import com.figuro.player.netplayer.NetPlayer;
import com.figuro.player.netplayer.ProcessMessages;
import com.figuro.player.netplayer.SendMessage;

/**
 * @author Keresztesi Tekla
 */
public class NetPlayerTest {

	static boolean stop = false;

	public static class AsNonApp extends Application implements IMessageSender,
	IMoveComplete {

		private String mGameStatus;
		private BoardState mBoardState;
		private BoardState boardStateToCheck;
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

			Test_Process_Messages();


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

			if (boardStateToCheck != null) {
				Assert.assertEquals(boardStateToCheck, result);
			}

			System.out.println("result: " + result);
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

		@Override
		public Label getMessage() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setMessage(Label message) {
			// TODO Auto-generated method stub

		}

		@Override
		public Label getScore() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setScore(Label score) {
			// TODO Auto-generated method stub

		}


		private BoardState getInitializedBoard() {
			BoardState st = new BoardState(8, 8);
			Cell black = new Cell();
			Cell white = new Cell();
			black.setUnit(new CheckersUnit(UnitEnum.PEASANT, 1));
			white.setUnit(new CheckersUnit(UnitEnum.PEASANT, 2));

			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 8; j++)
					st.set(new Point(i, j), new Cell());

			for (int i = 0; i < 2; i++)
				for (int j = 0; j < 4; j++) {
					int m = (i % 2) == 0 ? 0 : 1;
					st.set(new Point(j * 2 + m, i), black);
					st.set(new Point(j * 2 + (1 - m), 7 - i), white);
				}

			return st;
		}


		public void startClientThread(String address, int port) {
			try {
				Socket socket = new Socket(address, port);
				boardStateToCheck = getInitializedBoard();

				String toString = BoardState.toString(boardStateToCheck);
				(new SendMessage(socket, "NOTIFY " + toString)).start();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}

		@Test
		public void Test_Process_Messages() {
			try {
				ServerSocket socket = new ServerSocket(0);	
				startClientThread(socket.getInetAddress().getHostAddress(), socket.getLocalPort());

				Socket clSocket = socket.accept();

				ProcessMessages proc = new ProcessMessages(clSocket, null);
				proc.setCallback(this);
				proc.start();


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


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
