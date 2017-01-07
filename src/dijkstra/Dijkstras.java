package dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dijkstra.*;

/**
 * Dijkstra's Algorithm
 */
public class Dijkstras {

    private final List<Vertex> nodes;
    private final List<Edge> edges;
    private Set<Vertex> nodesToConsider;
    private Set<Vertex> consideredNodes;
    private Map<Vertex, Vertex> predecessor;
    private Map<Vertex, Integer> distance;

    public Dijkstras(Graph graph) {
        this.nodes = new ArrayList<Vertex>(graph.getVertices());
        this.edges = new ArrayList<Edge>(graph.getEdges());
    }

    public void execute(Vertex source) {
        consideredNodes = new HashSet<Vertex>();
        nodesToConsider = new HashSet<Vertex>();
        distance = new HashMap<Vertex, Integer>();
        predecessor = new HashMap<Vertex, Vertex>();
        distance.put(source, 0);
        consideredNodes.add(source);
        while (nodesToConsider.size() > 0) {
            Vertex node = getMinimum(nodesToConsider);
            consideredNodes.add(node);
            nodesToConsider.remove(node);
            findMinimalDistance(node);
        }
    }

    private void findMinimalDistance(Vertex node) {
        List<Vertex> adjacentNodes = getNeighbours(node);
        for (Vertex target: adjacentNodes) {
            int toNext = getShortestDistance(node) + getDistance(node, target);
            if (getShortestDistance(target) > toNext) {
                distance.put(target, toNext);
                predecessor.put(target, node);
                nodesToConsider.add(target);
            }
        }
    }

    private int getDistance(Vertex source, Vertex destination) {
        for (Edge edge: edges) {
            if (edge.getSource().equals(source) && edge.getDestination().equals(destination)) {
                return edge.getWeight();
            }
        }
        throw new RuntimeException("Can't find edge!");
    }

    private List<Vertex> getNeighbours(Vertex node) {
        List<Vertex> neighbours = new ArrayList<Vertex>();
        for (Edge edge: edges) {
            if (edge.getSource().equals(node) && !isSettled(edge.getDestination())) {
                neighbours.add(edge.getDestination());
            }
        }
        return neighbours;
    }

    private Vertex getMinimum(Set<Vertex> vertices) {
        Vertex minimum = null;
        for (Vertex vertex: vertices) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Vertex node) {
        return consideredNodes.contains(node);
    }

    private int getShortestDistance(Vertex destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    public LinkedList<Vertex> getPath(Vertex target) {
        LinkedList<Vertex> path = new LinkedList<Vertex>();
        Vertex step = target;
        // Check if path exists
        if (predecessor.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessor.get(step) != null) {
            step = predecessor.get(step);
            path.add(step);
        }
        Collections.reverse(path);
        return path;
    }
}
