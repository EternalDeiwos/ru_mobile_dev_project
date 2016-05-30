package com.github.eternaldeiwos.biomapapp.model;

/**
 * Created by glinklater on 2016/05/30.
 */

public enum ResponseType {
    OK("OK"),
    ZERO_RESULTS("ZERO_RESULTS"),
    OVER_QUERY_LIMIT("OVER_QUERY_LIMIT"),
    REQUEST_DENIED("REQUEST_DENIED"),
    INVALID_REQUEST("INVALID_REQUEST"),
    UNKNOWN_ERROR("UNKNOWN_ERROR");

    private String val;

    ResponseType(String val) {
        this.val = val;
    }

    public static ResponseType getEnum(String val) {
        for (ResponseType r : values()) {
            if (r.toString().equals((val))) return r;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return val;
    }
}
