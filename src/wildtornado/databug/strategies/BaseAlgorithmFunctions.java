package wildtornado.databug.strategies;

import wildtornado.databug.storage.UserHashMap;
import wildtornado.databug.constants.Constants;
import wildtornado.databug.objects.Distance;
import wildtornado.databug.objects.Preference;
import wildtornado.databug.util.Printer;

import java.util.*;

public abstract class BaseAlgorithmFunctions implements Algorithm {

    protected String algorithmName;

    protected UserHashMap userHashMap;
    protected int currentUser;
    protected List<Distance> distances;
    protected List<Distance> nearestxNeighbours;
    protected double threshold;
    protected List<Distance> nearestThresholdNeighbours;

    protected boolean sorted = false;
    protected int sortMethod = Constants.UNKNOWN;

    public void run(int neighbours, double threshold) {
        generateDistances(userHashMap, currentUser, userHashMap.getSingleUserValues(currentUser));
        sortDistances();
        printNeighbours();
        generatexNeighbours(neighbours);
        printxNeighbours();
        generateThresholdNeighbours(threshold);
        printThresholdNeighbours();
    }

    public void generateDistances(UserHashMap userHashMap, int num, List<Preference> currentUserValues) {
        this.sorted = false;
        List<Distance> distances = new ArrayList<Distance>();
        Set set = userHashMap.get().entrySet();
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

    protected Distance calculateDistance(List<Preference> userOne, List<Preference> userTwo, int comparedUserID) {
        return null;
    }

    public List<Distance> getDistances() {
        return this.distances;
    }

    protected boolean checkIfUserHasAdditionalItem(List<Preference> userOne, List<Preference> userTwo) {
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
            this.sorted = true;
            Collections.sort(this.distances);
        }
    }

    public boolean isSorted() {
        return this.sorted;
    }

    public void printNeighbours() {
        if (this.sorted) {
            Printer.printNeighbours(this.distances, algorithmName);
        } else {
            Printer.unsortedWarning();
        }
    }

    //This function assumes the set has been sorted.
    public void generatexNeighbours(int neighbourAmount) {
        if (this.sorted) {
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
        } else {
            Printer.unsortedWarning();
        }
    }

    public List<Distance> getxNeighbours() {
        return this.nearestxNeighbours;
    }

    public void printxNeighbours() {
        Printer.printxNeighbours(this.nearestxNeighbours, algorithmName);
    }

    //This function assumes the set has been sorted.
    public void generateThresholdNeighbours(double threshold) {
        if (this.sorted) {
            nearestThresholdNeighbours = new ArrayList<Distance>();
            Distance min = distances.get(0);
            this.threshold = threshold;
            for (Distance distance : distances) {
                if (this.sortMethod == Constants.ASC) {
                    if (distance.getDistance() <= min.getDistance() * (1 + threshold) && distance.getHasAdditionalItems()) {
                        nearestThresholdNeighbours.add(distance);
                    } else {
                        break;
                    }
                }
                if (this.sortMethod == Constants.DESC) {
                    if (distance.getDistance() >= min.getDistance() * (1 - threshold) && distance.getHasAdditionalItems()) {
                        nearestThresholdNeighbours.add(distance);
                    } else {
                        break;
                    }
                }
            }
        } else {
            Printer.unsortedWarning();
        }
    }

    public List<Distance> getThresholdNeighbours() {
        return this.nearestThresholdNeighbours;
    }

    public void printThresholdNeighbours() {
        Printer.printThresholdNeighbours(this.nearestThresholdNeighbours, this.threshold, algorithmName);
    }
}
