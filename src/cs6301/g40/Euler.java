
// change following line to your group number
package cs6301.g40;

import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;

public class Euler {
    int VERBOSE;
    List<Graph.Edge> tour;
    Graph inputGraph;
    Graph.Vertex startVertex;
    // Constructor
    Euler(Graph g, Graph.Vertex start) {
	VERBOSE = 1;
	tour = new LinkedList<>();
    inputGraph = g;
    startVertex = start;
    }

    // To do: function to find an Euler tour
    public List<Graph.Edge> findEulerTour() {
	findTours();
	if(VERBOSE > 9) { printTours(); }
	stitchTours();
	return tour;
    }

    /* To do: test if the graph is Eulerian.
     * If the graph is not Eulerian, it prints the message:
     * "Graph is not Eulerian" and one reason why, such as
     * "inDegree = 5, outDegree = 3 at Vertex 37" or
     * "Graph is not strongly connected"
     */
    boolean isEulerian() {
        int numComps=0;
        //LinkedList<Graph.Edge> incomingEdges= new LinkedList<Graph.Edge>();
        int[] outDegree = new int[inputGraph.n];
        int[] inDegree = new int[inputGraph.n];
        Arrays.fill(inDegree, 0);
        Arrays.fill(outDegree, 0);
        DFS D1 = new DFS(DFS.DFSVertex.class,inputGraph);
        D1.findDFS(inputGraph.v[0],false);
        numComps = StronglyConnectedComps.findStronglyConnectedComponents(D1,numComps);
        if(numComps == 1)
        {
            for (Graph.Vertex v:inputGraph)
            {
                outDegree[v.name] = v.adj.size();
                for (Graph.Edge e:v.adj)
                {
                    inDegree[e.otherEnd(v).getName()]++;
                }
            }
            if(Arrays.equals(inDegree,outDegree))
            {
                System.out.println("Graph is Eulerian");
                System.out.println("Reason: Graph is strongly connected and has equal inDegree and outDegree");
                return true;
            }
            else
            {
                System.out.println("Graph is not Eulerian");
                System.out.println("Reason: Graph does not have inDegree = outDegree for every Vertex");
                return false;
            }
        }
        else
        {
            System.out.println("Graph is not Eulerian");
            System.out.println("Reason: Graph is not strongly connected");
            return false;
        }
    }

    // Find tours starting at vertices with unexplored edges
    void findTours() {
    }

    /* Print tours found by findTours() using following format:
     * Start vertex of tour: list of edges with no separators
     * Example: lp2-in1.txt, with start vertex 3, following tours may be found.
     * 3: (3,1)(1,2)(2,3)(3,4)(4,5)(5,6)(6,3)
     * 4: (4,7)(7,8)(8,4)
     * 5: (5,7)(7,9)(9,5)
     *
     * Just use System.out.print(u) and System.out.print(e)
     */
    void printTours() {
    }

    // Stitch tours into a single tour using the algorithm discussed in class
    void stitchTours() {
    }

    void setVerbose(int v) {
	VERBOSE = v;
    }

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Graph:-");
        Graph g = Graph.readDirectedGraph(in);
        Euler obj = new Euler(g,g.v[0]);
        if(obj.isEulerian())
        {
            System.out.println("Done");
        }
    }
}
