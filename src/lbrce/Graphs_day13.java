package lbrce;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

public class Graphs_day13 {
    //Q1: Shortest Path using BFS
    //leetcode 1091, 127
    public static List<Integer> findShortestPath(int vertices, List<List<Integer>> adjList, int source, int destination) {
        int[] parent = new int[vertices];
        Arrays.fill(parent, -1);
        boolean[] visited = new boolean[vertices];

        Deque<Integer> queue = new ArrayDeque<>();
        visited[source] = true;
        queue.offer(source);
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            if(curr == destination) break;

            for(int neighbor : adjList.get(curr)) {
                if(!visited[neighbor]) {
                    visited[neighbor] = true;
                    parent[neighbor] = curr;
                    queue.offer(neighbor);
                }
            }
        }
        List<Integer> path = new ArrayList<>();
        if(!visited[destination]) return path;

        for(int at = destination; at != -1; at=parent[at]) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    //Q2: Number of Islands
    //Leetcode 200
    public static int numIsland(char[][] grid) {
        if(grid == null || grid.length == 0) return 0;
        int islandCount = 0;
        
        for(int r = 0; r < grid.length; r++) {
            for(int c = 0; c < grid[0].length; c++) {
                if(grid[r][c] == '1') {
                    islandCount++;
                    //Identify all the connected 1's
                    sinkIsland(grid, r, c);   
                }
            }
        }
        return islandCount;
    }
    private static void sinkIsland(char[][] grid, int r, int c) {
        if(r < 0 || c < 0 || r >= grid.length || c >= grid[0].length || grid[r][c] == '0')
            return;
        //Saves O(M+N) space instead of taking a visited array
        grid[r][c] = '0';

        //explore all 4 direction
        sinkIsland(grid, r + 1, c); //Down
        sinkIsland(grid, r - 1, c); //Up
        sinkIsland(grid, r, c + 1); //Right
        sinkIsland(grid, r, c - 1); //Left
    }

    //Q3: size of the largest island (connected component of 1s)
    //leetcode 695
    public static int maxAreaIsland(char[][] grid) {
        if(grid == null || grid.length == 0) return 0;
        int maxArea = 0;

        for(int r = 0; r < grid.length; r++) {
            for(int c = 0; c < grid[0].length; c++) {
                if(grid[r][c] == '1') {
                   maxArea = Math.max(maxArea,
                    calculateArea(grid, r, c));   
                }
            }
        }
        return maxArea;
    }
    private static int calculateArea(char[][] grid, int r, int c) {
        if(r < 0 || c < 0 || r >= grid.length || c >= grid[0].length || grid[r][c] == '0')
            return 0;
        //Saves O(M+N) space instead of taking a visited array
        grid[r][c] = '0';

        //explore all 4 direction
        return 1 + calculateArea(grid, r + 1, c) //Down
            + calculateArea(grid, r - 1, c) //Up
            + calculateArea(grid, r, c + 1) //Right
            + calculateArea(grid, r, c - 1); //Left
    }

    //Q4: Detect a cycle in an undirected graph using BFS
    //leetcode 261
    public static boolean hasCycleUndirected (int vertices, List<List<Integer>> adjList) {
        boolean[] visited = new boolean[vertices];
        //loop handle all the disconnected componenets
        for(int i = 0; i<vertices; i++) {
            if(!visited[i]) {
                if(bfsCycleCheck(i, adjList, visited))
                    return true;
            }
        }
        return false;
    }
    private static boolean bfsCycleCheck(int start, List<List<Integer>> adjList, boolean[] visited) {
       //Use two parallel queues
       Queue<Integer> nodeQueue = new ArrayDeque<>();
       Queue<Integer> parentQueue = new ArrayDeque<>();
       visited[start] = true;
       nodeQueue.offer(start);
       parentQueue.offer(-1);

        while (!nodeQueue.isEmpty()) {
            int currNode = nodeQueue.poll();
            int currParent = parentQueue.poll();

            for(int neighbour : adjList.get(currNode)) {
                if(!visited[neighbour]) {
                    visited[neighbour] = true;
                    //push neighbour and its parent
                    nodeQueue.offer(neighbour);
                    parentQueue.offer(currNode);
                } else if(neighbour != currParent)
                    return true;
            }
        }
        return false;
    }

