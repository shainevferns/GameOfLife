import java.util.ArrayList;

// Basic 
public class LifeGrid {
	
	private int r;
	private int c;
	private int[][] grid; // create full map?
	
	public LifeGrid(int rows, int cols) { // generates a grid r x c
		// can also use this.r and this.c
		r = rows; // private int r = parameter r
		c = cols;// private int c = parameter c
		grid = new int[r][c];
	}

	public int getCell(int x, int y) { // returns the cell listed at x, y
		// TODO Auto-generated method stub
		return grid[x][y];
	}
	
	public int getNumRows() { // returns num of rows
		return grid.length;
	}
	
	public int getNumCols() { // returns number of cols
		return grid[0].length; // CHANGE LATER
	}
	
	public int setCell(int x, int y, int value) { // CHANGE LATER
		grid[x][y] = value;
		return grid[x][y];
	}
	
	public void evolve() {
		// Outline of process
		// Create a TEMP grid of the same dimensions.
		
		// 2. Visit every cell of the grid. 
			// For each cell, count the living neighbors.
			// Determine its state in the next evolution. (Apply the rules.)
		boolean isDead;
		
		ArrayList<Integer> deadCells = new ArrayList<Integer>(); // ArrayList dynamic length
		ArrayList<Integer> reCells = new ArrayList<Integer>(); // list of reproduction cells
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				int neighbors = countNeighbors(i, j);
				
				// DEATH RULES SKULL x7
				if (grid[i][j] == 1) { // affects only live cells
					
//					System.out.println("x: " + i + ", y: " + j + ", neighbors: " + neighbors); // debug
					
					
					if (neighbors <= 1 || neighbors >= 4) { // death rules (1 or less, 4 or more)
						isDead = true;
					} else {
						isDead = false;
					}
					
					
					if (isDead) { // if the cell is considered dead by the system
						
						deadCells.add(i); // saves x pos to deadCells
						deadCells.add(j); // saves y pos to deadCells
					}
				}
				
				// reproduction rules (affects ONLY empty cells)
				if (neighbors == 3 && grid[i][j] == 0) {
						reCells.add(i);
						reCells.add(j);
				}
			}
		}
		
		// 3. Replace current generation with the new generation.
		
				//grid = temp; // Releases current grid from memory (garbage collection)
							    // assign grid to refer to the temp grid made in evolve
		
		// THESE TWO FOR LOOPS UPDATE THE GAME
		// death system
		// goes through the x and y values for dead cells and actually tweaks them
		for (int cellPos = 0; cellPos < deadCells.size()-1; cellPos++) {
			// creates x and y pos and resets them at the beginning of the for loop
			int x = 0;
			int y = 0;
			
//			if (cellPos%2==0) { // even indexes are the x position, odds are y pos
				x = deadCells.get(cellPos);
//			} else {
				y = deadCells.get(cellPos+1);
				
				System.out.println(x + ", " + y);
//			}
			
//			System.out.println(x + ", " + y);
			if (cellPos%2==0) {
				grid[x][y] = 0;
			}
		}
		
		
		// goes through the x and y values for reproduction cells and actually tweaks them
		for (int cellPos = 0; cellPos < reCells.size()-1; cellPos++) {
			// creates x and y pos and resets them at the beginning of the for loop
			int x = 0;
			int y = 0;
			
			x = reCells.get(cellPos);
			y = reCells.get(cellPos+1);
			
//			System.out.println(x + ", " + y);
			if (cellPos%2==0) {
				grid[x][y] = 1;
			}
		}
		
		// Added by me: loop the grid :)))
		/*
		 * Plan:
		 * check beforehand where the cells wil go,
		 * if it goes over grid length or vice versa,
		 * change values in deadCells and reCells as needed
		 * (probably only reCells because why would you
		 * wrap cells that would be killed :/
		 * */
		
	}
	
	private int countNeighbors(int r, int c) {
		// CHECK BOUNDARIES (r can't be below 0, can't be above grid length), same with c
		/*
		 0, 0, 0
		 0, 1, 0
		 0, 0, 0
		 
		 (r-1)(c-1), (r-1)(c), (r-1)(c+1)
		 (r)(c-1), 1 (r)(c), (r)(c+1)
		 (r-1)(c+1), (r+1)(c), (r+1)(c+1)
		 * */
		int count = 0; // count is the number of neighbors counted
//	

		boolean notCounted = false;
	
		// designates each area that should be affected by the for loop
		int[] areas = {0, 0, 0, 0};
		
		// upper area, lower area, leftmost area, rightmost area
		
		if (r-1 < 0) areas[0] = 1; // upper
			
		if (r+1 >= grid.length) areas[1] = 1; // lower
		
		if (c-1 < 0) areas[2] = 1; // leftmost
	
		if (c+1 >= grid[0].length) areas[3] = 1; // rightmost
		
		for (int i = (r-1)+areas[0]; i <= (r+1)-areas[1]; i++) { // goes through rows (moves down)
			for (int j = (c-1)+areas[2]; j <= (c+1)-areas[3]; j++) { // goes through cols (moves right)
				notCounted = false;
				if (i == r && j == c) {
					notCounted = true;
				}
//				System.out.println("counted? " + notCounted);
	
				if (!notCounted) {
					if (grid[i][j] == 1) {
						count++;
					}
				}
			}
		}
		return count;
	}
	
	private int checkCell(int x, int y) {
		// checks the cell at x and y and returns 0 or 1, dead and alive respectively
		
		return grid[x][y];
		
	}
	
	public void clearGrid() { // clears the grid, is called from LifeCanvas
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[0].length; c++) {
				grid[r][c] = 0;
			}
		}
	}
}
