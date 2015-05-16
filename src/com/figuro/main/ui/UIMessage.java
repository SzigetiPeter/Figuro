package com.figuro.main.ui;

import javafx.application.Platform;
import javafx.scene.control.Label;

import com.figuro.common.IMessageSender;

public class UIMessage implements IMessageSender {
    private Label messageLabel;
    private Label scoreLabel;

    public UIMessage(Label message, Label score) {
        super();
        this.messageLabel = message;
        this.scoreLabel = score;
    }

    @Override
    public void displayMessage(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                messageLabel.setText(message);
            }
        });

    }

    @Override
    public void updateGameState(String gameStatus) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                scoreLabel.setText(gameStatus);
            }
        });
    }

    @Override
    public Label getMessage() {
        return messageLabel;
    }

    @Override
    public void setMessage(Label message) {
        this.messageLabel = message;
    }

    @Override
    public Label getScore() {
        return scoreLabel;
    }

    @Override
    public void setScore(Label score) {
        this.scoreLabel = score;
    }

}
