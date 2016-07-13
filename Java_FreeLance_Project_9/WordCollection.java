import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Collection of words
 */
public class WordCollection {

    BST<String, Word> wordTree;

    /**
     * Construct a new Word Collection
     * @param filename  Name of the file
     */
    public WordCollection(String filename) {
        wordTree = new BST<String, Word>();
        readFile(filename);
    }

    /**
     * Reads the file and inserts them into the tree
     * @param filename  Name of the file
     */
    private void readFile(String filename) {
        File file = new File(filename);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;

            //Read until there are no more lines to read
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\W+");

                //Process every word
                for (int i = 0; i < words.length; i++) {
                    String curr = words[i].toLowerCase();
                    if (curr.length() > 0) {
                        //If this word exists, increment its counter. Otherwise place this word in the tree
                        if (wordTree.find(curr) != null) {
                            wordTree.find(curr).increment();
                        } else {
                            wordTree.insert(curr, new Word(curr));
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Print the preorder traversal
     */
    public void printPreorder() {
        wordTree.nonRecursivePreorderTraverse();
    }

    /**
     * Print the inorder traversal
     */
    public void printInorder() {
        wordTree.nonRecursiveInorderTraverse();
    }

    /**
     * Print the postorder traversal
     */
    public void printPostorder() {
        wordTree.nonRecursivePostorderTraverse();
    }

    /**
     * Print the level order traversal
     */
    public void printLevelOrder() {
        wordTree.levelOrderTraverse();
    }

    /**
     * Draw the tree sideways
     */
    public void draw() {
        wordTree.draw();
    }
}
