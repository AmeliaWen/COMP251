import java.io.File;
import java.util.*;


public class FordFulkerson {
    public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
        ArrayList<Integer> path = new ArrayList<Integer>();
        /* YOUR CODE GOES HERE*/
        boolean[] visited = new boolean[graph.getNbNodes()];
        path.add(source);
        Integer next = source;
        while(next != destination){
            boolean neighbour = false;
            visited[next]= true;
            for (Edge e: graph.listOfEdgesSorted()){
                if (e.nodes[0]== next && (e.weight>0) && (!visited[e.nodes[1]])){
                    path.add(e.nodes[1]);
                    neighbour = true;
                    break;
                }
            }
            if (neighbour == false){
                path.remove(path.size()-1);
                if (path.size()== 0){
                    return new ArrayList<>();
                }
            }
            next= path.get(path.size()-1);

        }
        return path;
    }
    public static String fordfulkerson( WGraph graph){
        String answer="";
        int maxFlow = 0;

        /* YOUR CODE GOES HERE		*/
        int source = graph.getSource();
        int destination= graph.getDestination();
        WGraph residual = new WGraph(graph);
        WGraph capacity = new WGraph(graph);
        for (Edge e: graph.getEdges()){
            e.weight=0;
        }
		while (pathDFS(source, destination, residual).size() != 0 ) {
			ArrayList<Edge> edges = new ArrayList<Edge>();
			ArrayList<Integer> path = pathDFS(source, destination, residual);
			if(path.size()<=1){
			    break;
            }
			int bottleneck = residual.getEdge(path.get(0), path.get(1)).weight;
			for (int i = 0; i < path.size() - 1; i++) {
				if (path.get(i) != graph.getDestination()) {
					Edge e = residual.getEdge(path.get(i), path.get(i+1));
					edges.add(e);
					if (e.weight < bottleneck) {
						bottleneck = e.weight;
					}

				}
			}
            for(int i = 0; i < path.size() - 1; i++){
                Integer n1 = path.get(i);
                Integer n2 = path.get(i + 1);
                Edge edge = graph.getEdge(n1, n2);
                if(edge != null){
                    graph.setEdge(n1, n2, edge.weight + bottleneck);
                }
                else{
                    edge = graph.getEdge(n2, n1);
                    graph.setEdge(n2, n1, edge.weight - bottleneck);
                }
            }

			for (Edge e: graph.getEdges()) {
				int cap = capacity.getEdge(e.nodes[0], e.nodes[1]).weight;
				if(residual.getEdge(e.nodes[1], e.nodes[0]) == null) {
					residual.addEdge(new Edge(e.nodes[1], e.nodes[0], e.weight));
                    residual.setEdge(e.nodes[0], e.nodes[1], cap-e.weight);
				}else{
                    residual.setEdge(e.nodes[0], e.nodes[1], cap-e.weight);
                    residual.setEdge(e.nodes[1], e.nodes[0], e.weight);
                }
			}
			maxFlow += bottleneck;
		}
        answer += maxFlow + "\n" + graph.toString();
        return answer;
    }


    public static void main(String[] args){
        //String file = args[0];
        String file = "/Users/amelia/Downloads/HW3/test5.txt";
        //String file = "/Users/amelia/Downloads/COMP251_A3_TestCases-main/FordFulkerson/tests/FFTest6.txt";
        File f = new File(file);
        WGraph g = new WGraph(file);
        System.out.println(fordfulkerson(g));

    }
}

