package algorythms;

import functions.TernarFunction;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GradientMethod implements MinimisationAlg {
    double eps;
    List<TernarFunction> dF;
    double[][] gaussian;
    FileWriter file;
    double[] real;

    public GradientMethod(double eps, TernarFunction dFdx, TernarFunction dFdy, TernarFunction dFdz, double[][] gaussian, FileWriter file) {
        this.eps = eps;
        this.dF = new ArrayList<>();
        dF.add(dFdx);
        dF.add(dFdy);
        dF.add(dFdz);
        this.gaussian = gaussian;
        this.file = file;
        real = new double[]{-2500000.0 / 79999973.0, 1500.0 / 79999973.0, 0.25};
    }

    private double getStep(double[] x) {
        double dsum = 0;
        double usum = 0;
        for (int k = 0; k < gaussian.length; k++) {
            double localSum = 0;
            for (int h = 0; h < gaussian.length; h++) {
                localSum += gaussian[k][h] * dF.get(k).calc(x[0], x[1], x[2]) * dF.get(h).calc(x[0], x[1], x[2]);
            }
            dsum += localSum;
            usum += Math.pow(dF.get(k).calc(x[0], x[1], x[2]), 2);
        }
        return usum / dsum;
    }

    private double difference(double[] x, double[] y) {
        double sum = 0;
        for (int i = 0; i < x.length; i++) {
            sum += Math.pow(x[i] - y[i], 2);
        }
        return sum / x.length;
    }

    private void printData(double[] x1, double[] x) throws IOException {
        System.out.println("vector: " + Arrays.toString(x1));
        System.out.println("difference: " + difference(x1, x));
        System.out.println("diff from real min: " + difference(x1, real) + "\n");

        this.file.write("vector: " + Arrays.toString(x1) + "\n");
        this.file.write("difference: " + difference(x1, x) + "\n");
        this.file.write("diff from real min: " + difference(x1, real) + "\n\n");
    }

    @Override
    public double[] minimise(double[] x0) throws IOException{
        double[] x1 = Arrays.copyOf(x0, 3);
        double[] x;

        do {
            x = Arrays.copyOf(x1, 3);
            double step = this.getStep(x);
            for (int i = 0; i < x.length; i++) {
                x1[i] = x[i] - step * dF.get(i).calc(x[0], x[1], x[2]);
            }
            this.printData(x1, x);
        } while (this.difference(x1, x) > eps);
        return x1;
    }
}
