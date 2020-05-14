package priorityQueue;

import graph.Graph;

import java.util.HashMap;
import java.util.Map;

/** A priority queue implemented using a min heap.
 *  You may not use any Java built-in classes, you should implement
 *  PriorityQueue yourself to get full credit for this part of the project.
 *  You may use/modify the MinHeap code posted by the instructor under codeExamples, as long as you understand it. */
public class PriorityQueue {
    private int[] heap; // the array to store the heap
    private int maxsize; // the size of the array
    private int size; // the current number of elements in the array
    private int[] positions;// creates new positions array with size of numNodes



    public PriorityQueue(int max) {
        maxsize = max + 1;
        heap = new int[maxsize];
        size = 0;
        heap[0] = Integer.MIN_VALUE;
        positions = new int[max];
        // Note: no actual data is stored at heap[0].
        // Assigned MIN_VALUE so that it's easier to bubble up
    }

    private int leftChild(int pos) {
        return 2 * pos;
    }

    private int rightChild(int pos) {
        return 2 * pos + 1;
    }

    private int parent(int pos) {
        return pos / 2;
    }

    private boolean isLeaf(int pos) {
        return ((pos > size / 2) && (pos <= size));
    }

    private void swap(int pos1, int pos2) {
        int tmp;
        tmp = heap[pos1];
        heap[pos1] = heap[pos2];
        heap[pos2] = tmp;
        int firstId = getNodeId(pos1);
        int secId = getNodeId(pos2);
        positions[firstId] = pos2;
        positions[secId] = pos1;
    }
    /** Insert a new element (nodeId, priority) into the heap.
     *  For this project, the priority is the current "distance"
     *  for this nodeId in Dikstra's algorithm. */
    public void insert(int nodeId, int priority) {
        size++;
        heap[size] = priority;
        positions[nodeId] = size;
        int current = size;
        while (heap[current] < heap[parent(current)]){
            swap(current, parent(current));
            current = parent(current);
            positions[nodeId] = current;
        }

    }

    public int removeMin() {
        swap(1, size); // swap the end of the heap into the root
        size--;  	   // removed the end of the heap
        int nodeId = -1;
        if (size != 0) {
            pushDown(1);
        }
        nodeId = getNodeId(size + 1);
        return nodeId;
    }

    public void reduceKey(int nodeId, int newPriority) {
        int heapPos = positions[nodeId];
        heap[heapPos] = newPriority;
        bubbleUp(nodeId);
    }

    public boolean isEmpty(){
        if (size == 0){
            return true;
        }
        return false;
    }

    private void bubbleUp(int nodeId){
        int position = positions[nodeId];
        int parent = parent(position);
        while(heap[parent] > heap[position]){
            swap(parent, position);
            position = positions[nodeId];
            parent = parent(position);
        }

    }

    private void pushDown(int position) {
        int smallestChild;
        while (!isLeaf(position)) {
            smallestChild = leftChild(position); // set the smallest child to left child
            if ((smallestChild < size) && (heap[smallestChild] > heap[smallestChild + 1])) {
                smallestChild = smallestChild + 1; // right child was smaller, so smallest child = right child
            }
            // the value of the smallest child is less than value of current,
            // the heap is already valid
            if (heap[position] <= heap[smallestChild]) {
                return;
            }
            swap(position, smallestChild);
            position = smallestChild;
        }
    }
    /*method to get nodeId from the positions array*/
    private int getNodeId(int heapPos){
        int nodeId = -1;
        for(int i = 0; i < positions.length; i++){
            if (positions[i] == heapPos){
                nodeId = i;
                break;
            }
        }
        return nodeId;
    }
}

