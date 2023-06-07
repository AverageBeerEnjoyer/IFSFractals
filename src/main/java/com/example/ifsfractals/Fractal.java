package com.example.ifsfractals;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Fractal {
    private double[][] A;
    private List<Dot> dots;

    public Fractal(File file) {
        parse(file);
        dots = new ArrayList<>();
        countData();
    }

    public List<Dot> getDots() {
        return dots;
    }

    private void countData() {
        Queue<Dot> queue = new ArrayDeque<>();
        Dot startDot = new Dot(0, 0);
        queue.add(startDot);
        while (dots.size() < 500000) {
            Dot dot = queue.remove();
            List<Dot> nextDots = countNextDots(dot);
            for (int i = 0; i < A.length; ++i) {
                dots.add(nextDots.get(i));
                queue.add(nextDots.get(i));
            }
        }
    }

    private List<Dot> countNextDots(Dot dot) {
        List<Dot> res = new ArrayList<>();
        for (int i = 0; i < A.length; ++i) {
            res.add(apply(i, dot));
        }
        return res;
    }

    private Dot apply(int i, Dot dot) {
        double x, y;
        x = A[i][0] * dot.x() + A[i][1] * dot.y() + A[i][4];
        y = A[i][2] * dot.x() + A[i][3] * dot.y() + A[i][5];
        return new Dot(x, y);
    }

    private void parse(File file) {
        try {
            Scanner scanner = new Scanner(file);
            int n = scanner.nextInt();
            double[][] funcs = new double[n][6];
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < 6; ++j) {
                    funcs[i][j] = scanner.nextDouble();
                }
            }
            A = funcs;
        } catch (FileNotFoundException e) {
            System.out.println("file error");
        }
    }
}
