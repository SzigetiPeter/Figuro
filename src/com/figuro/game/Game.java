package com.figuro.game;

import com.figuro.game.boardGraphics.IBoardGraphics;
import com.figuro.game.rules.IGameRules;
import com.figuro.game.stepEvaluator.IStepEvaluator;

/**
 * @author Mathe E. Botond
 *
 *         A packaging class enclosing game specific components
 */
public abstract class Game {
	private IGameRules rules;
	private IStepEvaluator evaluator;
	private IBoardGraphics graphics;

	public Game(IGameRules rules, IStepEvaluator evaluator,
			IBoardGraphics graphics) {

		super();
		this.rules = rules;
		this.evaluator = evaluator;
		this.graphics = graphics;
	}

	public IGameRules getRules() {
		return rules;
	}

	public IStepEvaluator getEvaluator() {
		return evaluator;
	}

	public IBoardGraphics getGraphics() {
		return graphics;
	}

	public abstract String getGameName();
}
