package wildtornado.databug.strategies;

import wildtornado.databug.constants.Constants;
import wildtornado.databug.objects.Distance;
import wildtornado.databug.objects.Preference;
import wildtornado.databug.storage.UserHashMap;

import java.util.List;

public class Euclidean extends BaseAlgorithmFunctions {

    public Euclidean(UserHashMap userHashMap, int currentUser) {

        this.userHashMap = userHashMap;
        this.currentUser = currentUser;
        this.algorithmName = "euclidean";
        this.sorted = false;
        this.sortMethod = Constants.ASC;
    }

    @Override
    protected Distance calculateDistance(List<Preference> userOne, List<Preference> userTwo, int comparedUserID) {
        double sum = 0;
        int matches = 0;
        for (Preference prefOne : userOne) {
            for (Preference prefTwo : userTwo) {
                if (prefTwo.getProduct() == prefOne.getProduct()) {
                    sum += Math.pow(prefOne.getRating() - prefTwo.getRating(),  2);
                    matches++;
                    break;
                }
            }
        }
        double distance = Math.sqrt(sum);
        return matches > 0 ? new Distance(comparedUserID, distance, matches, checkIfUserHasAdditionalItem(userOne, userTwo), this.sortMethod) : null;
    }

}
