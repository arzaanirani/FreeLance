package com.ldnel;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;


/**
 * Created by ldnel_000 on 2015-07-27.
 */
public class FXMouseApplication extends Application {

        //private instance variables

        //Model data -the data our app is about
        private MovingWord movingWord;
        private Graph graph;

        //variables to keep track of items targeted with mouse
        private MovingWord movingWordBeingTargeted = null;
        private Node nodeBeingTargeted = null;
        private Node nodeBeingDragged = null;
        private Edge edgeBeingTargeted = null;
        private Node edgeBeingCreated = null; //holds the origin node

        private Location previousMouseLocation; //keep track of last mouse click location
        private double deltaX, deltaY; //distance mouse was pressed from object location

        //background image
        private Image map;
        private String mapSourcePath = "BiwardMap.gif"; //image file relative to project directory
                                                        //500x591 biward map image
        private boolean displayBackgroundImage = false;

        private ArrayList<Drawable> itemsToDraw; //collection of things to draw on canvas
        private ArrayList<Movable> itemsToMove; //collection of things that move by themselves

        private AnimationTimer timer; //for animating frame based motion

        public static boolean animationIsRunning = false;


        //GUI elements
        Canvas canvas; //drawing canvas

        //GUI menus
        MenuBar menubar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu runMenu = new Menu("Run");
        Menu displayMenu = new Menu("Display");
        ContextMenu contextMenu = new ContextMenu(); //popup menu

        //GUI list, text fields, labels, and buttons
        Label wordLabel = new Label("  word:");
        TextField wordTextField = new TextField();
        Button enterButton = new Button("Enter");

