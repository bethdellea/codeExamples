

/***
 * 
 * @author Beth Dellea
 * A class that will simulate a fire burning, given parameters for the number of units to burn and the likelihood of fire spreading.
 * The main function also prints out how much of the area burned and how long it took, once the fire has burned out.
 *
 */

public class FireSimulator {
	private double probable;
	public Terrain t;
	private int xNum;
	private int yNum;

	public FireSimulator (int x, int y, double prob){
		this.xNum = x;
		this.yNum = y;
		this.t = new Terrain(x,y);
		this.probable = prob;
	}
	/**
	 * starts the fire at a random location within the forest grid
	 */
	public void startFire(){
		int xRand = (int) (Math.random()*xNum);
		int yRand = (int)(Math.random()*yNum);
		t.setBurning(xRand,yRand);
		t.update();
	}
	
	/**
	 * determines the spread of the fire by evaluating each forest square and those directly touching it
	 * if a neighboring square is on fire, uses the probability to determine if the current square will catch fire
	 * if the current square is burning it will be "empty" next time, and if it is empty it will remain empty.
	 */
	public void spread(){
		for (int i=0; i<xNum; i++){
			for (int j=0; j<yNum; j++){
				if (t.checkState(i, j)==2){		
					if (t.hasBurningNeighbor(i, j) == true){
						double likely = Math.random();
						if (likely < probable){ t.setBurning(i, j);}
						}
					} 
				if (t.checkState(i, j) == 1){t.setEmpty(i, j);} 
				if (t.checkState(i, j) == 0){t.maintain(i, j);}
				}
			}
		t.update(); //if the current square is empty or a forest, it should not be changed and should not change anything around it
		return;
	}
	/**
	 * Designed to be used at the end of the simulation, this determines what proportion of the area has burned
	 * @return a decimal that represents the portion of the grid that is "empty" at the time of checking
	 */
	public double amountBurned(){
		double burned = 0;
		int total = 0;
		for (int i=0; i<xNum; i++){
			for (int j=0; j<yNum; j++){
				total ++;
				if(t.checkState(i, j)==0){
					burned++;
				}
			}
		}
		return (burned/total);
	}
	/**
	 * Checks that there are still parts of the forest on fire
	 * @return true if there is still a fire somewhere, false if all squares are "empty" or "forest"
	 */
	public boolean stillBurn(){
		for (int i=0; i<xNum; i++){
			for (int j=0; j<yNum; j++){
				if (t.checkState(i,j)==1){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Runs the simulation
	 * @param args
	 */
	public static void main(String[] args){
		int numSteps = 0;
		StdOut.print("Please input the number of units in the width: ");
		int W = StdIn.readInt();
		StdOut.print("Please input the number of units in the height: ");
		int H = StdIn.readInt();
		StdOut.print("Please input the probability of a fire spreading (a decimal between 0 and 1): ");
		double P = StdIn.readDouble();
		FireSimulator fs = new FireSimulator(W,H,P);
		//keeps fire noises going while the simulator runs
		StdAudio.loop("fire1.wav");
		//will not stop until program is terminated
		fs.startFire();
		

		Boolean stillBurning=fs.stillBurn();
		while (stillBurning){
			fs.spread();
			try {Thread.sleep(1000);} catch (InterruptedException ie) {}
			numSteps++;
			stillBurning = fs.stillBurn();
		}
		System.out.println(fs.amountBurned()*100+"% of the area burned");
		System.out.println("It took "+numSteps+" steps to burn out.");
		
	}
}
