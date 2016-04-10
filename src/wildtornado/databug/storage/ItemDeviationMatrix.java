package wildtornado.databug.storage;

import wildtornado.databug.objects.UserPreference;
import wildtornado.databug.util.Printer;

import java.util.List;

public class ItemDeviationMatrix {

    private List<UserPreference> userPreferenceList;
    private int idMax = 0;
    private double[][] devMatrix;
    private int[][] freqMatrix;

    public ItemDeviationMatrix(List<UserPreference> userPreferenceList) {
        this.userPreferenceList = userPreferenceList;
        for(UserPreference u: userPreferenceList) {
            this.idMax = u.getPreference().getProduct() > this.idMax ? u.getPreference().getProduct() : this.idMax;
        }
        this.devMatrix = new double[idMax+1][idMax+1];
        this.freqMatrix = new int[idMax+1][idMax+1];
    }

    public void generate() {

        for(UserPreference productOne : this.userPreferenceList) {
            for(UserPreference productTwo : this.userPreferenceList) {
                devMatrix[getProduct(productOne)][getProduct(productTwo)] += getValue(productOne) - getValue(productTwo);
                freqMatrix[getProduct(productOne)][getProduct(productTwo)] += 1;
            }
        }

        for(int i = 0; i <= idMax; i++) {
            for(int j = 0; j <= idMax; j++) {
                devMatrix[i][j] = freqMatrix[i][j] > 0 ? devMatrix[i][j] / freqMatrix[i][j] : 0;
            }
        }

    }

    private int getProduct(UserPreference u) {
        return u.getPreference().getProduct();
    }

    private double getValue(UserPreference u) {
        return u.getPreference().getRating();
    }

    public int getIdMax() {
        return this.idMax;
    }

    public double[][] getDevMatrix() {
        return this.devMatrix;
    }

    public int[][] getFreqMatrix() {
        return this.freqMatrix;
    }

    public void printDevMatrix() {
        Printer.printDevMatrix(this.devMatrix);
    }

    public void printFreqMatrix() {
        Printer.printFreqMatrix(this.freqMatrix);
    }

}
