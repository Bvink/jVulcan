package wildtornado;

import wildtornado.databug.ParseDataSet;
import wildtornado.databug.UserPreference;
import wildtornado.databug.objects.User;
import wildtornado.databug.strategies.Algorithm;
import wildtornado.databug.strategies.Cosine;
import wildtornado.databug.strategies.Euclidean;
import wildtornado.databug.strategies.Pearson;
import wildtornado.databug.util.RatingsPredictor;

import java.util.List;

public class Start {

    public static void main(String[] args) {
        int currentUser = 186;
        int amount = 8;
        int neighbours = 25;
        int minimumNeighbours = 3;
        double threshold = 0.35;

        ParseDataSet parser = new ParseDataSet();
        List<User> userList = parser.importHundredK();
        UserPreference userPreference = new UserPreference();
        userPreference.generate(userList);
        //userPreference.printData();

        if (userPreference.userExists(currentUser)) {
            Algorithm euclidean = algorithm(userPreference, currentUser, neighbours, threshold, new Euclidean());
            Algorithm pearson = algorithm(userPreference, currentUser, neighbours, threshold, new Pearson());
            Algorithm cosine = algorithm(userPreference, currentUser, neighbours, threshold, new Cosine());

            RatingsPredictor predictor = ratingsPredictor(userPreference, currentUser, amount, minimumNeighbours, pearson);

        }
    }

    private static Algorithm algorithm(UserPreference userHashMap, int currentUser, int neighbours, double threshold, Algorithm algorithm) {
        algorithm.generateDistances(userHashMap, currentUser, userHashMap.getSingleUserValues(currentUser));
        //List<Distance> distances = algorithm.getDistances();
        algorithm.sortDistances();
        algorithm.printNeighbours();
        algorithm.generatexNeighbours(neighbours);
        algorithm.printxNeighbours();
        algorithm.generateThresholdNeighbours(threshold);
        algorithm.printThresholdNeighbours();
        return algorithm;
    }

    private static RatingsPredictor ratingsPredictor(UserPreference userHashMap, int currentUser, int amount, int minimumNeighbours, Algorithm algorithm) {
        if (algorithm.isSorted()) {
            RatingsPredictor predictor = new RatingsPredictor(algorithm.getxNeighbours(), userHashMap, currentUser);
            predictor.printRatedProducts(currentUser);
            predictor.printUserRatings(currentUser);
            predictor.generateRateableProducts();
            predictor.printRateableProducts();
            predictor.generatePredictions(minimumNeighbours);
            predictor.printPredictions();
            predictor.sortPredictions();
            predictor.printnPredictions(amount);
            return predictor;
        }
        return null;
    }
}