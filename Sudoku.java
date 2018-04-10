import java.util.*;       // Math Random  
import java.awt.*;        // Uses AWT's Layout Managers
import java.awt.event.*;  // Uses AWT's Event Handlers
import javax.swing.*;     // Uses Swing's Container/Components
import java.util.Stack;
import java.util.ArrayList;
import java.util.Collections;
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
   private int[][] puzzle = new int[GRID_SIZE][GRID_SIZE];
   private Stack locations = new Stack();
   private ArrayList<Integer> possibilities = new ArrayList<Integer>();
   
   private boolean[][] masks2 =
      {{false, false, false},
       {false, false, false},
       {false, false, false}};
       
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

      firstCell(puzzle);
      fillCell(puzzle, possibilities);
      
      Panel cp = new Panel(new GridLayout(GRID_SIZE, GRID_SIZE));  // 9x9 GridLayout
      
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
      boolean state;
      for (int row = 0; row < GRID_SIZE; ++row) {
          for (int col = 0; col < GRID_SIZE; ++col) {
                state = slotFull(row, col);
                if(state == false) {
                    return false;
                }
          }
      }
      return true;
   }
   
   public int randNumGen() {
      Random generate = new Random();
      int random = generate.nextInt(9) + 1;
      return random;
   }
   
   public boolean uniqueRow(int row, int num, int[][]arry) {
      for (int col = 0; col < GRID_SIZE; ++ col) {
          if (num == arry[row][col]) {
             return false;
          }
      }
      return true;
   }

   public boolean uniqueColumn(int col, int num, int[][]arry) {
      for (int row = GRID_SIZE - 1; row >= 0; --row) {
          if (num == arry[row][col]) {
             return false;
          }
      }
      return true;
   }

   public boolean uniqueSubP2(int row, int col, int num, int[][] arry) {
    
       for (int r = row; r < row + 3; ++r) { 
           for (int c = col; c < col + 3; ++c) {
               if (num == arry[r][c])
               return false;
           }
          
       }
       return true;
   }

   public boolean uniqueSubP1(int row, int col, int num, int[][] arry) {
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

   public boolean uniqueCheck(int row, int col, int num, int[][]arry) {
      if(uniqueRow(row, num, arry) && uniqueColumn(col, num, arry) && uniqueSubP1(row, col, num, arry))
          return true;
      else 
          return false;
      }
    
   //fills the very first cell in our board so we can get the recursive method started
   public void firstCell(int[][] arry) {
      int num = randNumGen();
      arry[0][0] = num;
      locations.push(0);
      locations.push(0);
   }
   
   //find all real possibilities
   public void addPoss(int row, int col, int[][] arry, ArrayList<Integer> possibilities) {
      // iterates through 1-9. if num is unique it is added to poss. list

      for (int i = 1; i < GRID_SIZE + 1; ++i) {
         if (uniqueCheck(row, col, i, arry))
            possibilities.add(i);
      }

      Collections.shuffle(Arrays.asList(possibilities));
   }
   
   public void findCell(int[][]arry){
      int constrained = 9;
      int row = 0;
      int col = 0;     
      //iterate through board
       for(int i = 0; i < GRID_SIZE; ++i) {
         for(int j = 0; j < GRID_SIZE; ++j) {
            //skip cells with numbers
            if(arry[i][j] != 0)
               continue;
            //call add and get count
            addPoss(i, j, arry, possibilities);
            int count = possibilities.size();
            //compare, make constrained = count if <=
            if(count <= constrained) {
               constrained = count;
               row = i;
               col = j;
            }
            //**IMPORTANT: CLEAR POSSIBILITIES LIST EVERYTIME
            possibilities.clear();
         }
      }
      locations.push(col);
      locations.push(row);
    }
   //will fill based on board constraints
   
   public void fillCell(int[][] arry, ArrayList possibilities) {
      
      //call findCell here to push row and col into stack
      
      findCell(arry);
      
      //retrieve row and col, we want to use peek so they stay in stack
      
      int row = (Integer) locations.pop();
      int col = (Integer) locations.pop();
      
      locations.push(col);
      locations.push(row);
      
      addPoss(row, col, arry, possibilities);
      //make collections class later? shuffles the elements inside arraylist
      
      Collections.shuffle(possibilities);
      
      //if there's a possibility for this cell
      if(boardFull()){      
         possibilities.clear();   
         return;
        }
         
      if(!possibilities.isEmpty()) {
          
         //grab the first element, because it's shuffled it doesn't matter
         
         int num = (Integer) possibilities.get(0);
         
         //set that cell = to num
         
         arry[row][col] = num;
         
         //recursive call (which creates new arraylist, recalls find cell, etc...)
         possibilities.clear();
         fillCell(arry, possibilities);
      }
      else {
          
         //this time we want to pop instead of peek so we can remove the elements
         
         //if we didn't pop, the algorithm would only ever look at the last row and col placed
         
         //and never see the row's and col's placed before it.
         while(possibilities.size() == 0) {
            possibilities.clear();
            row = (Integer) locations.pop();        
            col = (Integer) locations.pop();
            addPoss(row, col, arry, possibilities);
         }
         
         //remove the num that's still in this int num in this step of recursive function
         
         //possibilities.remove(arry[row][col]);
         
         //recursive call
         
         fillCell(arry, possibilities);
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
