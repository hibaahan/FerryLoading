import java.io.*;
import java.util.*;

//Hash Table
class Main  {
    
	int L;//the lengh of the ferry
	ArrayList<Integer> integers;//Array that has the length of the cars 
	int bestK;                  // Tracks the maximum number of cars added
    int[] bestX, currX;         // Arrays to track the solution and the current path
    static int[] stateTableHash;
	static int hashTableSize ; // Big Table to track visited states 
   
    static int hashFuntion(int key, int value){
    return (key + value) % hashTableSize;// the use of modulo in order to stay in the index range of the table and also this fubction uses key and value which will 
}
    //insert in the hashtable if there is a collission use linear brobing move to the next index
    static void insert(int key, int value){
    int hashValue = key + value;
    int hashPos = hashFuntion(key, value);
    while(stateTableHash[hashPos] != -1){//the position is occupied by another state 
        ++hashPos;               // Linear probing
        hashPos %= hashTableSize;
    }
    stateTableHash[hashPos] = hashValue;//inserer la position
}

static boolean find(int key, int value){
    int hashValue = key + value;
    int hashPos = hashFuntion(key, value);
    while(stateTableHash[hashPos] != -1){//the position is occupied by another state
        if(stateTableHash[hashPos] == hashValue){
            return true;  // State already processed
        }
        ++hashPos;        // Linear probing
        hashPos %= hashTableSize;
    }
    return false;  // State not found
}




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

			int currCarSum = 0;//The sum of the lenght of the car taht are loaded in the ferry
			for(int i=0; i <currK; i++)
			{
				currCarSum = currCarSum + integers.get(i);
			}
			// Add car to left
			int key = currK + 1;
			int val = currS-carLength;
	
			// Attempt to place the car on the left (babord)
			if (currS >= carLength && !find(key,val)){
				
					currX[currK] = 1; // Mark car as placed on babord(port)
					int newS = currS - carLength;
					BacktrackSolve(currK + 1, newS);
					insert(key,val);
					
				
			}
	
			// Attempt to place the car on the right (tribord)
			 // Calculate the space used on tribord
			 
			if (L -currCarSum +L -currS >= carLength && !find(key,currS)) {
				
					currX[currK] = 0; // Mark car as placed on tribord
					
					BacktrackSolve(currK + 1, currS);
					insert(key,currS);

					
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
			  hashTableSize=L+ integers.size();
			  stateTableHash = new int[hashTableSize]; // Big Table initialization
			  Arrays.fill(stateTableHash, -1);//fill our table with -1 to verify if the entry is occuppied yes or no
  
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
			System.err.println("Some error exception found!"+ e.getMessage());
			e.printStackTrace();

		}
		
	}

}
