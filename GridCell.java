/*
 * Filename: GridCell.java
 * Author: Matias Lin
 * Date: 12/19/2018
 */

import objectdraw.*;
import java.awt.Color;
import java.awt.event.*;

/**
 * Holds the code for the Grid Cells.
 */
public class GridCell implements MouseListener {
  /* Constants for GridCell.java */
  // Reference to The Game constants
  private PathFinder pathFinder; 

  // Current Row
  private int rowIndex;

  // Current Column
  private int colsIndex;

  // Canvas
  private DrawingCanvas canvas;

  // FilledRect
  private FilledRect gridCell;

  // Reference to the Grid
  private Grid grid;

  // Destination - true if it is
  private boolean isDestination;
  // Home = true if it is
  private boolean isHome;
  // Barrier - true if it is
  private boolean isBarrier;

  // Number Of Destination and Home
  private int numberOfHome;
  private int numberOfDestination;

  /* Constants for the A* Path Finder Algorithm */
  private int gValue; // Distance from cell to home
  private int hValue; // Distance from cell to destination
  private int fValue; // The sum of the G and H Values
  private int barrierPlus;

  // Array of Open Cells - Shortest Path
  private GridCell[] open;

  /**
   *  Creates the GridCell.
   *
   *  @param rowIndex row where the GridCell should be placed
   *  @param colsIndex cols where the GridCell should be placed
   *  @param startX the X position on the canvas where the cell will be drawn
   *  @param startY the Y position on the canvas where the cell will be drawn
   *  @param canvas the canvas on which the GridCell will be drawn
   *  @param pathFinder the reference to the PathFinder.java
   *  @param grid the reference to the Grid
   */
  public GridCell( int rowIndex, int colsIndex, double startX, double startY,
                   DrawingCanvas canvas, PathFinder pathFinder, Grid grid ) {
    // Draw the grid cells
    gridCell = new FilledRect( startX, startY, PATHConstants.GRID_CELL_SIZE,
                               PATHConstants.GRID_CELL_SIZE, canvas );

    // Setting the color of the grid cells
    gridCell.setColor( PATHConstants.BACKGROUND_COLOR );

    // Updating the row and column and row
    this.rowIndex = rowIndex;
    this.colsIndex = colsIndex;

    // Updating canvas
    this.canvas = canvas;

    // Updating the grid
    this.grid = grid;

    // Updating path finder
    this.pathFinder = pathFinder;
  }

  /**
   * Setting the color of the path.
   *
   * @param color the color of the right way
   */
  public void setColorPath( Color color ) {
    gridCell.setColor( color );
  }

  /**
   * Setting the value of the barrier plus.
   */
  public void setBarrierPlus() {
    barrierPlus = PATHConstants.BARRIER_PLUS;
  }

