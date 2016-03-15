package wildtornado.databug.objects;

public class Preference {

    private int product;
    private double rating;

    public Preference(int product, double rating) {
        this.product = product;
        this.rating = rating;
    }

    public int getProduct() {
        return product;
    }

    public double getRating() {
        return rating;
    }

}
