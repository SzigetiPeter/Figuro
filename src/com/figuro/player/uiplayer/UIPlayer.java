package com.figuro.player.uiplayer;

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
    private int boardSizeX = 1000;
    private int boardSizeY = 1000;

    private Stage stage;

    public UIPlayer(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setInitialState(BoardState board) {
        internalState = board;
        this.boardGraphics = new BoardGraphics(board.getBoard().length, board.getBoard().length);

        for (int x = 0; x < board.getBoard().length; x++)
            for (int y = 0; y < board.getBoard()[x].length; y++) {
                if (board.getBoard()[x][y] != null && board.getBoard()[x][y].hasUnit()) {
                    IPip p = boardGraphics.getFigureGraphic(board.getBoard()[x][y].getUnit());
                    p.getPip().setTranslateX(x * boardSizeX / 8);
                    p.getPip().setTranslateY(y * boardSizeY / 8);
                    boardGraphics.getBoardGraphic().getBoardPane().getChildren().add(p.getPip());
                }
            }
        stage.setScene(new Scene(boardGraphics.getBoardGraphic().getBoardPane(), boardSizeX, boardSizeY));
    }

    @Override
    public void move(IMoveComplete callback) {
        //TODO: check next player and allow only that player to move
        boardGraphics.getAnimation().enableMove(callback);
    }

    @Override
    public void wrongMoveResetTo(BoardState board) {
        internalState = board;
        setInitialState(board);
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
    	//do nothing
    }

    @Override
    public int getPrefferedOrder() {
        return 0;
    }

    @Override
    public void notify(BoardState counterMove) {
        internalState = counterMove;
        
    }

}
