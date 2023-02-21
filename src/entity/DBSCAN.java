package entity;

import java.util.ArrayList;
import java.util.Arrays;

public class DBSCAN {
  private ArrayList<Point> points;
  private int num_clusters;
  private double eps;
  private int minPts;
  private int[] labels;

  public DBSCAN(ArrayList<Point> points, double eps, int minPts) {
    this.points = points;
    this.num_clusters = 0;
    this.eps = eps;
    this.minPts = minPts;
    this.labels = new int[points.size()];
    Arrays.fill(labels, -1);
  }

  public int[] fit() {
    for (int i = 0; i < points.size(); i++) {
      Point p = points.get(i);
      if (labels[i] != -1) continue;
      ArrayList<Point> neighbors = rangeQuery(p);
      if (neighbors.size() < minPts) {
        labels[i] = 0;
        continue;
      }
      num_clusters++;
      expandCluster(i, neighbors);
    }
    return labels;
  }

  private void expandCluster(int i, ArrayList<Point> neighbors) {
    labels[i] = num_clusters;
    for (int j = 0; j < neighbors.size(); j++) {
      Point p = neighbors.get(j);
      int idx = points.indexOf(p);
      if (labels[idx] == -1) {
        ArrayList<Point> new_neighbors = rangeQuery(p);
        if (new_neighbors.size() >= minPts) {
          neighbors.addAll(new_neighbors);
        }
      }
      if (labels[idx] == 0) {
        labels[idx] = num_clusters;
      }
    }
  }

  private ArrayList<Point> rangeQuery(Point p) {
    ArrayList<Point> neighbors = new ArrayList<>();
    for (Point q : points) {
      if (p.distanceTo(q) <= eps) {
        neighbors.add(q);
      }
    }
    return neighbors;
  }
}
