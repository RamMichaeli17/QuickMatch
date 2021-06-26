package com.example.colormatch;

public class customerModel {

    private int id;
    private String name;
    private int score;
    private boolean isActive;

    public customerModel(int id, String name, int score, boolean isActive) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.isActive = isActive;
    }

    public customerModel()
    {

    }


    @Override
    public String toString() {
        return "#" + id +
                " " + name +
                " " + score +"     "
                +" Switch = "+isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
