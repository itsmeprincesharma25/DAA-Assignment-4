package graph.dagsp;

import graph.core.Graph;
import graph.core.WeightedEdge;
import graph.topo.TopoSort;

import java.util.*;

/**
 * Longest path in a DAG (assuming non-negative weights).
 * Same idea as shortest, but maximize instead of minimize.
 */
public class DagLongestPath {
    public static class Result {
        public final int source;
        public final long[] best;
        public final int[] parent;
        public Result(int source, long[] best, int[] parent) {
            this.source = source; this.best = best; this.parent = parent;
        }
        public List<Integer> reconstruct(int target) {
            if (best[target] == Long.MIN_VALUE) return List.of();
            List<Integer> path = new ArrayList<>();
            for (int v = target; v != -1; v = parent[v]) path.add(v);
            Collections.reverse(path);
            return path;
        }
    }

    public static Result longestFromSource(Graph dag, int source) {
        List<Integer> topo = TopoSort.kahn(dag);
        long[] best = new long[dag.n];
        int[] parent = new int[dag.n];
        Arrays.fill(best, Long.MIN_VALUE);
        Arrays.fill(parent, -1);
        best[source] = 0;

        for (int u : topo) {
            if (best[u] == Long.MIN_VALUE) continue;
            for (WeightedEdge e : dag.outEdges(u)) {
                long nd = best[u] + e.w();
                if (nd > best[e.v()]) { best[e.v()] = nd; parent[e.v()] = u; }
            }
        }
        return new Result(source, best, parent);
    }

    // simple helper to find index of max value
    public static int argmax(long[] a) {
        int idx = 0;
        for (int i = 1; i < a.length; i++) if (a[i] > a[idx]) idx = i;
        return idx;
    }
}
