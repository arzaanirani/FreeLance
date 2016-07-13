public class BSTNode<Key, E> implements BinNode<E> {
/**
 * Attributes
 */
	private Key Key;
	private E Element;
	private BSTNode<Key,E> Left;
	private BSTNode<Key,E> Right;
	
	/**
	 * Constructor
	 * @param k The key.
	 * @param e The value.
	 * @param leftChild Left child.
	 * @param rightChild Right child.
	 */
	public BSTNode(Key k, E e, BSTNode<Key,E> leftChild, BSTNode<Key,E> rightChild ){
		Key = k;
		Element = e;
		Left=leftChild;
		Right = rightChild;
	}

	/**
	 * Gets the Key reference.
	 * @return Key Reference.
	 */
	public Key getKey(){
		return Key;
	}
	/**
	 * Method to set the reference key.
	 * @param k The key reference.
	 */
	public void setKey(Key k){
		Key = k;
	}
	
	/**
	 * Method to get the element in node.
	 * @return The Element in Node.
	 */
	@Override
	public E getElement() {
		
		return Element;
	}
	/**
 	* Method to set the element within node.
 	* @param e The Element via input.
 	*/
	@Override
	public void setElement(E e) {
		Element = e;
		
	}
	/**
	 * Method to set the left child of the current root node.
	 * @param leftChild The left child.
	 */
	 public void setLeft(BSTNode<Key, E> leftChild) {
		    Left = leftChild;
		    }
	/**
 	* Method to return the left node of the current node.
 	* @return The left Child.
 	*/
	/*
	@Override
	public BinNode<E> getLeft() {
		return Left;
	}*/
	@Override
	public BSTNode<Key, E> getLeft() {
		return Left;
	}
	/**
	 * Method to set the right child of the current root node.
	 * @param rightChild The right child.
	 */
	 public void setRight(BSTNode<Key, E> rightChild) {
		 Right = rightChild;
	 }
	/**
 	* Method to get the right node of the current node.
 	* @return The Right child.
 	*/
	@Override
	/*
	public BinNode<E> getRight() {
		return Right;
	}*/
	public BSTNode<Key, E> getRight() {
		return Right;
	}
	/**
 	* Method to check is node is a leaf node.
 	* @return True iff leaf node.
 	*/
	@Override
	public boolean isLeaf() {
		if(Left == null && Right == null){
			return true;
		}
		else
			return false;
	}
}
