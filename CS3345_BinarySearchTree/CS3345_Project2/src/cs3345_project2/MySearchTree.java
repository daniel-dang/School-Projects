package cs3345_project2;

import static java.lang.Math.pow;

//MySearchTree class: generic class
public class MySearchTree <T> {
    private Node<T> root;               //Root node
    
    //Constructor
    public MySearchTree(){
        root = null;
    }
    
    //Class Node
    class Node <T>{
        private T content;              //Content attribute can be any type
        private int key;                //Key in int type
        
        Node <T> right_child;           //Right child node
        Node <T> left_child;            //Left child node
        
        //constructor
        public Node(int key, T content){
            this.key = key;
            this.content = content;
        }
    }
    
    //Most of the method below have a helper method that send the root node 
    //in the helper method.
    //The reason for this is to make the program simple and the user doesn't need
    //to send the root in the method from the main method.
    
    //Insert method
    public void insert(int key, T content){
        root = insert(key, content, root);      
    }
    //Insert helper method
    protected Node insert(int key, T content, Node<T> root){        
        Node temp = root;                       //make another node that reference the root
        
        //If the root is null, create a new node and point right and left child to null
        if (temp == null){
            temp = new Node<T>(key, content);
            temp.right_child = null;
            temp.left_child = null;
        }
        //if tree is not empty, recursively add node
        else{
            //if key of new content is bigger than current node
            //go to the right
            if (key < temp.key){
                temp.left_child = insert(key, content, temp.left_child);
            }
            //else if key of new content is less than current node
            //go to the left
            else{
                temp.right_child = insert(key, content, temp.right_child);
            }
        }
        return temp;
    }
    
    //Find method
    public boolean find(int key){
        Node temp = find(key, root);        //Send root node t helper method
        //If the key the user trying to find match with the node return from
        //the helper method, return true, else return false
        if (temp.key == key)
            return true;
        else
            return false;
    }
    //Find helper method
    protected Node find(int key, Node<T> root){
        Node temp = root;           //reference the root node
        //if the right child and left child is null, meaning that is the leafs node
        //then return that node. This will result in a false result since the tree
        //cannot find the node and have travel to the leafs of everynode 
        if (temp.right_child == null || temp.left_child == null)
            return temp;
        else if( temp.key == key)   //if key is equal to the node's key, return that node
            return temp;
        else if (key < temp.key){   //if key is less than the current node key, go left
            return find(key, temp.left_child);
        }
        else{                       //else go right
            return find(key, temp.right_child);
        }          
    }
    
    //leaf count method
    public int leafCount(){
        return leafCount(root);
    }
    //leaf count helper method
    protected int leafCount(Node<T> root){
        Node temp = root;       //refernce the root
        //if node is null, return 0
        if (temp == null){
            return 0;
        }
        //if the current node left and right child is null then count as one because 
        //this is the leaf
        if (temp.left_child == null && temp.right_child == null){
            return 1;
        }
        //otherwise return when traverse to the right and the left of the tree
        else{
            return leafCount(temp.left_child) + leafCount(temp.right_child);
        }
    }
    //parent count method
    public int parentCount(){
        return parentCount(root, 0);
    }
    //parent count helper method
    protected int parentCount(Node<T> root, int count){
        int parent = count;     //reference the root
        //if the root is null then return 0
        if (root == null)
            return 0;
        //if the root left and right child is null return 0, because 
        //this is the leafs not the parents
        if (root.left_child == null && root.right_child == null){
            return 0;
        }
        //otherwise, traven to the right and the left of the tree and add 1
        //since if the program will execute these line below, it mean that it is not
        //the leafs
        else{
            parent = 1 + parentCount(root.left_child, count) + parentCount(root.right_child, count);
        }
        
        return parent;  //return the count of parents at the end
    }
    
    //inorder traverse
    public void inOrderTraverse(Node targetNode){
        //In this if statement, as long as the node is not null
        //travel to the left child, print the most left node.
        //then travel to the right child and print 
        if (targetNode != null){
            inOrderTraverse(targetNode.left_child);
            System.out.print("[" + targetNode.key + ":" + targetNode.content + "] ");
            inOrderTraverse(targetNode.right_child);
        }
    }
    
