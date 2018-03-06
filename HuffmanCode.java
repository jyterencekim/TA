/* Answer the questions in the assignment here
//
// (a)
//
//
//
// (b)
//
//
//
*/

import java.io.*;

public class HuffmanCode {

    public void run() throws IOException {
	int[] frequencies = getFrequencies("amodestproposal_swift.txt");
	//int[] frequencies = getFrequencies("uglyduckling_andersen.txt");

	Node tree = makeTree(frequencies);
	String [] codes = new String[256];
	createCodes(codes, tree);

	String message = readMessage("codedmessage1.txt");
	String result = decode(message, tree);
	System.out.println(result);

	//System.out.println(tester("amodestproposal_swift.txt"));

    }
    private String tester(String filename) throws IOException {
	int[] frequencies = getFrequencies(filename);
	Node tree = makeTree(frequencies);
	String [] codes = new String[256];
	createCodes(codes, tree);
	String encoded = encode(readMessage(filename), codes); 
	String result = decode(encoded, tree);
	return result;
    }

    private boolean isValidChar(char c) {
	//return (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z'));
	return Character.isLetter(c) || c == ' ' || c == '\n';
    }

    // input: the name of a text file
    // output: a length-127 array containing the number of times each 
    // character appears in the input file
    public int[] getFrequencies(String fileName) throws IOException {
	String fileContents = readMessage(fileName);
	int[] frequencies = new int[127];
	for (int i = 0; i < 127; i ++) {
	    frequencies[i] = 0;
	}

	for (int i = 0; i < fileContents.length(); i ++) {
	    char givenChar = fileContents.charAt(i);
	    //if ((int)givenChar < 127) {
	    if(isValidChar(givenChar)) {
		frequencies[(int)givenChar]++;
	    }
	}

	return frequencies;
    }

    private void traverse(Node root) {
	if (root == null) {
	    return;
	}
	System.out.print("[" + root.symbol + ", " + root.count + "] ");
	traverse(root.left);
	traverse(root.right);
    }
    // input: an array of character frequencies
    // output: a pointer to the root of a Huffman tree built using the 
    // specified frequencies
    public Node makeTree(int[] frequencies) {
	PQHeap q = new PQHeap();
	// 1. Create a new tree node for each letter and put it in the queue
	for (int i = 0; i < 127; i ++) {
	    Node newNode = new Node((char) i, frequencies[i]);
	    q.add(newNode);
	}

	while (q.size() > 1) { // not sure about this condition
	    Node nodeA = q.remove();
	    Node nodeB = q.remove();
	    Node internalNode = new Node('\0', nodeA.count + nodeB.count);
	    internalNode.left = nodeA;
	    internalNode.right = nodeB;
	    q.add(internalNode);
	}

	Node root = q.remove();
	//traverse(root); // for printing
	return root;
    }


    // input: codes: an empty array to be filled in with the binary encodings
    //               for each character
    //        tree: the Huffman tree from which to read the encodings
    // at the end of the method, codes should contain the binary encoding of
    // each character

    // ** HELPER NEEDED? **
    private void createCodesRecurse(String[] codes, Node root, String codeSoFar) {
	if (root == null) {
	    return;
	}
	//if (root.symbol != '\0' && (int) root.symbol < 127) {
	if(isValidChar(root.symbol) && root.left == null && root.right == null) {
	    codes[(int)root.symbol] = codeSoFar;
	}
	createCodesRecurse(codes, root.left, codeSoFar + "0");
	createCodesRecurse(codes, root.right, codeSoFar + "1");
    }

    public void createCodes(String[] codes, Node tree) {
	createCodesRecurse(codes, tree, "");
	for (String code : codes) {
	    //System.out.println(code);
	}
    }

 
    // input: message: a text string that is to be encoded
    //        code: an array of the binary encoding to use for each character
    // output: a binary string containing the encoded version of the message
    public String encode(String message, String[] code) {
	String result = "";

	for (int i = 0; i < message.length(); i ++) {
	    char c = message.charAt(i);
	    if (isValidChar(c)) {
		result = result + code[(int)c];
	    }
	}
	return result;
    }


    // input: message: a binary string that is to be decoded
    //        tree: the Huffman tree to use to decode the message
    // output: a String containing the text decoding of the message
    public String decode(String message, Node tree) {
	String result = "";
	Node pointer = tree;

	for (int i = 0; i < message.length(); i ++) {
	    char c = message.charAt(i);
	    if (c == '0') {
		pointer = pointer.left;
	    }
	    else {
		pointer = pointer.right;
	    }
	    if (pointer.left == null && pointer.right == null) {
		result += pointer.symbol;
		pointer = tree;
	    }
	}
	return result;
    }


    // input: the name of the file to be read
    // output: a String containing the contents of the file
    public String readMessage(String fileName) throws IOException {
	FileReader fr = new FileReader(fileName);
	String message = "";
	int nextChar = fr.read();
	while(nextChar != -1) {
	    message = message + (char)nextChar;
	    nextChar = fr.read();
	}
	return message;
    }


    public static void main(String [] args) {
	try {
	    new HuffmanCode().run();
	}
	catch(IOException e) {
	    e.printStackTrace();
	    System.exit(0);
	}
    }

}