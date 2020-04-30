package priorityQueue;

/** An element of the priority queue. Stores node id and priority.
 *  Fill in the code in the compareTo method to compare elements by priority.
 */
public class PriorityQueueElement implements Comparable<PriorityQueueElement>{
    private int nodeId;
    private int priority;

    /** Constructor */
    public PriorityQueueElement(int nodeId, int priority) {
        this.nodeId = nodeId;
        this.priority = priority;
    }

    public int getId() {
        return nodeId;
    }

    public void setPriority(int newPriority) {
        this.priority = newPriority;
    }

    @Override
    public int compareTo(PriorityQueueElement o) {
        // FILL IN CODE - compare elements by priority

        return -1; // change
    }
}