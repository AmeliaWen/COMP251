import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class US_elections {

	public static int solution(int num_states, int[] delegates, int[] votes_Biden, int[] votes_Trump, int[] votes_Undecided){
		//1. create 2 arrays store state weight for available states and num of votes needed to win the state
		int[] stateVotes = new int[num_states];
		int[] votesNeeded = new int[num_states];
		//int[] winned = new int[num_states];
		int win = 0;
		//winnedD: total weight of state for already winned states
		int winnedD = 0;
		//sum: total sum of delegate weights
		int sum = 0;
		//sumN: total sum of available delegate weights (max total weight we can get)
		int sumN = 0;
		//vMax: total number of available votes for each state (max undecided votes we can get)
		int vMax = 0;
		// j: number of available states
		int j = 0;
		for (int i = 0;i<num_states;i++){
			int votes = (votes_Biden[i]+votes_Trump[i]+votes_Undecided[i])/2+1-votes_Biden[i];
			if(votes<=0 || votes>votes_Undecided[i]){
				if(votes<=0) {
					win+=(-votes);
					winnedD+=delegates[i];
				}
			}else{
				stateVotes[j]=delegates[i];
				votesNeeded[j]= votes;
				vMax+=votes;
				j++;
				sumN+=delegates[i];
			}
			sum += delegates[i];
		}
		//2. calculate total num votes needed
		int total = sum/2+1-winnedD;
		//3. knapsack the rest : at least stateVotes X minimize votesNeeded V
		//0. calculate cost_max and val_max : include all solutions
		//cost_max: vMax ; val_max: sumN
		//a. votesNeeded (ci) as values V(i)
		//b. stateVotes(vi) as weights W(i)
		//c. weight limit (val_max - X) => min stateVotes can remove  j: n
		int limit =sumN- total;
		if (limit<0) return -1;// limit: new WLimit
		int K[][] = new int[j+1][limit+1];
		for (int i = 0;i<=j;i++){
			for (int w = 0;w<=limit;w++){
				if (i == 0 || w == 0){
					K[i][w]=0;
				}else if (stateVotes[i-1]<=w){
					K[i][w]=Math.max(votesNeeded[i-1]+K[i-1][w-stateVotes[i-1]],K[i-1][w]);
				}else{
					K[i][w]= K[i-1][w];
				}

			}
		}
		// result: cost_max - prev_result
		return vMax - K[j][limit];

	}

	public static void main(String[] args) {
	 try {
	 	String path = "/Users/amelia/Downloads/Public/test3";
			//String path = args[0];
      File myFile = new File(path);
      Scanner sc = new Scanner(myFile);
      int num_states = sc.nextInt();
      int[] delegates = new int[num_states];
      int[] votes_Biden = new int[num_states];
      int[] votes_Trump = new int[num_states];
 			int[] votes_Undecided = new int[num_states];	
      for (int state = 0; state<num_states; state++){
			  delegates[state] =sc.nextInt();
				votes_Biden[state] = sc.nextInt();
				votes_Trump[state] = sc.nextInt();
				votes_Undecided[state] = sc.nextInt();
      }
      sc.close();
      int answer = solution(num_states, delegates, votes_Biden, votes_Trump, votes_Undecided);
      	System.out.println(answer);
    	} catch (FileNotFoundException e) {
      	System.out.println("An error occurred.");
      	e.printStackTrace();
    	}
  	}

}