package com.figuro.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.figuro.common.BoardState;
import com.figuro.engine.persistency.IPersistency;
import com.figuro.game.Game;
import com.figuro.game.rules.IGameRules;
import com.figuro.player.IPlayer;

/**
 * @author Dalyay Kinga
 */

public class GameJob implements Runnable {
	private RunningState runningState;
	private Map<Integer, IPlayer> players;
	private List<IPlayer> spectators;
	private Game game;

	private int playerCount;
	private IGameoverCallback gameoverCallback;
	private IPersistency persistency;
	private BoardState state;
	
	private int currentPlayerId;
	private int otherPlayerId;

	public GameJob(IPersistency persistency) {
		this.persistency = persistency;

		players = new HashMap<Integer, IPlayer>();
		spectators = new ArrayList<IPlayer>();
		playerCount = 0;
		runningState = new RunningState();
	}
	
	public void addPlayer(IPlayer player) throws Exception {
		if (playerCount < 2) {
			playerCount += 1;
			players.put(playerCount, player);
		} else {
			throw new Exception("Player count is already 2!");
		}
	}

	public void addSpectator(IPlayer player) {
		spectators.add(player);
	}
	
	public void setCurrentPlayer(int currentPlayerId) {
		this.currentPlayerId = currentPlayerId;
		otherPlayerId = (currentPlayerId == 1) ? 2 : 1;
	}
	
	public int getCurrentPlayerId() {
		return currentPlayerId;
	}

	public void removePlayers() {
		players.clear();
		spectators.clear();
		playerCount = 0;
	}

	public void setGame(Game game, IGameoverCallback gameoverCallback) {
		this.game = game;
		this.gameoverCallback = gameoverCallback;
	}

	private void setInitialState(List<IPlayer> players, BoardState state) {
		for (IPlayer player : players) {
			player.setInitialState(state);
		}
	}
	
	private void setInitialState(Map<Integer, IPlayer> players, BoardState state) {
		for (Entry<Integer, IPlayer> entry : players.entrySet()) {
			entry.getValue().setInitialState(state);
		}
	}

	private void arrangePlayers() {
		System.out.println("Preffered order: 1:" + players.get(1).getPrefferedOrder() + " 2:" + players.get(2).getPrefferedOrder());
		if (players.get(1).getPrefferedOrder() == 2 || players.get(2).getPrefferedOrder() == 1) {
			IPlayer tmpPlayer = players.get(1);
			players.put(1, players.get(2));
			players.put(2, tmpPlayer);
		}
		
		players.get(1).setId(1);
		players.get(2).setId(2);
	}
	
	public void setState(BoardState state) {
		this.state = state;
	}

	@Override
	public void run() {
		if (playerCount != 2) {
			this.terminate();
		}

		IGameRules rules = game.getRules();
		
		arrangePlayers();
		setInitialState(players, state);
		setInitialState(spectators, state);
		MoveComplete moveCompleteCallback;

		while (this.runningState.isRunning()
				&& !rules.isGameOver(state, currentPlayerId)) {
			moveCompleteCallback = new MoveComplete(this.runningState);

			setCurrentPlayer(currentPlayerId);
			IPlayer currentPlayer = players.get(currentPlayerId);
			currentPlayer.move(moveCompleteCallback);
			moveCompleteCallback.listen();

			BoardState result = moveCompleteCallback.getResult();

			if (! this.runningState.isRunning()) {
				break;
			}
			
			//System.out.println(result.toString());

			if (!rules.isValidMove(state, result, currentPlayerId)) {
				currentPlayer.wrongMoveResetTo(state);
				continue;
			}

			IPlayer otherPlayer = players.get(otherPlayerId);
			otherPlayer.notify(result);

			for (IPlayer player : spectators) {
				player.notify(result);
			}
			
			result = rules.applyMoveEffect(state, result, currentPlayerId);
			
			currentPlayer.update(result);
			otherPlayer.update(result);
			for (IPlayer player : spectators) {
				player.update(result);
			}

			currentPlayerId = rules.getNextPlayer(state, result, currentPlayerId);
			state = result;
		}

		if (rules.isGameOver(state, currentPlayerId)) {
			String score = Integer.toString(rules.getFinalState(state,
					currentPlayerId));
			this.gameoverCallback.gameFinishedWith(score);
		} else {
			if (!this.runningState.isRunning()) {
				persistency.save(state, currentPlayerId);
			}
		}
	}

	public void terminate() {
		this.runningState.terminate();
	}
}
