package wildtornado.databug.objects;

public class Distance {

    private int userID;
    private double distance;
    private int matchingItems;
    private boolean hasAdditionalItems;

    public Distance(int userID, double distance, int matchingItems, boolean hasAdditionalItems) {
        this.userID = userID;
        this.distance = distance;
        this.matchingItems = matchingItems;
        this.hasAdditionalItems = hasAdditionalItems;
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
}
