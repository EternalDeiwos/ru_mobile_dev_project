package com.github.eternaldeiwos.biomapapp.model;

import com.github.eternaldeiwos.biomapapp.rest.RestReverseGeocode;

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

    public LocationEntry[] entries;
    public ResponseType status;

    public Location(LocationEntry[] entries, ResponseType status) {
        this.entries = entries;
        this.status = status;
    }

    public Location() { /* default */ }

    public boolean isSuccess() { return this.status == ResponseType.OK; }

    public LocationEntry getBestEntry() {
        LocationEntry best = entries.length > 0
                ? entries[0]
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
