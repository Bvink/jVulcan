package wildtornado;

import wildtornado.databug.predictors.DistancePredictor;
import wildtornado.databug.predictors.SlopeOnePredictor;
import wildtornado.databug.storage.ItemDeviationMatrix;
import wildtornado.databug.storage.UserHashMap;
import wildtornado.databug.util.DataSetParser;
import wildtornado.databug.constants.Constants;
import wildtornado.databug.objects.UserPreference;
import wildtornado.databug.strategies.Algorithm;

import java.util.List;

public class Start {

    public static void main(String[] args) {
        int currentUser = 186;
        int amount = 3;
        int neighbours = 3;
        int minimumNeighbours = 1;
        double threshold = 0.35;

        DataSetParser parser = new DataSetParser();
        List<UserPreference> userPreferenceList = parser.dataImport(Constants.HUNDREDK);

        UserHashMap userHashMap = new UserHashMap();
        userHashMap.generate(userPreferenceList);
        //userPreference.printData();

        /*

        if (userHashMap.userExists(currentUser)) {
            Algorithm euclidean = algorithm(userHashMap, currentUser, neighbours, threshold, new Euclidean());
            Algorithm pearson = algorithm(userHashMap, currentUser, neighbours, threshold, new Pearson());
            Algorithm cosine = algorithm(userHashMap, currentUser, neighbours, threshold, new Cosine());

            RatingsPredictor predictor = ratingsPredictor(userHashMap, currentUser, amount, minimumNeighbours, pearson);

        }

        */

        ItemDeviationMatrix itemDeviationMatrix = new ItemDeviationMatrix(userPreferenceList);
        itemDeviationMatrix.generate();
        SlopeOnePredictor slope = new SlopeOnePredictor(currentUser, itemDeviationMatrix, userHashMap);
        slope.generate();
        slope.sortPredictions();
        slope.printPredictions();

    }

    private static Algorithm algorithm(UserHashMap userHashMap, int currentUser, int neighbours, double threshold, Algorithm algorithm) {
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

    private static DistancePredictor ratingsPredictor(UserHashMap userHashMap, int currentUser, int amount, int minimumNeighbours, Algorithm algorithm) {
        if (algorithm.isSorted()) {
            DistancePredictor predictor = new DistancePredictor(algorithm.getxNeighbours(), userHashMap, currentUser);
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