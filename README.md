model
====

This project implements a cellular automata simulator.

Names: Sahil Gupta, William Francis, Sam Chan

### Timeline

Start Date: 2/16/19

Finish Date: 3/9/19

Hours Spent: 45

### Primary Roles

Sam - worked mostly on the front end. Used CSS to design the front end, added a variety of buttons with 
different uses (saving state/changing state). Also worked on error checking and the configuration file.
Will - worked on backend, frontend, and and configuration details. Worked on displaying the grid for all
shapes as well. Also helped implement simulations, neighbor policies, and edge policies.
Sahil - Mainly worked on backend (implementing simulations, neighbor policies, edge policies) with 
a little front end (implementing the graph).

### Resources Used

https://stackoverflow.com/questions/49550428/on-javafx-is-there-any-way-to-draw-a-regular-polygon-without-knowing-the-coordi


### Running the Program

Main class: 

The Main class is in the view module.

Data files needed: 

The data files needed for testing and running the program are in the data folder. These CSV files are
read to construct the simulation and begin running it. We also use a resources.properties file for naming 
buttons and files.

Interesting data files:

Our resources.properties file has the names of all the buttons etc. A couple of our simulation file setups
are somewhat interesting (like Game Of Life glider)

Features implemented:

* Shapes: Square, Triangle, Hexagon
* Neighbor Policies: Cardinal, Complete, Diagonal
* Edge Policies: Bounded, Toroidal, Klein Bottle
* Simulations: Fire, Game Of Life, Percolation, Predator Prey, Rock Paper Scissors, and Segregation
* Chart: Chart that displays the number of each type of cell for the current grid with proper colors
* Images: Images can be applied on any type or shape of cell
* Error Checking: robust error checking to provide feedback on errors in config file etc.
* Design: CSS used to make front end look better
* Functionality: pause (p), step through (.), and toolbar buttons to change the state of the current simulation
* Mouse Clicking: user can click on a cell to cycle through its current states (NOTE: feature is buggy
and sometimes clicking on a cell registers as a neighboring cell)
* Setups: Different grids can either be provided or created using probability or concentration distributions

These are the main features implemented. We implemented all features required for complete (in case we forgot
to list one above) 

Assumptions or Simplifications:

We do not believe we simplified any aspects of the project. 
For segregation we assumed that if there are not enough empty spots for every cell to move to a satisfied spot, 
the simulation will aim to make as many cells satisfied as possible.

Known Bugs:

The feature that should let you click on a cell during a simulation to change its state is buggy. Sometimes
the cell you click on does not change states but another neighboring cell does instead.

Extra credit:


### Notes

The simulations are generic and work for all shapes. Implementing new simulations setups is pretty easy. 
You need to make the CSV file with the desired grid (shape, size, neighbor policy, edge policy, etc.) then 
add a button that maps to that CSV file in the SceneMaker class.

### Impressions

This project was fun and a great learning experience. The project ended up being easier than expected.
I would've preferred to use XML files than a config file.