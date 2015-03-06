import java.awt.Color;


/***
 * 
 * @author Beth Dellea
 * A class that sets up the terrain of the simulation, including evaluation of states and changing state
 */



public class Terrain {


	private int W;  // Width of Grid
	private int H;  // Height of Grid
	private int[][] state; //0 - empty, 1 - burning, 2- forest
	private int[][] nextState; //0 - empty, 1 - burning, 2- forest
	
	/**
	 * constructor for Terrain Class
	 * @param width
	 * @param height
	 */
	public Terrain (int width, int height){
  	    W = width;
		H = height;

		state = new int[W][H];
		nextState = new int[W][H];

	    for (int i = 0; i<W; i++){
	    	for (int j=0; j <H; j++){
	    		state[i][j] = 2;
	    		nextState[i][j] = 2;
	    	}
	    }

	    StdDraw.setCanvasSize(400,400);
        StdDraw.setXscale(0, W);
        StdDraw.setYscale(0, H);
	    this.update();        
	}	

	/**
	 * updates terrain by swapping next state as current state and redrawing
	 */
	
	public void update(){

        
        for (int i = 0; i<W; i++){
	    	for (int j=0; j <H; j++){
	    		state[i][j] = nextState[i][j];

	    		if (state[i][j] == 1){ StdDraw.picture(i+.45, j+.45, "fire.jpg");} 
	    		else if (state[i][j] == 2){ StdDraw.picture(i+.45, j+.45, "tree.png");}
	    		else {StdDraw.picture(i+.45,j+.45,"soot.jpg");}
	    		/* --- initial, color-based version of the simulation
	    		 * Color c = new Color(30,30,30);
	    		if (state[i][j] == 1){ c = new Color(255,30,30);} 
	    		else if (state[i][j] == 2){ c = new Color(30,200,30);}
	    		StdDraw.setPenColor(c);
	    		StdDraw.filledSquare(i+.45, j+.45, 0.45);
	    		 */
	    	}
	    }
		
		
	}
	
	
	/* ADD More Method Functions Here */
	
	/**
	 * takes x and y locations of square to be checked, returns the state
	 * @param x
	 * @param y
	 * @return returns 2 if a forest, 1 if on fire, 0 if empty
	 */
	public int checkState(int x, int y){
		return state[x][y];
	}
	
	/**
	 * Keeps the state the same, intended for "forest" and "empty" squares, could also be used for water areas
	 * @param x -- x location of the square
	 * @param y -- y location of the square
	 */
	public void maintain(int x, int y){
		nextState[x][y] = state[x][y];
	}
	/**
	 * Sets a specified square to the empty state
	 * @param x -- x location of the square
	 * @param y -- y location of the square
	 */
	public void setEmpty(int x, int y){
		nextState[x][y] = 0;
	}
	/**
	 * Sets a specified square to the burning state
	 * @param x -- x location of the square
	 * @param y -- y location of the square
	 */
	public void setBurning(int x, int y){
		nextState[x][y] = 1;
	}
	
	/**
	 * Checks neighboring (NSEW) squares to judge if a square itself may burn
	 * @param x -- x location of current square
	 * @param y -- y location of current square
	 * @return true if a square to NSE or W is burning, false if none are
	 */
	public boolean hasBurningNeighbor(int x, int y){
		//for each direction, checks if the square to be checked will be in bounds
		//then sees if it is on fire. As soon as one square is firey, we can stop checking.
		if (x >0){
			int north = this.checkState(x-1,y);
			if (north == 1){
				return true;
			}
		}
		if(y < H-1){
			int east = this.checkState(x, y+1);
			if (east == 1){ //same as west
				return true;
			}
		} 
		if(x< W-1){
			int south = this.checkState(x+1,y);
			if (south == 1){ //same as west
				return true;
			}
		} 
		if(y>0){
			int west = this.checkState(x,y-1);
			if (west == 1){ 
				return true;
			}
		} 
		return false;
	}
	/**
	 * Sample main method that tests Terrain Class - 
	 * @param args
	 */
	
	public static void main(String[] args) {
		Terrain t = new Terrain(15,10);
		t.update();
	}
	
}
