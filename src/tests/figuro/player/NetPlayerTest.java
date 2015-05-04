package tests.figuro.player;

import java.net.InetSocketAddress;
import java.util.ArrayList;

import javafx.scene.Group;

import org.junit.Rule;
import org.junit.Test;

import com.figuro.player.IConnectDelegate;
import com.figuro.player.NetPlayer;

/**
 * @author Keresztesi Tekla
 */
public class NetPlayerTest implements IConnectDelegate{

	ArrayList<InetSocketAddress> listeningAddresses = new ArrayList<InetSocketAddress>();
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Test
	public void TestNetPlayer() { //ez a mainbe jar
		
		try {
			//BorderPane root = new BorderPane();
			//Scene scene = new Scene(root,400,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//primaryStage.setScene(scene);
			//primaryStage.show();

			NetPlayer netPlayer = new NetPlayer(this);
			netPlayer.setId(1);
			netPlayer.setup(new Group());

			NetPlayer netPlayer2 = new NetPlayer(this);
			netPlayer2.setId(2);
			netPlayer2.setup(new Group());


		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void isListeningOn(String ipString, int portNumber) {

		listeningAddresses.add(new InetSocketAddress(ipString, portNumber));
		// ha van 
	}

	@Override
	public void stoppedListening(String ipString, int portNumber) {
		int i = 0;
		for (i = 0; i < listeningAddresses.size();  i++) {
			if (listeningAddresses.get(i).getAddress().equals(ipString) && listeningAddresses.get(i).getPort() == portNumber) {
				break;
			}
		}

		if (i < listeningAddresses.size()) {
			listeningAddresses.remove(i);
		}
	}

	@Override
	public InetSocketAddress getPlayerInetAdress() {
		if (listeningAddresses.size() > 0)
			return listeningAddresses.get(0);
		else
			return null;
	}

}
