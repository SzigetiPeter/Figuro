package com.figuro.main.ui;

public class PlayerSetupTemplate {
	private String player1;
	private String player2;
	private String spectator = null;

	public PlayerSetupTemplate(String player1, String player2, String spectator) {
		super();
		this.player1 = player1;
		this.player2 = player2;
		this.spectator = spectator;
	}

	public PlayerSetupTemplate(String player1, String player2) {
		super();
		this.player1 = player1;
		this.player2 = player2;
	}

	public String getPlayer1() {
		return player1;
	}

	public String getPlayer2() {
		return player2;
	}

	public String getSpectator() {
		return spectator;
	}

}
