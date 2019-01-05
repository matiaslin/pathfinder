/*
 * Filename: PATHConstants.java
 * Author: Matias Lin
 * Date: 12/18/2018
 */

import java.awt.Color;

/**
 * Contains the constants for PathFinder.java
 */
public class PATHConstants {
  //***************************************
  // Quantitative Constants for PathFinder
  //***************************************
  
  // Size of the window
  public static final int WINDOW_WIDTH = 600;
  public static final int WINDOW_HEIGHT = 600;

  // Size of the cells
  public static final int GRID_CELL_SIZE = 40;

  // Maximum number of arguments
  public static final int MAX_NUM_ARGS = 2;

  // Default number of columns and rows
  public static final int DEFAULT_ROWS = 10;
  public static final int DEFAULT_COLS = 10;

  // Rows and Columns for the GridLayout, lower JPanel
  public static final int ROWS_LAYOUT = 2;
  public static final int COLS_LAYOUT = 0;

  // Constants to find the G, H and F values
  public static final int NUMBER_OF_SURROUNDING_CELLS = 8;
  public static final int MODULUS_CONSTANT = 2;
  public static final int LOOP_EXCEPTION = 6;

  // Value for the diagonal path
  public static final int DIAGONAL = 14;

  // Value for the straight path
  public static final int STRAIGHT = 10;
  
  // Value for the barriers
  public static final int BARRIER_PLUS = 100;

  // Valie for the iterator stopper
  public static final int STOPPER = 1000;
  
  //********************************
  // Color Constants for PathFinder
  //********************************
  
  public static final Color BACKGROUND_COLOR = new Color( 32, 34, 37 );
  public static final Color GRID_LINE_COLOR = new Color( 170, 171, 173 );
  public static final Color DESTINATION_COLOR = new Color( 50, 205, 50 );
  public static final Color HOME_COLOR = new Color( 70, 130, 180 );
  public static final Color BARRIER_COLOR = new Color( 192, 192, 192 );
  public static final Color PATH_COLOR = new Color( 255, 124, 0 );

  //**********************************
  // Strings Constants for PathFinder
  //**********************************

  // Usage message
  public static final String USG_MESSAGE = "\nYou can specify the rows and" + 
                                           " columns, separated by a space\n";
  // Unrecognizable Keyword error message
  public static final String ERR_KEYWORD = "%s: Unrecognizable Keyword";
  // Unrecognizable rows error message
  public static final String ERR_ROWS = "%s: Unrecognizable Rows\n";
  // Unrecognizable columns error message
  public static final String ERR_COLS = "%s: Unrecognizable Columns";
  // Wrong number of arguments error message
  public static final String NUM_ARGS_ERR = "Wrong number of arguments, please"+
                                            " check the usage message";

  // Text for the selection label
  public static final String SELECTION_TXT = "Selected: ";
  public static final String DEFAULT_SELECTION_TXT = "NONE";

  // Text for the run label
  public static final String RUN_PROGRAM_TXT = "Press to find the closest path"
                                               + " -->";

  // Texts for the buttons
  public static final String DESTINATION = "Destination";
  public static final String HOME = "Home";
  public static final String BARRIER = "Barrier";
  public static final String RUN = "Run";

  // STOPPER message
  public static final String STOPPER_MSG = "Make sure there's a way to get " +
                                           "to the destination.";

  // Shortest Path
  public static final String SHORTEST_PATH = "The shortest path took %s moves";
}

