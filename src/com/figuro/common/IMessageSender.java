/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.common;

import javafx.scene.control.Label;

/**
 * @author Aszalos Gyorgy
 */
public interface IMessageSender {
    public void displayMessage(String message);

    public void updateGameState(String gameStatus);

    public Label getMessage();

    public void setMessage(Label message);

    public Label getScore();

    public void setScore(Label score);

}
