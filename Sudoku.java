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
 

   private int[][] puzzle = new int[GRID_SIZE][GRID_SIZE];      
       
   private Stack stack1 = new Stack();
   private Stack stack2 = new Stack();
   private Stack stack3 = new Stack();
   private Stack stack4 = new Stack();
   private Stack stack5 = new Stack();
   private Stack stack6 = new Stack();
   private Stack stack7 = new Stack();
   private Stack stack8 = new Stack();
   private Stack stack9 = new Stack();
      
   
       // Hardcoded game board
       //for testing remove: (0,0) = 5
      // {{5, 3, 4, 6, 7, 8, 9, 1, 2},
       // {6, 7, 2, 1, 9, 5, 3, 4, 8},
       // {1, 9, 8, 3, 4, 2, 5, 6, 7},
       // {8, 5, 9, 7, 6, 1, 4, 2, 3},
       // {4, 2, 6, 8, 5, 3, 7, 9, 1},
       // {7, 1, 3, 9, 2, 4, 8, 5, 6},
       // {9, 6, 1, 5, 3, 7, 2, 8, 4},
       // {2, 8, 7, 4, 1, 9, 6, 3, 5},
       // {3, 4, 5, 2, 8, 6, 1, 7, 9}};
   
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
      
      //test
      //for (int i = 1; i <= GRID_SIZE; ++i) 

      
      //int number = randNumGen();
      //startPoss(poss);
      boolean board = fillBoard(puzzle);
      //fillRow(0, puzzle);
      //fillRow(1, puzzle);
      
      //public void fillCell(int row, int col, int num, int[][] arry, int[] badNums)
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
   
   //iterates through board, if an empty slot is found returns false, else true
   public boolean boardFull() {
       for (int row = 0; row < GRID_SIZE; ++row) {
           for (int col = 0; col < GRID_SIZE; ++col) {
                if (!slotFull(row, col))
                    return false;
            }
       }
       return true;
   }
   
   public int randNumGen() {
       Random generate = new Random();
       int random = generate.nextInt(9) + 1;
       return random;
   }
   
   //dump and replace.
   public boolean fillBoard(int[][] arry) {
           
           int row = 0; 
           
           if (boardFull()) 
              return true;
        
           //keep trying to fill the board   
           else {
               fillRow(row++, arry);
               return fillBoard(arry);
               
           }
           
           //return fillBoard(arry);
           
   }
   
   // public void fillRow(int row, int[][] arry) {
       // int count = 0;
    
       // for (int col = 0; col < GRID_SIZE; ++col) {
           // int temp = randNumGen();
           
           // if (uniqueRow(row, temp, arry)  && uniqueColumn(col, temp, arry)) {
                // arry[row][col] = temp; //insert unique int into the board
            // }
           
           // if checking shows the same int in the row
           // else {
             
               // fill the spot with a unique number
               // do{
                    // count = 0;
                    // temp = randNumGen();
                   
                    // if (uniqueRow(row, temp, arry) && uniqueColumn(col, temp, arry)) {
                       // arry[row][col] = temp; //insert unique int into the board
                       // count++;
                    // }

               
               // } while(count == 0);
            // }
        // }
   
    // }
//Checks from top left   
public boolean uniqueRow(int row, int num, int[][]arry) {
     for (int col = 0; col < GRID_SIZE; ++col) {
           if (num == arry[row][col]) {
              return false;
           }
     }
     return true;
}

//Checks from bottom left
public boolean uniqueColumn(int col, int num, int[][]arry) {
     for (int row = GRID_SIZE - 1; row >= 0; --row) {
          if (num == arry[row][col]) {
             return false;
          }
     }
     return true;
}

