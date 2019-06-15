CompSci 307: Simulation Project Analysis by Will Francis (wbf7)
===================

> This is the link to the [assignment](http://www.cs.duke.edu/courses/compsci307/current/assign/03_simulation/)

Design Review
=======

### Status

#####What makes the code well-written and readable (i.e., does it do what you expect, does it require comments to understand)?

In general, besides a few of the more complicated Rules methods (createNextGrid() and its helpers) the code is quite 
readable, with generally small methods that do only one or at most two things. The class hierarchies are pretty straightforward
and their interactions aren't very complicated.

#####What makes this project's code flexible or not (i.e., what new features do you feel are easy or hard to add)?

I think this code is fairly flexible. It's easy to reconfigure a particular simulation using the .properties file or the
specific .csv files, and it's pretty straightforward to add a new simulation (see Design.md for instructions). It's
also not at all difficult to add a new shape. Although ShapeMaker is a little messy to navigate, new shapes don't require
much additional code

#####What dependencies between the code are clear (e.g., public methods and parameters) and what are through "back channels" (e.g., static calls, order of method call, requirements for specific subclass types)? 

Dependencies between view and model are exclusively through public methods. As far as back channels, there is little to 
no use of static calls but there is some requirement for specific Cell subclasses within PredatorPreyGrid (Fish/Shark) 
and RockPaperScissorsGrid (Rock, Paper, Scissors)


### Overall Design

#####Reflect on how the program is currently organized.

Two main modules are View and Model, within view, the primary class is SimulationViewer, which is called by Main
In Model we have a Grid interface and and AbstractShapeGrid, and all the simulations (XYZGrid) descend from those. 
Additionally, some Simulations require special Cells, which descend from the Cell superclass, such as RPSCell, Rock, Paper, and Scissors

#####As briefly as possible, describe everything that needs to be done to add a new kind of simulation to the project.
1. Create subclass in the model of the Grid class, implement methods such as createNewGrid() and specifying rules of the simulation
2. Edit ConfigReader to be able to parse config files of this type
3. Edit SimulationViewer and other view classes to allow buttons to be added to change initial config params
4. Edit view classes (primarily SimulationView) to allow buttons to be added to the scene so an instance of the simulationgrid can be created and that type of simulation to be run.

#####Describe the overall design of the program, without referring to specific data structures or actual code (focus on how the classes relate to each other through behavior (methods) rather than their state (instance variables)).
SimulationViewer creates the view, once a button is pressed by the user, ConfigReader is called, reading data from a particular
.csv file. Then once the Grid is constructed, the Application runs and calls createNextGrid in each step method, iterating the Grid

#####Justify why your overall code is designed the way it is and any issues the team wrestled with when coming up with the final design.

The code uses simple inheritance hierarchies that interact in straightforward ways. Most prominently, SimulationViewer does not use much of Grid's methods except for CreateNextGrid

We struggled with but I think eventually found a good solution to allowing different kinds of shapes. It took us a while to figure out
that with our design, shape only matters in neighbor considerations and in drawing the grid, so we didn't have to, say, make a
SquarePercolationGrid, a HexPercolationGrid, and a TrianglePercolationGrid. We only had to have specific shaped classes in neighborConstruction

### Flexibility

#####Describe what you think makes this project's overall design flexible or not (i.e., what new features do you feel it is able to support adding easily).

It is pretty easily able to add new Simulations and new Shapes. We worked to make sure that our project could handle essentially any new feature (new edge policy, neighbor policy, shapes, etc) with fairly minimal
work. Currently the program is not equipped to handle irregular shapes and would require substantial reworking to implement, but that's about
it as far as inflexibility that I can think of 

#####Describe one feature from the assignment specification that was implemented by a teammate in detail. What is interesting about this code? (why did you choose it?)

Sahil's work on implementing the graph was some really good work. He managed to make the graph's colors reflect the colors of each Sim's possible state
and made it continually update through time, rather than just stopping at a certain point like it did initially. It's also pretty streamlined, with only like 3 or 4 methods to
accomplish the entire project of the graph


### Your Design

#####Describe an abstraction (either a class or class hierarchy) you created and how it helped (or hurt) the design. Discuss any Design Checklist issues within your code (justify why they do not need to be fixed or describe how they could be fixed if you had more time).

It was my idea to create the neighborConstruction module, which allowed for new shapes not to completely break our project or result in a new class of each sim for each shape (e.g. SquarePercolationGrid, a HexPercolationGrid, and a TrianglePercolationGrid).
All we had to do was split off neighbor-work into its own hierarchy and for any new shapes just add to that hierarchy, not the overall Grid hierarchy

#####Describe one feature that you implemented in detail:

I implemented RockPaperScissorsGrid. The rules of RPS are very simple, with an added twist of optional randomness to the defeater threshold. 
There is a hierarchy of Cells with a superclass of RPS Cell. This allows the cells themselves to determine who defeats them, rather than
putting all that work in RockPaperScissorsGrid.

Cell->RPSCell->Rock, Paper, and Scissors

#####Provide links to one or more GIT commits that contributed to implementing this feature, identifying whether the commit represented primarily new development, debugging, testing, refactoring, or something else.

New development. Usually I spread out my commits a bit more, but for RPSGrid I did a complete first pass at implementation before commiting
[Link to Commit](https://coursework.cs.duke.edu/compsci307_2019spring/simulation_team03/commit/f562df601c70ab17ca1cdf623ecc53ccd6fbe347)

#####Justify why the code is designed the way it is or what issues you wrestled with that made the design challenging. Are there any assumptions or dependencies from this code that impact the overall design of the program? If not, how did you hide or remove them?

There are no dependencies in RPS that aren't in all of the Grids. Because of our design, each grid is only required to 
implement createNextGrid(), which greatly simplifies all interactions between Grids and other parts of the program

### Alternate Designs

#####Describe two design decisions made during the project in detail:

1. We debated implementing shape policy, neighbor policy, and edge policy as classes in the Grid hierarchy, but could not 
figure out a design that did not result in crazy grid variations like SquareToroidalCompleteGameOfLifeGrid or etc. Because
of this we decided to handle edge and neighbor policy within AbstractShapeGrid (and eventually we spun the majority of that
work into neighborConstruction)

2. We decided to split Cell and State into different classes. In general we can describe Cells as points in the Grid that possess a State that
can be changed. States are immutable once constructed because there are only a set number of possible states in each Simulation

#####What are the trade-offs of the design choice (describe the pros and cons of the different designs)? Which would you prefer and why (it does not have to be the one that is currently implemented)?

1. If we would have been able to figure out a proper design, we would have been able to have much smaller classes and more modular design
As it stands, the design is not as modular as it could be and the classes are bigger than is perhaps ideal, but in our
limited time frame our current design was sufficient and works just fine

2. Potentially we could have designed things in such a way that Cell and State could have been combined which would
have streamlined things a bit, but we preferred our design for conceptual simplicity