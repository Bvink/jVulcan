package wildtornado.databug;

import wildtornado.databug.constants.Constants;
import wildtornado.databug.objects.Preference;
import wildtornado.databug.objects.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ParseDataset {

    public List<User> importCSV() {
        List<User> collection = new ArrayList<User>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + Constants.CSV_LOCATION));
            String stringRead = br.readLine();
            while (stringRead != null) {
                User dl = new User();
                String[] elements = stringRead.split(",", 3);
                dl.setUserID(Integer.parseInt(elements[0]));
                dl.setPreference(new Preference(Integer.parseInt(elements[1]), Double.parseDouble(elements[2])));
                collection.add(dl);
                stringRead = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return collection;
    }

    public List<User> importHundredK() {
        List<User> collection = new ArrayList<User>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + Constants.HUNDREDK_LOCATION));
            String stringRead = br.readLine();
            while (stringRead != null) {
                User dl = new User();
                String[] elements = stringRead.split("\\s+", 4);
                dl.setUserID(Integer.parseInt(elements[0]));
                dl.setPreference(new Preference(Integer.parseInt(elements[1]), Double.parseDouble(elements[2])));
                collection.add(dl);
                stringRead = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return collection;
    }
}
