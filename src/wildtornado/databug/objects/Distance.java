package wildtornado.databug.objects;

import wildtornado.databug.constants.Constants;

public class Distance implements Comparable {

    private int userID;
    private double distance;
    private int matchingItems;
    private boolean hasAdditionalItems;
    private int sortMethod;

    public Distance(int userID, double distance, int matchingItems, boolean hasAdditionalItems, int sortMethod) {
        this.userID = userID;
        this.distance = distance;
        this.matchingItems = matchingItems;
        this.hasAdditionalItems = hasAdditionalItems;
        this.sortMethod = sortMethod;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getMatchingItems() {
        return matchingItems;
    }

    public void setMatchingItems(int matchingItems) {
        this.matchingItems = matchingItems;
    }

    public boolean getHasAdditionalItems() {
        return hasAdditionalItems;
    }

    public void setHasAdditionalItems(boolean hasAdditionalItems) {
        this.hasAdditionalItems = hasAdditionalItems;
    }

    public int getSortMethod() {
        return sortMethod;
    }

    public void setSortMethod(int sortMethod) {
        this.sortMethod = sortMethod;
    }

    @Override
    public int compareTo(Object o) {
        Distance that = (Distance) o;
        if (this.getDistance() < that.getDistance()) return -sortMethod;
        if (this.getDistance() > that.getDistance()) return sortMethod;
        return Constants.UNKNOWN;
    }
}
