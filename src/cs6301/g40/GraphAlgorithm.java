
package cs6301.g40;

import java.lang.reflect.Array;

public class GraphAlgorithm<T> {
    Graph g;
    // Algorithm uses a parallel array for storing information about vertices
    T[] node;

    /*public GraphAlgorithm(Graph g) {
	this.g = g;
    }*/
    public GraphAlgorithm(Class <T> c,Graph g)
    {
        this.g = g;
        node = (T[]) Array.newInstance(c,g.n);
    }

    T getVertex(Graph.Vertex u) {
	return Graph.Vertex.getVertex(node, u);
    }
}