  /**
   *  Setting the G, H and F Values.
   *
   *  @param rowIndexHome the row index of the home cell
   *  @param colsIndexHome the column index of the home cell
   *  @param rowIndexDestination the row index of the destination cell
   *  @param colsIndexDestination the cols index of the destination cell
   */
  public void setGHFValues( int rowIndexHome, int colsIndexHome,
                            int rowIndexDestination, int colsIndexDestination )
  {
    /* Setting the G Value */
    // Conditions
    int result1G = Math.abs( this.rowIndex - this.colsIndex );
    int result2G = Math.abs( rowIndexHome - colsIndexHome );
    int result3G = Math.abs( this.rowIndex - rowIndexHome );
    int result4G = Math.abs( this.colsIndex - colsIndexHome );
    int result5G = this.rowIndex + this.colsIndex;
    int result6G = rowIndexHome + colsIndexHome;
    int result7Ga = Math.abs( result1G + result2G );
    int result7Gb = Math.abs( result1G - result2G );
    int result8G = Math.abs( result5G - result6G );
    boolean conditionG = result3G == result4G;
  
    // Directly diagonal path
    if ( (result1G == result2G || result5G == result6G) && conditionG ) {
      //System.out.println( "This is result3G: " + result3G );
      this.gValue = PATHConstants.DIAGONAL * result3G;
      // Directly straight path - across
    } else if ( this.rowIndex == rowIndexHome ) {
      //TODO REMOVE
      //System.out.println("STRAIGHT1 G");
        this.gValue = PATHConstants.STRAIGHT * result4G;
      // Directly straight path - down
    } else if ( this.colsIndex == colsIndexHome ) {
      //TODO REMOVE
      //System.out.println("STRAIGHT2 G");
        this.gValue = PATHConstants.STRAIGHT * result3G;
      // Not direclty across or diagonal
    } else {
        // Vertical Axis
        if ( result3G > result4G ) {
          // The column index is bigger than the home column index
          if ( this.colsIndex > colsIndexHome ) {
            // Rows and columns are bigger than home indexes
            if ( this.rowIndex > rowIndexHome ) {
              if ( rowIndexHome >= colsIndexHome ) {
        //TODO REMOVE
        //System.out.println("Condition 1 G a");
                // Calculating the gValue
                int diagonal = result4G * PATHConstants.DIAGONAL;
                int straight = result7Gb * PATHConstants.STRAIGHT;
                this.gValue = diagonal + straight;
              } else {
        //TODO REMOVE
        //System.out.println("Condition 1 G b");
                // Calculating the gValue
                int diagonal = result4G * PATHConstants.DIAGONAL;
                int straight = result7Ga * PATHConstants.STRAIGHT;
                this.gValue = diagonal + straight;
              }
              // Row index is smaller than home row index
            } else {
      //TODO REMOVE
      //System.out.println("Condition 2 G");
               // Calculating the gValue
               int diagonal = result4G * PATHConstants.DIAGONAL;
               int straight = result8G * PATHConstants.STRAIGHT;
               this.gValue = diagonal + straight;
            }
            // If the column index is smaller than the home column index
          } else {
              // The rows index is bigger than the home row index
              if ( this.rowIndex > rowIndexHome ) {
      //TODO REMOVE
      //System.out.println("Condition 3 G");
                // Calculating the gValue
                int diagonal = result4G * PATHConstants.DIAGONAL;
                int straight = result8G * PATHConstants.STRAIGHT;
                this.gValue = diagonal + straight;
                // The row index is smaller than the home row Index
              } else {
                  if ( rowIndexHome >= colsIndexHome ) {
        //TODO REMOVE
        //System.out.println("Condition 4 G a");
                    // Calculating the gValue
                    int diagonal = result4G * PATHConstants.DIAGONAL;
                    int straight = result7Ga * PATHConstants.STRAIGHT;
                    this.gValue = diagonal + straight;
                  } else {
          //TODO REMOVE
          //System.out.println("Condition 4 G b");
                      // Calculating the gValue
                      int diagonal = result4G * PATHConstants.DIAGONAL;
                      int straight = result7Gb * PATHConstants.STRAIGHT;
                      this.gValue = diagonal + straight;
                  }
              }
          }
          // result3G < result4G
        } else {
          // The row is bigger than the home row index
          if ( this.rowIndex > rowIndexHome ) {
            // Rows and columns are bigger than home index
            if ( this.colsIndex > colsIndexHome ) {
              if ( rowIndexHome >= colsIndexHome ) {
          //TODO REMOVE
          //System.out.println("Condition 5 G a");
                // Calculating the gValue
                int diagonal = result3G * PATHConstants.DIAGONAL;
                int straight = result7Ga * PATHConstants.STRAIGHT;
                this.gValue = diagonal + straight;
                // If home row index is smaller than home cols index
              } else { 
          //TODO REMOVE
          //System.out.println("Condition 5 G b");
                int diagonal = result3G * PATHConstants.DIAGONAL;
                int straight = result7Gb * PATHConstants.STRAIGHT;
                this.gValue = diagonal + straight;
              }
              // Cols index is smaller than home cols index
            } else {
        //TODO REMOVE
        //System.out.println("Condition 6 G");
              // Calculating the gValue
               int diagonal = result3G * PATHConstants.DIAGONAL;
               int straight = result8G * PATHConstants.STRAIGHT;
               this.gValue = diagonal + straight;
            }
            // If the row is smaller than the home row index
          } else {
              // The cols index is bigger than the home cols index
              if ( this.colsIndex > colsIndexHome ) {
        //TODO REMOVE
        //System.out.println("Condition 7 G");
                // Calculating the gValue
                int diagonal = result3G * PATHConstants.DIAGONAL;
                int straight = result8G * PATHConstants.STRAIGHT;
                this.gValue = diagonal + straight;
                // The rows index is smaller than the home row Index
              } else {
                if ( rowIndexHome >= colsIndexHome ) {
        //TODO REMOVE
        //System.out.println("Condition 8 G a");
                  // Calculating the gValue
                  int diagonal = result3G * PATHConstants.DIAGONAL;
                  int straight = result7Gb * PATHConstants.STRAIGHT;
                  this.gValue = diagonal + straight;
                } else {
          //TODO REMOVE
          //System.out.println("Condition 8 G b");
                    // Calculating the gValue
                    int diagonal = result3G * PATHConstants.DIAGONAL;
                    int straight = result7Ga * PATHConstants.STRAIGHT;
                    this.gValue = diagonal + straight;
                }
              }
          }
        }
    }
    
    /* Setting the H Value */
    // Conditions
    int result1H = Math.abs( this.rowIndex - this.colsIndex );
    int result2H = Math.abs( rowIndexDestination - colsIndexDestination );
    int result3H = Math.abs( this.rowIndex - rowIndexDestination );
    int result4H = Math.abs( this.colsIndex - colsIndexDestination );
    int result5H = this.rowIndex + this.colsIndex;
    int result6H = rowIndexDestination + colsIndexDestination;
    int result7Ha = Math.abs( result1H + result2H );
    int result7Hb = Math.abs( result1H - result2H );
    int result8H = Math.abs( result5H - result6H );
    boolean conditionH = result3H == result4H;
  
    // Directly diagonal path
    if ( (result1H == result2H || result5H == result6H) && conditionH ) {
      //TODO REMOVE
      //System.out.println("DIAGONAL H");
      this.hValue = PATHConstants.DIAGONAL * result3H;
      //System.out.println("This is hValue up to now: " + this.hValue );
      // Directly straight path
    } else if ( this.rowIndex == rowIndexDestination ) {
      //TODO REMOVE
      //System.out.println("STRAIGHT1 H");
        this.hValue = PATHConstants.STRAIGHT * result4H;
      // Directly straight path
    } else if ( this.colsIndex == colsIndexDestination ) {
      //TODO REMOVE
      //System.out.println("STRAIGHT2 H");
        this.hValue = PATHConstants.STRAIGHT * result3H;
    } else {
        // Vertical Axis
        if ( result3H > result4H ) {
          // The column index is bigger than the destination column index
          if ( this.colsIndex > colsIndexDestination ) {
            // Rows and columns are bigger than destination indexes
            if ( this.rowIndex > rowIndexDestination ) {
              if ( rowIndexDestination >= colsIndexDestination ) {
        //TODO REMOVE
        //System.out.println("Condition 1 H a");
                // Calculating the hValue
                int diagonal = result4H * PATHConstants.DIAGONAL;
                int straight = result7Hb * PATHConstants.STRAIGHT;
                this.hValue = diagonal + straight;
              } else {
        //TODO REMOVE
        //System.out.println("Condition 1 H b");
                // Calculating the hValue
                int diagonal = result4H * PATHConstants.DIAGONAL;
                int straight = result7Ha * PATHConstants.STRAIGHT;
                //System.out.println( "7Ha: " + result7Ha );
                this.hValue = diagonal + straight;
              }
              // Row index is smaller than destination row index
            } else {
      //TODO REMOVE
      //System.out.println("Condition 2 H");
               // Calculating the hValue
               int diagonal = result4H * PATHConstants.DIAGONAL;
               int straight = result8H * PATHConstants.STRAIGHT;
               this.hValue = diagonal + straight;
            }
            // If the column index is smaller than the destination column index
          } else {
              // The rows index is bigger than the destination row index
              if ( this.rowIndex > rowIndexDestination ) {
      //TODO REMOVE
      //System.out.println("Condition 3 H");
                // Calculating the hValue
                int diagonal = result4H * PATHConstants.DIAGONAL;
                int straight = result8H * PATHConstants.STRAIGHT;
                this.hValue = diagonal + straight;
                // The row index is smaller than the destination row Index
              } else {
                  if ( rowIndexDestination >= colsIndexDestination ) {
        //TODO REMOVE
        //System.out.println("Condition 4 H a");
                    // Calculating the hValue
                    int diagonal = result4H * PATHConstants.DIAGONAL;
                    int straight = result7Ha * PATHConstants.STRAIGHT;
                    this.hValue = diagonal + straight;
                  } else {
          //TODO REMOVE
          //System.out.println("Condition 4 H b");
                      // Calculating the hValue
                      int diagonal = result4H * PATHConstants.DIAGONAL;
                      int straight = result7Ha * PATHConstants.STRAIGHT;
                      this.hValue = diagonal + straight;
                  }
              }
          }
          // result3H < result4H
        } else {
            // The row is bigger than the destination row index
            if ( this.rowIndex > rowIndexDestination ) {
              // Rows and columns are bigger than destination index
              if ( this.colsIndex > colsIndexDestination ) {
                if ( rowIndexDestination >= colsIndexDestination ) {
          //TODO REMOVE
          //System.out.println("Condition 5 H a");
                  // Calculating the hValue
                  int diagonal = result3H * PATHConstants.DIAGONAL;
                  int straight = result7Ha * PATHConstants.STRAIGHT;
                  this.hValue = diagonal + straight;
                  // If the destination row index is smaller than the cols index
                } else {
          //TODO REMOVE
          //System.out.println("Condition 5 H b");
                  // Calculating the hValue
                  int diagonal = result3H * PATHConstants.DIAGONAL;
                  int straight = result7Hb * PATHConstants.STRAIGHT;
                  this.hValue = diagonal + straight;
                }
                // Cols index is smaller than destination cols index
              } else {
        //TODO REMOVE
        //System.out.println("Condition 6 H");
                // Calculating the hValue
                 int diagonal = result3H * PATHConstants.DIAGONAL;
                 int straight = result8H * PATHConstants.STRAIGHT;
                 //System.out.println( "STRAIGHT: " + straight );
                 this.hValue = diagonal + straight;
              }
              // If the row is smaller than the destination row index
            } else {
                // The cols index is bigger than the destination cols index
                if ( this.colsIndex > colsIndexDestination ) {
        //TODO REMOVE
        //System.out.println("Condition 7 H");
                  // Calculating the hValue
                  int diagonal = result3H * PATHConstants.DIAGONAL;
                  int straight = result8H * PATHConstants.STRAIGHT;
                  this.hValue = diagonal + straight;
                  // The cols index is smaller than the destination cols index
                } else {
                    if ( rowIndexDestination >= colsIndexDestination ) {
          //TODO REMOVE
          //System.out.println("Condition 8 H a");
                      // Calculating the hValue
                      int diagonal = result3H * PATHConstants.DIAGONAL;
                      int straight = result7Hb * PATHConstants.STRAIGHT;
                      this.hValue = diagonal + straight;
                    } else {
            //TODO REMOVE
            //System.out.println("Condition 8 H b");
                        // Calculating the hValue
                        int diagonal = result3H * PATHConstants.DIAGONAL;
                        int straight = result7Ha * PATHConstants.STRAIGHT;
                        this.hValue = diagonal + straight;
                    }
                }
            }
        }
    }

    /* Setting the F Value */
    this.setFValue( ( this.gValue + this.hValue ) );

    // TODO REMOVE
    //System.out.println( "R: " + this.rowIndex + " C: " + this.colsIndex );
    //System.out.println( "GValue: " + this.gValue + " HValue: " + this.hValue + " FValue: " + getFValue() );
  }

