package graph;

/** Edge class represents a link in the linked list of edges for a vertex.
 *  Each Edge stores the id of the "neighbor" (the vertex where this edge is going =
 *  "destination" vertex), the cost and the reference to the next Edge.
 */
class Edge {
    private int neighbor; // id of the neighbor ("destination" vertex of this edge)
    private int cost; // cost of this edge
    private Edge next; // reference to the next "edge" in the linked list

    public Edge(int neighbor, int cost, Edge next){
        this.cost = cost;
        this.neighbor = neighbor;
        this.next = next;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    public void setNeighbor(int neighbor){
        this.neighbor = neighbor;
    }
    public void setNext(Edge next){
        this.next = next;
    }


    public int getCost(){
        return cost;
    }
    public int getNeighbor(){
        return neighbor;
    }
    public Edge getNext() {
        return next;
    }
    // FILL IN CODE: constructor, getters, setters

}