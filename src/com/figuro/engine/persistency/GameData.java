package com.figuro.engine.persistency;

import java.io.Serializable;
import java.util.List;

import com.figuro.common.BoardState;

/**
 * @author Dalyay Kinga
 */

public class GameData implements Serializable {

	private static final long serialVersionUID = 1L;
	public String game;
	public List<String> players;
	public BoardState boardState;
	
	public GameData(String game, List<String> players, BoardState boardState)
	{
		this.game = game;
		this.players = players;
		this.boardState = boardState;
	}	
}