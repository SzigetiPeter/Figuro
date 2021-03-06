package com.figuro.common;

import java.util.HashMap;
import java.util.Map;

import javafx.stage.Stage;

import com.figuro.engine.GameJob;
import com.figuro.engine.GameRunner;
import com.figuro.engine.IEngineHandler;
import com.figuro.engine.persistency.IPersistency;
import com.figuro.engine.persistency.Persistency;
import com.figuro.game.Checkers;
import com.figuro.game.Game;
import com.figuro.game.boardGraphics.BoardGraphics;
import com.figuro.game.rules.CheckersRules;
import com.figuro.game.stepEvaluator.CheckersEvaluator;
import com.figuro.player.IPlayer;
import com.figuro.player.botplayer.AlphaBetaSearch;
import com.figuro.player.botplayer.BotPlayer;
import com.figuro.player.botplayer.IStepSearch;
import com.figuro.player.netplayer.NetPlayer;
import com.figuro.player.uiplayer.UIPlayer;

public class Builder implements IBuilder {
	private Stage stage;
	private IMessageSender messages;
	private Map<String, Object> stuff;

	public Builder(Stage stage, IMessageSender messages) {
		super();
		this.stage = stage;
		this.messages = messages;

		stuff = new HashMap<String, Object>();
	}

	public IEngineHandler createEngine() {
		IPersistency persistency = new Persistency(messages);
		GameJob gameJob = new GameJob(persistency, messages);
		return new GameRunner(gameJob, this, messages, persistency);
	}

	@Override
	public String[] getGameTypes() {
		String[] gameTypes = new String[1];
		gameTypes[0] = IBuilder.CHECKERS_GAME;
		return gameTypes;
	}

	@Override
	public Game createGame(String type) {
		Game game;
		switch (type) {
		case IBuilder.CHECKERS_GAME:
			game = new Checkers(new CheckersRules(), new CheckersEvaluator(),
					new BoardGraphics(100, 100));
			break;
		default:
			return null;
		}

		stuff.put(type, game);
		return game;
	}

	@Override
	public IPlayer createPlayer(String type) {
		IPlayer player;

		switch (type) {
		case IBuilder.UI_PLAYER:
			if (! stuff.containsKey(type)) {
				stuff.put(type, new UIPlayer(stage, messages));
			}
			player = (IPlayer)stuff.get(type);
			break;
		case IBuilder.NET_PLAYER:
			player = new NetPlayer(messages);
			break;
		case IBuilder.ALPHABETA_PLAYER:
			int maxLevel = 3;
			IStepSearch stepSearch;
			try {
				stepSearch = new AlphaBetaSearch(new CheckersEvaluator(),
						new CheckersRules(), maxLevel);
				return new BotPlayer(stepSearch);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		default:
			return null;
		}

		stuff.put(type, player);
		return player;
	}

	public Object get(String type) {
		if (stuff.containsKey(type)) {
			return stuff.get(type);
		}
		return null;
	}

	@Override
	public void free() {
		stuff.clear();
	}

	public IPersistency getPersistency() {
		String persistencyName = IPersistency.class.getName();
		if (! stuff.containsKey(persistencyName)) {
			stuff.put(persistencyName, new Persistency(messages));
		}
		return (IPersistency)stuff.get(persistencyName);
	}
}
