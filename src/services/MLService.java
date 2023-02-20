package services;

import entity.CompteDataScience;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@WebService(name = "MLWS")
public class MLService {

  @WebMethod(operationName = "getComptes")
  public List<CompteDataScience> getComptes() {
    List<CompteDataScience> cptes = new ArrayList<>();
    cptes.add(new CompteDataScience(1,"Nils","boby"));
    cptes.add(new CompteDataScience(2, "Doe","John"));
    return cptes;
  }

  @WebMethod(operationName = "getMoy")
  public double getMoy(double[] list) {
    double sum = 0;
    for (double number : list) {
      sum += number;
    }
    return sum/list.length;
  }

  @WebMethod(operationName = "getDataKMeans")
  public int[] getDataKMeans(double[][] data) {
    // Définir le nombre de clusters
    int nbClusters = 3;

    // Initialiser aléatoirement les centroids
    Random rand = new Random();
    List<double[]> centroids = new ArrayList<>();
    for (int i = 0; i < nbClusters; i++) {
      int randIndex = rand.nextInt(data.length);
      centroids.add(data[randIndex]);
    }

    // Effectuer le clustering nbClusters-means
    int[] clusters = new int[data.length];
    for (int iteration = 0; iteration < 100; iteration++) {
      // Affecter chaque point de données au centroid le plus proche
      for (int i = 0; i < data.length; i++) {
        double[] point = data[i];
        double minDist = Double.MAX_VALUE;
        int minIndex = -1;
        for (int j = 0; j < centroids.size(); j++) {
          double dist = distance(point, centroids.get(j));
          if (dist < minDist) {
            minDist = dist;
            minIndex = j;
          }
        }
        clusters[i] = minIndex;
      }

      // Mettre à jour les centroids en fonction des points de données assignés
      for (int i = 0; i < centroids.size(); i++) {
        double[] newCentroid = new double[data[0].length];
        int count = 0;
        for (int j = 0; j < data.length; j++) {
          if (clusters[j] == i) {
            count++;
            for (int d = 0; d < data[j].length; d++) {
              newCentroid[d] += data[j][d];
            }
          }
        }
        if (count > 0) {
          for (int d = 0; d < newCentroid.length; d++) {
            newCentroid[d] /= count;
          }
          centroids.set(i, newCentroid);
        }
      }
    }

    return clusters;
  }

  // méthode de calcul de la sitance euclidienne
  public static double distance(double[] a, double[] b) {
    double somme = 0;
    for (int i = 0; i < a.length; i++) {
      somme += Math.pow(a[i] - b[i], 2);
    }
    return Math.sqrt(somme);
  }
}
