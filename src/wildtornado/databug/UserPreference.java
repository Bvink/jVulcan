package wildtornado.databug;

import wildtornado.databug.objects.Preference;
import wildtornado.databug.objects.User;
import wildtornado.databug.util.Printer;

import java.util.*;

public class UserPreference {

    private HashMap<Integer, List<Preference>> userPreference = new HashMap<Integer, List<Preference>>();

    public HashMap<Integer, List<Preference>> generate(List<User> userList) {
        for (User user : userList) {
            if (userPreference.get(user.getUserID()) == null) {
                userPreference.put(user.getUserID(), new ArrayList<Preference>());
            }
            userPreference.get(user.getUserID()).add((user.getPreference()));
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
