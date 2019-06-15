List as many design issues as you can in the method (using line numbers to identify the exact code) from large things like (potential) duplicated code or if statements that should be generalized through polymorphism, data structures, or resource files down to medium sized things like poor error handling or long lambdas methods to small things like consistent coding conventions or ignored assignment design requirements (like using Resources instead of magic values). For many of these methods, this should be a long list of issues!

-Long methods, many magic values and duplicated code.
-In AddmoreButtons method, this method is extremely long and does a lot of redundant things.
-In predatorpreygrid most of the updating is done in one method, the code could be more readable by splitting it up and keeping the code DRY
-SimulationViewer method itself has a lot of utility methods for creating various scenes or setup, which each could be made their own classes or branched out to other methods


Organize the list of issues based on things that could be fixed together based on a different design choice or using similar refactorings and prioritize these groups based on which would provide the most improvement to the overall code (not just this method).

- Perhaps we could use a shapegrid class for each type of shape (for ex, square, triangle, hexagon) to make the abstractshapegrid class less long, more readable, and more tailored toward one class
- Use less magic values by relying on the resources.properties file

Describe specific overall design changes or refactorings to fix the three most important issues you identified.

- Make more specific classes and improve hierarchy to reduce duplicate code, reduce the size of classes