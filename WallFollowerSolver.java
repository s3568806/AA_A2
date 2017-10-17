package mazeSolver;
import java.util.*;
import maze.Maze;

/**
 * Implements WallFollowerSolver
 */

public class WallFollowerSolver implements MazeSolver {
	boolean[][] visited = null;
	int cellsExp = 0;
	boolean exit_found = false;
	boolean solved = false;
	@Override
	public void solveMaze(Maze maze) {
		// TODO Auto-generated method stub
		visited = new boolean[maze.sizeR][maze.sizeC];
		int y = maze.entrance.r;
		int x = maze.entrance.c;
		
		solveMaze(y, x, maze);
	} // end of solveMaze()
	
	public void solveMaze(int y, int x, Maze maze){
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		if(y == maze.exit.r && x == maze.exit.c){
			exit_found = true;
			solved = true;
		}
		getNeighbors(y, x, maze, neighbors);
		int dir = 0;
		int new_y = 0;
		int new_x = 0;
		
		while(neighbors.size() != 0 && exit_found == false){
			dir = neighbors.get(0);
			new_y = y + maze.deltaR[dir];
			new_x = x + maze.deltaC[dir];
			if(visited[new_y][new_x] == true){
				neighbors.remove(0);
			}
			else{
				visited[new_y][new_x] = true;
				neighbors.remove(0);
				cellsExp++;
				solveMaze(new_y, new_x, maze);
			}
		}
	}
	
	public void getNeighbors(int y, int x, Maze maze, ArrayList<Integer> neighbors){
		int new_y = 0;
		int new_x = 0;
		for(int i= 0; i < maze.map[y][x].neigh.length; i++){
			new_y = y + maze.deltaR[i];
			new_x = x + maze.deltaC[i];
			if(new_y >= 0 && new_y < maze.sizeR && new_x >= 0 && new_x < maze.sizeC){
				if(maze.map[y][x].wall[i] != null && visited[new_y][new_x] == false){
					if(maze.map[y][x].wall[i].present == false){
						neighbors.add(i);
					}
				}
			}
		}
	}
    
	@Override
	public boolean isSolved() {
		if(solved == true){
			return true;
		}
		return false;
	} // end if isSolved()
    
    
	@Override
	public int cellsExplored() {
		// TODO Auto-generated method stub
		return cellsExp;
	} // end of cellsExplored()

} // end of class WallFollowerSolver
