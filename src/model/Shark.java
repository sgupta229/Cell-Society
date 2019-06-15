package model;

import java.awt.Point;

/**
 * Shark Cell for PredatorPrey
 */
public class Shark extends Fish{

    private int MAX_ENERGY;
    private int SHARK_SPAWN_TIMER;
    private int energy;
    private int ENERGY_FROM_FOOD;

    /** standard constructor
     *
     * @param gridPos x y coord of cell
     * @param state state of cell
     * @param maxEnergy max energy of shark
     * @param energyFood energy gain from food
     * @param sharkSpawn time to reproduce
     */
    public Shark(Point gridPos, State state, int maxEnergy, int energyFood, int sharkSpawn){
        super(gridPos, state, sharkSpawn);
        this.MAX_ENERGY = maxEnergy;
        this.ENERGY_FROM_FOOD = energyFood;
        this.SHARK_SPAWN_TIMER = sharkSpawn;
        this.energy = MAX_ENERGY;
    }

    /**
     * constructor takes a generic cell and creates a new Shark
     */
    public Shark(Cell cell, int maxEnergy, int energyFood, int sharkSpawn){
        super(cell.getPosition(), cell.getCurrState(), sharkSpawn);
        this.MAX_ENERGY = maxEnergy;
        this.ENERGY_FROM_FOOD = energyFood;
        this.SHARK_SPAWN_TIMER = sharkSpawn;
        this.energy = MAX_ENERGY;
    }

    /**
     * constructor takes a generic cell and creates a new Shark with the attributes of an old Shark
     */
    public Shark(Point newPos, State sharkState, int maxEnergy, int energyFood, int sharkSpawn, int currentTimer, int energy) {
        super(newPos, sharkState, sharkSpawn, currentTimer);
        this.MAX_ENERGY = maxEnergy;
        this.ENERGY_FROM_FOOD = energyFood;
        this.SHARK_SPAWN_TIMER = sharkSpawn;
        this.energy = energy;
    }


    /**
     * gets current energy level
     */
    public int getEnergy() {
        return energy;
    }

    /**
     * sets a new max energy level
     */
    public void setMaxEnergy(int energy) {this.MAX_ENERGY = energy;}

    /**
     * sets a new shark spawn time
     */
    public void setSharkSpawnTimer(int timer) {this.SHARK_SPAWN_TIMER = timer;}

    /**
     * sets a new amount of energy gain from food
     */
    public void setEnergyFromFood(int energy) {this.ENERGY_FROM_FOOD = energy;}


    /**
     * resets energy to max
     */
    public void resetEnergy() {
        this.energy = MAX_ENERGY;
    }

    /**
     * subtracts energy
     */
    public void decrementEnergy() { energy--;}

    /**
     * increases energy by a certain amount, if
     */
    public void increaseEnergy() {
        energy+=ENERGY_FROM_FOOD;
        if (energy > MAX_ENERGY){
            resetEnergy();
        }
    }

    /**
     * resets spawn timer
     */
    @Override
    public void resetSpawnTimer() {
        setSpawnTimer(SHARK_SPAWN_TIMER);
    }
}
