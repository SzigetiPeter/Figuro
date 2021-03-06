package com.figuro.engine;

import java.util.ArrayList;
import java.util.List;

import com.figuro.common.BoardState;
import com.figuro.common.IBuilder;
import com.figuro.common.IMessageSender;
import com.figuro.engine.persistency.IPersistency;
import com.figuro.game.Game;
import com.figuro.player.IPlayer;

/**
 * @author Dalyay Kinga
 */

public class GameRunner implements IEngineHandler {

	IBuilder builder;
	IMessageSender message;
	IPersistency persistency;
	List<String> playerTypes;

	Thread thread;
	GameJob job;

	public GameRunner(GameJob job, IBuilder builder,
			IMessageSender message, IPersistency persistency) {
		
		this.job = job;
		this.builder = builder;
		this.message = message;
		this.persistency = persistency;
		this.playerTypes = new ArrayList<String>();

		thread = new Thread(job);
	}

	@Override
	public IPlayer addPlayer(String playerType) {
		playerTypes.add(playerType);

		IPlayer player = builder.createPlayer(playerType);

		try {
			job.addPlayer(player);
			return player;
		} catch (Exception e) {
			message.displayMessage(e.getMessage());
		}

		return null;
	}

	@Override
	public IPlayer addSpectator(String playerType) {
		IPlayer spectator = builder.createPlayer(playerType);
		job.addSpectator(spectator);
		return spectator;
	}

	@Override
	public void removePlayers() {
		job.removePlayers();
	}

	private void startGame(
			Game game,
			IGameoverCallback callback, 
			BoardState state,
			int currentPlayerId) {
		
		job.setGame(game, callback);
		job.setState(state);
		job.setCurrentPlayer(currentPlayerId);
		persistency.setGame(game.getGameName());
		persistency.setPlayers(playerTypes);
		thread.start();
	}
	
	@Override
	public void runGame(String gameType, IGameoverCallback callback) {
		Game game = builder.createGame(gameType);
		startGame(game, callback, game.getRules().getInitialState(), 1);
	}

	@Override
	public boolean isGameResumable() {
		return persistency.isGameSaved();
	}

	@Override
	public void resumeGame(IGameoverCallback callback) {
		if (isGameResumable()) {
			if (persistency.load()) {
				List<String> playersType = persistency.getPlayers();

				for (String playerType : playersType) {
					try {
						job.addPlayer(builder.createPlayer(playerType));
					} catch (Exception e) {
						//no way this could happen
					}
				}

				String gameType = persistency.getGame();
				Game game = builder.createGame(gameType);
				BoardState boardState = persistency.getBoardState();
				int currentPlayerId = persistency.getCurrentPlayerId();
				
				startGame(game, callback, boardState, currentPlayerId);
			}
		} else {
			message.displayMessage("Cannot resume game, it was not saved!");
		}
	}

	@Override
	public void exit() {
		job.terminate();
	}
}
