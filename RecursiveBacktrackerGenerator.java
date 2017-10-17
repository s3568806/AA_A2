package mazeGenerator;
import java.util.*;
import maze.Maze;

public class RecursiveBacktrackerGenerator implements MazeGenerator {
	int adjust = 0;
	Random generator = new Random();
	boolean[][] visited = null;
		
	@Override
	public void generateMaze(Maze maze) {
		adjust = maze.map.length-1;
		//Set up variables according to maze specs
		visited = new boolean[maze.sizeR][maze.sizeC];
		for(int i=0; i < visited.length; i++){
			for(int j=0; j < visited[i].length; j++){
				visited[i][j] = false;
			}
		}
		int yPos = generator.nextInt(maze.map.length); //generator.nextInt(maze.map.length);
		int xPos = generator.nextInt(maze.map[yPos].length); //generator.nextInt(maze.map.length);
		
		//normal maze
		if(maze.type == 0){
			visited[yPos][xPos] = true;
			normalMaze(yPos, xPos, maze);
		}
	} // end of generateMaze()
	
	public void normalMaze(int y, int x, Maze maze){
		//get all neighbors available and put in array list
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		gotNeighborsNormal(y, x, maze, neighbors);
		
		int new_y = 0;
		int new_x = 0;
		//while there are still neighbors loop
		while(neighbors.size() != 0){
			//randomly pick item from array
			int dir = neighbors.get(generator.nextInt(neighbors.size()));
			new_y = y + maze.deltaR[dir];
			new_x = x + maze.deltaC[dir];
			if(new_y >= 0 && new_x >= 0
				&& new_y < maze.map.length && new_x < maze.map[y].length){
					if(visited[new_y][new_x] == true){
						neighbors.remove(Integer.valueOf(dir));
					}
					else{
						maze.map[y][x].wall[dir].present = false;
						visited[new_y][new_x] = true;
						neighbors.remove(Integer.valueOf(dir));
						normalMaze(new_y, new_x, maze);
					}
				}
		}
		
	}
	
	public void gotNeighborsNormal(int y, int x, Maze maze, ArrayList<Integer> array){
		int new_y = 0;
		int new_x = 0;
		for(int i=0; i < maze.map[y][x].neigh.length; i++){
			new_y = y + maze.deltaR[i];
			new_x = x + maze.deltaC[i];
			if(new_y >= 0 && new_x >= 0
				&& new_y < maze.map.length && new_x < maze.map[y].length){
				if(maze.map[y][x].neigh[i] != null && visited[new_y][new_x] == false){
					array.add(i);
				}
			}
		}
	}
} // end of class RecursiveBacktrackerGenerator
