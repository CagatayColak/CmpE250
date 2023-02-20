import java.util.HashMap;
import java.util.Map;

public class Node implements Comparable<Node>{

    private String name;
    private Map<Node, Integer> adjacentNodes;
    private int cost;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void setAdjacentNodes(Map<Node, Integer> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Node(String name) {
        this.name = name;
        this.adjacentNodes = new HashMap<>();
        this.cost = Integer.MAX_VALUE;
    }

    public void add(Node neighborNode, int cost){
        this.adjacentNodes.put(neighborNode, cost);
        neighborNode.getAdjacentNodes().put(this, cost);

    }

    @Override
    public int compareTo(Node otherNode) {
        return Integer.compare(this.cost, otherNode.cost);
    }
}
