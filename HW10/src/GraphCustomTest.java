import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class GraphCustomTest {

    @Test
    public void testBFSNullOrMissing() {
        Graph<Integer> graph = new Graph<>(
                new HashSet<>(List.of(new Vertex<>(5))),
                new HashSet<>()
        );

        assertThrows(IllegalArgumentException.class, () -> {GraphAlgorithms.bfs(null, graph); });
        assertThrows(IllegalArgumentException.class, () -> {GraphAlgorithms.bfs(new Vertex<>(5), null); });
        assertThrows(IllegalArgumentException.class, () -> {GraphAlgorithms.bfs(new Vertex<>(15), graph); });
    }

    @Test
    public void testDFSNullOrMissing() {
        Graph<Integer> graph = new Graph<>(
                new HashSet<>(List.of(new Vertex<>(5))),
                new HashSet<>()
        );

        assertThrows(IllegalArgumentException.class, () -> {GraphAlgorithms.dfs(null, graph); });
        assertThrows(IllegalArgumentException.class, () -> {GraphAlgorithms.dfs(new Vertex<>(5), null); });
        assertThrows(IllegalArgumentException.class, () -> {GraphAlgorithms.dfs(new Vertex<>(15), graph); });
    }

    @Test
    public void testKruskalNull() {
        assertThrows(IllegalArgumentException.class, () -> GraphAlgorithms.kruskals(null));
    }

    @Test
    public void testKruskalEmpty() {
        Graph<Integer> graph = new Graph<>(new HashSet<>(), new HashSet<>());
        assertNull(GraphAlgorithms.kruskals(graph));
    }

    @Test
    public void testDijkstraMissingOrNull() {
        Graph<Integer> graph = new Graph<>(
                new HashSet<>(List.of(new Vertex<>(5))),
                new HashSet<>()
        );

        assertThrows(IllegalArgumentException.class, () -> {GraphAlgorithms.dijkstras(null, graph); });
        assertThrows(IllegalArgumentException.class, () -> {GraphAlgorithms.dijkstras(new Vertex<>(5), null); });
        assertThrows(IllegalArgumentException.class, () -> {GraphAlgorithms.dijkstras(new Vertex<>(15), graph); });
    }

    @Test
    public void testSingleNodeGraphTraversal() {
        Graph<Integer> empty = new Graph<>(
                new HashSet<>(List.of(new Vertex<>(5))),
                new HashSet<>()
        );

        assertListEquals(
                GraphAlgorithms.bfs(new Vertex<>(5), empty),
                List.of(new Vertex<>(5))
        );

        assertListEquals(
                GraphAlgorithms.dfs(new Vertex<>(5), empty),
                List.of(new Vertex<>(5))
        );

        assertNull(
                GraphAlgorithms.kruskals(empty)
        );
    }

    @Test
    public void testSingleNodeGraphTraversalWithLoops() {
        HashSet<Edge<Integer>> edges = new HashSet<>();
        Vertex<Integer> v1 = new Vertex<>(5);
        edges.add(new Edge<>(v1, v1, 0));
        edges.add(new Edge<>(v1, v1, 12323));

        Graph<Integer> empty = new Graph<>(
                new HashSet<>(List.of(v1)),
                edges
        );

        assertListEquals(
                List.of(new Vertex<>(5)),
                GraphAlgorithms.bfs(new Vertex<>(5), empty)
        );

        assertListEquals(
                List.of(new Vertex<>(5)),
                GraphAlgorithms.dfs(new Vertex<>(5), empty)
        );

        assertEquals(
                new HashSet<>(List.of()),
                GraphAlgorithms.kruskals(empty)
        );
    }

    @Test
    public void testTraversalUnconnectedLoops() {
        Vertex<Integer> v1 = new Vertex<>(5);
        Vertex<Integer> v2 = new Vertex<>(15);

        HashSet<Edge<Integer>> edges = new HashSet<>();
        edges.add(new Edge<>(v1, v1, 12));
        edges.add(new Edge<>(v2, v2, 12));

        Graph<Integer> graph = new Graph<>(
                new HashSet<>(List.of(v1, v2)),
                edges
        );

        assertListEquals(
                List.of(new Vertex<>(5)),
                GraphAlgorithms.bfs(new Vertex<>(5), graph)
        );

        assertListEquals(
                List.of(new Vertex<>(15)),
                GraphAlgorithms.bfs(new Vertex<>(15), graph)
        );

        assertListEquals(
                List.of(new Vertex<>(5)),
                GraphAlgorithms.dfs(new Vertex<>(5), graph)
        );

        assertListEquals(
                List.of(new Vertex<>(15)),
                GraphAlgorithms.dfs(new Vertex<>(15), graph)
        );

        assertNull(GraphAlgorithms.kruskals(graph));
    }

    @Test
    public void testLoopTraversal() {
        Vertex<Integer> v1 = new Vertex<>(5);
        Vertex<Integer> v2 = new Vertex<>(15);
        Vertex<Integer> v3 = new Vertex<>(25);
        Vertex<Integer> v4 = new Vertex<>(35);

        HashSet<Edge<Integer>> edges = new HashSet<>();
        addUndirectedEdge(v1, v2, edges, 4);
        addUndirectedEdge(v1, v2, edges, 7); // parallel edge with different weights

        addUndirectedEdge(v2, v3, edges, 4);

        addUndirectedEdge(v3, v4, edges, 8);
        addUndirectedEdge(v3, v4, edges, 19); // parallel edge with different weights

        addUndirectedEdge(v4, v1, edges, 4);

        edges.add(new Edge<>(v1, v1, 0));
        edges.add(new Edge<>(v3, v3, 40));

        Graph<Integer> graph = new Graph<>(
                new HashSet<>(List.of(v1, v2, v3, v4)),
                edges
        );

        assertListEquals(
                List.of(new Vertex<>(15), new Vertex<>(5), new Vertex<>(25), new Vertex<>(35)),
                GraphAlgorithms.bfs(new Vertex<>(15), graph)
        );

        assertListEquals(
                List.of(new Vertex<>(15), new Vertex<>(5), new Vertex<>(35), new Vertex<>(25)),
                GraphAlgorithms.dfs(new Vertex<>(15), graph)
        );

        assertEquals(
                new HashSet<>(List.of(
                        new Edge<>(v1, v2, 4),
                        new Edge<>(v2, v3, 4),
                        new Edge<>(v4, v1, 4),
                        new Edge<>(v2, v1, 4),
                        new Edge<>(v3, v2, 4),
                        new Edge<>(v1, v4, 4)
                )),
                GraphAlgorithms.kruskals(graph)
        );
    }

    @Test
    public void testKruskalsNegativeEdges() {

        List<Vertex<Integer>> v = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            v.add(new Vertex<>(i));
        }

        Set<Edge<Integer>> edges = new LinkedHashSet<>();

        addUndirectedEdge(v.get(0), v.get(4), edges, 123);
        addUndirectedEdge(v.get(3), v.get(2), edges, 6);
        addUndirectedEdge(v.get(1), v.get(2), edges, -123);
        addUndirectedEdge(v.get(2), v.get(4), edges, 9);
        addUndirectedEdge(v.get(2), v.get(5), edges, 9);
        addUndirectedEdge(v.get(1), v.get(6), edges, 4);
        addUndirectedEdge(v.get(7), v.get(5), edges, 10);
        addUndirectedEdge(v.get(6), v.get(7), edges, 7);
        addUndirectedEdge(v.get(4), v.get(7), edges, -123);

        Graph<Integer> graph = new Graph<>(new HashSet<>(v), edges);

        Set<Edge<Integer>> mst = new HashSet<>(edges);

        mst.remove(new Edge<>(v.get(2), v.get(4), 9));
        mst.remove(new Edge<>(v.get(4), v.get(2), 9));

        mst.remove(new Edge<>(v.get(5), v.get(7), 10));
        mst.remove(new Edge<>(v.get(7), v.get(5), 10));

        assertEquals(
                mst,
                GraphAlgorithms.kruskals(graph)
        );
    }

    @Test
    public void testDijkstraRandomAPSP() {
        // I'm particularly proud of this one
        // Note that the floyd-warshall algorithm (used to check dijkstra) takes O(N^3) time,
        // so this test may take a second or two to run

        List<Vertex<Integer>> v = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            v.add(new Vertex<>(i));
        }

        Random rand = new Random(1230124801);

        Set<Edge<Integer>> edges = new LinkedHashSet<>();

        int[][] groundTruth = new int[300][300];

        for (int i = 0; i < 300; i++) {
            for (int j = 0; j < 300; j++) {
                groundTruth[i][j] = rand.nextInt(0, 2000);
                edges.add(new Edge<>(v.get(i), v.get(j), groundTruth[i][j]));
                if (i == j) groundTruth[i][j] = 0;
            }
        }

        // Now use floyd-warshall to compute APSP
        for (int k = 0; k < 300; k++) {
            for (int i = 0; i < 300; i++) {
                for (int j = 0; j < 300; j++) {
                    groundTruth[i][j] = Math.min(groundTruth[i][j], groundTruth[i][k] + groundTruth[k][j]);
                }
            }
        }

        Graph<Integer> graph = new Graph<>(new HashSet<>(v), edges);

        // Now test dijkstra using ground truth for verification
        for (int i = 0; i < 300; i++) {
            Map<Vertex<Integer>, Integer> result = GraphAlgorithms.dijkstras(new Vertex<>(i), graph);

            for (int j = 0; j < 300; j++) {
                assertTrue("Dijkstra did not find a path from vertex " + i + " to vertex " + j + " even though one exists", result.containsKey(v.get(j)));
                assertEquals("Error finding shortest path from " + i + " to " + j,
                        groundTruth[i][j], (int) result.get(new Vertex<>(j))
                );
            }
        }
    }

    @Test
    public void testKruskallWithIncompleteTree() {
        List<Vertex<Integer>> v = new ArrayList<>();
        v.add(new Vertex<>(5));
        v.add(new Vertex<>(15));
        v.add(new Vertex<>(25));
        v.add(new Vertex<>(35));

        Set<Edge<Integer>> edges = new HashSet<>();
        edges.add(new Edge<>(v.get(0), v.get(1), 10));
        edges.add(new Edge<>(v.get(1), v.get(0), 10));
        edges.add(new Edge<>(v.get(0), v.get(2), 20));
        edges.add(new Edge<>(v.get(2), v.get(0), 20));
        edges.add(new Edge<>(v.get(2), v.get(1), 30));
        edges.add(new Edge<>(v.get(1), v.get(2), 30));

        Graph<Integer> graph = new Graph<>(new HashSet<>(v), edges);

        assertNull(GraphAlgorithms.kruskals(graph));
    }

    @Test
    public void testKruskallWithIncompleteTree2() {
        List<Vertex<Integer>> v = new ArrayList<>();
        v.add(new Vertex<>(5));
        v.add(new Vertex<>(15));
        v.add(new Vertex<>(25));
        v.add(new Vertex<>(35));

        Set<Edge<Integer>> edges = new HashSet<>();
        edges.add(new Edge<>(v.get(0), v.get(1), 10));
        edges.add(new Edge<>(v.get(1), v.get(0), 10));

        edges.add(new Edge<>(v.get(2), v.get(3), 30));
        edges.add(new Edge<>(v.get(3), v.get(2), 30));

        Graph<Integer> graph = new Graph<>(new HashSet<>(v), edges);

        assertNull(GraphAlgorithms.kruskals(graph));
    }

    @Test
    public void testDijkstraWithNoPath() {
        List<Vertex<Integer>> v = new ArrayList<>();
        v.add(new Vertex<>(5));
        v.add(new Vertex<>(15));
        v.add(new Vertex<>(25));
        v.add(new Vertex<>(35));

        Set<Edge<Integer>> edges = new HashSet<>();
        edges.add(new Edge<>(v.get(0), v.get(1), 10));
        edges.add(new Edge<>(v.get(1), v.get(0), 10));
        edges.add(new Edge<>(v.get(0), v.get(2), 20));
        edges.add(new Edge<>(v.get(2), v.get(0), 20));
        edges.add(new Edge<>(v.get(2), v.get(1), 30));
        edges.add(new Edge<>(v.get(1), v.get(2), 30));

        Graph<Integer> graph = new Graph<>(new HashSet<>(v), edges);

        Map<Vertex<Integer>, Integer> expected = new HashMap<>();
        expected.put(new Vertex<>(5), 0);
        expected.put(new Vertex<>(15), 10);
        expected.put(new Vertex<>(25), 20);
        expected.put(new Vertex<>(35), Integer.MAX_VALUE);

        assertEquals(
                expected,
                GraphAlgorithms.dijkstras(v.get(0), graph)
        );
    }

    private void addUndirectedEdge(Vertex<Integer> v1, Vertex<Integer> v2, Set<Edge<Integer>> edges, int weight) {
        edges.add(new Edge<>(v1, v2, weight));
        edges.add(new Edge<>(v2, v1, weight));
    }

    private <T> void assertListEquals(List<T> expected, List<T> actual) {
        assertArrayEquals(
                expected.toArray(),
                actual.toArray()
        );
    }

    private <T extends Exception> void assertThrows(Class<T> exceptionClass, Runnable executable) {
        try {
            executable.run();
        } catch (Exception e) {
            if (!exceptionClass.isInstance(e)) {
                fail(e.getClass().getSimpleName() + " was thrown, expected " + exceptionClass.getSimpleName());
            } else {
                return;
            }
        }

        fail("Expected " + exceptionClass.getSimpleName() + ", but no exception was thrown");
    }

}