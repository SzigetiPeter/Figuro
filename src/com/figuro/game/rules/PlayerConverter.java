/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.game.rules;

/**
 *
 * @author Isti
 */
public class PlayerConverter {
	public static PlayerEnum PlayerIdToPlayerEnum(int playerId) {
		PlayerEnum player = null;

		if (playerId == 1) {
			player = PlayerEnum.BLACK;
		} else if (playerId == 2) {
			player = PlayerEnum.WHITE;
		}

		return player;
	}

	public static int PlayerEnumToPlayerId(PlayerEnum player) {
		int playerId = 0;
		if (player == PlayerEnum.BLACK) {
			playerId = 1;
		} else if (player == PlayerEnum.WHITE) {
			playerId = 2;
		}

		return playerId;
	}
}
