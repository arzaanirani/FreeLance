import java.util.*;
import java.io.*;

public class DecisionTree {

	private DecisionStump root;
	
	public DecisionTree(int dimension, int numberOfClasses) {
		DecisionStump.nClasses= numberOfClasses;
		DecisionStump.dim= dimension;
		root= new DecisionStump();
	}
	
	// returns the root of the decision tree
	public DecisionStump getRoot() {
		return root;
	}
	
	// get the class associated with this vector according to the decision tree
	public int getDecision(double[] vector) {
		return root.getDecision(vector);
	}
	
	// replace a leaf node by a DecisionStump with two leaves
	public void replace(DecisionStump leaf, int featureIndex, double threshold) {
		DecisionStump node = new DecisionStump(featureIndex, threshold); // create new node
		node.setGreaterBranch(new DecisionStump()); //set dummy greater than node
		node.setSmallerBranch(new DecisionStump()); //set dummy smaller than node
		if (leaf.getParent() == null)
			root = leaf;
		else if (leaf.getParent().getGreaterBranch() == leaf) { //replace larger
			leaf.getParent().setGreaterBranch(node); //set to greater
		} else {
			leaf.getParent().setSmallerBranch(node); //set to smaller
		}
	}
	
	// gets the leaf with the smallest maximal class probability
	public DecisionStump getSmallestMaxProb() {
		DecisionStump maxProbStump = null;
		double maxProb = -Double.MAX_VALUE;
		
		Stack<DecisionStump> stack = new Stack<DecisionStump>();
        stack.push(root);
 
        while(!stack.empty()){
            DecisionStump stump = stack.pop();
            
            if (stump.getSmallerBranch() == null && stump.getGreaterBranch() == null) { //only check if it's a leaf
	            if (stump.getMaxProb() > maxProb) {
	            	maxProbStump = stump;
	            	maxProb = stump.getMaxProb();
	            }
            }
            	
            if(stump.getSmallerBranch() != null){
                stack.push(stump.getSmallerBranch());
            }
            if(stump.getSmallerBranch() != null){
                stack.push(stump.getSmallerBranch());
            }
        }
        return maxProbStump;
	}

	// updates the probability count of each node of the decision tree
	// based on a sample for which the associated class is known
	public void train(double[] vector, int classNumber) {
		root.updateProbCount(vector, classNumber);
	}
	
	// reset the probability counts of all nodes
	public void resetAll() {
			reset(root);
	}

	private void reset(DecisionStump ds) {
	
		ds.resetProbCount();
		
		if (!ds.isExternal()) {
			reset(ds.getSmallerBranch()); // typo correction here
			reset(ds.getGreaterBranch()); // typo correction here
		}
	}

	// pre-order print of all nodes
	public void print() {
		preorderPrint(root);
	}

	public void preorderPrint(DecisionStump stump) {
		if(stump == null) return;
		  
		System.out.println(stump);
		  
		preorderPrint( stump.getSmallerBranch() );
		preorderPrint( stump.getGreaterBranch() ); 
	}
	
	private static void trainTree(DecisionTree dt, String file) {
		try {
			Scanner scanner = new Scanner(new File(file));
			scanner.useDelimiter("[,\r\n]+");
			
			while(scanner.hasNextDouble()) {
				double[] vector = { scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble() };
				String strType = scanner.next();
				int type = -1;
				if (strType.equalsIgnoreCase("Iris-setosa"))
					type = 0;
				else if (strType.equalsIgnoreCase("Iris-versicolor"))
					type = 1;
				else
					type = 2;
				dt.train(vector, type);
				//System.out.println("Vector: " + vector + " Type: " + type);
			}
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

		// ********* PART B *************** //
		System.out.println("********* PART B ***************");
		
		try {
			DecisionTree dt = new DecisionTree(4, 3);
			dt.replace(dt.getRoot(), 0, 5.0);
				
			trainTree(dt, "iris.data.txt");
			dt.print();
			
			dt.replace(dt.getSmallestMaxProb(), 2, 2.5);
			dt.resetAll();
			
			trainTree(dt, "iris.data.txt");
			dt.print();
			
			dt.replace(dt.getSmallestMaxProb(), 1, 3.0);
			dt.resetAll();
			
			trainTree(dt, "iris.data.txt");
			dt.print();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error reading file...");
		}
		
		// ********* PART C *************** //
		System.out.println("********* PART C ***************");
		try {
			Random random = new Random();
			DecisionTree dt = new DecisionTree(4, 3);
			dt.replace(dt.getRoot(), random.nextInt(4), random.nextDouble() * 5);
			
			for (int i = 0; i < 50; i++) {
				dt.replace(dt.getSmallestMaxProb(), random.nextInt(4), random.nextDouble() * 5);
				
				trainTree(dt, "iris.data.txt");
				dt.print();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error reading file...");
		}
		
	}
}