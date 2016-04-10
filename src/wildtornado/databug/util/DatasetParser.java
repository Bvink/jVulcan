package wildtornado.databug.util;

import wildtornado.databug.objects.DataObject;
import wildtornado.databug.objects.Preference;
import wildtornado.databug.objects.UserPreference;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class DataSetParser {

    public List<UserPreference> dataImport(DataObject dataObject) {
        List<UserPreference> collection = new ArrayList<UserPreference>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + dataObject.getLocation()));
            String stringRead = br.readLine();
            while (stringRead != null) {
                UserPreference dl = new UserPreference();
                String[] elements = stringRead.split(dataObject.getSplitter(), dataObject.getElements());
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
