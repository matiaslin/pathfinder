/*
 * Filename: Grid.java
 * Author: Matias lin
 * Date: 12/18/2018
 */

import objectdraw.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

/**
 *  Draws the grid for PathFinder.
 */
public class Grid implements MouseListener, ActionListener {

  // The width and height of the canvas
  private int width;
  private int height;

  // Columns and Rows
  private int rows;
  private int cols;

  // Canvas
  private DrawingCanvas canvas;

  // Array of GridCells
  private GridCell[][] gridCellArray;

  // Array of Barriers GridCell
  private GridCell[] gridBarrierArray;

  /**
   * Updates the constants.
   *
   * @param rows the number of rows
   * @param cols the number of columns
   * @param canvas the canvas on which the grid is going to be drawn on
   */
  public Grid( int rows, int cols, DrawingCanvas canvas ) {
    // Updating the width and height of the canvas
    width = canvas.getWidth();
    height = canvas.getHeight();

    // Creating the Array of GridCells
    gridCellArray = new GridCell[rows][cols];

    // Updating the cols and rows
    setRows( rows );
    setCols( cols );

    // Updating the canvas
    setCanvas( canvas );

    // Reference to the PathFinder.java
    PathFinder pathFinder = new PathFinder();

    // Drawing the whole grid
    draw( canvas, rows, cols, pathFinder );
  }

  /**
   * Draws the grid.
   *
   * @param canvas the canvas on which the grid is going to be draw
   * @param rows the row in the grid
   * @param cols the columns in the grid
   * @param pathFinder the reference to PathFinder
   */
  public void draw( DrawingCanvas canvas, int rows, int cols, PathFinder 
                    pathFinder ) {
    // Creating the background
    FilledRect background = new FilledRect( 0, 0, width, height, canvas );
    background.setColor( PATHConstants.BACKGROUND_COLOR );
    
    //Creating the cells
    double cellStartPointX = 0;
    double cellStartPointY = 0;

    for ( int i = 0; i < cols; i++ ) {
      for ( int o = 0; o < rows; o++ ) {
        // Adding the grid Cells
        GridCell gridCell = new GridCell( o, i, cellStartPointX, 
                                          cellStartPointY, canvas, pathFinder, 
                                          this );
        // Updating the Y values
        cellStartPointY += PATHConstants.GRID_CELL_SIZE;

        // Adding the GridCell into the array
        gridCellArray[o][i] = gridCell;

        // Adding the mouse listener
        canvas.addMouseListener( gridCell );
      }
      cellStartPointY = 0;
      //Updating the X values
      cellStartPointX += PATHConstants.GRID_CELL_SIZE;
    }

    // Drawing the horizontal lines
    double horizontalStartPointX = 0;
    double horizontalStartPointY = 0;
    double horizontalEndPointX = PATHConstants.GRID_CELL_SIZE * cols;
    double horizontalEndPointY = horizontalStartPointX;

    for ( int i = 0; i <= rows; i++ ) {
      // Creating the lines
      Line horizontal = new Line( horizontalStartPointX, horizontalStartPointY,
                                  horizontalEndPointX, horizontalEndPointY,
                                  canvas );
      horizontal.setColor( PATHConstants.GRID_LINE_COLOR );

      // Updating the coordinates
      horizontalStartPointY += PATHConstants.GRID_CELL_SIZE;
      horizontalEndPointY = horizontalStartPointY;
    }

    // Drawing the vertical lines
    double verticalStartPointX = 0;
    double verticalStartPointY = 0;
    double verticalEndPointY = PATHConstants.GRID_CELL_SIZE * rows;
    double verticalEndPointX = verticalStartPointX;

    for ( int i = 0; i <= cols; i++ ) {
      // Creating the lines
      Line vertical = new Line( verticalStartPointX, verticalStartPointY,
                                verticalEndPointX, verticalEndPointY,
                                canvas );
      vertical.setColor( PATHConstants.GRID_LINE_COLOR );

      // Updating the coordinates
      verticalStartPointX += PATHConstants.GRID_CELL_SIZE;
      verticalEndPointX = verticalStartPointX;
    }
  }

  /**
   * Gives access to the grid cell array.
   *
   * @return the grid cell array
   */
  public GridCell[][] getGridCellArray() {
    return this.gridCellArray;
  }

  /**
   * Find the grid cell that is a home.
   *
   * @return the Grid Cell of the home
   */
  public GridCell getHome() {
    GridCell homeCell = null;

    // Checking on every grid cell
    for ( int i = 0; i < cols; i++ ) {
      for ( int o = 0; o < rows; o++ ) {
        try {
          boolean temp = gridCellArray[i][o].isHome();
          
          // There's a home cell 
          if ( temp ) {
            homeCell = gridCellArray[i][o];
          }
        } catch ( NullPointerException e ) {}
      }
    }
    return homeCell;
  }

