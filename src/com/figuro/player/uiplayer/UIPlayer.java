package com.figuro.player.uiplayer;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import com.figuro.common.BoardState;
import com.figuro.engine.IMoveComplete;
import com.figuro.game.boardGraphics.BoardGraphics;
import com.figuro.game.boardGraphics.IBoardGraphics;
import com.figuro.game.boardGraphics.IPip;
import com.figuro.player.IPlayer;

public class UIPlayer implements IPlayer {

    protected BoardState internalState;
    protected int id;

    private IBoardGraphics boardGraphics;
    private int boardSizeX = 500;
    private int boardSizeY = 500;

    private Stage stage;

    public UIPlayer(Stage stage) {
        this.stage = stage;

        this.boardGraphics = new BoardGraphics(8, 8);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stage.setScene(new Scene(boardGraphics.getBoardGraphic().getBoardPane(), boardSizeX, boardSizeY));
            }
        });
    }

    @Override
    public void setInitialState(BoardState board) {
        internalState = board;
        setBoardState(board);
    }

    @Override
    public void move(IMoveComplete callback) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // TODO: check next player and allow only that player to move
                boardGraphics.getAnimation().enableMove(internalState, callback);
            }
        });
    }

    @Override
    public void wrongMoveResetTo(BoardState board) {
        internalState = board;
        setBoardState(internalState);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean needsSetup() {
        return false;
    }

    @Override
    public void setup(Group parent, Button okButton) {
        // do nothing
    }

    @Override
    public int getPrefferedOrder() {
        return 0;
    }

    @Override
    public void notify(BoardState counterMove) {
        internalState = counterMove;
    }
    
	@Override
	public void update(BoardState state) {
		internalState = state;
        setBoardState(internalState);
	}

    private void setBoardState(BoardState state) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                clearPips();
                for (int x = 0; x < state.getBoard().length; x++)
                    for (int y = 0; y < state.getBoard()[x].length; y++) {
                        if (state.getBoard()[x][y].hasUnit()) {
                            if (state.getBoard()[x][y] != null) {
                                IPip p = boardGraphics.getFigureGraphic(state.getBoard()[x][y].getUnit());
                                p.getPip().setTranslateX(x * boardSizeX / 8);
                                p.getPip().setTranslateY((7 - y) * boardSizeY / 8);
                                boardGraphics.getBoardGraphic().getBoardPane().getChildren().add(p.getPip());
                            }
                        }
                    }
            }
        });
    }

    private void clearPips() {
        boardGraphics.getBoardGraphic().getBoardPane().getChildren().removeIf(item -> item.getId() != "BOARD");
    }
}
