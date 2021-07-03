package com.example.colormatch;

public class highScore implements Comparable{
    private String username;
    private String score;

    public highScore(String username, String score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }


    @Override
    public int compareTo(Object p1) {
        int score1=Integer.parseInt(score);
        int score2=Integer.parseInt(((highScore)p1).getScore());

        return score2-score1;
    }
}

