public class PQHeap {

    Node[] data;
    int numElts = 0;


    // resizes the underlying array by doubling the number
    // of elements that can be stored
    private void resize() {
	Node[] temp = new Node[numElts * 2 + 1];

	for(int i = 1; i <= numElts; i++) {
	    temp[i] = data[i];
	}
	data = temp;
	//inspect();
    }

    private boolean isValid(int index) {
	return (1 <= index) && (index <= numElts);
    }
    
    private void swap(int indexA, int indexB) {
	Node buffer = data[indexA];
	data[indexA] = data[indexB];
	data[indexB] = buffer;
    }

    private void siftUp(int index) {
	int smallestIndex = index;
	int parent = (int) Math.floor((double) index / 2);
	/*
	int leftChild = 2 * i;
	int rightChild = 2 * i + 1;
	
	 
	if (isValid(leftChild) && data[leftChild] < data[smallestIndex]) {
	    smallestIndex = leftChild;
	}
	if (isValid(rightChild) && data[rightChild] < data[smallestIndex]) {
	    smallestIndex = rightChild;
	}
	*/
	if (isValid(parent) && (data[smallestIndex].count < data[parent].count)) {
       	    swap(smallestIndex, parent);
	    siftUp(parent);
	}
    }



    public void add(Node toAdd) {
	int currentCapacity = data.length - 1;
	
	if (numElts + 1 > currentCapacity) {
	    resize();
	    currentCapacity = data.length - 1;
	}
	
	numElts++;
	data[numElts] = toAdd;
	siftUp(numElts);
    }

    private void siftDown(int index) {
	int smallestIndex = index;
	int leftChild = 2 * index;
	int rightChild = 2 * index + 1;
	
	 
	if (isValid(leftChild) && data[leftChild].count < data[smallestIndex].count) {
	    smallestIndex = leftChild;
	}
	if (isValid(rightChild) && data[rightChild].count < data[smallestIndex].count) {
	    smallestIndex = rightChild;
	}
       
	if (smallestIndex != index) {
       	    swap(smallestIndex, index);
	    siftDown(smallestIndex);
	}
    }

    public Node remove() {
	swap(numElts, 1);
	Node removed = data[numElts];
	data[numElts] = null;
	numElts--;
	siftDown(1);

	return removed;
    }

    public int size() {
	return numElts;
    }

    public boolean isEmpty() { 
	return (numElts == 0);
    }

    private void inspect() {
	for (int i = 1; i <= size(); i ++) {
	    System.out.print(data[i] + " ");
	}
	System.out.println("\nsize = " + size() + " " + "length = " + data.length);
    }

    public PQHeap() {
	data = new Node[2];
    }

} 