  /**
   * Sets the F Value. 
   *
   * @param value the f value
   */
  public void setFValue( int value ) {
    this.fValue = value + this.barrierPlus;
  }

  /**
   * Returns the F Value.
   *
   * @return the F value of the grid cell
   */
  public int getFValue() {
    return this.fValue;
  }

  /**
   * Returns the H Value.
   *
   * @return the H value of the grid cell
   */
  public int getHValue() {
    return this.hValue;
  }

  /**
   * Returns the G Value.
   *
   * @return the G value of the grid cell
   */
  public int getGValue() {
    return this.gValue;
  }

  /**
   * Returns the current Row Index.
   *
   * @return the current row Index
   */
  public int getCurrentRowIndex() {
    return this.rowIndex;
  }

  /**
   * Returns the current Cols Index.
   *
   * @return the current cols Index
   */
  public int getCurrentColsIndex() {
    return this.colsIndex;
  }

  /**
   * Determine if the grid cell is the destination
   *
   * @return true if the grid cell is the destination
   */
  public boolean isDestination() {
    return this.isDestination;
  }

  /**
   * Determine if the grid cell is the home.
   *
   * @return true if the grid cell is the home 
   */
  public boolean isHome() {
    return this.isHome;
  }

  /**
   * Determine if the grid cell is a barrier.
   *
   * @return true if the grid cell is a barrier 
   */
  public boolean isBarrier() {
    return this.isBarrier;
  }

