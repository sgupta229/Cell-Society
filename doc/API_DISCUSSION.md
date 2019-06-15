# API DISCOVERY

package model;
public abstract class RPSCell extends Cell{ 
    public abstract RPSCell getDefeater(Map<Integer, State> stateMap); INTERNAL
    public abstract RPSCell copy(); INTERNAL
}
 
package model;
public class PredatorPreyGrid extends AbstractShapeGrid { 
    public void createNextGrid() EXTERNAL
        public void updateMaxEnergy(int max)  EXTERNAL
    public void updateEnergyFood(int energy)  EXTERNAL
    public void updateSharkSpawn(int timer)  EXTERNAL
    public void updateFishSpawn(int timer)  EXTERNAL
}
 
package model;
public class FireGrid extends AbstractShapeGrid { 
    public void createNextGrid() EXTERNAL
    public void updateProb(double newProb) EXTERNAL
}
 
package model;
public class Shark extends Fish{ 
    public int getEnergy() INTERNAL
    public void setMaxEnergy(int energy) this.MAX_ENERGY = energy;} INTERNAL
    public void setSharkSpawnTimer(int timer) this.SHARK_SPAWN_TIMER = timer;} INTERNAL
    public void setEnergyFromFood(int energy) this.ENERGY_FROM_FOOD = energy;} INTERNAL
    public void resetEnergy()  INTERNAL
    public void decrementEnergy()  energy--;} INTERNAL
    public void increaseEnergy()  INTERNAL
    public void resetSpawnTimer()  INTERNAL
}
 
package model;
public abstract class AbstractShapeGrid implements Grid { 
    public void setNeighbors(int shape) EXTERNAL
    public abstract void createNextGrid(); EXTERNAL
    public Cell[][] getNextGrid() EXTERNAL
    public void setNextGrid(Cell[][] next)  this.nextGrid = next; } EXTERNAL
    public Cell[][] getCurrentGrid()  EXTERNAL
    public Cell getCurrentCell(int row, int col)  EXTERNAL
    public void setCurrentGrid(Cell[][] curr)  this.currGrid = curr; } EXTERNAL
    public int getNumRows() INTERNAL
    public int getNumCols() INTERNAL
    public Map<Integer, State> getPossibleStateMap() INTERNAL
    public int getEdgePolicy()  return edgePolicy; } INTERNAL
    public int getNeighborPolicy() INTERNAL
    public String getCellsString(Cell[][] cells) EXTERNAL
    public int getShape()  INTERNAL
}
 
package model;
public class Scissors extends RPSCell { 
    public RPSCell getDefeater(Map<Integer, State> stateMap) INTERNAL
    public RPSCell copy() INTERNAL
}
 
package model;
public class GameOfLifeGrid extends AbstractShapeGrid { 
    public void createNextGrid() EXTERNAL
}
 
package model;
public class Paper extends RPSCell { 
    public RPSCell getDefeater(Map<Integer, State> stateMap) INTERNAL
    public RPSCell copy() INTERNAL
}
 
package model;
public class Rock extends RPSCell { 
    public RPSCell getDefeater(Map<Integer, State> stateMap) INTERNAL
    public RPSCell copy() INTERNAL
}
 
package model;
public class ConfigReader { 
    public int getNumCols() INTERNAL
    public int getNumRows() INTERNAL
    public List<Integer> getGridData() INTERNAL
    public String getSimType() INTERNAL
    public int getMyNumStates()  return myNumStates; } INTERNAL
    public Map<Integer, State> getMyStateMap()  return myStateMap; } INTERNAL
    public String toString() INTERNAL
    public int getCellShape() INTERNAL
    public int getNeighborPolicy() INTERNAL
    public int getEdgePolicy() INTERNAL
    public boolean getIsOutlined() INTERNAL
    public int getInitialConfig() INTERNAL
}
 
package model;
public class RockPaperScissorsGrid extends AbstractShapeGrid { 
    public void createNextGrid() EXTERNAL
    public void updateThreshhold(int newThresh) EXTERNAL
    public void updateRandom(int newRandom) EXTERNAL
}
 
package model;
public class Fish extends Cell{ 
    public int getSpawnTimer() INTERNAL
    public void resetSpawnTimer() INTERNAL
    public void setSpawnTimer(int t) INTERNAL
    public void setFishSpawnTimer(int t) INTERNAL
    public void decrementSpawnTimer()  this.spawnTimer--; } INTERNAL
}
 
package model;
public class PercolationGrid extends AbstractShapeGrid { 
    public void createNextGrid() EXTERNAL
}
 
package model;
public class SegregationGrid extends AbstractShapeGrid { 
    public List<Point> findEmptyCells() NOT API
    public void createNextGrid() EXTERNAL
    public void updateThresh(double newThresh) EXTERNAL
}
  
package model.neighborConstruction;
public abstract class NeighborConstructor { 
    public Grid getMyGrid() INTERNAL
    public int getEdgePolicy() INTERNAL
}
 
package model;
public class State { 
    public String getStateName() EXTERNAL
    public void setStateName(String stateName) NOT API (NEVER USED, SHOULD BE DELETED)
    public int getType() EXTERNAL
    public void setType(int type) NOT API (NEVER USED, SHOULD BE DELETED)
    public Color getColor() EXTERNAL
    public void setColor(Color color) NOT API (NEVER USED, SHOULD BE DELETED)
    public String getImage()  EXTERNAL
    public void setImage(String image) NOT API (NEVER USED, SHOULD BE DELETED)
    public double getProb() INTERNAL
    public int getAmount() INTERNAL
    public String toString() INTERNAL
    public int hashCode() INTERNAL
    public boolean equals(Object o) INTERNAL
}
 
