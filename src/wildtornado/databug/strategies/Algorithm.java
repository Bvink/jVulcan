package wildtornado.databug.strategies;

import wildtornado.databug.UserTreeMap;
import wildtornado.databug.objects.Distance;
import wildtornado.databug.objects.Preference;

import java.util.List;

public interface Algorithm {

    public void generateDistances(UserTreeMap t, int num, List<Preference> currentUserValues);

    public List<Distance> getDistances();

    public void sortDistances();

    public void generatexNeighbours(int x);

    public List<Distance> getxNeighbours();

    public void generateTresholdNeighbours(double treshold);

    public List<Distance> getTresholdNeighbours();

    public void printNeighbours();

    public void printxNeighbours();

    public void printTresholdNeighbours();
}
