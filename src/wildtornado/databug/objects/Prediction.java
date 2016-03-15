package wildtornado.databug.objects;

public class Prediction {

    private int userID;
    private int product;
    private double rating;

    public Prediction(int userID, int product, double rating) {
        this.userID = userID;
        this.product = product;
        this.rating = rating;
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
}
