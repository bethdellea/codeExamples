/****
 * @author Beth
 * December 2014
 * the Grid class for the minesweeper lab. This class handles most of the actual gameplay functionality
 * 
 * 
 * STILL NEEDS RECURSIVELY CLICK WHEN CLICK A ZERO BUT DON'T 'click' ON FLAGGED SQUARESSSSSSSSS
 * also STILL NEEDS to not let users click a bomb on their first click -- don't place bombs until after first click
 */

import java.awt.event.KeyEvent;

public class Grid {

	private Square[][] terrain;
	private int H;
	private int W;
	private int numMines;
	private int flagCount;
	private boolean gameLost;
	private boolean gameWon;
	private boolean gameOver;
	
	
	public Grid(int hIn, int wIn, int minesIn){
		H = hIn;
		W = wIn;
		numMines = minesIn;
		terrain = new Square[W][H];
		flagCount = 0;
		gameLost = false;
		gameWon = false;
		gameOver = false;

		StdDraw.setCanvasSize(W*20,H*30);
		StdDraw.setXscale(0,W);
		StdDraw.setYscale(0,H+1);
		StdDraw.textLeft(.5, H+1, "Flags Left: "+String.valueOf(this.numMines));
	    for (int i = 0; i<W; i++){
	    	for (int j=0; j <H; j++){
	    		terrain[i][j] = new Square(i,j);
	    	}
	    }
	}

	/**
	 * Uses try/catch to check all 8 directions for bombs, uses accumulator pattern to find total bomb neighbors,
	 * assigns that value and works its way through the grid in that manner.
	 */
	public void countNeighborBomb(){
		for (int i = 0; i<this.W; i++){
	    	for (int j=0; j <this.H; j++){
	    		if (this.terrain[i][j].isBomb()==false){  //only count neighboring bombs if current square is NOT a bomb itself
		    		int bombsCounted = 0;
		    		try{ //directly right
		    			if(this.terrain[i+1][j].isBomb()){bombsCounted++;}
		    		}catch(ArrayIndexOutOfBoundsException AIOOBE){}
		    		try{ //directly left
		    			if(this.terrain[i-1][j].isBomb()){bombsCounted++;}
		    		}catch(ArrayIndexOutOfBoundsException AIOOBE){}
		    		try{ //directly below
		    			if(this.terrain[i][j-1].isBomb()){bombsCounted++;}
		    		}catch(ArrayIndexOutOfBoundsException AIOOBE){}
		    		try{ //directly above
		    			if(this.terrain[i][j+1].isBomb()){bombsCounted++;}
		    		}catch(ArrayIndexOutOfBoundsException AIOOBE){}
		    		try{ //above + left
		    			if(this.terrain[i-1][j+1].isBomb()){bombsCounted++;}
		    		}catch(ArrayIndexOutOfBoundsException AIOOBE){}
		    		try{ //above + right
		    			if(this.terrain[i+1][j+1].isBomb()){bombsCounted++;}
		    		}catch(ArrayIndexOutOfBoundsException AIOOBE){}
		    		try{ //below + left
		    			if(this.terrain[i-1][j-1].isBomb()){bombsCounted++;}
		    		}catch(ArrayIndexOutOfBoundsException AIOOBE){}
		    		try{ //below + right
		    			if(this.terrain[i+1][j-1].isBomb()){bombsCounted++;}
		    		}catch(ArrayIndexOutOfBoundsException AIOOBE){}
		    		
		    		this.terrain[i][j].setBombNeighbors(bombsCounted);
	    		}else{this.terrain[i][j].setBombNeighbors(-1);}
	    	}
		}
		
	}
	
	/**
	 * when a game is lost (by clicking on a mine), reveals all squares on the grid and ends the game
	 */
	public void gameLost(){
		
		this.gameLost=true;

		for (int i = 0; i<this.W; i++){
	    	for (int j=0; j <this.H; j++){
	    		this.terrain[i][j].reveal();
	    	}
		}

	//	this.gameOver = true;
	}
	
	/**
	 * Generates a random x and y coordinate. If the square already has a bomb, no bomb may be placed. If the space does not have a bomb yet, the bomb will
	 * be placed. Continues until all allotted bombs have been placed.
	 */
	public void placeBombs(){
		int bombsPlaced = 0;
		while(bombsPlaced<this.numMines){
			int bombX = (int)(Math.random()*W);
			int bombY = (int)(Math.random()*H);
			
			if (!this.terrain[bombX][bombY].isBomb() && !this.terrain[bombX][bombY].checkReveal()){
				this.terrain[bombX][bombY].setBomb();
				bombsPlaced ++;
			}
		}
		return;
	}
	
	/**
	 * updates boolean variable checked in while loop of gameplay based on the two ways a game can end
	 */
	public void endGameCheck(){
		if (gameWon() == true){
			this.gameOver=true;
			
			StdAudio.play("fanfare.wav");}
		else if (this.gameLost == true){
			this.gameOver=true;
			
			StdAudio.play("Sad_Trombone.wav");} 
	}
	
