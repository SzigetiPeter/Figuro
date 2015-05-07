package com.figuro.engine;

import java.util.ArrayList;
import java.util.List;

import com.figuro.common.BoardState;
import com.figuro.game.Game;
import com.figuro.game.rules.IGameRules;
import com.figuro.player.IPlayer;

public class GameJob implements Runnable {
	private RunningStateHolder runningState;
	private List<IPlayer> players;
	private List<IPlayer> spectators;
	private Game game;
	
	private int playerCount;
	private IGameoverCallback gameoverCallback;
	
	public GameJob() {
		players = new ArrayList<IPlayer>();
		spectators = new ArrayList<IPlayer>();
		playerCount = 0;
		runningState = new RunningStateHolder();
	}

	public void addPlayer(IPlayer player) throws Exception {
		if(playerCount < 2)
		{
			playerCount += 1;
			players.add(player);			
		}
		else
		{
			throw new Exception("Player count is already 2!");
		}
	}
	
	public void addSpectator(IPlayer player) {
		spectators.add(player);
	}
	
	public void removePlayers()
    {
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
	
	@Override
	public void run() {
		if(playerCount != 2)
		{			
			this.terminate();	
		}
			
		IGameRules rules = game.getRules();
		BoardState state = rules.getInitialState();
		
		setInitialState(players, state);
		setInitialState(spectators, state);
		
		int currentPlayer = 1, otherPlayer = 2;
		MoveComplete callback;
		while (this.runningState.isRunning() && !rules.isGameOver(state, currentPlayer)  ) {
			callback = new MoveComplete(this.runningState);
			
			IPlayer player1 =  players.get(currentPlayer);
			player1.move(callback);
			callback.listen();
			
			BoardState result = callback.getResult();
			
			if (result == null) {
				continue;
			}
			
			if (!rules.isValidMove(state, result, currentPlayer)) {
				player1.wrongMoveResetTo(state);
				continue;
			}
			
			IPlayer player2 = players.get(otherPlayer);
			player2.notify(result);
			
			for (IPlayer player: spectators) {
				player.notify(result);
			}
			
			currentPlayer = rules.getNextPlayer(state, result, currentPlayer);			
			state = result;
		}
		
		if (rules.isGameOver(state, currentPlayer))
		{
			String score = Integer.toString(rules.getFinalState(state, currentPlayer));
			this.gameoverCallback.gameFinishedWith(score); 
		}
	}

	public void terminate() {
		this.runningState.terminate();
	}
}
