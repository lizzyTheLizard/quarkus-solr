package io.quarkiverse.solr;

import org.apache.solr.client.solrj.beans.Field;

public class SolrBean {
    @Field
    public String id;
    @Field("name_s")
    public String name;

    public SolrBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @SuppressWarnings("unused") //Used internally by solr
    public SolrBean() {
    }
}
