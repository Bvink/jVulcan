package wildtornado.databug.strategies;

import wildtornado.databug.storage.UserHashMap;
import wildtornado.databug.objects.Distance;
import wildtornado.databug.objects.Preference;

import java.util.List;

public interface Algorithm {

    public void run(int neighbours, double threshold);

    public void generateDistances(UserHashMap userHashMap, int num, List<Preference> currentUserValues);

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
