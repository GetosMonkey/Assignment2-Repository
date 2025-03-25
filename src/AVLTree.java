/**
 * The AVLTree class implements a self-balancing AVL Tree to efficiently store 
 * and retrieve knowledge base entries. It supports insertion, searching, and 
 * traversal operations while maintaining balanced tree properties.
 *
 * Features:
 * - Balanced binary search tree structure.
 * - Insert nodes with a key, sentence, and confidence score.
 * - Search for nodes using a key or both key and sentence.
 * - Apply AVL rotations (left and right) to maintain tree balance.
 * - Supports in-order traversal and printing of the tree contents.
 *
 * Usage:
 * This class is used in GenericsKbAVLApp to store and manage knowledge base 
 * entries while ensuring optimal search efficiency.
 * 
 * Resource: https://www.youtube.com/watch?v=Jj9Mit24CWk
 * Credit : Geekific, AVL Trees
 * 
 * Previous Resource: https://www.youtube.com/watch?v=zIX3zQP0khM
 * Credit : Geekific, Binary Search Trees
 * 
 * @author Maryam Abrahams
 * @version 1.0
 * @since 2025-03-05
 */


public class AVLTree {

    public Node root; 

    //Constructor
    public AVLTree(){
        this.root = null; 
    }

    // If empty
    //@Override 
    public boolean isEmpty(){
        return root == null; 
    }

    // Traversal 

    public void traverse(){ 
        traverseInOrder(root); 
    }

    public void traverseInOrder(Node node){ 
        if (node != null){ 
            traverseInOrder(node.getLeftChild()); 
            traverseInOrder(node.getRightChild());
        }
    }

    // Recursive Search 

    public Node searchByKey(String key){
        return searchByKey(root, key); 
    }

    public Node searchByKey(Node node, String key){

        if (node == null || key.equals(node.key)) {
            return node; }

        if (key.compareTo(node.key) < 0) { return searchByKey(node.getLeftChild(), key); } 
        
        else {return searchByKey(node.getRightChild(), key); }
    }

        // key & sentence 

    public Node searchSentence(String key, String sentence){
        return searchSentence(root, key, sentence);
    }

    public Node searchSentence(Node node, String key, String sentence){
        
        if (node == null || (key.equals(node.key) && node.sentence.contains(sentence))) {return node;}

        if (key.compareTo(node.key) < 0) {return searchSentence(node.getLeftChild(), key, sentence); } 
        
        else {return searchSentence(node.getRightChild(), key, sentence);}
    } 
    
    //________________________________________________________________________________

    //Insertion updated to fit AVL Tree

    public void insert(String key, String sentence, double CS){
        root = insert(root, key, sentence, CS); 
    }

    public Node insert(Node node, String key, String sentence, double confidenceScore){

        if (node == null){
            return new Node(key, sentence, confidenceScore); 
        }
        if (key.compareTo(node.key) < 0){

            node.setLeftChild(insert(node.getLeftChild(), key, sentence, confidenceScore));

        } else if (key.compareTo(node.key) > 0){

            node.setRightChild(insert(node.getRightChild(), key, sentence, confidenceScore)); 
        
        } else {

            // If the Key already exists update is CS is higher than existing CS
            if (confidenceScore > node.confidenceScore){
                node.sentence = sentence; 
                node.confidenceScore = confidenceScore;
                System.out.println("Statement successfully added :D !!!"); 
                //prevents duplicates
            }

            return node;
        }

        updateHeight(node); 
        return applyRotation(node); 
         
    }

    // Helping methods

    public void updateHeight(Node node){

        int maxHeight = Math.max( 
            height(node.getLeftChild()), 
            height(node.getRightChild())); 

        node.setHeight( maxHeight + 1); 
    }
    
    public int height(Node node){ 
        return node != null ? node.getHeight() : 0; 
    }

    // Applying the rotation based of of 4 cases 

    public Node applyRotation ( Node node){

        int balance = balance(node); 

        // case 1: left-heavy
            if (balance > 1) {
                // case 3: left-right 
                if (balance(node.getLeftChild()) < 0){
                    node.setLeftChild(rotateLeft(node.getLeftChild()));
                }
                return rotateRight(node); 
            }
        // case 2: right-heavy
            if (balance < -1){
                // case 4: right-left
                if (balance(node.getRightChild()) > 0){
                    node.setRightChild(rotateRight(node.getRightChild()));
                }
                return rotateLeft(node); 
            }
        return node; 
    }

    public Node rotateRight(Node node){
        Node leftNode = node.getLeftChild(); 
        Node centerNode = leftNode.getRightChild(); 
        leftNode.setRightChild(node);
        node.setLeftChild(centerNode);
        updateHeight(node); 
        updateHeight (leftNode); 
        return leftNode; 
    }

    public Node rotateLeft(Node node){
        Node rightNode = node.getRightChild(); 
        Node centerNode = rightNode.getLeftChild(); 
        rightNode.setLeftChild(node);
        node.setRightChild(centerNode);
        updateHeight(node);
        updateHeight(rightNode); 
        return rightNode; 
    }

    public int balance (Node node){
        return node != null
                    ? height(node.getLeftChild())-height(node.getRightChild())
                    : 0; 
    }
    //________________________________________________________________________________

    // Recursive PrintTree method to test if my Tree is correctly updating:

    public void printTree() {
        System.out.println("Tree Contents (In-order traversal):");
        printTree(root);
        System.out.println();
    }
    
    private void printTree(Node node) {
        if (node != null) {
            // left subtree
            printTree(node.getLeftChild());
    
            // Print the current
            System.out.println("Key: " + node.key + ", Sentence: " + node.sentence + ", Confidence Score: " + node.confidenceScore);
    
            // right subtree
            printTree(node.getRightChild());
        }
    }

}
