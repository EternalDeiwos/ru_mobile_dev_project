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
    ADMINISTRATIVE_LEVEL_4("administrative_area_level_4"),
    ADMINISTRATIVE_LEVEL_5("administrative_area_level_5"),
    POSTAL_CODE("postal_code"),
    SUBURB("neighborhood"),
    STREET("route"),
    NUMBER("street_number"),
    INTERSECTION("intersection"),
    COLLOQUIAL("colloquial_area"),
    LOCALITY("locality"),
    SUBLOCALITY("sublocality"),
    SUBLOCALITY_LEVEL_1("sublocality_level_1"),
    SUBLOCALITY_LEVEL_2("sublocality_level_2"),
    SUBLOCALITY_LEVEL_3("sublocality_level_3"),
    SUBLOCALITY_LEVEL_4("sublocality_level_4"),
    SUBLOCALITY_LEVEL_5("sublocality_level_5"),
    PREMISE("premise"),
    SUBPREMISE("subpremise"),
    NATURAL_FEATURE("natural_feature"),
    AIRPORT("airport"),
    PARK("park");

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