        private void buildMenus(Stage theStage){
        //build the menus for the menu bar

            //Build Run menu items
            MenuItem startMenuItem = new MenuItem("Start Timer");
            runMenu.getItems().addAll(startMenuItem);
            startMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    startAnimation();
                    repaintCanvas(canvas);
                }
            });
            MenuItem stopMenuItem = new MenuItem("Stop Timer");
            runMenu.getItems().addAll(stopMenuItem);
            stopMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    stopAnimation();
                    repaintCanvas(canvas);
                }
            });

            //Build File menu items
            MenuItem aboutMenuItem = new MenuItem("About This App");
            fileMenu.getItems().addAll(aboutMenuItem);
            aboutMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Ver 1.0 \u00A9 L.D. Nel 2015");
                    alert.showAndWait();
                }
            });

            fileMenu.getItems().addAll(new SeparatorMenuItem());

            MenuItem newMenuItem = new MenuItem("New");
            fileMenu.getItems().addAll(newMenuItem);
            newMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    itemsToDraw.remove(graph);
                    graph = new Graph();
                    itemsToDraw.add(graph);
                    repaintCanvas(canvas);
                 }
            });

            MenuItem openMenuItem = new MenuItem("Open");
            fileMenu.getItems().addAll(openMenuItem);
            openMenuItem.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent e){
                    System.out.println("openFile()");
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Open File");
                    String currentDirectoryProperty = System.getProperty("user.dir");
                    File currentDirectory = new File(currentDirectoryProperty);
                    fileChooser.setInitialDirectory(currentDirectory);
                    File selectedFile = fileChooser.showOpenDialog(theStage);
                    System.out.println("opened file: " + selectedFile);
                    if(selectedFile != null) openFile(selectedFile);
                }
            });

            MenuItem saveMenuItem = new MenuItem("Save As");
            fileMenu.getItems().addAll(saveMenuItem);
            saveMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {

                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Save As");
                    String currentDirectoryProperty = System.getProperty("user.dir");
                    File currentDirectory = new File(currentDirectoryProperty);
                    fileChooser.setInitialDirectory(currentDirectory);
                    File selectedFile = fileChooser.showSaveDialog(theStage);


                    System.out.println("save to file: " + selectedFile);
                    saveFile(selectedFile);

                }
            });

            fileMenu.getItems().addAll(new SeparatorMenuItem());

            MenuItem openImageMenuItem = new MenuItem("Open Image");
            fileMenu.getItems().addAll(openImageMenuItem);
            openImageMenuItem.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent e){
                    System.out.println("openFile()");
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Choose Image File");
                    fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
                    String currentDirectoryProperty = System.getProperty("user.dir");
                    File currentDirectory = new File(currentDirectoryProperty);
                    fileChooser.setInitialDirectory(currentDirectory);
                    File selectedFile = fileChooser.showOpenDialog(theStage);
                    System.out.println("opened file: " + selectedFile);
                    if(selectedFile != null) openImageFile(selectedFile);
                }
            });
            //Build Display menu items
            //Shows example of using a check box menu item
            CheckMenuItem displayImageItem = new CheckMenuItem("Display Background Image");
            displayMenu.getItems().addAll(displayImageItem);
            displayImageItem.setSelected(displayBackgroundImage);
            displayImageItem.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue ov, Boolean oldValue, Boolean newValue) {
                    //set app model variables and update canvas
                    displayBackgroundImage = newValue;
                    repaintCanvas(canvas);
                }
            });

            displayMenu.getItems().addAll(new SeparatorMenuItem());

            ToggleGroup nodeSizeToggleGroup = new ToggleGroup();

            RadioMenuItem smallNodeMenuItem = new RadioMenuItem("Small Nodes");
            displayMenu.getItems().addAll(smallNodeMenuItem);
            smallNodeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    Node.NODE_RADIUS = Node.SMALL_NODE_RADIUS;
                    repaintCanvas(canvas);
                }
            });
            smallNodeMenuItem.setToggleGroup(nodeSizeToggleGroup);

            RadioMenuItem mediumNodeMenuItem = new RadioMenuItem("Medium Nodes");
            displayMenu.getItems().addAll(mediumNodeMenuItem);
            mediumNodeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    Node.NODE_RADIUS = Node.MEDIUM_NODE_RADIUS;
                    repaintCanvas(canvas);
                }
            });
            mediumNodeMenuItem.setToggleGroup(nodeSizeToggleGroup);


            RadioMenuItem largeNodeMenuItem = new RadioMenuItem("Large Nodes");
            displayMenu.getItems().addAll(largeNodeMenuItem);
            largeNodeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    Node.NODE_RADIUS = Node.LARGE_NODE_RADIUS;
                    repaintCanvas(canvas);
                }
            });
            largeNodeMenuItem.setToggleGroup(nodeSizeToggleGroup);

            mediumNodeMenuItem.setSelected(true);
            Node.NODE_RADIUS = Node.MEDIUM_NODE_RADIUS;


            //Build Popup context menu items
            MenuItem pauseContextMenuItem = new MenuItem("Pause Animation");
            contextMenu.getItems().addAll(pauseContextMenuItem);
            pauseContextMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    stopAnimation();
                    repaintCanvas(canvas);
                }
            });

            MenuItem resumeContextMenuItem = new MenuItem("Resume Animation");
            contextMenu.getItems().addAll(resumeContextMenuItem);
            resumeContextMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    startAnimation();
                    repaintCanvas(canvas);
                }
            });





        }

    //required by any Application subclass
        @Override
        public void start(Stage mainStage){

            //Here we do most of the initialization for the application

            //load background map from file
            // Load an image from local machine using an InputStream
            try {
                map = new Image(new FileInputStream(mapSourcePath));
            }
            catch(FileNotFoundException e) {
                System.out.println("ERROR: Image File Not Found");
                map = null;

            }

            mainStage.setTitle("Graph Editor");

            VBox root = new VBox(); //root group node of scene graph (vertical layout box)
            Scene theScene = new Scene(root); //our GUI scene
            mainStage.setScene(theScene); //add scene to our app's stage

            //build application menus
            //add menus to menu bar object
            menubar.getMenus().add(fileMenu);
            menubar.getMenus().add(runMenu);
            menubar.getMenus().add(displayMenu);

            //add menu bar object to application scene root
            root.getChildren().add(menubar); //add menubar to GUI
            buildMenus(mainStage); //add menu items to menus


             //create canvas the size of background image
            canvas = new Canvas(map.getWidth(),map.getHeight()); //element we will draw on
            root.getChildren().add(canvas);

            //add mouse event handler to canvas
            //example of using a Lamda expression in java
            canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
                        handleMousePressedEvent(e);
                    }
            );
            canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent e) -> {
                        handleMouseReleasedEvent(e);
                    }
            );
            canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent e) -> {
                        handleMouseDraggedEvent(e);
                    }
            );
            
            //add key pressed handler to canvas
            canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
                public void handle(KeyEvent ke) {
                    //Listen for arrow keys, delete using key press event.
                    //arrow keys, delete key, don't show up in KeyTyped events,
                    String text = "";
                    if (ke.getCode() == KeyCode.RIGHT) text += "RIGHT";
                    else if (ke.getCode() == KeyCode.LEFT) text += "LEFT";
                    else if (ke.getCode() == KeyCode.UP) text += "UP";
                    else if (ke.getCode() == KeyCode.DOWN) text += "DOWN";
                    else if (ke.getCode() == KeyCode.DELETE) { 
                        if(graph != null) graph.deleteSelected(); //delete all selected
                        repaintCanvas(canvas); //update display
                    }
                    else text += ke.getCharacter();

                    System.out.println("key press: " + text);

                    ke.consume(); //don't let keyboard event propagate
                }
            });

            //add key typed handler to canvas
            canvas.setOnKeyTyped(new EventHandler<KeyEvent>() {
                public void handle(KeyEvent ke) {
                    //canvas handler for typed keys
                    String text = ke.getCharacter();
                    if (ke.isAltDown()) {
                        text += " , alt down";
                    }
                    if (ke.isControlDown()) {
                        text += " , ctrl down";
                    }
                    if (ke.isMetaDown()) {
                        text += " , meta down";
                    }
                    if (ke.isShiftDown()) {
                        text += " , shift down";
                    }

                    System.out.println("key typed: " + text + " - " + ke.getCode());

                    ke.consume(); //don't let keyboard event propagate
                }
            });

            //create text entry field and enter button
            HBox wordEntryBox = new HBox(); //horizontal layout box
            wordEntryBox.setSpacing(20); //space elements
            wordEntryBox.setAlignment(Pos.TOP_LEFT);
            wordEntryBox.getChildren().addAll(wordLabel, wordTextField, enterButton);
            root.getChildren().addAll(wordEntryBox);

            wordTextField.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    ArrayList<Node> selectedNodes = graph.getSelectedNodes();
                    if(selectedNodes != null && selectedNodes.size() > 0){
                        for(Node n: selectedNodes) n.setLabel(wordTextField.getText().trim());
                    }
                    else
                      movingWord.setText(wordTextField.getText().trim());
                    wordTextField.clear(); //clear the text field
                    repaintCanvas(canvas);
                }
            });

            enterButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    ArrayList<Node> selectedNodes = graph.getSelectedNodes();
                    if(selectedNodes != null && selectedNodes.size() > 0){
                        for(Node n: selectedNodes) n.setLabel(wordTextField.getText().trim());
                    }
                    else
                      movingWord.setText(wordTextField.getText().trim());
                    wordTextField.clear(); //clear the text field
                    repaintCanvas(canvas);
                }
            });

            //create timer for animation
            timer = new AnimationTimer() { //e.g. of anonymous inner subclass
                @Override
                public void handle(long nowInNanoSeconds) {
                    //this method will be called abut 60 times per second
                    //which is default behaviour of the AnimationTimer class

                    //move all movable items within the area provided
                    for(Movable m : itemsToMove){
                        m.advanceInArea(0,0,canvas.getWidth(), canvas.getHeight());
                    }
                    repaintCanvas(canvas); //refesh our canvas rendering
                }
            };

            //initialize array lists and application model objects
            itemsToDraw = new ArrayList<Drawable>();
            itemsToMove = new ArrayList<Movable>();

            movingWord = new MovingWord("HelloWorld", 50,60);
            itemsToDraw.add(movingWord);
            itemsToMove.add(movingWord);

            graph = new Graph();
            itemsToDraw.add(graph);

            //create some hard-coded nodes in the graph.
            Node node1 = new Node(100, 100, "A");
            Node node2 = new Node(400,300,"B");
            Node node3 = new Node(100,400,"C");
            graph.addNode(node1);
            graph.addNode(node2);
            graph.addNode(node3);
            	
            graph.addEdge(new Edge(node1, node2));
            graph.addEdge(new Edge(node1, node3));
            graph.addEdge(new Edge(node2, node3));
            //startAnimation(); //start the animation timer

            mainStage.show(); //show the application window
            repaintCanvas(canvas); //do initial repaint

        }

    private void openImageFile(File anImageFile){
        if(anImageFile == null) return;
        Image loadedImage;
        try {
            loadedImage = new Image(new FileInputStream(anImageFile));
        }
        catch(FileNotFoundException e) {
            System.out.println("ERROR: Image File Not Found");
            loadedImage = null;

        }
        if(loadedImage != null) map = loadedImage;
        repaintCanvas(canvas);

    }
    private void openFile(File selectedFile){
        System.out.println("openFile()");
         Graph newGraph = Graph.parseFromFile(selectedFile);
         if(newGraph != null){
             itemsToDraw.remove(graph);
             graph = newGraph;
             itemsToDraw.add(graph);
             repaintCanvas(canvas);

         }
        //TO DO
        //Write code to open file

    }
    private void saveFile(File aFile){
        System.out.println("saveFile()");
        if(aFile == null) return;
        //save the chartModel to disk
        PrintWriter outputFileStream = null;

        try{
            outputFileStream = new PrintWriter(new FileWriter(aFile));

            graph.writeToFile(outputFileStream);

            outputFileStream.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error: Cannot open file" + outputFileStream + " for writing.");

        } catch (IOException e) {
            System.out.println("Error: Cannot write to file: " + outputFileStream);

        }
   }
    private void startAnimation(){
        timer.start();
        animationIsRunning = true;
    }
    private void stopAnimation(){
        timer.stop();
        animationIsRunning = false;
    }

    private void handleMousePressedEvent(MouseEvent e){
        //mouse handler for canvas
        canvas.requestFocus(); //enable canvas to receive keyboard events

        //Windows uses mouse release popup trigger
        //Mac uses mouse press popup trigger
        if(e.isPopupTrigger())
            contextMenu.show(canvas, e.getScreenX(), e.getScreenY());
        else {
            contextMenu.hide(); //in case it was left open

            //examine where the mouse is relative to the
            //screen and parts of the window
            Location mouseLocation = new Location(e.getX(), e.getY());
            previousMouseLocation = mouseLocation; //relative to canvas top


            movingWordBeingTargeted = null;
            nodeBeingTargeted = null;
            nodeBeingDragged = null;
            edgeBeingTargeted = null; //set to null (unselect existing)

            //determine if mouse was pressed on the moving word.
            if(movingWord.containsLocation(mouseLocation)) movingWordBeingTargeted = movingWord;

            //determine if mouse was pressed on a graph Node
            nodeBeingTargeted = graph.nodeAtLocation(mouseLocation);
            edgeBeingTargeted = graph.edgeAtLocation(mouseLocation); //get any edges clicked
            
            if (edgeBeingTargeted != null && nodeBeingTargeted == null) {
            	edgeBeingTargeted.invertSelected();
            }
            if(e.getClickCount() == 2 && nodeBeingTargeted == null && edgeBeingTargeted == null){ //if edge and node aren't selected, add node
                //double click on an empty spot
                //create a new node
                graph.addNode(new Node(mouseLocation));
            }
            //print out mouse locations for inspection
            System.out.println("mouse scene: " +
                            e.getSceneX() +
                            "," +
                            e.getSceneY()
            );
            System.out.println("mouse screen: " +
                            e.getScreenX() +
                            "," +
                            e.getScreenY()
            );
            System.out.println("mouse get: " +
                            e.getX() +
                            "," +
                            e.getY()
            );
        }
        repaintCanvas(canvas); //update the GUI canvas
    }

    private void handleMouseReleasedEvent(MouseEvent e){

        //Windows uses mouse release popup trigger
        //Mac uses mouse press popup trigger
        if(e.isPopupTrigger())
            contextMenu.show(canvas, e.getScreenX(), e.getScreenY());

        Node nodeReleasedOn = graph.nodeAtLocation(new Location(e.getX(), e.getY()));
        if(nodeReleasedOn != null) { //if we released on a node
        	if (nodeReleasedOn == nodeBeingTargeted) { //if the node being targeted was selected again
        		nodeBeingTargeted.toggleSelected(); //toggle the value
        	} else if (edgeBeingCreated != null && edgeBeingCreated != nodeReleasedOn) { //released edge on another node
        		if (!graph.edgeExists(edgeBeingCreated, nodeReleasedOn)) { //if the edge doesn't exist
        			graph.addEdge(new Edge(edgeBeingCreated, nodeReleasedOn)); //add it to the graph
        		}
        	}
        }
        edgeBeingCreated = null; //we released from dragging. (Doesnt matter if we were creating edge or not. Just set null)

        if(nodeReleasedOn == null && nodeBeingTargeted == null) graph.clearSelectedNodes();

        repaintCanvas(canvas);
    }

    private void handleMouseDraggedEvent(MouseEvent e){
        if(!animationIsRunning && movingWordBeingTargeted != null) {
            //move word is animation is stopped
            movingWord.moveDelta(e.getX() - previousMouseLocation.getX(),
                                 e.getY() - previousMouseLocation.getY());
        }

        if(nodeBeingTargeted != null && nodeBeingTargeted.isSelected()) {
            //transfer node being targeted to node being dragged
            nodeBeingDragged = nodeBeingTargeted;
            nodeBeingTargeted = null;
        } else if (nodeBeingTargeted != null && !nodeBeingTargeted.isSelected()) { //drag new edge from this node
        	edgeBeingCreated = nodeBeingTargeted; //set node to the instance variable
        }

        if(nodeBeingDragged != null ){
                //move all selected nodes
                for(Node selectedNode : graph.getSelectedNodes()) {
                    selectedNode.moveDelta(e.getX() - previousMouseLocation.getX(),
                            e.getY() - previousMouseLocation.getY());
                }
        } else if (edgeBeingCreated != null) {
        	previousMouseLocation = new Location(e.getX(), e.getY());
        }

        previousMouseLocation = new Location(e.getX(), e.getY());
        repaintCanvas(canvas);

    }



    private void repaintCanvas(Canvas aCanvas){
            //repaint the contents of our GUI canvas

            //obtain the graphics context for drawing on the canvas
            GraphicsContext thePen = aCanvas.getGraphicsContext2D();

            //clear the canvas
            double canvasWidth = aCanvas.getWidth();
            double canvasHeight = aCanvas.getHeight();
            thePen.clearRect(0, 0, canvasWidth, canvasHeight);

            if(map != null && displayBackgroundImage)
                thePen.drawImage(map, 0,0); //draw background image if available

            //set graphics context for drawing on canvas
           //Draw all the drawable items on the canvas
            for(Drawable item : itemsToDraw)
                item.drawWith(thePen);
            
            if (edgeBeingCreated != null) { //if we're dragging to an edge
            	thePen.setStroke(Color.BLACK); //set line color to black
            	thePen.setLineWidth(2); //make width 2
            	thePen.strokeLine(edgeBeingCreated.getLocation().getX(), edgeBeingCreated.getLocation().getY(), previousMouseLocation.getX(), previousMouseLocation.getY());
            }

        }

    public static void main(String[] args) {
            //entry point for javaFX application
            System.out.println("starting main application");
            launch(args); //will cause application's to start and
                          // run it's start() method
            System.out.println("main application is finished");
        }


}
