package wildtornado;

import wildtornado.databug.ParseDataset;
import wildtornado.databug.UserTreeMap;
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
        UserTreeMap userTreeMap = new UserTreeMap();
        userTreeMap.generate(userList);
        //t.printData();

        if (userTreeMap.userExists(currentUser)) {
            Algorithm euclidean = algorithm(userTreeMap, currentUser, x, threshold, new Euclidean());
            Algorithm pearson = algorithm(userTreeMap, currentUser, x, threshold, new Pearson());
            Algorithm cosine = algorithm(userTreeMap, currentUser, x, threshold, new Cosine());

            RatingsPredictor predictor = ratingsPredictor(userTreeMap, currentUser, n, pearson);

        }
    }

    private static Algorithm algorithm(UserTreeMap userTreeMap, int currentUser, int x, double threshold, Algorithm algorithm) {
        algorithm.generateDistances(userTreeMap, currentUser, userTreeMap.getSingleUserValues(currentUser));
        //List<Distance> distances = algorithm.getDistances();
        algorithm.sortDistances();
        algorithm.printNeighbours();
        algorithm.generatexNeighbours(x);
        algorithm.printxNeighbours();
        algorithm.generateThresholdNeighbours(threshold);
        algorithm.printThresholdNeighbours();
        return algorithm;
    }

    private static RatingsPredictor ratingsPredictor(UserTreeMap userTreeMap, int currentUser, int n, Algorithm algorithm) {
        if (algorithm.isSorted()) {
            RatingsPredictor predictor = new RatingsPredictor();
            predictor.setNeighbours(algorithm.getxNeighbours());
            predictor.setUserTreeMap(userTreeMap);
            predictor.setCurrentUser(currentUser);
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