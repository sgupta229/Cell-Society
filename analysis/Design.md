###How to Add New Features


.csv file structure:

NAMEOFSIMUALTION
SHAPE,NEIGHBORPOLICY,EDGEPOLICY,ISOUTLINEDGRID?,HOWGRIDISMADE (DETERMINISTIC, PROBABILISTIC, TOTALSUM)
NUMSTATES
STATE1NAME,STATETYPE(int),COLOR,IMAGE,PROBABILITY,AMOUNTFORTOTALSUM
NUMCOLS,NUMROWS
[insert deterministic grid below, for example:]
2,1,2,0,1,2,0,2,1,0
1,0,1,2,1,2,1,2,1,1
1,0,1,1,0,2,0,1,2,1
0,2,2,2,2,1,1,1,2,2
1,1,0,1,0,1,2,2,1,2
1,0,0,1,0,0,2,2,1,2
0,2,2,2,2,1,1,0,1,0
0,1,0,0,2,1,2,1,2,2
2,2,2,1,2,0,2,2,2,1
1,2,0,1,0,2,1,0,0,1

#####Adding New Simulations

1. Create subclass in the model of the Grid class, implement methods such as createNewGrid() and specifying rules of the simulation
2. Edit ConfigReader to be able to parse config files of this type
3. Edit SimulationViewer and other view classes to allow buttons to be added to change initial config params
4. Edit view classes (primarily SimulationView) to allow buttons to be added to the scene so an instance of the simulationgrid can be created and that type of simulation to be run.

#####Adding New Shapes

1. Add new if statement to setNeighbors in AbstractShapeGrid, constructing the new ShapeXNeighborConstructor
2. In neighborConstruction, create a new ShapeXNeighborConstructor class and implement AddNeighbors()
3. Modify ShapeMaker to incorporate the new shape's drawing
4. Edit .csv files with the name of the new shape or create a new .csv file

#####Adding New Neighbor Policies

1. Edit addNeighbors() in each concrete neighborConstruction class to conform to new neighbor policy
2. Edit .csv files or create new one with new neighbor policy listed


#####Adding New Edge Policies


###Major Design Choices


* One big decision that was made was the decision to truly abstract the Grids out. Initially, we had just a grid for each simulation, but we realized this wasn't flexible enough and didn't make use of good OOP practices such as polymorphism/inheritance.
Thus, we created a Grid interface as well as an AbstractShapeGrid that allowed concrete simulationgrids to extend these grids

* Similarly, our abstraction for Cell was fairly large as well. While it isn't an abstract class, we knowingly kept in concrete so that most classes could take advantage of the basic functionality of the Cell class and more specific simulations that needed more unique specs for its Cell states would create subclasses of Cell, for example Shark or Paper, that would incorporate their 
own implementations that included private instance variables, getter/setters, modified constructors, etc. for the rules of the game.

* The pros were obvious as it allowed for better flexibility in the code, easier for programmers to add onto the code and add new features, and was generally better OOP design. I definitely prefer the greater amount of abstraction as it was more readable, logical, clean, and kept the code DRY.


###Assumptions/Decisions

* One important abstraction we creted was AbstractShapeGrid, which was a class that extended the Grid interface and specified instance variables for the class such as neighbors, edges, shape, grids, and state maps.
  It also included useful methods such as getters and setters for the Grid so that other classes (namely in View) can employ them to display the simmulation results on a chart or on the grid to the user.
  This abstraction was extremely important because of it was the main parent grid for the simulation grids, and specified the abstract createNextGrid() that was the backbone of each simulation's rules and 
  required the grids to implement. The setneighbors  method was really important as it specified what type of shape would be primarily used in the simulation and therefore the type of neighbor policy.

