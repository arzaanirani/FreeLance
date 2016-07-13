package e2soln;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * An undirected graph of Node<T>s.
 * @param <T> the type of values in this Graph's Nodes.
 */
public class Graph<T> {

    // instance variable
	private Map<Node<T>, Set<Node<T>>> graphNodes;
	
    /**
     * Creates a new empty Graph.
     */
        // constructor
	public Graph() {
		graphNodes = new HashMap<Node<T>, Set<Node<T>>>();
	}

    /**
     * Returns a Set of Nodes in this Graph.
     * @return a Set of Nodes in this Graph
     */
        // getNodes
	public Set<Node<T>> getNodes() {
		return graphNodes.keySet();
	}

    /**
     * Returns the Node from this Graph with the given ID.
     * @param id the ID of the Node to return
     * @return the Node from this Graph with the given ID
     * @throws NoSuchNodeException if there is no Node with ID
     *    id in this Graphs
     */
        // getNode
	public Node<T> getNode(int id) {
		for (Map.Entry<Node<T>, Set<Node<T>>> entry : graphNodes.entrySet()) 
		    if (entry.getKey().getId() == id) 
		    	return entry.getKey();
		return null;
	}
	
    /**
     * Returns a Set of neighbours of the given Node.
     * @param node the Node whose neighbours are returned
     * @return a Set of neighbours of node
     */
    // getNeighbours
	public Set<Node<T>> getNeighbours(Node<T> node) {
		return graphNodes.get(node);
	}

    /**
     * Returns whether Nodes with the given IDs are adjacent in this Graph.
     * @param id1 ID of the node to test for adjacency
     * @param id2 ID of the node to test for adjacency
     * @return true, if Nodes with IDs id1 and id2 are adjacent in this Graph,
     *    and false otherwise
     * @throws NoSuchNodeException if node with ID id1 or id2 is not in this Graph
     */
    // areAdjacent
	public boolean areAdjacent(int id1, int id2) throws NoSuchNodeException {
		Node<T> node1 = getNode(id1);
		Node<T> node2 = getNode(id2);
		if (node1 == null || node2 == null) {
			//throw exception
			throw new NoSuchNodeException();
		}
		return areAdjacent(node1, node2);
	}
	
    /**
     * Returns whether the given Nodes are adjacent in this Graph.
     * @param node1 the Node to test for adjacency with node2
     * @param node2 the Node to test for adjacency with node1
     * @return true, if node1 and node2 are adjacent in this Graph,
     *    and false otherwise
     * @throws NoSuchNodeException if node1 or node2 are not in this Graph
     */
    // areAdjacent
	public boolean areAdjacent(Node<T> node1, Node<T> node2) {
		return getNeighbours(node1).contains(node2);
	}

    /**
     * Returns the number of nodes in this Graph.
     * @return The number of nodes in this Graph.
     */
    public int getNumNodes() {
        return getNodes().size();	
    }

    /**
     * Returns the number of edges in this Graph.
     * @return The number of edges in this Graph.
     */
    public int getNumEdges() {	
        int total = 0;

        for (Node<T> node : getNodes()) {
            total += getNeighbours(node).size();
        }

        return total / 2;
    }

    /**
     * Adds a new Node with the given value to this Graph. 
     * @param id the ID of the new Node
     * @param value the value of the new Node
     */
    // addNode
    public void addNode(int id, T value) {
    	graphNodes.put(new Node<T>(id, value), new HashSet<Node<T>>());
    }

    /**
     * Adds an edge between the given nodes in this Graph. If there 
     * is already an edge between node1 and node2, does nothing.
     * @param node1 the node to add an edge to and from node2
     * @param node2 the node to add an edge to and from node1
     * @throws NoSuchNodeException if node1 or node2 is not in
     *    this Graph
     */
    // addEdge
    public void addEdge(Node<T> node1, Node<T> node2) throws NoSuchNodeException {
    	if (!areAdjacent(node1, node2)) {
    		if (!graphNodes.containsKey(node1) || !graphNodes.containsKey(node2)) {
    			throw new NoSuchNodeException();
    		}
    		graphNodes.get(node1).add(node2);
    		graphNodes.get(node2).add(node1);
    	}
    }

    /**
     * Adds an edge between the nodes with the given IDs in this Graph. 
     * @param id1 ID of the node to add an edge to and from
     * @param id2 ID of the node to add an edge to and from
     * @throws NoSuchNodeExceptionf there is no Node with ID 
     *    id1 or ID id2 in this Graph.
     */
    // addEdge
    public void addEdge(int id1, int id2) throws NoSuchNodeException {
    	Node<T> node1 = getNode(id1);
		Node<T> node2 = getNode(id2);
		if (node1 == null || node2 == null) {
			//throw exception
			throw new NoSuchNodeException();
		}
		graphNodes.get(node1).add(node2);
		graphNodes.get(node2).add(node1);
    }

    @Override
    public String toString() {

        String result = "";
        result += "Number of nodes: " + getNumNodes() + "\n";
        result += "Number of edges: " + getNumEdges() + "\n";

        for (Node<T> node: getNodes()) {
            result += node + " is adjacent to: ";
            for    (Node<T> neighbour: getNeighbours(node)) {
                result += neighbour + " ";
            }
            result += "\n";
        }
        return result;
    }
}