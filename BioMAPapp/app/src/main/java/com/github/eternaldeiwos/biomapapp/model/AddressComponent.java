package com.github.eternaldeiwos.biomapapp.model;

/**
 * Created by glinklater on 2016/05/30.
 */

public class AddressComponent {
    public String long_name;
    public String short_name;
    public LocationType[] types;

    @Override
    public String toString() {
        return long_name;
    }

    public AddressComponent(String long_name, String short_name, LocationType[] types) {
        this.long_name = long_name;
        this.short_name = short_name;
        this.types = types;
    }
}
