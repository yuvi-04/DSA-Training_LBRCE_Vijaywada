package lbrce;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class GraphAlgorithm {
    //Q1: Network Delay Time
    //leetcode 743
    public static int networkDelayTime(int[][] times, int n, int k) {
        List<List<int[]>> adj = new ArrayList<>();
        for(int i = 0; i<= n; i++)
            adj.add(new ArrayList<>());
        for(int[] edge : times) {
            adj.get(edge[0]).add(new int[]{edge[1], edge[2]});
        }

        //Min heap based on path cost(Distance, Node)
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        minHeap.offer(new int[]{0,k});

        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;

        while (!minHeap.isEmpty()) {
            int[] curr = minHeap.poll();
            int currentDist = curr[0];
            int u = curr[1];

            if(currentDist > dist[u]) continue;
            for(int[] neighbour : adj.get(u)) {
                int v = neighbour[0];
                int weight = neighbour[1];
                if(dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    minHeap.offer(new int[]{dist[v], v});
                }
            }
        }
        int maxTime = 0;
        for (int i = 1; i <= n; i++) {
            if(dist[i] == Integer.MAX_VALUE) return -1;
            maxTime = Math.max(maxTime, dist[i]);
        }
        return maxTime;
    }

    //Q2:  Cheapest Flights Within K Stops: (Bellman-Ford variant)
    //leecode 787
    public static int findCheapFlight(int n, int[][] flights, int src, int dest, int k) {
        List<List<int[]>> adj = new ArrayList<>();
        for(int i = 0; i<n; i++) adj.add(new ArrayList<>());
        for (int[] f : flights) {
            adj.get(f[0]).add(new int[]{f[1], f[2]});
        }

        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{src,0});

        //Track minimum stops taken to reach a node
        int[] stopMap = new int[n];
        Arrays.fill(stopMap, Integer.MAX_VALUE);
        stopMap[src] = 0;

        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        int stops = 0;
        while (!q.isEmpty() && stops <= k) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int[] curr = q.poll();
                int u = curr[0];
                int cost = curr[1];

                for (int[] neighbour : adj.get(u)) {
                    int v = neighbour[0];
                    int weight = neighbour[1];

                    //check if it is cheaper OR taking less stops
                    if(cost + weight < dist[v] || stops < stopMap[v]) {
                        dist[v] = Math.min(dist[v], cost+weight);
                        stopMap[v] = stops;
                        q.offer(new int[]{v, cost+weight});
                    }
                }
            }
            stops++;
        }
        return dist[dest] == Integer.MAX_VALUE ? -1 : dist[dest];
    }

    //Q3: Minimam Spanning Tree (Kruskal)
    //leetcode 1584, 1135
    static class UnionFind {
        int[] parent, rank;
        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for(int i = 0; i<n; i++) parent[i] = i;
        }
        public int find(int i) {
            if(parent[i] == i) return i;
            return parent[i] = find(parent[i]);
        }
        public boolean union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);
            if (rootI == rootJ) return false;
            if(rank[rootI] < rank[rootJ])
                parent[rootI] = rootJ;
            else if(rank[rootI] > rank[rootJ])
                parent[rootJ] = rootI;
            else {
                parent[rootJ] = rootI;
                rank[rootI]++;
            }
            return true;
        }
    }
    public static int kruskalMST(int[][] edges, int numVertices) {
        Arrays.sort(edges, Comparator.comparingInt(a->a[2]));
        UnionFind uf = new UnionFind(numVertices);
        int totalCost = 0;
        int edgesIncluded = 0;

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int weight = edge[2];

            if(uf.union(u, v)) {
                totalCost += weight;
                edgesIncluded++;
                System.out.println(u + " -> " + v + " Weight: " + weight);

                if(edgesIncluded == numVertices - 1)
                    break;
            } else {
                System.out.println("Rejected Edge: " + u + " -> " + v + " Weight: " + weight);
            }
        }
        return totalCost;
    }
    //Edges are created in the function only
    //the distance between the nodes is calculated dynamically
    // public static int kruskalMST(int[][] points) {
    //     int n = points.length;
    //     List<int[]> edges = new ArrayList<>();
    //     for (int i = 0; i < n; i++) {
    //         for (int j = i+1; j < n; j++) {
    //             int dist = Math.abs(points[i][0]-points[j][0]) + Math.abs(points[i][1]-points[j][1]);
    //             edges.add(new int[]{dist, i, j});
    //         }
    //     }
    //     edges.sort(Comparator.comparingInt(a -> a[0]));

    //     UnionFind uf = new UnionFind(n);
    //     int cost = 0, edgeUsed = 0;
    //     for (int[] edge : edges) {
    //         if(uf.union(edge[1], edge[2])) {
    //             cost += edge[0];
    //             if(++edgeUsed == n-1) break;
    //         }
    //     }
    //     return cost;
    // }

    //Q4: Prim's Algorithm
    //leetcode 1584
    static class NodePair {
        int neighbour, weight;
        public NodePair(int neighbour, int weight) {
            this.neighbour = neighbour;
            this.weight = weight;
        }
    }
    public static int primsMST(int[][] edges, int numVertices) {
        List<List<NodePair>> adj = new ArrayList<>();
        for(int i = 0; i<numVertices; i++)
            adj.add(new ArrayList<>());
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int weight = edge[2];
            adj.get(u).add(new NodePair(v, weight));
            adj.get(v).add(new NodePair(u, weight));
        }

        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        boolean[] visited = new boolean[numVertices];
        int totalCost = 0, edgeIncluded = 0;
        //weight 0 with node 0
        minHeap.offer(new int[]{0,0});

        while (!minHeap.isEmpty() && edgeIncluded < numVertices) {
            int[] curr = minHeap.poll();
            int weight = curr[0];
            int currNode = curr[1];

            //if the node is already part of MST
            //Ignore it as it avoids cycle
            if(visited[currNode]) continue;

            //include it into MST
            visited[currNode] = true;
            totalCost += weight;
            edgeIncluded++;

            if(currNode != 0)
                System.out.println("Added Node: " + currNode + " weight: " + weight);
            else
                System.out.println("Root Node: 0");

            //Add all unvisited neighbours of the current node to Min Heap
            for (NodePair edgePair : adj.get(currNode)) {
                if(!visited[edgePair.neighbour])
                    minHeap.offer(new int[]{edgePair.weight, edgePair.neighbour});
            }
        }
        return totalCost;
    }
    public static void main(String[] args) {
        //Q1
        // int[][] times = {{2,1,1}, {2,3,1}, {3,4,1}};
        // System.out.println(networkDelayTime(
        //     times, 4, 2));

        //Q2
        // int[][] flights = {{0,1,100},{1,2,100},{0,2,500}};
        // System.out.println(findCheapFlight(
        //     3, flights, 0, 2, 1));

        //Q3
        // int [][] graph1 = {
        //     {0, 1, 4},{0, 7, 8},{1, 2, 8},{1, 7, 11},
        //     {2, 3, 7},{2, 8, 2},{2, 5, 4},{3, 4, 9},
        //     {3, 5, 14},{4, 5, 10},{5, 6, 2},{6, 7, 1},
        //     {6, 8, 6},{7, 8, 7}
        // };
        // System.out.println(kruskalMST(graph1, 9));

        //Q4
        int [][] graph2 = {
            {0, 1, 4},{0, 7, 8},{1, 2, 8},{1, 7, 11},
            {2, 3, 7},{2, 8, 2},{2, 5, 4},{3, 4, 9},
            {3, 5, 14},{4, 5, 10},{5, 6, 2},{6, 7, 1},
            {6, 8, 6},{7, 8, 7}
        };
        System.out.println(primsMST(graph2, 9));
    }
}
