package wildtornado.databug.strategies;


import wildtornado.databug.constants.Constants;
import wildtornado.databug.objects.Distance;
import wildtornado.databug.objects.Preference;

import java.util.List;

public class Cosine extends AlgorithmBaseFunctions implements Algorithm {

    public Cosine() {
        this.algorithmName = "cosine";
        this.sorted = false;
        this.sortMethod = Constants.DESC;
    }

    protected Distance calculateDistance(List<Preference> userOne, List<Preference> userTwo, int comparedUserID) {
        double sumOfRatingsTimesRatings = 0;
        double sumUserOneRatingsSquare = 0;
        double sumUserTwoRatingsSquare = 0;
        int matches = 0;
        for (Preference prefOne : userOne) {
            for (Preference prefTwo : userTwo) {
                if (prefOne.getProduct() == prefTwo.getProduct()) {
                    sumOfRatingsTimesRatings += prefOne.getRating() * prefTwo.getRating();
                    sumUserOneRatingsSquare += Math.pow(prefOne.getRating(), 2);
                    sumUserTwoRatingsSquare += Math.pow(prefTwo.getRating(), 2);
                    matches++;
                    break;
                }
            }
        }
        double distance = sumOfRatingsTimesRatings / (Math.sqrt(sumUserOneRatingsSquare) * Math.sqrt(sumUserTwoRatingsSquare));
        return (matches > 0 && !Double.isNaN(distance)) ? new Distance(comparedUserID, distance, matches, checkIfUserHasAdditionalItem(userOne, userTwo)) : null;
    }
}
