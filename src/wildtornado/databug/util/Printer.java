package wildtornado.databug.util;

import wildtornado.databug.objects.Distance;
import wildtornado.databug.objects.Prediction;

import java.text.DecimalFormat;
import java.util.List;

public class Printer {

    private static final DecimalFormat df = new DecimalFormat("0.00000");

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

    public static void printTresholdNeighbours(List<Distance> distances, double treshold, String type) {
        if (distances != null) {
            System.out.println("Printing all " + type + " neighbours with in " + treshold + " treshold.");
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
        for(int p : products) {
            System.out.println(p);
        }
        end();
    }

    public static void printRateableProducts(List<Integer> products, int currentUser) {
        System.out.println("Printing the products available to be rated for user: " + currentUser);
        for(int product : products) {
            System.out.println(product);
        }
        end();
    }


    public static void printPredictions(List<Prediction> predictions) {
        System.out.println("Printing the predictions:");
        for(Prediction p : predictions) {
            System.out.println("User: " + p.getUserID() + ", product: " + p.getProduct() + ", rating: " + df.format(p.getRating()));
        }
        end();
    }

    public static void printnPredictions(List<Prediction> predictions, int n) {
        System.out.println("Printing the top " + n + " predictions:");
        for(int i = 0; i < n; i++) {
            System.out.println("User: " + predictions.get(i).getUserID() + ", product: " + predictions.get(i).getProduct() + ", rating: " + df.format(predictions.get(i).getRating()));
        }
        end();
    }

    public static boolean unsortedWarning(boolean val) {
        if (!val) {
            System.out.println("Warning, the dataset is unsorted!");
            end();
        }
        return val;
    }

    private static void end() {
        System.out.println();
    }
}
