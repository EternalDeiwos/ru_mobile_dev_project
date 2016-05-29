package com.github.eternaldeiwos.biomapapp.model;

/**
 * Created by glinklater on 2016/05/29.
 */

public class Taxonomy {
    public String taxon_id;
    public String taxon_name;
    public String rank;
    public String common_name;
    public String project;

    public Taxonomy() { /* default */ }

    public Taxonomy(String taxon_id, String taxon_name, String rank, String common_name) {
        this.taxon_id = taxon_id;
        this.taxon_name = taxon_name;
        this.rank = rank;
        this.common_name = common_name;
    }

    public Taxonomy(String taxon_id, String taxon_name, String rank, String common_name, String project) {
        this.taxon_id = taxon_id;
        this.taxon_name = taxon_name;
        this.rank = rank;
        this.common_name = common_name;
        this.project = project;
    }

    public Taxonomy(String taxon_id, String taxon_name, String rank, String common_name, Project project) {
        this.taxon_id = taxon_id;
        this.taxon_name = taxon_name;
        this.rank = rank;
        this.common_name = common_name;
        this.project = project.toString();
    }

    @Override
    public String toString() {
        return this.common_name;
    }
}
