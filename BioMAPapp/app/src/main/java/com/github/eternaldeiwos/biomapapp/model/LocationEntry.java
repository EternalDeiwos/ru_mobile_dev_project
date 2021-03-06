package com.github.eternaldeiwos.biomapapp.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by glinklater on 2016/05/30.
 */

public class LocationEntry implements Comparable<LocationEntry> {
    public String address;
    public List<LocationType> types;
    public List<AddressComponent> addressComponents;

    @Override
    public String toString() {
        return address;
    }

    public LocationEntry(String address, List<LocationType> types, List<AddressComponent> addressComponents) {
        this.address = address;
        this.types = types;
        this.addressComponents = addressComponents;
    }

    public LocationType bestType() {
        LocationType bestType = types.size() > 0
                ? types.get(0)
                : null;
        for (LocationType t : types) {
            if (t.compareTo(bestType) > 0) bestType = t;
        }
        return bestType;
    }

    @Override
    public int compareTo(LocationEntry another) {
        return this.bestType().compareTo(another.bestType());
    }

    public AddressComponent getBestAddressComponent(LocationType type) {
        for (AddressComponent c : addressComponents)
            for (LocationType t : c.types)
                if (t.compareTo(type) == 0) return c;
        return null;
    }

    public AddressComponent getBestAddressComponent(LocationType[] types) {
        for (LocationType type : types) {
            for (AddressComponent c : addressComponents) {
                for (LocationType t : c.types) {
                    if (t.compareTo(type) == 0) return c;
                }
            }
        }
        return null;
    }
}
