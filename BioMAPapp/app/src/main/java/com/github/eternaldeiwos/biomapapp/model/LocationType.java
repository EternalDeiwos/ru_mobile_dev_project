package com.github.eternaldeiwos.biomapapp.model;

/**
 * Created by glinklater on 2016/05/30.
 */

public enum LocationType {
    POLITICAL("political"),
    COUNTRY("country"),
    PROVINCE("administrative_area_level_1"),
    DISTRICT("administrative_area_level_2"),
    TOWN("administrative_area_level_3"),
    POSTAL_CODE("postal_code"),
    SUBURB("neighborhood"),
    STREET("route"),
    NUMBER("street_number");

    private String val;

    LocationType(String val) {
        this.val = val;
    }

    public static LocationType getEnum(String val) {
        for (LocationType t : values()) {
            if (t.toString().equals(val)) return t;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return this.val;
    }
}
