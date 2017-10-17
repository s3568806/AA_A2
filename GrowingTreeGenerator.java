package mazeGenerator;

import java.util.*;

import maze.Maze;
import maze.Cell;

public class GrowingTreeGenerator implements MazeGenerator {
	// Growing tree maze generator. As it is very general, here we implement as "usually pick the most recent cell, but occasionally pick a random cell"
	Random generator = new Random();
	ArrayList<Cell> cells = new ArrayList(); 
	boolean[][] visited = null;
	double threshold = 0.1;
	
	@Override
	public void generateMaze(Maze maze) {
		//normal
		visited = new boolean[maze.sizeR][maze.sizeC];
		int y = generator.nextInt(maze.map.length);
		int x = generator.nextInt(maze.map[y].length);
		
		Cell start = new Cell(y, x);
		cells.add(start);
		int index = 0;
		
		while(cells.size() != 0){
			index = generator.nextInt(cells.size());
			Cell picked = cells.get(index);
			ArrayList<Integer> neighbors = new ArrayList<Integer>();
			getNeighbors(picked, maze, neighbors);
			if(neighbors.size() == 0){
				cells.remove(index);
			}
			else{
				int dir = neighbors.get(generator.nextInt(neighbors.size()));
				int new_y = picked.r + maze.deltaR[dir];
				int new_x = picked.c + maze.deltaC[dir];
				if(visited[new_y][new_x] == false){
					maze.map[picked.r][picked.c].wall[dir].present = false;
					visited[new_y][new_x] = true;
					neighbors.remove(Integer.valueOf(dir));
					cells.add(maze.map[new_y][new_x]);
				}
				else{
					neighbors.remove(Integer.valueOf(dir));
				}
			}
		}
	}
	
	public void getNeighbors(Cell cell, Maze maze, ArrayList neighbors){
		int new_y = 0;
		int new_x = 0;
		for(int i=0; i < maze.map[cell.r][cell.c].neigh.length; i++){
			new_y = cell.r + maze.deltaR[i];
			new_x = cell.c + maze.deltaC[i];
			if(new_y >= 0 && new_x >= 0 && new_y < maze.sizeR && new_x < maze.sizeC){
				if(maze.map[cell.r][cell.c].neigh[i] != null && visited[new_y][new_x] == false){
					neighbors.add(i);
				}
			}
		}
	}
}