  /**
   * Find the grid cell that is a destination.
   *
   * @return the Grid Cell of the destination
   */
  public GridCell getDestination() {
    GridCell destinationCell = null;

    // Checking on every grid cell
    for ( int i = 0; i < cols; i++ ) {
      for ( int o = 0; o < rows; o++ ) {
        try {
          boolean temp = gridCellArray[i][o].isDestination();
          
          // There's a home cell 
          if ( temp ) {
            destinationCell = gridCellArray[i][o];
          }
        } catch ( NullPointerException e ) {} 
      }
    }
    return destinationCell;
  }

  /**
   * Get the number of destinations.
   *
   * @return the number of destination 
   */
  public int getNumberOfDestination() {
    // Counting the number of destination 
    int count = 0; 

    // Checking on every grid cell
    for ( int i = 0; i < cols; i++ ) {
      for ( int o = 0; o < rows; o++ ) {
        try {
          boolean temp = gridCellArray[i][o].isDestination();

          if ( temp ) {
            count++;
          }
        } catch ( NullPointerException e ) {}
      }
    }
    return count;
  }

  /**
   * Get the number of home.
   *
   * @return the number of home 
   */
  public int getNumberOfHome() {
    // Counting the number of home
    int count = 0; 

    // Checking on every grid cell
    for ( int i = 0; i < cols; i++ ) {
      for ( int o = 0; o < rows; o++ ) {
        try {
          boolean temp = gridCellArray[i][o].isHome();

          if ( temp ) {
            count++;
          }
        } catch ( NullPointerException e ) {}
      }
    }
    return count;
  }

  /**
   * Get the number of barriers.
   *
   * @return the number of barriers
   */
  public int getNumberOfBarriers() {
    // Counting the number of barriers
    int count = 0; 

    // Checking on every grid cell
    for ( int i = 0; i < cols; i++ ) {
      for ( int o = 0; o < rows; o++ ) {
        try {
          boolean temp = gridCellArray[i][o].isBarrier();

          if ( temp ) {
            // Setting those cells to null
            gridCellArray[i][o] = null;
            // Increasing the count of barriers
            count++;
          }
        } catch ( NullPointerException e ) {}
      }
    }
    return count;
  }

  /**
   * Find the grid cell that is a barrier.
   *
   * @return the array of Grid Cell barriers
   */
  public GridCell[] getBarrier() {
    int tmp = 0;

    // Checking on every grid cell
    for ( int i = 0; i < cols; i++ ) {
      for ( int o = 0; o < rows; o++ ) {
        try {
          boolean temp = gridCellArray[i][o].isBarrier();
          
          // There's a home cell - Adding it to the array of barrier cells
          if ( temp ) {
            gridBarrierArray[tmp] = gridCellArray[i][o];
            tmp++;
          } 
        } catch ( NullPointerException e ) {}
      }
    }
    return gridBarrierArray;
  }

  /**
   * Checks if there's a destination grid cell already.
   *
   * @return true if there's one
   */
  public boolean checkDestination() {
    boolean oneAlready = false;

    // Checking on every grid cell
    for ( int i = 0; i < cols; i++ ) {
      for ( int o = 0; o < rows; o++ ) {
        try {
          boolean temp = gridCellArray[i][o].isDestination();

          if ( temp ) {
            oneAlready = true;
          }
        } catch ( NullPointerException e ) {}
      }
    }
    return oneAlready;
  }

  /**
   * Checks if there's a home grid cell already.
   *
   * @return true if there's one
   */
  public boolean checkHome() {
    boolean oneAlready = false;

    // Checking on every grid cell
    for ( int i = 0; i < cols; i++ ) {
      for ( int o = 0; o < rows; o++ ) {
        try {
          boolean temp = gridCellArray[i][o].isHome();

          if ( temp ) {
            oneAlready = true;
          }
        } catch ( NullPointerException e ) {}
      }
    }
    return oneAlready;
  }

  /**
   * Set the number of rows.
   *
   * @param rows the number of rows
   */
  public void setRows( int rows ) {
    this.rows = rows;
  }

  /**
   * Set the number of cols.
   *
   * @param rows the number of cols
   */
  public void setCols( int cols ) {
    this.cols = cols;
  }

  /**
   * Set the canvas.
   *
   * @param canvas the Drawing canvas
   */
  public void setCanvas( DrawingCanvas canvas ) {
    this.canvas = canvas;
  }

  /**
   * Create the array of barriers.
   *
   * @param evt the action fired when the run button is pressed
   */
  public void actionPerformed( ActionEvent evt ) {
    gridBarrierArray = new GridCell[ this.getNumberOfBarriers() ];
  }
  
  /**
   * Methods required for implementing MouseListener
   */
   public void mousePressed( MouseEvent evt ) {}
   public void mouseReleased( MouseEvent evt ) {}
   public void mouseExited( MouseEvent evt ) {}
   public void mouseEntered( MouseEvent evt ) {}
   public void mouseClicked( MouseEvent evt ) {}
}
