package wildtornado.databug.strategies;

import wildtornado.databug.UserTreeMap;
import wildtornado.databug.objects.Distance;
import wildtornado.databug.objects.Preference;
import wildtornado.databug.util.Printer;

import java.util.*;

public class Pearson implements Algorithm {

    private List<Distance> distances;
    private int neighbourAmount;
    private List<Distance> nearestxNeighbours;
    private double treshold;
    private List<Distance> nearestTresholdNeighbours;

    public void generateDistances(UserTreeMap t, int num, List<Preference> currentUserValues) {
        List<Distance> distances = new ArrayList<Distance>();
        Set set = t.get().entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            int selectedUserId = (Integer) me.getKey();
            if (selectedUserId != num) {
                List<Preference> selectedUserValues = (List<Preference>) me.getValue();
                distances.add(calculateDistance(currentUserValues, selectedUserValues, selectedUserId));
            }

        }
        this.distances = distances;
    }

    public List<Distance> getDistances() {
        return this.distances;
    }

    private Distance calculateDistance(List<Preference> userOne, List<Preference> userTwo, int comparedUserID) {
        double sumOfRatingsTimesRatings = 0;
        double sumUserOneRatings = 0, sumUserOneRatingsSquare = 0;
        double sumUserTwoRatings = 0, sumUserTwoRatingsSquare = 0;
        int matches = 0;
        for (Preference prefOne : userOne) {
            for (Preference prefTwo : userTwo) {
                if (prefOne.getProduct() == prefTwo.getProduct()) {
                    sumOfRatingsTimesRatings += prefOne.getRating() * prefTwo.getRating();
                    sumUserOneRatings += prefOne.getRating();
                    sumUserOneRatingsSquare += Math.pow(prefOne.getRating(), 2);
                    sumUserTwoRatings += prefTwo.getRating();
                    sumUserTwoRatingsSquare += Math.pow(prefTwo.getRating(), 2);
                    matches++;
                    break;
                }
            }
        }
        double topOfEquation = sumOfRatingsTimesRatings - ((sumUserOneRatings * sumUserTwoRatings) / matches);
        double bottomOfEquation = Math.sqrt(sumUserOneRatingsSquare - (Math.pow(sumUserOneRatings, 2) / matches)) * Math.sqrt(sumUserTwoRatingsSquare - (Math.pow(sumUserTwoRatings, 2)) / matches);
        double distance = topOfEquation / bottomOfEquation;
        return new Distance(comparedUserID, distance, matches, checkIfUserHasAdditionalItem(userOne, userTwo));
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
        if (this.distances.size() > 0) {
            Collections.sort(this.distances, new Comparator<Distance>() {
                @Override
                public int compare(Distance o1, Distance o2) {
                    if (o1.getDistance() > o2.getDistance()) return -1;
                    if (o1.getDistance() < o1.getDistance()) return 1;
                    return 0;
                }
            });
        }
    }

    public void printNeighbours() {
        Printer.printNeighbours(this.distances, "pearson");
    }

    //This function assumes the set has been sorted.
    public void generatexNeighbours(int neighbourAmount) {
        nearestxNeighbours = new ArrayList<Distance>();
        this.neighbourAmount = neighbourAmount;
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

    public List<Distance> getxNeighbours() {
        return this.nearestxNeighbours;
    }

    public void printxNeighbours() {
        Printer.printxNeighbours(this.nearestxNeighbours, this.neighbourAmount, "pearson");
    }

    public void generateTresholdNeighbours(double treshold) {
        nearestTresholdNeighbours = new ArrayList<Distance>();
        Distance min = distances.get(0);
        this.treshold = treshold;
        for (Distance distance : distances) {
            if (distance.getDistance() >= min.getDistance() * 1 - treshold && distance.getHasAdditionalItems()) {
                nearestTresholdNeighbours.add(distance);
            } else {
                break;
            }
        }
    }

    public List<Distance> getTresholdNeighbours() {
        return this.nearestTresholdNeighbours;
    }

    public void printTresholdNeighbours() {
        Printer.printTresholdNeighbours(this.nearestTresholdNeighbours, this.treshold, "pearson");
    }
}
