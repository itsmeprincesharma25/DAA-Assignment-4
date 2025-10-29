package graph.scc;

import graph.core.Graph;
import java.util.List;

/**
 * Runs Tarjan and builds the condensation DAG (each SCC becomes one node).
 */
public class CondensationGraph {
    public final Graph dag;
    public final int[] compId;
    public final int compCount;
    public final List<List<Integer>> components;

    public CondensationGraph(Graph g, TarjanSCC scc) {
        this.compId = scc.run();
        this.compCount = scc.getCompCount();
        this.components = scc.components();
        this.dag = g.subgraphByMapping(compId, compCount);
    }
}
