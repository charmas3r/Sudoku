# Sudoku

GUI program includes game board generation using a backtracking technique to generate complete boards. Cells are filled by an algorithm which fills the last most constrained cell in the matrix. This algorithm fills on average 55% percent of the board before backtracking. Backtracking algorthim starts when there are no options available for legal placement in a cell. The previous insertion location is popped from the stack and the currently placed number added back to an array of possibilities which are randomly picked from. 
