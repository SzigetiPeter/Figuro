package com.figuro.game.boardGraphics;

import java.awt.Point;
import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import com.figuro.common.BoardState;
import com.figuro.common.IUnit;
import com.figuro.engine.IMoveComplete;
import com.figuro.game.rules.Cell;

/**
 *
 * @author SzPeter
 */
public class Animation implements IAnimation {

    private ArrayList<IPip> pips;
    private IBoard board;
    private Point2D dragAnchor;
    private double initX;
    private double initY;

    public Animation() {
    }

    /**
     * 
     * @param pips
     */
    public Animation(IBoard board, ArrayList<IPip> pips) {
        this.board = board;
        this.pips = pips;
    }

    @Override
    public void enableMove(BoardState currentState, IMoveComplete movedCallback) {
        this.board.getBoardPane().getChildren().forEach(new Consumer<Node>() {

            @Override
            public void accept(Node t) {
                if (t.getUserData() != null
                        && t.getUserData().getClass().isAssignableFrom(pips.get(0).getUnit().getClass())) {
                    t.setOnMousePressed(new InitAnimation());
                    t.setOnMouseDragged(new TranzitAnimation());
                    t.setOnMouseReleased(new FinishAnimation(currentState, movedCallback));
                }

            }
        });

    }

    @Override
    public void disableMove() {
        this.board.getBoardPane().getChildren().forEach(new Consumer<Node>() {

            @Override
            public void accept(Node t) {
                if (t.getUserData() != null
                        && t.getUserData().getClass().isAssignableFrom(pips.get(0).getUnit().getClass())) {
                    t.setOnMousePressed(null);
                    t.setOnMouseDragged(null);
                    t.setOnMouseReleased(null);
                }

            }
        });

    }

    class InitAnimation implements EventHandler<MouseEvent> {

        public InitAnimation() {
        }

        @Override
        public void handle(MouseEvent event) {
            Group selected = ((Group) event.getSource());
            initX = selected.getTranslateX();
            initY = selected.getTranslateY();
            dragAnchor = new Point2D(event.getSceneX(), event.getSceneY());
        }

    }

    class TranzitAnimation implements EventHandler<MouseEvent> {

        public TranzitAnimation() {
        }

        @Override
        public void handle(MouseEvent event) {
            Group selected = ((Group) event.getSource());
            if (!selected.getId().isEmpty()) {

                double dragX = event.getSceneX() - dragAnchor.getX();
                double dragY = event.getSceneY() - dragAnchor.getY();

                double newXPosition = initX + dragX;
                double newYPosition = initY + dragY;
                if ((newXPosition >= 0)

                && (newXPosition <= 1000 - ((Circle) selected.getChildren().get(0)).getRadius() * 2)) {
                    selected.setTranslateX(newXPosition);
                }

                if ((newYPosition >= 0)
                        && (newYPosition <= 1000 - ((Circle) selected.getChildren().get(0)).getRadius() * 2)) {
                    selected.setTranslateY(newYPosition);

                }
            }

        }

    }

    class FinishAnimation implements EventHandler<MouseEvent> {

        private BoardState currentState;
        private IMoveComplete movedCallback;

        public FinishAnimation(BoardState current, IMoveComplete movedCallback) {
            this.currentState = current;
            this.movedCallback = movedCallback;
        }

        @Override
        public void handle(MouseEvent event) {
            Group selected = ((Group) event.getSource());
            Point startingPoint = getCheckerCoordinate(board, dragAnchor.getX(), board.getBoardPane().getHeight()
                    - dragAnchor.getY());
            Point finalPoint = getCheckerCoordinate(board, event.getSceneX(),
                    board.getBoardPane().getHeight() - event.getSceneY());
            NumberBinding rectsAreaSize = Bindings.min(board.getBoardPane().heightProperty(), board.getBoardPane()
                    .widthProperty());
            selected.setTranslateX(finalPoint.x * rectsAreaSize.divide(board.getBoardSizeX()).floatValue());
            selected.setTranslateY((finalPoint.y) * rectsAreaSize.divide(board.getBoardSizeY()).floatValue());

            if (finalPoint.equals(startingPoint)) {
                return;
            }

            BoardState newBoardState = new BoardState(this.currentState);
            newBoardState.set(finalPoint, new Cell((IUnit) selected.getUserData()));
            newBoardState.set(startingPoint, new Cell());
            newBoardState.setLatestMoved(finalPoint);
            newBoardState.setLatestMovedFrom(startingPoint);
            // TODO: check if move is valid com.foguro.game.rules
            disableMove();
            movedCallback.setResult(newBoardState);
        }

    }

    private Point getCheckerCoordinate(IBoard board, double x, double y) {
        Point boardPoint = new Point();
        NumberBinding rectsAreaSize = Bindings.min(board.getBoardPane().heightProperty(), board.getBoardPane()
                .widthProperty());

        for (int i = 1; i <= board.getBoardSizeX(); i++) {
            if (rectsAreaSize.divide(board.getBoardSizeX()).multiply(i).floatValue() < x) {
                boardPoint.x = i;
            }
            if (rectsAreaSize.divide(board.getBoardSizeY()).multiply(i).floatValue() < y) {
                boardPoint.y = 7 - i;
            }
        }
        return boardPoint;
    }

}
