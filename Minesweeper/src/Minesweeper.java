/****
 * 
 * @author Beth
 * December 2014
 * The main class for the Minesweeper program. This class has the menu, creates the grid, and calls that.
 * 
 */

import java.awt.event.KeyEvent;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Minesweeper {

	private Grid g;
	private Scanner sc;
		
	public Minesweeper(){
		this.sc = new Scanner(System.in);
	}
	
	/**
	 * Prints out the menu of basic gameplay, outlining motions and levels
	 */
	public static void printMenu(){
		System.out.println("Welcome to Minesweeper!\n\t*Click to reveal square \n\t*Press 'F' key and click to toggle flag\n \nPlease select your level: ");
		System.out.println("1) Beginner (10x10 grid, 10 Mines)\n2) Intermediate (16x16 grid, 40 Mines)\n3) Expert (16x30 grid, 99 Mines)\n4) Custom (You input height, width, and mines)\n5) Exit this program");
	} //change this -- instead of exit program in here, have a menu for exit/new game
	
	/**
	 * When a general menu is used, this guides user input so that it is valid
	 * @param min -- one below the lowest input number accepted in the menu
	 * @param max -- one above the highest input number accepted in the menu
	 * @return
	 */
	public int getMenuOption(int min, int max){
		//repeatedly ask user for choice until they give a valid int w/in range
		
		int choice = -1;
		while (true){
			choice = -1;
			System.out.print("Choose your level: ");
			try{
				choice = this.sc.nextInt();
				
			} catch(InputMismatchException ime){
				this.sc.nextLine();
				System.out.println("Please enter a valid integer input");
				continue;
			}
			
			if ( choice < max && choice > min){
				//sc.close();
				return choice;
			} else{
				System.out.println("Plese input an integer that is within the acceptable range.");
			}	
		}
	}
	
	/**
	 * when using a custom grid, prompts users for proper input and returns it
	 * @return int -- the input height for the grid
	 */
	public int getCustomH(){
		int customH = 0;
		int holdH = 0;
		while(true){ //searches for positive, integer input here. no max height, but it would impede gameplay if the entire window were not visible
					//that's more or less the user's own challenge, though
			System.out.println("Enter a desired height for the game grid: ");
			try{
				if(sc.hasNextDouble()){
					holdH = sc.nextInt();
				}	
			}catch (InputMismatchException ime){
				sc.nextLine();
				System.out.println("Please enter a valid integer input");
				continue;
			}
			if (holdH > 0){
				customH = holdH;
				break;
			} else{System.out.println("Please enter a positive number for grid height.");}
	
		}
		return customH;
	}
	/**
	 * when using a custom grid, prompts users for proper input and returns it
	 * @return int -- the input width for the grid
	 */
	public int getCustomW(){
		int customW = 0;
		int holdW = 0;
		while(true){//searches for positive, integer input here. no max width, but it would impede gameplay if the entire window were not visible
			//that's more or less the user's own challenge, though
			System.out.println("Enter a desired width for the game grid: ");
			try{
				if(sc.hasNextDouble()){
					holdW = sc.nextInt();
				}	
			}catch (InputMismatchException ime){
				sc.nextLine();
				System.out.println("Please enter a valid integer input");
				continue;
			}
			if (holdW > 0){
				customW = holdW;
				break;
			} else{System.out.println("Please enter a positive number for grid width.");}
	
		}
		return customW;
	}
	/*
	 * when using a custom grid, prompts users for proper input and returns it --- input must be a real, positive, whole number
	 * @param h -- the integer value of the height of the custom game
	 * @param w -- the integer value of the width of the custom game
	 * number of mines placed cannot exceed (h*w)-2
	 * @return int -- the input for number of mines in the grid
	 */
	public int getCustomM(int h, int w){
		System.out.println("Please enter the number of mines you would like in this game. \n\t(NOTE: number of mines must be greater than zero but no more than the total number of squares -2.");
		int customM = getMenuOption(0,(h*w)-1);
		
		return customM;
	}
	/**
	 * The function that handles the user menu and how, exactly, it loops. Technically, each time a user plays, it re-points the g variable to a new object
	 */
	public void running(){
		int choice = 0;
		while(choice !=-1){
			printMenu();
			choice = getMenuOption(0,6);
			if (choice == 1){
				Grid g = new Grid (10,10,10);
				g.run();
				System.out.println("Game over. Please choose another option\n");
			} else if(choice == 2){
				Grid g = new Grid(16,16,40);
				g.run();
				System.out.println("Game over. Please choose another option\n");
			} else if(choice == 3){
				Grid g = new Grid(16,30,99);
				g.run();
				System.out.println("Game over. Please choose another option\n");
			} else if(choice == 4){
				int h = this.getCustomH();
				int w = this.getCustomW();
				int m = this.getCustomM(w,h);
				Grid g = new Grid(h,w,m);
				g.run();
				//custom game input options hereeeee
				System.out.println("Game over. Please choose another option.\n");
			} else{
				System.out.println("Thank you for considering our games today!\n");
				choice = -1;
			}
		}
				
	}

	
	public static void main (String[] args){
		Minesweeper game1 = new Minesweeper();
		game1.running();
		game1.sc.close();
	}
}