    //preorder traverse
    public void preOrderTraverse(Node targetNode){
        //In this if statement, as long as the node is not null
        //print the parent node, then travel to the left and right of tree and
        //print all the node.
        if (targetNode != null){
            System.out.print("[" + targetNode.key + ":" + targetNode.content + "] ");
            preOrderTraverse(targetNode.left_child);
            preOrderTraverse(targetNode.right_child);
        }
    }
    
    //Ancestor method
    public void ancestor(int key){
        ancestor(key, root);
        System.out.println(this.root.key);  //This print the last node which is
                                            //the root of the tree
    }
    //Ancestor helper method
    protected void ancestor(int key, Node<T> root){
        //if the root's key is the key that we looking for,
        //print out the root's key.
        //This is a recursive method which mean the last node that we're looking for
        //will always be the root is it recursively passed in the next parent call after call.
        if (root.key == key){
            System.out.print("Ancestor of " + key + " is:");
        }
        //if the node's key is less than key we looking for, go left
        else if(key < root.key){
            ancestor(key, root.left_child);
            System.out.print(" " + root.left_child.key + " ");
        }
        //else go right
        else{
            ancestor(key, root.right_child);
            System.out.print(" " + root.right_child.key + " ");
        }
    }
    
    //height count method
    public int height(){
        return height(root);
    }
    //height count helper method
    protected int height(Node<T> root){
        //if the root is null return -1
        //the reason for this is because the depth of three is N-1
        //therefor the leafs nodes will not count as one level
        if (root == null){
            return -1;
        }
        //travel to the left and to the right
        int heightL = height(root.left_child);
        int heightR = height(root.right_child);
        //if the height on the left side is bigger than the right, then we count 1
        if (heightL > heightR){
            return heightL + 1;
        }
        //else count right one.
        else{
            return heightR + 1;
        }
    }
    
    //perfect method
    public boolean perfect(MySearchTree tree){
        //In binary tree, a perfect tree will have formula of leafs = 2^height
        //For example, if we have height of 3, then that mean the leafs will be 8,
        //2 child for each parent.
        int height = tree.height();     //call method height to get height of tree
        int leafs = tree.leafCount();   //call method leaf to get leaf of tree
        
        //if the leaf is equal to 2^height then the tree is perfect, else if false
        if (leafs == (int)pow(2.0, (double)height))
            return true;
        else 
            return false;
    }
    
    //main method demonstrate the methods in the program
    public static void main(String[] args){
        //make a tree
        MySearchTree<String> testTree = new MySearchTree<>();
        
        //insert node
        testTree.insert(40, "Biology");
        testTree.insert(20, "Economic");
        testTree.insert(60, "Pharmacology");
        testTree.insert(10, "English");
        testTree.insert(30, "PharmTech");
        testTree.insert(50, "MedMicro");
        testTree.insert(70, "Calculus");
        testTree.insert(5, "Math");
        testTree.insert(15, "History");
        testTree.insert(25, "Government");
        testTree.insert(35, "Literature");
        testTree.insert(45, "C++");
        testTree.insert(55, "Java");
        testTree.insert(65, "Discrete Math");
        testTree.insert(75, "Data Structure");
        
        //Print tree inorder traversal
        System.out.println("Inorder traverse: ");
        testTree.inOrderTraverse(testTree.root);
        System.out.println("\n");
        //Print tree preorder traversal
        System.out.println("Preorder traverse: ");
        testTree.preOrderTraverse(testTree.root);
        System.out.println();
        
        //Find a key that is in the list: found = true
        System.out.println("\nFound: " + testTree.find(50));
        //Find a key that is not in the list: found = false;
        System.out.println("Found: " + testTree.find(31));
        
        //Count leafs
        System.out.println("Leaf counts: " + testTree.leafCount());
        
        //Count parents
        System.out.println("Parent counts: " + testTree.parentCount());
        
        //print ancestor of key
        testTree.ancestor(5);
        
        //print tree height
        System.out.println("Tree height: " + testTree.height());
        
        //is the tree perfect?
        System.out.println("Is this tree perfect?: " + testTree.perfect(testTree));
    }
}