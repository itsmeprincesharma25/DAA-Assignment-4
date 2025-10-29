package graph.core;

/**
 * very small edge record:
 * u -> v with integer weight w
 */
public record WeightedEdge(int u, int v, int w) {}
