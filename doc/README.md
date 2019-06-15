# Cell Society 
## By Sam Chan, Sahil Gupta, Will Francis

###High Level Design:

- How does a Cell know what rules to apply for its model?
There is a concrete implementation of an Abstract Simulation with a specific Next State method 
- How does a Cell know about its neighbors? How can it update itself without affecting its neighbors update?
We save a pointer to the next neighbor so we can access those neighbors and modify itself without modifying its neighbors
- What behaviors does the Grid itself have? How can it update all of the Cells it contains?
Contains the size of the grid, possible cell states, shape of cells
- What information about a model could be given in a configuration file?
The current state of all the cells
The size of grid and shape of cells
Number of possible states
- Ruleset? (Perhaps as an integer for the type of game)
- How is the GUI updated after all the cells have been updated?
All states of cells are changed and a new grid is produced on the screen


###CRC Cards

What actions is this class responsible for?
What other classes are required to help this class fulfill those responsibilities?
These other classes will either be passed to this object's constructor or methods, or returned from a method, or created by this object internally


Class: Cell
- Responsibilities:
Knows State, 
knows neighbors, 
reports new state
Contains position(?), shape type

Grid
- Responsibilities:
Edge policy, cell shape, size


###Use Cases:

Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors)
Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing)
Move to the next generation: update all cells in a model from their current state to their next state and display the result graphically
Set a model parameter: set the value of a parameter, probCatch, for a model, Fire, based on the value given in a configuration file


###Some helpful links:

https://coursework.cs.duke.edu/compsci307_2019spring/simulation_team03
Initial Design:
https://www2.cs.duke.edu/courses/spring19/compsci307/classwork/06_design_cellsociety/
Reading:
https://natureofcode.com/book/chapter-7-cellular-automata/
