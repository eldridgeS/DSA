import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Homework8TestCases {
    private Graph<Integer> directedGraph1;
    private Graph<Character> undirectedGraph1;
    private Graph<Integer> directedGraph2;
    private Graph<Character> undirectedGraph2;

    @Before
    public void setUp() {
        directedGraph1 = createDirectedGraph1();
        undirectedGraph1 = createUndirectedGraph1();
        directedGraph2 = createDirectedGraph2();
        undirectedGraph2 = createUndirectedGraph2();
    }

    private Graph<Integer> createDirectedGraph1() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        for (int i = 1; i <= 5; i++) {
            vertices.add(new Vertex<>(i));
        }

        Set<Edge<Integer>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 1));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(3), 2));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(4), 3));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(5), 4));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(1), 5));

        return new Graph<>(vertices, edges);
    }

    private Graph<Character> createUndirectedGraph1() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 65; i <= 69; i++) {
            vertices.add(new Vertex<>((char) i));
        }

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 1));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 1));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 2));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 2));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('D'), 3));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('B'), 3));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 4));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 4));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 5));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('D'), 5));

        return new Graph<>(vertices, edges);
    }

    private Graph<Integer> createDirectedGraph2() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        for (int i = 1; i <= 10; i++) {
            vertices.add(new Vertex<>(i));
        }

        Set<Edge<Integer>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 2));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(3), 4));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(3), 1));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(4), 5));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(5), 3));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(6), 7));
        edges.add(new Edge<>(new Vertex<>(6), new Vertex<>(7), 2));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(8), 1));
        edges.add(new Edge<>(new Vertex<>(8), new Vertex<>(9), 6));
        edges.add(new Edge<>(new Vertex<>(9), new Vertex<>(10), 4));
        edges.add(new Edge<>(new Vertex<>(10), new Vertex<>(1), 3));

        return new Graph<>(vertices, edges);
    }

    private Graph<Character> createUndirectedGraph2() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 71; i <= 75; i++) {
            vertices.add(new Vertex<>((char) i));
        }

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('H'), 9));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('G'), 9));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('I'), 10));
        edges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('G'), 10));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('J'), 4));
        edges.add(new Edge<>(new Vertex<>('J'), new Vertex<>('H'), 4));
        edges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('K'), 6));
        edges.add(new Edge<>(new Vertex<>('K'), new Vertex<>('I'), 6));
        edges.add(new Edge<>(new Vertex<>('J'), new Vertex<>('K'), 7));
        edges.add(new Edge<>(new Vertex<>('K'), new Vertex<>('J'), 7));

        return new Graph<>(vertices, edges);
    }

    /////////////// BFS tests
    @Test
    public void testBfsNullStart() {
        assertThrows(IllegalArgumentException.class, () -> {
            GraphAlgorithms.bfs(null, directedGraph1);
        });
    }

    @Test
    public void testBfsNullGraph() {
        Vertex<Integer> start = new Vertex<>(1);
        assertThrows(IllegalArgumentException.class, () -> {
            GraphAlgorithms.bfs(start, null);
        });
    }

    @Test
    public void testBfsStartNotInGraph() {
        Vertex<Integer> start = new Vertex<>(6);
        assertThrows(IllegalArgumentException.class, () -> {
            GraphAlgorithms.bfs(start, directedGraph1);
        });
    }

    @Test
    public void testBfsDirectedGraph1() {
        Vertex<Integer> start = new Vertex<>(1);
        List<Vertex<Integer>> expected = Arrays.asList(new Vertex<>(1), new Vertex<>(2), new Vertex<>(3), new Vertex<>(4), new Vertex<>(5));
        List<Vertex<Integer>> result = GraphAlgorithms.bfs(start, directedGraph1);
        assertEquals(expected, result);
    }

    @Test
    public void testBfsUndirectedGraph1() {
        Vertex<Character> start = new Vertex<>('A');
        List<Vertex<Character>> expected = Arrays.asList(new Vertex<>('A'), new Vertex<>('B'), new Vertex<>('C'), new Vertex<>('D'), new Vertex<>('E'));
        List<Vertex<Character>> result = GraphAlgorithms.bfs(start, undirectedGraph1);
        assertEquals(expected, result);
    }

    @Test
    public void testBfsDirectedGraph2() {
        Vertex<Integer> start = new Vertex<>(1);
        List<Vertex<Integer>> expected = Arrays.asList(new Vertex<>(1), new Vertex<>(2), new Vertex<>(3), new Vertex<>(4), new Vertex<>(5), new Vertex<>(6), new Vertex<>(7), new Vertex<>(8), new Vertex<>(9), new Vertex<>(10));
        List<Vertex<Integer>> result = GraphAlgorithms.bfs(start, directedGraph2);
        assertEquals(expected, result);
    }

    @Test
    public void testBfsUndirectedGraph2() {
        Vertex<Character> start = new Vertex<>('G');
        List<Vertex<Character>> expected = Arrays.asList(new Vertex<>('G'), new Vertex<>('H'), new Vertex<>('I'), new Vertex<>('J'), new Vertex<>('K'));
        List<Vertex<Character>> result = GraphAlgorithms.bfs(start, undirectedGraph2);
        assertEquals(expected, result);
    }

    @Test
    public void testBfsDisconnectedGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        vertices.add(new Vertex<>(1));
        vertices.add(new Vertex<>(2));
        Set<Edge<Integer>> edges = new HashSet<>();
        Graph<Integer> disconnectedGraph = new Graph<>(vertices, edges);

        Vertex<Integer> start = new Vertex<>(1);
        List<Vertex<Integer>> expected = Arrays.asList(new Vertex<>(1));
        List<Vertex<Integer>> result = GraphAlgorithms.bfs(start, disconnectedGraph);
        assertEquals(expected, result);
    }

    @Test
    public void testBfsSingleVertexGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        vertices.add(new Vertex<>(1));
        Set<Edge<Integer>> edges = new HashSet<>();
        Graph<Integer> singleVertexGraph = new Graph<>(vertices, edges);

        Vertex<Integer> start = new Vertex<>(1);
        List<Vertex<Integer>> expected = Arrays.asList(new Vertex<>(1));
        List<Vertex<Integer>> result = GraphAlgorithms.bfs(start, singleVertexGraph);
        assertEquals(expected, result);
    }

    ////////////////// DFS tests
    @Test
    public void testDfsNullStart() {
        assertThrows(IllegalArgumentException.class, () -> {
            GraphAlgorithms.dfs(null, directedGraph1);
        });
    }

    @Test
    public void testDfsNullGraph() {
        Vertex<Integer> start = new Vertex<>(1);
        assertThrows(IllegalArgumentException.class, () -> {
            GraphAlgorithms.dfs(start, null);
        });
    }

    @Test
    public void testDfsStartNotInGraph() {
        Vertex<Integer> start = new Vertex<>(6);
        assertThrows(IllegalArgumentException.class, () -> {
            GraphAlgorithms.dfs(start, directedGraph1);
        });
    }

    @Test
    public void testDfsDirectedGraph1() {
        Vertex<Integer> start = new Vertex<>(1);
        List<Vertex<Integer>> expected = Arrays.asList(
                new Vertex<>(1), new Vertex<>(2), new Vertex<>(3),
                new Vertex<>(4), new Vertex<>(5)
        );
        List<Vertex<Integer>> result = GraphAlgorithms.dfs(start, directedGraph1);
        assertEquals(expected, result);
    }

    @Test
    public void testDfsUndirectedGraph1() {
        Vertex<Character> start = new Vertex<>('A');
        List<Vertex<Character>> expected = Arrays.asList(
                new Vertex<>('A'), new Vertex<>('B'), new Vertex<>('D'),
                new Vertex<>('C'), new Vertex<>('E')
        );
        List<Vertex<Character>> result = GraphAlgorithms.dfs(start, undirectedGraph1);
        assertEquals(expected, result);
    }

    @Test
    public void testDfsDirectedGraph2() {
        Vertex<Integer> start = new Vertex<>(1);
        List<Vertex<Integer>> expected = Arrays.asList(
                new Vertex<>(1), new Vertex<>(2), new Vertex<>(3),
                new Vertex<>(4), new Vertex<>(5), new Vertex<>(6),
                new Vertex<>(7), new Vertex<>(8), new Vertex<>(9),
                new Vertex<>(10)
        );
        List<Vertex<Integer>> result = GraphAlgorithms.dfs(start, directedGraph2);
        assertEquals(expected, result);
    }

    @Test
    public void testDfsUndirectedGraph2() {
        Vertex<Character> start = new Vertex<>('G');
        List<Vertex<Character>> expected = Arrays.asList(
                new Vertex<>('G'), new Vertex<>('H'), new Vertex<>('J'),
                new Vertex<>('K'), new Vertex<>('I')
        );
        List<Vertex<Character>> result = GraphAlgorithms.dfs(start, undirectedGraph2);
        assertEquals(expected, result);
    }

    @Test
    public void testDfsDisconnectedGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        vertices.add(new Vertex<>(1));
        vertices.add(new Vertex<>(2));
        Set<Edge<Integer>> edges = new HashSet<>();
        Graph<Integer> disconnectedGraph = new Graph<>(vertices, edges);

        Vertex<Integer> start = new Vertex<>(1);
        List<Vertex<Integer>> expected = Arrays.asList(new Vertex<>(1));
        List<Vertex<Integer>> result = GraphAlgorithms.dfs(start, disconnectedGraph);
        assertEquals(expected, result);
    }

    @Test
    public void testDfsSingleVertexGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        vertices.add(new Vertex<>(1));
        Set<Edge<Integer>> edges = new HashSet<>();
        Graph<Integer> singleVertexGraph = new Graph<>(vertices, edges);

        Vertex<Integer> start = new Vertex<>(1);
        List<Vertex<Integer>> expected = Arrays.asList(new Vertex<>(1));
        List<Vertex<Integer>> result = GraphAlgorithms.dfs(start, singleVertexGraph);
        assertEquals(expected, result);
    }

    /////////// Dijkstra's tests
    @Test
    public void testDijkstraStartIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            GraphAlgorithms.dijkstras(null, directedGraph1);
        });
    }

    @Test
    public void testDijkstraStartNotInGraph() {
        Vertex<Integer> start = new Vertex<>(6);
        assertThrows(IllegalArgumentException.class, () -> {
            GraphAlgorithms.dijkstras(start, directedGraph1);
        });
    }

    @Test
    public void testDijkstraGraphIsNull() {
        Vertex<Integer> start = new Vertex<>(1);
        assertThrows(IllegalArgumentException.class, () -> {
            GraphAlgorithms.dijkstras(start, null);
        });
    }

    @Test
    public void testDijkstraDirectedGraph1() {
        Vertex<Integer> start = new Vertex<>(1);
        Map<Vertex<Integer>, Integer> expected = new HashMap<>();
        expected.put(new Vertex<>(1), 0);
        expected.put(new Vertex<>(2), 1);
        expected.put(new Vertex<>(3), 3);
        expected.put(new Vertex<>(4), 6);
        expected.put(new Vertex<>(5), 10);

        Map<Vertex<Integer>, Integer> result = GraphAlgorithms.dijkstras(start, directedGraph1);
        assertEquals(expected, result);
    }

    @Test
    public void testDijkstraUndirectedGraph1() {
        Vertex<Character> start = new Vertex<>('A');
        Map<Vertex<Character>, Integer> expected = new HashMap<>();
        expected.put(new Vertex<>('A'), 0);
        expected.put(new Vertex<>('B'), 1);
        expected.put(new Vertex<>('C'), 2);
        expected.put(new Vertex<>('D'), 4);
        expected.put(new Vertex<>('E'), 9);

        Map<Vertex<Character>, Integer> result = GraphAlgorithms.dijkstras(start, undirectedGraph1);
        assertEquals(expected, result);
    }

    @Test
    public void testDijkstraDirectedGraph2() {
        Vertex<Integer> start = new Vertex<>(1);
        Map<Vertex<Integer>, Integer> expected = new HashMap<>();
        expected.put(new Vertex<>(1), 0);
        expected.put(new Vertex<>(2), 2);
        expected.put(new Vertex<>(3), 3);
        expected.put(new Vertex<>(4), 8);
        expected.put(new Vertex<>(5), 11);
        expected.put(new Vertex<>(6), 18);
        expected.put(new Vertex<>(7), 20);
        expected.put(new Vertex<>(8), 21);
        expected.put(new Vertex<>(9), 27);
        expected.put(new Vertex<>(10), 31);

        Map<Vertex<Integer>, Integer> result = GraphAlgorithms.dijkstras(start, directedGraph2);
        assertEquals(expected, result);
    }

    @Test
    public void testDijkstraUndirectedGraph2() {
        Vertex<Character> start = new Vertex<>('G');
        Map<Vertex<Character>, Integer> expected = new HashMap<>();
        expected.put(new Vertex<>('G'), 0);
        expected.put(new Vertex<>('H'), 9);
        expected.put(new Vertex<>('I'), 10);
        expected.put(new Vertex<>('J'), 13);
        expected.put(new Vertex<>('K'), 16);

        Map<Vertex<Character>, Integer> result = GraphAlgorithms.dijkstras(start, undirectedGraph2);
        assertEquals(expected, result);
    }

    //////////////// Kruskal's tests
    @Test
    public void testKruskalNullGraph() {
        assertThrows(IllegalArgumentException.class, () -> {
            GraphAlgorithms.kruskals(null);
        });
    }

    @Test
    public void testKruskalSingleVertexGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        vertices.add(new Vertex<>(1));
        Set<Edge<Integer>> edges = new HashSet<>();
        Graph<Integer> singleVertexGraph = new Graph<>(vertices, edges);

        Set<Edge<Integer>> expected = new HashSet<>();
        Set<Edge<Integer>> result = GraphAlgorithms.kruskals(singleVertexGraph);
        assertEquals(expected, result);
    }

    @Test
    public void testKruskalUndirectedGraph1() {
        Set<Edge<Character>> expectedEdges = new HashSet<>();
        expectedEdges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 1));
        expectedEdges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 1));
        expectedEdges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 2));
        expectedEdges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 2));
        expectedEdges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('D'), 3));
        expectedEdges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('B'), 3));
        expectedEdges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 5));
        expectedEdges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('D'), 5));

        Set<Edge<Character>> resultEdges = new HashSet<>();
        Graph<Character> mstGraph = new Graph<>(undirectedGraph1.getVertices(), GraphAlgorithms.kruskals(undirectedGraph1));
        resultEdges.addAll(mstGraph.getEdges());

        assertEquals(expectedEdges, resultEdges);
    }

    @Test
    public void testKruskalUndirectedGraph2() {
        Set<Edge<Character>> expectedEdges = new HashSet<>();
        expectedEdges.add(new Edge<>(new Vertex<>('K'), new Vertex<>('I'), 6));
        expectedEdges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('K'), 6));
        expectedEdges.add(new Edge<>(new Vertex<>('J'), new Vertex<>('H'), 4));
        expectedEdges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('J'), 4));
        expectedEdges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('G'), 9));
        expectedEdges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('H'), 9));
        expectedEdges.add(new Edge<>(new Vertex<>('J'), new Vertex<>('K'), 7));
        expectedEdges.add(new Edge<>(new Vertex<>('K'), new Vertex<>('J'), 7));

        Set<Edge<Character>> resultEdges = new HashSet<>();
        Graph<Character> mstGraph = new Graph<>(undirectedGraph2.getVertices(), GraphAlgorithms.kruskals(undirectedGraph2));
        resultEdges.addAll(mstGraph.getEdges());

        assertEquals(expectedEdges, resultEdges);
    }
}