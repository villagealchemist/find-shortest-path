package userInterface;

import graph.Dijkstra;
import graph.Graph;

/** The Main ("driver") class for project Dijkstra. Run this class to run the project.  */
public class Main {
    public static void main(String[] args) {
            // Initialize a graph
            Graph graph = new Graph();

            // Create an instance of the Dijkstra class
            Dijkstra dijkstra = new Dijkstra("input/USA.txt", graph);

            // Create a graphical user interface and wait for user to click on two cities:
            GUIApp app = new GUIApp(dijkstra, graph);
            // GUIApp would call Dijkstra's shortestPath method
    }
}
