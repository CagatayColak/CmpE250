import java.util.LinkedList;

public class MaxFlow{
    private int numberOfNodes;

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public void setNumberOfNodes(int x) {
        numberOfNodes = x;
    }

    boolean bfs(int[][] residualGraph, int s, int t, int[] pastNode) {

        boolean[] flagged = new boolean[numberOfNodes];
        for (int i = 0; i < numberOfNodes; ++i)
            flagged[i] = false;

        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        flagged[s] = true;
        pastNode[s] = -1;

        while (queue.size() != 0) {
            int u = queue.poll();

            for (int v = 0; v < numberOfNodes; v++) {
                if (!flagged[v] && residualGraph[u][v] > 0) {
                    if (v == t) {
                        pastNode[v] = u;
                        return true;
                    }
                    queue.add(v);
                    pastNode[v] = u;
                    flagged[v] = true;
                }
            }
        }

        return false;
    }

    int fordFulkerson(int[][] graph, int s, int t)
    {
        int u, v;

        int[][] residualGraph = new int[numberOfNodes][numberOfNodes];

        for (u = 0; u < numberOfNodes; u++)
            for (v = 0; v < numberOfNodes; v++)
                residualGraph[u][v] = graph[u][v];

        int[] pastNode = new int[numberOfNodes];

        int maxFlow = 0;

        while (bfs(residualGraph, s, t, pastNode)) {
            int pathFlow = Integer.MAX_VALUE;
            for (v = t; v != s; v = pastNode[v]) {
                u = pastNode[v];
                pathFlow= Math.min(pathFlow, residualGraph[u][v]);
            }
            for (v = t; v != s; v = pastNode[v]) {
                u = pastNode[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }
}