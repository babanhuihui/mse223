package shipSearch;

public class Map {
	public  int row;
	public 	int col;
	public  int[][] grid;
	public Map(int row, int col)
	{
		this.row = row;
		this.col = col;
		grid = new int[row][col];
	}
	public boolean inMap(int Arow, int Acol){
		if ( Arow < 0 || Arow > row-1 ||Acol < 0 || Acol > col-1){
			return false;
		} 
		return true;
	}
	
	public void initialPlane(int xCor, int yCor, int index){
		grid[xCor][yCor] = index;
	}
	
	
	public void setPlane(int xCor, int yCor, Plane plane){
		grid[xCor][yCor] = 0;
		grid[plane.x][plane.y] = plane.index;
		//System.out.print("Plane" + plane.index + " in Coordinate:" + plane.x + " , " + plane.y);
	}
	
	public boolean planeInArea(int xCor, int yCor){
		if(grid[xCor][yCor] != 0){
			return true;
		}
		return false;
	}
}
