package graph;


/** A class that represents a graph where nodes are cities (of type CityNode).
 * The cost of each edge connecting two cities is the distance between the cities.
 * Fill in code in this class. You may add additional methods and variables.
 * You are required to implement a PriorityQueue from scratch to get full credit.
 */
import java.util.*;
import java.io.*;
import java.awt.Point;

public class Graph {
    public final int EPS_DIST = 5;

    private int numNodes = 0;     // total number of nodes
    private int numEdges = 0; // total number of edges
    private CityNode[] nodes; // array of nodes of the graph
    private Edge[] adjacencyList; // adjacency list; for each vertex stores a linked list of edges
    private Map<String, Integer> labelsToIndices = new HashMap<>(); // a HashMap that maps each city to the corresponding node id

    /**
     * Read graph info from the given file, and create nodes and edges of
     * the graph.
     *
     * @param filename name of the file that has nodes and edges
     */
    public void loadGraph(String filename) {
        try {
            FileReader file = new FileReader(filename);
            BufferedReader br = new BufferedReader(file);
            br.readLine();
            int totalNodes = Integer.parseInt(br.readLine());
            String line;
            nodes = new CityNode[totalNodes];
            while ((line = br.readLine()) != null && !line.equals("ARCS")) { //reads cities and coordinates
                String[] myLine = line.split(" ");
                String cityName = myLine[0];
                Double x = Double.parseDouble(myLine[1]);
                Double y = Double.parseDouble(myLine[2]);
                CityNode newNode = new CityNode(cityName, x, y);
                addNode(newNode);
            }
            adjacencyList = new Edge[numNodes];
            while ((line = br.readLine()) != null) { //reads edges and adds to adjacency list
                String[] myLine = line.split(" ");
                int cityId = labelsToIndices.get(myLine[0]);
                int destId = labelsToIndices.get(myLine[1]);
                int cost = Integer.parseInt(myLine[2]);
                Edge newEdge = new Edge(destId, cost, null);
                addEdge(cityId, newEdge);
            }
        }catch(IOException e){
                System.out.println("IO Exception!");
        }
    }

    /**
     * Add a node to the array of nodes.
     * Increment numNodes variable.
     * Called from loadGraph.
     *
     * @param node a CityNode to add to the graph
     */
    public void addNode(CityNode node) {
        labelsToIndices.put(node.getCity(), numNodes);
        nodes[numNodes] = node;
        numNodes++;
    }

    /**
     * Return the number of nodes in the graph
     * @return number of nodes
     */
    public int numNodes() {
        return numNodes;
    }
    public int numEdges(){
        return numEdges;
    }

    /**
     * Adds the edge to the linked list for the given nodeId
     * Called from loadGraph.
     *
     * @param nodeId id of the node
     * @param edge edge to add
     */
    public void addEdge(int nodeId, Edge edge) {
            int destId = edge.getNeighbor();//id of destination vertex
            Edge destEdge = new Edge(nodeId, edge.getCost(), null);

            Edge cur = adjacencyList[nodeId]; //place in array of start city
            Edge destCur = adjacencyList[destId]; //place in array of destination city
            if (cur == null){
                cur = edge;
                adjacencyList[nodeId] = cur;
            }else {
                while (cur.getNext() != null) {
                    if (cur.getNeighbor() == destId){
                        break;
                    }
                    cur = cur.getNext();
                }
                cur.setNext(edge);
            }
            if(destCur == null){
                destCur = destEdge;
                adjacencyList[destId] = destCur;
            }else{
                while (destCur.getNext() != null){
                    if(destCur.getNeighbor() == nodeId){
                        break;
                    }
                    destCur = destCur.getNext();
                }
                destCur.setNext(destEdge);
            }
            numEdges += 2;
        }

    /**
     * Returns an integer id of the given city node
     *
     * @param city node of the graph
     * @return its integer id
     */
    public int getId(CityNode city) {
        int cityIndex = labelsToIndices.get(city.getCity());
        return cityIndex;
    }

