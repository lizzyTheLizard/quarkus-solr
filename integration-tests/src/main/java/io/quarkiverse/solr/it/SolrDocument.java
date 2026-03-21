package io.quarkiverse.solr.it;

import org.apache.solr.client.solrj.beans.Field;

public class SolrDocument {
    @Field
    public String id;

    @Field("name")
    public String name;

    @Field("description")
    public String description;

    @SuppressWarnings("unused") //Used internally by solr
    public SolrDocument() {
    }

    @SuppressWarnings("unused") //Used internally by jackson
    public SolrDocument(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
