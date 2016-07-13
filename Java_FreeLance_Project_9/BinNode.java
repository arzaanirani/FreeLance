
public interface BinNode<E> {
    public E getElement();
    public void setElement(E value);

    public BinNode<E> getLeft();

    public BinNode<E> getRight();

    public boolean isLeaf();
}