package com.figuro.common;

public class Evaluation {
    private int score;
    private int player;
    
    public Evaluation()
    { }
    
    public Evaluation(int player, int score)
    {
        this.player = player;
        this.score = score;
    }
    
    public int getScore()
    {
        return score;
    }
    
    public void setScore(int score)
    {
        this.score = score;
    }
    
    public int getPlayer()
    {
        return player;
    }
    
    public void setPlayer(int player)
    {
        this.player = player;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.score;
        hash = 53 * hash + this.player;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Evaluation other = (Evaluation) obj;
        if (this.score != other.score) {
            return false;
        }
        if (this.player != other.player) {
            return false;
        }
        return true;
    }
}
