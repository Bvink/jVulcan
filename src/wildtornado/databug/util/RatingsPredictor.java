package wildtornado.databug.util;

import wildtornado.databug.UserTreeMap;
import wildtornado.databug.objects.Distance;
import wildtornado.databug.objects.Preference;

import java.util.ArrayList;
import java.util.List;

public class RatingsPredictor {

    private List<Distance> neighbours;
    private UserTreeMap userTreeMap;
    List<Integer> rateableProducts;
    private int currentUser;

    public void setNeighbours(List<Distance> neighbours) {
        this.neighbours = neighbours;
    }

    public void setUserTreeMap(UserTreeMap userTreeMap) {
        this.userTreeMap = userTreeMap;
    }

    public void setCurrentUser(int currentUser) {
        this.currentUser = currentUser;
    }

    public List<Integer> getRatedProducts(int user) {
        List<Integer> ratedProducts = new ArrayList<Integer>();
        List<Preference> products = userTreeMap.getSingleUserValues(user);
        for(Preference p : products) {
            ratedProducts.add(p.getProduct());
        }
        return ratedProducts;
    }

    public void printRatedProducts(int user) {
        Printer.printRatedProducts(getRatedProducts(user), user);
    }

    public List<Preference> getUserRatings(int user) {
        return userTreeMap.getSingleUserValues(user);
    }

    public void generateRateableProducts() {
        List<Integer> rateableProducts = new ArrayList<Integer>();
        List<Integer> currentUserRatedProducts = getRatedProducts(currentUser);
        for(Distance user : this.neighbours) {
            List<Integer> neighbourRatedProducts = getRatedProducts(user.getUserID());
            for(int product : neighbourRatedProducts) {
                if(!rateableProducts.contains(product) && !currentUserRatedProducts.contains(product)) {
                    rateableProducts.add(product);
                }
            }
        }
        this.rateableProducts = rateableProducts;
    }

    public void printRateableProducts() {
        Printer.printRateableProducts(this.rateableProducts, this.currentUser);
    }

    public double getSingleRating(int user, int productNumber) {
        List<Preference> currentUserRatings = getUserRatings(user);
        for(Preference p : currentUserRatings) {
            if(p.getProduct() == productNumber) {
                return p.getRating();
            }
        }
        return 0;
    }

    public void predictRatings() {
        for(int product : this.rateableProducts) {
            List<Double> ratings = new ArrayList<Double>();
            for(Distance neighbour : this.neighbours) {
                ratings.add(getSingleRating(neighbour.getUserID(), product));
            }
            predictSingleProductRating(ratings, product);
        }
    }

    public void predictSingleProductRating(List<Double> ratings, int product) {
        System.out.println("product " + product + " has these ratings.");
        for(double rating : ratings) {
            System.out.println(rating);
        }
    }
}
