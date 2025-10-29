package graph.topo;

import graph.core.Graph;
import graph.core.WeightedEdge;
import java.util.*;

/**
 * Kahn's algorithm (BFS on indegrees). Works only on DAGs.
 */
public class TopoSort {
    public static List<Integer> kahn(Graph dag) {
        int n = dag.n;
        int[] indeg = new int[n];
        for (int u = 0; u < n; u++)
            for (WeightedEdge e : dag.outEdges(u)) indeg[e.v()]++;

        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) if (indeg[i] == 0) q.add(i);

        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.removeFirst();
            order.add(u);
            for (WeightedEdge e : dag.outEdges(u))
                if (--indeg[e.v()] == 0) q.add(e.v());
        }
        if (order.size() != n) throw new IllegalStateException("Not a DAG");
        return order;
    }
}
