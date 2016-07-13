import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.plaf.basic.BasicArrowButton;

public class ShapeDisplayProgram {
    public static final int MAX_VERTICES = 5000;
    public static final int MAX_FACES = 10000;
    
    public static DisplayPanel   displayPanel;
    
    public static int         numVertices;  // Number of vertices in the model
    public static int         numFaces;     // Number of faces in the model
    	
    public static float[][]   vertices;     // 3D vertices of the model
    public static int[][]     faces;        // 3D faces of the model
    
    public static int[][]     facesX = new int[0][0];    // X coordinates of 2D projected face vertices 
    public static int[][]     facesY = new int[0][0];    // Y coordinates of 2D projected face vertices
    public static float[] max_x = new float[1];
    
    public static void sortByXCoordinates() {
        // ... Put your code in here ...
    	for (int i=0; i<numFaces; i++) 
    	{
            //System.out.println(facesX[i][0]+" "+facesX[i][1]+" "+facesX[i][2]);
    	}
    	int n = facesX.length;
        for (int j = 1; j < n; j++) {
            int key = (int)max_x[j];
            int i = j-1;
            int x1 = facesX[j][0];
            int y1 =facesY[j][0];
            int x2 =facesX[j][1];
            int y2 =facesY[j][1];
            int x3 =facesX[j][2];
            int y3 =facesY[j][2];
            while ( (i > -1) && ( max_x [i] < key ) ) 
            {
                facesX[i+1][0] = facesX[i][0];
                facesY[i+1][0] = facesY[i][0];
                facesX[i+1][1] = facesX[i][1];
                facesY[i+1][1] = facesY[i][1];
                facesX[i+1][2] = facesX[i][2];
                facesY[i+1][2] = facesY[i][2];
                max_x[i+1] = max_x[i];
                i--;
            }
            facesX[i+1][0] = x1;
            facesY[i+1][0] = y1;
            facesX[i+1][1] = x2;
            facesY[i+1][1] = y2;
            facesX[i+1][2] = x3;
            facesY[i+1][2] = y3;
            max_x[i+1] = key;
        }
    }
    
    public static void computeProjection() {
        // Now create the polygons/triangles
        System.out.println("Sorting faces, please wait ...");
        for (int i=0; i<numFaces; i++) {
            float x0 = (int)(vertices[faces[i][0]][0] * 700);
            float y0 = (int)(vertices[faces[i][0]][1] * 700);
            float z0 = (int)(vertices[faces[i][0]][2] * 700);
            float x1 = (int)(vertices[faces[i][1]][0] * 700);
            float y1 = (int)(vertices[faces[i][1]][1] * 700);
            float z1 = (int)(vertices[faces[i][1]][2] * 700);
            float x2 = (int)(vertices[faces[i][2]][0] * 700);
            float y2 = (int)(vertices[faces[i][2]][1] * 700);
            float z2 = (int)(vertices[faces[i][2]][2] * 700);
            facesX[i][0] = (int)z0;
            facesY[i][0] = 800-(int)y0;
            facesX[i][1] = (int)z1;
            facesY[i][1] = 800-(int)y1;
            facesX[i][2] = (int)z2;
            facesY[i][2] = 800-(int)y2;
            max_x[i]=Math.max(facesX[i][0], Math.max(facesX[i][1], facesX[i][2]));
            //System.out.println(""+max_x[i]);
        }       
        sortByXCoordinates();
        System.out.println("All done.");
    }
    
    
    // This method loads the model from a file
    public static void loadData(String dataFile) {
        try { 
            BufferedReader   in  = new BufferedReader(new FileReader(dataFile)); 
            
            // Find out how many vertices and faces there are
            StringTokenizer wholeLine = new StringTokenizer(in.readLine()); // skip the word OFF
            wholeLine = new StringTokenizer(in.readLine());
            numVertices = Integer.parseInt(wholeLine.nextToken());
            numFaces = Integer.parseInt(wholeLine.nextToken());
            
            // Now read in the vertices
            vertices = new float[numVertices][3];
            for (int i=0; i<numVertices; i++) {
                wholeLine = new StringTokenizer(in.readLine());
                vertices[i][0] = Float.parseFloat(wholeLine.nextToken());
                vertices[i][1] = Float.parseFloat(wholeLine.nextToken());
                vertices[i][2] = Float.parseFloat(wholeLine.nextToken());
            }

            // Now read in the faces
            faces = new int[numFaces][3];
            max_x = new float[numFaces];
            for (int i=0; i<numFaces; i++) {
                wholeLine = new StringTokenizer(in.readLine());
                wholeLine.nextToken(); // skip the first number which is the #vertices of faces, assumed to be 3
                faces[i][0] = Integer.parseInt(wholeLine.nextToken());
                faces[i][1] = Integer.parseInt(wholeLine.nextToken());
                faces[i][2] = Integer.parseInt(wholeLine.nextToken());
            }
            
            facesX = new int[numFaces][3];
            facesY = new int[numFaces][3];

            // All done
            in.close(); 
        } catch (FileNotFoundException e) { 
            System.out.println("Error: Cannot open file for reading"); 
            System.exit(-1);
        } catch (IOException e) { 
            System.out.println("Error: Cannot read from file"); 
            System.exit(-1);
        } 
    }
        
    public static void main(String [] args) {
        String fileName = JOptionPane.showInputDialog(null, "Enter 3D Model File Name (e.g., Spider)");
        loadData(fileName + ".off");
        System.out.println("Vertices: " + numVertices);
        System.out.println("Faces: " + numFaces);
        
        computeProjection();
        
        JFrame frame = new JFrame("Shape Display Program");
        frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        frame.getContentPane().add(displayPanel = new DisplayPanel(facesX, facesY));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack(); // Makes size according to panel's preference
        frame.setVisible(true);
    }
}
