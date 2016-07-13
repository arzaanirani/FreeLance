import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class Glitch {

    public static void incrementNth(String inFile, String outFile, int n) {
        try {
            InputStream in = new FileInputStream(inFile);
            OutputStream out = new FileOutputStream(outFile);
            int current = 0;
            int count = 0;
            while (current >= 0) {
                current = in.read();
                if (count++ % n == 0 && current >= 0)
                    current++;
                out.write(current);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void swapRandomly(String inFile, String outFile, Random rng, double prob) {
        try {
            InputStream in = new FileInputStream(inFile);
            OutputStream out = new FileOutputStream(outFile);
            int a = 0;
            int b = 0;
            while (a >= 0 && b >= 0) {
                a = in.read();
                b = in.read();
                boolean swap = rng.nextDouble() < prob;
                out.write(swap ? b : a);
                out.write(swap ? a : b);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        swapRandomly("original.gif", "swap.gif", new Random(), 0.001);
    }
}