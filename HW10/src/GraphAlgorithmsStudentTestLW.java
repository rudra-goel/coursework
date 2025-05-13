import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


import static org.junit.Assert.assertEquals;

/**
 * Author: Liam Weng
 * Directed graph images can be found in piazza, I couldn't find a good way to consistently generate a graph in csvistool
 * undirectedGraph is the default undirected for djikstras, undirectedGraph2 is the default undirected for Kruskal
 * undirectedGraph3 was used in the in-depth example of kruskal, mainly just to check that null is returned when a tree can't be found
 */
public class GraphAlgorithmsStudentTestLW {

    private static final int TIMEOUT = 200;
    private Graph<Character> directedGraph;
    private Graph<Character> directedGraph2;
    private Graph<Character> undirectedGraph;
    private Graph<Character> undirectedGraph2;
    private Graph<Character> undirectedGraph3;

    @Before
    public void init() {
        directedGraph = createDirectedGraph();
        directedGraph2 = createDirectedGraph2();
        undirectedGraph = createUndirectedGraph(); //from csvistool for dijkstras
        undirectedGraph2 = createUndirectedGraph2(); //from csvistool for kruskal
        undirectedGraph3 = createUndirectedGraph3(); //from video where kruskal is invaid
    }

    /**
     * Creates a directed graph.
     * The graph is depicted in the pdf.
     *
     * @return the completed graph
     */
    private Graph<Character> createDirectedGraph() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 65; i <= 72; i++) {
            vertices.add(new Vertex<>((char) i));
        }

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 0));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 0));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('E'), 0));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('B'), 0));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('F'), 0));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('D'), 0));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('B'), 0));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('G'), 0));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('G'), 0));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('C'), 0));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('F'), 0));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('H'), 0));


        return new Graph<>(vertices, edges);
    }

        /**
     * Creates a directed graph.
     * The graph is depicted in the pdf.
     *
     * @return the completed graph
     */
    private Graph<Character> createDirectedGraph2() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 65; i <= 72; i++) {
            vertices.add(new Vertex<>((char) i));
        }

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 0));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 0));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('E'), 0));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('B'), 0));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('B'), 0));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('F'), 0));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('G'), 0));

        return new Graph<>(vertices, edges);
    }

    /**
     * Creates an undirected graph.
     * The graph is depicted in the pdf.
     *
     * @return the completed graph
     */
    private Graph<Character> createUndirectedGraph() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 65; i <= 72; i++) {
            vertices.add(new Vertex<>((char) i));
        }

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 8));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 8));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 2));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 2));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 5));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 5));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('C'), 1));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('B'), 1));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('D'), 1));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('B'), 1));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('G'), 7));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('B'), 7));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('E'), 3));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('C'), 3));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('F'), 4));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('D'), 4));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('G'), 0));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('F'), 0));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('H'), 1));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('F'), 1));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('G'), 2));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('E'), 2));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('H'), 1));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('G'), 1));


        return new Graph<>(vertices, edges);
    }

    /**
     * Creates an undirected graph.
     * The graph is depicted in the pdf.
     *
     * @return the completed graph
     */
    private Graph<Character> createUndirectedGraph2() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 65; i <= 72; i++) {
            vertices.add(new Vertex<>((char) i));
        }

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 1));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 1));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 5));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 5));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('C'), 7));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('B'), 7));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('D'), 9));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('B'), 9));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('G'), 1));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('B'), 1));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('F'), 4));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('D'), 4));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('G'), 2));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('F'), 2));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('H'), 0));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('F'), 0));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('G'), 10));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('E'), 10));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('H'), 3));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('G'), 3));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('G'), 13));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('C'), 13));



        return new Graph<>(vertices, edges);
    }

        /**
     * Creates an undirected graph.
     * The graph is depicted in the pdf.
     *
     * @return the completed graph
     */
    private Graph<Character> createUndirectedGraph3() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 65; i <= 73; i++) {
            vertices.add(new Vertex<>((char) i));
        }
        vertices.add(new Vertex<>((char) 88));
        vertices.add(new Vertex<>((char) 89));


        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 4));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 4));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('H'), 8));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('A'), 8));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('C'), 8));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('B'), 8));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('H'), 11));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('B'), 11));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('I'), 2));
        edges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('C'), 2));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('I'), 7));
        edges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('H'), 7));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('G'), 1));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('H'), 1));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('F'), 4));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('C'), 4));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('F'), 2));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('G'), 2));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 7));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 7));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 9));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('D'), 9));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('F'), 10));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('E'), 10));
        edges.add(new Edge<>(new Vertex<>('X'), new Vertex<>('Y'), 3));
        edges.add(new Edge<>(new Vertex<>('Y'), new Vertex<>('X'), 3));




        return new Graph<>(vertices, edges);
    }

    @Test(timeout = TIMEOUT)
    public void testBFS() {
        List<Vertex<Character>> bfsActual = GraphAlgorithms.bfs(
            new Vertex<>('A'), directedGraph);

        List<Vertex<Character>> bfsExpected = new LinkedList<>();
        bfsExpected.add(new Vertex<>('A'));
        bfsExpected.add(new Vertex<>('C'));
        bfsExpected.add(new Vertex<>('D'));
        bfsExpected.add(new Vertex<>('E'));
        bfsExpected.add(new Vertex<>('B'));
        bfsExpected.add(new Vertex<>('F'));
        bfsExpected.add(new Vertex<>('G'));
        bfsExpected.add(new Vertex<>('H'));

        assertEquals(bfsExpected, bfsActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDFS() {
        List<Vertex<Character>> dfsActual =
            GraphAlgorithms.dfs(new Vertex<>('A'), directedGraph2);

        List<Vertex<Character>> dfsExpected = new LinkedList<>();
        dfsExpected.add(new Vertex<>('A'));
        dfsExpected.add(new Vertex<>('C'));
        dfsExpected.add(new Vertex<>('B'));
        dfsExpected.add(new Vertex<>('D'));
        dfsExpected.add(new Vertex<>('E'));

        assertEquals(dfsExpected, dfsActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstras() {
        Map<Vertex<Character>, Integer> dijkActual = GraphAlgorithms.dijkstras(
            new Vertex<>('E'), undirectedGraph);

        Map<Vertex<Character>, Integer> dijkExpected = new HashMap<>();
        dijkExpected.put(new Vertex<>('A'), 5);
        dijkExpected.put(new Vertex<>('B'), 4);
        dijkExpected.put(new Vertex<>('C'), 3);
        dijkExpected.put(new Vertex<>('D'), 5);
        dijkExpected.put(new Vertex<>('E'), 0);
        dijkExpected.put(new Vertex<>('F'), 2);
        dijkExpected.put(new Vertex<>('G'), 2);
        dijkExpected.put(new Vertex<>('H'), 3);

        assertEquals(dijkExpected, dijkActual); //default csvistool undirected graph
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstras2() {
        Map<Vertex<Character>, Integer> dijkActual = GraphAlgorithms.dijkstras(
            new Vertex<>('D'), undirectedGraph);

        Map<Vertex<Character>, Integer> dijkExpected = new HashMap<>();
        dijkExpected.put(new Vertex<>('A'), 4);
        dijkExpected.put(new Vertex<>('B'), 1);
        dijkExpected.put(new Vertex<>('C'), 2);
        dijkExpected.put(new Vertex<>('D'), 0);
        dijkExpected.put(new Vertex<>('E'), 5);
        dijkExpected.put(new Vertex<>('F'), 4);
        dijkExpected.put(new Vertex<>('G'), 4);
        dijkExpected.put(new Vertex<>('H'), 5);

        assertEquals(dijkExpected, dijkActual); //default csvistool undirected graph
    }


    @Test(timeout = TIMEOUT)
    public void testKruskals2() {
        Set<Edge<Character>> mstActual = GraphAlgorithms.kruskals(
                undirectedGraph2);

        Set<Edge<Character>> mstExpected = new HashSet<>();
        mstExpected.add(new Edge<>(new Vertex<>('F'), new Vertex<>('H'), 0));
        mstExpected.add(new Edge<>(new Vertex<>('H'), new Vertex<>('F'), 0));
        mstExpected.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 1));
        mstExpected.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 1));
        mstExpected.add(new Edge<>(new Vertex<>('B'), new Vertex<>('G'), 1));
        mstExpected.add(new Edge<>(new Vertex<>('G'), new Vertex<>('B'), 1));
        mstExpected.add(new Edge<>(new Vertex<>('G'), new Vertex<>('F'), 2));
        mstExpected.add(new Edge<>(new Vertex<>('F'), new Vertex<>('G'), 2));
        mstExpected.add(new Edge<>(new Vertex<>('D'), new Vertex<>('F'), 4));
        mstExpected.add(new Edge<>(new Vertex<>('F'), new Vertex<>('D'), 4));
        mstExpected.add(new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 5));
        mstExpected.add(new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 5));
        mstExpected.add(new Edge<>(new Vertex<>('E'), new Vertex<>('G'), 10));
        mstExpected.add(new Edge<>(new Vertex<>('G'), new Vertex<>('E'), 10));

        assertEquals(mstExpected, mstActual); //default csvistool graph
    }

    @Test(timeout = TIMEOUT)
    public void testKruskals3() {
        Set<Edge<Character>> mstActual = GraphAlgorithms.kruskals(
                undirectedGraph3);

        Set<Edge<Character>> mstExpected = null;

        assertEquals(mstExpected, mstActual); //from "In-Depth Example" vid
    }
}