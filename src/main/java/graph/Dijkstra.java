package graph;


/** Class Dijkstra. Implementation of Dijkstra's algorithm for finding the shortest path
 * between the source vertex and other vertices in the graph.
 *  Fill in code. You may add additional helper methods or classes.
 */

import priorityQueue.PriorityQueue;

import java.util.*;
import java.awt.Point;

public class Dijkstra {
    private Graph graph; // stores the graph of CityNode-s and edges connecting them
    private List<Integer> shortestPath; // nodes that are part of the shortest path
    priorityQueue.PriorityQueue queue;


    /** Constructor
     *
     * @param filename name of the file that contains info about nodes and edges
     * @param graph graph
     */
    public Dijkstra(String filename, Graph graph) {
        this.graph = graph;
        graph.loadGraph(filename);
    }


    /**
     * Returns the shortest path between the origin vertex and the destination vertex.
     * The result is stored in shortestPathEdges.
     * This function is called from GUIApp, when the user clicks on two cities.
     * @param origin source node
     * @param destination destination node
     * @return the ArrayList of nodeIds (of nodes on the shortest path)
     */
    public List<Integer> computeShortestPath(CityNode origin, CityNode destination) {
        shortestPath = new LinkedList<Integer>();
        queue = new PriorityQueue(graph.numNodes());
        int INFINITY = (int)Double.POSITIVE_INFINITY;
        boolean[] found = new boolean[graph.numNodes()];
        int sourceIndex = graph.getId(origin);

        int[][] dTable = new int[graph.numNodes()][2]; //[i][0] = current path cost [i][1] = source vertex id

        int cost = 0;
        int sourceVer = 1; //these are to make it easier to recall which index is which for the dtable

        /*create Dijkstra's table*/
        for(int i = 0; i < graph.numNodes(); i++){
            dTable[i][sourceVer] = -1;
            if (i == sourceIndex) { //initializes origin to have itself as source, and it's found to be true
                //dTable[i][sourceIndex] = graph.getId(origin);
                dTable[i][cost] = 0;
            }else{ //initializes all other vertices to have infinity and -1
                dTable[i][cost] = INFINITY;
            }
            queue.insert(i, dTable[i][cost]);
        }

        while (!queue.isEmpty()) {
            int sVertex = queue.removeMin();
            Edge adjList = graph.getAdjList(sVertex);
            Edge current = adjList;
            while (current != null) {
                int destId = current.getNeighbor();
                int total = dTable[sVertex][cost] + current.getCost();
                if (total < dTable[destId][cost]) {
                    dTable[destId][cost] = total;
                    dTable[destId][sourceVer] = sVertex;
                    queue.reduceKey(destId, total);
                }
                current = current.getNext();
            }
        }

        int k = graph.getId(destination);
        while(k != graph.getId(origin)){
            shortestPath.add(0, k);
            k = dTable[k][sourceVer];
        }
        shortestPath.add(0, k); //adds origin
        return shortestPath;
    }




    /**
     * Return the shortest path as a 2D array of Points.
     * Each element in the array is another array that has 2 Points:
     * these two points define the beginning and end of a line segment.
     * @return 2D array of points
     */
    public Point[][] getPath() {
        if (shortestPath == null)
            return null;
        return graph.getPath(shortestPath); // delegating this task to the Graph class
    }

    /** Set the shortestPath to null.
     *  Called when the user presses Reset button.
     */
    public void resetPath() {
        shortestPath = null;
    }

}