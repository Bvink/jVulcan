package wildtornado.databug.storage;

import wildtornado.databug.objects.UserPreference;
import wildtornado.databug.util.Printer;

import java.util.Arrays;
import java.util.List;

public class ItemDeviationMatrix {

    private List<UserPreference> userPreferenceList;
    private int idMax = 0;
    private double[][] iDev;
    private int[][] iFreq;

    public ItemDeviationMatrix(List<UserPreference> userPreferenceList) {
        this.userPreferenceList = userPreferenceList;
        for(UserPreference u: userPreferenceList) {
            this.idMax = u.getPreference().getProduct() > this.idMax ? u.getPreference().getProduct() : this.idMax;
        }
        this.iDev = new double[idMax+1][idMax+1];
        this.iFreq = new int[idMax+1][idMax+1];
    }

    public void generate() {

        for(UserPreference productOne : this.userPreferenceList) {
            for(UserPreference productTwo : this.userPreferenceList) {
                iDev[getProduct(productOne)][getProduct(productTwo)] += getValue(productOne) - getValue(productTwo);
                iFreq[getProduct(productOne)][getProduct(productTwo)] += 1;
            }
        }

        for(int i = 0; i <= idMax; i++) {
            for(int j = 0; j <= idMax; j++) {
                iDev[i][j] = iFreq[i][j] > 0 ? iDev[i][j] / iFreq[i][j] : 0;
            }
        }

    }

    private int getProduct(UserPreference u) {
        return u.getPreference().getProduct();
    }

    private double getValue(UserPreference u) {
        return u.getPreference().getRating();
    }

    public void printDevMatrix() {
        Printer.printDevMatrix(this.iDev);
    }

    public void printFreqMatrix() {
        Printer.printFreqMatrix(this.iFreq);
    }

}
