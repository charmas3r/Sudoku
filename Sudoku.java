import java.util.*;       // Math Random  
import java.awt.*;        // Uses AWT's Layout Managers
import java.awt.event.*;  // Uses AWT's Event Handlers
import javax.swing.*;     // Uses Swing's Container/Components
 
/**
 * The Sudoku game.
 * To solve the number puzzle, each row, each column, and each of the
 * nine 3×3 sub-grids shall contain all of the digits from 1 to 9
 */
public class Sudoku extends JFrame {
   // Name-constants for the game properties
   public static final int GRID_SIZE = 9;    // Size of the board
   public static final int SUBGRID_SIZE = 3; // Size of the sub-grid
 
   // Name-constants for UI control (sizes, colors and fonts)
   public static final int CELL_SIZE = 60;   // Cell width/height in pixels
   public static final int CANVAS_WIDTH  = CELL_SIZE * GRID_SIZE;
   public static final int CANVAS_HEIGHT = CELL_SIZE * GRID_SIZE;
                                             // Board width/height in pixels
   public static final Color OPEN_CELL_BGCOLOR = Color.YELLOW;
   public static final Color OPEN_CELL_TEXT_YES = new Color(0, 255, 0);  // RGB
   public static final Color OPEN_CELL_TEXT_NO = Color.RED;
   public static final Color CLOSED_CELL_BGCOLOR = new Color(240, 240, 240); // RGB
   public static final Color CLOSED_CELL_TEXT = Color.BLACK;
   public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);
 
   // The game board composes of 9x9 JTextFields,
   // each containing String "1" to "9", or empty String
   private JTextField[][] tfCells = new JTextField[GRID_SIZE][GRID_SIZE];
 
   // Hardcoded game board
   private int[][] puzzle = new int[9][9];
   
   private boolean[][] masks =
      {{false, false, false, false, false, false, false, false, false},
       {false, false, false, false, false, false, false, false, false},
       {false, false, false, false, false, false, false, false, false},
       {false, false, false, false, false, false, false, false, false},
       {false, false, false, false, false, false, false, false, false},
       {false, false, false, false, false, false, false, false, false},
       {false, false, false, false, false, false, false, false, false},
       {false, false, false, false, false, false, false, false, false},
       {false, false, false, false, false, false, false, false, false}};
       
       //Buttons for game
       JButton easy = new JButton("Easy");
       JButton normal = new JButton("Normal");
       JButton hard = new JButton("Hard"); 
       
       JButton restart = new JButton("Restart"); 
       JButton timer = new JButton("Timer"); 
   
 
   /**
    * Constructor to setup the game and the UI Components
    */
   
   public Sudoku() {
      Panel panelDisplay = new Panel(new FlowLayout());
      panelDisplay.add(easy);
      panelDisplay.add(normal);
      panelDisplay.add(hard);
      
      Panel pd2 = new Panel(new FlowLayout(FlowLayout.RIGHT));
      pd2.add(restart);
      pd2.add(timer);

      
      Panel cp = new Panel(new GridLayout(GRID_SIZE, GRID_SIZE));  // 9x9 GridLayout
      
      fillRow(0, puzzle);
      
      // Allocate a common listener as the ActionEvent listener for all the
      //  JTextFields
      // ... [TODO 3] (Later) ....
 
      // Construct 9x9 JTextFields and add to the content-pane
      for (int row = 0; row < GRID_SIZE; ++row) {
         for (int col = 0; col < GRID_SIZE; ++col) {
            tfCells[row][col] = new JTextField(); // Allocate element of array
            cp.add(tfCells[row][col]);            // ContentPane adds JTextField
            if (masks[row][col]) {
               tfCells[row][col].setText("");     // set to empty string
               tfCells[row][col].setEditable(true);
               tfCells[row][col].setBackground(OPEN_CELL_BGCOLOR);
 
               // Add ActionEvent listener to process the input
               // ... [TODO 4] (Later) ...
            } else {
               tfCells[row][col].setText(puzzle[row][col] + "");
               tfCells[row][col].setEditable(false);
               tfCells[row][col].setBackground(CLOSED_CELL_BGCOLOR);
               tfCells[row][col].setForeground(CLOSED_CELL_TEXT);
            }
            // Beautify all the cells
            tfCells[row][col].setHorizontalAlignment(JTextField.CENTER);
            tfCells[row][col].setFont(FONT_NUMBERS);
         }
      }
 
      // Set the size of the content-pane and pack all the components
      //  under this container.
      cp.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
      pack();
      
      setLayout(new BorderLayout());
      add(panelDisplay, BorderLayout.EAST);
      add(pd2, BorderLayout.SOUTH);
      add(cp, BorderLayout.CENTER);
      
      setSize(600, 400);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Handle window closing
      setTitle("Sudoku");
      setVisible(true);
   }
 
   public boolean slotFull(int r, int c) {
       if (puzzle[r][c] == 0) {
           return false;
        }
       return true;
   }
   
   public boolean boardFull() {
       for (int row = 0; row < GRID_SIZE; ++row) {
           for (int col = 0; col < GRID_SIZE; ++col) {
                slotFull(row, col);
            }
       }
       return true;
   }
   
   public int randNumGen() {
       Random generate = new Random();
       int random = generate.nextInt(9) + 1;
       return random;
   }
   
   // public void fillBoard(int[][] arry) {
      // for (int row = 0; row < GRID_SIZE; ++row) {
           // for (int col = 0; col < GRID_SIZE; ++col) {
                // int temp = randNumGen();
                // if (!uniqueRow(temp, arry))
                    // arry[row][col] = temp; //insert unique int into the board
                // else {
                    // col--; // if not unique repeat filling procedure for this spot
                // }
            // }
       // }
   // }
   
   // public void fillRow(int row, int[][] arry) {
       // int count = 0;
    
       // for (int col = 0; col < GRID_SIZE; ++col) {
           // int temp = randNumGen();
           
           // if (uniqueRow(row, temp, arry)) {
                // arry[row][col] = temp; //insert unique int into the board
            // }
           
           // if checking shows the same int in the row
           // else {
             
               // fill the spot with a unique number
               // do{
                    // count = 0;
                    // temp = randNumGen();
                   
                    // if (uniqueRow(row, temp, arry)) {
                       // arry[row][col] = temp; //insert unique int into the board
                       // count++;
                    // }

               
               // } while(count == 0);
            // }
        // }
   // }
   
    public boolean uniqueRow(int row, int num, int[][]arry) {
     for (int col = 0; col < GRID_SIZE; ++ col) {
           if (num == arry[row][col]) {
              return false;
           }
     }
     return true;
}

