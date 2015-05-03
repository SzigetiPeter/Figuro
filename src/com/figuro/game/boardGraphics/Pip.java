package com.figuro.game.boardGraphics;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

import com.figuro.common.IUnit;
import com.figuro.common.PlayerConverter;
import com.figuro.common.PlayerEnum;
import com.figuro.common.UnitEnum;

/**
 *
 * @author SzPeter
 */
public class Pip implements IPip {

    private IUnit unit;
    private int pipSize = 50;
    private Group pip;

    public Pip() {
    }

    /**
     * 
     * @param id
     */
    public Pip(IBoard board, IUnit unit) {
        this.unit = unit;
        this.pip = createPip(board, unit);
    }

    @Override
    public Group getPip() {
        return pip;
    }

    @Override
    public void setPip(Group pip) {
        this.pip = pip;
    }

    @Override
    public IUnit getUnit() {
        return this.unit;
    }

    /**
     * 
     * @param id
     */
    @Override
    public void setUnit(IUnit unit) {
        this.unit = unit;
    }

    private Circle createBoundCircle(IBoard board, int size, double center, double radius, Color color) {
        NumberBinding rectsAreaSize = Bindings.min(board.getBoardPane().heightProperty(), board.getBoardPane()
                .widthProperty());

        Circle circle = new Circle(size, color);
        circle.centerXProperty().bind(rectsAreaSize.divide(board.getBoardSizeX() * center));
        circle.centerYProperty().bind(rectsAreaSize.divide(board.getBoardSizeY() * center));
        circle.radiusProperty().bind(rectsAreaSize.divide(board.getBoardSizeX() * radius));
        circle.setStrokeType(StrokeType.OUTSIDE);
        circle.setStroke(Color.web("white", 0.2f));
        circle.setStrokeWidth(4f);
        return circle;
    }

    private Group createPip(IBoard board, IUnit unit) {
        Group figure = new Group();

        if (unit.getType() == UnitEnum.PEASANT
                && unit.getOwnerId() == PlayerConverter.PlayerEnumToPlayerId(PlayerEnum.WHITE)) {
            figure.getChildren().add(createBoundCircle(board, pipSize, 2, 2.5, Color.WHITE));
        }
        if (unit.getType() == UnitEnum.PEASANT
                && unit.getOwnerId() == PlayerConverter.PlayerEnumToPlayerId(PlayerEnum.BLACK)) {
            figure.getChildren().add(createBoundCircle(board, pipSize, 2, 2.5, Color.BLACK));
        }
        if (unit.getType() == UnitEnum.KING
                && unit.getOwnerId() == PlayerConverter.PlayerEnumToPlayerId(PlayerEnum.WHITE)) {
            figure.getChildren().add(createBoundCircle(board, pipSize, 2, 2.5, Color.BLACK));
            figure.getChildren().add(createBoundCircle(board, pipSize / 3 * 2, 4, 3, Color.WHITE));
        }
        if (unit.getType() == UnitEnum.KING
                && unit.getOwnerId() == PlayerConverter.PlayerEnumToPlayerId(PlayerEnum.BLACK)) {
            figure.getChildren().add(createBoundCircle(board, pipSize, 2, 2.5, Color.WHITE));
            figure.getChildren().add(createBoundCircle(board, pipSize / 3 * 2, 4, 3, Color.BLACK));
        }
        figure.setId("PIP_" + unit.getType() + "_" + unit.getOwnerId());
        figure.setUserData(unit);
        figure.autosize();
        return figure;

    }

}