  /**
   * Other methods required for implementing MouseListener
   */
  public void mousePressed( MouseEvent evt ) {}
  public void mouseReleased( MouseEvent evt ) {}
  public void mouseExited( MouseEvent evt ) {}
  public void mouseEntered( MouseEvent evt ) {}

  /**
   * Sets the selected cell to destination or home.
   *
   * @param evt the point that fired the event
   */
  public void mouseClicked( MouseEvent evt ) {
    // Converting the evt into a location
    int xxx = evt.getX();
    int yyy = evt.getY();
    Location location = new Location( xxx, yyy );
    
    //Getting the number of destination and home cells
    numberOfDestination = grid.getNumberOfDestination();
    numberOfHome = grid.getNumberOfHome();

    // If the cursor is on the cell
    if ( gridCell.contains( location ) ) {
      if ( pathFinder.isDestination() && numberOfDestination == 0 ) {
        // The Cell is a Destination
        setDestination( true );
        gridCell.setColor( PATHConstants.DESTINATION_COLOR );
        // The Cell is Home
      } else if ( pathFinder.isHome() && numberOfHome == 0 ) {
          setHome( true );
          gridCell.setColor( PATHConstants.HOME_COLOR );
          // The Cell is Barrier
      } else if ( pathFinder.isBarrier() ) {
          setBarrier( true );
          gridCell.setColor( PATHConstants.BARRIER_COLOR );
      }
    }
  }

  /**
   * Set the grid cell as destination.
   *
   * @param state true if it is a destination 
   */
  public void setDestination( boolean state ) {
    this.isDestination = state;
    this.isHome = !state;
    this.isBarrier = !state;
  }

  /**
   * Set the grid cell as home.
   *
   * @param state true if it is a home 
   */
  public void setHome( boolean state ) {
    this.isDestination = !state;
    this.isHome = state;
    this.isBarrier = !state;
  }

  /**
   * Set the grid cell as barrier.
   *
   * @param state true if it is a barrier 
   */
  public void setBarrier( boolean state ) {
    this.isDestination = !state;
    this.isHome = !state;
    this.isBarrier = state;
  }
}
