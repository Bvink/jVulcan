package wildtornado.databug.constants;

import wildtornado.databug.objects.DataObject;

public class Constants {

    //Data file location.
    public static final DataObject CSV = new DataObject("\\\\src\\\\wildtornado\\\\databug\\\\files\\\\userItem.data", ",", 3);
    public static final DataObject HUNDREDK = new DataObject("\\\\src\\\\wildtornado\\\\databug\\\\files\\\\u.data", "\\s+", 4);

    //Sort distance.
    public static final int ASC = 1;
    public static final int DESC = -1;
    public static final int UNKNOWN = 0;
}
