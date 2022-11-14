package com.example.cmcourse.services;

import com.example.cmcourse.data.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterpolationService {
  private final ThreadLocal<List<Point>> threadLocalPoints = new ThreadLocal<>();

  public double newtonFormula(List<Point> points, double x) {
    if (threadLocalPoints.get() == null || !threadLocalPoints.get().equals(points)) {
      threadLocalPoints.set(points);
    }
    double[][] finalDifferences = generateFinalDifferences(points);
    double q = (x - points.get(0).getX()) / (points.get(1).getX() - points.get(0).getX());
    double mult = 1;
    double factorial = 1;
    double sum = points.get(0).getY();
    for (int i = 0; i < finalDifferences.length; i++) {
      mult *= (q - i);
      factorial *= (i + 1);
      sum += mult * finalDifferences[0][i] / factorial;
    }
    return sum;
  }

  private double[][] generateFinalDifferences(List<Point> points) {
    double[][] finalDifferences = new double[points.size() - 1][points.size() - 1];
    for (int i = 0; i < finalDifferences.length; i++) {
      finalDifferences[i][0] = points.get(i + 1).getY() - points.get(i).getY();
    }
    for (int i = 1; i < finalDifferences.length; i++) {
      for (int j = 0; j < finalDifferences.length - i; j++) {
        finalDifferences[j][i] = finalDifferences[j + 1][i - 1] - finalDifferences[j][i - 1];
      }
    }
    return finalDifferences;
  }
}