public boolean uniqueSubP2(int row, int col, int num, int[][]arry) {
    
        for (int r = row; r < row + 3; ++r) {
            
            for (int c = col; c < col + 3; ++c) {
                    if (num == arry[r][c])
                         return false;
            }
          
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

public boolean uniqueCheck(int row, int col, int num, int[][] arry) {
      if(uniqueRow(row, num, arry) && uniqueColumn(col, num, arry) && uniqueSubP1(row, col, num, arry))
          return true;
      else 
          return false;
}

//find all real possibilities
public void addPoss(int row, int col, int[][] arry, ArrayList<Integer> possList) {
    // iterates through 1-9. if num is unique it is added to poss. list
    
    for (int i = 0; i < GRID_SIZE; ++i) {
       
        if (uniqueCheck(row, col, i, arry))
            possList.add(i);
    }

    Collections.shuffle(Arrays.asList(possList));
}


public void findCell(int[][] arry, ArrayList<Integer> possList) {
    
    //iterate through the whole board
    for (int i = 0; i < GRID_SIZE; ++i) { 
        
        for (int j = 0; j < GRID_SIZE; ++j) {
                
                //at each location find the size of full possibility list.
                
                addPoss(i, j, arry, possList);
                int temp = possList.size();
                
                
                //if the poss. list is 1, then push to first stack
                if (temp == 1) {
                    stack1.push(j);
                    stack1.push(i);
                }
                
                else if (temp == 2) {
                    stack2.push(j);
                    stack2.push(i);
                }
                
                else if (temp == 3) {
                    stack3.push(j);
                    stack3.push(i);
                }    
                
                else if (temp == 4) {
                    stack4.push(j);
                    stack4.push(i);
                }    
                
                else if (temp == 5) {
                    stack5.push(j);
                    stack5.push(i);
                }    
                
                else if (temp == 6) {
                    stack6.push(j);
                    stack6.push(i);
                }    
                
                else if (temp == 7) {
                    stack7.push(j);
                    stack7.push(i);
                }    
                
                else if (temp == 8) {
                    stack8.push(j);
                    stack8.push(i);
                }    
                
                else  {
                    stack9.push(j);
                    stack9.push(i);
                }    
                
                
                
               
            }
    
    }
    
    //now find the first non empty stack
                //and pop the row and col into it
                if (!stack1.isEmpty()) {
                    locations.push(stack1.pop);
                    locations.push(stack1.pop);
                }
                else if (!stack2.isEmpty()) {
                    locations.push(stack2.pop);
                    locations.push(stack2.pop);
                }
                else if (!stack3.isEmpty()) {
                    locations.push(stack3.pop);
                    locations.push(stack3.pop);
                }
                else if (!stack4.isEmpty()) {
                    locations.push(stack4.pop);
                    locations.push(stack4.pop);
                }
                else if (!stack5.isEmpty()) {
                    locations.push(stack5.pop);
                    locations.push(stack5.pop);
                }
                else if (!stack6.isEmpty()) {
                    locations.push(stack6.pop);
                    locations.push(stack6.pop);
                }
                else if (!stack7.isEmpty()) {
                    locations.push(stack7.pop);
                    locations.push(stack7.pop);
                }
                else if (!stack8.isEmpty()) {
                    locations.push(stack8.pop);
                    locations.push(stack8.pop);
                }
                else {
                    locations.push(stack9.pop);
                    locations.push(stack9.pop);
                }
}
// public void startPoss(int[] pass) {
    // int temp = randNumGen();
    // int count = 0;
    
    // while (count != 9) {
        // for (int i = 1 i <=;
    // }
        
    // //randomize elements
    // Collections.shuffle(Arrays.asList(pass));
// }

// public void deleteElement(int arr[], int x)
    // {
        // // Search x in array
        // int i;
        // for (i=0; i<arr.length; i++)
            // if (arr[i] == x)
                // break;
  
        // // If x found in array
        // if (i < arr.length)
        // {
            // // reduce size of array and move all
            // // elements on space ahead
            // int n = arr.length;
            
            // n = n - 1;
            // for (int j=i; j<arr.length; j++)
                // arr[j] = arr[j+1];
        // }
  
        // Collections.shuffle(Arrays.asList(arr));
    // }
      
// public boolean stackCheck(int num, Stack pass) {
   // int temp;
   // Stack tempStack = new Stack();
   
   // makes a copy of current passed stack
   // tempStack.addAll(pass);
   
   // pop one element at a temp of the copied stack to check if passed num is in stack. 
      // for (int i = 0; i < tempStack.size(); ++i) {
          // temp = (Integer) tempStack.pop();  
              // if (temp == num) 
              // return false;
   // }      
      
   // return true;
// }
      
// public void fillCell(int row, int col, int[][] arry, int[] possSet) {

      // when we move up a cell, we need to find all possibilites for the current cell
      // to do that we need to eliminate any other numbers that show up in a row, column or subgrid
      
      // if (boardFull())
          // return;    
      
      // take one possibility and try to insert it
      // int temp = possSet[0];
      
      // in case board state fails later on in the puzzle, save location where we added 
      // and possibility set. both get put on different stacks.
      // possStack.push(possSet);
         
      
      // test board state, if it passes then go to the next spot and repeat the process. 
      // if (uniqueCheck(row, col, temp, puzzle)) { 
          
          // push location
          // backtrackStack.push(col);
          // backtrackStack.push(row);
          
          // add number to the puzzle
          // arry[row][col] = temp;
          
          // move to next cell in current row, if we reach the end of the row wrap to the next line
          // if(col == 8) {
            // ++row;
            // col = 0;
          // }
          // else
            // ++col;
          
          // get all possibilities for next cell
          // addPoss(row, col, arry, possSet);
          // deleteElement(possSet, temp);
         
          // fillCell(row, col, puzzle, possSet);
      // }
      
      // else board state fails, start backtracking
      // else {
          
          // we need pop the location  
          // row = (Integer) backtrackStack.pop();
          // col = (Integer) backtrackStack.pop();
          
          // pop possibilities of previous cell
          // possSet = (TreeSet) possStack.pop();
          // addPoss(row, col, arry, possSet);
          
          // remove the last cell's current element from its possibility set
          // addPoss(row, col, arry, possSet);
          // deleteElement(possSet, temp);
          
          // call method again
          //fillCell(row, col, puzzle, possSet);
     // }

//}

public void fillRow(int row, int[][] arry) {
     
     for (int col = 0; col < GRID_SIZE; ++col) {
           int temp = randNumGen();
           
           while (!uniqueCheck(row, col, temp, arry))
                temp = randNumGen();
               
           arry[row][col] = temp;
                
     }
}

// public void fillColumn(int col, int[][] arry) {
     // for (int row = 0; row < GRID_SIZE; ++row) {
           // int temp = randNumGen();
           // //  && !uniqueColumn(col, temp, arry)|| !uniqueSubP1(row, col, temp, arry)
           // while (!uniqueColumn(row, temp, arry) )
                  // temp = randNumGen();
           // arry[row][col] = temp;
     // }
// }

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