public boolean uniqueColumn(int col, int num, int[][]arry) {
     for (int row = 0; row < GRID_SIZE; ++row) {
          if (num == arry[row][col]) {
             return false;
          }
     }
     return true;
}

public boolean uniqueSubP2(int row, int col, int num, int[][]arry) {
     int r = row;
     int c = col;
     while (row != r + 3) {
          while (col != c + 3) {
                    if (num == arry[row][col])
                            return false;
                    ++c;
          }
          ++r;
        }
        return true;
}

public boolean uniqueSubP1(int row, int col, int num, int[][]arry) {
     int r = 0;
     int c = 0;
     int n = num; //not sure if this is necessary
     if (0 <= row && row <= 2) {
        if (0 <= col && col <= 2) {
           r = 0;
           c = 0;
        }
        else if (3 <= col && col <=5) {
           r = 0;
           c = 3;
        }
        else {
           r = 0;
           c = 6;
        }
    }
    else if (2 <= row && row <= 5) {
        if (0 <= col && col <= 2) {
           r = 3;
           c = 0;
        }
        else if (2 <= col && col <=5) {
           r = 3;
           c = 3;
        }
        else {
           r = 3;
           c = 6;
        }
    }
    else {
        if (6 <= row && row <= 8) {
            if (0 <= col && col <= 2) {
                r = 6;
                c = 0;
            }
            else if (3 <= col && col <= 5) {
                r = 6;
                c = 3;
            }
            else {
                r = 6;
                c = 6;
            }
        }
    }
    boolean state = uniqueSubP2(r, c, n, arry);
    return state;
}

public void fillRow(int row, int[][] arry) {
     for (int col = 0; col < GRID_SIZE; ++col) {
           int temp = randNumGen();
           //  && !uniqueColumn(col, temp, arry)|| !uniqueSubP1(row, col, temp, arry)
           while (!uniqueRow(row, temp, arry) )
                  temp = randNumGen();
           arry[row][col] = temp;
     }
}

   /** The entry main() entry method */
   public static void main(String[] args) {
      // [TODO 1] (Now)
      // Check Swing program template on how to run the constructor
      SwingUtilities.invokeLater(new Runnable() {
          public void run() {
              //new Intro();
              new Sudoku();
              
          }   
      });
    
   }

   // Define the Listener Inner Class
   // ... [TODO 2] (Later) ...
}