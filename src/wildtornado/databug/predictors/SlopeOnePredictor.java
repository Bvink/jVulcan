package wildtornado.databug.predictors;

import wildtornado.databug.objects.Prediction;
import wildtornado.databug.objects.Preference;
import wildtornado.databug.storage.ItemDeviationMatrix;
import wildtornado.databug.storage.UserHashMap;

import java.util.ArrayList;
import java.util.List;

public class SlopeOnePredictor extends BasePredictorFunctions {

    private List<Preference> userProducts;
    private double[][] devMatrix;
    private int[][] freqMatrix;
    private int idMax;

    public SlopeOnePredictor(int currentUser, ItemDeviationMatrix itemDeviationMatrix, UserHashMap userHashMap) {
        this.currentUser = currentUser;
        this.userProducts = userHashMap.getSingleUserValues(currentUser);
        this.devMatrix = itemDeviationMatrix.getDevMatrix();
        this.freqMatrix = itemDeviationMatrix.getFreqMatrix();
        this.idMax = itemDeviationMatrix.getIdMax();
        this.predictions = new ArrayList<Prediction>();
    }

    public void generate() {
        for(int i = 0; i <= idMax; i++) {
            Prediction prediction = getSingleProductPrediction(i);
            if(prediction != null) {
                this.predictions.add(prediction);
            }
        }
    }

    public Prediction getSingleProductPrediction(int productNumber) {
        double num;
        int denom;
        num = 0;
        denom = 0;
        if (!hasRated(productNumber)) {
            for (Preference p : userProducts) {
                num += (p.getRating() + devMatrix[p.getProduct()][productNumber]) * freqMatrix[p.getProduct()][productNumber];
                denom += freqMatrix[p.getProduct()][productNumber];
            }
        }
        double outcome = num / denom;
        if (!Double.isNaN(outcome)) {
            return new Prediction(currentUser, productNumber, outcome, 0);
        }
        return null;
    }

    private boolean hasRated(int num) {
        for(Preference p : this.userProducts) {
            if(num == p.getProduct()) {
                return true;
            }
        }
        return false;
    }



}
