package wildtornado;

import wildtornado.databug.ParseDataset;
import wildtornado.databug.UserHashMap;
import wildtornado.databug.objects.User;
import wildtornado.databug.strategies.Algorithm;
import wildtornado.databug.strategies.Cosine;
import wildtornado.databug.strategies.Euclidean;
import wildtornado.databug.strategies.Pearson;
import wildtornado.databug.util.RatingsPredictor;

import java.util.List;

public class Start {

    public static void main(String[] args) {
        int currentUser = 7;
        int x = 3;
        int n = 8;
        double threshold = 0.35;

        ParseDataset parser = new ParseDataset();
        List<User> userList = parser.importCSV();
        UserHashMap userHashMap = new UserHashMap();
        userHashMap.generate(userList);
        //t.printData();

        if (userHashMap.userExists(currentUser)) {
            Algorithm euclidean = algorithm(userHashMap, currentUser, x, threshold, new Euclidean());
            Algorithm pearson = algorithm(userHashMap, currentUser, x, threshold, new Pearson());
            Algorithm cosine = algorithm(userHashMap, currentUser, x, threshold, new Cosine());

            RatingsPredictor predictor = ratingsPredictor(userHashMap, currentUser, n, pearson);

        }
    }

    private static Algorithm algorithm(UserHashMap userHashMap, int currentUser, int x, double threshold, Algorithm algorithm) {
        algorithm.generateDistances(userHashMap, currentUser, userHashMap.getSingleUserValues(currentUser));
        //List<Distance> distances = algorithm.getDistances();
        algorithm.sortDistances();
        algorithm.printNeighbours();
        algorithm.generatexNeighbours(x);
        algorithm.printxNeighbours();
        algorithm.generateThresholdNeighbours(threshold);
        algorithm.printThresholdNeighbours();
        return algorithm;
    }

    private static RatingsPredictor ratingsPredictor(UserHashMap userHashMap, int currentUser, int n, Algorithm algorithm) {
        if (algorithm.isSorted()) {
            RatingsPredictor predictor = new RatingsPredictor(algorithm.getxNeighbours(), userHashMap, currentUser);
            predictor.printRatedProducts(currentUser);
            predictor.generateRateableProducts();
            predictor.printRateableProducts();
            predictor.generatePredictions();
            predictor.printPredictions();
            predictor.sortPredictions();
            predictor.printnPredictions(n);
            return predictor;
        }
        return null;
    }
}