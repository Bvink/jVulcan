package wildtornado.databug.predictors;

import wildtornado.databug.objects.Prediction;
import wildtornado.databug.util.Printer;

import java.util.Collections;
import java.util.List;

public class BasePredictor {

    protected int currentUser;
    protected List<Prediction> predictions;
    protected boolean sorted = false;

    public List<Prediction> getPredictions() {
        return this.predictions;
    }

    public void sortPredictions() {
        if (this.predictions.size() > 0) {
            this.sorted = true;
            Collections.sort(this.predictions);
        }
    }

    public void printPredictions() {
        Printer.printPredictions(this.predictions);
    }

    //This function assumes the set has been sorted.
    public void printnPredictions(int n) {
        if (this.sorted) {
            if (n > predictions.size()) {
                n = predictions.size();
            }
            Printer.printnPredictions(this.predictions, n);
        } else {
            Printer.unsortedWarning();
        }
    }
}
