package graph.core;

import java.util.*;

/**
 * Minimal adjacency-list graph.
 * I keep it generic enough for both DAGs and general directed graphs.
 */
public class Graph {
    public final int n;            // number of vertices 0..n-1
    public final boolean directed; // we only use directed=true here
    private final List<List<WeightedEdge>> adj; // adjacency lists

    public Graph(int n, boolean directed) {
        this.n = n;
        this.directed = directed;
        this.adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    }

    // add edge u->v with weight w (if undirected, also add v->u)
    public void addEdge(int u, int v, int w) {
        adj.get(u).add(new WeightedEdge(u, v, w));
        if (!directed) adj.get(v).add(new WeightedEdge(v, u, w));
    }

    // get all edges going out of u
    public List<WeightedEdge> outEdges(int u) { return adj.get(u); }

    // used to build the condensation graph from SCC ids
    public Graph subgraphByMapping(int[] compId, int compCount) {
        Graph g = new Graph(compCount, true);
        // avoid parallel duplicate component edges
        Set<Long> seen = new HashSet<>();
        for (int u = 0; u < n; u++) {
            int cu = compId[u];
            for (WeightedEdge e : adj.get(u)) {
                int cv = compId[e.v()];
                if (cu != cv) {
                    long key = (((long)cu) << 32) | (cv & 0xffffffffL);
                    if (seen.add(key)) g.addEdge(cu, cv, e.w());
                }
            }
        }
        return g;
    }
}
