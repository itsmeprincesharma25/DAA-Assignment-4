package graph.scc;

import graph.core.Graph;
import java.util.*;

/**
 * Tarjan's algorithm (stack + discovery times).
 * Returns an array compId[v] giving which SCC each vertex belongs to.
 */
public class TarjanSCC {
    private final Graph g;
    private int time = 0, compCount = 0;
    private final int[] disc, low, compId;
    private final boolean[] inStack;
    private final Deque<Integer> st = new ArrayDeque<>();

    public TarjanSCC(Graph g) {
        this.g = g;
        int n = g.n;
        disc = new int[n];
        low  = new int[n];
        compId = new int[n];
        inStack = new boolean[n];
        Arrays.fill(disc, -1); // -1 means “unvisited”
    }

    public int[] run() {
        for (int i = 0; i < g.n; i++)
            if (disc[i] == -1) dfs(i);
        return compId;
    }

    private void dfs(int u) {
        disc[u] = low[u] = time++;
        st.push(u);
        inStack[u] = true;

        for (var e : g.outEdges(u)) {
            int v = e.v();
            if (disc[v] == -1) {           // tree edge
                dfs(v);
                low[u] = Math.min(low[u], low[v]);
            } else if (inStack[v]) {       // back edge
                low[u] = Math.min(low[u], disc[v]);
            }
        }

        // root of an SCC?
        if (low[u] == disc[u]) {
            while (true) {
                int v = st.pop();
                inStack[v] = false;
                compId[v] = compCount;
                if (v == u) break;
            }
            compCount++;
        }
    }

    public int getCompCount() { return compCount; }

    // convenience: list of vertices per component
    public List<List<Integer>> components() {
        List<List<Integer>> comps = new ArrayList<>();
        for (int i = 0; i < compCount; i++) comps.add(new ArrayList<>());
        for (int v = 0; v < g.n; v++) comps.get(compId[v]).add(v);
        return comps;
    }
}
