package sample.Model;

import java.util.List;

 public class Ship {
    private List<String> locations;
    private List<String> hits;

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public List<String> getHits() {
        return hits;
    }

    public void setHits(List<String> hits) {
        this.hits = hits;
    }
}