	/**
	 * dictates the case in which the game is won, and the actions to be taken then
	 * @return boolen -- true if game has been won, false if game is still in progress or lost
	 */
	public boolean gameWon(){
		int notRevealed = 0;

		for (int i = 0; i<this.W; i++){
	    	for (int j=0; j <this.H; j++){
	    		if(this.terrain[i][j].checkReveal() == false){
	    			notRevealed++;
	    		}
	    	}
	    }
		if (notRevealed == this.numMines){
			this.gameWon = true;
			this.gameOver = true;

			this.gameWon=true;
			
			return true;
			}	//works as a test for if the game is won because if you reveal a mine your game is over!!
		else{return false;}
	}
	
	/**
	 * Counts up the total flags placed by player, used for flag countdown in upper left corner
	 * @return int -- the number of flags that have been placed on the grid
	 */
	public int getFlagCount(){
		int totalFlags = 0;
		
		for (int i = 0; i<this.W; i++){
	    	for (int j=0; j <this.H; j++){
	    		if(this.terrain[i][j].beenFlagged() == true){totalFlags++;}
	    	}
	    }
		this.flagCount = totalFlags;
		return totalFlags;
	}
	
	/**
	 * when a 0 square is clicked, clicks all adjoining squares that are 0 squares as well
	 * @param xIn -- int -- the x-location of the square to recursively click
	 * @param yIn -- int -- the y-location of the square to recursively click
	 */
	public void recursiveClick(int xIn, int yIn){
		int currX = xIn;
		int currY = yIn;
		
		if (this.terrain[currX][currY].getBombNeighbors()==0 && this.terrain[currX][currY].beenFlagged()==false){  //only simulate clicks if it's a 0 square
    		try{ //directly right
    			if(this.terrain[currX+1][currY].checkReveal() || this.terrain[currX+1][currY].beenFlagged()){}
    			else{ //if statement stops it from overflowing by continuing to try and click ones that are already clicked or flagged
    				this.terrain[currX+1][currY].reveal();
    				recursiveClick(currX+1, currY); //call this function again and keep doing it
    			}
    		}catch(ArrayIndexOutOfBoundsException AIOOBE){}
    		try{ //directly above
    			if(this.terrain[currX][currY+1].checkReveal() || this.terrain[currX][currY+1].beenFlagged()){}
    			else{
    				this.terrain[currX][currY+1].reveal();
    				recursiveClick(currX, currY+1); //call this function again and keep doing it
    			}
    		}catch(ArrayIndexOutOfBoundsException AIOOBE){}
    		try{ //directly left
    			if(this.terrain[currX-1][currY].checkReveal() || this.terrain[currX-1][currY].beenFlagged()){}
    			else{
    				this.terrain[currX-1][currY].reveal();
    				recursiveClick(currX-1, currY); //call this function again and keep doing it
    			}
    		}catch(ArrayIndexOutOfBoundsException AIOOBE){}
    		try{//directly below
    			if(this.terrain[currX][currY-1].checkReveal() || this.terrain[currX][currY-1].beenFlagged()){}
    			else{
    				this.terrain[currX][currY-1].reveal();
    				recursiveClick(currX, currY-1); //call this function again and keep doing it
    			}
	    		}catch(ArrayIndexOutOfBoundsException AIOOBE){}
    		try{ //above + left
    			if(this.terrain[currX-1][currY+1].checkReveal() || this.terrain[currX-1][currY+1].beenFlagged()){}
    			else{
    				this.terrain[currX-1][currY+1].reveal();
    				recursiveClick(currX-1, currY+1); //call this function again and keep doing it
    			}
    		}catch(ArrayIndexOutOfBoundsException AIOOBE){}
    		try{ //above + right
    			if(this.terrain[currX+1][currY+1].checkReveal() || this.terrain[currX+1][currY+1].beenFlagged()){}
    			else{
    				this.terrain[currX+1][currY+1].reveal();
    				recursiveClick(currX+1, currY+1); //call this function again and keep doing it
    			}
    		}catch(ArrayIndexOutOfBoundsException AIOOBE){}
    		try{ //below + left
    			if(this.terrain[currX-1][currY-1].checkReveal() || this.terrain[currX-1][currY-1].beenFlagged()){}
    			else{
    				this.terrain[currX-1][currY-1].reveal();
    				recursiveClick(currX-1, currY-1); //call this function again and keep doing it
    			}
    		}catch(ArrayIndexOutOfBoundsException AIOOBE){}
    		try{ //below + right
    			if(this.terrain[currX+1][currY-1].checkReveal() || this.terrain[currX+1][currY-1].beenFlagged()){}
    			else{
    				this.terrain[currX+1][currY-1].reveal();
    				recursiveClick(currX+1, currY-1); //call this function again and keep doing it
    			}
    		}catch(ArrayIndexOutOfBoundsException AIOOBE){}
    		return;
		}else{return;}
		
	}
	
