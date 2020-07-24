import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {

	static ArrayList<Integer> playerPositions = new ArrayList<>(); 
	static ArrayList<Integer> cpuPositions = new ArrayList<>();

	public static void main (String[] args){
		//This is to create the initial game board (no X's or O's inputed yet)
		char[][] gameBoard = {{' ', '|', ' ', '|', ' '},
				{'-', '+', '-', '+', '-'},
				{' ', '|', ' ', '|', ' '},
				{'-', '+', '-', '+', '-'},
				{' ', '|', ' ', '|', ' '}};
		//Indicates that the user is currently the player
		String user = "player";
		boolean skip = false;

		while(true){
			System.out.println("Which symbol would you like to use (X/O)?: ");
			Scanner scan1 = new Scanner(System.in);
			char playerSymbol = scan1.next().charAt(0); 

			while(playerSymbol != 'x' && playerSymbol != 'X' && playerSymbol != 'o' && playerSymbol != 'O'){
				System.out.println("Invalid symbol selected, please pick a valid symbol (X or O): ");
				playerSymbol = scan1.next().charAt(0);
			}

			if(playerSymbol == 'x'){
				playerSymbol = 'X';
			}
			else if(playerSymbol == 'o'){
				playerSymbol = 'O';
			}

			//prints the game board
			printGb(gameBoard);

			while(true){
				skip = false;
				//prompt user for input
				System.out.println("Enter your placement (1-9):");
				Scanner scan = new Scanner(System.in);
				int playerPos = scan.nextInt();

				//checks if valid position
				while(playerPositions.contains(playerPos) || cpuPositions.contains(playerPos)){
					System.out.println("This placement has been taken. Choose a valid placement.");
					playerPos = scan.nextInt();
				}
				//places player's symbol on location
				user = "player";
				placeSymbol(gameBoard, playerPos, user, playerSymbol);
				String result = checkWinner();

				if(result != ""){
					System.out.println(result);
					printGb(gameBoard);

					skip = resetGame(gameBoard);

				}
				if(skip == false){
					user = "cpu";
					Random rand = new Random();
					int cpuPos = rand.nextInt(9) + 1;

					while((playerPositions.contains(cpuPos) || cpuPositions.contains(cpuPos)) && (playerPositions.size() + cpuPositions.size() < 9)){
						cpuPos = rand.nextInt(9) + 1;
					}

					if(playerPositions.size() + cpuPositions.size() < 9){
						placeSymbol(gameBoard, cpuPos, user, playerSymbol);
					}
					printGb(gameBoard);
					result = checkWinner();

					if(result != ""){
						System.out.println(result);
						printGb(gameBoard);
						skip = resetGame(gameBoard);
					}
				}
			}
		}
	}

	public static boolean resetGame(char[][] gb){
		boolean correctInput = false;
		while(correctInput == false){
			System.out.println("Would you like to play again? (Y/N):");
			Scanner scan2 = new Scanner(System.in);
			char yOrN = scan2.next().charAt(0);
			if(yOrN == 'y' || yOrN == 'Y'){
				correctInput = true;

				gb[0][0] = ' ';
				gb[0][1] = '|';
				gb[0][2] = ' ';
				gb[0][3] = '|';
				gb[0][4] = ' ';

				gb[1][0] = '-';
				gb[1][1] = '+';
				gb[1][2] = '-';
				gb[1][3] = '+';
				gb[1][4] = '-';

				gb[2][0] = ' ';
				gb[2][1] = '|';
				gb[2][2] = ' ';
				gb[2][3] = '|';
				gb[2][4] = ' ';

				gb[3][0] = '-';
				gb[3][1] = '+';
				gb[3][2] = '-';
				gb[3][3] = '+';
				gb[3][4] = '-';

				gb[4][0] = ' ';
				gb[4][1] = '|';
				gb[4][2] = ' ';
				gb[4][3] = '|';
				gb[4][4] = ' ';

				printGb(gb);

				playerPositions.clear();
				cpuPositions.clear();
				
				return true;
			}
			else if(yOrN == 'n' || yOrN == 'N'){
				correctInput = true;
				//skip = true;
				System.exit(0);
			}
			else{
				System.out.println("You have entered the incorrect input please input either \'Y\' or \'N\' ");
			}
		}
		return false;
	}

	public static void printGb(char[][] gb){
		//for loop to print the initial game board
		//first for loop is for each row in the game board, i.e. for 
		//each array inside the double array
		//second loop is for each symbol in each row
		for(char[] row: gb){
			for(char c: row){
				System.out.print(c);
			}
			System.out.println();
		}
	}

	public static void placeSymbol(char[][] gb, int position, String user, char playerSymbol){

		char symbol = ' ';

		char cpuSymbol = ' ';

		if (playerSymbol == 'X'){
			cpuSymbol = 'O';
		}
		else if(playerSymbol == 'O'){
			cpuSymbol = 'X';
		}

		if(user.equals("player")){
			symbol = playerSymbol;
			playerPositions.add(position);
		}
		else if(user.equals("cpu")){
			symbol = cpuSymbol;
			cpuPositions.add(position);
		}

		switch(position){
		case 1:
			gb[0][0] = symbol;
			break;
		case 2:
			gb[0][2] = symbol;
			break;
		case 3:
			gb[0][4] = symbol;
			break;
		case 4:
			gb[2][0] = symbol;
			break;
		case 5:
			gb[2][2] = symbol;
			break;
		case 6:
			gb[2][4] = symbol;
			break;
		case 7:
			gb[4][0] = symbol;
			break;
		case 8:
			gb[4][2] = symbol;
			break;
		case 9:
			gb[4][4] = symbol;
			break;
		default:
			break;
		}

	}

	//Method for checking if either the player or cpu has won yet.
	public static String checkWinner(){
		//List of winning conditions for the rows
		List topRow = Arrays.asList(1, 2, 3);
		List midRow = Arrays.asList(4, 5, 6);
		List botRow = Arrays.asList(7, 8, 9);

		//List of winning conditions for the columns
		List leftCol = Arrays.asList(1, 4, 7);
		List midCol = Arrays.asList(2, 5, 8);
		List rightCol = Arrays.asList(3, 6, 9);

		//List of winning conditions for the diagonals
		List leftDiag = Arrays.asList(1, 5, 9);
		List rightDiag = Arrays.asList(3, 5, 7);

		//Creates a list of lists so we will be able to loop through the different variations of the winning conditions 
		// to see if the player or cpu has won
		List<List> winning = new ArrayList<List>();
		//add the winning conditions to the list of lists
		winning.add(topRow);
		winning.add(midRow);
		winning.add(botRow);
		winning.add(leftCol);
		winning.add(midCol);
		winning.add(rightCol);
		winning.add(leftDiag);
		winning.add(rightDiag);

		//loop through each winning condition list and see if the playerPositions match any of the winning conditions and 
		//if they do, return that they have won, same goes for cpu. Also checks for a tie when the total number of symbols (O and X) are equal to 9
		for(List l: winning){
			if(playerPositions.containsAll(l)){
				return "Congratulations you have won!";
			}
			if(cpuPositions.containsAll(l)){
				return "CPU wins. You have lost.";
			}
			if((playerPositions.containsAll(l) == false) && (cpuPositions.containsAll(l) == false) && (playerPositions.size() + cpuPositions.size() == 9)){
				return "The game has ended in a tie.";
			}
		}
		return "";
	}

}
