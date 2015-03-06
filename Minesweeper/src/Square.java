/************************
 * @author Beth
 * December 2014
 * The Square class for the minesweeper lab.
 */

import java.awt.Color;

public class Square {

	
	private boolean isRevealed;
	private boolean hasFlag;
	private boolean hasBomb;
	private boolean bombClicked;
	private int x;
	private int y;
	private int numBombNeighbors;  //when adding num, std draw has a string feature thing to use
	private Color cHidden;
	private Color cRevealed;
	private Color bomb;
	private Color bombExplode;
	
	public Square(int xIn, int yIn){
		isRevealed = false;
		hasFlag = false;
		hasBomb = false;
		bombClicked = false;
		x = xIn;
		y = yIn;
		numBombNeighbors = 0;
		cHidden = StdDraw.GRAY;
		cRevealed = new Color(72,196,116);
		bombExplode = StdDraw.RED;
		bomb = StdDraw.BOOK_RED;
	}
	/**
	 * each square draws itself, based on factors such as if it is revealed or hidden, number of neighboring bombs, its own bomb status, and its flagged status
	 */
	public void draw(){
	
		if (this.isRevealed == false){
			StdDraw.setPenColor(cHidden);
			StdDraw.filledSquare(this.x+.45, this.y+.45, .45);
			if( this.hasFlag == true){
				StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
				StdDraw.text(this.x+.45, this.y+.45, "F");
			}
			
		} else if(this.isRevealed==true && this.isBomb() == false) {
			StdDraw.setPenColor(cRevealed);
			StdDraw.filledSquare(this.x+.45, this.y+.45, .47);
			if(this.numBombNeighbors > 0){
				StdDraw.setPenColor(StdDraw.WHITE);
				StdDraw.text(this.x+.45, this.y+.45, String.valueOf(this.numBombNeighbors));
			
			}
		}else if(this.bombClicked == true){
			StdDraw.setPenColor(bombExplode);
			StdDraw.filledSquare(this.x+.45, this.y+.45, .47);
			
		}else {
			StdDraw.setPenColor(bomb);
			StdDraw.filledSquare(this.x+.45, this.y+.45, .47);
		}
		
	}
	
	/**
	 * get the x-coordinate of a square
	 * @return int -- the x-coordinate of a square
	 */
	public int getX(){	return this.x;}
	
	/**
	 * get the y-coordinate of a square
	 * @return int -- the y-coordinate of a square
	 */
	public int getY(){return this.y;}
	
	/**
	 * sets the boolean for bombClicked to true (should be called when a bomb is clicked)
	 */
	public void setBombClick(){	this.bombClicked = true;}
	
	/**
	 * gets the information on if a square has been revealed yet
	 * @return boolean -- true if has been revealed, false if not yet
	 */
	public boolean checkReveal(){return this.isRevealed;}
	
	/**
	 * sets the value for the isRevealed boolean for a square (should be called when a square is clicked)
	 */
	public void reveal(){this.isRevealed = true;}
	
	/**
	 * gets the flagged status of a square
	 * @return boolean - true if the square has been flagged, false if not yet
	 */
	public boolean beenFlagged(){return this.hasFlag;}
	
	/**
	 * gets the bomb-notbomb status of a square
	 * @return boolean -- true if square contains a mine, false if square is empty
	 */
	public boolean isBomb(){return this.hasBomb;}
	
	/**
	 * adds a bomb to a square by changing the boolean, will only work if there is no bomb already in the square
	 */
	public void setBomb(){
		if (this.hasBomb == false){
			this.hasBomb=true;
		} 
	}
	
	/**
	 * toggles flags on/off for a square - if flag is on, will go to off, and vice versa
	 */
	public void toggleFlag(){
		if(this.hasFlag == false){this.hasFlag = true;} 
		else{this.hasFlag = false;}
	}
	
	/**
	 * sets the numBombNeighbors value for a square based function input parameters
	 * @param count -- int, the number of neighboring bombs a square has
	 */
	public void setBombNeighbors(int count){this.numBombNeighbors = count;}

	/**
	 * used for recursive clicking, gets number value that would be displayed in the square
	 * @return int -- the number of neighboring bombs a square has
	 */
	public int getBombNeighbors(){return this.numBombNeighbors;}
}