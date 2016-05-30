package com.github.eternaldeiwos.biomapapp.model;

/**
 * Created by glinklater on 2016/05/28.
 */

public class Permission {
    public String app_name;
    public boolean insert = false;
    public boolean select = false;
    public boolean update = false;
    public boolean delete = false;
    public boolean user_required = true;
    public boolean trusted = false;

    public Permission(String app_name, boolean insert, boolean select, boolean update, boolean delete, boolean user_required, boolean trusted) {
        this.app_name = app_name;
        this.insert = insert;
        this.select = select;
        this.update = update;
        this.delete = delete;
        this.user_required = user_required;
        this.trusted = trusted;
    }

    public Permission() { /* default */ }

    public String debugText() {
        StringBuffer sb = new StringBuffer();

        sb.append(app_name);
        sb.append(":");
        sb.append(" i:" + insert + ";");
        sb.append(" s:" + select + ";");
        sb.append(" u:" + update + ";");
        sb.append(" d:" + delete + ";");
        sb.append(" r:" + user_required + ";");
        sb.append(" t:" + trusted + ";");

        return sb.toString();
    }

    @Override
    public String toString() { return app_name; }
}
