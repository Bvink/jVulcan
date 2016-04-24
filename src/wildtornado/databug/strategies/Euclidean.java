package wildtornado.databug.strategies;

import org.junit.Assert;
import org.junit.Test;

import wildtornado.databug.constants.Constants;
import wildtornado.databug.objects.Distance;
import wildtornado.databug.objects.Preference;
import wildtornado.databug.storage.UserHashMap;

import java.util.ArrayList;
import java.util.List;

public class Euclidean extends BaseAlgorithmFunctions {

    /*
    public Euclidean(UserHashMap userHashMap, int currentUser) {

        this.userHashMap = userHashMap;
        this.currentUser = currentUser;
        this.algorithmName = "euclidean";
        this.sorted = false;
        this.sortMethod = Constants.ASC;
    }
    */

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

    /**
     * Test if the calculation is correct.
     **/

    @Test
    public void testFormula() {

        int testUser = 5;

        List<Preference> userOne = new ArrayList<>();
        userOne.add(new Preference(104, 3.5));
        userOne.add(new Preference(105, 2.5));
        List<Preference> userTwo = new ArrayList<>();
        userTwo.add(new Preference(104, 4.5));
        userTwo.add(new Preference(105, 1.5));

        double manualCalculation = Math.sqrt(Math.pow(3.5 - 4.5, 2) + Math.pow(2.5 - 1.5, 2));

        Assert.assertEquals(calculateDistance(userOne, userTwo, testUser).getDistance(), manualCalculation, 0.0001);

    }

    /**
     * Test if the amount of matching items is one.
     **/

    @Test
    public void testAmountOfMatches() {

        int testUser = 5;
        int testAmount = 1;

        List<Preference> userOne = new ArrayList<>();
        userOne.add(new Preference(104, 3.5));
        userOne.add(new Preference(105, 2.5));
        List<Preference> userTwo = new ArrayList<>();
        userTwo.add(new Preference(104, 4.5));
        userTwo.add(new Preference(101, 1.5));

        Assert.assertEquals(calculateDistance(userOne, userTwo, testUser).getMatchingItems(), testAmount);

    }

    /**
     * Test if the amount of additional items is correctly counted
     **/

    @Test
    public void testAdditionalItems() {

        int testUser = 5;
        int testAmount = 1;

        List<Preference> userOne = new ArrayList<>();
        userOne.add(new Preference(104, 3.5));
        userOne.add(new Preference(105, 2.5));
        List<Preference> userTwo = new ArrayList<>();
        userTwo.add(new Preference(104, 4.5));
        userTwo.add(new Preference(105, 1.5));

        Assert.assertFalse(calculateDistance(userOne, userTwo, testUser).getHasAdditionalItems());

        userTwo.add(new Preference(106, 4.5));
        Assert.assertTrue(calculateDistance(userOne, userTwo, testUser).getHasAdditionalItems());

    }
}
