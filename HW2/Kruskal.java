import java.util.*;

public class Kruskal{

    public static WGraph kruskal(WGraph g){

        /* Fill this method (The statement return null is here only to compile) */
        WGraph result = new WGraph();
        ArrayList<Edge> edges = g.listOfEdgesSorted();
        DisjointSets p = new DisjointSets(g.getNbNodes());
        for (Edge e : edges){
            if (IsSafe(p,e)){
                p.union(e.nodes[0], e.nodes[1]);
                result.addEdge(e);
            }
        }
        
        return result;
    }

    public static Boolean IsSafe(DisjointSets p, Edge e){
        int first = e.nodes[0]; //first vertex
        int second = e.nodes[1];//second vertex
       // Boolean isSaf;
        if (p.find(first) == p.find(second)) { //if both vertices are in same parition, then not safe
            return false;
        } else {
            return true;
        }
        /* Fill this method (The statement return 0 is here only to compile) */
        //return isSaf;
    
    }

    public static void main(String[] args){
        String f = "/Users/amelia/Desktop/COMP251_A2_TestCases-master/Kruskal/kruTest3.txt";
        //String file = args[0];
        WGraph g = new WGraph(f);
        WGraph t = kruskal(g);
        System.out.println(t);

   } 
}
