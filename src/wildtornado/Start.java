package wildtornado;

import wildtornado.databug.ParseDataset;
import wildtornado.databug.UserTreeMap;
import wildtornado.databug.objects.Distance;
import wildtornado.databug.objects.User;
import wildtornado.databug.strategies.Algorithm;
import wildtornado.databug.strategies.Cosine;
import wildtornado.databug.strategies.Euclidean;
import wildtornado.databug.strategies.Pearson;
import wildtornado.databug.util.RatingsPredictor;

import java.util.List;

public class Start {

    public static void main(String[] args) {
        int currentUser = 3;
        int x = 3;
        int n = 8;
        double treshold = 0.35;

        ParseDataset parser = new ParseDataset();
        List<User> userList = parser.importCSV();
        UserTreeMap userTreeMap = new UserTreeMap();
        userTreeMap.generate(userList);
        //t.printData();

        if (userTreeMap.userExists(currentUser)) {
            Algorithm euclidean = new Euclidean();
            euclidean.generateDistances(userTreeMap, currentUser, userTreeMap.getSingleUserValues(currentUser));
            List<Distance> euclideanDistances = euclidean.getDistances();
            euclidean.sortDistances();
            euclidean.printNeighbours();
            euclidean.generatexNeighbours(x);
            euclidean.printxNeighbours();
            euclidean.generateTresholdNeighbours(treshold);
            euclidean.printTresholdNeighbours();

            Algorithm pearson = new Pearson();
            pearson.generateDistances(userTreeMap, currentUser, userTreeMap.getSingleUserValues(currentUser));
            List<Distance> pearsonDistances = pearson.getDistances();
            pearson.sortDistances();
            pearson.printNeighbours();
            pearson.generatexNeighbours(x);
            pearson.printxNeighbours();
            pearson.generateTresholdNeighbours(treshold);
            pearson.printTresholdNeighbours();

            Algorithm cosine = new Cosine();
            cosine.generateDistances(userTreeMap, currentUser, userTreeMap.getSingleUserValues(currentUser));
            List<Distance> cosineDistances = cosine.getDistances();
            cosine.sortDistances();
            cosine.printNeighbours();
            cosine.generatexNeighbours(x);
            cosine.printxNeighbours();
            cosine.generateTresholdNeighbours(treshold);
            cosine.printTresholdNeighbours();

            if(pearson.isSorted()) {
                RatingsPredictor predictor = new RatingsPredictor();
                predictor.setNeighbours(pearson.getxNeighbours());
                predictor.setUserTreeMap(userTreeMap);
                predictor.setCurrentUser(currentUser);
                predictor.printRatedProducts(currentUser);
                predictor.generateRateableProducts();
                predictor.printRateableProducts();
                predictor.generatePredictions();
                predictor.printPredictions();
                predictor.sortPredictions();
                predictor.printnPredictions(n);
            }
        }
    }

}