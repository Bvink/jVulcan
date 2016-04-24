package wildtornado;

import junit.framework.Assert;
import org.junit.Test;
import wildtornado.databug.storage.UserHashMap;
import wildtornado.databug.strategies.Cosine;
import wildtornado.databug.strategies.Euclidean;
import wildtornado.databug.strategies.Pearson;
import wildtornado.databug.util.DataSetParser;
import wildtornado.databug.constants.Constants;
import wildtornado.databug.objects.UserPreference;
import wildtornado.databug.strategies.Algorithm;

import java.util.List;

public class Start {

    public static void main(String[] args) {
        int currentUser = 7;
        int neighbours = 3;
        double threshold = 0.35;

        //int amount = 3;
        //int minimumNeighbours = 1;

        DataSetParser parser = new DataSetParser();
        List<UserPreference> userPreferenceList = parser.dataImport(Constants.CSV);

        UserHashMap userHashMap = new UserHashMap();
        userHashMap.generate(userPreferenceList);
        //userPreference.printData();


        if (userHashMap.userExists(currentUser)) {

            /*
            Algorithm euclidean = new Euclidean(userHashMap, currentUser);
            euclidean.run(neighbours, threshold);


            Algorithm pearson = new Pearson(userHashMap, currentUser);
            pearson.run(neighbours, threshold);

            Algorithm cosine = new Cosine(userHashMap, currentUser);
            cosine.run(neighbours, threshold);
            */

            //DistancePredictor predictor = new DistancePredictor(pearson.getxNeighbours(), userHashMap, currentUser);
            //predictor.run(amount, minimumNeighbours, pearson);

        }
    }
}