/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.engine.persistency;

import java.util.List;

import com.figuro.common.BoardState;

/**
 * @author Aszalos Gyorgy
 */
public interface IPersistency {	
	public String getGame();
	
	public void setGame(String game);

	public List<String> getPlayers();
	
	public void setPlayers(List<String> players);

	public BoardState getBoardState();
	
	public int getCurrentPlayerId();
	
	public boolean isGameSaved();
	
	public void save(BoardState state, int currentPlayerId);

	public boolean load();
}
