package com.figuro.main.ui;

import com.figuro.common.IBuilder;

public class PlayerSetupFactory {
	final static String NET_GAME = "net-game";
	final static String PLAYER_VS_BOT = "player-vs-bot";
	final static String BOT_VS_BOT = "bot-vs-bot";

	public PlayerSetupTemplate createSetup(String setupName) {
		switch (setupName) {
		case PlayerSetupFactory.NET_GAME:
			return new PlayerSetupTemplate(IBuilder.UI_PLAYER,
					IBuilder.NET_PLAYER);
		case PlayerSetupFactory.PLAYER_VS_BOT:
			return new PlayerSetupTemplate(IBuilder.UI_PLAYER,
					IBuilder.ALPHABETA_PLAYER);
		case PlayerSetupFactory.BOT_VS_BOT:
			return new PlayerSetupTemplate(IBuilder.ALPHABETA_PLAYER,
					IBuilder.ALPHABETA_PLAYER, IBuilder.UI_PLAYER);
		default:
			return null;
		}
	}
}
