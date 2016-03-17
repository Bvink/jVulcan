package wildtornado.databug.strategies;


import wildtornado.databug.UserTreeMap;
import wildtornado.databug.objects.Distance;
import wildtornado.databug.objects.Preference;
import wildtornado.databug.util.Printer;

import java.util.*;

public class Cosine implements Algorithm {

    private List<Distance> distances;
    private List<Distance> nearestxNeighbours;
    private double treshold;
    private List<Distance> nearestTresholdNeighbours;

    private boolean sorted = false;

    public void generateDistances(UserTreeMap t, int num, List<Preference> currentUserValues) {
        this.sorted = false;
        List<Distance> distances = new ArrayList<Distance>();
        Set set = t.get().entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            int selectedUserId = (Integer) me.getKey();
            if (selectedUserId != num) {
                List<Preference> selectedUserValues = (List<Preference>) me.getValue();
                Distance d = calculateDistance(currentUserValues, selectedUserValues, selectedUserId);
                if (d != null) {
                    distances.add(calculateDistance(currentUserValues, selectedUserValues, selectedUserId));
                }
            }

        }
        this.distances = distances;
    }

    public List<Distance> getDistances() {
        return this.distances;
    }

    private Distance calculateDistance(List<Preference> userOne, List<Preference> userTwo, int comparedUserID) {
        double sumOfRatingsTimesRatings = 0;
        double sumUserOneRatingsSquare = 0;
        double sumUserTwoRatingsSquare = 0;
        int count = 0;
        int matches = 0;
        int matchingProducts = 0;
        for (Preference prefOne : userOne) {
            for (Preference prefTwo : userTwo) {
                if (prefOne.getProduct() == prefTwo.getProduct()) {
                    sumOfRatingsTimesRatings += prefOne.getRating() * prefTwo.getRating();
                    sumUserOneRatingsSquare += Math.pow(prefOne.getRating(), 2);
                    sumUserTwoRatingsSquare += Math.pow(prefTwo.getRating(), 2);
                    matches++;
                    matchingProducts++;
                    break;
                }
            }
            count++;
            if (count > matches) {
                sumOfRatingsTimesRatings += prefOne.getRating() * 0;
                sumUserOneRatingsSquare += Math.pow(prefOne.getRating(), 2);
                sumUserTwoRatingsSquare += Math.pow(0, 2);
                matches++;
            }
        }
        double distance = sumOfRatingsTimesRatings / (Math.sqrt(sumUserOneRatingsSquare) * Math.sqrt(sumUserTwoRatingsSquare));
        return (matches > 0 && !Double.isNaN(distance)) ? new Distance(comparedUserID, distance, matchingProducts, checkIfUserHasAdditionalItem(userOne, userTwo)) : null;
    }

    private boolean checkIfUserHasAdditionalItem(List<Preference> userOne, List<Preference> userTwo) {
        int count = 0;
        for (Preference prefTwo : userTwo) {
            for (Preference prefOne : userOne) {
                if (prefTwo.getProduct() == prefOne.getProduct()) {
                    count++;
                }
            }
        }
        return count < userTwo.size();
    }

    public void sortDistances() {
        this.sorted = true;
        if (this.distances.size() > 0) {
            Collections.sort(this.distances, new Comparator<Distance>() {
                @Override
                public int compare(Distance o1, Distance o2) {
                    if (o1.getDistance() > o2.getDistance()) return -1;
                    if (o1.getDistance() < o2.getDistance()) return 1;
                    return 0;
                }
            });
        }
    }

    public boolean isSorted() {
        return this.sorted;
    }

    public void printNeighbours() {
        Printer.unsortedWarning(sorted);
        Printer.printNeighbours(this.distances, "cosine");
    }

    //This function assumes the set has been sorted.
    public void generatexNeighbours(int neighbourAmount) {
        if (Printer.unsortedWarning(this.sorted)) {
            nearestxNeighbours = new ArrayList<Distance>();
            if (neighbourAmount > this.distances.size()) {
                neighbourAmount = this.distances.size();
                System.out.println("x too large.");
            }
            for (int i = 0; i < neighbourAmount; i++) {
                if (this.distances.get(i).getHasAdditionalItems()) {
                    nearestxNeighbours.add(this.distances.get(i));
                } else {
                    neighbourAmount++;
                    if (neighbourAmount > this.distances.size()) {
                        break;
                    }
                }
            }
        }
    }

    public List<Distance> getxNeighbours() {
        return this.nearestxNeighbours;
    }

    public void printxNeighbours() {
        Printer.printxNeighbours(this.nearestxNeighbours, "cosine");
    }

    //This function assumes the set has been sorted.
    public void generateTresholdNeighbours(double treshold) {
        if (Printer.unsortedWarning(this.sorted)) {
            nearestTresholdNeighbours = new ArrayList<Distance>();
            Distance min = this.distances.get(0);
            this.treshold = treshold;
            for (Distance distance : this.distances) {
                if (distance.getDistance() >= min.getDistance() * 1 - treshold && distance.getHasAdditionalItems()) {
                    nearestTresholdNeighbours.add(distance);
                } else {
                    break;
                }
            }
        }
    }

    public List<Distance> getTresholdNeighbours() {
        return this.nearestTresholdNeighbours;
    }

    public void printTresholdNeighbours() {
        Printer.printTresholdNeighbours(this.nearestTresholdNeighbours, this.treshold, "cosine");
    }
}
