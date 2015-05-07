package com.figuro.engine;

import com.figuro.common.BoardState;
import com.figuro.common.IBuilder;
import com.figuro.common.IMessageSender;
import com.figuro.engine.persistency.IPersistency;
import com.figuro.game.Game;
import com.figuro.game.rules.IGameRules;
import com.figuro.player.IPlayer;

public class GameRunner implements IEngineHandler {

	IBuilder builder;
	IGameRules rules;
	BoardState boardState;
	IMessageSender message;
	IPersistency persistancy;
	
	Thread thread;
	GameJob job;
	
	public GameRunner(GameJob job, IBuilder builder, IGameRules rules, IMessageSender message, IPersistency persistancy) {		
		this.job = job;
		this.builder = builder;
		this.rules = rules;
		this.message = message;
		this.persistancy = persistancy;
		
		thread = new Thread(job);
	}
	
	@Override
	public void start()
	{
		//Külön thread indítása a játéknak, amely várakozhat a háttérjátékosokra és a játék logikára
		thread.start();
	}
	
	@Override
    public void addPlayer(String playerType) 
    {
    	//Hozzáad egy játékost a játékhoz
    	IPlayer player = builder.createPlayer(playerType);
    	
    	try {
			job.addPlayer(player);
		} catch (Exception e) {	
			message.displayMessage(e.getMessage());
		}   	
    }
    
	@Override
    public void addSpectator(String playerType) 
    {
    	//Hozzáad egy figyelőt a játékhoz. hasznos abban az esetben ha pl. két Bot játékos játszik és egy UIPlayer a megfigyelő    	
    	IPlayer spectator = builder.createPlayer(playerType);
    	job.addSpectator(spectator);
    }
    
	@Override
    public void removePlayers()
    {
    	//Eltávolítja a játékhoz hozzáadott játékosokat    	
    	job.removePlayers();
    }
    
	@Override
    public void runGame(String gameType, IGameoverCallback callback)
    {
    	//Elindítja és ütemezi a meghatározott játékot
    	Game game = builder.createGame(gameType);
    	job.setGame(game, callback);
    	thread.start();
    }
	
	@Override
	public boolean isGameResumable()
	{		
		return persistancy.isGameSaved();
	}
	
	@Override
    public void resumeGame(IGameoverCallback callback)
    { 
		if(isGameResumable())
		{
			persistancy.load();
		}
    }    

    @Override
    public void exit()
    {
    	job.terminate();
    }
}
