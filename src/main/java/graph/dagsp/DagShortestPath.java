package graph.dagsp;

import graph.core.Graph;
import graph.core.WeightedEdge;
import graph.topo.TopoSort;

import java.util.*;

/**
 * Single-source shortest paths on a DAG.
 * Simple student version: relax edges in topological order.
 */
public class DagShortestPath {

    // result holder: distances + parents so we can rebuild paths
    public static class Result {
        public final int source;
        public final long[] dist;
        public final int[] parent;

        public Result(int source, long[] dist, int[] parent) {
            this.source = source;
            this.dist = dist;
            this.parent = parent;
        }

        /** rebuild path source -> target (empty list if unreachable) */
        public List<Integer> reconstruct(int target) {
            if (dist[target] == Long.MAX_VALUE) return List.of();
            List<Integer> path = new ArrayList<>();
            for (int v = target; v != -1; v = parent[v]) path.add(v);
            Collections.reverse(path);
            return path;
        }
    }

    /** shortest paths in DAG from 'source' */
    public static Result sssp(Graph dag, int source) {
        List<Integer> topo = TopoSort.kahn(dag);

        long[] dist = new long[dag.n];
        int[] parent = new int[dag.n];
        Arrays.fill(dist, Long.MAX_VALUE);
        Arrays.fill(parent, -1);
        dist[source] = 0;

        for (int u : topo) {
            if (dist[u] == Long.MAX_VALUE) continue; // skip unreachable nodes
            for (WeightedEdge e : dag.outEdges(u)) {
                long nd = dist[u] + e.w();
                if (nd < dist[e.v()]) {       // relax
                    dist[e.v()] = nd;
                    parent[e.v()] = u;
                }
            }
        }
        return new Result(source, dist, parent);
    }
}
