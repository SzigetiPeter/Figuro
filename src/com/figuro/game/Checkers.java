package com.figuro.game;

import com.figuro.game.boardGraphics.IBoardGraphics;
import com.figuro.game.rules.IGameRules;
import com.figuro.game.stepEvaluator.IStepEvaluator;

public class Checkers extends Game {
	final static String NAME = "game.checkers";

	public Checkers(IGameRules rules, IStepEvaluator evaluator,
			IBoardGraphics graphics) {
		super(rules, evaluator, graphics);
	}

	@Override
	public String getGameName() {
		return Checkers.NAME;
	}

}
