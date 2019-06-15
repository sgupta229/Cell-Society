package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * PredatorPrey Grid Simulation
 */
public class PredatorPreyGrid extends AbstractShapeGrid {

    // use enums?
    private State emptyState;
    private State fishState;
    private State sharkState;

    private int maxEnergy;
    private int energyFood;
    private int sharkSpawn;
    private int fishSpawn;

    /**
     *
     * @param width number of columns
     * @param height number of rows
     * @param gridData grid data structure
     * @param possibleStateMap map of possible States in this sim
     * @param edgePolicy int of edge policy (0-2)
     * @param neighborPolicy int of neighbor policy (0-2)
     * @param shape int of shape (0-2)
     * @param maxEnergy max energy a shark can have
     * @param energyFood amount of energy food gives
     * @param sharkSpawn time for a shark to reproduce
     * @param fishSpawn time for a fish to reproduce
     */
    public PredatorPreyGrid(int width, int height, Cell[][] gridData, Map<Integer, State> possibleStateMap, int edgePolicy, int neighborPolicy, int shape,
                            int maxEnergy, int energyFood, int sharkSpawn, int fishSpawn) {
        super(width, height, gridData, possibleStateMap, edgePolicy, neighborPolicy, shape);
        var stateMap = getPossibleStateMap();
        emptyState = stateMap.get(0);
        fishState = stateMap.get(1);
        sharkState = stateMap.get(2);

        this.maxEnergy = maxEnergy;
        this.energyFood = energyFood;
        this.sharkSpawn = sharkSpawn;
        this.fishSpawn = fishSpawn;

        populateGridWithActors();
    }

    private void populateGridWithActors() {
        var currentGrid = getCurrentGrid();
        for(int i = 0; i < currentGrid.length; i++) {
            for(int j = 0; j < currentGrid[0].length; j++) {
                var cell = currentGrid[i][j];
                if (cell.getCurrState().equals(fishState)) {
                    currentGrid[i][j] = new Fish(cell, fishSpawn);
                } else if (cell.getCurrState().equals(sharkState)) {
                    currentGrid[i][j] = new Shark(cell, maxEnergy, energyFood, sharkSpawn);
                }
            }
        }
    }

    /**
     * method to create the next state of Grid (the rules method)
     */
    public void createNextGrid() {
        var currentGrid = getCurrentGrid();
        setNeighbors(getShape());
        var nextGrid = new Cell[currentGrid.length][currentGrid[0].length];

        for (Cell[] row: currentGrid){
            for (Cell cell: row){
                var oldPos = cell.getPosition();
                Point newPos;
                if (isFish(cell)){
                    var fish = (Fish) cell;
                    fish.decrementSpawnTimer();

                    if (isShark(fish)){
                        var shark = (Shark) fish;
                        shark.decrementEnergy();
                        newPos = trollForFish(shark);
                        if (!newPos.equals(oldPos)){
                            shark.increaseEnergy();
                            if (shark.getSpawnTimer() <= 0){
                                shark.resetSpawnTimer();
                                nextGrid[oldPos.y][oldPos.x] = new Shark(oldPos, sharkState, maxEnergy, energyFood, sharkSpawn);
                            }
                            else {
                                nextGrid[oldPos.y][oldPos.x] = new Cell(oldPos, emptyState);
                            }
                        } else{
                            newPos = getNewPosition(shark);
                            nextGrid[oldPos.y][oldPos.x] = new Cell(oldPos, emptyState);
                        }
                        if (shark.getEnergy() <= 0){
                            nextGrid[oldPos.y][oldPos.x] = new Cell(oldPos, emptyState);
                            continue;
                        }
                        nextGrid[newPos.y][newPos.x] = new Shark(newPos, sharkState, maxEnergy, energyFood, sharkSpawn, shark.getSpawnTimer(), shark.getEnergy());

                    }
                    else {
                        newPos = getNewPosition(fish);
                        if (nextGrid[oldPos.y][oldPos.x] != null
                                && nextGrid[oldPos.y][oldPos.x].getCurrState().equals(sharkState)) {
                            continue;
                        }
                        if (nextGrid[newPos.y][newPos.x] != null
                                && !(nextGrid[newPos.y][newPos.x].getCurrState().equals(emptyState))){
                            newPos = oldPos;
                        }

                        if (!newPos.equals(oldPos)){
                            if (fish.getSpawnTimer() <= 0){
                                fish.resetSpawnTimer();
                                nextGrid[oldPos.y][oldPos.x] = new Fish(oldPos, fishState, fishSpawn);
                            }
                            else {
                                nextGrid[oldPos.y][oldPos.x] = new Cell(oldPos, emptyState);
                            }
                        }
                        nextGrid[newPos.y][newPos.x] = new Fish(newPos, fishState, fish.getSpawnTimer());
                    }
                }
                else if (nextGrid[oldPos.y][oldPos.x] == null || nextGrid[oldPos.y][oldPos.x].getCurrState().equals(emptyState)) {
                    nextGrid[oldPos.y][oldPos.x] = new Cell(oldPos, emptyState);
                }
            }
        }
//        System.out.println("\n========\n" + getCellsString(currentGrid) + "\n========\n" + getCellsString(nextGrid));
        setNextGrid(nextGrid);
    }


    private boolean isShark(Cell cell) {
        return cell instanceof Shark;
    }

    private boolean isFish(Cell cell) {
        return cell instanceof Fish;
    }

    private Point trollForFish(Cell cell) {
        return getNewPosition(cell, fishState);
    }

    private Point getNewPosition(Cell cell){
        return getNewPosition(cell, emptyState);
    }

    private Point getNewPosition(Cell cell, State allowedState) {
        var possibleMoves = new ArrayList<Cell>();
        for (Cell c : cell.getNeighbors()) {
            if (c.getCurrState().equals(allowedState)) {
                possibleMoves.add(c);
            }
        }

        if (possibleMoves.isEmpty()) {
            return cell.getPosition();
        }

        var r = new Random();
        int i = r.nextInt(possibleMoves.size());
        return possibleMoves.get(i).getPosition();
    }

    /** updates maxEnergy if button is pressed
     *
     * @param max new maxEnergy
     */
    public void updateMaxEnergy(int max) {
        for (Cell[] row : getCurrentGrid()) {
            for (Cell cell : row) {
                if(isShark(cell)) {
                    ((Shark) cell).setMaxEnergy(max);
                }
            }
        }
    }

    /** updates energyFood if button is pressed
     *
     * @param energy new energyFood
     */
    public void updateEnergyFood(int energy) {
        for (Cell[] row : getCurrentGrid()) {
            for (Cell cell : row) {
                if(isShark(cell)) {
                    ((Shark) cell).setEnergyFromFood(energy);
                }
            }
        }
    }

    /** updates energyFood if button is pressed
     *
     * @param timer new sharkSpawn
     */
    public void updateSharkSpawn(int timer) {
        for (Cell[] row : getCurrentGrid()) {
            for (Cell cell : row) {
                if(isShark(cell)) {
                    ((Shark) cell).setSharkSpawnTimer(timer);
                }
            }
        }
    }

    /** updates fishSpawn if button is pressed
     *
     * @param timer new fishSpawn
     */
    public void updateFishSpawn(int timer) {
        for (Cell[] row : getCurrentGrid()) {
            for (Cell cell : row) {
                if(isFish(cell)) {
                    ((Fish) cell).setFishSpawnTimer(timer);
                }
            }
        }
    }

}