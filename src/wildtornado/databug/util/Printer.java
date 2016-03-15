package wildtornado.databug.util;

import wildtornado.databug.objects.Distance;
import wildtornado.databug.objects.Prediction;

import java.util.List;

public class Printer {

    public static void printNeighbours(List<Distance> distances, String type) {
        System.out.println("printing all " + type + " neighbours.");
        for (Distance distance : distances) {
            printDistance(distance.getUserID(), distance.getDistance(), distance.getMatchingItems(), distance.getHasAdditionalItems());
        }
        end();
    }

    public static void printxNeighbours(List<Distance> distances, int x, String type) {
        System.out.println("Printing the " + x + " closest " + type + " neighbours.");
        for (Distance distance : distances) {
            printDistance(distance.getUserID(), distance.getDistance(), distance.getMatchingItems(), distance.getHasAdditionalItems());
        }
        end();
    }

    public static void printTresholdNeighbours(List<Distance> distances, double treshold, String type) {
        System.out.println("Printing all " + type + " neighbours with in " + treshold + " treshold.");
        for (Distance distance : distances) {
            printDistance(distance.getUserID(), distance.getDistance(), distance.getMatchingItems(), distance.getHasAdditionalItems());
        }
        end();
    }

    private static void printDistance(int user, double distance, int matches, boolean rated) {
        System.out.println(user + ": " + distance + " (" + matches + ")" + " (has additional items rated: " + rated + ")");
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
            System.out.println("User: " + p.getUserID() + ", product: " + p.getProduct() + ", rating: " + p.getRating());
        }
    }

    private static void end() {
        System.out.println();
    }
}
