import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class project5 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
        String line = reader.readLine();
        String[] lineSplit = line.split(" ");
        int numberOfNodes = Integer.parseInt(lineSplit[0]) + 8;
        int[][] graph = new int[numberOfNodes][numberOfNodes];
        int counter = 0;
        while ((line = reader.readLine()) != null) {
            lineSplit = line.split(" ");
            if (counter == 0) {
                for (int i = 0; i < lineSplit.length; i++) {
                    graph[0][i + 1] = Integer.parseInt(lineSplit[i]);
                }
                counter++;
            } else {
                for (int i = 1; i < lineSplit.length; i += 2) {
                    int neighbor;
                    if (lineSplit[i].equals("KL")) {
                        neighbor = numberOfNodes - 1;
                    } else {
                        neighbor = Integer.parseInt(lineSplit[i].substring(1)) + 7;
                    }
                    int weight = Integer.parseInt(lineSplit[i + 1]);
                    graph[counter][neighbor] = weight;
                }
                counter++;
            }
        }

        MaxFlow m = new MaxFlow();
        m.setNumberOfNodes(numberOfNodes);
        System.out.println(m.fordFulkerson(graph, 0, numberOfNodes - 1));
        writer.write(m.fordFulkerson(graph, 0, numberOfNodes - 1) + "\n");
        minCut(graph, 0, numberOfNodes - 1, numberOfNodes, writer);
    }

    private static void minCut(int[][] graph, int s, int t, int numberofnodes, BufferedWriter writer) throws IOException {
        int u,v;

        int[][] rGraph = new int[graph.length][graph.length];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                rGraph[i][j] = graph[i][j];
            }
        }

        int[] parent = new int[graph.length];

        while (bfs(rGraph, s, t, parent)) {
            int pathFlow = Integer.MAX_VALUE;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                pathFlow = Math.min(pathFlow, rGraph[u][v]);
            }

            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                rGraph[u][v] = rGraph[u][v] - pathFlow;
                rGraph[v][u] = rGraph[v][u] + pathFlow;
            }
        }

        boolean[] isVisited = new boolean[graph.length];
        dfs(rGraph, s, isVisited);

        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                if (graph[i][j] > 0 && isVisited[i] && !isVisited[j]) {
                    if(i == 0){
                        System.out.println("r" + (j-1));
                        writer.write("r" + (j-1) + "\n");
                    }
                    else if(i < 7){
                        if(j != numberofnodes - 1) {
                            System.out.println("r" + (i - 1) + " c" + (j-7));
                            writer.write("r" + (i - 1) + " c" + (j-7) + "\n");
                        }
                        else{
                            System.out.println("r" + (i - 1) + " KL");
                            writer.write("r" + (i - 1) + " KL" + "\n");
                        }
                    }
                    else{
                        if(j != numberofnodes - 1) {
                            System.out.println("c" + (i - 7) + " c" + (j-7));
                            writer.write("c" + (i - 7) + " c" + (j-7) + "\n");
                        }
                        else{
                            System.out.println("c" + (i - 7) + " KL");
                            writer.write("c" + (i - 7) + " KL" + "\n");
                        }
                    }
                }
            }
        }
        writer.close();
    }

    private static boolean bfs(int[][] rGraph, int s, int t, int[] parent) {

        boolean[] visited = new boolean[rGraph.length];

        Queue<Integer> q = new LinkedList<Integer>();
        q.add(s);
        visited[s] = true;
        parent[s] = -1;

        while (!q.isEmpty()) {
            int v = q.poll();
            for (int i = 0; i < rGraph.length; i++) {
                if (rGraph[v][i] > 0 && !visited[i]) {
                    q.offer(i);
                    visited[i] = true;
                    parent[i] = v;
                }
            }
        }
        return (visited[t]);
    }

    private static void dfs(int[][] rGraph, int s, boolean[] visited) {
        visited[s] = true;
        for (int i = 0; i < rGraph.length; i++) {
            if (rGraph[s][i] > 0 && !visited[i]) {
                dfs(rGraph, i, visited);
            }
        }
    }
}