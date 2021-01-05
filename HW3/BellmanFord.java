//package F20A3;

import java.util.*;

public class BellmanFord{

    private int[] distances = null;
    private int[] predecessors = null;
    private int source;

    class BellmanFordException extends Exception{
        public BellmanFordException(String str){
            super(str);
        }
    }

    class NegativeWeightException extends BellmanFordException{
        public NegativeWeightException(String str){
            super(str);
        }
    }

    class PathDoesNotExistException extends BellmanFordException{
        public PathDoesNotExistException(String str){
            super(str);
        }
    }

    BellmanFord(WGraph g, int source) throws NegativeWeightException{
        /* Constructor, input a graph and a source
         * Computes the Bellman Ford algorithm to populate the
         * attributes 
         *  distances - at position "n" the distance of node "n" to the source is kept
         *  predecessors - at position "n" the predecessor of node "n" on the path
         *                 to the source is kept
         *  source - the source node
         *
         *  If the node is not reachable from the source, the
         *  distance value must be Integer.MAX_VALUE
         */
        int number = g.getNbNodes();
        this.source= source;
        this.distances= new int[number];
        this.predecessors= new int [number];
        for (int i = 0;i<number;i++){
            this.distances[i]= Integer.MAX_VALUE;
            this.predecessors[i]= -1;
        }
        this.distances[source]=0;
        ArrayList<Edge> edges = g.listOfEdgesSorted();
        for (int a = 1;a<number;a++){
            for (Edge e: edges){
                if(this.distances[e.nodes[0]]!=Integer.MAX_VALUE &&(this.distances[e.nodes[1]]>e.weight+this.distances[e.nodes[0]])){
                    this.distances[e.nodes[1]]= e.weight+this.distances[e.nodes[0]];
                    this.predecessors[e.nodes[1]]=e.nodes[0];
                }
            }
        }
        for (Edge e: edges){
            if ((this.distances[e.nodes[0]]!= Integer.MAX_VALUE)&&(this.distances[e.nodes[1]]>e.weight+this.distances[e.nodes[0]])){
                throw new NegativeWeightException("negative weight cycle");
            }
        }

    }

    public int[] shortestPath(int destination) throws PathDoesNotExistException{
        /*Returns the list of nodes along the shortest path from 
         * the object source to the input destination
         * If not path exists an Error is thrown
         */
        int node = destination;
        int [] paths = new int [this.distances.length];
        ArrayList<Integer> shortestPath = new ArrayList<Integer>();
        int id = 0;
        while(node != this.source){
            if (this.predecessors[node]== -1){
                throw new PathDoesNotExistException("path not exist");
            }else{
                paths[id++]= node;
                shortestPath.add(node);
                node= this.predecessors[node];
            }
        }
        paths[id++]= node;
        shortestPath.add(node);
        int[] shortest = new int[id];
        for (int i = 0; i<id;i++){
            shortest[i]= paths[(id-1-i)];
        }
       // int [] paths = new int [this.distances.length];
        //paths[0]= node;


        return shortest;
    }

    public void printPath(int destination){
        /*Print the path in the format s->n1->n2->destination
         *if the path exists, else catch the Error and 
         *prints it
         */
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++){
                int next = path[i];
                if (next == destination){
                    System.out.println(destination);
                }
                else {
                    System.out.print(next + "-->");
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){
        String file = "/Users/amelia/Downloads/HW3/bf1.txt";

        //String file = "/Users/amelia/Downloads/COMP251_A3_TestCases-main/BellmanFord/tests/bfTest6.txt";
        WGraph g = new WGraph(file);
        try{
            BellmanFord bf = new BellmanFord(g, g.getSource());
            bf.printPath(g.getDestination());
        }
        catch (Exception e){
            System.out.println(e);
        }

   } 
}

