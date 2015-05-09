/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.game.rules;

import com.figuro.common.BoardState;

/**
 *
 * @author Isti
 */
public interface IBoardInitializer {
	public BoardState initializeBoardStateElements(BoardState state,
			int blackPlayerId, int whitePlayerId);
}
