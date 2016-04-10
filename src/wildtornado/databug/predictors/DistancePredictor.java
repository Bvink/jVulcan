package wildtornado.databug.predictors;

import wildtornado.databug.storage.UserHashMap;
import wildtornado.databug.objects.Distance;
import wildtornado.databug.objects.Prediction;
import wildtornado.databug.objects.Predictor;
import wildtornado.databug.objects.Preference;
import wildtornado.databug.util.Printer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DistancePredictor extends BasePredictor {

    private List<Distance> neighbours;
    private UserHashMap userHashMap;
    private List<Integer> rateableProducts;

    public DistancePredictor(List<Distance> neighbours, UserHashMap userHashMap, int currentUser) {
        this.neighbours = neighbours;
        this.userHashMap = userHashMap;
        this.currentUser = currentUser;
    }

    public List<Integer> getRatedProducts(int user) {
        List<Integer> ratedProducts = new ArrayList<Integer>();
        List<Preference> products = userHashMap.getSingleUserValues(user);
        for (Preference p : products) {
            ratedProducts.add(p.getProduct());
        }
        return ratedProducts;
    }

    public void printRatedProducts(int user) {
        Printer.printRatedProducts(getRatedProducts(user), user);
    }

    public List<Preference> getUserRatings(int user) {
        return userHashMap.getSingleUserValues(user);
    }

    public void printUserRatings(int user) {
        Printer.printUserRatings(getUserRatings(user), user);
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

    public void generatePredictions(int minimumNeighbours) {
        List<Prediction> predictions = new ArrayList<Prediction>();
        for (int product : this.rateableProducts) {
            Prediction p = predictSingleProductRating(getPredictors(product), product);
            if (p.getRatedBy() >= minimumNeighbours) {
                predictions.add(p);
            }
        }
        this.predictions = predictions;
    }

    private Prediction predictSingleProductRating(List<Predictor> predictors, int product) {
        int count = 0;
        double distanceTotal = getDistanceTotal(predictors);
        double rating = 0;
        for (Predictor p : predictors) {
            rating += (p.getDistance() / distanceTotal) * p.getRating();
            if (p.getRating() > 0) {
                count++;
            }
        }
        return new Prediction(currentUser, product, rating, count);
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

    private double getDistanceTotal(List<Predictor> predictors) {
        double getDistanceTotal = 0;
        for (Predictor p : predictors) {
            if (p.getRating() != 0) {
                getDistanceTotal += p.getDistance();
            }
        }
        return getDistanceTotal;
    }

}