package model;
public class Cell { 
    public List<Cell> getNeighbors() INTERNAL
    public void addNeighbor(Cell neighbor) INTERNAL
    public void addAllNeighbors(List<Cell> list) this.neighbors = list;} INTERNAL
    public State getCurrState() EXTERNAL
    public Point getPosition() INTERNAL
    public void setCurrState(State s) EXTERNAL
    public void setPosition(Point p)  NOT API (NEVER USED, SHOULD BE DELETED)
    public void setPosition(int x, int y)   NOT API (NEVER USED, SHOULD BE DELETED)
    public String toString() EXTERNAL
}
 
package view;
public class ViewerHelper { 
    public Grid getGrid(int numCols, int numRows, Cell[][] arr, String simType,  INTERNAL
    public void populateRoot(Cell[][] cells) INTERNAL
    public Cell[][] getCellArrayFromList(int numCols, int numRows, List<Integer> gridData, Map<Integer, State> possibleStateMap) INTERNAL
}
 
package view;
public class SceneMaker { 
    public Scene setupSplashScreen(int width, int height, Paint background) INTERNAL
    public void loadFromConfig() INTERNAL
}
 
package view;
public class ShapeMaker { 
    public Point findCenter(int r, int c, int shape) INTERNAL
    public javafx.scene.shape.Polygon makeShape(int r, int c) INTERNAL
    public void setPolygonSides(Polygon polygon, double centerX, double centerY, double radius, int sides) INTERNAL
}
 
package view;
public class Main extends Application { 
      public void start(Stage stage) INTERNAL
}
 
package view;
public class CellChart { 
      public LineChart initChart(Grid grid) INTERNAL
    public String convertToHex(Color c) INTERNAL
    public void updateLineChart(Grid grid, BorderPane root) INTERNAL
}
 
package view;
public class SimulationViewer { 
    public Scene getSplashScene() INTERNAL
    public void loadProperties() INTERNAL
    public void splashScreenHandleKey(KeyCode code) INTERNAL
    public Scene setupSimulation(String simFile) INTERNAL
    public void showError(String message) INTERNAL
    public void setProbCatch(double prob) INTERNAL
    public void setSegregationThreshold(double segreg_thresh) INTERNAL
    public void setMaxEnergy(int max_energy)  maxEnergy = max_energy; } INTERNAL
    public void setEnergyFood(int energy_food)  energyFood = energy_food; }  INTERNAL
    public void setSharkSpawn(int shark_spawn)  sharkSpawn = shark_spawn; } INTERNAL
    public void setFishSpawn(int fish_spawn)  fishSpawn = fish_spawn; } INTERNAL
    public void setScene(Scene scene)  primaryStage.setScene(scene); } INTERNAL
    public double getProbCatch()  return probCatch; } INTERNAL
    public int getRPSThreshold()  return rpsThreshold; } INTERNAL
    public int getRPSRandom()  return rpsRandom; } INTERNAL
    public double getSegregationThreshold()  return segregationThreshold; } INTERNAL
    public int getMaxEnergy()  return maxEnergy; } INTERNAL
    public int getEnergyFood()  return energyFood; } INTERNAL
    public int getSharkSpawn()  return sharkSpawn;} INTERNAL
    public int getFishSpawn()  return fishSpawn; } INTERNAL
    public void setRPSThreshold(int rps_threshold)  rpsThreshold = rps_threshold; } INTERNAL
    public void setRPSRandom(int rps_random)  rpsRandom = rps_random; }  INTERNAL
    public ShapeMaker getShapeMaker()  return shapeMaker; } INTERNAL
    public boolean getOutlinedGrid()  return outlinedGrid; } INTERNAL
    public Pane getRoot()  return myRoot; } INTERNAL
}

# API DOCUMENTATION

###External:

getCurrGrid() (used to get the current grid and display it in the front end)
createNextGrid() (used to generate the next iteration of the grid based on the simulation?s rules)
setCurrentGrid (used to set the grid to the result of creating the next grid) 
getShape() (used to get the shape type used in the simulation and pass it into setNeighbors)
setNeighbors() (used to set the neighbor type to the shape type provided in the config)


###Internal:

Grid.getPossibleStateMap() (used to get a Map of all possible states for this simulation)
Grid.getCurrentGrid() (gets the current Cell[][] grid)
Cell.getCurrentState() (gets the state of a cell)
Cell.getNeighbors() (gets a list of a cell?s neighbors)
State.getType() (gets the type (int) of a state)
Grid.setNextGrid(Cell[][] grid) (sets a grid?s nextGrid--used in createNextGrid())
Grid.createNextGrid() (implements rules for a simulation?s propogation)
State.equals(State o) (returns true if o equals this state)

We want users to think of our API process as having a current grid and a map of possible cell states. In createNextGrid(), you loop through the current grid and change the cell states as needed according to the simulation?s rules by using the possible states in the map. You then set the next grid equal to the new grid.

Steps to create a new Simulation
Write subclass of AbstractGrid (XXGrid) and implement createNextGrid()
createNextGrid()?s implementation must call Grid.setNextGrid(Cell[][] grid)
Depending on the complexity of the simulation it may be necessary to make subclasses of Cell, so that cell objects can contain their own information as the simulation propogates. 

