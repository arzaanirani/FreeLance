/**
 * Run the tests
 */
public class Main {

    public static void main(String[] args) {
        WordCollection wordCollection = new WordCollection("input");
        System.out.println("---------------printInorder()------------");
        wordCollection.printInorder();
        System.out.println("---------------printLevelOrder()---------");
        wordCollection.printLevelOrder();
        System.out.println("---------------printPreorder()-----------");
        wordCollection.printPreorder();
        System.out.println("-------------------draw()----------------");
        wordCollection.draw();

        return;
    }
}
