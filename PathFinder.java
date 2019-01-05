/*
 * Filename: PathFinder.java
 * Author: Matias Lin
 * Date: 12/18/2018
 */

import objectdraw.*;
import Acme.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Random;

/**
 * Program that identifies the shortest path from point A to B.
 */
public class PathFinder extends WindowController {

  // Reference to Grid.java
  private Grid grid;

  // Only one destination and home allowed
  private static boolean onlyOneDestination;
  private static boolean onlyOneHome;

  // Rows and Columns
  private int rows;
  private int cols;

  // Constants for The Game
  private static boolean isDestination;
  private static boolean isHome;
  private static boolean isBarrier;

  // JPanels
  private JPanel upper = new JPanel();
  private JPanel lower = new JPanel();

  // JButtons
  private JButton destination = new JButton( PATHConstants.DESTINATION );
  private JButton home = new JButton( PATHConstants.HOME );
  private JButton barrier = new JButton( PATHConstants.BARRIER );
  private JButton run = new JButton( PATHConstants.RUN );

  // JLabels
  private JLabel selection = new JLabel( PATHConstants.SELECTION_TXT +
                                         PATHConstants.DEFAULT_SELECTION_TXT );
  private JLabel runProgram = new JLabel( PATHConstants.RUN_PROGRAM_TXT );

  /**
   *  Reference to PathFinder for GridCells
   */
  public PathFinder() {}

  /**
   * Updates the values of rows and cols.
   *
   * @param rows the number of rows
   * @param cols the number of cols
   */
  public PathFinder( int rows, int cols ) {
    this.rows = rows;
    this.cols = cols;
  }

  /**
   * Get the current value of destination.
   *
   * @return true if the button destination is being selected
   */ 
  public boolean isDestination() {
    return isDestination;
  }

  /**
   * Get the current value of home.
   *
   * @return true if the button home is being selected
   */ 
  public boolean isHome() {
    return isHome;
  }

  /**
   * Get the current value of barrier.
   *
   * @return true if the button barrier is being selected
   */ 
  public boolean isBarrier() {
    return isBarrier;
  }

  /**
   * Displays the usage message.
   */
  private static void usageMessage() {
    System.out.println( PATHConstants.USG_MESSAGE );
  }

  /**
   * Sets the destination to the state provided.
   * 
   * @param state true if it is selected 
   */
  private void setDestination( boolean state ) {
    isDestination = state;
  }

  /**
   * Sets the home to the state provided.
   * 
   * @param state true if it is selected 
   */
  private void setHome( boolean state ) {
    isHome = state;
  }

  /**
   * Sets the barrier to the state provided.
   * 
   * @param state true if it is selected 
   */
  private void setBarrier( boolean state ) {
    isBarrier = state;
  }

  /**
   * Sets the only ony destination boolean value.
   *
   * @param state true if there's already a destination cell
   */
  public void setOnlyOneDestination( boolean state ) {
    onlyOneDestination = state;
  }

  /**
   * Sets the only ony Home boolean value.
   *
   * @param state true if there's already a Home cell
   */
  public void setOnlyOneHome( boolean state ) {
    onlyOneHome = state;
  }

  /**
   * Creates the Grid.
   */
  public void startProgram() {
    // Other Time
    barrier.setEnabled( false );

    // Creates the grid
    grid = new Grid( rows, cols, canvas );
    
    // Adding the mouse listener to the grid
    canvas.addMouseListener( grid );

    // Path Finder algorithm
    Arrived arrived = new Arrived( run, grid );

    // Adding ActionListener Arrived.java
    run.addActionListener( arrived ); 

    // Adding ActionListener to the grid
    run.addActionListener( grid );
  }

  /**
   * Creates the interface.
   */
  public void begin() {
    // Upper JPanel
    upper.add( runProgram );
    upper.add( run );

    // Lower JPanel
    // Adding layout to lower panel
    lower.setLayout( new GridLayout( PATHConstants.ROWS_LAYOUT, 
                     PATHConstants.COLS_LAYOUT ) );
    // Selection
    lower.add( Box.createHorizontalGlue() ); // Glue
    lower.add( selection );
    selection.setHorizontalAlignment( JLabel.CENTER );
    // Buttons
    lower.add( Box.createHorizontalGlue() ); // Glue
    lower.add( destination );
    lower.add( home );
    lower.add( barrier );

    // Adding the panels
    this.add( upper, BorderLayout.NORTH );
    this.add( lower, BorderLayout.SOUTH );
    this.validate();

    // Adding ActionListener Private Inner Members 
    run.addActionListener( new RunButtonEventHandler() ); 
    destination.addActionListener( new DestinationButtonEventHandler() );
    home.addActionListener( new HomeButtonEventHandler() );
    // Other Time
    //barrier.addActionListener( new BarrierButtonEventHandler() );

    // Starts the program 
    startProgram();
  }

