package wildtornado.databug.util;

import wildtornado.databug.objects.Distance;
import wildtornado.databug.objects.Prediction;
import wildtornado.databug.objects.Preference;

import java.text.DecimalFormat;
import java.util.*;

public class Printer {

    private static final DecimalFormat df = new DecimalFormat("0.00000");

    @SuppressWarnings("unchecked")
    public static void printUserPreferenceData(Set set) {
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            System.out.print(me.getKey() + ": ");
            List<Preference> p = (List<Preference>) me.getValue();
            for (Preference pref : p) {
                System.out.print(pref.getProduct() + " " + pref.getRating() + ", ");
            }
            end();
        }
        end();
    }

    public static void printNeighbours(List<Distance> distances, String type) {
        System.out.println("printing all " + type + " neighbours.");
        for (Distance distance : distances) {
            printDistance(distance.getUserID(), distance.getDistance(), distance.getMatchingItems(), distance.getHasAdditionalItems());
        }
        end();
    }

    public static void printxNeighbours(List<Distance> distances, String type) {
        if (distances != null) {
            System.out.println("Printing the " + distances.size() + " closest " + type + " neighbours.");
            for (Distance distance : distances) {
                printDistance(distance.getUserID(), distance.getDistance(), distance.getMatchingItems(), distance.getHasAdditionalItems());
            }
            end();
        }
    }

    public static void printThresholdNeighbours(List<Distance> distances, double threshold, String type) {
        if (distances != null) {
            System.out.println("Printing all " + type + " neighbours with in " + threshold + " threshold.");
            for (Distance distance : distances) {
                printDistance(distance.getUserID(), distance.getDistance(), distance.getMatchingItems(), distance.getHasAdditionalItems());
            }
            end();
        }
    }

    private static void printDistance(int user, double distance, int matches, boolean rated) {
        System.out.println(user + ": " + df.format(distance) + " (" + matches + ")" + " (has additional items rated: " + rated + ")");
    }

    public static void printRatedProducts(List<Integer> products, int user) {
        System.out.println("Printing the products rated for user: " + user);
        for (int p : products) {
            System.out.println(p);
        }
        end();
    }

    public static void printUserRatings(List<Preference> preferences, int user) {
        System.out.println("Printing the product ratings for user: " + user);
        for (Preference p : preferences) {
            System.out.println("Product: " + p.getProduct() + ", Rating: " + p.getRating());
        }
        end();
    }

    public static void printRateableProducts(List<Integer> products, int currentUser) {
        System.out.println("Printing the products available to be rated for user: " + currentUser);
        for (int product : products) {
            System.out.println(product);
        }
        end();
    }


    public static void printPredictions(List<Prediction> predictions) {
        System.out.println("Printing the predictions:");
        for (Prediction p : predictions) {
            System.out.println("User: " + p.getUserID() + ", product: " + p.getProduct() + ", rating: " + df.format(p.getRating()) + ", Rated by " + p.getRatedBy() + " other users.");
        }
        end();
    }

    public static void printnPredictions(List<Prediction> predictions, int n) {
        System.out.println("Printing the top " + n + " predictions:");
        for (int i = 0; i < n; i++) {
            System.out.println("User: " + predictions.get(i).getUserID() + ", product: " + predictions.get(i).getProduct() + ", rating: " + df.format(predictions.get(i).getRating()) + ", Rated by " + predictions.get(i).getRatedBy() + " other users.");
        }
        end();
    }

    public static void unsortedWarning() {
        System.out.println("Warning, the dataset is unsorted!");
        end();
    }

    public static void printDevMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static void printFreqMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    private static void end() {
        System.out.println();
    }
}
