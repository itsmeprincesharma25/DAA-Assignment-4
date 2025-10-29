package app;

import com.google.gson.*;
import graph.core.Graph;
import graph.scc.CondensationGraph;
import graph.scc.TarjanSCC;
import graph.topo.TopoSort;
import graph.dagsp.DagShortestPath;
import graph.dagsp.DagLongestPath;

import java.io.FileReader;
import java.util.*;

/**
 * Simple CLI:
 *   java -jar target/daa-assignment-4-1.0.0.jar <mode> <data.json>
 * modes: scc | topo | dag-sssp | dag-longest
 */
public class App {
    // how the JSON looks
    static class EdgeJson { int u, v, w; }
    static class InputJson { boolean directed; int n; List<EdgeJson> edges; Integer source; String weight_model; }

    static Graph readGraph(String path) throws Exception {
        Gson gson = new Gson();
        try (FileReader fr = new FileReader(path)) {
            InputJson in = gson.fromJson(fr, InputJson.class);
            Graph g = new Graph(in.n, in.directed);
            for (EdgeJson e : in.edges) g.addEdge(e.u, e.v, e.w);
            return g;
        }
    }
    static Integer readSource(String path) throws Exception {
        Gson gson = new Gson();
        try (FileReader fr = new FileReader(path)) {
            return gson.fromJson(fr, InputJson.class).source;
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java -jar target/daa-assignment-4-1.0.0.jar <mode> <data.json>");
            System.out.println("Modes: scc | topo | dag-sssp | dag-longest");
            return;
        }
        String mode = args[0], path = args[1];
        Graph g = readGraph(path);

        switch (mode) {
            case "scc" -> {
                TarjanSCC scc = new TarjanSCC(g);
                int[] comp = scc.run();
                var comps = scc.components();
                System.out.println("SCC count = " + comps.size());
                for (int i = 0; i < comps.size(); i++) System.out.println("Component " + i + ": " + comps.get(i));
                var cg = new CondensationGraph(g, new TarjanSCC(g));
                System.out.println("Condensation DAG nodes = " + cg.compCount);
            }
            case "topo" -> {
                var cg = new CondensationGraph(g, new TarjanSCC(g));
                var order = TopoSort.kahn(cg.dag);
                System.out.println("Topo order (components): " + order);
                // expand to original vertex order (one valid order)
                List<Integer> taskOrder = new ArrayList<>();
                for (int cid : order) taskOrder.addAll(cg.components.get(cid));
                System.out.println("Derived order (original tasks): " + taskOrder);
            }
            case "dag-sssp" -> {
                var cg = new CondensationGraph(g, new TarjanSCC(g));
                Integer src = readSource(path);
                if (src == null) throw new IllegalArgumentException("source missing in dataset");
                int srcComp = cg.compId[src];
                var res = DagShortestPath.sssp(cg.dag, srcComp);
                System.out.println("Distances from component(" + src + "->" + srcComp + "): " + Arrays.toString(res.dist));
            }
            case "dag-longest" -> {
                var cg = new CondensationGraph(g, new TarjanSCC(g));
                Integer src = readSource(path);
                if (src == null) throw new IllegalArgumentException("source missing in dataset");
                int srcComp = cg.compId[src];
                var res = DagLongestPath.longestFromSource(cg.dag, srcComp);
                int t = DagLongestPath.argmax(res.best);
                System.out.println("Longest distances from component " + srcComp + ": " + Arrays.toString(res.best));
                System.out.println("Critical path (component indices) ends at " + t + " : " + res.reconstruct(t));
            }
            default -> throw new IllegalArgumentException("Unknown mode " + mode);
        }
    }
}
