package wildtornado.databug.objects;

public class Predictor {

    private int userID;
    private int product;
    private double rating;
    private double distance;

    public Predictor(int userID, int product, double rating, double distance) {
        this.userID = userID;
        this.product = product;
        this.rating = rating;
        this.distance = distance;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
