/*
 * Filename: Arrived.java
 * Author: Matias Lin
 * Date: 12/21/2018
 */

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Holds the codes for the path finder algorithm.
 */
public class Arrived implements ActionListener {
  /* Constants for Arrived.java */
  // Grid
  private Grid grid;

  // JButtons - Run
  private JButton run;

  // Index of the home Grid Cell
  private int homeRowIndex;
  private int homeColsIndex;

  // Index of the destination Grid Cell
  private int destinationRowIndex;
  private int destinationColsIndex;

  // Index of the barriers Grid Cell
  private int[] barrierRowIndex;
  private int[] barrierColsIndex;

  /* Constants for A* Path Finder Algorithm */
  // Array List of Open Cells - The shortest Path
  private ArrayList<GridCell> path;
  private ArrayList<GridCell> shortestPath;
  
  // Iterator stopper
  private int stopper;

  /**
   * Initializes the path finder algorithm.
   *
   * @param run the reference to the JButton
   * @param grid the reference to the Grid
   */
  public Arrived( JButton run, Grid grid ) {
    // Updating the run button
    setRun( run );

    // Updating the grid
    setGrid( grid );

    // Creating the array lists
    path = new ArrayList<GridCell>();
    shortestPath = new ArrayList<GridCell>();
  }

  /**
   * Updates the reference of the JButton.
   *
   * @param run the reference to the JButton
   */
  public void setRun( JButton run ) {
    this.run = run;
  }

  /**
   * Updates the reference to the grid.
   *
   * @param grid the reference to the Grid
   */
  public void setGrid( Grid grid ) {
    this.grid = grid;
  }

  /**
   * Setting the home row index.
   *
   * @param index the row index of the grid cell
   */
  public void setHomeRowIndex( int index ) {
    this.homeRowIndex = index;
  }

  /**
   * Setting the home column index.
   *
   * @param index the cols index of the grid cell
   */
  public void setHomeColsIndex( int index ) {
    this.homeColsIndex = index;
  }

  /**
   * Setting the destination row index.
   *
   * @param index the row index of the grid cell
   */
  public void setDestinationRowIndex( int index ) {
    this.destinationRowIndex = index;
  }

  /**
   * Setting the destination column index.
   *
   * @param index the cols index of the grid cell
   */
  public void setDestinationColsIndex( int index ) {
    this.destinationColsIndex = index;
  }

  /**
   * Setting the barrier row index.
   *
   * @param i the index of the array
   * @param index the row index of the grid cell
   */
  public void setBarrierRowIndex( int i, int index ) {
    this.barrierRowIndex[i] = index;
  }

  /**
   * Setting the barrier column index.
   *
   * @param i the index of the array
   * @param index the cols index of the grid cell
   */
  public void setBarrierColsIndex( int i, int index ) {
    this.barrierColsIndex[i] = index;
  }

  /**
   * Disables the button and starts the algorithm.
   *
   * @param evt Event fired when the run button is pressed
   */
  public void actionPerformed( ActionEvent evt ) {
    // Disabling the button run
    run.setEnabled( false );

    try {
      // Find the index of the home
      setHomeRowIndex( (grid.getHome()).getCurrentRowIndex() );
      setHomeColsIndex( (grid.getHome()).getCurrentColsIndex() );
    } catch ( NullPointerException e ) {
        System.out.println( "No Home Selected" );
        // Enable the button
    }

    try {
      // Find the index of the destination
      setDestinationRowIndex( (grid.getDestination()).getCurrentRowIndex() );
      setDestinationColsIndex( (grid.getDestination()).getCurrentColsIndex() );
    } catch ( NullPointerException e ) {
        System.out.println( "No Destination Selected" );
        // Enable the destination button
    }

    try {
      //Creating the arrays
      barrierRowIndex = new int[ grid.getNumberOfBarriers() ];
      barrierColsIndex = new int[ grid.getNumberOfBarriers() ];

      // Find the index of the barriers - For future purposes.
      for ( int i = 0; i < grid.getNumberOfBarriers(); i++ ) {
        setBarrierRowIndex(i, ((grid.getBarrier()))[i].getCurrentRowIndex());
        setBarrierColsIndex(i, ((grid.getBarrier()))[i].getCurrentColsIndex());
      }

    } catch ( NullPointerException e ) {
        System.out.println( "The reference of the barrier is null" );
    }

    // Adding the Home Grid Cell to the path array list
    path.add( grid.getHome() );

    // GridCell - parameter for the method calling
    GridCell current = grid.getHome();

    // Current row and column index
    int currentRow = current.getCurrentRowIndex();
    int currentCols = current.getCurrentColsIndex();

    // Reseting the infinite iterator counter
    stopper = 0;

    // Looping until we find the best path 
    while ( (currentRow != destinationRowIndex ) ||
            (currentCols != destinationColsIndex) ) {
      /* Looking for G, H, F Values for the surrounding cells */
      calculateGHFValues( currentRow, currentCols,
                          destinationRowIndex, destinationColsIndex );
      
     // Getting the last added grid cell to the array list and Updating vaues
      currentRow = (path.get(path.size()-1)).getCurrentRowIndex();
      currentCols = (path.get(path.size()-1)).getCurrentColsIndex();
    } 

    /* Find the correct number of cells */
    //finishIt();

    // Adding the destination to the shortestPath list
    //shortestPath.add( grid.getDestination() );

    
    // Setting the color of the right path
    /*
    for ( GridCell item : shortestPath ) {
      item.setColorPath( PATHConstants.DESTINATION_COLOR );
    }
    */

    // Prinnting how many moves it took - Future replace path with shortestPath
    System.out.printf( PATHConstants.SHORTEST_PATH, 
                       Integer.toString( path.size() - 1 ) );
  }

