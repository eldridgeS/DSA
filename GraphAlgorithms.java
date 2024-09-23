import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Eldridge Surianto
 * @version 1.0
 * @userid esurianto3
 * @GTID 903440765
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
        if (start == null || graph == null
            || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Invalid Arguments");
        }

        //create result, queue, visited set, add start to queue and visited set
        List<Vertex<T>> visitedOrder = new ArrayList<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        Set<Vertex<T>> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        //while queue not empty, dequeue and add neighbors
        while (!queue.isEmpty()) {
            Vertex<T> current = queue.poll();
            visitedOrder.add(current);

            //for neighbors, if neighbor not visited then add to queue, set
            List<VertexDistance<T>> neighbors = graph.getAdjList().get(current);
            for (VertexDistance<T> neighbor : neighbors) {
                if (!visited.contains(neighbor.getVertex())) {
                    queue.add(neighbor.getVertex());
                    visited.add(neighbor.getVertex());
                }
            }
        }

        return visitedOrder;
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
        if (start == null || graph == null
                || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Invalid Arguments");
        }

        //create result array, and visited set
        List<Vertex<T>> visitedOrder = new ArrayList<>();
        Set<Vertex<T>> visited = new HashSet<>();

        //call recursive function
        dfsHelper(start, graph, visited, visitedOrder);

        return visitedOrder;
    }

    /**
     * Helper method for the dfs method
     * @param current current vertex to be searched
     * @param graph graph to be recursed
     * @param visited visited set to be added to
     * @param visitedOrder order of nodes to be returned
     * @param <T> the generic typing of the data
     */
    private static <T> void dfsHelper(Vertex<T> current, Graph<T> graph,
                                      Set<Vertex<T>> visited, List<Vertex<T>> visitedOrder) {

        //add current to set and result array
        visited.add(current);
        visitedOrder.add(current);

        //for neighbors, if not in visited set, recurse with neighbor.getVertex
        List<VertexDistance<T>> neighbors = graph.getAdjList().get(current);
        for (VertexDistance<T> neighbor : neighbors) {
            if (!visited.contains(neighbor.getVertex())) {
                dfsHelper(neighbor.getVertex(), graph, visited, visitedOrder);
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
        if (start == null || graph == null
                || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Invalid Arguments");
        }

        //create result hashmap, pq, visited set
        Map<Vertex<T>, Integer> distances = new HashMap<>();
        PriorityQueue<VertexDistance<T>> pq = new PriorityQueue<>();
        Set<Vertex<T>> visited = new HashSet<>();

        // Initialize distances map and update starting point to 0
        for (Vertex<T> vertex : graph.getVertices()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(start, 0);

        //add start to pq, while pq not empty and set smaller than graph
        //poll & getVertex, if set does not contain vertex add to set and
        //enqueue and add to map neighbors not in set with dist=curr+getdist
        //provided it is the first iteration(smallest path)
        pq.add(new VertexDistance<>(start, 0));
        while (!pq.isEmpty() && visited.size() < graph.getVertices().size()) {
            VertexDistance<T> current = pq.poll();
            Vertex<T> currentVertex = current.getVertex();

            if (!visited.contains(currentVertex)) {
                visited.add(currentVertex);

                for (VertexDistance<T> neighbor : graph.getAdjList().get(currentVertex)) {
                    if (!visited.contains(neighbor.getVertex())) {
                        int newDist = distances.get(currentVertex) + neighbor.getDistance();
                        if (newDist < distances.get(neighbor.getVertex())) {
                            distances.put(neighbor.getVertex(), newDist);
                            pq.add(new VertexDistance<>(neighbor.getVertex(), newDist));
                        }
                    }
                }
            }
        }

        return distances;
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
     * You should NOT allow self-loops or parallel edges into the MST.
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
            throw new IllegalArgumentException("Graph must not be null");
        }

        //create mst set and pq of edges, and disjointset of T
        Set<Edge<T>> mst = new HashSet<>();
        PriorityQueue<Edge<T>> pq = new PriorityQueue<>(graph.getEdges());
        DisjointSet<T> disjointSet = new DisjointSet<>();

        // for vertices in graph, find(add) to disjointSet
        for (Vertex<T> vertex : graph.getVertices()) {
            disjointSet.find(vertex.getData());
        }

        //while pq not empty and mst less than desired size, poll edge
        //find root of vertices of edge, if not equal then add both directions
        //to mst, then union roots to disjointset
        while (!pq.isEmpty() && mst.size() < 2 * (graph.getVertices().size() - 1)) {
            Edge<T> edge = pq.poll();
            Vertex<T> u = edge.getU();
            Vertex<T> v = edge.getV();

            T uRoot = disjointSet.find(u.getData());
            T vRoot = disjointSet.find(v.getData());

            if (!uRoot.equals(vRoot)) {
                mst.add(edge);
                mst.add(new Edge<>(v, u, edge.getWeight()));
                disjointSet.union(uRoot, vRoot);
            }
        }

        // return mst if found if not return null
        if (mst.size() == 2 * (graph.getVertices().size() - 1)) {
            return mst;
        } else {
            return null;
        }
    }
}
