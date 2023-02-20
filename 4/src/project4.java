import java.io.*;
import java.util.ArrayList;

public class project4 {
    public static void main(String args[]) throws IOException {
        long startTime = System.currentTimeMillis();
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
        String line = reader.readLine();
        String[] lineSplit = line.split(" ");
        int numberOfNodes = Integer.parseInt(lineSplit[0]);
        ArrayList<String> startingEndingNodes = new ArrayList<>();
        ArrayList<String> flaggedNodes = new ArrayList<>();
        line = reader.readLine();
        lineSplit = line.split(" ");
        int numberOfFlags = Integer.parseInt(lineSplit[0]);

        for(int i = 0; i < 2 ; i++){
            line = reader.readLine();
            lineSplit = line.split(" ");
            if(i == 0){
                for(int j = 0; j < 2; j++){
                    startingEndingNodes.add(lineSplit[j]);
                    }
            }
            if (i == 1){
                for(int j = 0; j < numberOfFlags; j++){
                    flaggedNodes.add(lineSplit[j]);
                }
            }
        }

        int index = 0;
        WeightedGraph graph = new WeightedGraph();

        while ((line = reader.readLine()) != null) {
            lineSplit = line.split(" ");
            if (!graph.getIndexesOfNodes().containsKey(lineSplit[0])){
                Node node = new Node(lineSplit[0]);
                graph.addNode(node,index);
                graph.getNodes().add(index,node);
                index++;
            }
            for (int i = 1; i < lineSplit.length; i = i+2){
                if (!graph.getIndexesOfNodes().containsKey(lineSplit[i])){
                    Node node1 = new Node(lineSplit[i]);
                    graph.addNode(node1,index);
                    graph.getNodes().add(index,node1);
                    index++;
                }
                graph.getNodes().get(graph.getIndexesOfNodes().get(lineSplit[0])).add(graph.getNodes().get(graph.getIndexesOfNodes().get(lineSplit[i])),Integer.parseInt(lineSplit[i+1]));
            }
        }
        graph.computeShortestPath(graph.getNodes().get(graph.getIndexesOfNodes().get(startingEndingNodes.get(0))));
        if (graph.getNodes().get(graph.getIndexesOfNodes().get(startingEndingNodes.get(1))).getCost() == Integer.MAX_VALUE){
            writer.write(-1 + "\n");
        }
        else {
            writer.write(graph.getNodes().get(graph.getIndexesOfNodes().get(startingEndingNodes.get(1))).getCost() + "\n");
        }
        graph.getHeap().clear();
        for (Node node : graph.getNodes()){
            node.setCost(Integer.MAX_VALUE);
        }
        writer.write(graph.computeMinimumSpanningTree(graph.getNodes().get(graph.getIndexesOfNodes().get(flaggedNodes.get(0))), flaggedNodes.size(), flaggedNodes) + "\n");
        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
        writer.close();
        reader.close();
    }
}
