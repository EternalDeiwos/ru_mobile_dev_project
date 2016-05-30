package com.github.eternaldeiwos.biomapapp.model;

/**
 * Created by glinklater on 2016/05/30.
 */

public class LocationEntry {
    public String address;
    public LocationType[] types;
    public AddressComponent[] addressComponents;

    @Override
    public String toString() {
        return address;
    }

    public LocationEntry(String address, LocationType[] types, AddressComponent[] addressComponents) {
        this.address = address;
        this.types = types;
        this.addressComponents = addressComponents;
    }
}
