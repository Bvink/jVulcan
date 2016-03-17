package wildtornado.databug.strategies;

import wildtornado.databug.constants.Constants;
import wildtornado.databug.objects.Distance;
import wildtornado.databug.objects.Preference;

import java.util.List;

public class Euclidean extends AlgorithmBaseFunctions implements Algorithm {

    public Euclidean() {
        this.algorithmName = "euclidean";
        this.sorted = false;
        this.sortMethod = Constants.ASC;
    }

    @Override
    protected Distance calculateDistance(List<Preference> userOne, List<Preference> userTwo, int comparedUserID) {
        double distance = 0;
        int matches = 0;
        for (Preference prefOne : userOne) {
            for (Preference prefTwo : userTwo) {
                if (prefTwo.getProduct() == prefOne.getProduct()) {
                    distance += Math.sqrt(Math.pow(prefOne.getRating(), 2) + Math.pow(prefTwo.getRating(), 2));
                    matches++;
                    break;
                }
            }
        }
        return matches > 0 ? new Distance(comparedUserID, distance, matches, checkIfUserHasAdditionalItem(userOne, userTwo)) : null;
    }
}
