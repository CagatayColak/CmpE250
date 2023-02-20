import java.util.*;

public class WeightedGraph {

    private PriorityQueue<Node> heap = new PriorityQueue<>();
    private HashMap<String, Integer> indexesOfNodes = new HashMap<>();
    private ArrayList<Node> Nodes = new ArrayList<>();

    public PriorityQueue<Node> getHeap() {
        return heap;
    }

    public void setHeap(PriorityQueue<Node> heap) {
        this.heap = heap;
    }

    public HashMap<String, Integer> getIndexesOfNodes() {
        return indexesOfNodes;
    }

    public void setIndexesOfNodes(HashMap<String, Integer> indexesOfNodes) {
        this.indexesOfNodes = indexesOfNodes;
    }

    public ArrayList<Node> getNodes() {
        return Nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        Nodes = nodes;
    }

    public WeightedGraph() {
    }

    public void addNode(Node newNode, int index){
        indexesOfNodes.put(newNode.getName(),index);
    }

    public void computeShortestPath(Node sourceNode){
        sourceNode.setCost(0);
        Set<Node> settledNodes = new HashSet<>();

        getHeap().add(sourceNode);
        while (settledNodes.size() != getNodes().size()){
            if (getHeap().isEmpty()){
                return;
            }

            Node minDistanceNode = getHeap().poll();

            if (settledNodes.contains(minDistanceNode))
                continue;

            settledNodes.add(minDistanceNode);

            for(Map.Entry<Node, Integer> adjacentPair: minDistanceNode.getAdjacentNodes().entrySet()){
                Node adjacentNode = adjacentPair.getKey();
                Integer weight = adjacentPair.getValue();
                if (!settledNodes.contains(adjacentNode)){
                    int costOfMinDistanceNode = minDistanceNode.getCost();
                    if(costOfMinDistanceNode + weight < adjacentNode.getCost()){
                        adjacentNode.setCost(costOfMinDistanceNode + weight);
                    }
                    getHeap().add(adjacentNode);
                }
            }
        }

    }

    public int computeMinimumSpanningTree(Node sourceNode, int numberOfFlaggedNodes, ArrayList<String> flaggedNodes){
        sourceNode.setCost(0);
        int counter = 0;
        getHeap().add(sourceNode);

        while (!getHeap().isEmpty()){
            if(numberOfFlaggedNodes == 0){
                return counter;
            }
            Node minDistanceNode2 = getHeap().poll();

            if (flaggedNodes.contains(minDistanceNode2.getName())){
                counter += minDistanceNode2.getCost();
                minDistanceNode2.setCost(0);
                numberOfFlaggedNodes--;
            }

            for(Map.Entry<Node, Integer> adjacentPair: minDistanceNode2.getAdjacentNodes().entrySet()){
                Node adjacentNode = adjacentPair.getKey();
                int weight = adjacentPair.getValue();
                int costOfMinDistanceNode = minDistanceNode2.getCost();
                    if(costOfMinDistanceNode + weight < adjacentNode.getCost()){
                        getHeap().remove(adjacentNode);
                        adjacentNode.setCost(costOfMinDistanceNode + weight);
                        getHeap().add(adjacentNode);
                    }
                }
            }
        if(numberOfFlaggedNodes == 0){
            return counter;
        }
        else{
            return -1;
        }
    }
}
