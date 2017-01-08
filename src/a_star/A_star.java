package a_star;

import dijkstra.Vertex;
import dijkstra.Edge;
import dijkstra.Graph;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map;


/**
 * Created by sara on 08/01/17.
 */
public class A_star {

    private Map<Vertex, Vertex> cameFrom;
    private Set<Vertex> evaluatedVertices;
    private Set<Vertex> discoveredNodes;
    // THE COST OF GETTING FROM THE START NODE TO THE CURRENT NODE
    private Map<Vertex, Integer> gScore;
    // THE COST OF GETTING FROM THE START NODE TO THE GOAL VIA CURRENT NODE
    // PARTLY KNOWN, PARTLY HEURISTIC
    private Map<Vertex, Integer> fScore;
    private final Vertex start;
    private final Vertex goal;
    private final List<Edge> edges;
    private final List<Vertex> nodes;

    public A_star(Vertex start, Vertex goal, Graph graph) {
        this.edges = new ArrayList<Edge>(graph.getEdges());
        this.nodes = new ArrayList<Vertex>(graph.getVertices());
        this.start = start;
        this.goal = goal;
        // INITIALIZE WITH COST OF START NODE GOING TO ITSELF
        this.gScore.put(start, 0);
        this.fScore.put(start, heuristicCostEstimate(start, goal));
    }


    public int heuristicCostEstimate(Vertex start, Vertex goal) {
        // TODO: Write heuristic here. Does not need to be perfect.
        return 0;
    }

    public ArrayList<Vertex> execute() {

        while (!discoveredNodes.isEmpty()) {
            Vertex current = getMinNode();
            if (current.equals(goal)) {
                return reconstructPath(cameFrom, current);
            }

            discoveredNodes.remove(current);
            evaluatedVertices.add(current);
            for (Vertex neighbour: getNeighbours(current)) {
                if (evaluatedVertices.contains(neighbour)) {
                    continue; // IGNORE EVALUATED NEIGHBOURS
                }
                // THE DISTANCE FROM THE START TO A NEIGHBOUR
                int distance = 0;
                for (Edge edge: edges) {
                    if (edge.getSource().equals(current) && edge.getDestination().equals(neighbour)) {
                        distance = edge.getWeight();
                    }
                }
                int tentativeScore = gScore.get(current) + distance;

                if (!discoveredNodes.contains(neighbour)) {
                    discoveredNodes.add(neighbour);
                } else if (tentativeScore >= gScore.get(neighbour)) {
                    continue; // NOT A GOOD PATH
                }

                cameFrom.put(neighbour, current);
                gScore.put(neighbour, tentativeScore);
                int estimateToNextNode = gScore.get(neighbour) + heuristicCostEstimate(neighbour, goal);
                fScore.put(neighbour, estimateToNextNode);
            }
        }
        throw new RuntimeException("Error occurred");
    }

    private Vertex getMinNode() {
        Vertex minNode = null;
        int minValue = Integer.MAX_VALUE;
        for (Vertex key: discoveredNodes) {
             if (fScore.get(key) < minValue) {
                 minNode = key;
                 minValue = fScore.get(key);
             }
        }
        return minNode;
    }

    private ArrayList<Vertex> reconstructPath(Map<Vertex, Vertex> cameFrom, Vertex current) {
        ArrayList<Vertex> totalPath = new ArrayList<Vertex>();
        totalPath.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            totalPath.add(current);
        }
        return totalPath;
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

    private boolean isSettled(Vertex node) {
        return evaluatedVertices.contains(node);
    }
}
