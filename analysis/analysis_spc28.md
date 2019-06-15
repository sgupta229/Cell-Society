CompSci 307: Simulation Project Analysis by Samuel Chan (spc28)
===================

> This is the link to the [assignment](http://www.cs.duke.edu/courses/compsci307/current/assign/03_simulation/)

Design Review
=======

### Status


What makes the code well-written and readable (i.e., does it do what you expect, does it require comments to understand)?

* The code is in general fairly well-written and readable. The code has javadocs documentation to help guide the user in case there is any difficulty, but in general, the logic and order of progression from one class and one method to the next is 
generally straightforward. Variables, method names, and Classes are appropriately named and are not too long--generally they can take a few helper methods to separate out tasks into shorter sequential steps.

What makes this project's code flexible or not (i.e., what new features do you feel are easy or hard to add)?

* One of the most flexible part about the project is the ability for a user to essentially specify any type of configuration for the simulation (assuming the simulation type/config is valid) with a myriad of 
customizable settings. Adding features to the model to expand the code's features are fairly straightforward and essentially need a few edits to ConfigReader (to take in the new type of simulation) as well as a creation of a subclass Grid
to incorporate the new Grid type/functionality. There are various amounts of helper methods within classes to allow consumers of this project's API to add on with relative ease.

What dependencies between the code are clear (e.g., public methods and parameters) and what are through "back channels" (e.g., static calls, order of method call, requirements for specific subclass types)? 

* Dependencies in the code are fairly clear and are made through public methods, parameters, creation of subclasses and instances of classes. There are little to no static calls or other dependencies, although one issue that was encountered was downcasting 
the abstract Grid that was specified in the beginning to the specific type of Grid used in the simulation (although we did implement an if statement to ensure the downcasting would not encounter any type of exception). We do acknowledge this is relatively incorrect practice 
and next time would like to achieve more clear cut separation between the view/controller/model.


### Overall Design

Reflect on how the program is currently organized.

* The code is currently organized into a view and model. The view handles the displaying of the simulation, along with all the parameters, buttons, properties necessary to display the screen/grid/charts to the user and take in user input
during the simulation, including key press, button click, etc. The model is the back end of the program, which specifies the rules of the simulations and parses config files for setting up the simulation, along with how the simulation itself takes place and runs.

As briefly as possible, describe everything that needs to be done to add a new kind of simulation to the project.

* Firstly, the configreader class needs to be modified to ensure the config file can correctly parse for the specified type of simulation along with all the necessary parameters needed for the simulation, handling exceptions and formatting issues for the specific type.
Secondly, a new subclass of AbstractShapeGrid needs to be added that implements createnextGrid as well as any other helper methods that specify the rules of the game 
Thirdly, any number of view classes (probably mostly simulation viewer) needs to be modified to allow buttons to be added to the simulation when the simulation runs tailored for that specific type of simulation, as well as buttons on the splash screen to navigate to that simulation.

Describe the overall design of the program, without referring to specific data structures or actual code (focus on how the classes relate to each other through behavior (methods) rather than their state (instance variables)).

* Essentially, the SimulationViewer (and other helper methods inside view) set up the main screen along with all required buttons. When the button launching the simulation is clicked on, the configreader reads in the inputted .csv file, parsing it for relevant data. SimulationViewer creates an instance
of that type of simulation and each step of the animation, updates the grid to display the next step's results to the user, along with a chart for visual effect. Buttons on the top allow the user to interact with the simulation's properties in real time, and key press for specific commands are also handled.


Justify why your overall code is designed the way it is and any issues the team wrestled with when coming up with the final design.

* The code is designed the way it is such that as much as possible, the view and all its components are kept separate from the backend model "partition." This is to ensure clear code separation and to ensure our program was DRY. We also incorporated lots of inheritance, encapsulation, polymorphism to make the code as 
readable as possible and as flexible as possible for both the user, the programmer, and the consumer of our API. Thus, we had many layers of separation and abstraction between types of Grids, cells, etc.

### Flexibility

Describe what you think makes this project's overall design flexible or not (i.e., what new features do you feel it is able to support adding easily).

* This program's overall design was flexible in its ability to take advantage of abstraction and separation between layers. Definitely being able to add a new type of simulation easily was the top of our bucket list and we accomplished this very effectively, with each new simulation being easier to add than the last, as we abstracted away our program and ensured code separationm, eliminated redundancies.
* The fact that the code itself was very abstracted out definitely helped, as the top level of the hierarchy was the Grid interface, followed by the AbstractShapeGrid, which utilized different types of neighborConstruction. Each of the 6 simulations 
extended this abstract shape grid. The cell class itself was also able to be abstracted out (though it wasn't abstract), as for two of the simulations, there were different types of cells that extended the main Cell class.
As discussed before, it is relatively easy to create a new simulation, with all its required modifications, making the code fairly flexible.

Describe one feature from the assignment specification that was implemented by a team mate in detail. 
What is interesting about this code? (why did you choose it?)
Describe the design of this feature in detail? 
(what parts are closed? what implementation details are encapsulated? what assumptions are made? do they limit its flexibility?) 
How flexible is the design of this feature? (is it clear how to extend the code as designed? what kind of change might be hard given this design?)

* Cell chart was something that I had no part to play that Sahil did an excellent job implementing: it essentially plotted the states of the simulation at each time step (updated on step()) with different colors (at the end, sahil was able to figure out how to match the colors with the ones specified in the config file)
and to extend the chart once the graph had reached the end of its screen. He did this with LineChart, borderpane, and various other modules.
* It was super interesting because in the beginning, I had no idea how to display the data and Sahil really took the lead and accomplished this. Firstly, there is a method in the class that initially creates the cell chart with all its initial properties, including parameters passed in
from the results parsed from the ConfigReader like the color, as well as from the resources.properties file. Then it set up the chart itself and displays it to the user, and after each step, updates the chart accordingly.
The chart design is quite flexible, and exposes a succinct API to the user to use this graph in the simulation. A programmer later on can go in and modify the cellchart without fundamentally disturbing its ubiquity.


### Your Design

```java
```

Describe an abstraction (either a class or class hierarchy) you created and how it helped (or hurt) the design. Discuss any Design Checklist issues within your code (justify why they do not need to be fixed or describe how they could be fixed if you had more time).

* One abstraction I helped to create was AbstractShapeGrid, which was a class that extended the Grid interface and specified instance variables for the class such as neighbors, edges, shape, grids, and state maps.
It also included useful methods such as getters and setters for the Grid so that other classes (namely in View) can employ them to display the simmulation results on a chart or on the grid to the user.
This abstraction was extremely important because of it was the main parent grid for the simulation grids, and specified the abstract createNextGrid() that was the backbone of each simulation's rules and 
required the grids to implement. The setneighbors  method was really important as it specified what type of shape would be primarily used in the simulation and therefore the type of neighbor policy. There were no major design checklist issues that I can think of.

Describe one feature that you implemented in detail:

* ConfigReader class is a fairly complicated class that accomplished a myriad of things, including ensuring the data is correctly formatted the way it should be in the config .csv file and throwing specific exceptions when it isn't.
* It also provides getters for all the variables and data it has stored and parsed/generated from the config file itself

Provide links to one or more GIT commits that contributed to implementing this feature, identifying whether the commit represented primarily new development, debugging, testing, refactoring, or something else.

[Link to Commit](https://coursework.cs.duke.edu/compsci307_2019spring/simulation_team03/commit/abf61c52b02561925e4fdeb86d12cc9aaaefa002)

This commit is primarily development, and when the user specifies that the data should be generated probabilistically, provides a method that does this and returns an array of randomly probabilistically generated data based on the values specified in the config file

Justify why the code is designed the way it is or what issues you wrestled with that made the design challenging. Are there any assumptions or dependencies from this code that impact the overall design of the program? If not, how did you hide or remove them?

It is designed the way it is because we believed that inputting a config file was the way we wanted our simulation to run/acquire its data. Thus, depending on the ways the user would input the data, we would parse through it and depending on the type of data input, generate/parse the data itself.
Main assumptions/dependencies were that the data stored would eventually be used in the Grid/simulationviewer, and that the files inputted into the configreader actually existed and were formatted correctly


### Alternate Designs


Describe two design decisions made during the project in detail:

* One big decision that was made was the decision to truly abstract the Grids out. Initially, we had just a grid for each simulation, but we realized this wasn't flexible enough and didn't make use of good OOP practices such as polymorphism/inheritance.
Thus, we created a Grid interface as well as an AbstractShapeGrid that allowed concrete simulationgrids to extend these grids

* Similarly, our abstraction for Cell was fairly large as well. While it isn't an abstract class, we knowingly kept in concrete so that most classes could take advantage of the basic functionality of the Cell class and more specific simulations that needed more unique specs for its Cell states would create subclasses of Cell, for example Shark or Paper, that would incorporate their 
own implementations that included private instance variables, getter/setters, modified constructors, etc. for the rules of the game.

What are the trade-offs of the design choice (describe the pros and cons of the different designs)? Which would you prefer and why (it does not have to be the one that is currently implemented)?

* The pros were obvious as it allowed for better flexibility in the code, easier for programmers to add onto the code and add new features, and was generally better OOP design. I definitely prefer the greater amount of abstraction as it was more readable, logical, clean, and kept the code DRY.
