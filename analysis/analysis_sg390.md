CompSci 307: Simulation Project Analysis by Sahil (sg390)
===================

> This is the link to the [assignment](http://www.cs.duke.edu/courses/compsci307/current/assign/03_simulation/)

Design Review
=======

### Status

* I think our code is well-written and readable. We separated our classes into two main moduels: model and 
view. In our model class we have another module that deals specifically with setting neighbors, and the hierarchy
is clear and easy to understand that certain classes define the neighbors for different shapes and types 
of edge policies. We have one interface that our abstract grid class implements. All of our specific grid implementations
extend AbstractShapeGrid. Even without comments, I think the way we defined our basic functionality of an abstract grid is pretty
clear through variable names and the method names. Each specific implementation only tacks on a couple of extra methods that are
easy to understand what they do. The readability of the createNextGrid() method in each specific grid implementation, however, 
are pretty complex and not readable. This is due to the fact that some of the grid rules are somewhat complicated and looping
through our 2D array of cells makes reading the code somewhat difficult. Other than that, model is easy to digest and understandable.

For example, this is our createNextGrid() method for the predator prey simulation type: 

```java

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

```

This is one of our longest, if not longest, method in the program just because the simulation rules were complicated. Even after making
specific classes for Sharks and Fish, the code is still difficult to follow. The method name explains that this method is responsible
for creating the next predator prey grid though. 

This method is the addNeighbors method from our TriangleNeighborConstructor class. This method is slightly lengthy, but our
variable names make it pretty easy to follow what is going on.

```java

 protected void addNeighbors(Cell cell, int row, int col, int top, int bottom, int left, int right, Cell[][] grid) {
        boolean isUprightTriangle = checkUprightTriangle(row, col);
        if(getMyGrid().getNeighborPolicy() != DIAGONAL_NEIGHBORS) {
            addNeighborIfValid(cell, row, left, grid);
            addNeighborIfValid(cell, row, right, grid);
            if(isUprightTriangle) {
                addNeighborIfValid(cell, bottom, col, grid);
            }
            else {
                addNeighborIfValid(cell, top, col, grid);
            }
        }
        if(getMyGrid().getNeighborPolicy() != CARDINAL_NEIGHBORS) {
            addNeighborIfValid(cell, top, left, grid);
            addNeighborIfValid(cell, top, right, grid);
            addNeighborIfValid(cell, bottom, left, grid);
            addNeighborIfValid(cell, bottom, right, grid);
        }
        if(getMyGrid().getNeighborPolicy() == COMPLETE_NEIGHBORS) {
            int twoLeft = left - 1;
            int twoRight = right + 1;
            if(twoLeft < 0) {
                if(getEdgePolicy() != 0) {
                    twoLeft = twoLeft + getMyGrid().getNumCols();
                }
            }
            if(twoRight >= getMyGrid().getNumCols()) {
                if(getEdgePolicy() != 0) {
                    twoRight = twoRight - getMyGrid().getNumCols();
                }
            }
            addNeighborIfValid(cell, row, twoLeft, grid);
            addNeighborIfValid(cell, row, twoRight, grid);
            if(isUprightTriangle) {
                addNeighborIfValid(cell, top, col, grid);
                addNeighborIfValid(cell, bottom, twoLeft, grid);
                addNeighborIfValid(cell, bottom, twoRight, grid);
            }
            else {
                addNeighborIfValid(cell, bottom, col, grid);
                addNeighborIfValid(cell, top, twoLeft, grid);
                addNeighborIfValid(cell, top, twoRight, grid);
            }
        }
    }

```

As for view, we refactored as much as possible so that it is easy to see which class handles what for view. For example, the CellChart
class is exclusively responsible for managing and updating the chart in the simulation. The ShapeMaker class is responsible for 
setting the shapes in the grid depending on the shape and the number of them. SceneMaker is resopnsible for switching between 
scenes that are necessary in the simulation (splash screen, certain grid screens, etc. ). These classes tend to be longer and
somewhat more difficult to understand, but we named methods and variable names as best as possible to make it easy to understand
what specific methods are doing.

```java
private Scene setupRPSOptions(int width, int height, Paint background) {
        VBox vb = new VBox(vboxVar);
        vb.setAlignment(Pos.CENTER);
        var rpsScene = new Scene(vb, width, height, background);
        Button b0 = new Button(myResources.getString("RPS_SQUARE_BTN"));
        b0.setOnAction(e -> {
            var simulationFile = myResources.getString("RPS_SQUARE_FILE");
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        Button b1 = new Button(myResources.getString("RPS_HEX_BTN"));
        b1.setOnAction(e -> {
            var simulationFile = myResources.getString("RPS_HEX_FILE");
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        Button b2 = new Button(myResources.getString("RPS_TRIANGLE_BTN"));
        b2.setOnAction(e -> {
            var simulationFile = myResources.getString("RPS_TRIANGLE_FILE");
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        vb.getChildren().addAll(b0,b1,b2);
        rpsScene.getStylesheets().add(STYLESHEET);
        rpsScene.setOnKeyPressed(e -> viewer.splashScreenHandleKey(e.getCode()));

        return rpsScene;
    }

```

A lot of our methods in SceneMaker look like this, and it is easy to see what they are doing. They are setting up the options
to choose which type of grid the user wants to display for that particular simluation type. 

* I think this code is flexible. Adding new shapes simply requires adding a new ShapeNeighborConstructor class and implementing
how to set its neighbors. Our code to set neighbors is pretty modular and only requires implementing a couple of methods for all
types of neighbor and edge policies. Then the user just needs to make a few edits in the ConfigReader to allow for that type
of shape to be written in the config file. 

Our configuration file setup is also a large reason why our simulations are flexible. The user can choose virtually any type of simulation combination
by selecting shape, simulation type, the way the grid is initially set, the images for the cell types, the colors etc. 
The user can pretty much change up anything about the grid that they.

Adding new types of simulations is also easy. A new class representing that simulation just needs to extend AbstractShapeGrid
and implement the abstract method createNextGrid(). With a few edits in the configReader and adding a couple of buttons to display
a simulation of that grid type in view, the grid can be up and running in no time.

* I think the dependencies between our code is clearly seen through public methods. These methods are needed across
view and model. Other than that our modules are fairly self contained and are not dependent on each other at all. There may be 
a few back channels, but we avoided using static methods and calls throughout the project.

### Overall Design

The way the code is organized is pretty simple. We have a model package and a view package. The model package contains all of 
the backend elements such as the simulation grids and the abstract grid etc. We also have a package inside the model package 
neighborConstructor that has all of the classes that deal with setting neighbors for different shapes, neighbor policies, and edge 
policies. There is no javaFX in these classes. The view package contains all the classes that deal with making the graph, setting 
scenes, and updating the grid for the user to see.

* To add a new kind of simulation:
    * Create a new class in model that extends AbstractShapeGrid
    * Implement the createNextGrid() method which loops through the current grid (2D array of cells) and sets new neighbors
    for cells based on the rules of the simulation.
    * Modify the config reader so that it does not throw an exception for the new grid 
    * Go into SceneMaker and SimulationViewer to add a method that includes a button to load that CSV if that type of simulation 
    is selected by the user

* The overall design is as follows:
    * The SimulationViewer is our "main" class in the view package. This class is responsible for starting up the initial splash screen.
When the user selected a button (a simulation type), the user is taken to another scene where they can select a specific type
of grid of that simulation (different shapes/sizes/probabilities etc). When launching a simulation, the config reader reads 
in the CSV file associated with that button and parses all the data. Using this data, we construct the backend version of the
grid to get the current state of the grid and then createNextGrid() is called in the step function, which sets the current grid 
equal to the next version of the grid. At the same time the frontend methods are changing the colors of the shapes in the
grid that the user sees.

* Justification
    * I think our design is justified because our hierarchies are simple but justified. We have on parent class for all of
    our specific simulation classes, which makes sense because the only method that the children need to implement is createNextGrid().
    I also like how we ended up creating a separate hierarchy for the shapes in the neighborConstruction model. Other than 
    having to think of the best algorithms for adding neighbors, our code makes it easy to implement all edge policies at once. Only
    the neighbor policies need to be coded up if a new shape needs to be added. Our view class is pretty much independent
    from out model class. We organized our view class into a few classes to make it easier to understand. SimulationViewer is pretty long, 
    but we refactored it a lot so that the chart, scenes and shapes all have their own classes and it is easier to understand that those
    classes are specifically for those features.
    
    * I think one thing we wrestled with was our grid hierarchy design. We originally thought that we would have an abstract 
    grid class that had subclasses based on shape. So there would be an AbstractGrid class and then AbstractSquareGrid, 
    AbstractHexagonGrid etc . Then specific grids would implement these shapes. The problem was that grid implementations (like Segregation 
    Grid) stay the same and you cannot extend more than one class, so it didn't make sense to partition the hierarchy. We then made an 
    interface that our AbstractGrid class implements. I think the utility of this interface is questionable, but I think having just one 
    class that all the simulations extend is fine. We eventually decided to create a separate hierarchy for classes that deal
    with the neighbors and shapes.
    

### Flexibility

There are two primary reasons why I think our code is flexible. It is easy to add new simulations and it is easy to add shapes. 
I think figuring out how to add those two easily are the hardest parts. Editing the configuration file is not too hard as you just need
to make sure it can accept the new shape/configuration. A couple of changes can be streamlined so that less things need to be changed 
throughout the code, but the main idea is that add a new simulation and shape are easy using our hierarchies.

* Team mate feature
    * One feature that I did not touch was making the different shapes to display to the frontend. This is something Will did that I was 
    really impressed by. Displaying squares was easy because you can use javafx panes pretty easily to do so, but getting
    hexagons and triangles to fit together in a grid type shape is completely different, so I thought the way Will did it was super
    interesting. 
    * I think Will did a good job implementing this feature. Pretty much all of the work is done in ShapeMaker. The code is
    easy to follow since his variable names and methods have meaningful names. 
    
    
 ```java
private Point findCenter(int r, int c, int shape) {
        var radius = myCellDiameter/2;
        int x,y;
        x = y = -1;
        if (shape == triangleNum){
            y = r * radius + radius;
            x = c * radius + radius;
            if (isOdd(c)) {
                if (isEven(r)){
                    y-= radius/2;
                } else {
                    y += radius/2;
                }
            }
            if (r > 1) {
                int i = findCenterHelper();
                y+=(r*i);
            }
            if (isOdd(r)&&r>2){
                y-=radius/2;
            }
        }
        else if (shape== hexagonNum){
            y = r * myCellDiameter + radius;
            x = c * myCellDiameter + radius;
            if (isOdd(r)){
                x+=radius;
            }
        }else if (shape== squareNum){
            y = r * myCellDiameter + radius;
            x = c * myCellDiameter + radius;
        }
        return new Point(x, y);
    }
```

Looking at this code you know that he is finding the center based on the type of shape. The code is pretty
much all contained within the ShapeMaker class. Will makes an instance of this variable in SimulationViewr
and then sets shapes as necessary. A couple of assumptions he makes is that the only shapes are square, hexagon, and
triangle, which I think is fine because if there were to be more shapes it can easily be fixed to be modular. 
I like how most methods in the class are private though. He contains most of the work within the class so that 
when looking through SimulationViewer it is pretty straightforward what the ShapeMaker is doing.

I think the flexibility is somewhat limited because he could've maybe split up methods based on shapes such as 
findCenterSquare and call the method necessary based on the type of shape. Either way, it is not difficult
to add new shapes granted you have the math ready. Using shapemaker will was able to create the shapes, find their
centers, and place them in the grid to be displayed to the user. I just thought this was interesting
because it involved some interesting math, and we had to change a few things from our original implementation
to be able to display new shapes.


### Your Design

I created AbstractGrid class that all the other simulations extend. Although I was initially conflicted on whether 
or not this was the best way to do it, I think this class best captures all the similarities that simulations will 
have with each other. The subclasses are only required to implement one method which is createNextGrid(). The main purpose
of this class is so that all grids have certain characteristics (currGrid and nextGrid, state map, edge and neighbor policies etc). 
Also, this class calls the appropriate neighbor constructor and sets the neighbors for the cells so the 2D array is initialized
and all the cells have the appropriate neighbors. This is pretty much all the functionality needed between different
simulations. The rest needs to be specific to the rules of different simulations.

We used the checklist tool pretty extensively to fix bugs in our code. We easily cleaned up most magic value issues 
(except for a handful that did not make sense to fix) and fixed all wildcard import problems. The only issue we were 
unable to fix mostly were the cognitive complexity issues. The reason we couldn't fix these is because a lot of our 
createNextGrid() methods were too complex. We could've probably fixed these with more time by refactoring them into 
smaller and more digestable methods, but we didn't want to mess up our implementations which we had already thoroughly tested to be
correct. It was also difficult to solve these because we loop through our grid and update each cell's neighbor one by one.
This requires a nested for loop since we use a 2D array. This means any other for loop inside that nested for loop appears 
to the checker as cognitively complex since it looks like a bunch of nested for loops even though the first two are
just to get a cell. Therefore, I think our code does a great job of all the design issues. One problem that we should've 
fixed is that some of our constructors take a lot of arguemnts. For example, all of our grids take 8+ arguments, which is a
lot. There is probably a better way to implement that, and given more time I would want to look into that. Lastly, an 
issue we were unable to fix is that we pass around our 2D array. We would've only passed around individual cells rather
than our whole data structure had we known to fix this before. After finishing the project though, we had too much implemented
around that idea that we would've messed up the code had we tried to change it. If we had extensive time to refactor we could probably
fix it, but it would still be tough. In general though we satisfied pretty much all the items on the design checklist and 
I think our design is very intuitive and modular.

One feature I implemented was the chart. This line chart displays to the right of the grid and displays the amount of 
cells in the grid (real time) to give a representation of how many cells of each type are currently in the simulation. 
I made it so that it is a line chart and that it adjusts the axes accordingly based on the size of the grid. I also 
made it so that the colors of each line correspond to the colors of the cells in the grid so that the user can easily 
see which line corresponds to what cell. When the lines reach the end of the x-axis, the x-axis updates 
(for example 0-100 to 100-200) and the lines start from the left side of the graph to allow the user to continue 
viewing the distribution of each type of cell.

I think this code design is done well. I implemented this feature in its own class since the feature is not too 
comprehensive, and I made methods that initialize and update the line chart (as those are really the only two necessary). 
I create an instance of this object in another view class to display the chart to the user while calling my update
method in step. This specific feature was not large enough to need to create any hierarchies. 

The code does not have many dependencies. The chart just needs to be able to access the grid data and the state 
map so that it can count each type of cell and also set the colors and names accordingly. One link to a commit it:

[Link to Commit](https://coursework.cs.duke.edu/compsci307_2019spring/simulation_team03/commit/6f9a9048ab11deaf53550db0e21cea41225c9677)

This was the commit I made when I finished a rough version of the graph. It was not modular, did not set colors based
on the colors in the grid, and it could only handle 3 different types of cells at a time. This commit represents primary 
development since it was the rough design of the graph. 

### Alternate Designs

As I mentioned above, one thing we tried to figure out was how to alter our hierarchy for our grids to include shapes. We were unable
to come up with something though, because that would've resulted in more specific grids like SquareSegregationGrid and
HexagonSegregationGrid. This would've led to duplicate code. We ended up extending an abstract grid class and implementing
a grid interface. Although the interface doesn't add THAT much functionality, it helps with polymorphism and could be more useful
if we were to keep implementing more features. I actually prefer the way we did it because the hierarchy is simpler and easier
to understand. Also there are less classes and, of course, there is no duplicate code which is critical. There might be another
way to structure the hierarchy given the way we have it right now, but I think the way we did it is very modular and is also easy to understand.

Another design decision we made was to include all the functionality of stuff in our grids. The cells could've been responsible
for updating themselves, and we could have extended different types of cells rather than different types of grids. From the start, 
we could've decided to put the functionality inside of cells instead of grids. I honestly don't think it makes much of a difference. 
I think a pro is that making the grids have the functionality is just more intutive. The cell should only know basic stuff about itself, 
and the grid should be responsible for generating new versions of itself. Arguements could be made on both sides though, so I 
don't think it's a big deal.


