package main;

import algorythms.GradientMethod;
import algorythms.MinimisationAlg;
import functions.TernarFunction;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        TernarFunction f = (x, y, z) ->
                16 * x * x + 15 * y * y + 2 * z * z + 0.018 * x * y + x - z;
        TernarFunction dFdx = (x, y, z) ->
                32 * x + 0.018 * y + 1;
        TernarFunction dFdy = (x, y, z) ->
                30 * y + 0.018 * x;
        TernarFunction dFdz = (x, y, z) ->
                4 * z - 1;

        double[][] gaussian = {{32, 0.018, 0}, {0.018, 30, 0}, {0, 0, 4}};

        try (FileWriter file = new FileWriter("result.txt")) {
            MinimisationAlg gradient = new GradientMethod(0.00001, dFdx, dFdy, dFdz, gaussian, file);
            double[] x0 = {1, 1, 1};
            double[] minimum = gradient.minimise(x0);
            System.out.println("minimum is: " + Arrays.toString(minimum));
            file.write("minimum is: " + Arrays.toString(minimum) + "\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
