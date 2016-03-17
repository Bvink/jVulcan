package wildtornado.databug.strategies;

import wildtornado.databug.constants.Constants;
import wildtornado.databug.objects.Distance;
import wildtornado.databug.objects.Preference;

import java.util.List;

public class Pearson extends AlgorithmBaseFunctions implements Algorithm {

    public Pearson() {
        this.algorithmName = "pearson";
        this.sorted = false;
        this.sortMethod = Constants.DESC;
    }

    protected Distance calculateDistance(List<Preference> userOne, List<Preference> userTwo, int comparedUserID) {
        double sumOfRatingsTimesRatings = 0;
        double sumUserOneRatings = 0, sumUserOneRatingsSquare = 0;
        double sumUserTwoRatings = 0, sumUserTwoRatingsSquare = 0;
        int matches = 0;
        for (Preference prefOne : userOne) {
            for (Preference prefTwo : userTwo) {
                if (prefOne.getProduct() == prefTwo.getProduct()) {
                    sumOfRatingsTimesRatings = sumOfRatingsTimesRatings + prefOne.getRating() * prefTwo.getRating();
                    sumUserOneRatings += prefOne.getRating();
                    sumUserOneRatingsSquare += Math.pow(prefOne.getRating(), 2);
                    sumUserTwoRatings += prefTwo.getRating();
                    sumUserTwoRatingsSquare += Math.pow(prefTwo.getRating(), 2);
                    matches++;
                    break;
                }
            }
        }

        double topOfEquation = sumOfRatingsTimesRatings - ((sumUserOneRatings * sumUserTwoRatings) / matches);
        double bottomOfEquation = Math.sqrt(sumUserOneRatingsSquare - (Math.pow(sumUserOneRatings, 2) / matches)) * Math.sqrt(sumUserTwoRatingsSquare - (Math.pow(sumUserTwoRatings, 2)) / matches);
        double distance = topOfEquation / bottomOfEquation;

        return (matches > 0 && !Double.isNaN(distance)) ? new Distance(comparedUserID, distance, matches, checkIfUserHasAdditionalItem(userOne, userTwo)) : null;
    }
}
