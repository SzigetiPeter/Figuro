package com.figuro.player;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.URL;
import java.net.UnknownHostException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.figuro.common.BoardState;
import com.figuro.common.IMessageSender;
import com.figuro.engine.IMoveComplete;


public class NetPlayer implements IPlayer, IMessageSender, IMoveComplete {
	
	protected BoardState mBoardState;
	protected String mGameStatus;
	protected int mId;
	protected String mIP;
	protected int mPort;
	
	@Override
	public void displayMessage(String message) {
		System.out.println(message);		
	}

	@Override
	public void updateGameState(String gameStatus) {
	
		mGameStatus = gameStatus;
		System.out.println(gameStatus);
	}

	@Override
	public void setInitialState(BoardState board) {
	
		mBoardState = board;
		
	}

	@Override
	public void move(IMoveComplete callback) {
		
		try {
			callback.setResult(mBoardState);
		} catch (NullPointerException e) {
			System.out.println("Callback was null");
		}
	}

	@Override
	public void wrongMoveResetTo(BoardState board) {
		//throw exception here, nem kene ilyen megtortenjen, hogy rossz lepes van, nem tudom lekezelni
		//IllegalMoveException exc = new IllegalMoveException();
		//throw exc;
		
		mBoardState = board;
	}


	@Override
	public int getId() {
		return mId;
	}

	@Override
	public void setId(int id) {
		mId = id;
	}

	@Override
	public boolean needsSetup() {
		return true;
	}

	//helper to get external IP address
	public static String getIp() throws Exception {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            String ip = in.readLine();
            return ip;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	@Override
	public void setup(Group parent) {

		try {
			
			mIP = getIp();
			System.out.println(mIP);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			displayMessage("Could not create connection [E1]");
			return;
			
		}
		
		MyDialog dialog = new MyDialog(parent, mIP);
		dialog.sizeToScene();
		dialog.show();
		
	}

	@Override
	public int getPrefferedOrder() {
		
		return 0;
	}

	@Override
	public void setResult(BoardState result) {
		mBoardState = result;
		
	}
	
	public void listen() {
		ServerSocket s;
		try {
			s = new ServerSocket(0);
			
			mPort = s.getLocalPort();
			
			System.out.println(mIP + " port: " + mPort);
			
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
			displayMessage("Could not create connection [E2]");
			return;
			
		} catch (IOException e) {
			
			e.printStackTrace();
			displayMessage("Could not create connection [E3]");
			return;
		}
		
		
		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
			displayMessage("Could not close connection [E4]");
			return;
		}
	}
	
	class MyDialog extends Stage {
		
		public MyDialog(Group owner, String ipString) {
			super();
			setTitle("Setup");
			initModality(Modality.NONE);
			
			
			GridPane gridpane = new GridPane();
	        gridpane.setPadding(new Insets(5));
	        gridpane.setHgap(5);
	        gridpane.setVgap(5);

	        Label userNameLbl = new Label("Your IP: ");
	        gridpane.add(userNameLbl, 0, 1);

	        Label portLbl = new Label("Port: ");
	        gridpane.add(portLbl, 0, 2);
	        
	        Label userNameFld = new Label(ipString);
	        gridpane.add(userNameFld, 1, 1);

	        Label portFld = new Label();
	        portFld.setText("0");
	        gridpane.add(portFld, 1, 2);

	        Button listen = new Button("Listen");
	        listen.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
	        	
	        	@Override
	            public void handle(ActionEvent event) {
	            	System.out.println("Listen 1");
	            	listen();
	            	
	                //close();
	            }
	        });
	        
	        gridpane.add(listen, 1, 3);
	        GridPane.setHalignment(listen, HPos.RIGHT);
	        
	        Scene scene = new Scene(gridpane, 400, 400);
	        setScene(scene);

	        
		}
		
	}

	@Override
	public void notify(BoardState counterMove) {
		//TODO
		mBoardState = counterMove;
		
	}

}