	/**
	 * the function that reads for the first click on the grid, and waits to place the bombs until after that has happened
	 */
	public void firstClick(){
		boolean mouseDown = false;
		this.updateScreen();
		int startClicks = 0;
		while(startClicks<1){
			if (StdDraw.mousePressed() && mouseDown == false){
				mouseDown = true;
				int yStart = (int)StdDraw.mouseY();
				int xStart = (int)StdDraw.mouseX();
				try{	//should wait to set bombs until first valid mouse click
					if (yStart == this.terrain[xStart][yStart].getY() && xStart == this.terrain[xStart][yStart].getX()){
						this.terrain[xStart][yStart].reveal();
						this.placeBombs(); //place bombs before game fun begins
						this.countNeighborBomb(); //count neighbors and set that for each square before game fun begins
						
						if(this.terrain[xStart][yStart].getBombNeighbors() == 0){recursiveClick(xStart,yStart);}
						startClicks++;
					}
				}catch(ArrayIndexOutOfBoundsException AIOOBE){ continue;} 
			}
			mouseDown = false;
		}
		return;
	}
	/**
	 * Has everything redraw, including value for number of flags left, every 50ms
	 */
	public void updateScreen(){
		StdDraw.clear();
		this.getFlagCount();
		String flagNum = String.valueOf(this.numMines-this.flagCount); //countdown of flags to be placed, will start at number of mines, decrease as placed
	    StdDraw.setPenColor(StdDraw.BLACK);
	    StdDraw.textLeft(.5, H+1, "Flags Left: "+flagNum);
		for (int i=0; i<this.W; i++){
			for (int j=0; j<this.H; j++){
				this.terrain[i][j].draw();
			}
		}
		StdDraw.show(50);
	}
	
	/**
	 * Runs the clicking pars of the game, until the game has been won or lost
	 */
	public void run(){
		boolean mouseDown = false;
		boolean keyDown = false;
		this.firstClick();
		
		while (this.gameOver == false){//keep going until the game ends
			
			this.updateScreen();
			if (StdDraw.mousePressed() && mouseDown == false){
				mouseDown = true;
				int y = (int)(StdDraw.mouseY());
				int x = (int)(StdDraw.mouseX());
				try{ //forces program to not freak out if user clicks outside of the grid area
					//toggle flag on squares that have yet to be revealed
					if (StdDraw.isKeyPressed(KeyEvent.VK_F) && keyDown == false && y == terrain[x][y].getY() && x == terrain[x][y].getX() && terrain[x][y].checkReveal() == false){
					//	System.out.println("This is the bit where the flagging happens");
						/*
						 * last line left over from error checking, remains commented out because the error was not solved
						 * 		this if block is always ignored when this class's run function is called from Minesweeper. Flagging is not possible in that class
						 * 			but flagging is possible if this class is run -- level just has to be manually created
						 */
						keyDown = true;
						terrain[x][y].toggleFlag();
						keyDown=false;
					} else if (y == this.terrain[x][y].getY() && x == this.terrain[x][y].getX() && this.terrain[x][y].checkReveal() == false){
						//reveal squares that are clicked -- if flagged, remove the flag while it clicks so flag count stays accurate
						this.terrain[x][y].reveal();
						if(this.terrain[x][y].getBombNeighbors() == 0){recursiveClick(x,y);}
						if (this.terrain[x][y].beenFlagged()){this.terrain[x][y].toggleFlag();} //this is the bit that removes the flag that I was just talking about
						if (this.terrain[x][y].isBomb()){ //if player clicked on a bomb was not flagging, the game should end
							this.terrain[x][y].setBombClick(); //identifies the bomb that was clicked to lose; it will display in "StdDraw.RED" instead of "StdDraw.BOOK_RED"
							this.gameLost();
						}
					}
					
				} catch(ArrayIndexOutOfBoundsException AIOOBE){ continue;}

			}  else if (StdDraw.mousePressed() == false){
				mouseDown = false;
			}
			
			this.endGameCheck(); //check if the game has ended
		}
		this.updateScreen();	
		//display win/lose message at end of game, but out of the way so players can still see their game
		if(this.gameWon == true){
			StdDraw.setPenColor(StdDraw.DARK_GRAY);
			StdDraw.filledRectangle(0, this.H-.25, this.W+1, .5);
			StdDraw.setPenColor(StdDraw.GREEN);
			StdDraw.text(this.W/2, H-.25, "You WON!");
			System.out.println("You Won!!!!");
		} else{
			StdDraw.setPenColor(StdDraw.DARK_GRAY);
			StdDraw.filledRectangle(0, this.H-.25, this.W+1, .5);
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.text(this.W/2, H-.25, "You Lose!");
			System.out.println("You lost!!!!");
		}
		StdDraw.show(50);
	}



public static void main(String[] args){
	Grid griddy = new Grid(10,10,10);
	griddy.run();
}
}