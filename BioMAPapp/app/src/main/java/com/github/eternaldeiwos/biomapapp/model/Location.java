package com.github.eternaldeiwos.biomapapp.model;

import com.github.eternaldeiwos.biomapapp.rest.RestReverseGeocode;

import java.util.List;

/**
 * Created by glinklater on 2016/05/30.
 */

public class Location {
    public static final String[] BLACKLIST = {
            "Unnamed Road"
    };

    public static Location getLocationFromGPS(float lat, float lng) {
        return RestReverseGeocode.getLocation(lat, lng);
    }

    public List<LocationEntry> entries;
    public ResponseType status;

    public Location(List<LocationEntry> entries, ResponseType status) {
        this.entries = entries;
        this.status = status;
    }

    public Location() { /* default */ }

    public LocationEntry getBestEntry() {
        LocationEntry best = entries.size() > 0
                ? entries.get(0)
                : null;
        for (LocationEntry entry : entries) {
            if (entry.compareTo(best) > 0) best = entry;
        }
        return best;
    }

    public LocationEntry getEntry(LocationType type) {
        for (LocationEntry entry : entries)
            for (LocationType t : entry.types)
                if (t.compareTo(type) == 0) return entry;
        return null;
    }
}
