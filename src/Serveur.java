import services.MLService;
import services.MLServiceStatic;

import javax.xml.ws.Endpoint;

public class Serveur {
  public static void main(String[] args) {
    String url = "http://localhost:8585/";
    Endpoint.publish(url, new MLService());

    double[][] data = {{1, 2}, {2, 3}, {8, 7}, {2, 4}, {10, 12}, {9, 10}, {11, 13}, {13, 11}, {14, 12}, {13, 13}};
    MLServiceStatic.getDataKMeansStatic(data, 3);

    MLServiceStatic.getDataDBScanStatic(data, 0.5, 100);
  }
}
