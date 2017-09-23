package cs6301.g40;
//import cs6301.g40.Graph;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Queue;
import java.util.Stack;


//import static java.lang.Boolean.FALSE;
 class DFS extends GraphAlgorithm<DFS.DFSVertex>
{
    LinkedList<DFSVertex> DFS_Vertices;
    int timeCounter;
    Graph.Vertex [] graph_Vertices;
    LinkedList<DFSVertex> SortedByFinishedTime;
    public DFS(Class <DFSVertex> d,Graph g)
    {
        super(d,g);
        DFS_Vertices = new LinkedList<>();
        SortedByFinishedTime = new LinkedList<>();
        graph_Vertices = new Graph.Vertex[g.n];
        timeCounter=0;
        addVertexToArray();
    }

    public void addVertexToArray()
    {
        int i = 0;
        for (Graph.Vertex v1:this.g.v)
        {
            graph_Vertices[i]=v1;
            DFSVertex df1 = new DFSVertex(v1.name);
            df1 = DFSVertex.copyGraphVertex(v1,df1);
            this.node[v1.name] = df1;
            i++;
        }
    }


    public static class DFSVertex extends Graph.Vertex
    {
        boolean seen;
        int startTime;
        int finishTime;
        int componentNumber;

        /**
         * Constructor for Graph
         *
         * @param n : int - number of vertices
         */
        public DFSVertex(int n)
        {
            super(n);
            seen = false;
            startTime = 0;
            finishTime = 0;
            componentNumber=0;
        }

        public DFSVertex(Graph.Vertex v, int n)
        {
            super(n);
            //this.name=v.name;
            this.adj = v.adj;
            this.revAdj = v.revAdj;
            seen = false;
            startTime = 0;
            finishTime = 0;
            componentNumber=0;
        }

        public static DFSVertex copyGraphVertex(Graph.Vertex v, DFSVertex v1)
        {
            v1.adj = v.adj;
            v1.revAdj = v.revAdj;
            return v1;
        }
    }

    /*
    public Graph.Vertex getGraphVertex(DFSVertex u)
    {
        return graph_Vertices[u.name];
    }
    */
    public void findDFS(Graph.Vertex v,boolean ifFlippedGraph)
    {
        findDFS(this.getVertex(v),ifFlippedGraph);
    }

    public void findDFS( DFSVertex v1,boolean ifFlippedGraph)
    {
        this.timeCounter++;
        v1.seen=true;
        this.DFS_Vertices.add(v1);
        v1.startTime=timeCounter;
        if(!ifFlippedGraph)
        {
            for (Graph.Edge e : v1.adj) {
                if (this.getVertex(e.otherEnd(this.graph_Vertices[v1.name])).seen == true) {
                    continue;
                } else {
                    findDFS(this.getVertex(e.otherEnd(this.graph_Vertices[v1.name])),false);
                }
            }
            this.timeCounter++;
            v1.finishTime = this.timeCounter;
        }
        else
        {
            for (Graph.Edge e : v1.revAdj) {
                if (this.getVertex(e.otherEnd(this.graph_Vertices[v1.name])).seen == true) {
                    continue;
                } else {
                    findDFS(this.getVertex(e.otherEnd(this.graph_Vertices[v1.name])),true);
                }
            }
            this.timeCounter++;
            v1.finishTime = this.timeCounter;
        }
    }
}
public class StronglyConnectedComps
{

    public static DFS.DFSVertex findMinFinishTime(DFS v)
    {
        DFS.DFSVertex min = new DFS.DFSVertex(0);
        min.finishTime=Integer.MAX_VALUE;
        for (DFS.DFSVertex v1:v.DFS_Vertices)
        {
            if(v1.finishTime<min.finishTime)
                min=v1;
        }
        return min;
    }


    public static int findStronglyConnectedComponents(DFS v, int numComps)
    {
        while(!v.DFS_Vertices.isEmpty())
        {
            v.SortedByFinishedTime.push(findMinFinishTime(v));
            v.DFS_Vertices.remove(findMinFinishTime(v));
        }
        for (DFS.DFSVertex v1:v.SortedByFinishedTime)
        {
            v1.seen=false;
        }
            /*
        }
            for (Graph.Edge e:v1.adj)
            {
                ExcludedVertices.add(DFS.DFSVertex.getVertex(v.node,e.otherEnd(v.graph_Vertices[v1.name])));
            }
            for (DFS.DFSVertex inclV : v.node)
            {
                inclV.seen=false;
                if(!ExcludedVertices.contains(inclV))
                {
                    Graph.Edge revE = new Graph.Edge(v.graph_Vertices[v1.name],v.graph_Vertices[inclV.name],1);
                    v1.revAdj.add(revE);
                }
            }*/

        //}
        for(DFS.DFSVertex v1:v.SortedByFinishedTime)
        {
            if(!v1.seen)
            {
                numComps++;
                v.findDFS(v1,true);
            }
        }
        return numComps;
    }


    public static void main(String [] args)
    {
        int index=0,numComps=0;
        Scanner in = new Scanner(System.in);
       LinkedList<DFS.DFSVertex> ExcludedVertices = new LinkedList<>();
        //Graph g = new Graph(5);
        System.out.println("Enter Graph:-");
        Graph g = Graph.readDirectedGraph(in);

        //GraphAlgorithm<DFSVertex> DFSV1= new GraphAlgorithm<>(g);

        DFS D1 = new DFS(DFS.DFSVertex.class,g);
        D1.findDFS(g.v[0],false);
        numComps=findStronglyConnectedComponents(D1,numComps);
        System.out.println("Number of Componenets:" + numComps);
        /*
        for (Graph.Vertex u:g
             ) {
            Vertices[index++] = new DFS(u,0);
            //System.out.println(Vertices[index-1].getName());

        }
        */
       // BFSList=BFS.findBFS(g,g.getVertex(1),Vertices);
        //PathList=findStronglyConnectedComponents(g,g.getVertex(1),Vertices);
        System.out.println("--------------DFS Path--------------");
        for (DFS.DFSVertex v:D1.DFS_Vertices)
        {
            System.out.println(v);
        }
    }

}
