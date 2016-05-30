package com.github.eternaldeiwos.biomapapp.model;

import android.net.Uri;

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

    public Uri[] images;
    public Uri sound;
    public String username;
    public String userid;
    public String email;
    public String project;
    public String observers;
    public String country;
    public String province;
    public String nearesttown;
    public String locality;
    public int minelev;
    public int maxelev;
    public float lat;
    public float lng;
    public int accuracy;
    public String source;
    public int year;
    public int month;
    public int day;
    public String note;
    public String userdet;
    public int nestcount;
    public String nestsite;
    public boolean roadkill;
    public String taxonid;
    public String taxonname;
    public String institution_code;
    public String collection_code;
    public String recordbasis;
    public OccurrenceStatus recordstatus;

    public Record(
            Uri[] images,
            Uri sound,
            String username,
            String userid,
            String email,
            String project,
            String observers,
            String country,
            String province,
            String nearesttown,
            String locality,
            int minelev,
            int maxelev,
            float lat,
            float lng,
            int accuracy,
            String source,
            int year,
            int month,
            int day,
            String note,
            String userdet,
            int nestcount,
            String nestsite,
            boolean roadkill,
            String taxonid,
            String taxonname,
            String institution_code,
            String collection_code,
            String recordbasis,
            OccurrenceStatus recordstatus
    ) {
        this.images = images;
        this.sound = sound;
        this.username = username;
        this.userid = userid;
        this.email = email;
        this.project = project;
        this.observers = observers;
        this.country = country;
        this.province = province;
        this.nearesttown = nearesttown;
        this.locality = locality;
        this.minelev = minelev;
        this.maxelev = maxelev;
        this.lat = lat;
        this.lng = lng;
        this.accuracy = accuracy;
        this.source = source;
        this.year = year;
        this.month = month;
        this.day = day;
        this.note = note;
        this.userdet = userdet;
        this.nestcount = nestcount;
        this.nestsite = nestsite;
        this.roadkill = roadkill;
        this.taxonid = taxonid;
        this.taxonname = taxonname;
        this.institution_code = institution_code;
        this.collection_code = collection_code;
        this.recordbasis = recordbasis;
        this.recordstatus = recordstatus;
    }

    public Record() { /* default */ }
}
