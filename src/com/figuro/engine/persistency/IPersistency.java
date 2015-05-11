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
	public void setPlayers(List<String> players);
	
	public void setGame(String game);
	
	public void save(BoardState state, int currentPlayerId);

	public boolean load();

	public boolean isGameSaved();

	public String getGame();

	public List<String> getPlayers();

	public BoardState getBoardState();
}
