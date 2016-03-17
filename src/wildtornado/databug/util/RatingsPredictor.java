package wildtornado.databug.util;

import wildtornado.databug.UserTreeMap;
import wildtornado.databug.objects.Distance;
import wildtornado.databug.objects.Prediction;
import wildtornado.databug.objects.Predictor;
import wildtornado.databug.objects.Preference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RatingsPredictor {

    private List<Distance> neighbours;
    private UserTreeMap userTreeMap;
    private List<Integer> rateableProducts;
    private List<Prediction> predictions;
    private int currentUser;

    public void setNeighbours(List<Distance> neighbours) {
        this.neighbours = neighbours;
    }

    public void setUserTreeMap(UserTreeMap userTreeMap) {
        this.userTreeMap = userTreeMap;
    }

    public void setCurrentUser(int currentUser) {
        this.currentUser = currentUser;
    }

    public List<Integer> getRatedProducts(int user) {
        List<Integer> ratedProducts = new ArrayList<Integer>();
        List<Preference> products = userTreeMap.getSingleUserValues(user);
        for (Preference p : products) {
            ratedProducts.add(p.getProduct());
        }
        return ratedProducts;
    }

    public void printRatedProducts(int user) {
        Printer.printRatedProducts(getRatedProducts(user), user);
    }

    public List<Preference> getUserRatings(int user) {
        return userTreeMap.getSingleUserValues(user);
    }

    public void generateRateableProducts() {
        List<Integer> rateableProducts = new ArrayList<Integer>();
        List<Integer> currentUserRatedProducts = getRatedProducts(currentUser);
        for (Distance user : this.neighbours) {
            List<Integer> neighbourRatedProducts = getRatedProducts(user.getUserID());
            for (int product : neighbourRatedProducts) {
                if (!rateableProducts.contains(product) && !currentUserRatedProducts.contains(product)) {
                    rateableProducts.add(product);
                }
            }
        }
        this.rateableProducts = rateableProducts;
    }

    public void printRateableProducts() {
        Printer.printRateableProducts(this.rateableProducts, this.currentUser);
    }

    private double getSingleRating(int user, int productNumber) {
        List<Preference> currentUserRatings = getUserRatings(user);
        for (Preference p : currentUserRatings) {
            if (p.getProduct() == productNumber) {
                return p.getRating();
            }
        }
        return 0;
    }

    public void generatePredictions() {
        List<Prediction> predictions = new ArrayList<Prediction>();
        for (int product : this.rateableProducts) {
            predictions.add(predictSingleProductRating(getPredictors(product), product));
        }
        this.predictions = predictions;
    }

    public void printPredictions() {
        Printer.printPredictions(this.predictions);
    }

    public void printnPredictions(int n) {
        if (n > predictions.size()) {
            n = predictions.size();
        }
        Printer.printnPredictions(this.predictions, n);
    }

    private Prediction predictSingleProductRating(List<Predictor> predictors, int product) {
        double distance = getDistanceTotal();
        double rating = 0;
        for (Predictor p : predictors) {
            rating += (p.getDistance() / distance) * p.getRating();
        }
        return new Prediction(currentUser, product, rating);
    }

    public double getSingleProductPrediction(int product) {
        return predictSingleProductRating(getPredictors(product), product).getRating();
    }

    private List<Predictor> getPredictors(int product) {
        List<Predictor> predictors = new ArrayList<Predictor>();
        for (Distance neighbour : this.neighbours) {
            predictors.add(new Predictor(neighbour.getUserID(), product, getSingleRating(neighbour.getUserID(), product), neighbour.getDistance()));
        }
        return predictors;
    }

    private double getDistanceTotal() {
        double distance = 0;
        for (Distance d : neighbours) {
            distance += d.getDistance();
        }
        return distance;
    }

    public void sortPredictions() {
        if (this.predictions.size() > 0) {
            Collections.sort(this.predictions, new Comparator<Prediction>() {
                @Override
                public int compare(Prediction o1, Prediction o2) {
                    if (o1.getRating() > o2.getRating()) return -1;
                    if (o1.getRating() < o1.getRating()) return 1;
                    return 0;
                }
            });
        }
    }
}
