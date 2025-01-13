import java.io.*;
import java.util.*;
//using Big Table

class Main  {
    
	int L;//the lengh of the ferry
	ArrayList<Integer> integers;//Array that has the length of the cars 
	int bestK;                  // Tracks the maximum number of cars added
    int[] bestX, currX;         // Arrays to track the solution and the current path
    boolean[][] visited; // Big Table to track visited states 
   
	public void BacktrackSolve(int currK, int currS) {
		// Update the best solution if the current one is better
		if (currK > bestK) {
			bestK = currK;
			bestX = new int[bestK];
			System.arraycopy(currX, 0, bestX, 0, currK); // Copy current solution to best solution
		}
	
		// If there are still cars to consider
		if (currK < integers.size()) {
			int carLength = integers.get(currK); // Length of the current car

			int currCarSum = 0;//la somme de la longuer des voiture placé
			for(int i=0; i <currK; i++)
			{
				currCarSum = currCarSum + integers.get(i);
			}
	
			// Attempt to place the car on the left (babord)
			if (currS >= carLength && !visited[currK+1][currS-carLength]){
				
					currX[currK] = 1; // Mark car as placed on babord
					int newS = currS - carLength;
					BacktrackSolve(currK + 1, newS);
					visited[currK + 1][currS - carLength] = true;
					
				
			}
	
			// Attempt to place the car on the right (tribord)
			 // Calculate the space used on tribord
			 
			if (L -currCarSum +L -currS >= carLength && !visited[currK + 1][currS]) {
				
					currX[currK] = 0; // Mark car as placed on tribord
					BacktrackSolve(currK + 1, currS);
					visited[currK + 1][currS] = true;
					
				}
			}
		}
	
	

	
	// reads each problem from input file and call method to solve and print output   
	public void process() throws FileNotFoundException {
	    
		Scanner scanner = new Scanner(System.in);
	
		if (scanner.hasNextInt()) {
	        int numTests=scanner.nextInt(); // reads the number of test cases
		    for (int i=0; i< numTests; i++) {
		       if (i>0) System.out.println(); // printing line to standard output to separate outputs for problems
			   integers=new ArrayList<Integer>();
		       int cNum=scanner.nextInt(); // this line contains the length of the ferry (L) in meters
			   L=0;
		       if (cNum !=0) { 
		    	   L=cNum*100; // convert L from meters to centimeters
       		       while ((cNum=scanner.nextInt()) !=0) // this reads final line containing 0
		    	       integers.add(cNum); // this reads the length of each car
		       }
			   // Handle edge cases
			   if (integers.size() == 0 || L < integers.get(0)) {
				System.out.println(0); // No cars can be accommodated
				continue;
			}
		      
		      // Initialize variables for the current test case
			  bestK = -1;
			  bestX = new int[integers.size()];
			  currX = new int[integers.size()];
			  visited = new boolean[integers.size() + 1][L  + 1]; // Big Table initialization
  
			  // Call the recursive function
			  BacktrackSolve(0, L); 
  
			  // Print the results
			  System.out.println(bestK);
			  for (int j = 0; j < bestK; j++) {
				  System.out.println(bestX[j] == 1 ? "port" : "starboard");
			  }
			   

		    }	    
	
        }
		
	}

	
	
	public static void main(String[] args) {
	
        Main solver= new Main();
        
        try {
        solver.process();
        }
        catch(FileNotFoundException e) {
        	System.err.println("Error file not found!");
        }
		catch(Exception e) {
			System.err.println("Some error exception found!");
			

		}
		
	}

}
