import java.io.File;
import java.util.*;


public class FF {

    public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
        ArrayList<Integer> path = new ArrayList<Integer>();
        /* YOUR CODE GOES HERE*/
        ArrayList<Integer> discoveredNodes = new ArrayList<Integer>();
        path.add(source);
        discoveredNodes.add(source);
        while(discoveredNodes.size()!=0){
            int a = discoveredNodes.remove(0);
            if (a == destination) {
                break;
            }
            for (Edge e : graph.listOfEdgesSorted()){
                if(e.nodes[0]==a &&(e.weight>0)){
                    if (!path.contains(e.nodes[1])){
                        path.add(e.nodes[1]);
                        discoveredNodes.add(e.nodes[1]);
                        break;
                    }

                }
            }
        }

        return path;
    }
    private static boolean augmenting (ArrayList<Integer> paths, WGraph g){
        if (paths.size()==1 &&(paths.contains(g.getSource()))){
            return false;
        }
        return true;
    }
    private static boolean containsPath (ArrayList<Integer> paths, WGraph g){
        if (paths.size()==1 &&(paths.contains(g.getSource()))){
            return false;
        }
        return true;
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

        //ArrayList<Integer>path = pathDFS(source, destination, residual);
		while(pathDFS(source, destination, residual).contains(source)&&(pathDFS(source, destination, residual).contains(destination))) {

            ArrayList<Integer> dfs = pathDFS(source, destination, residual);
            if(dfs.size()==1 && !dfs.contains(graph.getDestination())){
                break;
            }
            //if (dfs.contains(destination)) {
                for (Integer i : dfs) {
                    System.out.println(i);
                }
                int bottleneck = Integer.MAX_VALUE;

                // Find the bottleneck flow
                for (int i = 0; i < dfs.size() - 1; i++) {
                    Edge edge = residual.getEdge(dfs.get(i), dfs.get(i + 1));
                    if (edge != null && edge.weight < bottleneck) {
                        bottleneck = edge.weight;
                    }
                }
                // Augment the max flow
                for (int i = 0; i < dfs.size() - 1; i++) {
                    Integer n1 = dfs.get(i);
                    Integer n2 = dfs.get(i + 1);
                    Edge edge = graph.getEdge(n1, n2);
                    if (edge != null) {
                        graph.setEdge(n1, n2, edge.weight + bottleneck);
                    } else {
                        edge = graph.getEdge(n2, n1);
                        graph.setEdge(n2, n1, edge.weight - bottleneck);
                    }
                }

                // Set residual graph
                for (int i = 0; i < dfs.size() - 1; i++) {
                    Integer n1 = dfs.get(i);
                    Integer n2 = dfs.get(i + 1);
                    Edge edge = graph.getEdge(n1, n2);
                    Edge capEdge = capacity.getEdge(n1, n2);
                    Edge redge = residual.getEdge(n1, n2);
                    if (edge == null) {
                        edge = graph.getEdge(n2, n1);
                    }
                    if (capEdge == null) {
                        capEdge = capacity.getEdge(n2, n1);
                    }
                    if (edge.weight <= capEdge.weight) {
                        residual.setEdge(n1, n2, capEdge.weight - edge.weight);
                        Edge bedge = residual.getEdge(n2, n1);
                        if (bedge == null) {
                            bedge = new Edge(n2, n1, edge.weight);
                            residual.addEdge(bedge);
                        } else {
                            residual.setEdge(n2, n1, edge.weight);
                        }
                    } else if (edge.weight > 0) {
                        Edge residualEdge = residual.getEdge(n1, n2);
                        if (residualEdge == null) {
                            Edge backEdge = new Edge(n1, n2, edge.weight);
                            residual.addEdge(backEdge);
                        } else {
                            residual.setEdge(n2, n1, edge.weight);
                        }
                    }
               // }
                //ArrayList<Integer> dfs = pathDFS(source, destination, residual);

            }

        }
        answer += maxFlow + "\n" + graph.toString();
        return answer;
    }


			/*ArrayList<Integer> dfs = pathDFS(source, destination, residual);
			for (Integer i : dfs){
			    System.out.println(i);
            }
			int bottleneck = Integer.MAX_VALUE;

			// Find the bottleneck flow
			for(int i = 0; i < dfs.size()-1; i++){
				Edge edge = residual.getEdge(dfs.get(i), dfs.get(i+1));
				if(edge != null && edge.weight < bottleneck){
					bottleneck = edge.weight;
				}
			}
			// Augment the max flow
			for(int i = 0; i < dfs.size() - 1; i++){
				Integer n1 = dfs.get(i);
				Integer n2 = dfs.get(i + 1);
				Edge edge = graph.getEdge(n1, n2);
				if(edge != null){
					graph.setEdge(n1, n2, edge.weight + bottleneck);
				}
				else{
                    edge = graph.getEdge(n2, n1);
					graph.setEdge(n2, n1, edge.weight - bottleneck);
				}
			}

			// Set residual graph
			for(int i=0; i<dfs.size()-1; i++){
				Integer n1 = dfs.get(i);
				Integer n2 = dfs.get(i + 1);
				Edge edge = graph.getEdge(n1, n2);
				Edge capEdge = capacity.getEdge(n1, n2);
				Edge redge = residual.getEdge(n1, n2);
				if(edge == null){
				    edge = graph.getEdge(n2, n1);
                }
				if(capEdge==null){
				    capEdge= capacity.getEdge(n2, n1);
                }
                if(edge.weight <= capEdge.weight){
                    residual.setEdge(n1, n2, capEdge.weight - edge.weight);
                    Edge bedge = residual.getEdge(n2, n1);
                    if (bedge == null){
                        bedge = new Edge(n2, n1,  edge.weight);
                        residual.addEdge(bedge);
                    }else{
                        residual.setEdge(n2, n1, edge.weight);
                    }
                } else if (edge.weight > 0) {
                    Edge residualEdge = residual.getEdge(n1, n2);
                    if(residualEdge == null){
                        Edge backEdge = new Edge(n1, n2, edge.weight);
                        residual.addEdge(backEdge);
                    }
                    else{
                        residual.setEdge(n2, n1, edge.weight);
                    }
                }
				/*if (redge.weight >= bottleneck){
                    residual.setEdge(n1, n2, redge.weight-bottleneck);
                    Edge b = residual.getEdge(n2, n1);
                    if (b == null){
                        Edge bedge = new Edge(n2, n1, bottleneck);
                        residual.addEdge(bedge);
                    }
                }*/
			//}*/
    /*
			path = pathDFS(source, destination, residual);
			maxFlow += bottleneck;
			bottleneck = Integer.MAX_VALUE;
			System.out.println(dfs.size());
			*/
		//}
        /*ArrayList<Integer> paths = pathDFS(graph.getSource(), graph.getDestination(), residual);
		while (!pathDFS(graph.getSource(), graph.getDestination(), residual).isEmpty()) {

			ArrayList<Edge> edgesOrder = new ArrayList<Edge>();
			ArrayList<Integer> verticesOrder = pathDFS(graph.getSource(), graph.getDestination(), residual);
			int bottleneck = residual.getEdge(verticesOrder.get(0), verticesOrder.get(1)).weight;
			for (int i = 0; i < verticesOrder.size() - 1; i++) {
				Integer currNode = verticesOrder.get(i);
				if (currNode != graph.getDestination()) {
					Edge e = residual.getEdge(currNode, verticesOrder.get(i+1));
					edgesOrder.add(e);
					if (e.weight < bottleneck) {
						bottleneck = e.weight;
					}

				}
			}
			for (Edge e: edgesOrder) {
				Edge eGraph = graph.getEdge(e.nodes[0], e.nodes[1]);
				if(eGraph !=null) {
					eGraph.weight += bottleneck;
				}
				else {
					graph.getEdge(e.nodes[1], e.nodes[0]).weight -= bottleneck;
				}
			}

			for (Edge e: graph.getEdges()) {

				int edgeWeight = e.weight;
				int maxCapacity = capacity.getEdge(e.nodes[0], e.nodes[1]).weight;
				if(residual.getEdge(e.nodes[1], e.nodes[0]) == null) {
					Edge backEdge = new Edge(e.nodes[1], e.nodes[0], edgeWeight);
					residual.addEdge(backEdge);
				}

				residual.getEdge(e.nodes[0], e.nodes[1]).weight = maxCapacity - edgeWeight;
				residual.getEdge(e.nodes[1], e.nodes[0]).weight = edgeWeight;
			}
			maxFlow += bottleneck;
		}*/

        /*ArrayList<Integer> paths = pathDFS(graph.getSource(), graph.getDestination(), residual);
        while (augmenting(pathDFS(graph.getSource(), graph.getDestination(), residual), residual)){
            if (paths.contains(graph.getDestination())){
                int bn = residual.getEdge(paths.get(0), paths.get(1)).weight;
                for (int i = 0;i<paths.size()-1;i++){
                    if (residual.getEdge(paths.get(i), paths.get(i+1))!=null){
                        if (residual.getEdge(paths.get(i), paths.get(i+1)).weight<bn){
                            bn = residual.getEdge(paths.get(i), paths.get(i+1)).weight;
                        }
                    }

                }
                maxFlow= maxFlow+bn;
                for (int j = 0;j<paths.size()-1;j++){
                    if (residual.getEdge(paths.get(j), paths.get(j+1))!=null){
                        residual.getEdge(paths.get(j), paths.get(j+1)).weight-=bn;
                        graph.getEdge(paths.get(j), paths.get(j+1)).weight+=bn;
                    }

                }
            }else{
                for (int i = 0;i<paths.size()-1;i++){
                    residual.getEdge(paths.get(i), paths.get(i+1)).weight=0;
                }
            }
            paths= pathDFS(graph.getSource(), graph.getDestination(), residual);
            if(paths.size()==1 && !paths.contains(graph.getDestination())){
                break;
            }
        }*/





    public static void main(String[] args){
        //String file = args[0];
        String file = "/Users/amelia/Downloads/COMP251_A3_TestCases-main/FordFulkerson/tests/FFTest5.txt";
        File f = new File(file);
        WGraph g = new WGraph(file);
        System.out.println(fordfulkerson(g));
    }
}

