package project20280.tree;

import project20280.interfaces.Position;

import java.util.ArrayList;

/**
 * Concrete implementation of a binary tree using a node-based, linked
 * structure.
 */
public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

    static java.util.Random rnd = new java.util.Random();
    /**
     * The root of the binary tree
     */
    protected Node<E> root = null; // root of the tree

    // LinkedBinaryTree instance variables
    /**
     * The number of nodes in the binary tree
     */
    private int size = 0; // number of nodes in the tree

    /**
     * Constructs an empty binary tree.
     */
    public LinkedBinaryTree() {
    } // constructs an empty binary tree

    // constructor

    public static LinkedBinaryTree<Integer> makeRandom(int n) {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<>();
        bt.root = randomTree(null, 1, n);
        return bt;
    }

    // nonpublic utility

    public static <T extends Integer> Node<T> randomTree(Node<T> parent, Integer first, Integer last) {
        if (first > last) return null;
        else {
            Integer treeSize = last - first + 1;
            Integer leftCount = rnd.nextInt(treeSize);
            Integer rightCount = treeSize - leftCount - 1;
            Node<T> root = new Node<T>((T) ((Integer) (first + leftCount)), parent, null, null);
            root.setLeft(randomTree(root, first, first + leftCount - 1));
            root.setRight(randomTree(root, first + leftCount + 1, last));
            return root;
        }
    }

    // accessor methods (not already implemented in AbstractBinaryTree)

    public static void main(String[] args) {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<Integer>();

        // Direct construction of Tree
        Position<Integer> root = bt.addRoot(12);
        Position<Integer> p1 = bt.addLeft(root, 25);
        Position<Integer> p2 = bt.addRight(root, 31);

        Position<Integer> p3 = bt.addLeft(p1, 58);
        bt.addRight(p1, 36);

        Position<Integer> p5 = bt.addLeft(p2, 42);
        bt.addRight(p2, 90);

        Position<Integer> p4 = bt.addLeft(p3, 62);
        bt.addRight(p3, 75);

        System.out.println("bt inorder: " + bt.size() + " " + bt.inorder());
        System.out.println("bt preorder: " + bt.size() + " " + bt.preorder());
        System.out.println("bt preorder: " + bt.size() + " " + bt.postorder());

        System.out.println(bt.toBinaryTreeString());
    }

    /**
     * Factory function to create a new node storing element e.
     */
    protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
        return new Node<E>(e, parent, left, right);
    }

    /**
     * Verifies that a Position belongs to the appropriate class, and is not one
     * that has been previously removed. Note that our current implementation does
     * not actually verify that the position belongs to this particular list
     * instance.
     *
     * @param p a Position (that should belong to this tree)
     * @return the underlying Node instance for the position
     * @throws IllegalArgumentException if an invalid position is detected
     */
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node)) throw new IllegalArgumentException("Not valid position type");
        Node<E> node = (Node<E>) p; // safe cast
        if (node.getParent() == node) // our convention for defunct node
            throw new IllegalArgumentException("p is no longer in the tree");
        return node;
    }

    /**
     * Returns the number of nodes in the tree.
     *
     * @return number of nodes in the tree
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the root Position of the tree (or null if tree is empty).
     *
     * @return root Position of the tree (or null if tree is empty)
     */
    @Override
    public Position<E> root() {
        return root;
    }

    // update methods supported by this class

    /**
     * Returns the Position of p's parent (or null if p is root).
     *
     * @param p A valid Position within the tree
     * @return Position of p's parent (or null if p is root)
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    @Override
    public Position<E> parent(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getParent();
    }

    /**
     * Returns the Position of p's left child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the left child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> left(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getLeft();
    }

    /**
     * Returns the Position of p's right child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the right child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> right(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getRight();
    }

    @Override
    public int depth(Position<E> p) throws IllegalArgumentException {
        // TODO
        if(!(p instanceof Node<E>)){
            throw new IllegalArgumentException("Incompatible node");
        }
        if(searchNode((Node<E>)p,root)==null){
            throw new IllegalArgumentException("node not located in the tree");
        }
        int depth=0;
        Node<E> temp = (Node<E>)p;
        while(temp!=root){
            depth++;
            temp=temp.getParent();
        }
        return depth;
    }

    public int height(Position<E> p) throws IllegalArgumentException {
        // TODO
        if(!(p instanceof Node<E>)){
            throw new IllegalArgumentException("Incompatible node");
        }
        if(searchNode((Node<E>)p,root)==null){
            throw new IllegalArgumentException("node not located in the tree");
        }



        return depth((getDeepest(root)))-this.depth(p);
    }

    @Override
    public boolean isExternal(Position<E> p) {
        // TODO
        if(!(p instanceof Node<E>)){
            throw new IllegalArgumentException("Incompatible node");
        }
        if(searchNode((Node<E>)p,root)==null){
            throw new IllegalArgumentException("node not located in the tree");
        }
        return ((Node<E>)p).getLeft()==null && ((Node<E>)p).getRight()==null;
    }

    /**
     * Places element e at the root of an empty tree and returns its new Position.
     *
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalStateException if the tree is not empty
     */
    public Position<E> addRoot(E e) throws IllegalStateException {
        // TODO
        if(root!=null){
            throw new IllegalStateException("Tree already has a root");
        }
        Node<E> newRoot =new Node<>(e,null,null,null);
        root=newRoot;
        size++;
        return newRoot;
    }

    public void insert(E e) {
        // TODO

    }

    // recursively add Nodes to binary tree in proper position
    private Node<E> addRecursive(Node<E> p, E e) {
        // TODO
        return null;
    }

    /**
     * Creates a new left child of Position p storing element e and returns its
     * Position.
     *
     * @param p the Position to the left of which the new element is inserted
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p already has a left child
     */
    public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
        // TODO
        if(!(p instanceof Node<E>)){
            System.out.println("Inputted node is not compatible");
            return null;
        }
        if(((Node<E>) p).getLeft()!=null){
            throw new IllegalArgumentException("node already contains a left child");
        }
        if(searchNode((Node<E>)p,root)==null){
            throw new IllegalArgumentException("node not located in the tree");
        }
        Node<E> newNode = new Node<E>(e,(Node)p,null,null);
        ((Node<E>)p).setLeft(newNode);
        size++;
        return newNode;
    }

    /**
     * Creates a new right child of Position p storing element e and returns its
     * Position.
     *
     * @param p the Position to the right of which the new element is inserted
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p already has a right child
     */
    public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
        // TODO
        if(!(p instanceof Node<E>)){
            System.out.println("Inputted node is not compatible");
            return null;
        }
        if(((Node<E>) p).getRight()!=null){
            throw new IllegalArgumentException("node already contains a left child");
        }
        if(searchNode((Node<E>)p,root)==null){
            throw new IllegalArgumentException("node not located in the tree");
        }
        Node<E> newNode = new Node<E>(e,(Node)p,null,null);
        ((Node<E>)p).setRight(newNode);
        size++;
        return newNode;
    }

    /**
     * Replaces the element at Position p with element e and returns the replaced
     * element.
     *
     * @param p the relevant Position
     * @param e the new element
     * @return the replaced element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public E set(Position<E> p, E e) throws IllegalArgumentException {
        // TODO
        if(!(p instanceof Node<E>)){
            System.out.println("Inputted node is not compatible");
            return null;
        }
        if(searchNode((Node<E>)p,root)==null){
            throw new IllegalArgumentException("node not located in the tree");
        }
        Node<E> newNode = new Node<E>(e,((Node<E>) p).getParent(),((Node<E>) p).getLeft(),((Node<E>) p).getRight());
        if(((Node<E>) p).getParent().getLeft()==p && ((Node<E>) p).getParent()!=null){
            ((Node<E>) p).getParent().setLeft(newNode);
        }
        else if(((Node<E>) p).getParent()!=null){
            ((Node<E>) p).getParent().setRight(newNode);
        }
        return p.getElement();
    }

    /**
     * Attaches trees t1 and t2, respectively, as the left and right subtree of the
     * leaf Position p. As a side effect, t1 and t2 are set to empty trees.
     *
     * @param p  a leaf of the tree
     * @param t1 an independent tree whose structure becomes the left child of p
     * @param t2 an independent tree whose structure becomes the right child of p
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p is not a leaf
     */
    public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2) throws IllegalArgumentException {
        // TODO
        if(!(p instanceof Node<E>)){
            System.out.println("Inputted node is not compatible");
            return;
        }
        if(searchNode((Node<E>)p,root)==null){
            throw new IllegalArgumentException("node not located in the tree");
        }
        if(((Node<E>) p).getLeft()!=null || ((Node<E>) p).getRight()!=null){
            throw new IllegalArgumentException("not a leaf node");
        }

        ((Node<E>) p).setLeft((Node<E>)t1.root());
        ((Node<E>) p).setRight((Node<E>)t2.root());

        //Set t1 and t2 to empty trees
        LinkedBinaryTree<E> emptyBinaryTree = new LinkedBinaryTree<>();
        size=size+ t1.size()+t2.size();
        t1=emptyBinaryTree;
        t2=emptyBinaryTree;
    }

    /**
     * Removes the node at Position p and replaces it with its child, if any.
     *
     * @param p the relevant Position
     * @return element that was removed
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p has two children.
     */
    public E remove(Position<E> p) throws IllegalArgumentException {
        // TODO
        E removedE = p.getElement();

        if(!(p instanceof Node<E>)){
            System.out.println("Inputted node is not compatible");
            return null;
        }
        if(searchNode((Node<E>)p,root)==null){
            throw new IllegalArgumentException("node not located in the tree");
        }
        if(((Node<E>) p).getRight()!=null && ((Node<E>) p).getLeft()!=null){
            throw new IllegalArgumentException("node has 2 child nodes");
        }
        if(((Node<E>) p).getLeft()!=null){
            p=((Node<E>) p).getLeft();
        }
        else{
            p=((Node<E>) p).getRight();
        }
        size--;
        return removedE;
    }

    public String toString() {
        return positions().toString();
    }

    public void createLevelOrder(ArrayList<E> l) {
        // TODO
    }

    private Node<E> createLevelOrderHelper(java.util.ArrayList<E> l, Node<E> p, int i) {
        // TODO
        return null;
    }

    public void createLevelOrder(E[] arr) {
        root = createLevelOrderHelper(arr, root, 0);
    }

    private Node<E> createLevelOrderHelper(E[] arr, Node<E> p, int i) {
        // TODO
        if(i==0){
            p=(Node<E>)this.addRoot(null);
        }
        p.setElement(arr[i]);
        if(2*i+1<arr.length && i>0){
            //Node<E> newLeftNode= new Node<E>(arr[i+(int)Math.pow(2,i)],p,null,null);
            Node<E> newLeftNode= new Node<E>(arr[2*i],p,null,null);
            p.setLeft(newLeftNode);
            createLevelOrderHelper(arr, p.getLeft(), 2*i+1);
        }
        if(2*i+2<arr.length && i>0){
            //Node<E> newRightNode= new Node<E>(arr[i+(int)Math.pow(2,i)+1],p,null,null);
            Node<E> newRightNode= new Node<E>(arr[2*i+1],p,null,null);
            p.setRight(newRightNode);
            createLevelOrderHelper(arr, p.getRight(), 2*i+2);
        }
        if(2*i<arr.length && i==0){
            //Node<E> newLeftNode= new Node<E>(arr[i+(int)Math.pow(2,i)],p,null,null);
            Node<E> newLeftNode= new Node<E>(arr[1],p,null,null);
            p.setLeft(newLeftNode);
            createLevelOrderHelper(arr, p.getLeft(), 1);
        }
        if(2*i+1<arr.length && i==0){
            //Node<E> newRightNode= new Node<E>(arr[i+(int)Math.pow(2,i)+1],p,null,null);
            Node<E> newRightNode= new Node<E>(arr[2],p,null,null);
            p.setRight(newRightNode);
            createLevelOrderHelper(arr, p.getRight(), 2);
        }

        return p;
    }

    public String toBinaryTreeString() {
        BinaryTreePrinter<E> btp = new BinaryTreePrinter<>(this);
        return btp.print();
    }

    /**
     * Helper Functions
     */

    private Position<E> searchNode (Node<E> target, Node<E> parent) {
        if(parent==target){
            return target;
        }
//        if(parent.getLeft()==target){
//            return target;
//        }
//        else if(parent.getRight()==target){
//            return target;
//        }

        if(parent.getRight()!=null && searchNode(target,parent.getRight())== target){
            return target;
        }
        if(parent.getLeft()!=null && searchNode(target,parent.getLeft())== target){
            return target;
        }
        return null;

    }

    private Node<E> getDeepest(Node<E> p){

        if(p.getLeft()==null && p.getRight()==null){
            return p;
        }
        if(p.getLeft()==null && p.getRight()!=null){
            return getDeepest(p.getRight());
        }
        else if(p.getLeft()!=null && p.getRight()==null){
            return getDeepest(p.getLeft());
        }
        else{
            Node<E> tempRight=getDeepest(p.getRight());
            Node<E> tempLeft=getDeepest(p.getLeft());
            if(this.depth(tempRight)>this.depth(tempLeft)){
                return tempRight;
            }
            else{
                return tempLeft;
            }
        }
    }



    /**
     * Nested static class for a binary tree node.
     */
    protected static class Node<E> implements Position<E> {
        private E element;
        private Node<E> left, right, parent;

        public Node(E e, Node<E> p, Node<E> l, Node<E> r) {
            element = e;
            left = l;
            right = r;
            parent = p;
        }

        // accessor
        public E getElement() {
            return element;
        }

        // modifiers
        public void setElement(E e) {
            element = e;
        }

        public Node<E> getLeft() {
            return left;
        }

        public void setLeft(Node<E> n) {
            left = n;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setRight(Node<E> n) {
            right = n;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setParent(Node<E> n) {
            parent = n;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (element == null) {
                sb.append("\u29B0");
            } else {
                sb.append(element);
            }
            return sb.toString();
        }
    }
}
