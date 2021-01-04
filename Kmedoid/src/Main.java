import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String file = "Dataset/data.csv";
        System.out.println("ini type " + file.getClass().getName());

        List<List<String>> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String[] finalValues = new String[2];

                // Menghilangkan index pertama pada values
                for (int i = 0; i < finalValues.length; i++) {
                    finalValues[i] = values[i + 1];
                }
                // System.out.println(Arrays.toString(values));
                data.add(Arrays.asList(finalValues));
            }
            data.remove(0);
            System.out.println(data);

            int s = data.size();
            int k = 2;
//            List<List<String>> medoids = new ArrayList<>();
//
//            for (int i = 0; i < k; i++) {
//                int medoid = (int) (Math.random() * data.size());
//                medoids.add(data.get(medoid));
//            }
            //System.out.println("Random medoid " + data.get(medoids));
            // System.out.println("Ini arralist medoid : " + medoids);
//            System.out.println(medoids.get(0).get(0));
//            System.out.println(medoids.get(0).get(1));
//            System.out.println(medoids.get(1).get(0));
//            System.out.println(medoids.get(1).get(1));
//
//            double x1 = Double.parseDouble(medoids.get(0).get(0));
//            double x2 = Double.parseDouble(medoids.get(0).get(1));
//            double y1 = Double.parseDouble(medoids.get(1).get(0));
//            double y2 = Double.parseDouble(medoids.get(1).get(1));
//
//            System.out.println(euclidean_distance(x1, x2, y1, y2));

            System.out.println("===============================");
            List<Double> finalCluster = iterMedoid(s, k, data).get(1);
            System.out.println(finalCluster);

//            List<List<Double>> medoids = calculateDistance(2, data);
//            System.out.println("medoids " + medoids);
//
//            System.out.println("===============================");
//
//            System.out.println(minMedoidDistance(s, medoids));


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static double euclidean_distance(double x1, double x2, double y1, double y2) {
        double p = Math.pow((y2 - x2), 2);
        double q = Math.pow((y1 - x1), 2);
        double distance = Math.sqrt(p + q);
        return distance;
    }

    public static List<List<Double>> calculateDistance(int k, List<List<String>> data) {
        List<List<Double>> df = new ArrayList<>();

        List<List<String>> medoids = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            int medoid = (int) (Math.random() * data.size());
            medoids.add(data.get(medoid));
        }

        System.out.println(medoids);

        for (int i = 0; i < medoids.size() ; i++) {
            List<String> medoid = medoids.get(i);
            ArrayList<Double> distancePoint = new ArrayList<>();
            for (int j = 0; j < data.size() ; j++) {
                List<String> point = data.get(j);

                double a1 = Double.parseDouble(medoid.get(0));
                double a2 = Double.parseDouble(medoid.get(1));

                double b1 = Double.parseDouble(point.get(0));
                double b2 = Double.parseDouble(point.get(1));

                double distance = euclidean_distance(a1, a2, b1, b2);
                distancePoint.add(distance);
            }

            df.add(distancePoint);
        }
        return df;
    }

    public static List<List<Double>> minMedoidDistance(int s, List<List<Double>> medoids) {
        List<List<Double>> newArray = new ArrayList<>();

        for (int i =  0; i < s; i++) {
            List<Double> item = new ArrayList<>();
            for (int j = 0; j < medoids.size(); j++) {
                item.add(medoids.get(j).get(i));
            }
            newArray.add(item);
        }

        ArrayList<Double> minMedoids = new ArrayList<>();
        ArrayList<Double> cluster = new ArrayList<>();
        for (int i = 0; i < newArray.size(); i++) {
            List<Double> temp = newArray.get(i);
            double minDistance = Collections.min(temp);
            double minIndex = temp.indexOf(Collections.min(temp));
            System.out.println(newArray.get(i).get(0) + " - " + newArray.get(i).get(1));
            minMedoids.add(minDistance);
            cluster.add(minIndex);
        }

        List<List<Double>> result = new ArrayList<>();
        result.add(minMedoids);
        result.add(cluster);
        return result;
    }

    public static List<List<Double>> iterMedoid(int s, int k, List<List<String>> data) {
        double check = -1;
        int iterasi = 0;

        List<List<Double>> medoids = calculateDistance(2, data);
        List<List<Double>> minMedoid = minMedoidDistance(s, medoids);

        while (check < 0) {
            List<List<Double>> medoids2 = calculateDistance(2, data);
            List<List<Double>> minMedoid2 = minMedoidDistance(s, medoids2);

            double sumDistance1 = 0;
            for (double i : minMedoid.get(0)) {
                sumDistance1 += i;
            }

            double sumDistance2 = 0;
            for (double i : minMedoid2.get(0)) {
                sumDistance2 += i;
            }

            check = sumDistance2 - sumDistance1;
            System.out.println("Iterasi " + iterasi + " : " + sumDistance2 + " - " + sumDistance1 + " = " + check);

            minMedoid = minMedoid2;
            iterasi++;
        }

        return minMedoid;

    }

}