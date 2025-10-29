package graph;

import graph.core.Graph;
import graph.scc.TarjanSCC;
import graph.topo.TopoSort;
import graph.dagsp.DagShortestPath;
import graph.dagsp.DagLongestPath;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class BasicsTest {

    // checks SCC merges a 3-cycle into 1 component
    @Test
    public void testSCCSimpleCycle() {
        Graph g = new Graph(3, true);
        g.addEdge(0,1,1); g.addEdge(1,2,1); g.addEdge(2,0,1);
        TarjanSCC scc = new TarjanSCC(g);
        scc.run();
        assertEquals(1, scc.getCompCount());
    }

    // checks Kahn gives a valid order for a chain 0->1->2->3
    @Test
    public void testTopoChain() {
        Graph g = new Graph(4, true);
        g.addEdge(0,1,1); g.addEdge(1,2,1); g.addEdge(2,3,1);
        List<Integer> order = TopoSort.kahn(g);
        assertEquals(List.of(0,1,2,3), order);
    }

    // shortest path on a small DAG
    @Test
    public void testDagSSSP() {
        Graph g = new Graph(4, true);
        g.addEdge(0,1,1); g.addEdge(0,2,5); g.addEdge(1,2,1); g.addEdge(2,3,2);
        var res = DagShortestPath.sssp(g, 0);
        assertEquals(0, res.dist[0]);
        assertEquals(2, res.dist[2]); // 0->1->2
        assertEquals(4, res.dist[3]); // 0->1->2->3
    }

    // longest (critical) path on the same DAG
    @Test
    public void testDagLongest() {
        Graph g = new Graph(4, true);
        g.addEdge(0,1,1);
        g.addEdge(0,2,5);
        g.addEdge(1,2,1);
        g.addEdge(2,3,2);

        var res = DagLongestPath.longestFromSource(g, 0);

        // source stays 0
        assertEquals(0, res.best[0]);

        // longest to node 2 should be 5 (via 0->2)
        assertEquals(5, res.best[2]);

        // longest to node 3 is 7 (0->2->3)
        assertEquals(7, res.best[3]);
    }
}
