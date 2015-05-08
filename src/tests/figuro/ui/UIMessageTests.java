package tests.figuro.ui;

import static org.junit.Assert.*;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.Test;
import com.figuro.main.ui.UIMessage;

public class UIMessageTests {
	
//	static boolean stop = false;
	
	public static class UIMessageAsNonApp extends Application {
	    @Override
	    public void start(Stage primaryStage) throws Exception {
	        // noop
	    }
	}
	
	@BeforeClass
	public static void initJFX() throws InterruptedException {
		Thread t = new Thread("JavaFX Init Thread") {
	        public void run() {
	        	try {
	        		Application.launch(UIMessageAsNonApp.class, new String[0]);	
				} catch (Exception e) {
					System.out.println("End...");
				}
	        }
	    };
	    t.setDaemon(true);
	    t.start();
	}

	@Test
	public void testDisplayMessage() {
		Label messageLabel = new Label();
		Label scoreLabel = new Label();
		String messageText = "Test message text";
		
		UIMessage uiMessage = new UIMessage(messageLabel, scoreLabel);
		uiMessage.displayMessage(messageText);
		
		assertTrue("Message text incorrect", messageText.equals(messageLabel.getText()));
	}
	
	@Test
	public void testUpdateGameState() {
		Label messageLabel = new Label();
		Label scoreLabel = new Label();
		String statusText = "Test score text";
		
		UIMessage uiMessage = new UIMessage(messageLabel, scoreLabel);
		uiMessage.updateGameState(statusText);
		
		assertTrue("Score text incorrect", statusText.equals(scoreLabel.getText()));
	}

}