    /**
     * Return the edges of the graph as a 2D array of points.
     * Called from GUIApp to display the edges of the graph.
     *
     * @return a 2D array of Points.
     * For each edge, we store an array of two Points, v1 and v2.
     * v1 is the source vertex for this edge, v2 is the destination vertex.
     * This info can be obtained from the adjacency list
     */
    public Point[][] getEdges() {
        int i = 0;
        Point[][] edges2D = new Point[numEdges][2];

        for (int j = 0; j < adjacencyList.length; j++){
            if(adjacencyList[j] == null){
                continue;
            }else {
                Edge cur = adjacencyList[j];
                while (cur != null){
                    int destVertex = cur.getNeighbor();
                    Point v1 = nodes[j].getLocation();
                    Point v2 = nodes[destVertex].getLocation();
                   edges2D[i][0] = v1;
                   edges2D[i][1] = v2;
                   cur = cur.getNext();
                   i++;
                }
            }
        }
        return edges2D;
    }

    /**
     * Get the nodes of the graph as a 1D array of Points.
     * Used in GUIApp to display the nodes of the graph.
     * @return a list of Points that correspond to nodes of the graph.
     */
    public Point[] getNodes() {
        if (this.nodes == null) {
            System.out.println("Graph has no nodes. Write loadGraph method first. ");
            return null;
        }
        Point[] pnodes = new Point[this.nodes.length];
        for(int i = 0; i < nodes.length; i++){
            Point point = nodes[i].getLocation();
            pnodes[i] = point;
        }

        return pnodes;
    }

    /**
     * Used in GUIApp to display the names of the airports.
     * @return the list that contains the names of cities (that correspond
     * to the nodes of the graph)
     */
    public String[] getCities() {
        if (this.nodes == null) {
            System.out.println("Graph has no nodes. Write loadGraph method first. ");
            return null;
        }
        String[] labels = new String[nodes.length];
        for (int i = 0; i < nodes.length; i++){
            labels[i] = nodes[i].getCity();
        }


        return labels;
    }

    /** Take a list of node ids on the path and return an array where each
     * element contains two points (an edge between two consecutive nodes)
     * @param pathOfNodes A list of node ids on the path
     * @return array where each element is an array of 2 points
     */
    public Point[][] getPath(List<Integer> pathOfNodes) {
        int i = 0;
        Point[][] edges2D = new Point[pathOfNodes.size()-1][2];
        if(pathOfNodes == null){
            return null;
        }else{
            while (i < pathOfNodes.size() - 1){
                CityNode first = nodes[pathOfNodes.get(i)];
                CityNode second = nodes[pathOfNodes.get(i + 1)];
                Point p1 = first.getLocation();
                Point p2 = second.getLocation();
                edges2D[i][0] = p1;
                edges2D[i][1] = p2;
                i++;
            }
        }
        return edges2D;
    }

    /**
     * Return the CityNode for the given nodeId
     * @param nodeId id of the node
     * @return CityNode
     */
    public CityNode getNode(int nodeId) {
        return nodes[nodeId];
    }

    /**
     * Take the location of the mouse click as a parameter, and return the node
     * of the graph at this location. Needed in GUIApp class. No need to modify.
     * @param loc the location of the mouse click
     * @return reference to the corresponding CityNode
     */
    public CityNode getNode(Point loc) {
        if (nodes == null) {
            System.out.println("No node at this location. ");
            return null;
        }
        for (CityNode v : nodes) {
            Point p = v.getLocation();
            if ((Math.abs(loc.x - p.x) < EPS_DIST) && (Math.abs(loc.y - p.y) < EPS_DIST))
                return v;
        }
        return null;
    }

    public Edge getAdjList(int nodeId){
        return adjacencyList[nodeId];
    }

}
