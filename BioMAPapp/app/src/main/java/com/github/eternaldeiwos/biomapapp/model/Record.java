package com.github.eternaldeiwos.biomapapp.model;

import com.orm.SugarRecord;

import java.net.URI;

/**
 * Created by glinklater on 2016/05/29.
 */

public class Record extends SugarRecord {

    public static final String GPS_SOURCE_PHONE = "GPS";
    public static final String GPS_SOURCE_GOOGLE_MAP = "Google Map";
    public static final String NEST_SITE_TREE = "tree";
    public static final String NEST_SITE_REED = "reed";
    public static final String NEST_SITE_MAN_MADE = "man-made";
    public static final String NEST_SITE_OTHER = "other";
    public static final String BASIS_PHOTO = "VMUS";
    public static final String BASIS_CAMERA_TRAP = "VMUS:CAMERA TRAP";
    public static final String BASIS_SIGHTING = "SIGHTING";
    public static final String BASIS_PRESERVED = "MUSEUM RECORD";

    public enum OccurrenceStatus {
        BLANK("blank"),
        NATURAL("naturally occurring"),
        RE_INTRODUCED("re-introduced"),
        INTRODUCED("introduced"),
        FERAL("feral"),
        CULTIVATED("cultivated"),
        EXOTIC("exotic");

        private String value;

        OccurrenceStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return this.getValue();
        }

        public static OccurrenceStatus getEnum(String value) {
            for (OccurrenceStatus v : values()) {
                if (v.getValue().equalsIgnoreCase(value)) return v;
            }
            throw new IllegalArgumentException();
        }
    }

    public URI[] images;
    public URI sound;
    public String username;
    public String userid;
    public String email;
    public String project;
    public String observers;
    public String country;
    public String province;
    public String nearesttown;
    public String locality;
    public int min_elev;
    public int max_elev;
    public float lat;
    public float lng;
    public int accuracy;
    public String gps_source;
    public int year;
    public int month;
    public int day;
    public String observer_note;
    public String user_identification;
    public int nest_count;
    public String nest_site;
    public boolean roadkill;
    public String taxonid;
    public String scientific_name;
    public String institution;
    public String collection_code;
    public String record_basis;
    public OccurrenceStatus record_status;
}
