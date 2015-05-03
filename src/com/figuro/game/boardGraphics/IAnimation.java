package com.figuro.game.boardGraphics;

import com.figuro.engine.IMoveComplete;

public interface IAnimation {
    public void enableMove(IMoveComplete movedCallback);
    public void disableMove();
}
