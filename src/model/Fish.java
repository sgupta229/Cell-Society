package model;

import java.awt.*;

public class Fish extends Cell{

    private int FISH_SPAWN_TIMER;
    private int spawnTimer;

    /** standard constructor
     *
     * @param gridPos x y coord of cell
     * @param state state of cell
     * @param fishSpawn time to reproduce
     */
    public Fish(Point gridPos, State state, int fishSpawn){
        super(gridPos, state);
        FISH_SPAWN_TIMER = fishSpawn;
        this.spawnTimer = fishSpawn;
    }

    /**
     * constructor takes a generic cell and creates a new Fish
     */
    public Fish(Cell cell, int fishSpawn){
        this(cell.getPosition(), cell.getCurrState(), fishSpawn);
        addAllNeighbors(cell.getNeighbors());
    }

    /**
     * constructor takes a generic cell and creates a new Fish with the attributes of an old one
     */
    public Fish(Point newPos, State sharkState, int sharkSpawn, int currentTimer) {
        super(newPos, sharkState);
        FISH_SPAWN_TIMER = sharkSpawn;
        this.spawnTimer = currentTimer;
    }


    public int getSpawnTimer() {
        return spawnTimer;
    }

    public void resetSpawnTimer() {
        this.spawnTimer = FISH_SPAWN_TIMER;
    }

    public void setSpawnTimer(int t) {
        this.spawnTimer = t;
    }

    public void setFishSpawnTimer(int t) {
        this.FISH_SPAWN_TIMER = t;
    }

    public void decrementSpawnTimer() { this.spawnTimer--; }
}
