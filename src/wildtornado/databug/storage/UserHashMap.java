package wildtornado.databug.storage;

import wildtornado.databug.objects.Preference;
import wildtornado.databug.objects.UserPreference;
import wildtornado.databug.util.Printer;

import java.util.*;

public class UserHashMap {

    private HashMap<Integer, List<Preference>> userPreference = new HashMap<Integer, List<Preference>>();

    public HashMap<Integer, List<Preference>> generate(List<UserPreference> userPreferenceList) {
        for (UserPreference userPreference : userPreferenceList) {
            if (this.userPreference.get(userPreference.getUserID()) == null) {
                this.userPreference.put(userPreference.getUserID(), new ArrayList<Preference>());
            }
            this.userPreference.get(userPreference.getUserID()).add((userPreference.getPreference()));
        }
        return userPreference;
    }

    public HashMap<Integer, List<Preference>> get() {
        return this.userPreference;
    }

    @SuppressWarnings("unchecked")
    public List<Preference> getSingleUserValues(int num) {
        Set set = userPreference.entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            if ((Integer) me.getKey() == num) {
                return (List<Preference>) me.getValue();
            }
        }
        return null;
    }

    public boolean userExists(int num) {
        return this.userPreference.containsKey(num);
    }

    public void printData() {
        Printer.printUserPreferenceData(this.userPreference.entrySet());
    }
}
