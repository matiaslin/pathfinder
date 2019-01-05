Filename: README.md
Author: Matias Lin
Date: 01/05/2019

Program Description - PathFinder.java:

The purpose of this program is to find the shortest path from point A to B.
The user will be able to specify the size of the grid they want to experiment on
by inputing the number of rows and then the number of columns separated by a
space, like so: "15 15" (by default the grid created is 10 by 10 ). Once the 
program has been executed, the user will be able to add a home to the grid by
pressing on the button that says "Home" and then selecting the grid they want on
the canvas. Notice that only one "Home" can be selected. Likewise, the user will
be able to select the "Destination" by selecting the othere button and pressing
on a cell on the canvas. Once both the "Home" and "Destination" has been
selected, the user can press the button "Run" that will start the path finding
algorithm.
The program will display the shortest path by painting certain cells with the
color green and the number of cells will be shown on the terminal.


Future work:

As one can see, there's a button on the far bottom right that says "Barrier".
Due to a lack of time, this feature wasn't included in the current version
(v 1.0). I hope in the future to include the possibility of adding barriers
and making the algorithm even more efficient.
