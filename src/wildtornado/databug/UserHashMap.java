package wildtornado.databug;

import wildtornado.databug.objects.Preference;
import wildtornado.databug.objects.User;

import java.util.*;

public class UserHashMap {

    private HashMap<Integer, List<Preference>> userHashMap = new HashMap<Integer, List<Preference>>();

    public HashMap<Integer, List<Preference>> generate(List<User> userList) {
        for (User user : userList) {
            if (userHashMap.get(user.getUserID()) == null) {
                userHashMap.put(user.getUserID(), new ArrayList<Preference>());
            }
            userHashMap.get(user.getUserID()).add((user.getPreference()));
        }
        return userHashMap;
    }

    public HashMap<Integer, List<Preference>> get() {
        return this.userHashMap;
    }

    public List<Preference> getSingleUserValues(int num) {
        Set set = userHashMap.entrySet();
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
        return this.userHashMap.containsKey(num);
    }

    public void printData() {
        Set set = userHashMap.entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            System.out.print(me.getKey() + ": ");
            List<Preference> p = (List<Preference>) me.getValue();
            for (Preference pref : p) {
                System.out.print(pref.getProduct() + " " + pref.getRating() + ", ");
            }
            System.out.println();
        }
    }
}