  /**
   * Run Button Private Inner Member.
   */
  private class RunButtonEventHandler implements ActionListener {
    /**
     * Will execute the path finder program.
     *
     * @param evt event fired when the button run is pressed
     */
    public void actionPerformed( ActionEvent evt ) {
      // Setting the states
      setDestination( false );
      setHome( false );
      // Other Time
      //setBarrier( false );

      // Make the button unavailable 
      destination.setEnabled( false );
      home.setEnabled( false );
      // Other Time
      //barrier.setEnabled( false );
    }
  }

  /**
   * Destination Button Private Inner Member.
   */
  private class DestinationButtonEventHandler implements ActionListener {
    /**
     * The user selected the destination button.
     *
     * @param evt event fired when the button destination is pressed
     */
    public void actionPerformed( ActionEvent evt ) {
      // Setting the states
      setDestination( true );
      setHome( false );
      // Other Time
      //setBarrier( false );

      // Make the button unavailable 
      destination.setEnabled( false );
      home.setEnabled( true );
      //barrier.setEnabled( true );

      // Changin the labels
      selection.setText( PATHConstants.SELECTION_TXT +
                         PATHConstants.DESTINATION );
    }
  }

  /**
   * Home Button Private Inner Member.
   */
  private class HomeButtonEventHandler implements ActionListener {
    /**
     * The user selected the home button.
     *
     * @param evt event fired when the button home is pressed
     */
    public void actionPerformed( ActionEvent evt ) {
      // Setting the states
      setDestination( false );
      setHome( true );
      // Other Time
      //setBarrier( false );

      // Make the button unavailable 
      destination.setEnabled( true );
      home.setEnabled( false );
      //Other Time
      //barrier.setEnabled( true );

      // Changin the labels
      selection.setText( PATHConstants.SELECTION_TXT + PATHConstants.HOME );
    }
  }

  /**
   * Barrier Button Private Inner Member.
   */
  private class BarrierButtonEventHandler implements ActionListener {
    /**
     * The user selected the home button.
     *
     * @param evt event fired when the button barrier is pressed
     */
    public void actionPerformed( ActionEvent evt ) {
      // Setting the states
      setDestination( false );
      setHome( false );
      // Other Time
      //setBarrier( true );

      // Make the button unavailable 
      destination.setEnabled( true );
      home.setEnabled( true );
      // Other Time
      //barrier.setEnabled( false );

      // Changin the labels
      selection.setText( PATHConstants.SELECTION_TXT + PATHConstants.BARRIER );
    }
  }

  /**
   * Grid Private Inner Member.
   */
  private class GridMouseEventHandler implements MouseListener {
    /**
     * Checking if there's a destination and a home already. 
     *
     * @param evt event fired when the canvas is pressed
     */
    public void mouseClicked( MouseEvent evt ) {
      setOnlyOneDestination ( grid.checkDestination() );
      setOnlyOneHome( grid.checkHome() );
    }

    /**
     * Other methods required by MouseListener.
     */
    public void mousePressed( MouseEvent evt ) {}
    public void mouseReleased( MouseEvent evt ) {}
    public void mouseExited( MouseEvent evt ) {}
    public void mouseEntered( MouseEvent evt ) {}
  }

  /**
   * Creates a 600x600 windows.
   *
   * @param args keyword for special functions
   */
  public static void main( String[] args ) {
    // Rows and Columns - default values
    int rows = PATHConstants.DEFAULT_ROWS;
    int cols = PATHConstants.DEFAULT_COLS;

    // Cheking if user entered the right amount of arguments
    if ( args.length != PATHConstants.MAX_NUM_ARGS  ) {
      if (  args.length > 0 ) {
        System.out.print( PATHConstants.NUM_ARGS_ERR );
        usageMessage();
        System.exit(1);
      }
    } else if ( args.length == PATHConstants.MAX_NUM_ARGS ) {
        try {
          // Converting everything into Integers
          rows = Integer.parseInt( args[0] );
          cols = Integer.parseInt( args[0] );
        } catch ( NumberFormatException e ) {
            System.out.printf( PATHConstants.ERR_ROWS, args[0] );
            System.out.printf( PATHConstants.ERR_COLS, args[1] );
            usageMessage();
            System.exit(1);
        }
    }

    /*
    if ( args.length == 1 ) {
      // Making the command line argument case sensitive
      args[0].toLowerCase();

      // Keyword random - will generate random starting location and destination
      if ( args[0].equals( "random" ) ) {
        // Enable Random
        System.out.println( "Random enabled!" );
      } else {
        System.out.printf( PATHConstants.ERR_KEYWORD, args[0] );
        usageMessage();
      }
    } 
    */

    // Prints usage message
    usageMessage();
    
    // Creates Windows
    new Acme.MainFrame( new PathFinder( rows, cols ), args, 
                        PATHConstants.WINDOW_WIDTH,
                        PATHConstants.WINDOW_HEIGHT );
  }
}
