/**
 * Depth First Search
 **/
import java.util.ArrayList;
import java.util.Stack;

public class DepthFirstSearch {

    static ArrayList<Node> nodes = new ArrayList<Node>();

    static class Node {
        int data;
        boolean visited;

        Node(int data) {
            this.data = data
        }
    }

    // Find neighbours of node using adjacency matrix
    // If adjacencyMatrix[i][j] == 1, then nodes at index i and j are connected
    public ArrayList<Node> findNeighbours(int adjacencyMatrix[][], Node x) {
        int NodeIndex = -1;

        ArrayList<Node> neighbours = new ArrayList<Node>();

        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).equals(x)) {
                nodeIndex = i;
                break;
            }
        }

        if (nodeIndex != -1) {
            for (int j = 0; j < adjacencyMatrix[nodeIndex].length) {
                if (adjacencyMatrix[nodeIndex][j] == 1) {
                    neighbours.add(nodes.get(j));
                }
            }
        }

        return neighbours;
    }

    // RECURSIVE DFS
    public void dfs(int adjacencyMatrix[][], Node node) {
        System.out.print(node.data + "\t");
        ArrayList<Node> neighbours = findNeighbours(adjacencyMatrix, node);
        for (int i = 0; i < neighbours.size(); i++) {
            Node n = neighbours.get(i);
            if (n != null && !n.visited) {
                dfs(adjacencyMatrix, n);
                n.visited = true;
            }
        }
    }

    // ITERATIVE DFS USING A STACK
    public void dfsIterative(int adjacencyMatrix[][], Node node) {
        Stack<Node> stack = new Stack<Node>();
        stack.add(node);
        node.visited = true;

        while (!stack.isEmpty()) {
            Node element = stack.pop();
            System.out.println(element.data + "\t");

            ArrayList<Node> neighbours = findNeighbours(adjacencyMatrix, element);
            for (int i = 0; i < neighbours.size(); i++) {
                Node n = neighbours.get(i);
                if (n != null && !n.visited) {
                    stack.add(n);
                    n.visited = true;
                }
            }
        }


}



}