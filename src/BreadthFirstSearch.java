/**
 * BREADTH FIRST SEARCH
 **/
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch {

    static class Node {
        int data;
        Node leftNode;
        Node rightNode;

        Node(int data, Node firstChild, Node secondChild) {
            this.data = data;
            this.leftNode = firstChild;
            this.rightNode = secondChild;
        }

        public ArrayList<Node> getChildren() {
            ArrayList<Node> childNodes = new ArrayList<>();
            if (this.leftNode != null) {
                childNodes.add(this.leftNode);
            }
            if (this.rightNode != null) {
                childNodes.add(this.rightNode);
            }
            return childNodes;
        }

        public boolean removeNode(Node n) {
            return false;
        }
    }

    Node startNode;
    Node endNode;

    public BreadthFirstSearch(Node start, Node end) {
        this.startNode = start;
        this.endNode = end;
    }

    public boolean compute() {
        if (this.startNode.equals(endNode)) {
            System.out.println("Goal node found!");
            System.out.println(endNode);
        }

        Queue<Node> queue = new LinkedList<>();
        ArrayList<Node> explored = new ArrayList<>();
        queue.add(this.startNode);
        explored.add(this.startNode);

        while (!queue.isEmpty()) {
            Node current = queue.remove();
            if (current.equals(this.endNode)) {
                System.out.println(explored);
                return true;
            }

            else {
                if (current.getChildren().isEmpty()) {
                    return false;
                } else {
                    queue.addAll(current.getChildren());
                }
            }
            explored.add(current);
        }
        return false;
    }
}
