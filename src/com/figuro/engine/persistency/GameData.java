package com.figuro.engine.persistency;

import java.util.List;

import com.figuro.common.BoardState;

public class GameData {

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