    //Q5: Detect a cycle in a directed graph using DFS
    //leetcode 207
    public static boolean hasCycleDirected(int vertices, List<List<Integer>> adjList) {
        boolean[] visited = new boolean[vertices];
        //tracks you current recursive path that we are taking
        boolean[] pathVisited = new boolean[vertices];

        for(int i = 0; i < vertices; i++) {
            if(!visited[i]) {
                if(dfsCycleCheck(i, adjList, visited, pathVisited))
                    return true;
            }
        }
        return false;
    }
    private static boolean dfsCycleCheck(int node, List<List<Integer>> adjList, boolean[] visited, boolean[] pathVisited) {
        visited[node] = true;
        //push current node onto the active stack
        pathVisited[node] = true;

        for(int neighbour : adjList.get(node)) {
            if(!visited[neighbour]) {
                if(dfsCycleCheck(neighbour, adjList, visited, pathVisited))
                    return true;
            }
            else if (pathVisited[neighbour]) return true;
        }
        //Backtracking step: pop the node from the path stack
        pathVisited[node] = false;
        return false;
    }

    //Q6: number of connected components in an undirected graph.
    //leetcode 323
    public static int countComponents(int vertices, List<List<Integer>> adjList) {
        boolean[] visited = new boolean[vertices];
        int count = 0;

        for(int i = 0; i<vertices; i++) {
            if (!visited[i]) {
                count++;
                bfsComponentTraversal(i, adjList, visited);
            }
        }
        return count;
    }
    private static void bfsComponentTraversal(int start, List<List<Integer>> adjList, boolean[] visited) {
        Queue<Integer> queue = new ArrayDeque<>();
        visited[start] = true;
        queue.offer(start);

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (int neighbour : adjList.get(curr)) {
                if (!visited[neighbour]) {
                    visited[neighbour] = true;
                    queue.offer(neighbour);
                }
            }
        }
    }

    public static void main(String[] args) {
        //Q1
        // List<List<Integer>> graph1 = buildAdjencyList(
        //     5, new int[][]{{0,1}, {0,2}, {1,3}, {3, 4}, {2,4}}, false);
        
        // System.out.println(findShortestPath(5, graph1, 0, 4));

        //Q2
        // char[][] grid = {
        //     {'1','1','0','0','0'},
        //     {'1','1','0','0','0'},
        //     {'0','0','1','0','0'},
        //     {'0','0','0','1','1'}};
        // System.out.println(numIsland(grid));

        //Q3
        // System.out.println(maxAreaIsland(grid));

        //Q4
        // List<List<Integer>> graph2 = buildAdjencyList(
        //     3, new int[][]{{0,1}, {1,2}, {2,0}}, false);
        // System.out.println(hasCycleUndirected(3, graph2));

        //Q5
        // List<List<Integer>> graph3 = buildAdjencyList(
        //     4,
        //     new int[][]{{0,1}, {1,2}, {2,3}, {3,1}},
        //     true);
        // System.out.println(hasCycleDirected(4, graph3));

        //Q6
        List<List<Integer>> graph4 = buildAdjencyList(
            5,
            new int[][]{{0,1}, {2,3}, {3,4}},
            false);
        System.out.println(countComponents(5, graph4));


    }

    private static List<List<Integer>> buildAdjencyList(int vertices, int[][] edges, boolean isDirected) {
        List<List<Integer>> adj = new ArrayList<>();
        for(int i = 0; i<vertices; i++)
            adj.add(new ArrayList<>());
        for(int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            if(!isDirected)
                adj.get(edge[1]).add(edge[0]);
        }
        return adj;
    }
}
