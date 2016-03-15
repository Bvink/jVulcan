package wildtornado.databug.util;

import wildtornado.databug.objects.Distance;

import java.util.List;

public class Printer {

    public static void printNeighbours(List<Distance> distances, String type) {
        System.out.println("printing all " + type + " neighbours.");
        for (Distance distance : distances) {
            print(distance.getUserID(), distance.getDistance(), distance.getMatchingItems(), distance.getHasAdditionalItems());
        }
        end();
    }

    public static void printxNeighbours(List<Distance> distances, int x, String type) {
        System.out.println("Printing the " + x + " closest " + type + " neighbours.");
        for (Distance distance : distances) {
            print(distance.getUserID(), distance.getDistance(), distance.getMatchingItems(), distance.getHasAdditionalItems());
        }
        end();
    }

    public static void printTresholdNeighbours(List<Distance> distances, double treshold, String type) {
        System.out.println("Printing all " + type + " neighbours with in " + treshold + " treshold.");
        for (Distance distance : distances) {
            print(distance.getUserID(), distance.getDistance(), distance.getMatchingItems(), distance.getHasAdditionalItems());
        }
        end();
    }

    private static void print(int user, double distance, int matches, boolean rated) {
        System.out.println(user + ": " + distance + " (" + matches + ")" + " (has additional items rated: " + rated + ")");
    }

    private static void end() {
        System.out.println();
    }
}
