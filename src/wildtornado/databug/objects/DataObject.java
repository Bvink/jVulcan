package wildtornado.databug.objects;

public class DataObject {
    private String location;
    private String splitter;
    private int elements;

    public DataObject(String location, String splitter, int elements) {
        this.location = location;
        this.splitter = splitter;
        this.elements = elements;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSplitter() {
        return splitter;
    }

    public void setSplitter(String splitter) {
        this.splitter = splitter;
    }

    public int getElements() {
        return elements;
    }

    public void setElements(int elements) {
        this.elements = elements;
    }
}