  /**
   * Calculates the G, H, F values.
   *
   * @param centerRow the row index of the home cell
   * @param centerCols the column index of the home cell
   * @param destinationRowIndex the row index of the destination cell
   * @param destinationColsIndex the column index of the destination cell
   */
  public void calculateGHFValues( int centerRow, int centerCols,
                                  int destinationRowIndex, 
                                  int destinationColsIndex ) {
    // Index modifiers
    int rowIndex = -1;
    int colsIndex = -1;

    // Array with the F values
    int[] fArray = new int[PATHConstants.NUMBER_OF_SURROUNDING_CELLS];

    // The smallest F Value
    int smallestFValue = Integer.MAX_VALUE;

    // The center Cell
    GridCell center = null;
    GridCell theWay = null;
    
    // Increase the stopper count
    stopper++;

    for ( int i = 0; i < PATHConstants.NUMBER_OF_SURROUNDING_CELLS; i++ ) {
      // The surrounding cells indexes
      int rowIndexCell = centerRow + rowIndex;
      int colsIndexCell = centerCols + colsIndex;

      try {
        center = 
          ( grid.getGridCellArray() )[rowIndexCell][colsIndexCell];
        if ( noGoingBack( rowIndexCell, colsIndexCell ) ) {
          // Setting the G, H, F values
          center.setGHFValues( homeRowIndex, homeColsIndex, destinationRowIndex,
                               destinationColsIndex );
          // If the F Value is smaller than the already stored F Value
          if ( center.getFValue() < smallestFValue ) {
            // Updating the smallestFValue
            smallestFValue = center.getFValue();
            // Updating the way
            theWay = center;
          // When having the same FValue resort to the smallest HValue
          } else if ( center.getFValue() == smallestFValue ) {
              if ( center.getHValue() < theWay.getHValue() ) {
                smallestFValue = center.getFValue();
                theWay = center;
                // When the G and H values are the same
              } else if ( center.getHValue() == theWay.getHValue() ) {
                  int centerSum = ( Math.abs(center.getCurrentRowIndex() - 
                                   destinationRowIndex) + Math.abs(
                                   center.getCurrentColsIndex() -
                                   destinationColsIndex) );
                  int theWaySum = ( Math.abs(theWay.getCurrentRowIndex() - 
                                   destinationRowIndex) + Math.abs(
                                   theWay.getCurrentColsIndex() -
                                   destinationColsIndex) );
                  // Choose the one closer to the destination
                  if ( centerSum < theWaySum ) {
                    smallestFValue = center.getFValue();
                    theWay = center;
                  }
              }
          }
        }
        // No Surrounding cells
      } catch ( IndexOutOfBoundsException e ) {
      } catch ( NullPointerException e ) {}

      colsIndex++;
      if ( i % PATHConstants.MODULUS_CONSTANT == 0 && ( i != 0 && i != 
           PATHConstants.LOOP_EXCEPTION ) ) {
        colsIndex = -1;
        rowIndex++;
      } 
      
      // Correcting for second row ( only two Grid Cells )
      if ( rowIndex == 0 && colsIndex == 0 ) {
        colsIndex = 1;
      }
    }

    // Storing the Grid Cell with the smallest F Value
    if ( theWay != null ) {
      path.add( theWay );
      theWay.setColorPath( PATHConstants.PATH_COLOR );
    }

    // Ending the path finder if the program can't find the way
    if ( stopper > PATHConstants.STOPPER ) {
      System.out.println( PATHConstants.STOPPER_MSG );
      System.exit(1);
    }
  }

  /**
   * Evaluates thee shortest path given the path.
   */
  public void finishIt() {
    // Lowest value possible
    int maxGValue = PATHConstants.STRAIGHT;

    // Looking for the cells within path with the least h value
    for ( int i = 1; i < path.size(); i++ ) {
      GridCell item = path.get(i);
      int value = item.getGValue();
      
      // Storing the right cells
      if ( value > maxGValue ) {

        boolean chosenOne = checkNeighbors( i );
        
        if ( chosenOne ) {
          // Add it to the shortestPath ArrayList
          shortestPath.add( item );
        }

        // Update the biggest G Value
        maxGValue = value;

      } else if ( value == maxGValue ) {
         // Making sure the cell has a possible neighbor cell
         boolean chosenOne = checkNeighbors( i );
         
         if ( chosenOne ) {
           shortestPath.add( item );
         }
      }
    }
  }

  /**
   * Checks if the following cell has a smaller hValue.
   *
   * @param i the index of the grid cell within the path array List
   *
   * @return true if the next h Value is smallest than the current h Value 
   */
  public boolean checkNeighbors( int i ) { 
    // The grid Cell to return
    boolean chosen = false;

    // Getting the next hValue
    int currentHValue = (path.get( i )).getHValue();

    try {
      int nextHValue = (path.get( i + 1 )).getHValue();

      if ( nextHValue < currentHValue  ) {
        chosen = true;
      }
    } catch ( IndexOutOfBoundsException e ) {}

    return chosen;
  }

  /**
   * Checks if the boundaries are part of the open array list.
   *
   * @param rowIndexCell the row of the boundary cell
   * @param colsIndexCel the column of the boundary cell
   *
   * @return false if it's already on the list
   */
  public boolean noGoingBack( int rowIndexCell, int colsIndexCell ) {
    // No Back Tracking
    boolean result = true;

    for ( GridCell cell : path ) {
      if ( cell.getCurrentRowIndex() == rowIndexCell && 
           cell.getCurrentColsIndex() == colsIndexCell ) {
        result = false;
      }
    }

    return result;
  }
}
