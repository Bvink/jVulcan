package wildtornado.databug.strategies;

import wildtornado.databug.UserTreeMap;
import wildtornado.databug.objects.Distance;
import wildtornado.databug.objects.Preference;

import java.util.List;

public interface Algorithm {

    public void generateDistances(UserTreeMap t, int num, List<Preference> currentUserValues);

    public List<Distance> getDistances();

    public void sortDistances();

    public boolean isSorted();

    public void generatexNeighbours(int x);

    public List<Distance> getxNeighbours();

    public void generateThresholdNeighbours(double threshold);

    public List<Distance> getThresholdNeighbours();

    public void printNeighbours();

    public void printxNeighbours();

    public void printThresholdNeighbours();
}
