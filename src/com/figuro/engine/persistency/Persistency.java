package com.figuro.engine.persistency;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import com.figuro.common.BoardState;
import com.figuro.common.IMessageSender;

/**
 * @author Dalyay Kinga
 */

public class Persistency implements IPersistency, Serializable {

	private static final long serialVersionUID = 1L;
	String game;
	List<String> players;
	BoardState boardState;

	String fileName = "gameData.ser";

	IMessageSender messageSender;

	public Persistency(IMessageSender messageSender) {
		this.messageSender = messageSender;
	}

	@Override
	public void setPlayers(List<String> players) {
		this.players = players;
	}

	@Override
	public void setGame(String game) {
		this.game = game;
	}
	
	@Override
	public void save(BoardState state, int currentPlayerId) {
		GameData gameData = new GameData(game, players, state);
		try {
			FileOutputStream fileOut = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(gameData);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			messageSender.displayMessage("Game couldn't be SAVED!");
		}
	}

	@Override
	public boolean load() {
		GameData gameData = null;
		try {
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			gameData = (GameData) in.readObject();
			in.close();
			fileIn.close();

			this.game = gameData.game;
			this.players = gameData.players;
			this.boardState = gameData.boardState;

			try {
				File file = new File(fileName);
				if (!file.delete()) {
					messageSender.displayMessage("Delete operation is failed!");
					return false;
				}
			} catch (Exception e) {
				messageSender.displayMessage("File not found!");
				return false;
			}

			return true;
		} catch (IOException i) {
			messageSender.displayMessage("Game couldn't be LOADED!");
		} catch (ClassNotFoundException c) {
			messageSender.displayMessage("GameData class not found!");
		}

		return false;
	}

	@Override
	public boolean isGameSaved() {
		File file = new File(fileName);

		if (file.exists()) {
			return true;
		}

		return false;
	}

	@Override
	public String getGame() {
		return game;
	}

	@Override
	public List<String> getPlayers() {
		return players;
	}

	@Override
	public BoardState getBoardState() {
		return boardState;
	}
}
