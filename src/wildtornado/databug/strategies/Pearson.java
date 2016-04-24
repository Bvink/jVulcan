package wildtornado.databug.strategies;

import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.instanceOf;

import wildtornado.databug.constants.Constants;
import wildtornado.databug.objects.Distance;
import wildtornado.databug.objects.Preference;
import wildtornado.databug.storage.UserHashMap;

import java.util.ArrayList;
import java.util.List;

public class Pearson extends BaseAlgorithmFunctions {

    /*
    public Pearson(UserHashMap userHashMap, int currentUser) {

        this.userHashMap = userHashMap;
        this.currentUser = currentUser;
        this.algorithmName = "pearson";
        this.sorted = false;
        this.sortMethod = Constants.DESC;
    }
    */

    @Override
    protected Distance calculateDistance(List<Preference> userOne, List<Preference> userTwo, int comparedUserID) {
        double sumOfRatingsTimesRatings = 0;
        double sumUserOneRatings = 0, sumUserOneRatingsSquare = 0;
        double sumUserTwoRatings = 0, sumUserTwoRatingsSquare = 0;
        int matches = 0;
        for (Preference prefOne : userOne) {
            for (Preference prefTwo : userTwo) {
                if (prefOne.getProduct() == prefTwo.getProduct()) {
                    sumOfRatingsTimesRatings += prefOne.getRating() * prefTwo.getRating();
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

        return (matches > 0 && !Double.isNaN(distance)) ? new Distance(comparedUserID, distance, matches, checkIfUserHasAdditionalItem(userOne, userTwo), this.sortMethod) : null;
    }

    /**
     * Test if the calculation is correct.
     **/

    @Test
    public void testFormula() {

        int testUser = 5;
        int count = 5;

        List<Preference> userOne = new ArrayList<>();
        userOne.add(new Preference(104, 4.75));
        userOne.add(new Preference(105, 4.5));
        userOne.add(new Preference(106, 5.0));
        userOne.add(new Preference(107, 4.25));
        userOne.add(new Preference(108, 4.0));
        List<Preference> userTwo = new ArrayList<>();
        userTwo.add(new Preference(104, 4.0));
        userTwo.add(new Preference(105, 3.0));
        userTwo.add(new Preference(106, 5.0));
        userTwo.add(new Preference(107, 2.0));
        userTwo.add(new Preference(108, 1.0));

        double topPartOne = 0;
        for (int i = 0; i < count; i++) {
            topPartOne += userOne.get(i).getRating() * userTwo.get(i).getRating();
        }

        double sumUserOne = 0, sumUserTwo = 0;
        for (int i = 0; i < count; i++) {
            sumUserOne += userOne.get(i).getRating();
            sumUserTwo += userTwo.get(i).getRating();
        }
        double topPartTwo = (sumUserOne * sumUserTwo) / count;

        double top = topPartOne - topPartTwo;

        double bottomPartOne = 0;
        for (int i = 0; i < count; i++) {
            bottomPartOne += Math.pow(userOne.get(i).getRating(), 2);
        }

        double bottomPartTwo = Math.pow(sumUserOne, 2) / count;

        double bottomPartThree = 0;
        for (int i = 0; i < count; i++) {
            bottomPartThree += Math.pow(userTwo.get(i).getRating(), 2);
        }

        double bottomPartFour = Math.pow(sumUserTwo, 2) / count;

        double bottom = Math.sqrt(bottomPartOne - bottomPartTwo) * Math.sqrt(bottomPartThree - bottomPartFour);

        double manualCalculation = top/bottom;

        Assert.assertEquals(calculateDistance(userOne, userTwo, testUser).getDistance(), manualCalculation, 0.0001);

    }

    /**
     * Test if the amount of matching items is one.
     **/

    @Test
    public void testAmountOfMatches() {

        int testUser = 5;
        int testAmount = 4;

        List<Preference> userOne = new ArrayList<>();
        userOne.add(new Preference(104, 4.75));
        userOne.add(new Preference(105, 4.5));
        userOne.add(new Preference(106, 5.0));
        userOne.add(new Preference(107, 4.25));
        userOne.add(new Preference(109, 4.0));
        List<Preference> userTwo = new ArrayList<>();
        userTwo.add(new Preference(104, 4.0));
        userTwo.add(new Preference(105, 3.0));
        userTwo.add(new Preference(106, 5.0));
        userTwo.add(new Preference(107, 2.0));
        userTwo.add(new Preference(108, 1.0));

        Assert.assertEquals(calculateDistance(userOne, userTwo, testUser).getMatchingItems(), testAmount);

    }


    /**
     * Test if null is returned when there is no correlation.
     **/

    @Test
    public void testNoCorrelation() {

        int testUser = 5;

        List<Preference> userOne = new ArrayList<>();
        userOne.add(new Preference(104, 4.0));
        userOne.add(new Preference(105, 4.0));
        userOne.add(new Preference(106, 4.0));
        userOne.add(new Preference(107, 4.0));
        userOne.add(new Preference(108, 4.0));
        List<Preference> userTwo = new ArrayList<>();
        userTwo.add(new Preference(104, 4.0));
        userTwo.add(new Preference(105, 4.0));
        userTwo.add(new Preference(106, 4.0));
        userTwo.add(new Preference(107, 4.0));
        userTwo.add(new Preference(108, 4.0));

        Assert.assertEquals(calculateDistance(userOne, userTwo, testUser), null);

        userOne = new ArrayList<>();
        userOne.add(new Preference(104, 4.75));
        userOne.add(new Preference(105, 4.5));
        userOne.add(new Preference(106, 5.0));
        userOne.add(new Preference(107, 4.25));
        userOne.add(new Preference(109, 4.0));
        userTwo = new ArrayList<>();
        userTwo.add(new Preference(104, 4.0));
        userTwo.add(new Preference(105, 3.0));
        userTwo.add(new Preference(106, 5.0));
        userTwo.add(new Preference(107, 2.0));
        userTwo.add(new Preference(108, 1.0));

        Assert.assertThat(calculateDistance(userOne, userTwo, testUser), instanceOf(Distance.class));

    }
}
