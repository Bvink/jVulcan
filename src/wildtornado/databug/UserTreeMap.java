package wildtornado.databug;

import wildtornado.databug.objects.Preference;
import wildtornado.databug.objects.User;

import java.util.*;

public class UserTreeMap {

    private TreeMap<Integer, List<Preference>> userTreeMap = new TreeMap<Integer, List<Preference>>();

    public TreeMap generate(List<User> userList) {
        for (User user : userList) {
            if (userTreeMap.get(user.getUserID()) == null) {
                userTreeMap.put(user.getUserID(), new ArrayList<Preference>());
            }
            userTreeMap.get(user.getUserID()).add((user.getPreference()));
        }
        return userTreeMap;
    }

    public TreeMap get() {
        return this.userTreeMap;
    }

    public List<Preference> getSingleUserValues(int num) {
        Set set = userTreeMap.entrySet();
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
        return this.userTreeMap.containsKey(num);
    }

    public void printData() {
        Set set = userTreeMap.entrySet();
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
