import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Rudra Goel
 * @version 1.0
 * @userid rgoel68
 * @GTID 903897740
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {

        if (start == null || graph == null) {
            throw new IllegalArgumentException("Inputs to BFS cannot be null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Cannot perform BFS with starting vertex not in graph");
        }

        List<Vertex<T>> visitedSet = new LinkedList<Vertex<T>>();

        Queue<Vertex<T>> queue = new LinkedList<Vertex<T>>();
        
        //get the adjacency list --> map where key is Vertex and Value is a list of VertexDistance objects
        Map<Vertex<T>, List<VertexDistance<T>>> adjacencyList = graph.getAdjList();

        //add the starting vertex to the queue and the visited set of vertices
        visitedSet.add(start);
        queue.add(start);

        while (queue.size() > 0) {
            Vertex<T> v = queue.remove(); // dequeue a vertex

            List<VertexDistance<T>> vd = adjacencyList.get(v);

            for (VertexDistance<T> w : vd) {
                //if the vertex is not in the visitedSet
                if (!visitedSet.contains(w.getVertex())) {
                    //add the vertex to the visited set to the end
                    visitedSet.add(visitedSet.size(), w.getVertex());
                    //add it to the queue of vertices
                    queue.add(w.getVertex());
                }
            }

        }

        return visitedSet;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Inputs to BFS cannot be null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Cannot perform BFS with starting vertex not in graph");
        }
        
        List<Vertex<T>> visitedSet = new LinkedList<Vertex<T>>();

        Map<Vertex<T>, List<VertexDistance<T>>> adjacencyList = graph.getAdjList();

        dfsHelper(visitedSet, start, adjacencyList);

        return visitedSet;

    }

    /**
     * Helper method for performing the depth-first-search algorithm recursively
     * @param <T> generic typing of data
     * @param visitedSet a list of vertices the algorithm has already processed
     * @param vertex the current vertex in the graph to process
     * @param adjacencyList the way the graph is structured
     */
    private static <T> void dfsHelper(List<Vertex<T>> visitedSet, Vertex<T> vertex, Map<Vertex<T>, 
                                        List<VertexDistance<T>>> adjacencyList) {

        //add the current vertex to the list of visited vertices
        visitedSet.add(visitedSet.size(), vertex);
        
        //get the neighbors of the current vertex
        List<VertexDistance<T>> vertexDistanceObjs = adjacencyList.get(vertex);

        //guarantees to visit vertices in order returned by adjacency list
        for (int i = 0; i < vertexDistanceObjs.size(); i++) {
            
            //get a neighbor to the current vertex
            VertexDistance<T> vd = vertexDistanceObjs.get(i);

            //explore that neighbor only if it is not in the set of visited vertices
            //if it is in the set, then go to the next neighbor
            if (!visitedSet.contains(vd.getVertex())) { 
                dfsHelper(visitedSet, vd.getVertex(), adjacencyList);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Inputs to BFS cannot be null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Cannot perform BFS with starting vertex not in graph");
        }

        PriorityQueue<VertexDistance<T>> pq = new PriorityQueue<VertexDistance<T>>();
        Set<Vertex<T>> visitedSet = new HashSet<Vertex<T>>();
        Map<Vertex<T>, Integer> distanceMap = new HashMap<Vertex<T>, Integer>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjacencyList = graph.getAdjList();

        //for each vertex in the graph, first need to set the distance to that vertex to be infinity
        //to get all the vertices of the graph, i need to use the keySet() method provided by Map interface
        Set<Vertex<T>> vertices = adjacencyList.keySet();
        for (Vertex<T> vertex : vertices) {
            distanceMap.put(vertex, Integer.MAX_VALUE);
        }
        //update distance to start vertex to be zero
        distanceMap.put(start, 0);
        //enqueue the starting vertex to the PQ with a distance of zero
        pq.add(new VertexDistance<T>(start, 0));

        //while the PQ is not empty and the visited set is not full (does not contain all the vertices of the graph)
        while (pq.size() > 0 && visitedSet.size() < vertices.size()) {
            //dequeue the smallest distanced vertex currently
            VertexDistance<T> vertexDistance = pq.remove();

            //if the current vertex is not contained in the visited set
            if (!visitedSet.contains(vertexDistance.getVertex())) {
                //add it to the visited set
                visitedSet.add(vertexDistance.getVertex());
                //update the distance to that vertex with the new, true distance
                distanceMap.put(vertexDistance.getVertex(), vertexDistance.getDistance());
                //get all the neighbors of this vertex
                List<VertexDistance<T>> neighbors = adjacencyList.get(vertexDistance.getVertex());
                //iterate through the neighbors and determine if it is not visited yet 
                //if the neighbor is not visited --> enqueue it to the PQ 
                for (int i = 0; i < neighbors.size(); i++) {
                    VertexDistance<T> neighbor = neighbors.get(i);
                    if (!visitedSet.contains(neighbor.getVertex())) {
                        //must compute and add to the PQ the cumulative distance
                        // the cumulative distance is the distance of the current vertex to the neighbor
                        pq.add(new VertexDistance<>(neighbor.getVertex(), 
                                                    neighbor.getDistance() + vertexDistance.getDistance()));
                    }
                }
            }   
        }


        return distanceMap;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * An MST should NOT have self-loops or parallel edges.
     *
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Cannot find MST on a null graph");
        } else if (graph.getEdges().size() == 0) {
            return null;
        }

        //mst set to be returned
        Set<Edge<T>> mst = new HashSet<Edge<T>>();

        //using disjoint set data structure for managing the partition for the set of vertices in the graph
        //once the partition on the set of vertices has been reduced to one subset containing all the vertices
        //kruskal's algorithm is terminated
        DisjointSet<Vertex<T>> ds = new DisjointSet<Vertex<T>>();

        //add every edge in the graph to a priority queue
        PriorityQueue<Edge<T>> pq = new PriorityQueue<Edge<T>>();
        for (Edge<T> edge : graph.getEdges()) {
            pq.add(edge);
        }

        //while the pq is not empty and the MST size is less than 2*(|V| - 1)
        while (pq.size() > 0 && mst.size() < 2 * (graph.getVertices().size() - 1)) {
            Edge<T> edge = pq.remove();
            /**
             * For every edge there are two vertices (v and w)
             * first check if the two vertices belong to the same subset
             *  --> if they do, do not add the edge to the MST
             *  --> if they do NOT, add the edge to the MST and merge the two subsets that the vertices are contained in
             */


            if (!ds.find(edge.getU()).equals(ds.find(edge.getV()))) {
                mst.add(edge);
                //add an edge going in the opposite direction since 
                //nondirectional edges in our case is represented as two directional edges
                mst.add(new Edge<T>(edge.getV(), edge.getU(), edge.getWeight()));

                ds.union(edge.getU(), edge.getV());
                ds.union(edge.getV(), edge.getU());
            }
        }

        //basically a final check ot see if the partition in the disjoint set is containing more than one subset
        //in the case that the partition on the set of vertices contains more than one subset, 
        //then an MST is not valid
        //since there are vertices on the graph that are not connected to the main graph
        List<Vertex<T>> vertices = new LinkedList<Vertex<T>>(graph.getVertices());
        for (int i = 0; i < vertices.size() - 1; i++) {
            if (!ds.find(vertices.get(i)).equals(ds.find(vertices.get(i + 1)))) {
                return null;
            }
        }

        return mst;
    }


}
