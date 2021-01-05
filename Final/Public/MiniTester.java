import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class MiniTester
{
    /*																NOTICE
     *							Enter the absolute address of the mycases folder as: "...\\src\\...\\mycases"
     *							e.g. "C:\\Users\\Me\\JavaWorkspace\\src\\project\\mycases" if mycases is in the project folder
     */
    static String directoryPath = "/Users/amelia/Desktop/mycases";



    public static void main(String[] args)
    {
        int score = 0;
        int total = 21;
        /*
         *															NOTICE
         *	If you get different results in different row orderings in a test case, but wonder whether the answer the original input file is correct,
         *	enter 0 for the argument permute (3rd argument) instead of 1 in that particular call of the method test.
         */
        score+=test(1, 1, 1); // one state
        score+=test(2, -1, 1); // state tie
        score+=test(3, -1, 1); // national tie
        score+=test(4, 8, 1); // equally assigned delegates, all could be won
        score+=test(5, 1, 1); // one dominating state, all could be won
        score+=test(6, -1, 1); // one dominating state
        score+=test(7, -1, 1); // delegates 1-7, national tie
        score+=test(8, 17, 1); // delegates 1-7, national tie, all could be won
        score+=test(9, 20, 1); // delegates 1-7, national tie, all could be won
        score+=test(10, 5, 1); // one major state, all could be won
        score+=test(11, 16, 1); // one major state, national tie, all could be won
        score+=test(12, 16, 1); // one major state, national tie, all could be won
        score+=test(13, 1016, 0); // two dominating states, national tie, all could be won
        score+=test(14, 92, 1); // one dominating state
        score+=test(15, 179, 1); // votes edge cases
        score+=test(16, -1, 1); // votes edge cases
        score+=test(17, 15, 1); // votes needed and delegates proportional
        score+=test(18,6,1); // votes needed and delegates anti-proportional
        score+=test(19,252,1); // votes needed and delegates anti-proportional
        score+=test(20,7,1); // votes needed and delegates anti-proportional
        score+=test(21,51,1); // votes needed and delegates anti-proportional
        System.out.println("Score: " + score + "/" + total + "\n");

        stressTest(100, 2000, 100, 1000, 0);
        System.out.println("\nYou can modify the inputs of the stress test.");
    }

    public static void stressTest(int numStates, int totalDelegates, int numWorst, int numTrials, int randomSeed)
    {
        // gives an approximation of runtime given total numbers of delegates and states
        if (numTrials < 5)
        {
            System.out.println("the minimum number of trials is 5");
            return;
        }
        System.out.println("Testing running time for cases with [" + numStates + "] states and about a total of [" + totalDelegates + "] delegates...");
        double[] timeStamps = new double[numTrials];
        int[] votes_Biden = new int[numStates];
        int[] votes_Trump = new int[numStates];
        int[][] votes_Undecided = new int[numTrials][numStates];
        Random rand = new Random(randomSeed);
        for (int i=0; i<numStates; i++)
        {
            votes_Trump[i]=1;
        }
        int[][] delegates = new int[numTrials][numStates];
        // randomize the number of delegates and undecided votes for each state
        for (int i=0; i<numTrials; i++)
        {
            for (int j=0; j<numStates; j++)
            {
                // 4/5 chance to win
                votes_Undecided[i][j] = rand.nextInt(10);
            }
            delegates[i] = randVecFixedSum(numStates, 1, totalDelegates, randomSeed);
        }
        double time;
        try
        {
            for (int i=0; i<numTrials; i++)
            {
                time = System.nanoTime();
                US_elections.solution(numStates, delegates[i], votes_Biden, votes_Trump, votes_Undecided[i]);
                timeStamps[i] = System.nanoTime() - time;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.println("Stress test quit due to an error occurred during the execution of your solution");
            return;
        }
        Arrays.sort(timeStamps);
        double totalTime = 0;
        for (int i=1; i<numWorst+1; i++)
        {
            totalTime += timeStamps[timeStamps.length-i];
        }
        totalTime = Math.round(totalTime/(1000.0))/1000.0; // now in milliseconds
        int reference = 0;
        for (int i=0; i<numWorst; i++)
        {
            reference+=f(numStates, totalDelegates);
        }
        double referenceTime = Math.round(reference/(1000.0))/1000.0;
        System.out.println("Running time for reference: " + referenceTime + "ms");
        System.out.println("Approximated total running time for [" + numWorst + "] worst cases in ["+ numTrials+ "] trials: " + totalTime + "ms");
        if (numStates>totalDelegates)
            System.out.println("Note that the approximation is significantly inaccurate when the number of states is almost equal or greater than the total number of delegates.");
    }

    public static int[] randVecFixedSum(int n, int lower, int sum, int randomSeed)
    {
        // returns a random vector of length n with entries >= lower, summing up to sum
        Random rand = new Random(randomSeed);
        int[] vector = new int[n];
        vector[n-1] = sum;
        sum -= lower*n;
        for (int i=0; i<n-1; i++)
        {
            vector[i] = rand.nextInt(sum) + lower;
        }
        Arrays.sort(vector);
        for (int i=n-1; i>0; i--)
        {
            vector[i] -= vector[i-1];
        }
        // Fisher-Yates shuffle
        int j;
        int tmp;
        for (int i=0; i<n; i++)
        {
            j = rand.nextInt(n-i)+i;
            tmp = vector[i];
            vector[i] = vector[j];
            vector[j] = tmp;
        }

        return vector;
    }

    public static long f(int _______, int _________){long ________;long _____;long ____________=1;long ___________;long ______;long __________=10;________ = System.nanoTime();for(_____=0; _____<_______; _____++){for (______=0; ______<_________; ______++){__________ = __________*_____*______;for(___________=0;___________<____________;___________++) {__________++;}}}return System.nanoTime()-________;}

    public static int test(int caseNum, int answer, int permute)
    {
        int tmp;
        tmp = testFile(directoryPath + "/c"+ caseNum + ".txt", permute);

        System.out.print("test case " + caseNum + " ");
        if (answer!=tmp)
        {
            System.out.print("failed ");
            if (tmp==-2)
            {
                System.out.println("due to inconsistent results in different row orderings");
            }
            else if (tmp==-3)
            {
                System.out.println("due to file not found.");
            }
            else if (tmp==-4)
            {
                System.out.println("due to an error occurred during execution");
            }
            else
            {
                System.out.println("- expected answer: " + answer + ", your answer: " + tmp);
            }
            return 0;
        }
        else
        {
            System.out.println("passed");
            return 1;
        }

    }

    public static int testFile(String path, int permute) {
        try {
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

            int answer = US_elections.solution(num_states, delegates, votes_Biden, votes_Trump, votes_Undecided);
            if (permute == 0)
            {
                return answer;
            }

            int[][] delegates_permutation = permutation(delegates);
            int[][] Biden_permutation = permutation(votes_Biden);
            int[][] Trump_permutation = permutation(votes_Trump);
            int[][] Undecided_permutation = permutation(votes_Undecided);
            int tmp;
            for (int i=0; i<Biden_permutation.length; i++)
            {
                tmp = US_elections.solution(num_states, delegates_permutation[i], Biden_permutation[i], Trump_permutation[i], Undecided_permutation[i]);
                if (tmp != answer)
                    return -2;
            }
            return answer;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return -3;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return -4;
        }

    }

    public static int[][] permutation(int[] arr)
    {
        ArrayList<Integer> tmp = new ArrayList<Integer>();
        for (int i=0; i<arr.length; i++)
            tmp.add(arr[i]);
        ArrayList<ArrayList<Integer>> permutation = permutation(tmp);
        int[][] result = new int[permutation.size()][arr.length];

        for (int i=0; i<permutation.size(); i++)
        {
            tmp = permutation.get(i);
            for (int j=0; j<tmp.size();j++)
                result[i][j] = tmp.get(j);
        }
        return result;
    }

    public static ArrayList<ArrayList<Integer>> permutation(ArrayList<Integer> arr)
    {
        ArrayList<ArrayList<Integer>> result;
        ArrayList<ArrayList<Integer>> lastPermutation;
        ArrayList<Integer> lastArray = new ArrayList<Integer>(arr);
        ArrayList<Integer> tmp;

        lastArray.remove(arr.size()-1);

        if (lastArray.size() == 0)
        {
            result = new ArrayList<ArrayList<Integer>>();
            result.add(arr);
            return result;
        }
        else
        {
            lastPermutation = permutation(lastArray);
            result = new ArrayList<ArrayList<Integer>>(lastPermutation.size()*arr.size());
            for (int i=0; i<lastPermutation.size(); i++)
            {
                for (int j=0; j<arr.size(); j++)
                {
                    tmp = new ArrayList<Integer>(lastPermutation.get(i));
                    tmp.add(j, arr.get(arr.size()-1));
                    result.add(tmp);
                }
            }
            return result;
        }
    }
}
