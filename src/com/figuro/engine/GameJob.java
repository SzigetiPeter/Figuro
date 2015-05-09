package com.figuro.engine;

import java.util.ArrayList;
import java.util.List;

import com.figuro.common.BoardState;
import com.figuro.engine.persistency.IPersistency;
import com.figuro.game.Game;
import com.figuro.game.rules.IGameRules;
import com.figuro.player.IPlayer;

/**
 * @author Dalyay Kinga
 */

public class GameJob implements Runnable {
	private RunningStateHolder runningState;
	private List<IPlayer> players;
	private List<IPlayer> spectators;
	private Game game;

	private int playerCount;
	private IGameoverCallback gameoverCallback;
	private IPersistency persistency;
	private List<String> playerTypes;
	private String gameType;

	public GameJob(IPersistency persistency) {
		this.persistency = persistency;

		players = new ArrayList<IPlayer>();
		spectators = new ArrayList<IPlayer>();
		playerCount = 0;
		runningState = new RunningStateHolder();
		playerTypes = new ArrayList<String>();
	}

	public void addPlayer(IPlayer player) throws Exception {
		if (playerCount < 2) {
			playerCount += 1;
			players.add(player);
		} else {
			throw new Exception("Player count is already 2!");
		}
	}

	public void addSpectator(IPlayer player) {
		spectators.add(player);
	}

	public void removePlayers() {
		players.clear();
		spectators.clear();
		playerCount = 0;
	}

	public void setGame(Game game, IGameoverCallback gameoverCallback,
			IPersistency persistency, String gameType, List<String> playerTypes) {
		this.game = game;
		this.gameoverCallback = gameoverCallback;
		this.gameType = gameType;
		this.playerTypes = playerTypes;
	}

	private void setInitialState(List<IPlayer> players, BoardState state) {
		for (IPlayer player : players) {
			player.setInitialState(state);
		}
	}

	@Override
	public void run() {
		if (playerCount != 2) {
			this.terminate();
		}

		IGameRules rules = game.getRules();
		BoardState state = rules.getInitialState();

		setInitialState(players, state);
		setInitialState(spectators, state);

		int currentPlayer = 1, otherPlayer = 2;
		MoveComplete moveCompleteCallback;

		while (this.runningState.isRunning()
				&& !rules.isGameOver(state, currentPlayer)) {
			moveCompleteCallback = new MoveComplete(this.runningState);

			IPlayer player1 = players.get(currentPlayer);
			player1.move(moveCompleteCallback);
			moveCompleteCallback.listen();

			BoardState result = moveCompleteCallback.getResult();

			if (result == null) {
				continue;
			}

			if (!rules.isValidMove(state, result, currentPlayer)) {
				player1.wrongMoveResetTo(state);
				continue;
			}

			IPlayer player2 = players.get(otherPlayer);
			player2.notify(result);

			for (IPlayer player : spectators) {
				player.notify(result);
			}

			currentPlayer = rules.getNextPlayer(state, result, currentPlayer);
			state = result;
		}

		if (rules.isGameOver(state, currentPlayer)) {
			String score = Integer.toString(rules.getFinalState(state,
					currentPlayer));
			this.gameoverCallback.gameFinishedWith(score);
		} else {
			if (!this.runningState.isRunning()) {
				persistency.save(this.gameType, this.playerTypes, state);
			}
		}
	}

	public void resumeGame(Game game, List<IPlayer> players,
			BoardState boardState, IGameoverCallback gameoverCallback,
			IPersistency persistency, String gameType, List<String> playerTypes) {
		if (players.size() != 2) {
			this.terminate();
		}

		IGameRules rules = game.getRules();
		setInitialState(players, boardState);

		int currentPlayer = 1, otherPlayer = 2;
		MoveComplete moveCompleteCallback;
		while (this.runningState.isRunning()
				&& !rules.isGameOver(boardState, currentPlayer)) {
			moveCompleteCallback = new MoveComplete(this.runningState);

			IPlayer player1 = players.get(currentPlayer);
			player1.move(moveCompleteCallback);
			moveCompleteCallback.listen();

			BoardState result = moveCompleteCallback.getResult();

			if (result == null) {
				continue;
			}

			if (!rules.isValidMove(boardState, result, currentPlayer)) {
				player1.wrongMoveResetTo(boardState);
				continue;
			}

			IPlayer player2 = players.get(otherPlayer);
			player2.notify(result);

			currentPlayer = rules.getNextPlayer(boardState, result,
					currentPlayer);
			boardState = result;
		}

		if (rules.isGameOver(boardState, currentPlayer)) {
			String score = Integer.toString(rules.getFinalState(boardState,
					currentPlayer));
			this.gameoverCallback.gameFinishedWith(score);
		} else {
			if (!this.runningState.isRunning()) {
				persistency.save(gameType, playerTypes, boardState);
			}
		}
	}

	public void terminate() {
		this.runningState.terminate();
	}
}
